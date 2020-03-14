package example;

import bensbasicgameengine.GameLogic.Events.LogicEvent;
import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.Physic.PhysicsObject;
import example.Weapons.Weapon;

public class PickupWeaponEvent extends LogicEvent {

    private GameObject gameObject;
    private Weapon weapon;

    public PickupWeaponEvent(GameObject gameObject, Weapon weapon){
        this.gameObject = gameObject;
        this.weapon = weapon;
    }


    @Override
    public String getTransmissionData() {
        return null;
    }

    @Override
    public void eventmethod() {
        for(PhysicsObject collidingobject : gameObject.getPhysicsObject().getCollides()){
            if(collidingobject.getParent().getHealth() != -1){
                collidingobject.getParent().giveWeapon(weapon);
                //gameObject.setGarbage(true);
                break;
            }
        }
    }

    @Override
    public boolean eventstate() {
        return gameObject.getPhysicsObject().iscolliding();
    }
}
