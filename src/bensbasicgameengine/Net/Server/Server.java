// Copyright 2019, Benedikt Strobel, All rights reserved.
package bensbasicgameengine.Net.Server;

import bensbasicgameengine.GameLogic.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class Server {

    private Logic gamelogic;
    private Acceptor acceptor;
    private Publisher publisher;
    private Object monitor = new AtomicBoolean();
    private List<Client> clients = new ArrayList<>();

    public Server(Logic gamelogic){
        this.gamelogic = gamelogic;
    }

    public void startup(){
        acceptor = new Acceptor(clients, gamelogic);
        publisher = new Publisher(clients, monitor);
        acceptor.start();
        publisher.start();
    }

    public void publish(String msg){
        publisher.publishmsg = msg;
        synchronized (monitor){
            monitor.notify();
        }
    }

}
