package bensbasicgameengine.Graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Graphic {

    private JPanel panel;
    private ArrayList<ArrayList<GraphicObject>> objectlist = new ArrayList<>();
    private BufferedImage[][] background;
    private final int tilesize = 64;
    private GraphicImage cursor = null;

    //TODO Rewrite Graphics

    public Graphic() {
        panel = new JPanel() {
            @Override
            public void paint(java.awt.Graphics g) {
                synchronized (objectlist){
                    try {
                        Graphics2D g2d = ((Graphics2D) g);
                        if(background != null){
                            for (int y = 0; y < background.length; y++) {
                                for (int x = 0; x < background[0].length; x++) {
                                    if (background[y][x] != null) {
                                        g2d.drawImage(background[y][x], x * tilesize, y * tilesize, null);
                                    }
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
                }
            }
        };
    }

    public void repaint() {
        panel.getParent().repaint();
        //panel.repaint();
    }

    public void setBackground(BufferedImage[][] background) {
        this.background = background;
    }

    public void setCursor(GraphicImage cursor) {
        this.cursor = cursor;
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
}
