// GameState that shows logo.

package com.thecubecast.ReEngine.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;
import com.thecubecast.ReEngine.Data.Common;
import com.thecubecast.ReEngine.Data.GameStateManager;


public class MainMenuState extends GameState {
	
	private int TileSize = 2;
	
	Music Click;
	Music Audio;

	//The Menu states
	private int currentState;
	
	public static final int Main = 0;
	public static final int Single = 1;
	public static final int Options = 2;
	public static final int GAMEOVER = 3;
	public static final int OPTIONS = 4;
	public static final int TEST = 5;
	
	//The buttons state 0
	int[] button01 = null;
	int[] button02 = null;
	int[] button03 = null;
	int[] button04 = null;
	
	//The buttons state 1
	int[] button11 = null;
	int[] button12 = null;
	int[] button13 = null;
	int[] button14 = null;
	int[] checkbox15 = null;
	boolean checkbox15Value = false;
	
	//The buttons state 2
	int[] button21 = null;
	int[] Slider22 = null;
	float Slider22Value = 0.0f;
	int[] button24 = null;
	
	public MainMenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		Music Click = Gdx.audio.newMusic(Gdx.files.internal("Music/click.wav"));
		
		Music Audio = Gdx.audio.newMusic(Gdx.files.internal("Music/The-8-Bit-Digger.wav"));
		Audio.play();
		//Audio.setLooping(true);
		
	}
	
	public void update() {
		handleInput();
		if ((100*Slider22Value) < 80) {
			//Audio.setVolume( -(100*Slider22Value));
		}
		
	}
	
	public void draw(SpriteBatch bbg, int width, int height, float Time) {
		
		//gsm.Render.DrawBackground(bbg, width, height);
		bbg.draw(gsm.Render.Images[03], 0, 0, width, height);
		
		//Each if statement is another part of the menu
		if (currentState == 0) { 
			//Common.print("Drawing first button at " + width/2 + " by " + (height/20)*9);
			button01 = gsm.Render.GUIButton(bbg, width/2, (height/20)*9, 5, true, "Singleplayer");
			button02 = gsm.Render.GUIButton(bbg, width/2, (height/20)*10, 5, true, "");
			button03 = gsm.Render.GUIButton(bbg, width/2, (height/20)*11, 5, true, "Options");
			button04 = gsm.Render.GUIButton(bbg, width/2, (height/20)*12, 5, true, "Quit");
		}
		if (currentState == 1) { 
			button11 = gsm.Render.GUIButton(bbg, width/2, (height/20)*9, 5, true, "Save 1");
			button12 = gsm.Render.GUIButton(bbg, width/2, (height/20)*10, 5, true, "Save 2");
			button13 = gsm.Render.GUIButton(bbg, width/2, (height/20)*11, 5, true, "Save 3");
			button14 = gsm.Render.GUIButton(bbg, width/2, (height/20)*12, 5, true, "Back");
		}
		
		if (currentState == 2) { 
			button21 = gsm.Render.GUIButton(bbg, width/2, (height/20)*9, 5, true, "Setting1");
			//font.setColor(Color.WHITE);
		 //  font.draw(bbg, "Hello world", 25, 160);
			//bbg.drawString("" + (100*Slider22Value), (width/2), (height/20)*10);
			
			Slider22 = gsm.Render.GUISlider(bbg, (width/16)*7, (height/20)*11, 5, false, Slider22Value);
			//button22 = gsm.Render.GUIButton(bbg, (width/16)*7, (height/20)*11, 2, true, "Volume -");
			//button23 = gsm.Render.GUIButton(bbg, (width/16)*9, (height/20)*11, 2, true, "Volume +");
			button24 = gsm.Render.GUIButton(bbg, width/2, (height/20)*12, 5, true, "Back");
			checkbox15 = gsm.Render.GUICheckBox(bbg, width/2, (height/20)*5, checkbox15Value);
		}
		
		gsm.Render.DrawAny(bbg, 63, "Tiles", gsm.MouseX, gsm.MouseY);
	}
	
	public void handleInput() {
		
		if(gsm.MouseClick[0] == 1 && currentState == 0 && button01 != null) { //Runs all the button checks
			
			//Button 1 of Menu State 0
			if(gsm.MouseClick[1] >= button01[0] && gsm.MouseClick[1] <= button01[2]) {
				if(gsm.MouseClick[2] >= button01[1] && gsm.MouseClick[2] <= button01[3]) {
					//Click.play();
					currentState = 1;
				}
			}
			
			//Button 2 of Menu State 0
			if(gsm.MouseClick[1] >= button02[0] && gsm.MouseClick[1] <= button02[2]) {
				if(gsm.MouseClick[2] >= button02[1] && gsm.MouseClick[2] <= button02[3]) {
					//Click.play();
					Common.print("Button 2 Was clicked!");
				}
			}
			
			//Button 3 of Menu State 0
			if(gsm.MouseClick[1] >= button03[0] && gsm.MouseClick[1] <= button03[2]) {
				if(gsm.MouseClick[2] >= button03[1] && gsm.MouseClick[2] <= button03[3]) {
					//Click.play();
					currentState = 2;
				}
			}
			
			//Button 4 of Menu State 0
			if(gsm.MouseClick[1] >= button04[0] && gsm.MouseClick[1] <= button04[2]) {
				if(gsm.MouseClick[2] >= button04[1] && gsm.MouseClick[2] <= button04[3]) {
					////Click.play();
					Common.print("Game Quit Button Pressed in menu!");
					Common.ProperShutdown();
				}
			}
		}
		
		
		if(gsm.MouseClick[0] == 1 && currentState == 1 && button11 != null) { //Runs all the button checks
			
			//Button 1 of Menu State 1
			if(gsm.MouseClick[1] >= button11[0] && gsm.MouseClick[1] <= button11[2]) {
				if(gsm.MouseClick[2] >= button11[1] && gsm.MouseClick[2] <= button11[3]) {
					//Click.play();
					gsm.Rwr.CreateSave("Save1");
					gsm.ChosenSave = "Save1";
					//Audio.stop();
					gsm.setState(GameStateManager.PLAY);
				}
			}
			
			//Button 2 of Menu State 1
			if(gsm.MouseClick[1] >= button12[0] && gsm.MouseClick[1] <= button12[2]) {
				if(gsm.MouseClick[2] >= button12[1] && gsm.MouseClick[2] <= button12[3]) {
					//Click.play();
					gsm.Rwr.CreateSave("Save2");
					gsm.ChosenSave = "Save2";
					//Audio.stop();
					gsm.setState(GameStateManager.PLAY);
				}
			}
			
			//Button 3 of Menu State 1
			if(gsm.MouseClick[1] >= button13[0] && gsm.MouseClick[1] <= button13[2]) {
				if(gsm.MouseClick[2] >= button13[1] && gsm.MouseClick[2] <= button13[3]) {
					//Click.play();
					gsm.Rwr.CreateSave("Save3");
					gsm.ChosenSave = "Save3";
					//Audio.stop();
					gsm.setState(GameStateManager.PLAY);
				}
			}
			
			//Button 4 of Menu State 1
			if(gsm.MouseClick[1] >= button14[0] && gsm.MouseClick[1] <= button14[2]) {
				if(gsm.MouseClick[2] >= button14[1] && gsm.MouseClick[2] <= button14[3]) {
					//Click.play();
					currentState = 0;
				}
			}
			
			
		}
		
		if(gsm.MouseClick[0] == 1 && currentState == 2 && button21 != null) { //Runs all the button checks
					
			//Button 1 of Menu State 0
			if(gsm.MouseClick[1] >= button21[0] && gsm.MouseClick[1] <= button21[2]) {
				if(gsm.MouseClick[2] >= button21[1] && gsm.MouseClick[2] <= button21[3]) {
					//Click.play();
				}
			}
			
			if(gsm.MouseDrag[1] >= Slider22[0] && gsm.MouseDrag[1] <= Slider22[2]) { // THIS IS THE SLIDER
				if(gsm.MouseDrag[2] >= Slider22[1] && gsm.MouseDrag[2] <= Slider22[3]) {
					//Click.play();
					float Slider22Valuetemp = ((float)(gsm.MouseDrag[1] - Slider22[0])/(Slider22[2] - Slider22[0]));
					Slider22Value =  Slider22Valuetemp;
				}
			}
			
			//Button 4 of Menu State 0
			if(gsm.MouseClick[1] >= button24[0] && gsm.MouseClick[1] <= button24[2]) {
				if(gsm.MouseClick[2] >= button24[1] && gsm.MouseClick[2] <= button24[3]) {
					//Click.play();
					currentState = 0;
				}
			}
			
			if(gsm.MouseClick[1] >= checkbox15[0] && gsm.MouseClick[1] <= checkbox15[2]) { // THE CHECK BOX
				if(gsm.MouseClick[2] >= checkbox15[1] && gsm.MouseClick[2] <= checkbox15[3]) {
					//Click.play();
					checkbox15Value = !checkbox15Value;
				}
			}
			
			
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			//JukeBox.stop("MenuNavigate");
			//Click.play();
			//Check what button the user is on, runs its function
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.NUM_0)) {
			//JukeBox.stop("MenuNavigate");
			currentState = 0;
			//Moves the Chosen button UP
		}
		if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
			//JukeBox.stop("MenuNavigate");
			currentState = 1;
			//Moves the Chosen button DOWN
		}
		if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
			//JukeBox.stop("MenuNavigate");

			currentState = 2;
			
			//Moves the Chosen button LEFT
		}
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			//JukeBox.stop("MenuNavigate");
			
			//Moves the Chosen button RIGHT
		}
		
	}
}