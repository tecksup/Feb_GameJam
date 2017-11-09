// GameState that tests new mechanics.

package com.thecubecast.ReEngine.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.thecubecast.ReEngine.Data.Common;
import com.thecubecast.ReEngine.Data.GameStateManager;
import com.thecubecast.ReEngine.Data.Player;

public class TestState extends GameState {
	
	Player player;
	int Cash = 0;
	
	boolean Moving = true;
	
	float lerp = 0.002f;
	Vector3 position;
	long last_time;
	
	float w;
    float h;
    
    TiledMap tiledMap;
    TiledMapTileLayer groundLay;
    Cell PlayerLoc;
    
    float camX;
    float camY;
    
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;

	public TestState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		player = new Player();
		player.setLocation(10, 75);
		Common.print("" + player.getLocation()[0]);
		
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camX = camera.position.x;
  		camY = camera.position.y;
  		camera.position.set((player.getLocation()[0]*80)+40, (player.getLocation()[1]*80)+40, camera.position.z);
  		position = camera.position;
  		last_time = System.nanoTime();

        tiledMap = new TmxMapLoader().load("Saves/Save2/MegaMiner_FirstMap.tmx");
        //tiledMapRenderer.setView(projectionMatrix, 0, 0, w, h);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        groundLay = (TiledMapTileLayer)tiledMap.getLayers().get(1);
	}
	
	public void update() {
		handleInput();
		
		long time = System.nanoTime();
	    int deltaTime = (int) ((time - last_time) / 1000000);
	    last_time = time;
		
		position.x += ((player.getLocation()[0]*80)+40 - position.x) * lerp * deltaTime;
		position.y += ((player.getLocation()[1]*80)+40 - position.y) * lerp * deltaTime;
		
		camera.position.set(position.x, position.y, camera.position.z);
		camera.update();
		
	}
	
	public void draw(SpriteBatch g, int width, int height, float Time) {

		gsm.Render.Player(g, Math.round((player.getLocation()[0]*80)-(camera.position.x - camX)), Math.round((player.getLocation()[1]*80)-(camera.position.y - camY)), player.getDirection());
	    //Overlay Layer
	    gsm.Render.GUIDeco(g, 0, height-70," " + Cash + "$");
	}
	
	public void RenderCam() {
		camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
	}
	
	public void handleInput() {

		if(Gdx.input.isTouched()) {
			camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
			camera.update();
			
			if (Gdx.input.getDeltaY() < 0) { // ZOOMS OUT
				
			}
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.NUM_9)) { //KeyHit
			//tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());			
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
		
		if (Moving) {
			if (groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile().getId() == -1) {
	
				if (Gdx.input.isKeyPressed(Keys.W)) { //KeyHit
					if (!player.getDirection().equals("up")) {
						player.setDirection("up");
					} else {
						player.setLocation(player.getLocation()[0], player.getLocation()[1] + 1);
					}
				}
				if (Gdx.input.isKeyPressed(Keys.S)) { //KeyHit
					if (!player.getDirection().equals("down")) {
						player.setDirection("down");
					} else {
						player.setLocation(player.getLocation()[0], player.getLocation()[1] - 1);
					}
				}
				if (Gdx.input.isKeyPressed(Keys.A)) { //KeyHit
					if (!player.getDirection().equals("left")) {
						player.setDirection("left");
					} else {
						player.setLocation(player.getLocation()[0] -1, player.getLocation()[1]);
					}
				}
				if (Gdx.input.isKeyPressed(Keys.D)) { //KeyHit
					if (!player.getDirection().equals("right")) {
						player.setDirection("right");
					} else {
						player.setLocation(player.getLocation()[0] + 1, player.getLocation()[1]);
					}
				}
			} else {
				
				if (Gdx.input.isKeyJustPressed(Keys.W)) { //KeyHit
					if (!player.getDirection().equals("up")) {
						player.setDirection("up");
					} else {
						player.setLocation(player.getLocation()[0], player.getLocation()[1] + 1);
					}
				}
				if (Gdx.input.isKeyJustPressed(Keys.S)) { //KeyHit
					if (!player.getDirection().equals("down")) {
						player.setDirection("down");
					} else {
						player.setLocation(player.getLocation()[0], player.getLocation()[1] - 1);
					}
				}
				if (Gdx.input.isKeyJustPressed(Keys.A)) { //KeyHit
					if (!player.getDirection().equals("left")) {
						player.setDirection("left");
					} else {
						player.setLocation(player.getLocation()[0] -1, player.getLocation()[1]);
					}
				}
				if (Gdx.input.isKeyJustPressed(Keys.D)) { //KeyHit
					if (!player.getDirection().equals("right")) {
						player.setDirection("right");
					} else {
						player.setLocation(player.getLocation()[0] + 1, player.getLocation()[1]);
					}
				}
			}
			
			if (groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile() == null) {
				
			}
			
			//Checks if the tile the player is on matches an ID
			if (groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile() != null && groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile().getId() == 21) {
				Common.print("Hey you just got Silver!");
				Cash = Cash + 25;
			}
			if (groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile() != null && groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile().getId() == 22) {
				Common.print("Hey you just got Copper!");
				Cash = Cash + 10;
			}
			if (groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile() != null && groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile().getId() == 23) {
				Common.print("Hey you just got Coal!");
				Cash = Cash + 5;
			}
			if (groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile() != null && groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile().getId() != 9 && groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile().getId() != 73) {
				Common.print("Hey you just got a " + groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile().getId());
			}
			groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).setTile(tiledMap.getTileSets().getTile(73));
		}
	}
	
		
	public void reSize(int wi, int he) {}
}