package bensbasicgameengine.Graphic;

import java.awt.*;

public abstract class GraphicObject {

    protected float alpha = 1.0F;

    public float getAlpha() {
        return alpha;
    }

    public abstract void paint(Graphics2D g2d);
}
