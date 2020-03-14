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
import example.Weapons.Pistol;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
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

    private int [][] background = {{25,19,14,19,19,19,19,14,14,19,19,19,19,19,14,14,19,19,19,19,19,14,14,14,19,19,19,19,19,26},
    {22,11,6,16,6,11,11,11,6,11,6,6,6,6,16,11,16,11,6,16,11,6,16,16,16,6,11,11,16,2},
    {23,16,16,16,11,11,16,11,11,6,6,11,6,6,16,6,11,16,11,6,6,6,16,11,11,6,6,11,16,2},
    {23,16,16,6,16,11,16,11,11,11,6,6,16,6,6,6,16,16,16,6,11,11,11,16,6,6,6,11,6,3},
    {23,16,16,6,11,11,6,11,16,11,11,11,16,6,11,16,11,6,16,16,16,6,11,11,6,16,6,6,11,3},
    {22,6,6,6,6,6,6,11,16,11,11,16,16,6,6,11,16,6,6,11,16,16,6,6,11,6,16,16,6,2},
    {22,16,6,6,11,6,6,11,11,16,16,6,16,6,16,16,6,11,6,16,16,6,16,11,16,16,11,16,6,1},
    {22,16,6,6,16,6,11,11,11,11,16,16,16,16,16,16,16,11,11,6,11,16,6,6,16,16,16,11,16,3},
    {21,6,16,16,11,16,16,16,16,11,11,16,6,16,16,6,16,11,11,6,11,16,6,11,11,16,16,11,11,2},
    {22,11,11,11,16,11,11,6,16,16,16,11,6,16,11,16,11,11,16,6,11,6,11,6,11,11,11,16,16,2},
    {22,16,6,16,16,11,6,16,16,6,16,11,16,6,16,16,16,16,6,11,6,11,11,11,11,16,16,16,16,2},
    {23,16,16,6,11,16,6,6,16,11,6,16,16,11,11,16,16,6,11,6,11,6,11,11,6,16,16,16,6,1},
    {21,6,16,16,11,11,6,11,16,11,16,16,16,6,16,6,11,11,11,11,6,16,16,11,16,16,6,11,16,1},
    {22,11,16,6,6,6,6,6,16,11,6,16,11,16,16,16,16,16,6,11,16,11,6,16,16,11,16,16,6,1},
    {23,16,6,6,6,16,6,6,16,6,6,16,11,6,6,6,6,16,6,6,6,6,16,16,11,6,11,16,11,2},
    {23,11,6,11,16,11,6,11,6,11,16,16,6,16,16,6,6,16,6,11,11,11,11,11,16,6,16,11,16,3},
    {21,6,11,16,6,6,11,11,11,16,16,11,6,6,16,6,11,11,16,16,11,16,11,16,16,11,6,11,16,3},
    {21,6,6,16,6,16,11,6,11,16,11,6,6,11,16,11,16,6,16,11,16,16,11,16,6,6,16,16,16,3},
    {23,6,6,16,16,16,11,16,11,16,16,16,6,16,6,6,6,16,11,11,16,16,16,6,11,16,16,6,16,1},
    {21,16,6,11,6,6,16,6,16,11,11,16,6,6,16,6,6,6,6,11,11,11,11,11,11,6,6,6,11,3},
    {23,6,6,11,16,11,6,6,11,11,11,6,11,11,11,16,16,11,11,11,16,16,16,6,11,16,6,11,16,3},
    {21,6,6,11,16,6,6,6,11,16,6,6,6,16,16,6,16,16,16,11,11,6,6,6,16,16,6,6,6,1},
    {23,11,6,11,16,16,16,11,16,11,11,16,11,16,11,16,16,11,6,16,16,11,16,6,6,6,6,6,6,2},
    {21,6,11,6,11,6,6,11,16,6,11,11,6,6,16,16,11,6,11,6,11,16,11,6,16,11,16,6,11,2},
    {23,16,11,6,11,6,6,16,11,11,16,16,11,6,6,11,6,11,11,11,11,6,16,11,6,16,6,6,16,1},
    {21,11,16,6,11,11,16,6,16,6,11,6,6,11,16,16,16,6,6,11,6,11,11,11,16,11,16,6,16,1},
    {23,16,11,16,16,6,16,16,11,6,16,16,6,11,16,11,16,16,11,11,6,6,11,11,6,11,11,11,11,3},
    {21,16,6,6,16,6,16,6,6,16,11,6,11,11,6,11,11,6,6,6,16,16,6,6,11,6,16,16,6,1},
    {22,11,11,11,6,6,16,16,16,16,16,11,11,11,16,16,16,16,11,16,11,11,11,6,16,16,16,11,11,1},
    {27,5,5,5,10,5,10,10,5,10,5,5,5,10,5,5,5,5,5,5,10,5,5,5,10,5,5,5,5,28}};

    private String texturepaths [] = {"dude.png","dudealex.png","dudearne.png","dudeben.png","dudecheesn.png", "dudehinze.png", "dudekai.png", "dudemanu.png", "dudemarius.png", "dudetim.png","dudetorsten.png","0.png","1.png","2.png","3.png","4.png","5.png","6.png","7.png","8.png","9.png","10.png",
            "11.png","12.png","13.png","14.png","15.png","16.png","17.png","18.png","19.png","20.png",
            "21.png","22.png","23.png","24.png","25.png","26.png","27.png","28.png","bg.png"};
    public static final int floortextstart = 11;
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

    public static void printBackground(int array[][]){
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[0].length; j++){
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void backgroundtoString(int array[][]){
        for(int i = 0; i < array.length; i++){
            System.out.print("{");
            for(int j = 0; j < array[0].length; j++){
                if(j == 29){
                    System.out.print(array[i][j]);
                }else{
                    System.out.print(array[i][j] + ",");
                }
            }
            if(i == 29){
                System.out.println("}");
            }else{
                System.out.println("},");
            }
        }
    }

    public static int [] [] generateBackground(){
        Random random = new Random();
        int [] [] background = new int [30][30];
        //Setting corners
        background [0][0] = 0;
        background [29][29] = 24;
        background[0][29] = 20;
        background[29][0] = 4;
        setupRow(1,29,19,14,19,background,0,random);
        setupRow(1,29,5,10,5,background,29,random);
        setupColum(1,29,3,2,1,background,29,random);
        setupColum(1,29,21,22,23,background,0,random);
        for(int i = 1; i < 29; i++){
            setupRow(1,29,6,11,16,background,i,random);
        }
        return background;
    }

    private static void setupRow(int from, int to, int option1, int option2, int option3, int [][] array, int y, Random random){
        for(int i = from; i < to; i++){
            int j = random.nextInt(3);
            switch (j){
                case 0:{array[y][i] = option1; break;}
                case 1:{array[y][i] = option2;break;}
                case 2:{array[y][i] = option3;break;}
            }
        }
    }

    private static void setupColum(int from, int to, int option1, int option2, int option3, int [][] array, int x, Random random){
        for(int i = from; i < to; i++){
            int j = random.nextInt(3);
            switch (j){
                case 0:{array[i][x] = option1; break;}
                case 1:{array[i][x] = option2;break;}
                case 2:{array[i][x] = option3;break;}
            }
        }
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
        frame.setSize(1000,1000);
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
        player.setHealth(100);
        player.giveWeapon(new Pistol());
        logic.addGameObject(player);
        logic.addWall(-30,0,1950,30);
        logic.addWall(0,1920, 30, 1950);
        logic.addWall(-30,-30, 30, 1980);
        logic.addWall(1920,0,1950,30);
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
        logic.addDeadZone(-100,-100,50,2100);
        logic.addDeadZone(-100,-100,2100,50);
        logic.addDeadZone(-100,1980,50,2100);
        logic.addDeadZone(1980,-100, 2100, 50);
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
        graphic.setBackground(background);
    }
}
