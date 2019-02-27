package com.thecubecast.ReEngine.worldObjects.AI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.thecubecast.ReEngine.Data.Cube;
import com.thecubecast.ReEngine.Data.GameStateManager;
import com.thecubecast.ReEngine.worldObjects.AI.Pathfinding.FlatTiledGraph;
import com.thecubecast.ReEngine.worldObjects.AI.Pathfinding.FlatTiledNode;
import com.thecubecast.ReEngine.worldObjects.AI.Pathfinding.TiledSmoothableGraphPath;
import com.thecubecast.ReEngine.worldObjects.HackSlashPlayer;
import com.thecubecast.ReEngine.worldObjects.NPC;

import java.util.List;

public class Enemy extends NPC {

    private Vector3 Destination;
    private Smart AI;
    HackSlashPlayer player;

    public GameStateManager gsm;

    public Enemy(String name, int x, int y, int z, Vector3 size, float knockbackResistance, float health, intractability interact, boolean invincible, FlatTiledGraph map, GameStateManager gsm) {
        super(name,x,y,z, size, knockbackResistance,health, interact, invincible);
        this.gsm = gsm;
        AI = new Smart(this, map);
    }

    public void update(float delta, List<Cube> Colls, HackSlashPlayer player) {
        this.player = player;
        super.update(delta, Colls);
        AI.setDestination(player.getPosition());
        AI.update();
        System.out.println(AI.getStateMachine().getCurrentState().name());
    }

    @Override
    public void draw(SpriteBatch batch, float Time) {
        batch.draw(gsm.Render.getTexture("BlackEnemyTemp"), getPosition().x,getPosition().y);
    }

    @Override
    public void interact() {
        if (!AI.getStateMachine().getCurrentState().equals(EnemyState.HUNTING))
            AI.getStateMachine().changeState(EnemyState.HUNTING);

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
