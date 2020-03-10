package bensbasicgameengine.Net.Server;

import bensbasicgameengine.GameLogic.Logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;

public class Acceptor extends Thread {

    private final int port = 56850;
    private ServerSocket serverSocket;
    private List <Client> clients;
    private int status = 0;
    private boolean run = true;
    int id = 0;

    private Logic gamelogic;

    public Acceptor(List <Client> clients, Logic gamelogic){
        this.clients = clients;
        this.gamelogic = gamelogic;
    }

    @Override
    public void run(){
        if(setup()){
            while(run){
                Client c = accept();
                if(c != null){
                    clients.add(c);
                }
            }
        }
    }

    private Client accept(){
        Socket clientsocket = null;
        try{
            clientsocket = serverSocket.accept();
            //clientsocket.setSoTimeout(2000);
        }catch(SocketTimeoutException ex){
            return null;
        }catch (IOException ex){
            return null;
        }
        System.out.println("Client connected");
        return new Client(id++, clientsocket, gamelogic);
    }

    private boolean setup(){
        try{
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(2000);
            Runtime.getRuntime().addShutdownHook(new Thread(){
                public void run(){
                    try{
                        serverSocket.close();
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
            });
        }catch(IOException ex){
            ex.printStackTrace();
            status = -1;
            return false;
        }
        status = 1;
        return true;
    }

}
