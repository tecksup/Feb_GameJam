// GameState that shows logo.

package com.thecubecast.ReEngine.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thecubecast.ReEngine.Data.Common;
import com.thecubecast.ReEngine.Data.GameStateManager;

public class LoadingState extends GameState {
	
	int ticks;
	
	public LoadingState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
	
	}
	
	public void update() {
		ticks = gsm.Tics;
		if(ticks > 120) {
			gsm.setState(GameStateManager.INTRO);
		}
		handleInput();
	}
	
	public void draw(SpriteBatch g, int width, int height, float Time) {
		gsm.Render.DrawAnimatedTile(g, gsm.Render.LoadingAnimation, 50, 50, 2.0f, 2.0f,  Time);
	}
	
	public void handleInput() {

		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) { //KeyHit
			gsm.setState(GameStateManager.INTRO);
		}

	}
}