// Copyright 2019, Benedikt Strobel, All rights reserved.
package example;

import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.GameLogic.Events.LogicEvent;
import bensbasicgameengine.GameLogic.HudObject;
import bensbasicgameengine.GameLogic.Logic;
import bensbasicgameengine.Graphic.Graphic;
import bensbasicgameengine.Input.KeyListener;
import bensbasicgameengine.Input.StringContainer;
import bensbasicgameengine.Net.Client.Client;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class KeyEvent extends LogicEvent {

    KeyListener keyListener;
    GameObject player;
    Graphic graphic;
    private AtomicInteger menustatus;
    private StringContainer inputstring;
    private Logic logic;
    private HudObject enterip;
    private Client client;

    public KeyEvent(KeyListener keyListener, GameObject player, Graphic graphic, AtomicInteger menustatus, Logic logic, StringContainer inputstring, HudObject enterip, Client client){
        this.keyListener = keyListener;
        this.player = player;
        this.graphic = graphic;
        this.menustatus = menustatus;
        this.logic = logic;
        this.inputstring = inputstring;
        this.enterip = enterip;
        this.client = client;
    }

    public void setEnterip(HudObject enterip){
        this.enterip = enterip;
    }

    @Override
    public void eventmethod() {
        boolean keys [] = keyListener.getKeysAndReset();
        if(menustatus.get() == 0){
            if(logic.isserver()){
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
                if(keys[KeyListener.Q]){
                    if(!keys[KeyListener.E]){
                        player.rotate(player.getOrientation()-5);
                    }
                }else{
                    if(keys[KeyListener.E]){
                        player.rotate(player.getOrientation()+5);
                    }
                }
                if(keys[KeyListener.LEFT]){
                    if(!keys[KeyListener.RIGHT]){
                        Point2D g = Graphic.getCameralocation();
                        graphic.setCameralocation(new Point2D.Double(g.getX()+5,g.getY()));
                    }
                }
                if(keys[KeyListener.RIGHT]){
                    if(!keys[KeyListener.LEFT]){
                        Point2D g = Graphic.getCameralocation();
                        graphic.setCameralocation(new Point2D.Double(g.getX()-5,g.getY()));
                    }
                }
                if(keys[KeyListener.UP]){
                    if(!keys[KeyListener.DOWN]){
                        Point2D g = Graphic.getCameralocation();
                        graphic.setCameralocation(new Point2D.Double(g.getX(),g.getY()+5));
                    }
                }
                if(keys[KeyListener.DOWN]){
                    if(!keys[KeyListener.UP]){
                        Point2D g = Graphic.getCameralocation();
                        graphic.setCameralocation(new Point2D.Double(g.getX(),g.getY()-5));
                    }
                }
                if(keys[KeyListener.ESC]){
                    if(menustatus.get() == 0){
                        menustatus.set(1);
                    }else{
                        menustatus.set(0);
                    }
                    logic.updateHUDObjects();
                }
            }else{
                if(keys[KeyListener.W]){
                    if(keys[KeyListener.S]){
                        client.send("A WS");
                    }else{
                        client.send("A W");
                    }
                }else {
                    if(keys[KeyListener.S]){
                        client.send("A S");
                    }else{
                        client.send("A WS");
                    }
                }
                if(keys[KeyListener.A]){
                    if(keys[KeyListener.D]){
                        client.send("A AD");
                    }else{
                        client.send("A A");
                    }
                }else{
                    if(keys[KeyListener.D]){
                        client.send("A D");
                    }else{
                        client.send("A AD");
                    }
                }
                if(keys[KeyListener.Q]){
                    if(!keys[KeyListener.E]){
                        client.send("A Q");
                    }
                }else{
                    if(keys[KeyListener.E]){
                        client.send("A E");
                    }
                }
                if(keys[KeyListener.LEFT]){
                    if(!keys[KeyListener.RIGHT]){

                    }
                }
                if(keys[KeyListener.RIGHT]){
                    if(!keys[KeyListener.LEFT]){

                    }
                }
                if(keys[KeyListener.UP]){
                    if(!keys[KeyListener.DOWN]){

                    }
                }
                if(keys[KeyListener.DOWN]){
                    if(!keys[KeyListener.UP]){

                    }
                    }
                }
                if(keys[KeyListener.ESC]){
                    if(menustatus.get() == 0){
                        menustatus.set(1);
                    }else{
                        menustatus.set(0);
                    }
                    logic.updateHUDObjects();
            }
        } else if (menustatus.get() == 1){
            if(keys[KeyListener.ESC]){
                if(menustatus.get() == 0){
                    menustatus.set(1);
                }else{
                    menustatus.set(0);
                }
                logic.updateHUDObjects();
            }
        } else if (menustatus.get() == 3){
            if(keys[KeyListener.ONE]){
                inputstring.add("1");
                enterip.setText("Enter IP: " + inputstring.getString());
                logic.updateHUDObjects();
            }
            if(keys[KeyListener.TWO]){
                inputstring.add("2");
                enterip.setText("Enter IP: " + inputstring.getString());
                logic.updateHUDObjects();
            }
            if(keys[KeyListener.THREE]){
                inputstring.add("3");
                enterip.setText("Enter IP: " + inputstring.getString());
                logic.updateHUDObjects();
            }
            if(keys[KeyListener.FOUR]){
                inputstring.add("4");
                enterip.setText("Enter IP: " + inputstring.getString());
                logic.updateHUDObjects();
            }
            if(keys[KeyListener.FIVE]){
                inputstring.add("5");
                enterip.setText("Enter IP: " + inputstring.getString());
                logic.updateHUDObjects();
            }
            if(keys[KeyListener.SIX]){
                inputstring.add("6");
                enterip.setText("Enter IP: " + inputstring.getString());
                logic.updateHUDObjects();
            }
            if(keys[KeyListener.SEVEN]){
                inputstring.add("7");
                enterip.setText("Enter IP: " + inputstring.getString());
                logic.updateHUDObjects();
            }
            if(keys[KeyListener.EIGHT]){
                inputstring.add("8");
                enterip.setText("Enter IP: " + inputstring.getString());
                logic.updateHUDObjects();
            }
            if(keys[KeyListener.NINE]){
                inputstring.add("9");
                enterip.setText("Enter IP: " + inputstring.getString());
                logic.updateHUDObjects();
            }
            if(keys[KeyListener.ZERO]){
                inputstring.add("0");
                enterip.setText("Enter IP: " + inputstring.getString());
                logic.updateHUDObjects();
            }
            if(keys[KeyListener.DOT]){
                inputstring.add(".");
                enterip.setText("Enter IP: " + inputstring.getString());
                logic.updateHUDObjects();
            }
            if(keys[KeyListener.BACKSPACE]){
                inputstring.setString(inputstring.getString().substring(0,inputstring.getString().length()-1));
                enterip.setText("Enter IP: " + inputstring.getString());
                logic.updateHUDObjects();
            }
            if(keys[KeyListener.ENTER]){
                System.out.println("Hud Object Clicked (Okay)");
                logic.setIp(inputstring);
                menustatus.set(0);
                logic.updateHUDObjects();
                logic.mainmenu = false;
            }
            if(keys[KeyListener.CTRL] && keys[KeyListener.V]){
                String s = "";
                try {
                    s = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                inputstring.add(s);
                enterip.setText("Enter IP: " + inputstring.getString());
                logic.updateHUDObjects();
            }
        }
    }

    @Override
    public boolean eventstate() {
        return keyListener.isKeyupdate();
    }

    @Override
    public String getTransmissionData() {
        return "-";
    }
}
