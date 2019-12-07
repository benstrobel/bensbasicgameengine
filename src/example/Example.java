// Copyright 2019, Benedikt Strobel, All rights reserved.
package example;

import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.GameLogic.Logic;
import bensbasicgameengine.GameLogic.LogicEvent;
import bensbasicgameengine.Graphic.Graphic;
import bensbasicgameengine.Graphic.GraphicImage;
import bensbasicgameengine.Input.KeyListener;
import bensbasicgameengine.Input.MouseMove_Listener;
import bensbasicgameengine.Input.Mouse_Listener;
import bensbasicgameengine.Physic.Physics;
import bensbasicgameengine.Physic.PhysicsObject;
import bensbasicgameengine.Physic.PhysicsRectangle;
import bensbasicgameengine.Sound.SoundManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Example {

    Graphic graphic = new Graphic();
    Physics physics = new Physics();
    //SoundManager soundManager = new SoundManager();
    KeyListener keyListener = new KeyListener();
    Mouse_Listener mouse_listener = new Mouse_Listener();
    MouseMove_Listener mouseMove_listener = new MouseMove_Listener();
    Logic logic = new Logic(graphic,physics,null,keyListener,mouse_listener,mouseMove_listener);

    String texturepaths [] = {"dude.png"};
    BufferedImage textures [];
    GameObject player;

    public static void main(String[] args) {
        new Example();
    }

    public Example(){
        setupGraphics();
        setupPlayer();
        setupEvents();
        setupWindow();
        logic.startloop();
    }

    private void setupWindow(){
        JFrame frame = new JFrame("Bens Basic Game Engine - Example");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.add(graphic.getPanel());
        frame.setVisible(true);
        frame.addKeyListener(keyListener);
        frame.setResizable(false);
    }

    private void setupEvents(){
        LogicEvent keyEvent = new KeyEvent(keyListener,player);
        logic.registerLogicEvent(keyEvent);
    }

    private void setupPlayer(){
        PhysicsObject playerrectangle = new PhysicsRectangle(new Point2D.Double(100,100), null, 1, 90, 70);
        player = new GameObject(playerrectangle,textures[0]);
        player.setGraphiclayerid(0);
        logic.addGameObject(player);
    }

    private void setupGraphics(){
        textures = new BufferedImage[texturepaths.length];
        URL toload;
        for(int i = 0; i < texturepaths.length; i++){
            toload = this.getClass().getResource(texturepaths[i]);
            if(toload != null){
                try {
                    textures [i] = ImageIO.read(toload);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logic.addGraphicLayer();
    }
}
