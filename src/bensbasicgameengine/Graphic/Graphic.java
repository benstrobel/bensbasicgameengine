// Copyright 2019, Benedikt Strobel, All rights reserved.

package bensbasicgameengine.Graphic;


import example.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Graphic {

    private JPanel panel;
    private ArrayList<ArrayList<GraphicObject>> objectlist = new ArrayList<>();
    private int[][] background;
    private final int tilesize = 64;
    private GraphicImage cursor = null;
    private JFrame frame;
    private Dimension framedimension;
    private static Point2D cameralocation = new Point2D.Double(0,0);

    public Graphic() {
        panel = new JPanel() {
            @Override
            public void paint(java.awt.Graphics g) {
                synchronized (objectlist){
                    int i = 0;
                    for(ArrayList<GraphicObject> list : objectlist){
                        i += list.size();
                    }
                    if(i == 0){
                        System.out.println("GraphicError");
                        //return;
                    }
                    try {
                        Graphics2D g2d = ((Graphics2D) g);
                        g2d.drawImage(Game.textures[40], 0, 0, null);
                        if(background != null){
                            for (int y = 0; y < background.length; y++) {
                                for (int x = 0; x < background[0].length; x++) {
                                    g2d.drawImage(Game.textures[background[y][x]+Game.floortextstart], (int)((x * tilesize) + getCameralocation().getX()), (int)((y * tilesize)+getCameralocation().getY()), null);
                                }
                            }
                        }
                        for (ArrayList<GraphicObject> list : objectlist) {
                            for (GraphicObject graphicObject : list) //ConcurrentEx
                            {
                                graphicObject.paint(g2d);
                            }
                        }
                        if (cursor != null) {
                            cursor.paint(g2d);
                        }
                    } catch (java.util.ConcurrentModificationException ex) {
                        System.out.println("#Graphics: Repainting too fast, graphics cant keep up");
                    }
                    //clearallowed = true;
                }
        }
        };
    }

    public void repaint() {
        if(frame != null){
            frame.repaint();
        }else{
            panel.getParent().repaint();
        }
    }

    public void setBackground(int[][] background) {
        this.background = background;
    }

    public void setCursor(GraphicImage cursor) {
        this.cursor = cursor;
    }

    public void setFrame(JFrame frame){
        this.frame  = frame;
        framedimension = frame.getSize();
    }

    public void addObject(int listid, GraphicObject graphicObject) {
        synchronized (objectlist) {
            if (listid < objectlist.size()) {
                objectlist.get(listid).add(graphicObject);
            }
        }
    }

    public void clear(){
        synchronized (objectlist){
            for(ArrayList<GraphicObject> list: objectlist){
                list.clear();
            }
        }
    }

    public int addList() {
        synchronized (objectlist) {
            int ret = objectlist.size();
            objectlist.add(new ArrayList<>());
            return ret;
        }
    }

    public boolean add(int listid, GraphicObject object){
        synchronized (objectlist){
            if(listid < objectlist.size() || listid == 0 && objectlist.size() == 0){
                objectlist.get(listid).add(object);
                return true;
            }
            return false;
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public ArrayList<ArrayList<GraphicObject>> getObjectlist() {
        synchronized (objectlist) {
            return objectlist;
        }
    }

    public Dimension getFramedimension() {
        return framedimension;
    }

    public void setCameralocation(Point2D cameralocation) {
        this.cameralocation = cameralocation;
    }

    public static Point2D getCameralocation() {
        return cameralocation;
    }
}
