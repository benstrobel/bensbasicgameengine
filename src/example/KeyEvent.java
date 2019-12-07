// Copyright 2019, Benedikt Strobel, All rights reserved.
package example;

import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.GameLogic.LogicEvent;
import bensbasicgameengine.Input.KeyListener;

public class KeyEvent extends LogicEvent {

    KeyListener keyListener;
    GameObject player;

    public KeyEvent(KeyListener keyListener, GameObject player){
        this.keyListener = keyListener;
        this.player = player;
    }

    @Override
    public void eventmethod() {
        boolean keys [] = keyListener.getKeysAndReset();
        if(keys[KeyListener.W]){
            if(keys[KeyListener.S]){
                player.getPhysicsObject().setVelocityY(0);
            }else{
                player.getPhysicsObject().setVelocityY(-5);
            }
        }else {
            if(keys[KeyListener.S]){
                player.getPhysicsObject().setVelocityY(5);
            }else{
                player.getPhysicsObject().setVelocityY(0);
            }
        }
        if(keys[KeyListener.A]){
            if(keys[KeyListener.D]){
                player.getPhysicsObject().setVelocityX(0);
            }else{
                player.getPhysicsObject().setVelocityX(-5);
            }
        }else{
            if(keys[KeyListener.D]){
                player.getPhysicsObject().setVelocityX(5);
            }else{
                player.getPhysicsObject().setVelocityX(0);
            }
        }

    }

    @Override
    public boolean eventstate() {
        return keyListener.isKeypressed();
    }
}
