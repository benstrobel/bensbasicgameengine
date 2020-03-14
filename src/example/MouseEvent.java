package example;

import bensbasicgameengine.GameLogic.Events.DeleteProjectilesEvent;
import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.GameLogic.Logic;
import bensbasicgameengine.GameLogic.Events.LogicEvent;
import bensbasicgameengine.Input.Mouse_Listener;
import bensbasicgameengine.Lib.Tools;
import bensbasicgameengine.Net.Client.Client;
import bensbasicgameengine.Physic.PhysicsObject;
import bensbasicgameengine.Physic.PhysicsRectangle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.concurrent.atomic.AtomicInteger;

public class MouseEvent extends LogicEvent {

    private Mouse_Listener mouse_listener;
    private Logic logic;
    private GameObject player;
    private Point2D camlocation;
    private AtomicInteger menustatus;
    private Client client;

    public MouseEvent(Mouse_Listener mouse_listener, Logic logic, GameObject player, Point2D camlocation, AtomicInteger menustatus, Client client){
        this.mouse_listener = mouse_listener;
        this.logic = logic;
        this.player = player;
        this.camlocation = camlocation;
        this.menustatus = menustatus;
        this.client = client;
    }

    @Override
    public void eventmethod() {
        if(menustatus.get() == 0){
            if(logic.isserver()){
                Point2D mousePos = (Point2D) mouse_listener.getPos().clone();
                mousePos.setLocation(mousePos.getX()+camlocation.getX(),mousePos.getY()+camlocation.getY());
                logic.entityClick(player,mousePos);
            }else{
                Point2D mousePos = (Point2D) mouse_listener.getPos().clone();
                mousePos.setLocation(mousePos.getX()+camlocation.getX(),mousePos.getY()+camlocation.getY());
                client.send("A C " + mousePos.getX() + " " + mousePos.getY());
                mouse_listener.resetAll();
            }
        }
    }

    @Override
    public boolean eventstate() {
        return mouse_listener.isLeftMousePressed();
    }

    @Override
    public String getTransmissionData() {
        return "-";
    }
}
