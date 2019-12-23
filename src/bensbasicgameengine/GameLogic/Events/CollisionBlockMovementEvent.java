package bensbasicgameengine.GameLogic.Events;

import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.Physic.PhysicsObject;

public class CollisionBlockMovementEvent extends LogicEvent {

    GameObject gameObject;

    public CollisionBlockMovementEvent(GameObject gameObject){
        this.gameObject = gameObject;
    }

    @Override
    public void eventmethod() {
        for(PhysicsObject target : gameObject.getPhysicsObject().getCollides()){

        }
    }

    @Override
    public boolean eventstate() {
        return gameObject.getPhysicsObject().iscolliding();
    }
}
