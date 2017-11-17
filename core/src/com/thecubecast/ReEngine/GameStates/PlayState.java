// GameState that tests new mechanics.

package com.thecubecast.ReEngine.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.thecubecast.ReEngine.Data.Common;
import com.thecubecast.ReEngine.Data.GameStateManager;
import com.thecubecast.ReEngine.Data.Player;

public class PlayState extends GameState {
	
	Player player;
	int Cash = 0;
	
	boolean Moving = false;
	
	float lerp = 0.005f;
	Vector3 position;
	long last_time;
	int deltaTime;

    SpriteBatch guiBatch;
    
    OrthographicCamera camera;
    
    TiledMap tiledMap;
    TiledMapTileLayer groundLay;
    TiledMapRenderer tiledMapRenderer;

	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		guiBatch = new SpriteBatch();
		
		player = new Player();
		player.setLocation(10, 75);
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        
        camera.position.set((player.getLocation()[0]*80)+40, (player.getLocation()[1]*80)+40, camera.position.z);
  		position = camera.position;
        
  		last_time = System.nanoTime();

        tiledMap = new AtlasTmxMapLoader().load("Saves/Save2/MegaMiner_FirstMap.tmx");
        //tiledMap.getTileSets().getTileSet(0).getTile(1).getTextureRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap , 5f);
        groundLay = (TiledMapTileLayer)tiledMap.getLayers().get(1);
	}
	
	public void update() {
		handleInput();
		
		long time = System.nanoTime();
	    deltaTime = (int) ((time - last_time) / 1000000);
	    last_time = time;
	    
	    FollowCam(camera);
	    
		camera.update();
	}
	
	public void draw(SpriteBatch g, int width, int height, float Time) {
		RenderCam();
		
		g.begin();
		g.setProjectionMatrix(camera.combined);
		
		gsm.Render.Player(g, Common.roundDown((player.getLocation()[0]*80)), Common.roundDown((player.getLocation()[1]*80)), player.getDirection());

		g.end();

		//Overlay Layer
		guiBatch.begin();
	    gsm.Render.GUIDeco(guiBatch, 0, height-80, gsm.ChosenSave + " - " + Cash + "$");
	    guiBatch.end();
	}
	
	public void RenderCam() {
		camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
	}
	
	public void FollowCam(OrthographicCamera cam) {
		position.x += ((player.getLocation()[0]*80)+40 - position.x) * lerp * deltaTime;		
		position.y += ((player.getLocation()[1]*80)+40 - position.y) * lerp * deltaTime;
		
		cam.position.set(position.x, position.y, cam.position.z);
		cam.update();
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
			camera.translate(0,16);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) { //KeyHit
			camera.translate(0,-16);
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) { //KeyHit
			camera.translate(-16,0);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) { //KeyHit
			camera.translate(16,0);	
		}
		
		if (!Moving) {
			if (Gdx.input.isKeyJustPressed(Keys.W)) { //KeyHit
				if (!player.getDirection().equals("up")) {
					player.setDirection("up");
				} else {
					player.setDirection("up");
					player.setLocation(player.getLocation()[0], player.getLocation()[1] + 1);
				}
			}
			if (Gdx.input.isKeyJustPressed(Keys.S)) { //KeyHit
				if (!player.getDirection().equals("down")) {
					player.setDirection("down");
				} else {
					player.setDirection("down");
					player.setLocation(player.getLocation()[0], player.getLocation()[1] - 1);
				}
			}
			if (Gdx.input.isKeyJustPressed(Keys.A)) { //KeyHit
				if (!player.getDirection().equals("left")) {
					player.setDirection("left");
				} else {
					player.setDirection("left");
					player.setLocation(player.getLocation()[0] -1, player.getLocation()[1]);
				}
			}
			if (Gdx.input.isKeyJustPressed(Keys.D)) { //KeyHit
				if (!player.getDirection().equals("right")) {
					player.setDirection("right");
				} else {
					player.setDirection("right");
					player.setLocation(player.getLocation()[0] + 1, player.getLocation()[1]);
				}
			}
			
			if (groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile() == null) {
				//Checks if the tile is null
			}
			
			//Checks if the tile the player is on matches an ID
			if (groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile() != null && groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile().getId() != 9 && groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile().getId() == 21) {
				Common.print("Hey you just got Silver!");
				Cash = Cash + 25;
			}
			if (groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile() != null && groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile().getId() != 9 && groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile().getId() == 22) {
				Common.print("Hey you just got Copper!");
				Cash = Cash + 10;
			}
			if (groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile() != null && groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile().getId() != 9 && groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile().getId() == 23) {
				Common.print("Hey you just got Coal!");
				Cash = Cash + 5;
			}
			if (groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile() != null && groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile().getId() != 9 && groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile().getId() == 400) {
				//Common.print("Hey you just got a " + groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile().getId());
			}
			groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).setTile(tiledMap.getTileSets().getTile(400));
		}
	}
	
		
	public void reSize(SpriteBatch g, int H, int W) {
		float posX = camera.position.x;
		float posY = camera.position.y;
		float posZ = camera.position.z;
		camera.setToOrtho(false);
		camera.position.set(posX, posY, posZ);
		
		Matrix4 matrix = new Matrix4();
		matrix.setToOrtho2D(0, 0, W, H);
		guiBatch.setProjectionMatrix(matrix);
	}
}