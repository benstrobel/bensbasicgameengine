package Physic;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Physics {

    private ArrayList<PhysicsObject> objectlist = new ArrayList<>();

    public Physics(){

    }

    public void tick(){
        //ArrayList<PhysicsObject> fol = new ArrayList<>();
        objectlist.forEach(obj -> obj.setColliding(null));
        objectlist.forEach(obj -> {obj.getPosition().setLocation(obj.getPosition().getX()+obj.getVelocityX(), obj.getPosition().getY()+obj.getVelocityY()); obj.updateShape(); obj.tick();});
        for(PhysicsObject obj : objectlist){
            for(PhysicsObject iobj : objectlist){
                obj.detectCollision(iobj);
            }
        }
    }

    public void tick(Consumer<PhysicsObject> lambda){
        if(lambda != null){
            for(PhysicsObject obj : objectlist){
                lambda.accept(obj);
                obj.updateShape();
            }
        }
        tick();
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
