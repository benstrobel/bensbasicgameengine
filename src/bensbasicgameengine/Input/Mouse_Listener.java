// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

public class Mouse_Listener implements MouseListener  {

    /*Note: All Values need to be resetted manually via the reset() method.
      This means you should "poll" the MouseState once per tick to ensure no input gets lost.
     */

    private boolean leftmousepressed = false, rightmousepressed = false, middlemousepressed = false, mouseEntered = false, mouseExited = false, mouseReleased = false, mousechanged = false;
    private Point2D pos;

    public void resetAll(){
        leftmousepressed = false;
        rightmousepressed = false;
        middlemousepressed = false;
        mouseEntered = false;
        mouseExited = false;
        mouseReleased = false;
        mousechanged = false;
    }

    public void resetLeftMouse(){
        leftmousepressed = false;
    }

    public void resetRightMouse(){
        rightmousepressed = false;
    }

    public void resetMiddleMouse(){
        middlemousepressed = false;
    }

    public boolean isLeftMousePressed() {
        return leftmousepressed;
    }

    public boolean isMiddleMousepressed() {
        return middlemousepressed;
    }

    public boolean isRightMousepressed() {
        return rightmousepressed;
    }

    public boolean isMouseEntered() {
        return mouseEntered;
    }

    public boolean isMouseExited() {
        return mouseExited;
    }

    public boolean isMouseReleased() {
        return mouseReleased;
    }

    public boolean isMousechanged() {
        return mousechanged;
    }

    public void setMousechanged(boolean mousechanged) {
        this.mousechanged = mousechanged;
    }

    public Point2D getPos() {
        return pos;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        boolean ol = leftmousepressed, or = rightmousepressed, om = middlemousepressed;
        pos = e.getPoint();
        if(e.getButton() == MouseEvent.BUTTON1){
            leftmousepressed = true;
        }else{
            if(e.getButton() == MouseEvent.BUTTON2){
                rightmousepressed = true;
            }else{
                if(e.getButton() == MouseEvent.BUTTON3){
                    middlemousepressed = true;
                }
            }
        }
        if(ol != leftmousepressed || or != rightmousepressed || om != middlemousepressed){
            mousechanged = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        boolean ol = leftmousepressed, or = rightmousepressed, om = middlemousepressed;
        pos = e.getPoint();
        if(e.getButton() == MouseEvent.BUTTON1){
            leftmousepressed = false;
        }else{
            if(e.getButton() == MouseEvent.BUTTON2){
                rightmousepressed = false;
            }else{
                if(e.getButton() == MouseEvent.BUTTON3){
                    middlemousepressed = false;
                }
            }
        }
        mouseReleased = true;
        if(ol != leftmousepressed || or != rightmousepressed || om != middlemousepressed){
            mousechanged = true;
        }
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
