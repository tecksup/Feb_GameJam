// The GameStateManager does exactly what its
// name says. It contains a list of GameStates.
// It decides which GameState to update() and
// draw() and handles switching between different
// GameStates.

package com.thecubecast.ReEngine.Data;

import com.thecubecast.ReEngine.GameStates.LoadingState;
import com.thecubecast.ReEngine.GameStates.GameState;
import com.thecubecast.ReEngine.GameStates.IntroState;
import com.thecubecast.ReEngine.GameStates.MainMenuState;
//import com.thecubecast.ReEngine.GameStates.OptionsState;
import com.thecubecast.ReEngine.GameStates.PlayState;
import com.thecubecast.ReEngine.GameStates.TestState;
import com.thecubecast.ReEngine.Graphics.Draw;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.thecubecast.ReEngine.Data.Common;

public class GameStateManager {
	
	public GameState[] gameStates;
	public int currentState;
	private int previousState;
	
	public String ChosenSave;
	
	public float CurrentTime;
	
	//Public render function object
	public Draw Render;
	
	//Public file handler
	public ReadWrite Rwr;
	
	//MousePos
	public int MouseX;
	public int MouseY;
	public int[] MouseDrag;
	public int[] MouseClick;
	public int OldCursor = 0;
	public int Cursor = 0;
	
	//screen
	public int Width;
	public int Height;
	
	public static final int NUM_STATES = 6;
	public static final int INTRO = 0;
	public static final int MENU = 1;
	public static final int PLAY = 2;
	public static final int LOADING = 3;
	public static final int OPTIONS = 4;
	public static final int TEST = 5;
	
	public GameStateManager() {
		
		//JukeBox.init();

		Render = new Draw();
		Rwr = new ReadWrite();
		
		Rwr.init();
		Render.Init();
		
		gameStates = new GameState[NUM_STATES];
		LoadState("STARTUP"); //THIS IS THE STATE WERE WE START WHEN THE GAME IS RUN
		
	}
	
	public void LoadState(String LoadIt) {
		previousState = currentState;
		unloadState(previousState);
		currentState = LOADING;
		//Set up the loading state 
		gameStates[LOADING] = new LoadingState(this);
		((LoadingState) gameStates[LOADING]).setLoad("STARTUP");
		gameStates[LOADING].init();
	}
	
	public void setState(int i) {
		previousState = currentState;
		unloadState(previousState);
		currentState = i;
		if(i == INTRO) {
			Common.print("Loaded state Intro");
			gameStates[i] = new IntroState(this);
			gameStates[i].init();
		}
		else if(i == MENU) {
			Common.print("Loaded state MENU");
			gameStates[i] = new MainMenuState(this);
			gameStates[i].init();
		}
		else if(i == PLAY) {
			Common.print("Loaded state PLAY");
			gameStates[i] = new PlayState(this);
			gameStates[i].init();
		}
		
		/**
		else if(i == OPTIONS) {
			Common.print("Loaded state Options");
			gameStates[i] = new OptionsState(this);
			gameStates[i].init();
		}
		**/
		else if(i == TEST) {
			//Common.print("Loaded state Test");
			gameStates[i] = new TestState(this);
			gameStates[i].init();
		}
		
	}
	
	public void unloadState(int i) {
		//Common.print("Unloaded state " + i);
		gameStates[i] = null;
	}
	
	public void update(int MousX, int MousY, int[] Draging, int[] MousCl) {
		if (Cursor != OldCursor) {
			OldCursor = Cursor;
			com.badlogic.gdx.graphics.Cursor customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursor" + Cursor + ".png")), 0, 0);
    		Gdx.graphics.setCursor(customCursor);
		}
		MouseX = MousX;
		MouseY = MousY;
		MouseDrag = Draging;
		MouseClick = MousCl;
		if(gameStates[currentState] != null) {
			
			gameStates[currentState].update();
		}
		//MouseClick[0] = 0;
	}
	
	public void draw(SpriteBatch bbg, int W, int H, float Time) {
		Width = W;
		Height = H;
		CurrentTime = Time;
		if(gameStates[currentState] != null) {
			gameStates[currentState].draw(bbg, H, W, Time);
		}
	}
	
	public void reSize(SpriteBatch bbg, int H, int W) {
		if(gameStates[currentState] != null) {
			gameStates[currentState].reSize(bbg, H, W);
		}
		Matrix4 matrix = new Matrix4();
		matrix.setToOrtho2D(0, 0, W, H);
		bbg.setProjectionMatrix(matrix);
	}
}
