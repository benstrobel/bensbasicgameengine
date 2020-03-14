// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.GameLogic;

import bensbasicgameengine.GameLogic.Events.CollisionDeleteEvent;
import bensbasicgameengine.GameLogic.Events.DeleteProjectilesEvent;
import bensbasicgameengine.GameLogic.Events.LogicEvent;
import bensbasicgameengine.Graphic.Graphic;
import bensbasicgameengine.Graphic.GraphicShape;
import bensbasicgameengine.Graphic.GraphicString;
import bensbasicgameengine.Input.KeyListener;
import bensbasicgameengine.Input.MouseMove_Listener;
import bensbasicgameengine.Input.Mouse_Listener;
import bensbasicgameengine.Input.StringContainer;
import bensbasicgameengine.Lib.Tools;
import bensbasicgameengine.Net.Client.Client;
import bensbasicgameengine.Net.Server.Server;
import bensbasicgameengine.Physic.Physics;
import bensbasicgameengine.Physic.PhysicsObject;
import bensbasicgameengine.Physic.PhysicsRectangle;
import bensbasicgameengine.Sound.SoundManager;
import example.ProjectileDealDamageEvent;
import example.Weapons.Weapon;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

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
    private boolean run = true, showhitbox = false, pause = false;
    private int graphiclayers = -1;
    private PhysicsObject camfollowobject;
    private Point2D camlocation = new Point2D.Double(0,0);
    private int currentid = 0;

    private ArrayList<LogicEvent> logicEvents;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<HudObject> hudObjects;
    private ArrayList<PhysicsObject> triggerObjects;

    private String transmitstring = "";
    private Client client;
    private Server server;
    private boolean isserver;
    public AtomicInteger menustatus;
    private ArrayList<Integer> deletedIDs = new ArrayList<>();

    public boolean mainmenu = true;
    private String ip;
    public int focusid = 0;
    public int clienttextureid = 0;

    public Logic(Graphic graphic, Physics physics, SoundManager soundManager, KeyListener keyListener, Mouse_Listener mouse_listener, MouseMove_Listener mouseMove_listener, Client client, AtomicInteger menustatus){
        this.menustatus = menustatus;
        this.graphic = graphic;
        graphic.setCameralocation(camlocation);
        this.physics = physics;
        this.soundManager = soundManager;
        this.keyListener = keyListener;
        this.mouse_listener = mouse_listener;
        this.mouseMove_listener = mouseMove_listener;
        this.client = client;
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

    public void setIp(StringContainer ip){
        this.ip = ip.getString();
    }

    public void mainmenuloop(){
        while(mainmenu){
            handleGlobalEvents();
            graphictick();
        }
    }

    public void setServer(Server server){
        this.server = server;
    }

    public void sendAllGameObjects(){
        gameObjects.forEach(o -> o.setChanged(true));
    }

    public void updateHUDObjects(){
        for(HudObject hudObject : hudObjects){
            if(hudObject.getMenuid() == menustatus.get()){
                hudObject.setEnabled(true);
            }else{
                hudObject.setEnabled(false);
            }
        }
    }

    public void clearGameObjects(){
        synchronized (gameObjects){
            gameObjects.clear();
        }
    }

    public String getTransmitData(){
        return transmitstring;
    }

    private void updateTransmitData(){
        String s = "U ";
        for(GameObject object : gameObjects){
            if(object.isChanged()){
                s += object.getTransmissionData(';');
                s += " ";
                object.setChanged(false);
            } else{
                if(object.isPoschange()){
                    s += object.getPosTransmissionData(';');
                    s+= " ";
                }
                if(object.isRotchange()){
                    s += object.getRotTransmissionData(';');
                    s+= " ";
                }
            }
        }
        for(int iD : deletedIDs){
            s += GameObject.getDelTransmissionData(';', iD);
            s += " ";
        }
        if(s.equals("")){
            transmitstring = "-";
        }else{
            transmitstring = s;
        }
        deletedIDs.clear();
        server.publish(transmitstring);
    }

    public void deleteObjectWithId(int iD){
        synchronized (gameObjects){
            for(GameObject gameObject : gameObjects){
                if(gameObject.getiD() == iD){
                    gameObject.setGarbage(true);
                    break;
                }
            }
        }
    }

    public void updateFromTransmitData(String data){
        if(data != null){
            if(data.equals("-")){
                logictick();
                return;
            }
            String [] array = data.split(" ");
            for(String s : array){
                GameObject go = GameObject.createfromTransmissionData(s,';', gameObjects);
                if(go != null){
                    synchronized (gameObjects){
                        gameObjects.removeIf(g -> g.getiD() == go.getiD());
                        gameObjects.add(go);
                    }
                }
            }
        }
    }

    public int getNextID(){
        return currentid++;
    }

    public GameObject getGameObjectwithID(int iD){
        synchronized (gameObjects){
            for(GameObject gameObject : gameObjects){
                if(gameObject.getiD() == iD){return gameObject;}
            }
            return null;
        }
    }

    private void tick(){
        if(graphic != null){graphic.repaint();}
        if(soundManager != null){soundManager.tick();}
        if(!pause){
            if(isserver){
                logictick();
                updateTransmitData();
            }else{
                handleGlobalEvents();
                updateFromTransmitData(client.getConnectionHandler().getData());
                camtick();
            }
        }
        graphictick();
    }

    public void setPause(boolean pause){
        this.pause = pause;
    }

    public boolean isPause() {
        return pause;
    }

    public void startloop(boolean isserver){
        this.isserver = isserver;
        loop();
    }

    private void camtick(){
        for(GameObject o : gameObjects){
            if(o.getiD() == focusid){
                camfollowobject = o.getPhysicsObject();
            }
        }
        if(camfollowobject != null){
            Dimension framedim = graphic.getFramedimension();
            camlocation.setLocation(camfollowobject.getCenterPosition().getX()-framedim.width/2,camfollowobject.getCenterPosition().getY()-framedim.height/2);
            graphic.setCameralocation(new Point2D.Double(-camfollowobject.getCenterPosition().getX()+framedim.width/2,-camfollowobject.getCenterPosition().getY()+framedim.height/2));
        }
    }

    private void logictick(){
        handleGlobalEvents();
        handleLocalEventsAndCollectGarbage();
        physics.tick();
        camtick();
        mouse_listener.setMousechanged(false);
        tickcounter++;
    }

    private void handleLocalEventsAndCollectGarbage(){
        synchronized (gameObjects){
            for(Iterator i = gameObjects.iterator(); i.hasNext();){
                GameObject gameObject = (GameObject) i.next();
                gameObject.tick();
                if(gameObject.isGarbage()){
                    deletedIDs.add(gameObject.getiD());
                    physics.removeObject(gameObject.getPhysicsObject());
                    i.remove();
                }
            }
        }
    }

    private void graphictick(){
        synchronized (graphic.getObjectlist()){
            graphic.clear();
            addgameObjects();
            addhudObjects();
        }
        graphic.repaint();
    }

    private void addgameObjects(){
        synchronized (gameObjects){
            synchronized (graphic.getObjectlist()){
                for(GameObject gameObject : gameObjects){
                    if(gameObject.getGraphicObject() != null && gameObject.getGraphiclayerid() <= graphiclayers){
                        graphic.add(gameObject.getGraphiclayerid(), gameObject.getGraphicObject());
                    }
                }
            }
        }
    }

    public void addhudObjects(){
        synchronized (gameObjects){
            if(showhitbox){
                synchronized (graphic.getObjectlist()){
                    for(GameObject gameObject : gameObjects){
                        Color c = null;
                        if(gameObject.getPhysicsObject().iscolliding()){c = Color.RED;}else{c = Color.GREEN;}
                        PhysicsObject physicsObject = gameObject.getPhysicsObject();
                        if(physicsObject instanceof PhysicsRectangle){
                            graphic.add(graphiclayers,new GraphicShape(((PhysicsRectangle)physicsObject).getunrotatedShape(), c, gameObject.isFill(),physicsObject.getOrientation()));
                        }else{
                            graphic.add(graphiclayers,new GraphicShape(physicsObject.getShape(), c, gameObject.isFill(),physicsObject.getOrientation()));
                        }
                    }
                }
            }
        }
        synchronized (hudObjects){
            synchronized (graphic.getObjectlist()){
                for(HudObject hudObject : hudObjects){
                    if(hudObject.isEnabled()){
                        graphic.add(graphiclayers,hudObject.getGraphicObject());
                        if(!hudObject.getText().equals("")){
                            graphic.add(graphiclayers, new GraphicString(hudObject.getText(),hudObject.getX(),hudObject.getY()));
                        }
                    }
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
            if(waittime < timepassed){System.out.println("Can't keep up, timediff: " + (waittime-timepassed));}
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
            gameObject.setGarbage(true);
        }
    }

    public void addHudObject(HudObject hudObject){
        hudObjects.add(hudObject);
    }

    public void removeHudObject(HudObject hudObject){
        hudObjects.remove(hudObject);
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

    public GameObject createGameObject(PhysicsObject physicsObject, BufferedImage bufferedImage){
        GameObject gameObject = new GameObject(getNextID(),physicsObject,bufferedImage);
        physicsObject.setParent(gameObject);
        return gameObject;
    }

    public GameObject createGameObject(PhysicsObject physicsObject, Color color, boolean fill){
        GameObject gameObject = new GameObject(getNextID(),physicsObject,color,fill);
        physicsObject.setParent(gameObject);
        return gameObject;
    }

    public void addDeadZone(double x, double y, int height, int width){
        PhysicsObject deadzonerect = new PhysicsRectangle(new Point2D.Double(x,y), 1, height, width);
        deadzonerect.setUnmoveable(true);
        GameObject deadzone = createGameObject(deadzonerect,null);
        deadzone.registerLogicEvent(new CollisionDeleteEvent(deadzone));
        deadzone.setFill(true);
        deadzone.setFlag("deadzone");
        addGameObject(deadzone);
    }

    public void addWall(double x, double y, int height, int width){
        PhysicsObject wallrect = new PhysicsRectangle(new Point2D.Double(x,y),1, height,width);
        wallrect.setUnmoveable(true);
        GameObject wall = createGameObject(wallrect,Color.GRAY, true);
        //wall.registerLogicEvent(new CollisionBlockMovementEvent(wall));
        wallrect.setSolid(true);
        wall.setFlag("wall");
        addGameObject(wall);
    }

    public void forcecamfollow(PhysicsObject camcenterpos){
        this.camfollowobject = camcenterpos;
    }

    public Point2D getCamlocation(){
        return camlocation;
    }

    public boolean isserver() {
        return isserver;
    }

    public void entityClick(GameObject entity, Point2D position){
        Weapon weapon = entity.getWeapon();
        if(weapon != null){
            if(weapon.shoot()){
                if(entity.getlastclickedintick() == tickcounter-1){
                    if(weapon.isFullautocapable() && weapon.isFullautoenabled()){
                        createProjectile(entity,position,(int)weapon.getDamage());
                    }
                }else{
                    createProjectile(entity,position,(int)weapon.getDamage());
                }
            }
        }
        entity.setlastclickedintick(tickcounter);
    }

    public void entityreload(GameObject entity){
        Weapon weapon = entity.getWeapon();
        if(weapon != null){
            weapon.reload();
        }
    }

    public void createProjectile(GameObject source, Point2D target, int damage){
        PhysicsObject projectilerectangle = new PhysicsRectangle(Tools.getMiddle(source.getPhysicsObject()), 1, 10, 5);
        GameObject projectile = new GameObject(getNextID(),projectilerectangle, Color.black, true);
        projectilerectangle.setParent(projectile);
        Point2D direction = Tools.calculateDirection(Tools.getMiddle(projectilerectangle),target,20);
        Point2D adddirection = Tools.calculateDirection(Tools.getMiddle(projectilerectangle),target,60);
        projectilerectangle.getPosition().setLocation(Tools.addVector(projectilerectangle.getPosition(),adddirection));
        projectile.getPhysicsObject().updateShape();
        projectilerectangle.setVelocityX(direction.getX());
        projectilerectangle.setVelocityY(direction.getY());
        projectilerectangle.setOrientation(Tools.getDegree(direction));
        projectile.registerLogicEvent(new DeleteProjectilesEvent(projectile));
        projectile.registerLogicEvent(new ProjectileDealDamageEvent(projectile,damage));
        addGameObject(projectile);
    }
}
