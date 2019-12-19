// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Graphic;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class GraphicObject {

    protected float alpha = 1.0F;

    public float getAlpha() {
        return alpha;
    }

    public abstract void paint(Graphics2D g2d);

    public Point2D getCameralocation() {
        return Graphic.getCameralocation();
    }
}
