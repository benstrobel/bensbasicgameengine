// Copyright 2019, Benedikt Strobel, All rights reserved.
package bensbasicgameengine.GameLogic.Events;

import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.Physic.PhysicsObject;

public class CollisionDeleteEvent extends LogicEvent {

    private GameObject gameObject;

    public CollisionDeleteEvent(GameObject gameObject){
        this.gameObject = gameObject;
    }

    @Override
    public String getTransmissionData() {
        return "0";
    }

    @Override
    public void eventmethod() {
        for(PhysicsObject physicsObject : gameObject.getPhysicsObject().getCollides()){
            if(!physicsObject.getFlag().equals("deadzone")){
                physicsObject.getParent().setGarbage(true);
            }
        }
    }

    @Override
    public boolean eventstate() {
        return gameObject.getPhysicsObject().iscolliding();
    }
}
