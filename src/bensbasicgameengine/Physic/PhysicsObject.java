// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Physic;

import bensbasicgameengine.GameLogic.GameObject;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class PhysicsObject implements Cloneable{
    protected Point2D position;
    protected double mass;
    protected double velocityX;
    protected double velocityY;
    protected double orientation;
    protected Shape shape;
    protected ArrayList <PhysicsObject> collides = new ArrayList<>();
    protected int tickcounter = 0;
    protected boolean unmoveable = false, removeflag = false, hypothetical = false, solid = false;
    protected int textureid = -1;
    protected double originalwidth, originalheight;
    protected GameObject parent;
    private String flag = "";
    int protection = 0;
    public PhysicsObject(Point2D position, double mass)
    {
        this.position = position;
        this.mass = mass;
    }

    public String getTransmissionData(char delimiter){
        return "" + position.getX() + delimiter + position.getY() + delimiter + velocityX + delimiter + velocityY + delimiter + orientation +delimiter
                + unmoveable + delimiter + hypothetical + delimiter + solid + delimiter + textureid +
                delimiter + originalwidth + delimiter + originalheight + delimiter + flag;
    }

    public static PhysicsObject createfromTransmissionData(String data, char delimiter){
        String [] array = data.split(""+delimiter);
        if(array[0].equals("c")){
            return PhysicsCircle.createfromTransmissionData(data,delimiter);
        }else if(array[0].equals("r")){
            return PhysicsRectangle.createfromTransmissionData(data,delimiter);
        }else{return null;}
    }

    public void setHypothetical(){
        hypothetical = true;
    }

    public void setHypothetical(boolean state){
        hypothetical = state;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
        parent.setChanged(true);
    }

    public boolean isHypothetical(){
        return hypothetical;
    }

    public void setFlag(String flag){
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
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
        if(!collides.contains(object)){
            collides.add(object);
        }
    }

    public void resetColliding(){collides.clear();}

    public abstract void updateShape();

    public Shape getShape(){return shape;};

    public Point2D getPosition() {
        return position;
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

    public void setOrientation(double orientation) { this.orientation = orientation;}

    public void setLocation(double x, double y){
        if(x != position.getX() || y != position.getY()){
            parent.setPoschange(true);
        }
        position.setLocation(x,y);
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setMass(double mass) {
        this.mass = mass;
        parent.setChanged(true);
    }

    public void setVelocityX(double velocityX) {
        if(velocityX != this.velocityX){
            this.velocityX = velocityX;
            //parent.setChanged(true);
        }
    }

    public void setVelocityY(double velocityY) {
        if(velocityY != this.velocityY){
            this.velocityY = velocityY;
            //parent.setChanged(true);
        }
    }

    public void addVelocityX(double velocityX){
        this.velocityX+=velocityX;
        if(velocityX != 0){
            //parent.setChanged(true);
        }
    }

    public void addVelocityY(double velocityY){
        this.velocityY+=velocityY;
        if(velocityY != 0){
            //parent.setChanged(true);
        }
    }

    public abstract Point2D getCenterPosition();

    public void setShape(Shape shape) {
        this.shape = shape;
        originalheight = shape.getBounds2D().getHeight();
        originalwidth = shape.getBounds2D().getWidth();
    }

    public double getOriginalHeight() {
        return originalheight;
    }

    public double getOriginalWidth() {
        return originalwidth;
    }

    public abstract boolean detectCollision(PhysicsObject object);

    public static boolean detectCollision(PhysicsCircle circle0, PhysicsCircle circle1){
        /*if(circle0.equals(circle1)){return false;}
        if(circle1.getRadius()*0.75 > circle0.getPhysicsPosition().distance(circle1.getPhysicsPosition()) || circle0.getRadius()*0.75 > circle1.getPhysicsPosition().distance(circle0.getPhysicsPosition()))
        {
            circle0.setColliding(circle1);
            circle1.setColliding(circle0);
            //distributeVelocity(circle0,circle1);
            return true;
        }
        return false;*/
        return detectCollisionGeneral(circle0,circle1);
    }

    public static boolean detectCollision(PhysicsCircle circle0, PhysicsRectangle rectangle0){
        /*Line2D[] lines = rectangle0.getLines();
        for(Line2D line : lines)
        {
            if(Math.abs(line.ptSegDist(circle0.getPhysicsPosition())) < circle0.getRadius()*0.50)
            {
                circle0.setColliding(rectangle0);
                rectangle0.setColliding(circle0);
                return true;
            }
        }
        return false;*/
        return detectCollisionGeneral(circle0,rectangle0);
    }

    public static boolean detectCollision(PhysicsRectangle rectangle0, PhysicsRectangle rectangle1){
        return detectCollisionGeneral(rectangle0,rectangle1);
    }

    private static boolean checkForFuturCollision(PhysicsObject futur, PhysicsObject stat){
        PhysicsObject phyobj0 = null;
        PhysicsObject phyobj00 = null;
        try {
            phyobj0 = (PhysicsObject) futur.clone();
            phyobj0.setHypothetical();
            phyobj00 = (PhysicsObject) futur.clone();
            phyobj00.setHypothetical();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        //--------------Checking for X
        phyobj0.getPosition().setLocation(phyobj0.getPosition().getX()+phyobj0.getVelocityX(), phyobj0.getPosition().getY());
        phyobj0.updateShape();
        phyobj00.getPosition().setLocation(phyobj00.getPosition().getX(), phyobj00.getPosition().getY()+phyobj00.getVelocityY());
        phyobj00.updateShape();
        if(!phyobj0.getShape().getBounds2D().intersects(stat.getShape().getBounds2D()) && !phyobj00.getShape().getBounds2D().intersects(stat.getShape().getBounds2D())){
            return false;
        }
        Area a = new Area(phyobj0.getShape());
        a.intersect(new Area(stat.getShape()));
        if(!a.isEmpty()){
            futur.setColliding(stat);
            stat.setColliding(futur);
            if(stat.isSolid()){
                futur.setVelocityX(0);
            }
            return true;
        }
        //------------Checking For Y
        a = new Area(phyobj00.getShape());
        a.intersect(new Area(stat.getShape()));
        if(!a.isEmpty()){
            futur.setColliding(stat);
            stat.setColliding(futur);
            if(stat.isSolid()){
                futur.setVelocityY(0);
            }
            return true;
        }
        return false;
    }

    public static boolean detectCollisionGeneral(PhysicsObject obj0, PhysicsObject phyobj1){
        if(obj0 == phyobj1){return false;}
        if(obj0.getFlag().equals("wall") && phyobj1.getFlag().equals("wall")){return false;}
        if(obj0.getFlag().equals("deadzone") && phyobj1.getFlag().equals("deadzone")){return false;}
        return checkForFuturCollision(obj0,phyobj1);
        //Possible performance increase: first check if bounds itersect, if they dont false, if they do then return result area intersect check
        //Possible performance increase: Currently each collision is being checked twice, from perspectives of the objects
    }

    public ArrayList<PhysicsObject> getCollides() {
        return collides;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public GameObject getParent() {
        return parent;
    }

    public Object clone() throws CloneNotSupportedException {
        PhysicsObject c = (PhysicsObject) super.clone();
        c.setHypothetical(true);
        c.setPosition((Point2D) position.clone());
        c.setHypothetical(false);
        return c;
    }
}