package example;

import bensbasicgameengine.GameLogic.LogicEvent;
import bensbasicgameengine.Input.MouseMove_Listener;
import bensbasicgameengine.Input.Mouse_Listener;

public class MouseEvent extends LogicEvent {

    private Mouse_Listener mouse_listener;

    public MouseEvent(Mouse_Listener mouse_listener){
        this.mouse_listener = mouse_listener;
    }

    @Override
    public void eventmethod() {
        System.out.println(mouse_listener.getPos());
        mouse_listener.reset();
    }

    @Override
    public boolean eventstate() {
        return mouse_listener.isMousePressed();
    }
}
