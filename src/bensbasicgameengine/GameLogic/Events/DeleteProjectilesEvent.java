// Copyright 2019, Benedikt Strobel, All rights reserved.
package bensbasicgameengine.GameLogic.Events;

import bensbasicgameengine.GameLogic.Events.LogicEvent;
import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.Physic.PhysicsObject;

import java.util.ArrayList;

public class DeleteProjectilesEvent extends LogicEvent {

    private GameObject gameObject;

    public DeleteProjectilesEvent(GameObject gameObject){
        this.gameObject = gameObject;
    }

    @Override
    public String getTransmissionData() {
        return "1";
    }

    @Override
    public void eventmethod() {
        gameObject.setGarbage(true);
    }

    @Override
    public boolean eventstate() {
        ArrayList <PhysicsObject> list = gameObject.getPhysicsObject().getCollides();
        for(PhysicsObject physicsObject : list){
            if(physicsObject.isSolid()){
                return true;
            }
        }
        return false;
    }
}
