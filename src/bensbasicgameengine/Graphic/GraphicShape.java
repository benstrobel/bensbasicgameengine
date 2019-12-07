// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Graphic;

import java.awt.*;

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
        g2d.draw(shape);
        if (fill) {
            g2d.fill(shape);
        }
    }
}

