package GameLogic;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse_Listener implements MouseListener  {
    private boolean mousePressed = false;

    public void reset(){
        mousePressed = false;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
