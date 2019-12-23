package bensbasicgameengine.GameLogic.Events;

import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.Physic.PhysicsObject;

public class CollisionDeleteEvent extends LogicEvent {

    private GameObject gameObject;

    public CollisionDeleteEvent(GameObject gameObject){
        this.gameObject = gameObject;
    }

    @Override
    public void eventmethod() {
        for(PhysicsObject physicsObject : gameObject.getPhysicsObject().getCollides()){
            if(!gameObject.getFlag().equals("deadzone")){
                physicsObject.getParent().setGarbage(true);
            }
        }
    }

    @Override
    public boolean eventstate() {
        return gameObject.getPhysicsObject().iscolliding();
    }
}
