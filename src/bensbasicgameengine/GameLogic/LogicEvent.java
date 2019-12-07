package bensbasicgameengine.GameLogic;

public abstract class LogicEvent {

    private boolean removeFlag = false;

    public boolean isRemoveFlag(){
        return removeFlag;
    }

    public abstract void eventmethod();

    public abstract boolean eventstate();

}
