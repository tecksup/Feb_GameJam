package com.thecubecast.ReEngine.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thecubecast.ReEngine.Data.Common;

import java.awt.*;
import java.io.File;

public class Draw {
	
	//Animation Variables
	public Animation<TextureRegion> LoadingAnimation; // Must declare frame type (TextureRegion)
	Texture LoadingSheet;
	
	public Animation<TextureRegion> StarsAnimation; // Must declare frame type (TextureRegion)
	Texture StarsSheet;
	
	//Always set to 1 above the number of spites in file
	public Texture[] Tiles = new Texture[74];
	public Texture[] GUI = new Texture[12];
	public Texture[] Images = new Texture[4];
	
	BitmapFont font = new BitmapFont();
	
	public void Init() {
		
		// Initialize the Animation with the frame interval and array of frames
		LoadingAnimation = new Animation<TextureRegion>(0.1f, loadAnim(LoadingSheet, "cube_loading_sprite.png", 4, 1));
		StarsAnimation = new Animation<TextureRegion>(0.1f, loadAnim(StarsSheet, "Stars.png", 3, 1));
		
	}
	
	public void Load() {
		//The loops bellow grab the tiles and add them to the variable
		for(int i=0; i < Tiles.length; ++i){
			if (i >= 10) {
				try {
					Common.print("Loaded images /Sprites/"+ Integer.toString(i) +".png");
					Tiles[i] = new Texture("Sprites/megaminer_"+ Integer.toString(i) +".png");
				}
				catch(Exception e) {
					//e.printStackTrace();
				}
			} else {
				try {
					Common.print("Loaded images /Sprites/0"+ Integer.toString(i) +".png");
					Tiles[i] = new Texture("Sprites/megaminer_0"+ Integer.toString(i) +".png");
				}
				catch(Exception e) {
					//e.printStackTrace();
				}
			}
        }
		for(int i=0; i < Images.length; ++i){
			if (i >= 10) {
				try {
					Common.print("Loaded images /Images/"+ Integer.toString(i) +".png");
					Images[i] = new Texture("Images/image_"+ Integer.toString(i) +".png");
				}
				catch(Exception e) {
					//e.printStackTrace();
				}
			} else {
				try {
					Common.print("Loaded images /Images/0"+ Integer.toString(i) +".png");
					Images[i] = new Texture("Images/image_0"+ Integer.toString(i) +".png");
				}
				catch(Exception e) {
					//e.printStackTrace();
				}
			}
        }
		for(int i=0; i < GUI.length; ++i){
			if (i >= 10) {
				try {
					Common.print("Loaded GUI images Sprites/GUI/GUI_"+ Integer.toString(i) +".png");
					GUI[i] = new Texture("Sprites/GUI/GUI_"+ Integer.toString(i) +".png");
				}
				catch(Exception e) {
					//e.printStackTrace();
				}
			} else {
				try {
					Common.print("Loaded GUI images Sprites/GUI/GUI_0"+ Integer.toString(i) +".png");
					GUI[i] = new Texture("Sprites/GUI/GUI_0"+ Integer.toString(i) +".png");
				}
				catch(Exception e) {
					//e.printStackTrace();
				}
			}
        }

		try {
			GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Munro.ttf")));
		}
		catch(Exception e) {
			//e.printStackTrace();
		}
	}
	
	private TextureRegion[] loadAnim(Texture TexSheet, String FileLocation, int Cols, int Rows) {
		// Load the sprite sheet as a Texture
		TexSheet = new Texture(Gdx.files.internal(FileLocation));

		// Use the split utility method to create a 2D array of TextureRegions. This is 
		// possible because this sprite sheet contains frames of equal size and they are 
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(TexSheet, 
				TexSheet.getWidth() / Cols,
				TexSheet.getHeight() / Rows);

		// Place the regions into a 1D array in the correct order, starting from the top 
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] walkFrames = new TextureRegion[Cols * Rows];
		int index = 0;
		for (int i = 0; i < Rows; i++) {
			for (int j = 0; j < Cols; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		return walkFrames;
	}
	
	public void DrawAnimatedTile(SpriteBatch buffer, Animation<TextureRegion> animation_, int x, int y, float SizeX, float SizeY, float stateTime) {
		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = animation_.getKeyFrame(stateTime, true);
		
		buffer.draw(currentFrame, x, y, SizeX, SizeY);
	}
	
	public void DrawSplash(SpriteBatch buffer, int ID, int x, int y, float x2, float y2, boolean centered) { // The x2 and y2 is Percentage of 100
			int SizeX = Images[ID].getWidth();
			int SizeY = Images[ID].getHeight();
		
			if (centered) {
				buffer.draw(Images[ID], x-((SizeX * x2)/2), y-((SizeY * y2)/2), SizeX * x2, SizeY * y2);	
			}
			else {
				buffer.draw(Images[ID], x, y, SizeX * x2, SizeY * y2);	
			}
			
	}
	
	public void DrawAny(SpriteBatch buffer, int ID, String Type, int x, int y) {
		if(Type.equals("Tiles")) {
			buffer.draw(Tiles[ID], x, y);
		}
		if(Type.equals("Images")) {
			buffer.draw(Images[ID], x, y);	
		}
		if(Type.equals("Gui")) {
			buffer.draw(GUI[ID], x, y);	
		}
	}
	
	public void DrawBackground(SpriteBatch buffer, int x, int y) {
		//Function is responsible for drawing the backgrounds, behind the tiles
		buffer.draw(Tiles[07], 0, 0, x, y);
	}
	
	public void DrawChunkDebugLines(SpriteBatch buffer, int x, int y , int TileSize, int cameraX, int cameraY) {
		//buffer.drawLine(0-cameraX, 0-cameraY, (TileSize*16)-cameraX, 0-cameraY);
		//buffer.drawLine(0-cameraX, 0- cameraY, 0-cameraX, (TileSize*16)-cameraY);
		//buffer.drawLine((TileSize*16)-cameraX, 0-cameraY, (TileSize*16)-cameraX, ((TileSize*16))-cameraY);
		//buffer.drawLine(0-cameraX, ((TileSize*16))-cameraY, (TileSize*16)-cameraX, ((TileSize*16))-cameraY);
	}
	
	//Renders the tiles across the world
	public void DrawTiles(SpriteBatch buffer, int OffsetX, int OffsetY, int TileSize, int WorldSize) {
	//Function is for drawing the main tiles
		
		
		
		for(int i=0; i < 60; ++i){  //draws the top layer of grass
			buffer.draw(Tiles[01], i*40 - OffsetX, 20 - OffsetY, TileSize, TileSize);
		}
		for(int i=0; i < 260; ++i){  //draws the dirt
			if (i < 60) {
				buffer.draw(Tiles[00], i*40 - OffsetX, 60 - OffsetY, TileSize, TileSize);
			}
			else if (i >= 60 && i < 120){
				buffer.draw(Tiles[00], (i - 60)*40 - OffsetX, 100 - OffsetY, TileSize, TileSize);
			}
			else if (i >= 120 && i < 200){ 
				buffer.draw(Tiles[00], (i - 120)*40 - OffsetX, 140 - OffsetY, TileSize, TileSize);
			}
			else if (i >= 200 && i < 260){ 
				buffer.draw(Tiles[00], (i - 200)*40 - OffsetX, 180 - OffsetY, TileSize, TileSize);
			}
		}
	}
	
	public void DrawTilesForeground(SpriteBatch buffer, int OffsetX, int OffsetY, int TileSize, int WorldSize) {
		//Function is for drawing the tiles that go in front of the player layer wise
	}
	
	//This will handle the animations as well
	public void Player(SpriteBatch buffer, int PosX, int PosY, String direction) {
		if (direction.equals("up")) { //UP
			//Common.print("player moved up");
			buffer.draw(Tiles[53], PosX, PosY);
			//Common.print("player drawn at x:" + PosX + " and y:" + PosY + " at sizes " + Sizex + " " + Sizey + " .");
		}
		if (direction.equals("left")) {
			//Common.print("player moved left");
			buffer.draw(Tiles[55], PosX, PosY);
		}
		if (direction.equals("down")) {
			//Common.print("player moved down");
			buffer.draw(Tiles[54], PosX, PosY);
		}
		if (direction.equals("right")) {
			//Common.print("player moved right");
			buffer.draw(Tiles[56], PosX, PosY);
		}
	}
	
	public void GUIDrawText(SpriteBatch buffer, int PosX, int PosY, String Text) {
		font.draw(buffer, Text, PosX , PosY);
	}
	
	//The GUI or Menu would go here.
	public void GUIDeco(SpriteBatch buffer, int PosX, int PosY, String Text) {
		buffer.draw(GUI[00], PosX, PosY);
		buffer.draw(GUI[01], PosX + Tiles[59].getWidth(), PosY);
		buffer.draw(GUI[01], PosX + (Tiles[59].getWidth()*2), PosY);
		buffer.draw(GUI[01], PosX + (Tiles[59].getWidth()*3), PosY);
		buffer.draw(GUI[01], PosX + (Tiles[59].getWidth()*4), PosY);
		buffer.draw(GUI[02], PosX + (Tiles[59].getWidth()*5), PosY);
		font.draw(buffer, "testing GUI - " + Text, PosX + Tiles[59].getWidth(), PosY + (Tiles[59].getHeight()/2));
	}
	
	public int[] GUIButton(SpriteBatch buffer, int PosX, int PosY, int length, boolean center, String text) {
		if (center) {
			PosX = PosX - ((GUI[00].getWidth()*length)/2);
		}
		buffer.draw(GUI[00], PosX, PosY);
		for (int i=1; i < (length); i++) {
			buffer.draw(GUI[01], PosX + (GUI[01].getWidth()*i), PosY);
		}
		buffer.draw(GUI[02], PosX + (GUI[02].getWidth()*length), PosY);
		font.draw(buffer, text, PosX+((GUI[00].getWidth()*length)/2), PosY + (GUI[00].getHeight()/2));
		
		int[] size = new int[] {PosX, PosY, PosX+(GUI[00].getWidth()*length), PosY+(GUI[00].getHeight())};
		return size;
	}
	
	public int[] GUISlider(SpriteBatch buffer, int PosX, int PosY, int length, boolean center, float SliderValue, String label, float Value) {
		if (center) {
			PosX = PosX - ((GUI[03].getWidth()*length)/2);
		}
		buffer.draw(GUI[03], PosX, PosY);
		for (int i=1; i < (length + 1); i++) {
			buffer.draw(GUI[04], PosX + (GUI[04].getWidth()*i), PosY);
		}
		buffer.draw(GUI[05], PosX + (GUI[04].getWidth()*length), PosY);
		//Draws the Dot
		buffer.draw(GUI[06], PosX + Math.round((((GUI[05].getWidth()*(length)))*SliderValue)), PosY);		
		
		font.draw(buffer, label + ": " + Value, PosX, PosY + (70));
		
		int[] size = new int[] {PosX, PosY - (GUI[05].getHeight()/4), PosX+(GUI[05].getWidth()*(length + 1)), PosY+(GUI[05].getHeight())}; 
		return size;
	}
	
	public int[] GUICheckBox(SpriteBatch buffer, int PosX, int PosY, boolean Checked) {
		if (Checked) {
			buffer.draw(GUI[11], PosX, PosY);
		}
		else {
			buffer.draw(GUI[10], PosX, PosY);	
		}
		
		int[] size = new int[] {PosX, PosY, PosX+(GUI[10].getWidth()), PosY+(GUI[10].getHeight())};
		return size;
	}
	
	public void HUDAchievement(SpriteBatch buffer, int PosX, int PosY, String text, int iconID, float Opacity, boolean Anim, float Time) {
		buffer.draw(GUI[00], PosX, PosY);
		buffer.draw(GUI[01], PosX + Tiles[59].getWidth(), PosY);
		buffer.draw(GUI[02], PosX + (Tiles[59].getWidth()*2), PosY);
		if (Anim) {
			if (iconID == 4 || iconID == 5 || iconID == 6) {
				DrawAnimatedTile(buffer, StarsAnimation, PosX + (Tiles[59].getWidth()*2) + (Tiles[59].getWidth()/2), PosY + (Tiles[59].getWidth()/2)-(Tiles[59].getWidth()/8), Tiles[iconID].getWidth()/4, Tiles[iconID].getWidth()/4, Time);
			} else {
				Common.print("Not Configured To Animate");
			}
		} else {
			buffer.draw(Tiles[iconID], PosX + (Tiles[59].getWidth()*2) + (Tiles[59].getWidth()/2), PosY + (Tiles[59].getWidth()/2)-(Tiles[59].getWidth()/8), Tiles[iconID].getWidth()/4, Tiles[iconID].getHeight()/4);
		}
		//buffer.draw(Tiles[iconID], PosX, PosY);
		font.draw(buffer, text, PosX + 20, PosY + (Tiles[59].getHeight()/2)+5);
	}
	
	public void MoneyFeedback(SpriteBatch buffer, int PosX, int PosY, String text, float DeltaTime) {
		font.setColor(Color.YELLOW);
		//font.setColor(0, 1, 1, 0.1f);
		font.draw(buffer, text, PosX + 20, PosY + 20 + (DeltaTime/1.5f));
		font.setColor(Color.WHITE);
	}
	
	public void HUDPopup(SpriteBatch buffer, int PosX, int PosY, String text) {
		
	}

	public void HUDDescr(SpriteBatch buffer, int PosX, int PosY, String text) {
		font.draw(buffer, text, PosX, PosY);
	}
}