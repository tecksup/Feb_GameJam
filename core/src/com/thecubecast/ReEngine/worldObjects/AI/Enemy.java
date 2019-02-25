package com.thecubecast.ReEngine.worldObjects.AI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.thecubecast.ReEngine.Data.Cube;
import com.thecubecast.ReEngine.Data.GameStateManager;
import com.thecubecast.ReEngine.worldObjects.AI.Pathfinding.FlatTiledGraph;
import com.thecubecast.ReEngine.worldObjects.AI.Pathfinding.FlatTiledNode;
import com.thecubecast.ReEngine.worldObjects.AI.Pathfinding.TiledSmoothableGraphPath;
import com.thecubecast.ReEngine.worldObjects.NPC;
import com.thecubecast.ReEngine.worldObjects.WorldObject;

import java.util.List;

public class Enemy extends NPC {

    private Vector3 Destination;
    private Smart AI;

    GameStateManager gsm;

    public Enemy(int x, int y, FlatTiledGraph map, GameStateManager gsm) {
        super("",x,y,0, new Vector3(16,16,16), 1,100 );
        this.gsm = gsm;
        AI = new Smart(this, map);
    }

    @Override
    public void update(float delta, List<Cube> Colls) {
        AI.setDestination(Destination);
        AI.update();
    }

    @Override
    public void draw(SpriteBatch batch, float Time) {
        batch.draw(gsm.Render.getTexture("BlackEnemyTemp"), getPosition().x,getPosition().y);
    }

    @Override
    public void interact() {
        if (!AI.getStateMachine().getCurrentState().equals(EnemyState.WALKING_TO_DESTINATION))
            AI.getStateMachine().changeState(EnemyState.WALKING_TO_DESTINATION);

        if (AI.getPath().nodes.size > 1) {
            setPosition(AI.getPath().get(1).x * 16, AI.getPath().get(1).y * 16, 0);
            AI.updatePath(true);
        }
    }

    public void setDestination(Vector3 destination) {
        Destination = destination;
        AI.setDestination(Destination);
        AI.updatePath(true);
    }

    public TiledSmoothableGraphPath<FlatTiledNode> getPath() {
        return AI.getPath();
    }

    public Smart getAI() {
        return AI;
    }
}
