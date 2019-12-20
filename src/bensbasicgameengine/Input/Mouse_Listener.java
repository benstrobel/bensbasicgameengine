// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

public class Mouse_Listener implements MouseListener  {

    /*Note: All Values need to be resetted manually via the reset() method.
      This means you should "poll" the MouseState once per tick to ensure no input gets lost.
     */

    private boolean mousePressed = false, mouseReleased = false, mouseEntered = false, mouseExited = false;
    private Point2D pos;

    public void reset(){
        mousePressed = false;
        mouseEntered = false;
        mouseExited = false;
        mouseReleased = false;
        pos = null;
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

    public boolean isMosuePressed() {
        return mousePressed;
    }

    public boolean isMouseReleased() {
        return mouseReleased;
    }

    public Point2D getPos() {
        return pos;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //mousePressed = true;
        pos = e.getPoint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        pos = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseReleased = true;
        mousePressed = false;
        pos = e.getPoint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseEntered = true;
        pos = e.getPoint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseExited = true;
        pos = e.getPoint();
    }
}
