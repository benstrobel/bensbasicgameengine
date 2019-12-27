// Copyright 2019, Benedikt Strobel, All rights reserved.
package bensbasicgameengine.GameLogic.Events;

import bensbasicgameengine.GameLogic.HudObject;
import bensbasicgameengine.Input.Mouse_Listener;

import java.awt.geom.Point2D;

public class HudClickEvent extends LogicEvent{

    private HudObject hudObject;
    private Mouse_Listener mouse_listener;

    public HudClickEvent(HudObject hudObject, Mouse_Listener mouse_listener){
        this.mouse_listener = mouse_listener;
        this.hudObject = hudObject;
    }

    @Override
    public void eventmethod(){
        hudObject.activationMethod();
    }

    @Override
    public boolean eventstate() {
        Point2D mousepos = mouse_listener.getPos();
        if(mouse_listener.isLeftMousePressed()){
            return hudObject.isEnabled()&& (mousepos.getX() > hudObject.getX()) && (mousepos.getY() > hudObject.getY())
                    && (mousepos.getX() < hudObject.getX()+hudObject.getWidth()) && (mousepos.getY() < hudObject.getY()+hudObject.getHeight());
        }
        return false;
    }
}
