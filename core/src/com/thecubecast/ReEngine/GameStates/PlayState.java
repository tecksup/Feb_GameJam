package com.thecubecast.ReEngine.GameStates;

import com.thecubecast.ReEngine.Data.GameStateManager;
import com.thecubecast.ReEngine.Tiled.GameMap;
import com.thecubecast.ReEngine.Tiled.TileType;
import com.thecubecast.ReEngine.Tiled.TiledGameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.thecubecast.ReEngine.Data.Common;

import java.awt.*;


public class PlayState extends GameState {	
	
	private String CurrentSave = gsm.ChosenSave;
	
	GameMap gameMap;
	
	OrthographicCamera cam;
	
	private int TileSize = 32; //This is the size of each tile, aswell as how far the camera moves per "turn"
	private int WorldSize = 200; //radius expanding from the origin point (0,0) of the world
	private int MousePosX;
	private int MousePosY;
	private int[] MouseDrag;
	//private int PlayerPosX = 0;
	//private int PlayerPosY = 0;
	private int cameraX = 0;
	private int cameraY = 0;
	
	
	//Buttons
	//Object[] Buttons = new Object[]; //This is supposed to be a array containing all the buttons
										//So they can be interated through in the button checks
	int[] button1 = null;
	int[] button2 = null;
	
	//only loading in 4 chunks from the chunk the player is in, in each direction
	//Max Loaded chunks will be 16, Total of 4096 Tiles in memory
	//Each chunk only stores 16X16 tiles
	
	//The chunks do not need to be held in a map, Calculate the chunks that need to be loaded
	//If they are near the players pos, When the camera distance becomes < a set distance - Unload the chunk
	// Load the chunk if the camera get > a set distance 
	
	//The chunks will be individual files for now Loading the chunk from file
	//Save/update the chunk to file, then unload it from memory
	
	
	
	private int PlayerDirection = 4;
	private boolean MenuOpen = false;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		gameMap = new TiledGameMap();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1920, 1080);
		cam.update();
		
		//JukeBox.load("/Music/bgmusic.wav", "introsound");
		//JukeBox.loop("introsound");
		//JukeBox.setVolume("introsound", -30.0f);
	}
	
	
	public void update() {
		
		MousePosX = gsm.MouseX;
		MousePosY = gsm.MouseY;
		MouseDrag = gsm.MouseDrag;
		
		handleInput();
		
		//This is were the camera location is updated
		//Camera tracks to the players pos, instead of placing the player on the middle of the screen non-relative to the map layout
		//cameraX = WIDTH/2; // Put PlayerPosX in the middle of the screen
		//cameraY = HEIGHT/2; // Put PlayerPosY in the middle of the screen
		
		//IF STATEMENT THAT WILL BE PUT HERE WHEN INPUT HANDLING IS FIXED
		//IT WILL QUIT THE GAME
		
	}
	
	public void draw(SpriteBatch bbg, int width, int height, float Time) {
		
		/**
		 * To use the camera
		 * Subtract the location of the sprite by the cameras position.
		 */
		
		//The bottom layer
		//Draw the background here
		gsm.Render.DrawBackground(bbg, width, height);
		
		//The Tiles are being drawn on this "Layer"
		//A function that reads the map file, then places each tile on the screen
		
		//gsm.Render.DrawTiles(bbg, cameraX, cameraY, TileSize, WorldSize);
		
		gameMap.render(cam);
		
		//The "Player" and other entities or overlays must be drawn last. Think top layer 
		gsm.Render.Player(bbg, (width/2), ((height/2) - (TileSize/2)), TileSize, TileSize, PlayerDirection);
		
		// Draws the Foreground
		//gsm.Render.DrawTilesForeground(bbg, cameraX, cameraY, TileSize, WorldSize);
		
		//Debug Layer goes here
		//gsm.Render.DrawChunkDebugLines(bbg, 0, 0, TileSize, cameraX, cameraY);
		
		//The GUI would go here
		gsm.Render.GUIDeco(bbg, 0, 0, CurrentSave); //Any overlays such as Health, gold, fuel, etc.
		if(MenuOpen) {
			//bbg.fillRect((width/6)*2, (height/6)*2, (width/6)*2, (height/6)*2);
			button1 = gsm.Render.GUIButton(bbg, width/2, height/2, 6,  true, "Return to Menu");
			} // The Game MEnu
		
		//gsm.Render.DrawAny(bbg, 00, "Tiles", MousePosX, MousePosY);
		
		//gameMap.render(cam);
		
		
		
		
	}
	
	public void handleInput() {
		
		if(Gdx.input.isTouched()) {
			cam.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
			cam.update();
		}
		
		if(Gdx.input.justTouched()) {
			Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			TileType type = gameMap.getTileTypeByLocation(1, pos.x, pos.y);
			
			if (type != null) { 
				Common.print("You Clicked on tile with id " + type.getId() + " " + type.getName());
			}
		}
		
		
		if(gsm.MouseClick[0] == 1 && MenuOpen) { //Runs all the button checks			
			if(gsm.MouseClick[1] >= button1[0] && gsm.MouseClick[1] <= button1[2]) { //The button1
				if(gsm.MouseClick[2] >= button1[1] && gsm.MouseClick[2] <= button1[3]) {
					//Save the game
					//stop all audio
					gsm.setState(GameStateManager.MENU);
				}
			}
		}
		
		if (MouseDrag[0] == 1) {
			//Common.print("Mouse draging at " + MousePosX + " " + MousePosY);
		}
		
		//Moves the player on the map
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			MenuOpen = !MenuOpen;
			Common.sleep(50);
		} else {}
		
		if (!MenuOpen) {
			if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
			{ 
				if (Gdx.input.isKeyPressed(Keys.LEFT))  {}
				else {
					if (PlayerDirection != 4) {
						PlayerDirection = 4;
					}
					else {
						cameraX += TileSize; 
					}
				}
			} 
			
			if (Gdx.input.isKeyPressed(Keys.LEFT))
			{ 
				if (Gdx.input.isKeyPressed(Keys.RIGHT)) {}
				else {
					if (PlayerDirection != 2) {
						PlayerDirection = 2;	
					}
					else {
						cameraX -= TileSize;
					}
				}
			}
			
			if (Gdx.input.isKeyPressed(Keys.UP))
			{ 
				if (Gdx.input.isKeyPressed(Keys.DOWN)) {}
				else {
					if (PlayerDirection != 1) {
						PlayerDirection = 1;
					}
					else {
						cameraY += TileSize;
					}
				}
			}
			
			
			if (Gdx.input.isKeyPressed(Keys.DOWN))
			{ 
				if (Gdx.input.isKeyPressed(Keys.UP)) {}
				else {
					if (PlayerDirection != 3) {
						PlayerDirection = 3;
					}
					else {
						cameraY -= TileSize; 
					}
				}
			} 
		}
	}

}