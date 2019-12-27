// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Graphic;

import bensbasicgameengine.Lib.Tools;

import java.awt.*;
import java.awt.geom.*;

public class GraphicShape extends GraphicObject {

    private Shape shape;
    private Color color;
    private boolean fill, isStatic = false;
    private double orientation;

    public GraphicShape(Shape shape, Color color, boolean fill, double orientation) {
        this.color = color;
        this.shape = shape;
        this.fill = fill;
        this.orientation = orientation;
    }

    public GraphicShape(Shape shape, Color color, boolean fill, double orientation, boolean isStatic) {
        this.color = color;
        this.shape = shape;
        this.fill = fill;
        this.orientation = orientation;
        this.isStatic = isStatic;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(color);
        Shape s = null;
        if(!isStatic){
            if(shape instanceof Rectangle2D){
                Rectangle2D h = (Rectangle2D) ((Rectangle2D)shape).clone();
                h = new Rectangle2D.Double(h.getX()+getCameralocation().getX(),h.getY()+getCameralocation().getY(),h.getWidth(),h.getHeight());
                s = Tools.rotateShape(orientation,h);
            }
            if(shape instanceof Ellipse2D){
                Ellipse2D h = ((Ellipse2D)shape);
                s = new Ellipse2D.Double(h.getX()+getCameralocation().getX(),h.getY()+getCameralocation().getY(),h.getWidth(),h.getHeight());
            }
            if(s != null){
                g2d.draw(s);
                if (fill) {
                    g2d.fill(s);
                }
            }else{
                g2d.draw(shape);
                if (fill) {
                    g2d.fill(shape);
                }
            }
        }else{
            g2d.draw(shape);
            if (fill) {
                g2d.fill(shape);
            }
        }

    }
}

