// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

public class MouseMove_Listener implements MouseMotionListener {
    private int mousex = 0,mousey = 0;
    private boolean dragged = false;

    public int getMousex() {
        return mousex;
    }

    public int getMousey() {
        return mousey;
    }

    public boolean isGettingDragged(){
        return dragged;
    }

    public Point2D getMousePos(){
        return new Point2D.Double(mousex,mousey);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragged = true;
        mousex = e.getX();//- Game.widthoffset;
        mousey = e.getY();//-Game.heightoffset;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        dragged = false;
        mousex = e.getX();//- Game.widthoffset;
        mousey = e.getY();//-Game.heightoffset;
    }
}
