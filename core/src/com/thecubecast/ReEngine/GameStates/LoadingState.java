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
		if(ticks > 60) {
			gsm.setState(GameStateManager.INTRO);
		}
		handleInput();
	}
	
	public void draw(SpriteBatch g, int width, int height, float Time) {
		gsm.Render.DrawAnimatedTile(g, gsm.Render.LoadingAnimation, 50, 50, Time);
		
		gsm.Render.DrawAny(g, 00, "Tiles", 50, 50);
		gsm.Render.DrawAny(g, 00, "Tiles", 50+gsm.Render.Tiles[60].getWidth(), 50+gsm.Render.Tiles[60].getHeight());
	    //g.draw(gsm.Render.Images[00], PosX, PoxY);
	}
	
	public void handleInput() {

		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) { //KeyHit
			gsm.setState(GameStateManager.INTRO);
		}

	}
}