package Graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Graphic {

    private JPanel panel;
    private ArrayList<ArrayList<GraphicObject>> objectlist = new ArrayList<>();
    private BufferedImage[][] background;
    private final int tilesize = 64;
    private GraphicImage cursor = null;

    public Graphic() {
        panel = new JPanel() {
            @Override
            public void paint(java.awt.Graphics g) {
                try {
                    Graphics2D g2d = ((Graphics2D) g);
                    for (int y = 0; background != null && y < background.length; y++) {
                        for (int x = 0; x < background[0].length; x++) {
                            if (background[y][x] != null) {
                                g2d.drawImage(background[y][x], x * tilesize, y * tilesize, null);
                            }
                        }
                    }
                    synchronized (objectlist) {
                        for (ArrayList<GraphicObject> list : objectlist) {
                            for (GraphicObject graphicObject : list) //ConcurrentEx
                            {
                                graphicObject.paint(g2d);
                            }
                        }
                    }
                    if (cursor != null) {
                        cursor.paint(g2d);
                    }
                } catch (java.util.ConcurrentModificationException ex) {
                    System.out.println("#Graphics: Repainting too fast, graphics cant keep up");
                }
            }
        };
    }

    public void repaint() {
        panel.repaint();
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

    public int addList() {
        synchronized (objectlist) {
            int ret = objectlist.size();
            objectlist.add(new ArrayList<>());
            return ret;
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
