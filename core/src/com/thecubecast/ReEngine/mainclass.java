package com.thecubecast.ReEngine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thecubecast.ReEngine.Data.GameStateManager;
import com.thecubecast.ReEngine.Data.Common;

public class mainclass extends ApplicationAdapter implements InputProcessor{
	
	//The Drawing Variable
	int W;
	int H;
	SpriteBatch batch;
	
	//Mouse Position in the window
	public int MouseX;
	public int MouseY;
	int[] MouseDrag = new int[] {0, 0, 0};
	int[] MouseClick = new int[] {0, 0, 0};
	
	// game state manager
	private GameStateManager gsm;

	// A variable for tracking elapsed time for the animation
	float stateTime;
	
	
	
	@Override
	public void create () { // INIT FUNCTION
		
		Cursor customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursor.png")), 0, 0);
		Gdx.graphics.setCursor(customCursor);
		
		stateTime = 0f;
		
		Gdx.input.setInputProcessor(this);
		
		//Just setting up the variables
		W = Gdx.graphics.getWidth();
		H = Gdx.graphics.getHeight();
		
		//Camera Init
		//camera = new OrthographicCamera(80, 45 * (H / W));
		//camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		//camera.update();
		
		
		//This is essentially the graphics object we draw too
		batch = new SpriteBatch();
		
		
		
		//Figure out how to do this before you start exporting things to external files
		gsm = new GameStateManager();
	}
	
	@Override
	public void render () { // UPDATE Runs every frame. 60FPS

		//Gdx.gl.glClearColor( 1, 1, 1, 1 );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Code goes here
		
		UpdateInput();
		Update(); //UPDATE
	
		
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
			
		//batch.begin();
		Draw(batch); //DRAW
		//batch.end();
		
		
		if(MouseClick[0] == 1) {
			MouseClick[0] = 0;
		}
	}
	
	public void UpdateInput(){
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) { //KeyHit
			Common.print("Pressed Enter");
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.GRAVE)) { //KeyHit
			Common.ProperShutdown();
		}
		
	}
	
	public void Update() {
		
		//Figure out how to do this before you start exporting things to external files
		gsm.update(MouseX, MouseY, MouseDrag, MouseClick);
	}
	
	public void Draw(SpriteBatch bbg) {
		//Figure out how to do this before you start exporting things to external files
		gsm.draw(bbg, H, W, stateTime);
	}
	
	@Override
	public void resize(int width, int height) {
		W = width;
		H = height;
		//callback.setHeight(height);
		//callback.setWidth(width);
		Common.print("Ran Resize!");
		Common.print("" + width + " and H: " + height);
		gsm.reSize(batch, height, width);
	}
	
	
	@Override
	public void dispose () { //SHUTDOWN FUNCTION
		batch.dispose();
		
		//Cleanup(); SaveAll();
	}

	public boolean keyDown(int keycode) {return false;}
	public boolean keyUp(int keycode) {return false;}
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {return false;}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		MouseDrag[0] = 0;
		//Common.print("Clicked!");
		int[] MouseClicked = new int[] {1, MouseX, MouseY};
		MouseClick = MouseClicked;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		//Common.print("Dragging");
			int[] MouseDraged = new int[] {1, screenX, screenY};
			MouseDrag = MouseDraged;
			MouseX = screenX;
			MouseY = screenY;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		MouseX = screenX;
		MouseY = screenY;
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}