package com.thecubecast.ReEngine.worldObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.thecubecast.ReEngine.Data.Cube;
import com.thecubecast.ReEngine.Data.DcpUtils.TextureAnimation;
import com.thecubecast.ReEngine.Data.GameStateManager;

import java.util.List;

public class HackSlashPlayer extends WorldObject{

    //True is left, False is right
    boolean Facing = true;

    TextureAnimation Walking;
    TextureAnimation Idle;

    TextureRegion Shadow;

    GameStateManager gsm;
    public HackSlashPlayer(int x, int y, GameStateManager gsm) {
        super(x,y,0, new Vector3(16,16,2));
        this.gsm = gsm;
        Walking = new TextureAnimation<TextureAtlas.AtlasRegion>(gsm.Render.getTextures("player"), 6f);
        Idle = new TextureAnimation<TextureAtlas.AtlasRegion>(gsm.Render.getTextures("player_idle"), 6f);
        Shadow = gsm.Render.getTexture("Shadow");
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
            batch.draw(Shadow, Facing ? getPosition().x : getPosition().x + 2, getPosition().y + getZFloor() / 2);
            //Idle animation
            batch.draw((TextureRegion) Idle.getFrame(Time), Facing ? getPosition().x + (((TextureRegion) Idle.getFrame(Time)).getRegionWidth()) : getPosition().x, getPosition().y + getPosition().z / 2, Facing ? -(((TextureRegion) Idle.getFrame(Time)).getRegionHeight()) : (((TextureRegion) Idle.getFrame(Time)).getRegionHeight()), (((TextureRegion) Idle.getFrame(Time)).getRegionHeight()));
        } else if(this.getVelocity().x+this.getVelocity().y >= 1) {
            batch.draw(Shadow, Facing ? getPosition().x : getPosition().x + 2, getPosition().y + getZFloor() / 2);
            //running animation
            batch.draw((TextureRegion) Walking.getFrame(Time), Facing ? getPosition().x + (((TextureRegion) Walking.getFrame(Time)).getRegionWidth()) : getPosition().x, getPosition().y + getPosition().z / 2, Facing ? -(((TextureRegion) Walking.getFrame(Time)).getRegionHeight()) : (((TextureRegion) Walking.getFrame(Time)).getRegionHeight()), (((TextureRegion) Walking.getFrame(Time)).getRegionHeight()));
        }


        if(this.getVelocity().x+this.getVelocity().y >= 1) {
            //Attack animation
        }

    }

    public void TriggerAttack() {
        System.out.println("Attacked ");
    }

    public void setFacing(boolean facing) {
        Facing = facing;
    }

    public boolean isFacing() {
        return Facing;
    }
}
