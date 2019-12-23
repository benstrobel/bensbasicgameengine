package bensbasicgameengine.GameLogic.Events;

import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.Physic.PhysicsCircle;
import bensbasicgameengine.Physic.PhysicsObject;
import bensbasicgameengine.Physic.PhysicsRectangle;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class CollisionBlockMovementEvent extends LogicEvent {

    GameObject gameObject;

    public CollisionBlockMovementEvent(GameObject gameObject){
        this.gameObject = gameObject;
    }

    @Override
    public void eventmethod() {
        //TODO
    }

    @Override
    public boolean eventstate() {
        return gameObject.getPhysicsObject().iscolliding();
    }
}
