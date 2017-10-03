// GameState that tests new mechanics.

package com.thecubecast.ReEngine.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thecubecast.ReEngine.Data.GameStateManager;

public class TestState extends GameState {
	
	public TestState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
	}
	
	public void update() {
		handleInput();
		
	}
	
	public void draw(SpriteBatch g, int width, int height, float Time) {
		
	}
	
	public void handleInput() {

		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) { //KeyHit
			
			
		}

	}
}