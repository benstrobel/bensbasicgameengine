package bensbasicgameengine.Graphic;

import java.awt.*;
import java.awt.geom.Point2D;

public class GraphicString extends GraphicObject {

    private String text = "";
    private Point2D pos;

    public GraphicString(String text, int x, int y){
        this.text = text;
        pos = new Point2D.Double(x,y+10);
    }

    public GraphicString(String text, Point2D pos){
        this.text = text;
        this.pos = pos;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.drawString(text,(int)pos.getX(),(int)pos.getY());
    }
}
