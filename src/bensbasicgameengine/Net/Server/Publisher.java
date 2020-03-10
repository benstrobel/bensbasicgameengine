package bensbasicgameengine.Net.Server;

import java.util.List;

public class Publisher extends Thread{

    private List<Client> clients;
    private Object monitor;
    public String publishmsg = "";

    public Publisher(List<Client> clients, Object monitor){
        this.clients = clients;
        this.monitor = monitor;
    }

    public void publish(String msg){
        for(Client c : clients){
            c.sendMsg(msg);
        }
    }

    @Override
    public void run(){
        while(true){
            publish(publishmsg);
            try {
                synchronized (monitor){
                    monitor.wait();
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
    }

}
