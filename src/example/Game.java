// Copyright 2019, Benedikt Strobel, All rights reserved.
package example;

import bensbasicgameengine.GameLogic.Events.HudClickEvent;
import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.GameLogic.HudObject;
import bensbasicgameengine.GameLogic.Logic;
import bensbasicgameengine.GameLogic.Events.LogicEvent;
import bensbasicgameengine.Graphic.Graphic;
import bensbasicgameengine.Graphic.GraphicShape;
import bensbasicgameengine.Input.*;
import bensbasicgameengine.Net.Client.Client;
import bensbasicgameengine.Net.Server.Server;
import bensbasicgameengine.Physic.Physics;
import bensbasicgameengine.Physic.PhysicsObject;
import bensbasicgameengine.Physic.PhysicsRectangle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

    AtomicInteger menustatus = new AtomicInteger(0);

    private Graphic graphic = new Graphic();
    private Physics physics = new Physics();
    //private SoundManager soundManager = new SoundManager();
    private KeyListener keyListener = new KeyListener();
    private Mouse_Listener mouse_listener = new Mouse_Listener();
    private MouseMove_Listener mouseMove_listener = new MouseMove_Listener();
    private WindowFocusListener windowFocusListener = new WindowFocusListener();
    private Client client = new Client();
    private Logic logic = new Logic(graphic,physics,null,keyListener,mouse_listener,mouseMove_listener,client,menustatus);
    private boolean isserver = true;
    private Server server = null;

    private String texturepaths [] = {"dude.png","dudealex.png","dudearne.png","dudeben.png","dudecheesn.png", "dudehinze.png", "dudekai.png", "dudemanu.png", "dudemarius.png", "dudetim.png","dudetorsten.png"};
    private final int floortextstart = 11;
    public static BufferedImage textures [];
    private GameObject player;
    private StringContainer inputstring = new StringContainer();
    HudObject enterip;
    KeyEvent keyEvent;


    public static void main(String[] args) {
        new Game();
    }

    public Game(){
        setupGraphics();
        setupWindow();
        setupPlayer();
        setupInGameHUD();
        setupDeadZones();
        setupEvents();
        setupMainMenu();
        setupInGame();
    }

    private void setupMainMenu(){
        logic.menustatus.set(2);
        HudObject host = new HudObject(300,200,300,100, new GraphicShape(new Rectangle2D.Double(300,200,300,100), Color.black, false, 0, true), "Host", 2) {
            @Override
            public void activationMethod() {
                System.out.println("Hud Object Clicked (Host)");
                isserver = true;
                menustatus.set(0);
                logic.updateHUDObjects();
                logic.mainmenu = false;
            }
        };
        LogicEvent hosthudclick = new HudClickEvent(host,mouse_listener);
        logic.registerLogicEvent(hosthudclick);
        logic.addHudObject(host);

        HudObject join = new HudObject(300,400,300,100, new GraphicShape(new Rectangle2D.Double(300,400,300,100), Color.black, false, 0, true), "Join", 2) {
            @Override
            public void activationMethod() {
                System.out.println("Hud Object Clicked (Join)");
                isserver = false;
                menustatus.set(3);
                logic.updateHUDObjects();
            }
        };
        LogicEvent joinhudclick = new HudClickEvent(join,mouse_listener);
        logic.registerLogicEvent(joinhudclick);
        logic.addHudObject(join);

        HudObject charselectleft = new HudObject(80,200, 50, 50, new GraphicShape(new Rectangle2D.Double(80,200,50,50), Color.black, false, 0, true), "<-", 2) {
            @Override
            public void activationMethod() {
                if(logic.clienttextureid > 0){
                    logic.clienttextureid--;
                }else{
                    logic.clienttextureid = floortextstart-1;
                }
                player.setBufferedImage(textures[logic.clienttextureid]);
            }
        };
        LogicEvent charleftclick = new HudClickEvent(charselectleft, mouse_listener);
        logic.registerLogicEvent(charleftclick);
        logic.addHudObject(charselectleft);

        HudObject charselectright = new HudObject(130,200,50,50, new GraphicShape(new Rectangle2D.Double(130,200,50,50), Color.black, false, 0, true), "->", 2) {
            @Override
            public void activationMethod() {
                if(logic.clienttextureid < floortextstart-1){
                    logic.clienttextureid++;
                }else{
                    logic.clienttextureid = 0;
                }
                player.setBufferedImage(textures[logic.clienttextureid]);
            }
        };
        LogicEvent charrightclick = new HudClickEvent(charselectright,mouse_listener);
        logic.registerLogicEvent(charrightclick);
        logic.addHudObject(charselectright);
        //--------------------------------------------------------- Join Menu (3)-----------------------------------------------
        enterip = new HudObject(300,200,300,20, new GraphicShape(new Rectangle2D.Double(300,200,300,20), Color.black, false, 0, true), "Enter IP: " + inputstring.getString(), 3) {
            @Override
            public void activationMethod() {
            }
        };
        logic.addHudObject(enterip);
        keyEvent.setEnterip(enterip);

        HudObject okay = new HudObject(550,250,50,30, new GraphicShape(new Rectangle2D.Double(550,250,50,30), Color.black, false, 0, true), "Okay", 3) {
            @Override
            public void activationMethod() {
                System.out.println("Hud Object Clicked (Okay)");
                logic.setIp(inputstring);
                menustatus.set(0);
                logic.updateHUDObjects();
                logic.mainmenu = false;
            }
        };
        LogicEvent okayclick = new HudClickEvent(okay,mouse_listener);
        logic.registerLogicEvent(okayclick);
        logic.addHudObject(okay);

        HudObject localhost = new HudObject(350,250,150,30, new GraphicShape(new Rectangle2D.Double(350,250,150,30), Color.black, false, 0, true), "Localhost", 3) {
            @Override
            public void activationMethod() {
                System.out.println("Hud Object Clicked (Localhost)");
                menustatus.set(0);
                logic.updateHUDObjects();
                inputstring.setString("127.0.0.1");
                logic.setIp(inputstring);
                logic.mainmenu = false;
            }
        };
        LogicEvent localhostclick = new HudClickEvent(localhost,mouse_listener);
        logic.registerLogicEvent(localhostclick);
        logic.addHudObject(localhost);

        logic.updateHUDObjects();
        logic.mainmenuloop();
    }

    private void setupInGame(){
        //logic.setShowhitbox(true);
        logic.forcecamfollow(player.getPhysicsObject());
        if(isserver){
            server = new Server(logic);
            server.startup();
            logic.setServer(server);
        }else{
            client.startup(logic, inputstring.getString());
        }
        logic.startloop(isserver);
    }

    private void setupWindow(){
        JFrame frame = new JFrame("Bens Basic Game Engine - Bambule in Bens Butze");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.add(graphic.getPanel());
        frame.addKeyListener(keyListener);
        frame.setResizable(false);
        frame.addWindowFocusListener(windowFocusListener);
        //frame.setUndecorated(true);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        graphic.setFrame(frame);
    }

    private void setupEvents(){
        keyEvent = new KeyEvent(keyListener,player,graphic,menustatus,logic,inputstring,enterip,client);
        logic.registerLogicEvent(keyEvent);
        LogicEvent mouseEvent = new MouseEvent(mouse_listener,logic,player,logic.getCamlocation(),menustatus, client);
        logic.registerLogicEvent(mouseEvent);
        LogicEvent windowFocusEvent = new WindowFocusEvent(windowFocusListener,keyListener);
        logic.registerLogicEvent(windowFocusEvent);
    }

    private void setupPlayer(){
        PhysicsObject playerrectangle = new PhysicsRectangle(new Point2D.Double(100,100), 1, 80, 60);
        player = new GameObject(logic.getNextID(),playerrectangle,textures[logic.clienttextureid]);
        playerrectangle.setParent(player);
        player.setGraphiclayerid(0);
        logic.addGameObject(player);
        PhysicsObject targetrectangle = new PhysicsRectangle(new Point2D.Double(300,300), 1, 50, 50);
        GameObject target = new GameObject(logic.getNextID(),targetrectangle,null);
        targetrectangle.setParent(target);
        logic.addGameObject(target);
        logic.addWall(0,0,1000,30);
        logic.addWall(0,970, 30, 1000);
        logic.addWall(0,0,30,1000);
        logic.addWall(970,0,1000,30);
    }

    private void setupInGameHUD(){
        HudObject testmenu = new HudObject(300,300,300,100, new GraphicShape(new Rectangle2D.Double(300,300,300,100), Color.black, false, 0, true), "Menu Test", 1) {
            @Override
            public void activationMethod() {
                System.out.println("Hud Object Clicked");
            }
        };
        LogicEvent hudclick0 = new HudClickEvent(testmenu,mouse_listener);
        logic.registerLogicEvent(hudclick0);
        logic.addHudObject(testmenu);
    }

    private void setupDeadZones(){
        logic.addDeadZone(-100,-100,50,1200);
        logic.addDeadZone(-100,-100,1200,50);
        logic.addDeadZone(-100,1050,50,1200);
        logic.addDeadZone(1050,-100, 1200, 50);
    }

    private void setupGraphics(){
        textures = new BufferedImage[texturepaths.length];
        URL toload;
        for(int i = 0; i < texturepaths.length; i++){
            toload = this.getClass().getResource("/" + texturepaths[i]);
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
