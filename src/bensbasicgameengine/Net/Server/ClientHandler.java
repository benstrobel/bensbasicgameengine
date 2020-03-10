package bensbasicgameengine.Net.Server;

import bensbasicgameengine.GameLogic.Logic;

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
            listen();
        }
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
        if(msg.equals("update")){
            out.println(gamelogic.getTransmitData());
        } else if(msg.equals("quit")){
            disconnected();
        }else{
            System.out.println(msg);
        }
        //TODO
    }

    private void connectionlost(){
        status = 2;
    }

    private void disconnected(){
        status = 3;
    }

}
