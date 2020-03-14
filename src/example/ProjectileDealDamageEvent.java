package example;

import bensbasicgameengine.GameLogic.Events.LogicEvent;
import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.Physic.PhysicsObject;

public class ProjectileDealDamageEvent extends LogicEvent {

    GameObject projectile;
    int damage;

    public ProjectileDealDamageEvent(GameObject projectile, int damage){
        this.projectile = projectile;
        this.damage = damage;
    }

    @Override
    public String getTransmissionData() {
        return null;
    }

    @Override
    public void eventmethod() {
        for(PhysicsObject collidingobject : projectile.getPhysicsObject().getCollides()){
            if(collidingobject.getParent().getHealth() != -1){
                collidingobject.getParent().addHealth(-damage);
                projectile.setGarbage(true);
            }
        }
    }

    @Override
    public boolean eventstate() {
        return projectile.getPhysicsObject().iscolliding();
    }
}
