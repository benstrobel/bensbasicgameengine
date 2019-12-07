// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Sound;

public enum Sound {
    AUA("aua"), SWORD("swordslash"), ITEM("item"), MUSIC("music"), DIED("youdied");

    private final String soundFileName;

    private Sound(String soundFileName){this.soundFileName = soundFileName;};

    public String getSoundFileName(){return this.soundFileName;};
}


