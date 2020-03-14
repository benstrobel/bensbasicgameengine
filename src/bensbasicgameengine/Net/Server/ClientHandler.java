package bensbasicgameengine.Net.Server;

import bensbasicgameengine.GameLogic.Events.DeleteProjectilesEvent;
import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.GameLogic.Logic;
import bensbasicgameengine.Lib.Tools;
import bensbasicgameengine.Physic.PhysicsObject;
import bensbasicgameengine.Physic.PhysicsRectangle;
import example.Game;
import example.Weapons.Pistol;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ClientHandler extends Thread{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private Logic gamelogic;
    private int ingameiD = -1;

    private int status = 0;
    //-2 = Unknown Error
    //-1 = Setup Error
    // 0 = Init
    // 1 = Active
    // 2 = Connection Lost
    // 3 = Disconnect by User
    // 4 = Disconnect by Server

    public ClientHandler(Socket socket, Logic gamelogic){
        this.socket = socket;
        this.gamelogic = gamelogic;
    }

    @Override
    public void run(){
        if(setup()){
            System.out.println("ClientHandler set up");
            //-----------------Adding Client representation to world
            PhysicsObject clientrectangle = new PhysicsRectangle(new Point2D.Double(100,100), 1, 80, 60);
            GameObject clientobject = new GameObject(gamelogic.getNextID(),clientrectangle,Game.textures[0]);
            clientrectangle.setParent(clientobject);
            clientobject.setGraphiclayerid(0);
            clientobject.setHealth(100);
            clientobject.giveWeapon(new Pistol());
            gamelogic.addGameObject(clientobject);
            ingameiD = clientobject.getiD();
            sendMsg("F " + ingameiD);
            //-------------------------Requesting GameObjects-------
            gamelogic.sendAllGameObjects();
            //---------------------------Done-----------------------
            listen();
        }
    }

    public void setIngameiD(int ingameiD) {
        this.ingameiD = ingameiD;
    }

    private boolean setup(){
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
        }catch (IOException ex){
            status = -1;
            return false;
        }
        status = 1;
        return true;
    }

    private void listen(){
        String msg = null;
        while(status == 1){
            try{
                msg = in.readLine();
            }catch (SocketException ex){
                connectionlost();
            }catch (SocketTimeoutException ex){
                connectionlost();
            }catch (IOException ex){
                status = -2;
                ex.printStackTrace();
            }
            handleMsg(msg);
        }
    }

    public void sendMsg(String msg){
        if(status == 1){
            out.println(msg);
            //System.out.println("Sent, " + socket);
        }
    }

    private void handleMsg(String msg){
        if(msg == null){return;}
        String [] array = msg.split(" ");
        if(array[0].equals("A")){
            handleAction(msg.substring(2));
        }else{
            System.out.println(msg);
        }
        //TODO
    }

    private void handleAction(String action){
        GameObject clientobject = gamelogic.getGameObjectwithID(ingameiD);
        if(clientobject == null){return;}
        if(action.equals("W")){
            clientobject.getPhysicsObject().setVelocityY(-5);
        }else if(action.equals("S")){
            clientobject.getPhysicsObject().setVelocityY(5);
        }else if(action.equals("WS")){
            clientobject.getPhysicsObject().setVelocityY(0);
        }else if(action.equals("A")){
            clientobject.getPhysicsObject().setVelocityX(-5);
        }else if(action.equals("D")){
            clientobject.getPhysicsObject().setVelocityX(5);
        }else if(action.equals("AD")){
            clientobject.getPhysicsObject().setVelocityX(0);
        }else if(action.equals("Q")){
            clientobject.rotate(clientobject.getOrientation()-5);
        }else if(action.equals("E")){
            clientobject.rotate(clientobject.getOrientation()+5);
        }else if(action.startsWith("C")){
            String [] array = action.split(" ");
            Point2D mousePos = new Point2D.Double(Double.parseDouble(array[1]), Double.parseDouble(array[2]));
            gamelogic.entityClick(gamelogic.getGameObjectwithID(ingameiD),mousePos);
        }else if(action.startsWith("T")){
            String [] array = action.split(" ");
            gamelogic.getGameObjectwithID(ingameiD).setBufferedImage(Game.textures[Integer.parseInt(array[1])]);
        }else if(action.equals("R")){
            gamelogic.entityreload(gamelogic.getGameObjectwithID(ingameiD));
        }
    }

    private void connectionlost(){
        status = 2;
        gamelogic.deleteObjectWithId(ingameiD);
    }

    private void disconnected(){
        status = 3;
        gamelogic.deleteObjectWithId(ingameiD);
    }

}
