// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.GameLogic;

import bensbasicgameengine.GameLogic.Events.LogicEvent;
import bensbasicgameengine.Graphic.GraphicImage;
import bensbasicgameengine.Graphic.GraphicObject;
import bensbasicgameengine.Graphic.GraphicShape;
import bensbasicgameengine.Physic.PhysicsObject;
import bensbasicgameengine.Physic.PhysicsRectangle;
import example.Game;
import example.Weapons.Weapon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class GameObject {

    private PhysicsObject physicsObject;
    private BufferedImage bufferedImage;
    private int graphiclayerid;
    private double orientation = 0;
    private ArrayList<LogicEvent> logicEvents;
    private Color graphicshapecolor;
    private boolean garbage = false, fill = false;
    private String flag = "";
    private Weapon weapon;
    private int iD;
    private int imgid = -1;
    private int health = -1;
    private boolean changed = true, poschange = false, rotchange = false, velchange = false;
    private long lastclickedintick = -1;

    public boolean isPoschange() {
        return poschange;
    }

    public boolean isRotchange() {
        return rotchange;
    }

    public void setlastclickedintick(long lastclickedintick) {
        this.lastclickedintick = lastclickedintick;
    }

    public long getlastclickedintick() {
        return lastclickedintick;
    }

    public void setHealth(int amount){
        health = amount;
    }

    public int getHealth() {
        return health;
    }

    public void addHealth(int amount){
        if(health != -1){
            if(health+amount < 0){
                health = 0;
                physicsObject.setLocation(100,100);
            }else{
                health += amount;
            }
        }
    }

    public void giveWeapon(Weapon weapon){
        this.weapon = weapon;
        changed = true;
    }

    public void removeWeapon(){
        weapon = null;
        changed = true;
    }

    public String getPosTransmissionData(char delimiter){
        poschange = false;
        return "p" + delimiter + iD + delimiter + physicsObject.getPosition().getX() + delimiter + physicsObject.getPosition().getY();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public static void applyPosTransmissionData(String data, char delimiter, ArrayList<GameObject> gameObjects){
        String [] array = data.split(""+delimiter);
        for(GameObject gameObject : gameObjects){
            if(gameObject.getiD() == Integer.parseInt(array[1])){
                gameObject.getPhysicsObject().setLocation(Double.parseDouble(array[2]), Double.parseDouble(array[3]));
                gameObject.physicsObject.updateShape();
                break;
            }
        }
    }

    public String getRotTransmissionData(char delimiter){
        rotchange = false;
        return "r" + delimiter + iD + delimiter + physicsObject.getOrientation();
    }

    public static void applyRotTransmissionData(String data, char delimiter, ArrayList<GameObject> gameObjects){
        String [] array = data.split(""+delimiter);
        for(GameObject gameObject : gameObjects){
            if(gameObject.getiD() == Integer.parseInt(array[1])){
                gameObject.rotate(Double.parseDouble(array[2]));
                break;
            }
        }
    }

    public String getDelTransmissionData(char delimiter){
        return "d" + delimiter + iD;
    }
    public static String getDelTransmissionData(char delimiter, int iD){
        return "d" + delimiter + iD;
    }

    public static void applyDelTransmissionData(String data, char delimiter, ArrayList<GameObject> gameObjects){
        String [] array = data.split(""+delimiter);
        synchronized (gameObjects){
            gameObjects.removeIf(o -> o.getiD() == Integer.parseInt(array[1]));
        }
    }

    public String getTransmissionData(char delimiter){
        String c;
        if(graphicshapecolor == null){
            c = "0";
        }else{
            c = Integer.toString(graphicshapecolor.getRGB());
        }
        String weaponstring = "-";
        if(weapon != null){weaponstring = weapon.getTransmissionData();}
        return "n" + delimiter + iD + delimiter + imgid + delimiter + orientation + delimiter + c  + delimiter + fill + delimiter + flag + delimiter + physicsObject.getTransmissionData('_') + delimiter + weaponstring + delimiter + health;
    }

    public static GameObject createfromTransmissionData(String data, char delimiter, ArrayList<GameObject> gameObjects){
        String [] array = data.split(""+delimiter);
        if(array[0].equals("n")){
            return createnewfromTransmissionData(data.substring(2),delimiter);
        } else if(array[0].equals("d")) {
            applyDelTransmissionData(data,delimiter,gameObjects);
            return null;
        } else if (array[0].equals("r")){
            applyRotTransmissionData(data,delimiter,gameObjects);
            return null;
        } else if (array[0].equals("p")){
            applyPosTransmissionData(data,delimiter,gameObjects);
            return null;
        } else {
            return null;
        }
    }

    public static GameObject createnewfromTransmissionData(String data, char delimiter){
        String [] array = data.split(""+delimiter);
        int iD = Integer.parseInt(array[0]);
        int imgid = Integer.parseInt(array[1]);
        BufferedImage img = null;
        if(imgid != -1 ){
            img = Game.textures[imgid];
        }
        double orientation = Double.parseDouble(array[2]);
        Color c;
        if(array[3].equals("0")){
            c = null;
        }else{
            c = new Color(Integer.parseInt(array[3]));
        }
        boolean fill = Boolean.parseBoolean(array[4]);
        String flag = array[5];
        PhysicsObject phyobj = PhysicsObject.createfromTransmissionData(array[6],'_');
        Weapon weapon;
        if(!array[7].equals("-")){
            weapon = Weapon.createFromTransmission(array[7]);
        }else{
            weapon = null;
        }
        return new GameObject(iD, img, orientation, c, fill, flag, phyobj, Integer.parseInt(array[1]), weapon, Integer.parseInt(array[8]));
    }

    /*public String getTransmissionData(ArrayList<LogicEvent> logicEvents, char delimiter){
        String s = "";
        for(LogicEvent logicEvent : logicEvents){
            String logs = logicEvent.getTransmissionData();
            if(!logs.equals("-")){
                s += (logs + delimiter);
            }
        }
        return s.substring(0,s.length()-1);
    }*/

    public GameObject(int iD, BufferedImage bufferedImage, double orientation, Color c, boolean fill, String flag, PhysicsObject physicsObject, int imgid, Weapon weapon, int health){
        this.iD = iD;
        this.bufferedImage = bufferedImage;
        this.orientation = orientation;
        this.graphicshapecolor = c;
        this.fill = fill;
        this.flag = flag;
        this.physicsObject = physicsObject;
        this.imgid = imgid;
        this.weapon = weapon;
        this.health = health;
        physicsObject.setParent(this);
    }

    public GameObject(int iD, PhysicsObject physicsObject, BufferedImage bufferedImage){
        this.physicsObject = physicsObject;
        this.bufferedImage = bufferedImage;
        this.iD = iD;
        for(int i = 0; i < Game.textures.length; i++){
            if(Game.textures[i] == bufferedImage){
                imgid = i;
                break;
            }
        }
    }

    public GameObject(int iD, PhysicsObject physicsObject, Color graphicshapecolor, boolean fill){
        this.physicsObject = physicsObject;
        this.graphicshapecolor = graphicshapecolor;
        this.fill = fill;
        this.iD = iD;
    }

    public void tick(){
        handleLocalEvents();

        if(weapon != null){
            weapon.tick();
        }
    }

    public void setFlag(String flag){
        this.flag = flag;
        physicsObject.setFlag(flag);
    }

    public String getFlag() {
        return flag;
    }

    public void setGraphiclayerid(int graphiclayerid) {
        this.graphiclayerid = graphiclayerid;
    }

    public int getGraphiclayerid() {
        return graphiclayerid;
    }

    public PhysicsObject getPhysicsObject() {
        return physicsObject;
    }

    public GraphicObject getGraphicObject() {
        if(graphicshapecolor != null){
            if(physicsObject instanceof PhysicsRectangle){
                return new GraphicShape(((PhysicsRectangle)physicsObject).getunrotatedShape(),graphicshapecolor, isFill(),((PhysicsRectangle)physicsObject).getOrientation());
            }else{
                return new GraphicShape(physicsObject.getShape(),graphicshapecolor, isFill(),orientation);
            }
        }else{
            if(bufferedImage == null){return null;}
            GraphicImage image = new GraphicImage(bufferedImage, physicsObject);
            image.setOrientation(orientation);
            return image;
        }
    }

    public double getOrientation() {
        return orientation;
    }

    public void rotate(double angle){
        physicsObject.setOrientation(angle);
        orientation = angle;
        rotchange = true;
    }

    private void handleLocalEvents(){
        if(logicEvents == null){return;}
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

    public void registerLogicEvent(LogicEvent event){
        if(logicEvents == null){logicEvents = new ArrayList<>();}
        synchronized (logicEvents){
            logicEvents.add(event);
        }
    }

    public void removeLogicEvent(LogicEvent event){
        if(logicEvents == null){return;}
        synchronized (logicEvents){
            logicEvents.remove(event);
        }
    }

    public boolean isGarbage() {
        return garbage;
    }

    public void setGarbage(boolean garbage) {
        this.garbage = garbage;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public boolean isChanged(){
        return changed;
    }

    public void setChanged(boolean changed){
        this.changed = changed;
    }

    public int getiD(){
        return iD;
    }

    public void setPoschange(boolean poschange){
        this.poschange = poschange;
    }

    public void setVelchange(boolean velchange){
        this.velchange = velchange;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        for(int i = 0; i < Game.textures.length; i++){
            if(Game.textures[i] == bufferedImage){
                imgid = i;
                break;
            }
        }
        changed = true;
    }
}
