// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Physic;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class PhysicsCircle extends PhysicsObject{

    private double radius;
    public PhysicsCircle(Point2D position, double mass, double radius)
    {
        super(position, mass);
        this.radius = radius;
        updateShape();
    }

    public PhysicsCircle(Point2D position, double mass, double radius, int textureid)
    {
        super(position, mass);
        this.radius = radius;
        this.textureid = textureid;
        updateShape();
    }

    private PhysicsCircle(Point2D position, double velX, double velY, double orientation, boolean unmovable, boolean hypothetical, boolean solid, int textureid, double originalwidth, double originalheight, String flag, double radius){
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
        this.radius = radius;
        updateShape();
    }

    public void updateShape(){
        setShape(new Ellipse2D.Double(position.getX(), position.getY(), radius, radius));
    }

    @Override
    public String getTransmissionData(char delimiter){
        return "c" + delimiter + super.getTransmissionData(delimiter) + delimiter + radius;
    }

    public static PhysicsCircle createfromTransmissionData(String data, char delimiter){
        String [] array = data.split(""+delimiter);
        if(array[0].equals("c")){
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
            double radius = Double.parseDouble(array[13]);
            return new PhysicsCircle(position,velX,velY,orientation,unmovable,hypothetical,solid,textureid,originalwidth,originalheight,flag,radius);
        }else{
            return null;
        }
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public Point2D getCenterPosition() {
        Point2D p = (Point2D) getPosition().clone();
        return new Point2D.Double(p.getX()+radius,p.getY()+radius);
    }

    public void bump(){
        if(tickcounter > protection+10){
            velocityX = -velocityX;
            velocityY = -velocityY;
            //System.out.println("Bump");
            protection = tickcounter;
        }
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Point2D getPhysicsPosition(){
        return new Point2D.Double(position.getX()+radius/2,position.getY()+radius/2);
    }

    @Override
    public boolean detectCollision(PhysicsObject object) {
        return detectCollisionGeneral(this,object);
        //if(object instanceof PhysicsCircle){return PhysicsObject.detectCollision(this, (PhysicsCircle)object);}
        //return false;
    }
}