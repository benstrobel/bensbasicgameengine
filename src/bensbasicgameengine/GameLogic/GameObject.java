// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.GameLogic;

import bensbasicgameengine.Graphic.GraphicImage;
import bensbasicgameengine.Graphic.GraphicObject;
import bensbasicgameengine.Physic.PhysicsObject;
import bensbasicgameengine.Physic.PhysicsRectangle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class GameObject {

    private PhysicsObject physicsObject;
    private BufferedImage bufferedImage;
    private int graphiclayerid;
    private double orientation = 0;
    private ArrayList<LogicEvent> logicEvents;
    private boolean garbage = false, fill = false;

    public GameObject(PhysicsObject physicsObject, BufferedImage bufferedImage){
        this.physicsObject = physicsObject;
        this.bufferedImage = bufferedImage;
    }

    public void tick(){
        handleLocalEvents();
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
        if(bufferedImage == null){return null;}
        GraphicImage image = new GraphicImage(bufferedImage, physicsObject);
        image.setOrientation(orientation);
        return image;
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
