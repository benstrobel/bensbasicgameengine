package bensbasicgameengine.Graphic;

import bensbasicgameengine.Physic.PhysicsObject;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class GraphicImage extends GraphicObject {

    private BufferedImage image;
    private int x,y;
    private int xoffset, yoffset;
    boolean centered = false;
    boolean subimage = false;
    public GraphicImage(BufferedImage image, Point2D position)
    {
        this.image = image;
        xoffset = image.getWidth()/2;
        yoffset = image.getHeight()/2;
        this.x = (int)position.getX();
        this.y = (int)position.getY();
    }

    public GraphicImage(BufferedImage image, Point2D position, boolean centered)
    {
        this.image = image;
        xoffset = image.getWidth()/2;
        yoffset = image.getHeight()/2;
        this.x = (int)position.getX();
        this.y = (int)position.getY();
        this.centered = centered;
    }

    public GraphicImage(BufferedImage image, Point2D position, PhysicsObject physicsObject)
    {
        this.image = image;
        xoffset = differance(image.getWidth(),(int)(physicsObject.getShape().getBounds2D().getWidth()))/2;
        yoffset = differance(image.getHeight(),(int)(physicsObject.getShape().getBounds2D().getHeight()))/2;
        this.x = (int)position.getX();
        this.y = (int)position.getY();
        this.centered = true;
    }

    public GraphicImage(BufferedImage image, Point2D position, float alpha)
    {
        this.image = image;
        xoffset = image.getWidth()/2;
        yoffset = image.getHeight()/2;
        this.x = (int)position.getX();
        this.y = (int)position.getY();
        this.alpha = alpha;
    }

    /* TODO Replace PhysicObject with required arguments to ensure low coupling
    public GraphicImage(BufferedImage image, Point2D position, PhysicsObject physicsObject, boolean widghtoffset)
    {


        final int distancefromplayercentertosword = 145;
        this.image = image;
        xoffset = differance(image.getWidth(),(int)(physicsObject.getShape().getBounds2D().getWidth()))/2;
        yoffset = differance(image.getHeight(),(int)(physicsObject.getShape().getBounds2D().getHeight()))/2;
        double degree = physicsObject.getOrientation();
        if(degree < 25){

        }else if(degree < 45){
            xoffset += 10;
            yoffset -= 20;
        }
        else if(degree < 70){
            xoffset += 15;
            yoffset -= 25;
        }
        this.x = (int)physicsObject.getShape().getBounds2D().getX();
        this.y = (int)physicsObject.getShape().getBounds2D().getY();
        this.centered = true;
    }*/

    public void setSubimage(boolean subimage) {
        this.subimage = subimage;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int differance(int a, int b){
        if(a > b){
            return Math.abs(a-b);
        }else{
            return Math.abs(b-a);
        }
    }

    @Override
    public void paint(Graphics2D g2d) {
        if(getAlpha() != 1.0F){
            g2d.setComposite(AlphaComposite.SrcOver.derive(getAlpha()));
        }
        if(centered){
            if(subimage){
                g2d.drawImage(image.getSubimage(0,0,image.getWidth(),image.getHeight()), x-xoffset, y-yoffset, null);
            }else{
                g2d.drawImage(image, x-xoffset, y-yoffset, null);
            }
        }else{
            if(subimage){
                g2d.drawImage(image.getSubimage(0,0,image.getWidth(),image.getHeight()), x, y,null);
            }else{
                g2d.drawImage(image, x, y,null);
            }
        }
        g2d.setComposite(AlphaComposite.SrcOver.derive(1.0F));
    }
}