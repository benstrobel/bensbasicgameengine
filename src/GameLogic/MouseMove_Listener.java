package GameLogic;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

public class MouseMove_Listener implements MouseMotionListener {
    private int mousex = 0,mousey = 0;

    public int getMousex() {
        return mousex;
    }

    public int getMousey() {
        return mousey;
    }

    public Point2D getMousePos(){
        return new Point2D.Double(mousex,mousey);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("Move X: " + e.getX() + " Y: " + e.getY());
        mousex = e.getX()-Game.widthoffset;
        mousey = e.getY()-Game.heightoffset;
    }
}
