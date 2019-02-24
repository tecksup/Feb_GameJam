package com.thecubecast.ReEngine.worldObjects.AI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.thecubecast.ReEngine.Data.Cube;
import com.thecubecast.ReEngine.Data.TkMap.TkMap;
import com.thecubecast.ReEngine.worldObjects.AI.Pathfinding.FlatTiledGraph;
import com.thecubecast.ReEngine.worldObjects.AI.Pathfinding.FlatTiledNode;
import com.thecubecast.ReEngine.worldObjects.AI.Pathfinding.TiledSmoothableGraphPath;
import com.thecubecast.ReEngine.worldObjects.WorldObject;

import java.util.List;

public class Enemy extends WorldObject {

    private Vector3 Destination;
    private Smart AI;

    public Enemy(int x, int y, FlatTiledGraph map) {
        super(x,y,0, new Vector3(16,16,16));

        AI = new Smart(this, map);
    }

    @Override
    public void init(int Width, int Height) {

    }

    @Override
    public void update(float delta, List<Cube> Colls) {
        AI.setDestination(Destination);
        AI.update();
    }

    @Override
    public void draw(SpriteBatch batch, float Time) {

    }

    public void setDestination(Vector3 destination) {
        Destination = destination;
        AI.setDestination(Destination);
        AI.updatePath(true);
    }

    public void GO() {
        //if (!AI.getStateMachine().getCurrentState().equals(Student_State.WALKING_TO_DESTINATION))
            //AI.getStateMachine().changeState(Student_State.WALKING_TO_DESTINATION);

        if (AI.getPath().nodes.size > 1) {
            setPosition(AI.getPath().get(1).x * 16, AI.getPath().get(1).y * 16, 0);
            AI.updatePath(true);
        }
    }

    public TiledSmoothableGraphPath<FlatTiledNode> getPath() {
        return AI.getPath();
    }

    public Smart getAI() {
        return AI;
    }
}
