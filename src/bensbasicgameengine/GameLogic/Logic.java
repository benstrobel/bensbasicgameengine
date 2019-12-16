// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.GameLogic;

import bensbasicgameengine.Graphic.Graphic;
import bensbasicgameengine.Graphic.GraphicObject;
import bensbasicgameengine.Graphic.GraphicShape;
import bensbasicgameengine.Input.KeyListener;
import bensbasicgameengine.Input.MouseMove_Listener;
import bensbasicgameengine.Input.Mouse_Listener;
import bensbasicgameengine.Lib.Tools;
import bensbasicgameengine.Physic.Physics;
import bensbasicgameengine.Physic.PhysicsObject;
import bensbasicgameengine.Sound.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Logic {

    private Graphic graphic;
    private Physics physics;
    private SoundManager soundManager;
    private KeyListener keyListener;
    private Mouse_Listener mouse_listener;
    private MouseMove_Listener mouseMove_listener;


    private long tickcounter = 0;
    private final int tickspersecond = 60;
    private final int waittime = 1000/tickspersecond; //in ms
    private boolean run = true, showhitbox = false;
    private int graphiclayers = -1;

    private ArrayList<LogicEvent> logicEvents;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<GraphicObject> hudObjects;
    private ArrayList<PhysicsObject> triggerObjects;

    //TODO Show Hitbox

    public Logic(Graphic graphic, Physics physics, SoundManager soundManager, KeyListener keyListener, Mouse_Listener mouse_listener, MouseMove_Listener mouseMove_listener){
        this.graphic = graphic;
        this.physics = physics;
        this.soundManager = soundManager;
        this.keyListener = keyListener;
        this.mouse_listener = mouse_listener;
        this.mouseMove_listener = mouseMove_listener;
        logicEvents = new ArrayList<>();
        gameObjects = new ArrayList<>();
        hudObjects = new ArrayList<>();
        triggerObjects = new ArrayList<>();
        if(mouse_listener != null){
            graphic.getPanel().addMouseListener(mouse_listener);
        }
        if(mouseMove_listener != null){
            graphic.getPanel().addMouseMotionListener(mouseMove_listener);
        }
    }

    private void tick(){
        if(graphic != null){graphic.repaint();}
        if(soundManager != null){soundManager.tick();}
        logictick();
        graphictick();
    }

    public void startloop(){
        loop();
    }

    private void logictick(){
        handleGlobalEvents();
        physics.tick();
        tickcounter++;
    }

    private void graphictick(){
        graphic.clear();
        addgameObjects();
        addhudObjects();
        graphic.repaint();
    }

    private void addgameObjects(){
        synchronized (gameObjects){
            synchronized (graphic.getObjectlist()){
                for(GameObject gameObject : gameObjects){
                    if(gameObject.getGraphiclayerid() <= graphiclayers){
                        graphic.add(gameObject.getGraphiclayerid(), gameObject.getGraphicObject());
                    }
                }
            }
        }
    }

    private void addhudObjects(){
        synchronized (gameObjects){
            synchronized (graphic.getObjectlist()){
                for(GameObject gameObject : gameObjects){
                    Color c = null;
                    if(gameObject.getPhysicsObject().iscolliding()){c = Color.RED;}else{c = Color.GREEN;}
                    graphic.add(graphiclayers,new GraphicShape(gameObject.getPhysicsObject().getShape(), c, false));
                }
            }
        }
        synchronized (hudObjects){
            synchronized (graphic.getObjectlist()){
                for(GraphicObject graphicObject : hudObjects){
                    graphic.add(graphiclayers,graphicObject);
                }
            }
        }
    }

    public void addGraphicLayer(){
        graphiclayers = graphic.addList();
    }

    private void handleGlobalEvents(){
        synchronized (logicEvents){
            for(Iterator<LogicEvent> it = logicEvents.iterator(); it.hasNext();){
                LogicEvent event = it.next();
                if(event.isRemoveFlag()){it.remove();continue;}
                if(event.eventstate()){
                    event.eventmethod();
                }
            }
        }
    }

    private void loop(){
        long time;
        long timepassed;
        while(run){
            time = System.currentTimeMillis();
            tick();
            timepassed = System.currentTimeMillis() - time;
            Tools.threadsleep(waittime-timepassed);
        }
    }

    public void registerLogicEvent(LogicEvent event){
        synchronized (logicEvents){
            logicEvents.add(event);
        }
    }

    public void removeLogicEvent(LogicEvent event){
        synchronized (logicEvents){
            logicEvents.remove(event);
        }
    }

    public long getTickcounter() {
        return tickcounter;
    }

    public void addGameObject(GameObject gameObject){
        synchronized (gameObjects){
            gameObjects.add(gameObject);
            physics.addObject(gameObject.getPhysicsObject());
        }
    }

    public void removeGameObject(GameObject gameObject){
        synchronized (gameObjects){
            gameObjects.remove(gameObject);
            physics.removeObject(gameObject.getPhysicsObject());
        }
    }

    public void setGraphiclayers(int graphiclayers){
        this.graphiclayers = graphiclayers;
    }

    public boolean isshowinghitboxes(){
        return showhitbox;
    }

    public void setShowhitbox(boolean showhitbox){
        this.showhitbox = showhitbox;
    }
}
