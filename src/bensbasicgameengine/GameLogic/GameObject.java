package bensbasicgameengine.GameLogic;

import bensbasicgameengine.Graphic.GraphicImage;
import bensbasicgameengine.Graphic.GraphicObject;
import bensbasicgameengine.Physic.PhysicsObject;

import java.awt.image.BufferedImage;

public class GameObject {

    private PhysicsObject physicsObject;
    private BufferedImage bufferedImage;
    private int graphiclayerid;

    //TODO Replace GraphicObject with TextureID

    public GameObject(PhysicsObject physicsObject, BufferedImage bufferedImage){
        this.physicsObject = physicsObject;
        this.bufferedImage = bufferedImage;
    }

    public void setGraphiclayerid(int graphiclayerid) {
        this.graphiclayerid = graphiclayerid;
    }

    public int getGraphiclayerid() {
        return graphiclayerid;
    }

    public PhysicsObject getPhysicsObject() {
        return physicsObject;
    }

    public GraphicObject getGraphicObject() {
        return new GraphicImage(bufferedImage, physicsObject.getPosition(), physicsObject);
    }
}
