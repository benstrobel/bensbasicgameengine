// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.GameLogic;

import bensbasicgameengine.GameLogic.Events.LogicEvent;
import bensbasicgameengine.Graphic.GraphicImage;
import bensbasicgameengine.Graphic.GraphicObject;
import bensbasicgameengine.Graphic.GraphicShape;
import bensbasicgameengine.Physic.PhysicsObject;
import bensbasicgameengine.Physic.PhysicsRectangle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class GameObject {

    private PhysicsObject physicsObject;
    private BufferedImage bufferedImage;
    private int graphiclayerid;
    private double orientation = 0;
    private ArrayList<LogicEvent> logicEvents;
    private Color graphicshapecolor;
    private boolean garbage = false, fill = false;
    private String flag = "";
    private int iD;

    public String getTransmissionData(char delimiter){
        String c;
        if(graphicshapecolor == null){
            c = "0";
        }else{
            c = graphicshapecolor.toString();
        }
        return iD + delimiter + orientation + delimiter + c + delimiter + garbage + delimiter + fill + delimiter + flag + delimiter + physicsObject.getTransmissionData(delimiter) + delimiter + getTransmissionData(logicEvents,delimiter);
    }

    public String getTransmissionData(ArrayList<LogicEvent> logicEvents, char delimiter){
        String s = "";
        for(LogicEvent logicEvent : logicEvents){
            String logs = logicEvent.getTransmissionData();
            if(!logs.equals("-")){
                s += (logs + delimiter);
            }
        }
        return s.substring(0,s.length()-1);
    }

    public GameObject(int iD, PhysicsObject physicsObject, BufferedImage bufferedImage){
        this.physicsObject = physicsObject;
        this.bufferedImage = bufferedImage;
        this.iD = iD;
    }

    public GameObject(int iD, PhysicsObject physicsObject, Color graphicshapecolor, boolean fill){
        this.physicsObject = physicsObject;
        this.graphicshapecolor = graphicshapecolor;
        this.fill = fill;
        this.iD = iD;
    }

    public void tick(){
        handleLocalEvents();
    }

    public void setFlag(String flag){
        this.flag = flag;
        physicsObject.setFlag(flag);
    }

    public String getFlag() {
        return flag;
    }

    public void setGraphiclayerid(int graphiclayerid) {
        this.graphiclayerid = graphiclayerid;
    }

    public int getGraphiclayerid() {
        return graphiclayerid;
    }

    public PhysicsObject getPhysicsObject() {
        return physicsObject;
    }

    public GraphicObject getGraphicObject() {
        if(graphicshapecolor != null){
            if(physicsObject instanceof PhysicsRectangle){
                return new GraphicShape(((PhysicsRectangle)physicsObject).getunrotatedShape(),graphicshapecolor, isFill(),((PhysicsRectangle)physicsObject).getOrientation());
            }else{
                return new GraphicShape(physicsObject.getShape(),graphicshapecolor, isFill(),orientation);
            }
        }else{
            if(bufferedImage == null){return null;}
            GraphicImage image = new GraphicImage(bufferedImage, physicsObject);
            image.setOrientation(orientation);
            return image;
        }
    }

    public double getOrientation() {
        return orientation;
    }

    public void rotate(double angle){
        physicsObject.setOrientation(angle);
        orientation = angle;
    }

    private void handleLocalEvents(){
        if(logicEvents == null){return;}
        synchronized (logicEvents){
            for(Iterator<LogicEvent> it = logicEvents.iterator(); it.hasNext();){
                LogicEvent event = it.next();
                if(event.isRemoveFlag()){it.remove();continue;}
                if(event.eventstate()){
                    event.eventmethod();
                }
            }
        }
    }

    public void registerLogicEvent(LogicEvent event){
        if(logicEvents == null){logicEvents = new ArrayList<>();}
        synchronized (logicEvents){
            logicEvents.add(event);
        }
    }

    public void removeLogicEvent(LogicEvent event){
        if(logicEvents == null){return;}
        synchronized (logicEvents){
            logicEvents.remove(event);
        }
    }

    public boolean isGarbage() {
        return garbage;
    }

    public void setGarbage(boolean garbage) {
        this.garbage = garbage;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }
}
