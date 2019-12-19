// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Physic;

import bensbasicgameengine.GameLogic.GameObject;

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

    public Line2D[] getLines()
    {
        Line2D [] lines = new Line2D[4];
        lines[0] = new Line2D.Double(position.getX(), position.getY(), position.getX(), position.getY()+height);
        lines[1] = new Line2D.Double(position.getX(), position.getY(), position.getX()+width, position.getY());
        lines[2] = new Line2D.Double(position.getX()+width, position.getY()+height, position.getX()+width, position.getY());
        lines[3] = new Line2D.Double(position.getX()+width, position.getY()+height, position.getX(), position.getY()+height);
        return lines;
    }

    public void updateShape(){
        setShape(new Rectangle2D.Double(position.getX(), position.getY(), width, height));
    }

    @Override
    public Shape getShape()
    {
        return rotate(getOrientation());
    }

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

    private Shape rotate(double angle){
        AffineTransform t = new AffineTransform();
        t.rotate(Math.toRadians(angle),shape.getBounds2D().getX()+shape.getBounds2D().getWidth()/2,shape.getBounds2D().getY() + shape.getBounds2D().getHeight()/2);
        Path2D.Double p = (Path2D.Double) t.createTransformedShape(shape);
        return p;
    }

    @Override
    public boolean detectCollision(PhysicsObject object) {
        if(object instanceof PhysicsCircle){return PhysicsObject.detectCollision((PhysicsCircle)object, this);}
        if(object instanceof PhysicsRectangle){return PhysicsObject.detectCollision(this, (PhysicsRectangle) object);}
        return false;
    }
}