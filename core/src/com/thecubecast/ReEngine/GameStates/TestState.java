// GameState that tests new mechanics.

package com.thecubecast.ReEngine.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.thecubecast.ReEngine.Data.GameStateManager;

public class TestState extends GameState {
	
    Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
	
	public TestState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        tiledMap = new TmxMapLoader().load("Saves/Save2/MegaMiner_FirstMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}
	
	public void update() {
		handleInput();
		
	}
	
	public void draw(SpriteBatch g, int width, int height, float Time) {
		 Gdx.gl.glClearColor(1, 0, 0, 1);
	        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        camera.update();
	        tiledMapRenderer.setView(camera);
	        tiledMapRenderer.render();
	}
	
	public void handleInput() {

		if(Gdx.input.isTouched()) {
			camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
			camera.update();
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) { //KeyHit
			tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
			
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) { //KeyHit
			if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
				camera.translate(0,1);
			else 
				camera.translate(0,16);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) { //KeyHit
			if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
				camera.translate(0,-1);
			else 
				camera.translate(0,-16);
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) { //KeyHit
			if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
				camera.translate(-1,0);
			else 
				camera.translate(-16,0);
			
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) { //KeyHit
			if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
				camera.translate(1,0);
			else 
				camera.translate(16,0);
			
		}
		
	}
}