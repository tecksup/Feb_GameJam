// GameState that shows logo.

package com.thecubecast.ReEngine.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thecubecast.ReEngine.Data.Common;
import com.thecubecast.ReEngine.Data.GameStateManager;

public class LoadingState extends GameState {
	
	int tics = 0;
	
	private String Load;
	public boolean Done = false;
	
	Thread t;
	
	float timeStart;
	
	public void setLoad(String LoadIt) {
		Load = LoadIt;
	}
	
	public LoadingState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		//Common.print("Loading " + Load);
		
		
		
		new Thread(new Runnable() {
				
			   @Override
			   public void run() {
			      Gdx.app.postRunnable(new Runnable() {
			         @Override
			         public void run() {
			        	 if (Load.equals("STARTUP")) {
			     			Common.print("Loading Startup");
			     			//RUN THE INITIALIZING STUFF
			     			gsm.Render.Load();
			     			Common.print("Loaded Startup");
			     			Done = true;
			     		}
			         }
			      });
			   }
			}).start();
		
		timeStart = System.nanoTime();
	}
	
	public void update() {
		tics++;
		if (Load.equals("STARTUP")) {
			if(tics >= 120 && Done) {
				gsm.setState(GameStateManager.INTRO);
			}
		}
		handleInput();
	}
	
	public void draw(SpriteBatch g, int width, int height, float Time) {
		g.begin();
		gsm.Render.DrawAnimatedTile(g, gsm.Render.LoadingAnimation, 50, 50, 2.0f, 2.0f,  Time);
		g.end();
	}
	
	public void handleInput() {

		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) { //KeyHit
			gsm.setState(GameStateManager.INTRO);
		}

	}
	
	public void reSize(SpriteBatch g,int wi, int he) {}
}