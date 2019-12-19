// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Graphic;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.AffineTransformOp;

public class GraphicShape extends GraphicObject {

    private Shape shape;
    private Color color;
    private boolean fill;

    public GraphicShape(Shape shape, Color color, boolean fill) {
        this.color = color;
        this.shape = shape;
        this.fill = fill;
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
        if(shape instanceof Path2D){
            Path2D h = (Path2D) ((Path2D)shape).clone();
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
    }
}

