// GameState that tests new mechanics.

package com.thecubecast.ReEngine.GameStates;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.thecubecast.ReEngine.Data.Achievement;
import com.thecubecast.ReEngine.Data.Common;
import com.thecubecast.ReEngine.Data.GameStateManager;
import com.thecubecast.ReEngine.Data.Player;
import com.thecubecast.ReEngine.Data.KeysDown;

public class PlayState extends GameState {
	
	int tile = 0;
	
	Player player;
	int Cash = 0;
	
	float[] Moving = new float[] {0, 0, 0};
	
	float lerp = 0.005f;
	Vector3 position;
	long last_time;
	int deltaTime;

    SpriteBatch guiBatch;
    
    //HUD Elements
    boolean menuOpen = false;
    List<Achievement> Achievements = new ArrayList<Achievement>();
    List<Achievement> MoneyFeedback = new ArrayList<Achievement>();
    
    //Controls
    List<KeysDown> KeysDw = new ArrayList<KeysDown>();
    
    OrthographicCamera camera;
    
    TiledMap tiledMap;
    TiledMapTileLayer groundLay;
    TiledMapRenderer tiledMapRenderer;

	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void AddAchievement(String text, int IconID, float Time, float Durration, boolean Anim) {
		Achievement temp = new Achievement(text, IconID, Time,  Durration, Anim);
		Achievements.add(Achievements.size(), temp);
		Common.print("Added Achievement: " + text);
	}
	
	public void AddMoneyFeedback(String text, float Time, float Durration) {
		Achievement temp = new Achievement(text, 0, Time,  Durration, false);
		MoneyFeedback.add(MoneyFeedback.size(), temp);
		Common.print("Added MoneyFeedback: " + text);
	}
	
	public void init() {
		
		gsm.Audio.playMusic("Wind", true);
		
		guiBatch = new SpriteBatch();
		
		player = new Player(30, 88);
        
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
	    
	    if (Moving[0] == 1) {
	    	float Distance = ((gsm.CurrentTime - Moving[1]) / Moving[2]) * 80;
	    	if (player.getDirection().equals("up")) {
	    		FollowCam(camera, Math.round((player.getLocation()[0]*80)+40), Math.round((player.getLocation()[1]*80)+40 + Distance));
			} else if (player.getDirection().equals("down")) {
				FollowCam(camera, Math.round((player.getLocation()[0]*80)+40), Math.round((player.getLocation()[1]*80)+40 - Distance));
			} else if (player.getDirection().equals("left")) {
				FollowCam(camera, Math.round((player.getLocation()[0]*80)+40 - Distance), Math.round(player.getLocation()[1]*80)+40);
			} else if (player.getDirection().equals("right")) {
				FollowCam(camera, Math.round((player.getLocation()[0]*80)+40 + Distance), Math.round(player.getLocation()[1]*80)+40);
			}
	    } else {
	    	FollowCam(camera, Math.round((player.getLocation()[0]*80)+40), Math.round((player.getLocation()[1]*80)+40));	
	    }
	    
		camera.update();
		
		for(int l=0; l< Achievements.size(); l++){
		   if (Achievements.get(l).getTime() >= Achievements.get(l).getDuration()) {
			   Achievements.remove(l);
		   }
		}

		for(int l=0; l< MoneyFeedback.size(); l++){
		   if (MoneyFeedback.get(l).getTime() >= MoneyFeedback.get(l).getDuration()) {
			   MoneyFeedback.remove(l);
		   }
		}		
	}
	
	public void draw(SpriteBatch g, int width, int height, float Time) {
		RenderCam();
		
		g.begin();
		g.setProjectionMatrix(camera.combined);
		
		
		if (Moving[0] == 1) {
			if (gsm.CurrentTime - Moving[1] <= Moving[2]) {
				float Distance = ((gsm.CurrentTime - Moving[1]) / Moving[2]) * 80;
				
				if (player.getDirection().equals("up")) {
					gsm.Render.Player(g, Math.round((player.getLocation()[0]*80)), Math.round((player.getLocation()[1]*80) + Distance), player.getDirection());
				} else if (player.getDirection().equals("down")) {
					gsm.Render.Player(g, Math.round((player.getLocation()[0]*80)), Math.round((player.getLocation()[1]*80) - Distance), player.getDirection());
				} else if (player.getDirection().equals("left")) {
					gsm.Render.Player(g, Math.round((player.getLocation()[0]*80) - Distance), Math.round((player.getLocation()[1]*80)), player.getDirection());
				} else if (player.getDirection().equals("right")) {
					gsm.Render.Player(g, Math.round((player.getLocation()[0]*80) + Distance), Math.round((player.getLocation()[1]*80)), player.getDirection());
				}
			} else {
				//Common.print("Player Location: " + (player.getLocation()[1]*80));
				if (player.getDirection().equals("up")) {
					player.setLocation(player.getLocation()[0], player.getLocation()[1] + 1);
					gsm.Render.Player(g, Math.round((player.getLocation()[0]*80)), Math.round((player.getLocation()[1]*80)), player.getDirection());
				} else if (player.getDirection().equals("down")) {
					player.setLocation(player.getLocation()[0], player.getLocation()[1] - 1);
					gsm.Render.Player(g, Math.round((player.getLocation()[0]*80)), Math.round((player.getLocation()[1]*80)), player.getDirection());
				} else if (player.getDirection().equals("left")) {
					player.setLocation(player.getLocation()[0] - 1, player.getLocation()[1]);
					gsm.Render.Player(g, Math.round((player.getLocation()[0]*80)), Math.round((player.getLocation()[1]*80)), player.getDirection());
				} else if (player.getDirection().equals("right")) {
					player.setLocation(player.getLocation()[0] + 1, player.getLocation()[1]);
					gsm.Render.Player(g, Math.round((player.getLocation()[0]*80)), Math.round((player.getLocation()[1]*80)), player.getDirection());
				} 
				
				//Mines the tile before it moves on
				MineTiles();
				
				if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.D)) {
					if (player.getDirection().equals("up") && Gdx.input.isKeyPressed(Keys.W)) {
						Move();
					} else if (player.getDirection().equals("down") && Gdx.input.isKeyPressed(Keys.S)) {
						Move();
					} else if (player.getDirection().equals("left") && Gdx.input.isKeyPressed(Keys.A)) {
						Move();
					} else if (player.getDirection().equals("right") && Gdx.input.isKeyPressed(Keys.D)) {
						Move();
					} else {
						if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.S)) {
							
						} else {
							if (Gdx.input.isKeyPressed(Keys.W)) { //KeyHit
								player.setDirection("up");
							}
							if (Gdx.input.isKeyPressed(Keys.S)) { //KeyHit
								player.setDirection("down");
							}
						}
						
						if (Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.D)) {
							
						} else {
							if (Gdx.input.isKeyPressed(Keys.A)) { //KeyHit
								player.setDirection("left");
							}
							if (Gdx.input.isKeyPressed(Keys.D)) { //KeyHit
								player.setDirection("right");
							}
						}
						Move();
					}
					
				} else {
					Moving[0] = 0;
					Moving[1] = 0;
					Moving[2] = 0;
				}
			}
		} else {
			gsm.Render.Player(g, Math.round((player.getLocation()[0]*80)), Math.round((player.getLocation()[1]*80)), player.getDirection());
		}
		
		//gsm.Render.DrawAny(g, tile, "Tiles", Common.roundDown((player.getLocation()[0]+1*80)),  Common.roundDown((player.getLocation()[1]*80)));
		//gsm.Render.GUIDrawText(g, Common.roundDown((player.getLocation()[0]+1*80)),  Common.roundDown((player.getLocation()[1]*80)-40), "" + tile);
		
		g.end();

		//Overlay Layer
		guiBatch.begin();
	    gsm.Render.GUIDeco(guiBatch, 0, height-80, gsm.ChosenSave + " - " + Cash + "$");
	    if (Achievements.size() != 0) {
			for(int l=0; l< Achievements.size(); l++){
				Achievements.get(l).setTime(Time);
				gsm.Render.HUDAchievement(guiBatch, width-260, (70 * l), Achievements.get(l).getText(), Achievements.get(l).getIconID(), Achievements.get(l).getOpacity(), Achievements.get(l).getAnim(), Time);
			}
	    	
	    }
	    
	    if (MoneyFeedback.size() != 0) {
			for(int l=0; l< MoneyFeedback.size(); l++){
				MoneyFeedback.get(l).setTime(Time);
				gsm.Render.MoneyFeedback(guiBatch, width/2 + 40, height/2 + 40 + (30 * l), MoneyFeedback.get(l).getText(), MoneyFeedback.get(l).getDuration()/MoneyFeedback.get(l).getTime());
			}
	    	
	    }
	    
	    if (menuOpen) {
	    	
	    }
	    
	    if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) { //KeyHit
	    	int TileID;
	    	
	    	gsm.Cursor = 2;
	   
	    	Vector3 pos = new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
			camera.unproject(pos);
			
	    	if (groundLay.getCell(Common.roundDown(pos.x)/80, Common.roundDown(pos.y)/80).getTile() != null) {
	    		TileID = groundLay.getCell(Common.roundDown(pos.x)/80, Common.roundDown(pos.y)/80).getTile().getId();
	    		camera.project(pos);
	    		gsm.Render.HUDDescr(guiBatch, Common.roundDown(pos.x)+15, Common.roundDown(pos.y)-15, "X: " + Common.roundDown(pos.x/80) + " Y: "+ Common.roundDown(pos.y/80));
	    		gsm.Render.HUDDescr(guiBatch, Common.roundDown(pos.x)+15, Common.roundDown(pos.y)-30, "" + TileID);
	    	} else {
	    		gsm.Render.HUDDescr(guiBatch, Common.roundDown(pos.x)+15, Common.roundDown(pos.y)-15, "X: " + Common.roundDown(pos.x/80) + " Y: "+ Common.roundDown(pos.y/80));
	    		camera.project(pos);
	    	}
		} else {
			gsm.Cursor = 0;
		}
	    
	    guiBatch.end();
	}
	
	public void RenderCam() {
		camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
	}
	
	public void FollowCam(OrthographicCamera cam, int playerx, int playery) {
		int mapBoundX = groundLay.getWidth()*80;
		int mapBoundY = (groundLay.getHeight()-17)*80;
		
		float PosibleX = position.x + (playerx - position.x) * lerp * deltaTime;
		if (PosibleX - (Gdx.graphics.getWidth()/2) >= 0 && PosibleX - (Gdx.graphics.getWidth()/2) <= mapBoundX) {
			position.x += (playerx - position.x) * lerp * deltaTime;
		}
		
		float PosibleY = position.y + (playery - position.y) * lerp * deltaTime;
		if (PosibleY - (Gdx.graphics.getHeight()/2) >= 0 && PosibleY - (Gdx.graphics.getHeight()/2) <= mapBoundY) {
			position.y += (playery - position.y) * lerp * deltaTime;
		} else if (PosibleY - (Gdx.graphics.getHeight()/2) >= mapBoundY) {
			position.y += (playery+160 - position.y) * lerp * deltaTime;
		}
		
		//position.x += ((player.getLocation()[0]*80)+40 - position.x) * lerp * deltaTime;		
		//position.y += ((player.getLocation()[1]*80)+40 - position.y) * lerp * deltaTime;
		
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
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) { //KeyHit
			menuOpen = !menuOpen;
		}
		
		if (Gdx.input.isKeyPressed(Keys.UP)) { //KeyHit
			int mapBoundY = (groundLay.getHeight()-18)*80;
			
			if (position.y - (Gdx.graphics.getHeight()/2) + 16 >= 0 && position.y - (Gdx.graphics.getHeight()/2) <= mapBoundY) {
				camera.translate(0,16);
			} else if (position.y - (Gdx.graphics.getHeight()/2) >= mapBoundY) {
				camera.translate(0,4);
			}
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) { //KeyHit
			int mapBoundY = (groundLay.getHeight()-18)*80;
			
			if (position.y - (Gdx.graphics.getHeight()/2) - 16 >= 0 && position.y - (Gdx.graphics.getHeight()/2) <= mapBoundY) {
				camera.translate(0,-16);
			} else if (position.y - (Gdx.graphics.getHeight()/2) >= mapBoundY) {
				camera.translate(0,-16);
			}
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) { //KeyHit
			int mapBoundX = groundLay.getWidth()*80;
			
			if (position.x - (Gdx.graphics.getWidth()/2) - 16 >= 0 && position.x - (Gdx.graphics.getWidth()/2) <= mapBoundX) {
				camera.translate(-16,0);
			}
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) { //KeyHit
			int mapBoundX = groundLay.getWidth()*80;
			
			if (position.x - (Gdx.graphics.getWidth()/2) + 16 >= 0 && position.x - (Gdx.graphics.getWidth()/2) <= mapBoundX) {
				camera.translate(16,0);
			}
		}
		
		if (Moving[0] == 0) {
			
			if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.S)) {
				
			} else {
				if (Gdx.input.isKeyPressed(Keys.W)) { //KeyHit
					if (!player.getDirection().equals("up")) {
						player.setDirection("up");
					} else {
						player.setDirection("up");
						Move();
					}
				}
				if (Gdx.input.isKeyPressed(Keys.S)) { //KeyHit
					if (!player.getDirection().equals("down")) {
						player.setDirection("down");
					} else {
						player.setDirection("down");
						Move();
					}
				}
			}
			
			if (Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.D)) {
				
			} else {
				if (Gdx.input.isKeyPressed(Keys.A)) { //KeyHit
					if (!player.getDirection().equals("left")) {
						player.setDirection("left");
					} else {
						player.setDirection("left");
						Move();
					}
				}
				if (Gdx.input.isKeyPressed(Keys.D)) { //KeyHit
					if (KeysDw.isEmpty()) {
						KeysDown temp = new KeysDown(Keys.D, gsm.CurrentTime);
						KeysDw.add(KeysDw.size(), temp);
					} else {
						if (KeysDw.get(KeysDw.size()-1).GetKeyTime(gsm.CurrentTime) >= 0.05f) {
							if (!player.getDirection().equals("right")) {
								player.setDirection("right");
							} else {
								player.setDirection("right");
								Move();
							}	
							KeysDw.remove(KeysDw.get(KeysDw.size()-1));
						}
					}
				}
			}
			
			
			if (Gdx.input.isKeyJustPressed(Keys.F)) { //KeyHit
				Common.print("Player is facing " + isFacing());
				if(isFacing() == 74) {
					AddAchievement("Activated Crate!", 73, gsm.CurrentTime, 1.5f, false);
				}
				if(isFacing() == 72 || isFacing() == 73) {
					AddAchievement("Activated Teleporter!", 6, gsm.CurrentTime, 5f, true);
					player.setLocation(20, 20);
				}
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.NUM_9)) { //KeyHit
			//	tile++;
				AddAchievement("Fake Achievement", 1, gsm.CurrentTime, 1f, false);
			}
			if (Gdx.input.isKeyJustPressed(Keys.NUM_8)) { //KeyHit
			//	tile--;
			}
			MineTiles();
		}
	}
	
	public int isOn() {
		if (groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile() != null) {
			return (groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).getTile().getId());
		} else {
			return -1;
		}
		
	}
	
	public int isFacing() {
		
		if (player.getLocation()[0]-1 < 0 || player.getLocation()[0]-1 > 100) {
			return -1;
		}
		
		if (player.getLocation()[1]-1 < 0 || player.getLocation()[1]-1 > 100) {
			return -1;
		}
		
		if (player.getDirection().equals("up")) {
			return (groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])+1).getTile().getId());
		}
		else if (player.getDirection().equals("down")) {
			return (groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])-1).getTile().getId());	
		}
		else if (player.getDirection().equals("left")) {
			return (groundLay.getCell(Common.roundDown(player.getLocation()[0])-1, Common.roundDown(player.getLocation()[1])).getTile().getId());
		}
		else if (player.getDirection().equals("right")) {
			return (groundLay.getCell(Common.roundDown(player.getLocation()[0])+1, Common.roundDown(player.getLocation()[1])).getTile().getId());
		}
		else {
			return -1;
		}
		
	}
		
	public void Move() {
		Moving[0] = 1;
		Moving[1] = gsm.CurrentTime;

		if(player.getLocation()[1]+1 > player.MaxY && player.getDirection().equals("up")) {
			Moving[0] = 0;
			Moving[1] = 0;
			Moving[2] = 0;
		}
		
		if (isFacing() == -1) {
			Moving[0] = 0;
			Moving[1] = 0;
			Moving[2] = 0;
		}
		
		if (isFacing() == 9) {
			Moving[2] = 0.6f;
		} else if (isFacing() == 10) {
			Moving[2] = 0.4f;
		} else if (isFacing() == 21) {
			Moving[2] = 0.6f;
		} else if (isFacing() == 22 || isFacing() == 12) {
			Moving[2] = 0.6f;
		} else if (isFacing() == 23|| isFacing() == 11) {
			Moving[2] = 0.6f;
		} else {
			Moving[2] = 0.2f;
		}
	}
	
	public void MineTiles() {
		//Checks if the tile the player is on matches an ID
		if (isOn() == 21) {
			AddMoneyFeedback("+25", gsm.CurrentTime, 2.5f);
			//AddAchievement("Hey you just got Silver!", 20, gsm.CurrentTime, 1.5f, false);
			Cash = Cash + 25;
		}
		if (isOn() == 22 || isOn() == 12) {
			AddMoneyFeedback("+10", gsm.CurrentTime, 2.5f);
			//AddAchievement("Hey you just got Copper!", 21, gsm.CurrentTime, 1.5f, false);
			Cash = Cash + 10;
		}
		if (isOn() == 23 || isOn() == 11) {
			AddMoneyFeedback("+5", gsm.CurrentTime, 2.5f);
			//AddAchievement("Hey you just got Coal!", 22, gsm.CurrentTime, 1.5f, false);
			Cash = Cash + 5;
		}
		if (isOn() != 400) {
			//Common.print("Hey you just got a " + groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile().getId());
		}
		if (player.getLocation()[1] < player.MaxY) {
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
	
	public void IsKeyPressed(Keys Keys) {
		
	}
}