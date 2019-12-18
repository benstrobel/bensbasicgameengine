package example;

import bensbasicgameengine.GameLogic.GameObject;
import bensbasicgameengine.GameLogic.LogicEvent;
import bensbasicgameengine.Input.MouseMove_Listener;
import bensbasicgameengine.Input.Mouse_Listener;
import bensbasicgameengine.Physic.PhysicsRectangle;

public class MouseEvent extends LogicEvent {

    private Mouse_Listener mouse_listener;
    private GameObject player;

    public MouseEvent(Mouse_Listener mouse_listener, GameObject player){
        this.mouse_listener = mouse_listener;
        this.player = player;
    }

    @Override
    public void eventmethod() {
        //System.out.println(mouse_listener.getPos());
        mouse_listener.reset();
        //player.rotate(player.getOrientation()+15);
    }

    @Override
    public boolean eventstate() {
        return mouse_listener.isMousePressed();
    }
}
