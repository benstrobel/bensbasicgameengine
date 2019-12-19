// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Lib;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Tools {

    public static void threadsleep(long ms){
        if(ms < 1){return;}
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
