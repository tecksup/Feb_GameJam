package com.thecubecast.ReEngine.worldObjects;

import com.badlogic.gdx.math.Vector3;
import com.thecubecast.ReEngine.Data.Cube;
import com.thecubecast.ReEngine.worldObjects.AI.Pathfinding.FlatTiledGraph;
import com.thecubecast.ReEngine.worldObjects.AI.Pathfinding.FlatTiledNode;
import com.thecubecast.ReEngine.worldObjects.AI.Pathfinding.TiledSmoothableGraphPath;
import com.thecubecast.ReEngine.worldObjects.AI.Smart;
import com.thecubecast.ReEngine.worldObjects.AI.Student_State;

import java.util.List;

public class Student extends NPC {

    private Vector3 Destination;
    private Smart AI;

    public Student(String name, int x, int y, int z, Vector3 size, float knockbackResistance, float health, intractability interact, FlatTiledGraph worldMap) {
        super(name, x, y, z, size, knockbackResistance, health, interact);

        //AI = new Smart(this, worldMap);

    }

    @Override
    public void init(int Width, int Height) {

    }

    @Override
    public void update(float delta, List<Cube> Colls) {
        super.update(delta, Colls);
        AI.setDestination(Destination);
        AI.update();

    }


    @Override
    public void interact() {
        if (!AI.getStateMachine().getCurrentState().equals(Student_State.WALKING_TO_DESTINATION))
            AI.getStateMachine().changeState(Student_State.WALKING_TO_DESTINATION);

        if (AI.getPath().nodes.size > 1) {
            setPosition(AI.getPath().get(1).x * 16, AI.getPath().get(1).y * 16, 0);
            AI.updatePath(true);
        }

    }

    public Vector3 getDestination() {
        return Destination;
    }

    public void setDestination(Vector3 destination) {
        Destination = destination;
        AI.setDestination(Destination);
        AI.update();
    }

    public void setDestination(int destinationX, int destinationY, int destinationZ) {
        Destination = new Vector3(destinationX, destinationY, destinationZ);
        AI.setDestination(Destination);
        AI.update();
    }

    public TiledSmoothableGraphPath<FlatTiledNode> getPath() {
        return AI.getPath();
    }

    public Smart getAI() {
        return AI;
    }
}