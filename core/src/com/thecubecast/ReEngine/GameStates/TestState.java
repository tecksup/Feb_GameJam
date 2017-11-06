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
import com.thecubecast.ReEngine.Data.Common;
import com.thecubecast.ReEngine.Data.GameStateManager;
import com.thecubecast.ReEngine.Data.Player;

public class TestState extends GameState {
	
	Player player;
	
	float w;
    float h;
    
    Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    
    float camZoom = 1.0f;
	
	public TestState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		player = new Player();
		player.setLocation(0, 0);
		Common.print("" + player.getLocation()[0]);
		
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

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
	        gsm.Render.GUIDeco(g, 0, 0, "TEST");
	        tiledMapRenderer.setView(camera);
	        tiledMapRenderer.render();
	}
	
	
	public void handleInput() {

		if(Gdx.input.isTouched()) {
			camera.translate(-Gdx.input.getDeltaX()*camZoom, Gdx.input.getDeltaY()*camZoom);
			camera.update();
			
			if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
				if (Gdx.input.getDeltaY() < 0) { // ZOOMS OUT
					if ((camZoom + 0.1f) < 5)
					camZoom = camZoom + 0.1f;
					camera.setToOrtho(false, w*camZoom, h*camZoom);
					camera.update();
				}
				if (Gdx.input.getDeltaY() > 0) {  // ZOOMS IN
					if ((camZoom - 0.1f) > 0.1)
						camZoom = camZoom - 0.1f;
					camera.setToOrtho(false, w*camZoom, h*camZoom);
					camera.update();
				}
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Keys.NUM_0)) {  // RESETS THE ZOOM
			camZoom = 1;
			camera.setToOrtho(false, w*camZoom, h*camZoom);
			camera.update();
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.NUM_9)) { //KeyHit
			tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());			
		}
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) { //KeyHit
			//tiledMap.getLayers().get("player");
		}
		
		if (Gdx.input.isKeyPressed(Keys.UP)) { //KeyHit
			if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
				camera.translate(0,4);
			else 
				camera.translate(0,16);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) { //KeyHit
			if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
				camera.translate(0,-4);
			else 
				camera.translate(0,-16);
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) { //KeyHit
			if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
				camera.translate(-4,0);
			else 
				camera.translate(-16,0);
			
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) { //KeyHit
			if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
				camera.translate(4,0);
			else 
				camera.translate(16,0);
			
		}
		
	}
}