package bensbasicgameengine.GameLogic;

import bensbasicgameengine.Physic.PhysicsObject;

public class CollisionDeleteEvent extends LogicEvent {

    private GameObject gameObject;

    public CollisionDeleteEvent(GameObject gameObject){
        this.gameObject = gameObject;
    }

    @Override
    public void eventmethod() {
        for(PhysicsObject physicsObject : gameObject.getPhysicsObject().getCollides()){
            physicsObject.getParent().setGarbage(true);
        }
    }

    @Override
    public boolean eventstate() {
        return gameObject.getPhysicsObject().iscolliding();
    }
}
