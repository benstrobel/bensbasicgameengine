package bensbasicgameengine.Net.Client;

import bensbasicgameengine.GameLogic.Logic;

public class Client {

    private Logic gamelogic;
    private ConnectionHandler connectionHandler;


    public void startup(Logic gamelogic){
        this.gamelogic = gamelogic;
        System.out.println("Trying to connect");
        connectionHandler = new ConnectionHandler("127.0.0.1", 56850, gamelogic);
        connectionHandler.start();
        System.out.println("Started");
   }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }
}
