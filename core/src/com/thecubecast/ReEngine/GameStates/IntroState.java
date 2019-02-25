// GameState that shows logo.

package com.thecubecast.ReEngine.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thecubecast.ReEngine.Data.GameStateManager;
import com.thecubecast.ReEngine.Data.controlerManager;
import sun.security.ssl.Debug;

public class IntroState extends GameState {

    OrthographicCamera camera;

    private int alpha;
    private int ticks = 0;

    static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;

    private final int FADE_IN = 20;
    private final int LENGTH = 40;
    private final int FADE_OUT = 20;

    public IntroState(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {

        camera = new OrthographicCamera();

        //JukeBox.load("/Music/bgmusic.wav", "LogoSound");
        //JukeBox.play("LogoSound");


    }

    public void update() {
        handleInput();
        ticks++;
        if (ticks < FADE_IN) {
            alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
            if (alpha < 0) alpha = 0;
        }
        if (ticks > FADE_IN + LENGTH) {
            alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
            if (alpha > 255) alpha = 255;
        }
        if (ticks > FADE_IN + LENGTH + FADE_OUT) {
            //JukeBox.stop("LogoSound");
            if (Gdx.input.isKeyPressed(Keys.D))
                GameStateManager.Debug = true;
            if (GameStateManager.ctm.isButtonDown(0, controlerManager.buttons.BUTTON_START) && GameStateManager.ctm.isButtonDown(0, controlerManager.buttons.BUTTON_BACK))
                GameStateManager.Debug = true;
            if (GameStateManager.Debug)
                Debug.println("Developer", "Debug set to true");
            gsm.setState(GameStateManager.State.MENU);
        }
    }

    public void draw(SpriteBatch g, int height, int width, float Time) {
        camera.setToOrtho(false, width, height);
        g.setProjectionMatrix(camera.combined);
        g.begin();
        Gdx.gl.glClearColor(255f, 255f, 255f, 1);

        gsm.Render.GUIDrawText(g, width / 2 - 35, height / 2+ 50, "A game made by", Color.WHITE);
        gsm.Render.GUIDrawText(g, width / 2 - 50, height / 2+ 30, "tecksup and darencius", Color.WHITE);
        g.draw(gsm.Render.getTexture("Gunter"), width / 2 - ((gsm.Render.getTexture("Gunter").getRegionWidth() * 0.5f) / 2), height / 2 - ((gsm.Render.getTexture("Gunter").getRegionHeight() * 0.5f) / 2));
        g.end();
    }

    public void drawUI(SpriteBatch g, int height, int width, float Time) {
        //Draws things on the screen, and not the world positions
        g.setProjectionMatrix(camera.combined);
        g.begin();
        //GUI must draw last
        g.end();
    }

    public void handleInput() {

        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) { //KeyHit
            //JukeBox.stop("LogoSound");
            gsm.setState(GameStateManager.State.MENU);
        }

    }

    @Override
    public void Shutdown() {

    }

}