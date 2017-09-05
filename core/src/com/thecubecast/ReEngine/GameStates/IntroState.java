// GameState that shows logo.

package com.thecubecast.ReEngine.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thecubecast.ReEngine.Data.Common;
import com.thecubecast.ReEngine.Data.GameStateManager;

public class IntroState extends GameState {
	
	private int alpha;
	private int ticks;
	
	static final int WORLD_WIDTH = 100;
	static final int WORLD_HEIGHT = 100;

	
	private final int FADE_IN = 20;
	private final int LENGTH = 40;
	private final int FADE_OUT = 20;
	
	public IntroState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		//JukeBox.load("/Music/bgmusic.wav", "LogoSound");
		//JukeBox.play("LogoSound");
	}
	
	public void update() {
		handleInput();
		ticks = gsm.Tics;
		if(ticks < FADE_IN) {
			alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
			if(alpha < 0) alpha = 0;
		}
		if(ticks > FADE_IN + LENGTH) {
			alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
			if(alpha > 255) alpha = 255;
		}
		if(ticks > FADE_IN + LENGTH + FADE_OUT) {
			//JukeBox.stop("LogoSound");
			gsm.setState(GameStateManager.MENU);
		}
	}
	
	public void draw(SpriteBatch g, int width, int height, float Time) {
	
		
		
		gsm.Render.DrawAny(g, 00, "Images", 60, 60);
		
	    //g.draw(gsm.Render.Images[00], PosX, PoxY);
	}
	
	public void handleInput() {

		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) { //KeyHit
			//JukeBox.stop("LogoSound");
			gsm.Render.Images[00] = null;
			gsm.setState(GameStateManager.MENU);
		}

	}
}