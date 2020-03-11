package bensbasicgameengine.Net.Client;

import bensbasicgameengine.GameLogic.Logic;

public class Client {

    private Logic gamelogic;
    private ConnectionHandler connectionHandler;


    public void startup(Logic gamelogic, String ip){
        this.gamelogic = gamelogic;
        System.out.println("Trying to connect");
        connectionHandler = new ConnectionHandler(ip, 56850, gamelogic);
        connectionHandler.start();
        System.out.println("Started");
   }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }
}
