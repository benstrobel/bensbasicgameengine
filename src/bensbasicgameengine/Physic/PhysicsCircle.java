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

    public void updateShape(){
        setShape(new Ellipse2D.Double(position.getX(), position.getY(), radius, radius));
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
        if(object instanceof PhysicsCircle){return PhysicsObject.detectCollision(this, (PhysicsCircle)object);}
        return false;
    }
}