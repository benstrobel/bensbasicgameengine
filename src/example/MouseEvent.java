package example;

import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.GameLogic.Logic;
import bensbasicgameengine.GameLogic.LogicEvent;
import bensbasicgameengine.Input.MouseMove_Listener;
import bensbasicgameengine.Input.Mouse_Listener;
import bensbasicgameengine.Physic.PhysicsObject;
import bensbasicgameengine.Physic.PhysicsRectangle;

import java.awt.geom.Point2D;

public class MouseEvent extends LogicEvent {

    private Mouse_Listener mouse_listener;
    private Logic logic;

    public MouseEvent(Mouse_Listener mouse_listener, Logic logic){
        this.mouse_listener = mouse_listener;
        this.logic = logic;
    }

    @Override
    public void eventmethod() {
        //System.out.println(mouse_listener.getPos());
        mouse_listener.reset();
        PhysicsObject targetrectangle = new PhysicsRectangle(new Point2D.Double(300,300), 1, 10, 5);
        GameObject target = new GameObject(targetrectangle,null);
        logic.addGameObject(target);
        targetrectangle.setVelocityY(20);
    }

    @Override
    public boolean eventstate() {
        return mouse_listener.isMousePressed();
    }
}
