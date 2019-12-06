package GameLogic;

import Graphic.GraphicObject;
import Physic.PhysicsObject;

public class GameObject {

    private PhysicsObject physicsObject;
    private GraphicObject graphicObject;
    private int graphiclayerid;

    public GameObject(PhysicsObject physicsObject, GraphicObject graphicObject){
        this.physicsObject = physicsObject;
        this.graphicObject = graphicObject;
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
        return graphicObject;
    }
}
