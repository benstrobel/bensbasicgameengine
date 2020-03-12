package bensbasicgameengine.Net.Server;

import bensbasicgameengine.GameLogic.Logic;

import java.net.Socket;

public class Client {

    private int id, status = 0;
    private ClientHandler clientHandler;
    private int ingameiD = -1;

    public Client(int id, Socket socket, Logic gamelogic){
        this.id = id;
        clientHandler = new ClientHandler(socket,gamelogic);
        clientHandler.start();
    }

    public void setIngameiD(int ingameiD) {
        this.ingameiD = ingameiD;
        getClientHandler().setIngameiD(ingameiD);
    }

    public void sendMsg(String msg){
        clientHandler.sendMsg(msg);
    }

    public int getId() {
        return id;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }
}
