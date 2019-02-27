package com.thecubecast.ReEngine.worldObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.thecubecast.ReEngine.Data.Cube;
import com.thecubecast.ReEngine.Data.DcpUtils.TextureAnimation;
import com.thecubecast.ReEngine.Data.GameStateManager;

import java.util.List;

public class HackSlashPlayer extends WorldObject{

    public float AttackTime;

    //True is left, False is right
    boolean Facing = true;

    TextureAnimation<TextureAtlas.AtlasRegion> Walking;
    TextureAnimation<TextureAtlas.AtlasRegion> Idle;

    TextureRegion Shadow;

    GameStateManager gsm;
    public HackSlashPlayer(int x, int y, GameStateManager gsm) {
        super(x,y,0, new Vector3(16,16,2));
        this.setState(type.Dynamic);
        this.gsm = gsm;
        Walking = new TextureAnimation<>(gsm.Render.getTextures("player"), 0.3f);
        Idle = new TextureAnimation<>(gsm.Render.getTextures("player_idle"), 0.3f);
        Shadow = gsm.Render.getTexture("Shadow");
    }

    @Override
    public void init(int Width, int Height) {

    }

    @Override
    public void update(float delta, List<Cube> Colls) {

        //Debug.println("Player", "" + delta);
        if (AttackTime - delta > 0)
            AttackTime -= delta;

        // Debug.println("Player", "" + AttackTime);

        if (getState().equals(type.Dynamic)) {

            super.setVelocityX((getVelocity().x + getVelocity().x * -1 * 0.25f));
            super.setVelocityY((getVelocity().y + getVelocity().y * -1 * 0.25f));
            super.setVelocityZ((getVelocity().z + getVelocity().z * -1 * 0.25f) - 1);

            Vector3 pos = new Vector3(getVelocity().x * delta, getVelocity().y * delta, getVelocity().z * delta);
            Vector3 newpos = new Vector3(getPosition()).add(getVelocity());
            if (pos.x < 0) { //Moving left
                if (checkCollision(new Vector3(newpos.x, getPosition().y, getPosition().z), Colls)) {
                    super.setVelocityX(0);
                } else {
                    super.setPositionX((getPosition().x - getVelocity().x * -1));
                }
            } else if (pos.x > 0) { // Moving right
                if (checkCollision(new Vector3(newpos.x, getPosition().y, getPosition().z), Colls)) {
                    super.setVelocityX(0);
                } else {
                    super.setPositionX((getPosition().x + getVelocity().x));
                }
            }

            if (pos.y < 0) { // Moving down
                if (checkCollision(new Vector3(getPosition().x, newpos.y, getPosition().z), Colls)) {
                    super.setVelocityY(0);
                } else {
                    super.setPositionY((getPosition().y - getVelocity().y * -1));
                }
            } else if (pos.y > 0) {
                if (checkCollision(new Vector3(getPosition().x, newpos.y, getPosition().z), Colls)) {
                    super.setVelocityY(0);
                } else {
                    super.setPositionY((getPosition().y + getVelocity().y));
                }
            }

            if (pos.z < 0) { // Moving Vertical
                if (checkCollision(new Vector3(getPosition().x, getPosition().y, newpos.z), Colls, true) || newpos.z <= 0) {
                    if (newpos.z <= 0) {
                        super.setPositionZ(0);
                        setZFloor(0);
                    }
                    super.setVelocityZ(0);

                } else {
                    super.setPositionZ((getPosition().z - getVelocity().z * -1));
                }
            } else if (pos.z > 0) {
                if (checkCollision(new Vector3(getPosition().x, getPosition().y, newpos.z), Colls)) {
                    super.setVelocityZ(0);
                } else {
                    super.setPositionZ((getPosition().z + getVelocity().z));
                }
            }
            //setPosition(getPosition().x + getVelocity().x, getPosition().y + getVelocity().y, getPosition().z + getVelocity().z);
        }

    }

    @Override
    public void draw(SpriteBatch batch, float Time) {

        if(Math.abs(this.getVelocity().y) >= 0.5f || Math.abs(this.getVelocity().x) >= 0.5f) {
            batch.draw(Shadow, Facing ? (int)getPosition().x : (int)getPosition().x + 2, (int)getPosition().y + (int)getZFloor() / 2);
            //running animation
            TextureRegion frame = Walking.getFrame(Gdx.graphics.getDeltaTime());
            batch.draw(frame, Facing ? (int)getPosition().x + (frame.getRegionWidth()) : (int)getPosition().x, (int)getPosition().y + (int)getPosition().z / 2, Facing ? -(frame.getRegionHeight()) : (frame.getRegionHeight()), (frame.getRegionHeight()));
        } else if(this.getVelocity().y < 0.5f || this.getVelocity().x < 0.5f) {
            batch.draw(Shadow, Facing ? (int)getPosition().x : (int)getPosition().x + 2, (int)getPosition().y + (int)getZFloor() / 2);
            //Idle animation
            TextureRegion frame = Idle.getFrame(Gdx.graphics.getDeltaTime());
            batch.draw(frame, Facing ? (int)getPosition().x + (frame.getRegionWidth()) : (int)getPosition().x, (int)getPosition().y + (int)getPosition().z / 2, Facing ? -(frame.getRegionHeight()) : (frame.getRegionHeight()), (frame.getRegionHeight()));
        }


        if(this.getVelocity().x+this.getVelocity().y >= 1) {
            //Attack animation
        }

    }

    public BoundingBox getAttackBox() {

        return getIntereactBox();
    }

    public BoundingBox getIntereactBox() {
        BoundingBox RectPla = new BoundingBox();

        if (isFacing()) {
            RectPla = new BoundingBox(new Vector3(getPosition().x + (1 * getSize().x), getPosition().y, getPosition().z), new Vector3(getPosition().x + (1 * getSize().x) + getSize().x, getPosition().y + getSize().y, getPosition().z + getSize().z));
        } else {
            RectPla = new BoundingBox(new Vector3(getPosition().x + (1 * getSize().x), getPosition().y, getPosition().z), new Vector3(getPosition().x + (1 * getSize().x) + getSize().x, getPosition().y + getSize().y, getPosition().z + getSize().z));
        }

        return RectPla;
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
