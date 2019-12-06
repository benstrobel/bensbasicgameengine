package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse_Listener implements MouseListener  {

    /*Note: All Values need to be resetted manually via the reset() method.
      This means you should "poll" the MouseState once per tick to ensure no input gets lost.
     */

    private boolean mousePressed = false, mouseReleased = false, mouseEntered = false, mouseExited = false;

    public void reset(){
        mousePressed = false;
        mouseEntered = false;
        mouseExited = false;
        mouseReleased = false;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public boolean isMouseEntered() {
        return mouseEntered;
    }

    public boolean isMouseExited() {
        return mouseExited;
    }

    public boolean isMosueReleased() {
        return mouseReleased;
    }

    public boolean isMouseReleased() {
        return mouseReleased;
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
        mouseReleased = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseEntered = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseExited = true;
    }
}
