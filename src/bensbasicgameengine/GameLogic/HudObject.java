// Copyright 2019, Benedikt Strobel, All rights reserved.
package bensbasicgameengine.GameLogic;

import bensbasicgameengine.Graphic.GraphicObject;

public abstract class HudObject {

    private int x,y,width,height;
    private boolean enabled = false;
    private GraphicObject graphicObject;
    private String text;

    public HudObject(int x, int y, int width, int height, GraphicObject graphicObject, String text){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.graphicObject = graphicObject;
        this.text = text;
    }

    public abstract void activationMethod();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public GraphicObject getGraphicObject() {
        return graphicObject;
    }
}
