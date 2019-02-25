// GameState that shows Main Menu.

package com.thecubecast.ReEngine.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.thecubecast.ReEngine.Data.GameStateManager;
import com.thecubecast.ReEngine.Data.ParticleHandler;
import com.thecubecast.ReEngine.Data.controlerManager;
import com.thecubecast.ReEngine.Graphics.Scene2D.UIFSM;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import static com.thecubecast.ReEngine.Data.GameStateManager.AudioM;
import static com.thecubecast.ReEngine.Graphics.Draw.loadAnim;


public class MainMenuState extends GameState {

    OrthographicCamera cameraGui;
    UIFSM Menus;

    int BGMusicID;

    Animation StarAnim;

    //Particles
    public static ParticleHandler Particles;

    public MainMenuState(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {

        StarAnim = new Animation<TextureRegion>(0.9f, loadAnim(new Texture(Gdx.files.internal("Sprites/Star_anim.png")), "Sprites/Star_anim.png", 8, 1));

        //Particles
        Particles = new ParticleHandler();

        gsm.DiscordManager.setPresenceState("In Menus");

        cameraGui = new OrthographicCamera();

        Menus = new UIFSM(cameraGui, gsm);

        //BGMusicID = AudioM.playMusic("NoName.wav", true, true);
    }

    public void update() {
        Particles.Update();
        handleInput();

        cameraGui.update();
    }

    public void draw(SpriteBatch bbg, int height, int width, float Time) {

        //DEBUG CODE
        gsm.Render.debugRenderer.setProjectionMatrix(cameraGui.combined);
        gsm.Render.debugRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //gsm.Render.debugRenderer.setColor();
        //gsm.Render.debugRenderer.circle(gsm.UIWidth/2, gsm.UIHeight/2, gsm.UIHeight/4);

        gsm.Render.debugRenderer.end();

        cameraGui.setToOrtho(false, width, height);
        bbg.setProjectionMatrix(cameraGui.combined);
        bbg.begin();

        TextureRegion temp = (TextureRegion) StarAnim.getKeyFrame(Time, true);
        bbg.draw(temp, gsm.WorldWidth/2 - temp.getRegionWidth()/2, gsm.WorldHeight/2 - temp.getRegionHeight()/2);


        //Particles
        Particles.Draw(bbg);

        bbg.end();


    }

    public void drawUI(SpriteBatch g, int height, int width, float Time) {
        //Draws things on the screen, and not the world positions
        cameraGui.setToOrtho(false, width, height);
        g.setProjectionMatrix(cameraGui.combined);
        g.begin();
        //GUI must draw last
        Menus.Draw(g);
        g.end();
    }


    public void handleInput() {

    }

    public void reSize(SpriteBatch g, int H, int W) {
        //stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true);
        cameraGui.setToOrtho(false);
        //Menus.reSize(cameraGui);
    }

    @Override
    public void Shutdown() {

    }

    @Override
    public void dispose() {
        //AudioM.stopMusic(BGMusicID);
    }

}