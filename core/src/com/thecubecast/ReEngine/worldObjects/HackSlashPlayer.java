package com.thecubecast.ReEngine.worldObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.thecubecast.ReEngine.Data.Cube;
import com.thecubecast.ReEngine.Data.GameStateManager;

import java.util.List;

public class HackSlashPlayer extends WorldObject{

    public enum Direction {
        Up,UpRight,Right,DownRight,Down,DownLeft,Left,UpLeft
    }

    GameStateManager gsm;
    public HackSlashPlayer(int x, int y, GameStateManager gsm) {
        super(x,y,0, new Vector3(16,16,2));
        this.gsm = gsm;
    }

    @Override
    public void init(int Width, int Height) {

    }

    @Override
    public void update(float delta, List<Cube> Colls) {

    }

    @Override
    public void draw(SpriteBatch batch, float Time) {

        if(this.getVelocity().x+this.getVelocity().y < 1) {
            //Idle animation
            batch.draw(gsm.Render.getTextureRegion("Dude"), getPosition().x, getPosition().y);
        } else if(this.getVelocity().x+this.getVelocity().y >= 1) {
            //running animation
            batch.draw(gsm.Render.getTextureRegion("Dude"), getPosition().x, getPosition().y);
        }

        if(this.getVelocity().x+this.getVelocity().y >= 1) {
            //Attack animation
        }

    }

    public void TriggerAttack(Direction direct) {
        System.out.println("Attacked " + direct.name());
    }
}
