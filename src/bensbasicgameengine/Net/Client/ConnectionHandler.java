package bensbasicgameengine.Net.Client;

import bensbasicgameengine.GameLogic.Logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ConnectionHandler extends Thread{

    private Socket socket;
    private String ip;
    private int port;
    private BufferedReader in;
    private PrintWriter out;
    private Logic gamelogic;

    private String data;

    public ConnectionHandler(String ip, int port, Logic gamelogic){
        this.ip = ip;
        this.port = port;
        this.gamelogic = gamelogic;
    }

    @Override
    public void run(){
        System.out.println("Connecting");
        if(setup()){
            System.out.println("Connected");
            while(true){
                try {
                    data = in.readLine();
                } catch(SocketTimeoutException ex){
                    data = "-";
                }
                catch (IOException e) {
                    //e.printStackTrace();
                }
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Not Connected");
    }

    public String getData() {
        return data;
    }

    private boolean setup(){
        try{
            socket = new Socket(ip,port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            socket.setSoTimeout(20);
        }catch(IOException ex){
            return false;
        }
        return true;
    }
}
