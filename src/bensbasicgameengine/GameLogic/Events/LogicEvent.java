// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.GameLogic.Events;

public abstract class LogicEvent {

    private boolean removeFlag = false;

    public boolean isRemoveFlag(){
        return removeFlag;
    }

    public abstract String getTransmissionData();

    public abstract void eventmethod();

    public abstract boolean eventstate();

}
