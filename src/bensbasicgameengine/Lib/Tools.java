// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Lib;

import bensbasicgameengine.Physic.PhysicsObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class Tools {

    public static void threadsleep(long ms){
        if(ms < 1){return;}
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Point2D calculateDirection(Point2D from, Point2D to){
        return mulVector(normVector(new Point2D.Double(to.getX()-from.getX(),to.getY()-from.getY())),5);    //Get vector on length 5
    }

    public static Point2D calculateDirection(PhysicsObject from, PhysicsObject to){
        Point2D source = getMiddle(from);
        Point2D target = (Point2D) to.getPosition().clone();
        return  calculateDirection(source,target);
    }

    public static Point2D getMiddle(PhysicsObject from){
        Point2D source = (Point2D) from.getPosition().clone();
        source.setLocation(source.getX()+from.getShape().getBounds2D().getWidth()/2,source.getY()+from.getShape().getBounds2D().getHeight()/2);
        return source;
    }

    public static double getDegree(Point2D m){
        double x = m.getX(), y = m.getY();
        double d = Math.abs(Math.toDegrees(Math.atan(m.getX()/m.getY())));
        if(x >= 0){
            //left
            if(y >= 0){
                //lower
                return 180-d;
            }else{
                //upper
                return d;
            }
        }else{
            //right
            if(y >= 0){
                //lower
                return 180+d;
            }else{
                //upper
                return 360-d;
            }
        }
    }

    public static Point2D mulVector(Point2D vector, double factor){
        return new Point2D.Double(vector.getX()*factor,vector.getY()*factor);
    }

    public static Point2D normVector(Point2D vector){
        double factor = 1/Math.sqrt(Math.pow(vector.getX(),2)+Math.pow(vector.getY(),2));
        return new Point2D.Double(vector.getX()*factor,vector.getY()*factor);
    }

    public static int differance(int a, int b){
        if(a > b){
            return Math.abs(a-b);
        }else{
            return Math.abs(b-a);
        }
    }

    public static Shape rotateShape(double angle, Shape shape){
        AffineTransform t = new AffineTransform();
        t.rotate(Math.toRadians(angle),shape.getBounds2D().getX()+shape.getBounds2D().getWidth()/2,shape.getBounds2D().getY() + shape.getBounds2D().getHeight()/2);
        Path2D.Double p = (Path2D.Double) t.createTransformedShape(shape);
        return p;
    }
}
