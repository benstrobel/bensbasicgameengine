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
    public String getTransmissionData() {
        return "-";
    }

    @Override
    public void eventmethod(){
        hudObject.activationMethod();
    }

    @Override
    public boolean eventstate() {
        if(mouse_listener.isMousechanged()){
            if(mouse_listener.isLeftMousePressed()){
                Point2D mousepos = mouse_listener.getPos();
                if(hudObject.isEnabled()&& (mousepos.getX() > hudObject.getX()) && (mousepos.getY() > hudObject.getY())
                        && (mousepos.getX() < hudObject.getX()+hudObject.getWidth()) && (mousepos.getY() < hudObject.getY()+hudObject.getHeight())){
                    mouse_listener.resetAll();
                    return true;
                }else{
                    return false;
                }
                //return
            }
            return false;
        }
        return false;
    }
}
