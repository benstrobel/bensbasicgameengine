// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Physic;

import java.awt.*;
import java.awt.geom.*;

public class PhysicsRectangle extends PhysicsObject{
    private int height,width;

    public PhysicsRectangle(Point2D position, double mass, int height, int width)
    {
        super(position, mass);
        this.height = height;
        this.width = width;
        updateShape();
    }

    public PhysicsRectangle(Point2D position, double mass, int height, int width, int textureid)
    {
        super(position, mass);
        this.height = height;
        this.width = width;
        this.textureid = textureid;
        updateShape();
    }

    private PhysicsRectangle(Point2D position, double velX, double velY, double orientation, boolean unmovable, boolean hypothetical, boolean solid, int textureid, double originalwidth, double originalheight, String flag, int width, int height){
        super(position,1);
        this.velocityX = velX;
        this.velocityY = velY;
        this.orientation = orientation;
        this.unmoveable = unmovable;
        this.hypothetical = hypothetical;
        this.solid = solid;
        this.textureid = textureid;
        this.originalwidth = originalwidth;
        this.originalheight = originalheight;
        this.setFlag(flag);
        this.width = width;
        this.height = height;
        updateShape();
    }

    @Override
    public String getTransmissionData(char delimiter){
        return "r" + delimiter + super.getTransmissionData(delimiter) + delimiter + width + delimiter + height;
    }

    public static PhysicsRectangle createfromTransmissionData(String data, char delimiter){
        String [] array = data.split(""+delimiter);
        if(array[0].equals("r")){
            Point2D position = new Point2D.Double(Double.parseDouble(array[1]), Double.parseDouble(array[2]));
            double velX = Double.parseDouble(array[3]);
            double velY = Double.parseDouble(array[4]);
            double orientation = Double.parseDouble(array[5]);
            boolean unmovable = Boolean.parseBoolean(array[6]);
            boolean hypothetical = Boolean.parseBoolean(array[7]);
            boolean solid = Boolean.parseBoolean(array[8]);
            int textureid = Integer.parseInt(array[9]);
            double originalwidth = Double.parseDouble(array[10]);
            double originalheight = Double.parseDouble(array[11]);
            String flag = array[12];
            int width = Integer.parseInt(array[13]);
            int height = Integer.parseInt(array[14]);
            return new PhysicsRectangle(position,velX,velY,orientation,unmovable,hypothetical,solid,textureid,originalwidth,originalheight,flag,width,height);
        }else{
            return null;
        }
    }

    public void updateShape(){
        setShape(new Rectangle2D.Double(position.getX(), position.getY(), width, height));
    }

    @Override
    public Shape getShape()
    {
        return rotate(getOrientation());
    }

    @Override
    public Point2D getCenterPosition() {
        Point2D p = (Point2D) getPosition().clone();
        return new Point2D.Double(p.getX()+width/2,p.getY()+height/2);
    }

    public Shape getunrotatedShape(){return shape;}

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Shape rotate(double angle){
        AffineTransform t = new AffineTransform();
        t.rotate(Math.toRadians(angle),shape.getBounds2D().getX()+shape.getBounds2D().getWidth()/2,shape.getBounds2D().getY() + shape.getBounds2D().getHeight()/2);
        Path2D.Double p = (Path2D.Double) t.createTransformedShape(shape);
        return p;
    }

    @Override
    public boolean detectCollision(PhysicsObject object) {
        Line2D line = new Line2D.Double(0,0,10,10);
        return detectCollisionGeneral(this,object);
        //if(object instanceof PhysicsCircle){return PhysicsObject.detectCollision((PhysicsCircle)object, this);}
        //if(object instanceof PhysicsRectangle){return PhysicsObject.detectCollision(this, (PhysicsRectangle) object);}
        //return false;
    }
}