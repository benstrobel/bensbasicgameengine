// Copyright 2019, Benedikt Strobel, All rights reserved.
package bensbasicgameengine.Net.Server;

import bensbasicgameengine.GameLogic.Logic;

import java.util.ArrayList;
import java.util.List;


public class Server {

    private Logic gamelogic;
    private Acceptor acceptor;
    private List<Client> clients = new ArrayList<>();

    public Server(Logic gamelogic){
        this.gamelogic = gamelogic;
    }

    public void startup(){
        acceptor = new Acceptor(clients, gamelogic);
        acceptor.start();
    }

}
