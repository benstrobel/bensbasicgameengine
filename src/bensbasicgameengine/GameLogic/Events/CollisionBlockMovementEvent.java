package bensbasicgameengine.GameLogic.Events;

import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.Physic.PhysicsCircle;
import bensbasicgameengine.Physic.PhysicsObject;
import bensbasicgameengine.Physic.PhysicsRectangle;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class CollisionBlockMovementEvent extends LogicEvent {

    GameObject gameObject;

    public CollisionBlockMovementEvent(GameObject gameObject){
        this.gameObject = gameObject;
    }

    @Override
    public void eventmethod() {
        ArrayList<PhysicsObject> list =  (ArrayList<PhysicsObject>) gameObject.getPhysicsObject().getCollides().clone();
        for(PhysicsObject phyObj : list){
            if(phyObj.getFlag() == "wall"){
                continue;
            }
            PhysicsObject c = null;
            try {
                c = (PhysicsObject) phyObj.clone();
                c.setHypothetical();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            if(c != null){
                c.getPosition().setLocation(c.getPosition().getX()+c.getVelocityX(), c.getPosition().getY()+c.getVelocityY());
                c.updateShape();
                c.tick();
                if(c.detectCollision(gameObject.getPhysicsObject())){
                    phyObj.setVelocityX(0);
                    phyObj.setVelocityY(0);
                }
            }
        }
    }

    @Override
    public boolean eventstate() {
        return gameObject.getPhysicsObject().iscolliding();
    }
}
