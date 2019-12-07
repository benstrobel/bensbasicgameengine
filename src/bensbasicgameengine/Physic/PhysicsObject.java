package bensbasicgameengine.Physic;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class PhysicsObject{
    protected Point2D position,centerofmass;
    protected double mass;
    protected double velocityX;
    protected double velocityY;
    protected double orientation;
    protected Shape shape;
    protected ArrayList <PhysicsObject> collides = new ArrayList<>();
    protected int tickcounter = 0;
    protected boolean unmoveable = false;
    protected int textureid = -1;
    protected boolean removeflag = false;
    protected boolean ignoreinsidecollision = false;
    int protection = 0;
    public PhysicsObject(Point2D position, Point2D centerofmass, double mass)
    {
        this.position = position;
        this.centerofmass = centerofmass;
        this.mass = mass;
    }

    public int getTextureid() {
        return textureid;
    }

    public String toString(){
        return "TextureID: " +textureid;
    }

    public void setRemoveflag(boolean removeflag){
        this.removeflag = removeflag;
    }

    public boolean isRemoveflag() {
        return removeflag;
    }

    public void tick()
    {
        tickcounter++;
    }

    public boolean isUnmoveable() {
        return unmoveable;
    }

    public void setUnmoveable(boolean unmoveable) {
        this.unmoveable = unmoveable;
    }

    public boolean iscolliding() {
        return (collides.size() != 0);
    }

    public boolean isCollidingWith(PhysicsObject object){
        return collides.contains(object);
    }

    public void setColliding(PhysicsObject object) {
        collides.add(object);
    }

    public void resetColliding(){collides.clear();}

    public abstract void updateShape();

    public Shape getShape(){return shape;};

    public Point2D getPosition() {
        return position;
    }

    public Point2D getCenterofmass() {
        return centerofmass;
    }

    public double getMass() {
        return mass;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getOrientation() {return orientation; }

    public void setOrientation(double orientation) { this.orientation = orientation; }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setCenterofmass(Point2D centerofmass) {
        this.centerofmass = centerofmass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void addVelocityX(double velocityX){
        this.velocityX+=velocityX;
    }

    public void addVelocityY(double velocityY){
        this.velocityY+=velocityY;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public boolean ignoresinsidecollision() {
        return ignoreinsidecollision;
    }

    public void setIgnoreinsidecollision(boolean ignoreinsidecollision) {
        this.ignoreinsidecollision = ignoreinsidecollision;
    }

    public abstract boolean detectCollision(PhysicsObject object);

    private static void distributeVelocity(PhysicsObject o1, PhysicsObject o2){
        if(o1.isUnmoveable()){
            if(o2.isUnmoveable()){

            }else{
                if(o2.tickcounter > o2.protection+10)
                {
                    o2.velocityX = -o2.velocityX;
                    o2.velocityY = -o2.velocityY;
                    o2.protection = o2.tickcounter;
                }
            }
        }else{
            if(o2.isUnmoveable()){
                if(o1.tickcounter > o1.protection+10)
                {
                    o1.velocityX = -o1.velocityX;
                    o1.velocityY = -o1.velocityY;
                    o1.protection = o1.tickcounter;
                }
            }else{
                if(o1.tickcounter > o1.protection+10 && o2.tickcounter > o2.protection+10)
                {
                    double oldo1x = o1.velocityX;
                    double oldo1y = o1.velocityY;
                    o1.setVelocityX(o1.getVelocityX()+(o1.getMass()*o1.getVelocityX() + o2.getMass() * o2.getVelocityX())/2);
                    o1.setVelocityY(o1.getVelocityY()+(o1.getMass()*o1.getVelocityY() + o2.getMass() * o2.getVelocityY())/2);
                    o2.setVelocityX(o2.getVelocityX()+ (o1.getMass()*oldo1x + o2.getMass() * o2.getVelocityX())/2);
                    o2.setVelocityY(o2.getVelocityY()+ (o1.getMass()*oldo1y + o2.getMass() * o2.getVelocityY())/2);
                    o2.protection = o2.tickcounter;
                    o1.protection = o1.tickcounter;
                }
            }
        }
    }

    public static boolean detectCollision(PhysicsCircle circle0, PhysicsCircle circle1){
        if(circle0.equals(circle1)){return false;}
        if(circle1.getRadius()*0.75 > circle0.getPhysicsPosition().distance(circle1.getPhysicsPosition()) || circle0.getRadius()*0.75 > circle1.getPhysicsPosition().distance(circle0.getPhysicsPosition()))
        {
            circle0.setColliding(circle1);
            circle1.setColliding(circle0);
            //distributeVelocity(circle0,circle1);
            return true;
        }
        return false;
    }

    public static boolean detectCollision(PhysicsCircle circle0, PhysicsRectangle rectangle0){
        Line2D[] lines = rectangle0.getLines();
        for(Line2D line : lines)
        {
            if(Math.abs(line.ptSegDist(circle0.getPhysicsPosition())) < circle0.getRadius()*0.50)
            {
                //System.out.println("Line starting from " + line.getP1() + " to " + line.getP2() + " is colliding with Circle @ " + circle0.getPhysicsPosition() + " with radius " + circle0.getRadius()*0.50);
                circle0.setColliding(rectangle0);
                rectangle0.setColliding(circle0);
                return true;
            }
        }
        return false;
    }

    public static boolean detectCollision(PhysicsRectangle rectangle0, PhysicsRectangle rectangle1){
        if(rectangle1.equals(rectangle0)){return false;}
        Line2D [] otherlines = rectangle1.getLines();
        Line2D [] lines = rectangle0.getLines();
        for(Line2D line : lines)
        {
            for(Line2D oline: otherlines)
            {
                if(line.intersectsLine(oline))
                {
                    rectangle0.setColliding(rectangle1);
                    rectangle1.setColliding(rectangle0);
                    return true;
                }
            }
        }
        if(!rectangle0.ignoresinsidecollision() && !rectangle1.ignoresinsidecollision()&& insideOf(rectangle0,rectangle1)){
            rectangle0.setColliding(rectangle1);
            rectangle1.setColliding(rectangle0);
            return true;
        }
        if (!rectangle0.ignoresinsidecollision() && !rectangle1.ignoresinsidecollision()&& insideOf(rectangle1,rectangle0))
        {
            rectangle0.setColliding(rectangle1);
            rectangle1.setColliding(rectangle0);
            return true;
        }
        return false;
    }

    private static boolean insideOf(PhysicsRectangle obj0, PhysicsObject obj1){
        Point2D pos0 = obj0.getPosition();
        Point2D pos1 = obj1.getPosition();
        return (pos0.getX() < pos1.getX() && pos0.getY() < pos1.getY() && pos0.getX()+obj0.getShape().getBounds2D().getWidth() > pos1.getX() && pos0.getY()+obj0.getShape().getBounds2D().getHeight() > pos1.getY());
    }
}