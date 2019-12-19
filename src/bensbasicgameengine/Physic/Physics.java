// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Physic;

import java.util.ArrayList;

public class Physics {

    //TODO Add composits

    private ArrayList<PhysicsObject> objectlist = new ArrayList<>();

    public Physics(){

    }

    public void tick(){
        objectlist.forEach(obj -> obj.resetColliding());
        objectlist.forEach(obj -> {obj.getPosition().setLocation(obj.getPosition().getX()+obj.getVelocityX(), obj.getPosition().getY()+obj.getVelocityY()); obj.updateShape(); obj.tick();});
        for(PhysicsObject obj : objectlist){
            for(PhysicsObject iobj : objectlist){
                obj.detectCollision(iobj);
            }
        }
    }

    public boolean addObject(PhysicsObject obj){
        return objectlist.add(obj);
    }

    public boolean removeObject(PhysicsObject obj){
        return objectlist.remove(obj);
    }

    public ArrayList<PhysicsObject> getObjectList(){
        return objectlist;
    }

    public PhysicsObject getObject(int id){
        return objectlist.get(id);
    }
}
