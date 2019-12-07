package bensbasicgameengine.Input;

import java.awt.event.WindowEvent;

public class WindowFocusListener implements java.awt.event.WindowFocusListener {

    private boolean lostfocus = false;

    public boolean hasLostfocus() {
        return lostfocus;
    }

    public void reset(){
        lostfocus = false;
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {

    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        lostfocus = true;
    }
}
