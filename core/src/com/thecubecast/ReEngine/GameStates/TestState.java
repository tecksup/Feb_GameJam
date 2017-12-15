// GameState that tests new mechanics.

package com.thecubecast.ReEngine.GameStates;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
import com.badlogic.gdx.utils.XmlWriter;
import com.thecubecast.ReEngine.Data.Achievement;
import com.thecubecast.ReEngine.Data.Common;
import com.thecubecast.ReEngine.Data.GameStateManager;
import com.thecubecast.ReEngine.Data.Player;
import com.thecubecast.ReEngine.Graphics.MenuState;
import com.thecubecast.ReEngine.Data.KeysDown;

public class TestState extends GameState {
	
	//The Menu states
	private int OldState;
	private int currentState;
	OrthographicCamera cameraGui;
	//The buttons only run functions and contain drawing object, the Value variables hold and change data
	//The buttons state 0     MAIN MENU
	List<MenuState> MainMenu = new ArrayList<MenuState>();
	List<MenuState> AudioMenu = new ArrayList<MenuState>();
	List<MenuState> Options = new ArrayList<MenuState>();

	//The Hud States
	public boolean HUDOpen = false;
	private int HUDOldState;
	private int HUDcurrentState;
	List<MenuState> InventoryHUD = new ArrayList<MenuState>();
	
	Player player;
	
	float[] Moving = new float[] {0, 0, 0};
	
	float lerp = 0.005f;
	Vector3 position;
	long last_time;
	int deltaTime;

    SpriteBatch guiBatch;
    
    //HUD Elements
    public boolean menuOpen = false;
    int tics = 0;
    boolean flashfuel = false;
    List<Achievement> Achievements = new ArrayList<Achievement>();
    List<Achievement> MoneyFeedback = new ArrayList<Achievement>();
    
    //Controls
    List<KeysDown> KeysDw = new ArrayList<KeysDown>();
    
    OrthographicCamera camera;
    
    TiledMap tiledMap;
    TiledMapTileLayer groundLay;
    TiledMapRenderer tiledMapRenderer;

	public TestState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void AddAchievement(String text, int IconID, float Time, float Durration, boolean Anim) {
		Achievement temp = new Achievement(text, IconID, Time,  Durration, Anim);
		Achievements.add(Achievements.size(), temp);
		Common.print("Added Achievement: " + text);
	}
	
	public void AddMoneyFeedback(String text, float Time, float Durration) {
		Achievement temp = new Achievement(text, 0, Time,  Durration, false);
		gsm.Audio.play("CashGet");
		MoneyFeedback.add(MoneyFeedback.size(), temp);
		Common.print("Added MoneyFeedback: " + text);
	}
	
	public void init() {
		
		//gsm.Rwr.CreateWorld(gsm.ChosenSave, 200, 200);
		
		 // tiledMap = new AtlasTmxMapLoader().load("Saves/Save2/MegaMiner_FirstMap.tmx");
        tiledMap = new AtlasTmxMapLoader().load("Saves/" + gsm.ChosenSave + "/map.tmx");
        //tiledMap.getTileSets().getTileSet(0).getTile(1).getTextureRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap , 5f);
        groundLay = (TiledMapTileLayer)tiledMap.getLayers().get(1);
		
        int SavedCash = (Integer) groundLay.getProperties().get("Cash");
        float SavedGas = (Float) groundLay.getProperties().get("Fuel");
        float SavedX = (Float) groundLay.getProperties().get("SavedX");
        float SavedY = (Float) groundLay.getProperties().get("SavedY");
        
        Common.print("SavedCash: " + SavedCash);
        
		MenuInit();
		
		gsm.Audio.playMusic("Wind", true);
		
		guiBatch = new SpriteBatch();
		
		player = new Player(SavedX, SavedY, SavedGas, SavedCash, tiledMap.getProperties().get("height", Integer.class)-12, tiledMap.getProperties().get("width", Integer.class)); //30 88
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        
        camera.position.set((player.getLocation()[0]*80)+40, (player.getLocation()[1]*80)+40, camera.position.z);
  		position = camera.position;
        
  		last_time = System.nanoTime();
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
		guiBatch.setProjectionMatrix(cameraGui.combined);
		
	    gsm.Render.GUIDeco(guiBatch, 0, height-80, gsm.ChosenSave + " - " + player.Cash + "$");
	    //gsm.Render.HUDNotification(guiBatch, width/2, height-100, 300 ,"Hey does this really wrap itself it would be so cool if it did so now i have to write a realllllllly long string to fill it up and make it wrap", gsm.ticks);
	    
	    if (player.Gas+45 < 5) {
	    	tics++;
	    	if (tics == 8) {
	    		tics = 0;
	    		flashfuel = !flashfuel;
	    	}
	    	gsm.Render.HUDFuel(guiBatch, 0, 0, player.Gas, flashfuel);

	    } else if (player.Gas+45 < 25) {
	    	tics++;
	    	if (tics == 15) {
	    		tics = 0;
	    		flashfuel = !flashfuel;
	    	}
	    	gsm.Render.HUDFuel(guiBatch, 0, 0, player.Gas, flashfuel);
	    	
	    	//gsm.Render.warningText(guiBatch, width/2, height - 40, "Warning Fuel Very Low!" , gsm.ticks);

	    } else if (player.Gas+45 < 35) {
	    	tics++;
	    	if (tics == 30) {
	    		tics = 0;
	    		flashfuel = !flashfuel;
	    	}
	    	gsm.Render.HUDFuel(guiBatch, 0, 0, player.Gas, flashfuel);
	    	
	    	//gsm.Render.warningText(guiBatch, width/2, height - 40, "Fuel Warning!" , gsm.ticks);

	    } else {
	    	tics = 0;
	    	flashfuel = false;
	    	 gsm.Render.HUDFuel(guiBatch, 0, 0, player.Gas, flashfuel);
	    }
	 
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
	    	MenuDraw(guiBatch, width, height, Time);
	    }
	    
	    if (HUDOpen) {
	    	HUDDraw(guiBatch, width, height, Time);
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
		int mapBoundY = (groundLay.getHeight()-18)*80;
		
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
		
		Vector3 pos = new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
		cameraGui.unproject(pos);
		
		gsm.MouseX = (int) pos.x;
		gsm.MouseY = (int) pos.y;
		gsm.MouseClick[1] = (int) pos.x;
		gsm.MouseClick[2] = (int) pos.y;
		gsm.MouseDrag[1] = (int) pos.x;
		gsm.MouseDrag[2] = (int) pos.y;
		
		if (menuOpen) {
	    	MenuHandle();
	    }
		
		if (HUDOpen) {
			MenuHandle();
		}
		
		if(Gdx.input.isTouched()) {
			//camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
			//camera.update();
			
			if (Gdx.input.getDeltaY() < 0) { // ZOOMS OUT
				
			}
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) { //KeyHit
			if (HUDOpen) {
				HUDOpen = false;
			} else {
				menuOpen = !menuOpen;
			}
			//HUDOpen = false;
			//menuOpen = !menuOpen;
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
		
		if (Moving[0] == 0 && menuOpen != true) {
			
			if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.S)) {
				
			} else {
				if (Gdx.input.isKeyPressed(Keys.W)) { //KeyHit
					if (KeysDw.isEmpty()) {
						KeysDown temp = new KeysDown(Keys.W, gsm.CurrentTime);
						KeysDw.add(KeysDw.size(), temp);
					} else {
						if (KeysDw.get(KeysDw.size()-1).GetKeyTime(gsm.CurrentTime) >= 0.05f) {
							if (!player.getDirection().equals("up")) {
								player.setDirection("up");
							} else {
								player.setDirection("up");
								Move();
							}	
							KeysDw.remove(KeysDw.get(KeysDw.size()-1));
						}
					}
				}
				if (Gdx.input.isKeyPressed(Keys.S)) { //KeyHit
					if (KeysDw.isEmpty()) {
						KeysDown temp = new KeysDown(Keys.S, gsm.CurrentTime);
						KeysDw.add(KeysDw.size(), temp);
					} else {
						if (KeysDw.get(KeysDw.size()-1).GetKeyTime(gsm.CurrentTime) >= 0.05f) {
							if (!player.getDirection().equals("down")) {
								player.setDirection("down");
							} else {
								player.setDirection("down");
								Move();
							}	
							KeysDw.remove(KeysDw.get(KeysDw.size()-1));
						}
					}
				}
			}
			
			if (Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.D)) {
				
			} else {
				if (Gdx.input.isKeyPressed(Keys.A)) { //KeyHit
					if (KeysDw.isEmpty()) {
						KeysDown temp = new KeysDown(Keys.A, gsm.CurrentTime);
						KeysDw.add(KeysDw.size(), temp);
					} else {
						if (KeysDw.get(KeysDw.size()-1).GetKeyTime(gsm.CurrentTime) >= 0.05f) {
							if (!player.getDirection().equals("left")) {
								player.setDirection("left");
							} else {
								player.setDirection("left");
								Move();
							}	
							KeysDw.remove(KeysDw.get(KeysDw.size()-1));
						}
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
					//AddAchievement("Activated Crate!", 73, gsm.CurrentTime, 1.5f, false);
					AddMoneyFeedback("" + player.topUp(), gsm.CurrentTime, 2.5f);
				}
				if(isFacing() == 72 || isFacing() == 73) {
					AddAchievement("Activated Teleporter!", 6, gsm.CurrentTime, 5f, true);
					player.setLocation(20, 20);
				}
				if(isFacing() == 75 || isFacing() == 76 || isFacing() == 77) {
					AddAchievement("Opened Shop!", 6, gsm.CurrentTime, 5f, true);
					HUDcurrentState = 0;
					HUDOpen = true;
				}
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.I)) { //KeyHit
				AddAchievement("Opened Inventory!", 6, gsm.CurrentTime, 5f, true);
				HUDcurrentState = 5;
				HUDOpen = !HUDOpen;
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.NUM_9)) { //KeyHit
			//	tile++;
				SaveGame();
				
				//AddAchievement("Fake Achievement", 1, gsm.CurrentTime, 1f, false);
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
		
		if (player.getDirection().equals("up")) {
			if (player.getLocation()[1]+1 > player.MaxY) {
				return -1;
			} else {
				return (groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])+1).getTile().getId());
			}
		}
		else if (player.getDirection().equals("down")) {
			if (player.getLocation()[1]-1 < 0) {
				return -1;
			} else {
				return (groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])-1).getTile().getId());
			}
		}
		else if (player.getDirection().equals("left")) {
			if (player.getLocation()[0]-1 < 0) {
				return -1;
			} else {
				return (groundLay.getCell(Common.roundDown(player.getLocation()[0])-1, Common.roundDown(player.getLocation()[1])).getTile().getId());
			}
		}
		else if (player.getDirection().equals("right")) {
			if (player.getLocation()[0]+1 >
			player.MaxX) {
				return -1;
			} else {
				return (groundLay.getCell(Common.roundDown(player.getLocation()[0])+1, Common.roundDown(player.getLocation()[1])).getTile().getId());
			}
		}
		else {
			return -1;
		}
		
	}
		
	public void Move() {

		boolean Ore = false;
		
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
			Moving[2] = 0.7f;
		} else if (isFacing() == 1 || isFacing() == 2) {
			Moving[2] = 0.5f;
		} else if (isFacing() == 10) {
			Moving[2] = 0.8f;
		} else if (isFacing() == 21) { //Silver
			Ore = true;
			Moving[2] = 0.9f;
		} else if (isFacing() == 22 || isFacing() == 12) { // Copper
			Ore = true;
			Moving[2] = 0.9f;
		} else if (isFacing() == 23 || isFacing() == 11) { // Coal
			Ore = true;
			Moving[2] = 0.9f;
		} else if (isFacing() >= 70 || isFacing() == 3 || isFacing() <= 4) {
			Moving[2] = 0.2f;
		} else if (isFacing() == 95) {
			Moving[2] = 0.2f;
		} else {
			Moving[2] = 0.8f;
		}
		
		if (player.getLocation()[1] < player.MaxY) {
			if (Ore) {
				player.Gas -= 2;
				Common.print("Gas Lost 2" );
			} else {
				if (isFacing() == 95) {
					Common.print("Gas Lost " + 0.2f );
					player.Gas -= 0.2f;
				} else {
					player.Gas -= 1;
					Common.print("Gas Lost 1" );
				} 
			}
		}
		
		if (player.Gas <= -45 && player.getLocation()[1] < player.MaxY) {
			player.setLocation(tiledMap.getProperties().get("height", Integer.class)/2 -5, tiledMap.getProperties().get("height", Integer.class)-12);
			//player.clearinventory();
			player.Cash = player.Cash - (player.Cash/4);
			player.Gas += 30;
			AddMoneyFeedback("-" + (player.Cash /= 4), gsm.CurrentTime, 2.5f);
		}
	}
	
	public void MineTiles() {
		//Checks if the tile the player is on matches an ID
		if (isOn() == 21) {
			AddMoneyFeedback("+25", gsm.CurrentTime, 2.5f);
			//AddAchievement("Hey you just got Silver!", 20, gsm.CurrentTime, 1.5f, false);
			player.Cash += 25;
		}
		if (isOn() == 22 || isOn() == 12) {
			AddMoneyFeedback("+10", gsm.CurrentTime, 2.5f);
			//AddAchievement("Hey you just got Copper!", 21, gsm.CurrentTime, 1.5f, false);
			player.Cash += 10;
		}
		if (isOn() == 23 || isOn() == 11) {
			AddMoneyFeedback("+5", gsm.CurrentTime, 2.5f);
			//AddAchievement("Hey you just got Coal!", 22, gsm.CurrentTime, 1.5f, false);
			player.Cash += 5;
		}
		if (isOn() != 95) {
			//Common.print("Hey you just got a " + groundLay.getCell(player.getLocation()[0], player.getLocation()[1]).getTile().getId());
		}
		if (player.getLocation()[1] < tiledMap.getProperties().get("height", Integer.class)-12) {
			groundLay.getCell(Common.roundDown(player.getLocation()[0]), Common.roundDown(player.getLocation()[1])).setTile(tiledMap.getTileSets().getTile(95));	
		}
	}
	
	public void SaveGame() {
		gsm.Rwr.saveMap(tiledMap, gsm.ChosenSave, player);
		tiledMap = new AtlasTmxMapLoader().load("Saves/" + gsm.ChosenSave + "/map.tmx");
		gsm.Rwr.saveMap(tiledMap, gsm.ChosenSave, player);
		AddAchievement("Saved the game!", 54, gsm.CurrentTime, 1f, false);
		
	}
	
	//BELOW IS MENU CODE
	//SHOULD BE SOMEWHAT PORTABLE
	
	public void changeState(int state) {
		OldState = currentState;
		currentState = state;
	}
	
	public void changeHUDState(int state) {
		HUDOldState = HUDcurrentState;
		HUDcurrentState = state;
	}
	
	public void Back() {
		int state = OldState;
		OldState = currentState;
		currentState = state;
	}
	
	public void MenuInit() {
		
		cameraGui = new OrthographicCamera();
        cameraGui.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		//Menu 1 / Main window
		MainMenu.add(MainMenu.size(), new MenuState("Button", "Return to Main Menu")); //Button 4
		MainMenu.add(MainMenu.size(), new MenuState("Button", "Options")); //Button 3
		MainMenu.add(MainMenu.size(), new MenuState("Button", "Resume")); //Button 1
		
		//Menu 3 / Options
		AudioMenu.add(AudioMenu.size(), new MenuState("Button", "Back")); //Button 1
		AudioMenu.add(AudioMenu.size(), new MenuState("Slider", "SoundVolume"));
		AudioMenu.add(AudioMenu.size(), new MenuState("Slider", "MusicVolume"));
		AudioMenu.add(AudioMenu.size(), new MenuState("Slider", "MasterVolume"));
		
		//Menu 2 / Options
		Options.add(Options.size(), new MenuState("Button", "Back")); //Button 1
		Options.add(Options.size(), new MenuState("Button", "Controls")); //Button 2
		Options.add(Options.size(), new MenuState("Button", "Graphics")); //Button 3
		Options.add(Options.size(), new MenuState("Button", "Audio")); //Button 3
		
		
		InventoryHUD.add(InventoryHUD.size(), new MenuState("Button", "Exit"));
		InventoryHUD.add(InventoryHUD.size(), new MenuState("Button", "Inventory"));
	}
	
	public void MenuDraw(SpriteBatch guiBatch, int width, int height, float Time) {
		
		//Each if statement is another part of the menu
		if (currentState == 0) { 
			gsm.Render.MenuBackground(guiBatch, width, height, width, height, 0.55f);
			gsm.Render.drawGuiMenus(MainMenu, guiBatch, height, width, gsm);
		}
		if (currentState == 1) { 
			gsm.Render.MenuBackground(guiBatch, width, height, width, height, 0.55f);
			gsm.Render.drawGuiMenus(Options, guiBatch, height, width, gsm);
		}
		if (currentState == 2) { 
			gsm.Render.MenuBackground(guiBatch, width, height, width, height, 0.55f);
			gsm.Render.drawGuiMenus(AudioMenu, guiBatch, height, width, gsm);
		}
		
	}

	public void HUDDraw(SpriteBatch guiBatch, int width, int height, float Time) {
		
		if (HUDcurrentState == 5) { 
			gsm.Render.MenuBackground(guiBatch, width, height, width, height, 0.25f);
			gsm.Render.drawGuiMenus(InventoryHUD, guiBatch, height, width, gsm);
		}
	}
	
	public void MenuHandle() {
		
		if (HUDOpen) {
			
			if(HUDcurrentState == 5) { //INVENTORY
				for (int i = 0; i < InventoryHUD.size(); i++) {
					
					if (gsm.MouseClick[0] == 1) {
						if (InventoryHUD.get(i).getType().equals("Button") && gsm.Render.GUIButtonCheck(gsm.MouseClick, InventoryHUD.get(i).getData())) {
							gsm.Audio.play("Click");
							if (InventoryHUD.get(i).getString().equals("Inventory")) {
								
							}
							if (InventoryHUD.get(i).getString().equals("Exit")) {
								HUDOpen = false;
							}
							
						}
						if (InventoryHUD.get(i).getType().equals("CheckBox") && gsm.Render.GUICheckBoxCheck(gsm.MouseClick, InventoryHUD.get(i).getData())) {
							gsm.Audio.play("Click");
							InventoryHUD.get(i).SetBool(!InventoryHUD.get(i).GetBool());
						}
					}
				}	
			}
			
			
			
			
		}
		
		if (menuOpen) {
			if(currentState == 0) { //Runs all the button checks
				
				for (int i = 0; i < MainMenu.size(); i++) {
					if (gsm.MouseDrag[0] == 1) {
						if (MainMenu.get(i).getType().equals("Slider") && gsm.Render.GuiSliderCheck(gsm.MouseDrag, MainMenu.get(i).getData())) {
						}
						
					}
					if (gsm.MouseClick[0] == 1) {
						if (MainMenu.get(i).getType().equals("Button") && gsm.Render.GUIButtonCheck(gsm.MouseClick, MainMenu.get(i).getData())) {
							gsm.Audio.play("Click");
							if (MainMenu.get(i).getString().equals("Resume")) {
								menuOpen = !menuOpen;
							}
							if (MainMenu.get(i).getString().equals("Options")) {
								changeState(1);
							}
							if (MainMenu.get(i).getString().equals("Return to Main Menu")) {
								SaveGame();
								Common.print("return to menu");
								gsm.Audio.stopMusic("Wind");
								gsm.setState(GameStateManager.MENU);
							}
							
						}
						if (MainMenu.get(i).getType().equals("CheckBox") && gsm.Render.GUICheckBoxCheck(gsm.MouseClick, MainMenu.get(i).getData())) {
							gsm.Audio.play("Click");
							MainMenu.get(i).SetBool(!MainMenu.get(i).GetBool());
						}
					}
				}
			
			} else if(currentState == 1) { //Runs all the button checks in the SinglePlayer Menu
				
				for (int i = 0; i < Options.size(); i++) {
					if (gsm.MouseDrag[0] == 1) {
						if (Options.get(i).getType().equals("Slider") && gsm.Render.GuiSliderCheck(gsm.MouseDrag, Options.get(i).getData())) {
						}
					}
					if (gsm.MouseClick[0] == 1) {
						if (Options.get(i).getType().equals("Button") && gsm.Render.GUIButtonCheck(gsm.MouseClick, Options.get(i).getData())) {
							gsm.Audio.play("Click");
							if (Options.get(i).getString().equals("Audio")) {
								changeState(2);
							}
							if (Options.get(i).getString().equals("Graphics")) {
								
							}
							if (Options.get(i).getString().equals("Controls")) {
								
							}
							if (Options.get(i).getString().equals("Back")) {
								changeState(0);
							}
							
						}
						if (Options.get(i).getType().equals("CheckBox")  && gsm.Render.GUICheckBoxCheck(gsm.MouseClick, Options.get(i).getData())) {
							gsm.Audio.play("Click");
							Options.get(i).SetBool(!Options.get(i).GetBool());
						}
					}
				}
			} else if(currentState == 2) { //Runs all the button checks in the Audio Menu
				
				for (int i = 0; i < AudioMenu.size(); i++) {
					if (gsm.MouseDrag[0] == 1) {
						if (AudioMenu.get(i).getType().equals("Slider") && gsm.Render.GuiSliderCheck(gsm.MouseDrag, AudioMenu.get(i).getData())) {

							//Check if the slider is MasterVolume
							if (AudioMenu.get(i).getString().equals("MasterVolume")) {
								gsm.Audio.MasterVolume = gsm.Render.GuiSliderReading(gsm.MouseDrag, AudioMenu.get(i).getData());
							}
							if (AudioMenu.get(i).getString().equals("MusicVolume")) {
								gsm.Audio.MusicVolume = gsm.Render.GuiSliderReading(gsm.MouseDrag, AudioMenu.get(i).getData());
							}
							if (AudioMenu.get(i).getString().equals("SoundVolume")) {
								gsm.Audio.SoundVolume = gsm.Render.GuiSliderReading(gsm.MouseDrag, AudioMenu.get(i).getData());
							}
						}
					}
					if (gsm.MouseClick[0] == 1) {
						if (AudioMenu.get(i).getType().equals("Button") && gsm.Render.GUIButtonCheck(gsm.MouseClick, AudioMenu.get(i).getData())) {
							gsm.Audio.play("Click");
							if (AudioMenu.get(i).getString().equals("Back")) {
								Back();
							}
							
						}
						if (AudioMenu.get(i).getType().equals("CheckBox")  && gsm.Render.GUICheckBoxCheck(gsm.MouseClick, AudioMenu.get(i).getData())) {
							gsm.Audio.play("Click");
							AudioMenu.get(i).SetBool(!AudioMenu.get(i).GetBool());
						}
					}
				}
			}
		}
	}
	
	//Ends the Gui Shit
	
	public void reSize(SpriteBatch g, int H, int W) {
		float posX = camera.position.x;
		float posY = camera.position.y;
		float posZ = camera.position.z;
		camera.setToOrtho(false);
		camera.position.set(posX, posY, posZ);
		
		Matrix4 matrix = new Matrix4();
		matrix.setToOrtho2D(0, 0, W, H);
		guiBatch.setProjectionMatrix(matrix);
		cameraGui.setToOrtho(false);
	}
	
	public void IsKeyPressed(Keys Keys) {
		
	}
}