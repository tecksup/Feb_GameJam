// GameState that shows logo.

package com.thecubecast.ReEngine.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;
import com.thecubecast.ReEngine.Data.Common;
import com.thecubecast.ReEngine.Data.GameStateManager;


public class MainMenuState extends GameState {
	
	Music Click;
	Music Audio;
	long AudioId;

	//The Menu states
	private int OldState;
	private int currentState;
	
	public static final int Main = 0;
	public static final int Single = 1;
	public static final int Options = 2;
	public static final int GAMEOVER = 3;
	public static final int OPTIONS = 4;
	public static final int TEST = 5;
	
	
	//The buttons only run functions and contain drawing object, the Value variables hold and change data
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
	
	//The buttons state 2
	int[] button21 = null;
	int[] Slider22 = null;
	float Slider22Value = 0.4f;
	int[] button24 = null;
	int[] checkbox25 = null;
	boolean checkbox25Value = false;
	
	public MainMenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		//Grab data from preferences file, Set Slider22Value accordingly
		
		Music Click = Gdx.audio.newMusic(Gdx.files.internal("Music/click.wav"));
		
		Sound Audio = Gdx.audio.newSound(Gdx.files.internal("Music/The-8-Bit-Digger.wav"));
		
		//Music Audio = Gdx.audio.newMusic(Gdx.files.internal("Music/The-8-Bit-Digger.wav"));
		AudioId = Audio.play();
		//Audio.setVolume(Slider22Value);
		Audio.setVolume(AudioId, Slider22Value);
		//Click.play();
		Audio.setLooping(AudioId, true);
		
	}
	
	public void update() {
		handleInput();
		if ((100*Slider22Value) < 80) {
			//Audio.pause();
			//Audio.setVolume(Slider22Value);
			//Audio.play();
		}
		
		
	}
	
	public void changeState(int state) {
		OldState = currentState;
		currentState = state;
	}
	
	public void Back() {
		int state = OldState;
		OldState = currentState;
		currentState = state;
	}
	
	public void draw(SpriteBatch bbg, int width, int height, float Time) {
		
		//gsm.Render.DrawBackground(bbg, width, height);
		bbg.draw(gsm.Render.Images[03], 0, 0, width, height);
		
		//Each if statement is another part of the menu
		if (currentState == 0) { 
			//Common.print("Drawing first button at " + width/2 + " by " + (height/20)*9);
			button01 = gsm.Render.GUIButton(bbg, width/2, (height/20)*12, 5, true, "Singleplayer");
			button02 = gsm.Render.GUIButton(bbg, width/2, (height/20)*10, 5, true, "");
			button03 = gsm.Render.GUIButton(bbg, width/2, (height/20)*8, 5, true, "Options");
			button04 = gsm.Render.GUIButton(bbg, width/2, (height/20)*6, 5, true, "Quit");
			
			//The buttons state 1
			button11 = null;
			button12 = null;
			button13 = null;
			button14 = null;
			checkbox25 = null;
					
			//The buttons state 2
			button21 = null;
			Slider22 = null;
			button24 = null;
		} 		
		if (currentState == 1) { 
			button11 = gsm.Render.GUIButton(bbg, width/2, (height/20)*12, 5, true, "Save 1");
			button12 = gsm.Render.GUIButton(bbg, width/2, (height/20)*10, 5, true, "Save 2");
			button13 = gsm.Render.GUIButton(bbg, width/2, (height/20)*8, 5, true, "Save 3");
			button14 = gsm.Render.GUIButton(bbg, width/2, (height/20)*6, 5, true, "Back");
			
			//The buttons state 0
			button01 = null;
			button02 = null;
			button03 = null;
			button04 = null;
					
			//The buttons state 2
			button21 = null;
			Slider22 = null;
			button24 = null;
		}
		
		if (currentState == 2) { 
			button21 = gsm.Render.GUIButton(bbg, width/2, (height/20)*12, 5, true, "Setting1");
			gsm.Render.GUIDrawText(bbg, (width/2), Math.round((height/20)*11.5f), "" + (100*Slider22Value));
			Slider22 = gsm.Render.GUISlider(bbg, (width/2), (height/20)*10, 5, true, Slider22Value);
			button24 = gsm.Render.GUIButton(bbg, width/2, (height/20)*8, 5, true, "Back");
			checkbox25 = gsm.Render.GUICheckBox(bbg, width/2, (height/20)*6, checkbox25Value);
			
			//The buttons state 0
			button01 = null;
			button02 = null;
			button03 = null;
			button04 = null;
			
			//The buttons state 1
			button11 = null;
			button12 = null;
			button13 = null;
			button14 = null;
		}
		
		gsm.Render.DrawAny(bbg, 72, "Tiles", gsm.MouseX, gsm.MouseY);
	}
	
	public void handleInput() {
		
		if(gsm.MouseClick[0] == 1 && currentState == 0 && button01 != null) { //Runs all the button checks
			
			//Button 1 of Menu State 0
			if(gsm.MouseClick[1] >= button01[0] && gsm.MouseClick[1] <= button01[2]) {
				if(gsm.MouseClick[2] >= button01[1] && gsm.MouseClick[2] <= button01[3]) { //SinglePlayer
					//Click.play();
					changeState(1);
				}
			}
			
			//Button 2 of Menu State 0
			if(gsm.MouseClick[1] >= button02[0] && gsm.MouseClick[1] <= button02[2]) {
				if(gsm.MouseClick[2] >= button02[1] && gsm.MouseClick[2] <= button02[3]) { // Future Multiplayer Button, Currently Blank
					//Click.play();
					Common.print("Button 2 Was clicked!");
				}
			}
			
			//Button 3 of Menu State 0
			if(gsm.MouseClick[1] >= button03[0] && gsm.MouseClick[1] <= button03[2]) {
				if(gsm.MouseClick[2] >= button03[1] && gsm.MouseClick[2] <= button03[3]) { // Options
					//Click.play();
					changeState(2);
				}
			}
			
			//Button 4 of Menu State 0
			if(gsm.MouseClick[1] >= button04[0] && gsm.MouseClick[1] <= button04[2]) {
				if(gsm.MouseClick[2] >= button04[1] && gsm.MouseClick[2] <= button04[3]) { // QUIT/EXIT
					////Click.play();
					Common.print("Game Quit Button Pressed in menu!");
					Common.ProperShutdown();
				}
			}
		}
		
		
		if(gsm.MouseClick[0] == 1 && currentState == 1 && button11 != null) { //Runs all the button checks in the SinglePlayer Menu
			
			//Button 1 of Menu State 1
			if(gsm.MouseClick[1] >= button11[0] && gsm.MouseClick[1] <= button11[2]) {
				if(gsm.MouseClick[2] >= button11[1] && gsm.MouseClick[2] <= button11[3]) {  // Save1
					//Click.play();
					gsm.Rwr.CreateSave("Save1");
					gsm.ChosenSave = "Save1";
					//Audio.stop();
					gsm.setState(GameStateManager.PLAY);
				}
			}
			
			//Button 2 of Menu State 1
			if(gsm.MouseClick[1] >= button12[0] && gsm.MouseClick[1] <= button12[2]) {
				if(gsm.MouseClick[2] >= button12[1] && gsm.MouseClick[2] <= button12[3]) { // Save2
					//Click.play();
					gsm.Rwr.CreateSave("Save2");
					gsm.ChosenSave = "Save2";
					//Audio.stop();
					gsm.setState(GameStateManager.PLAY);
				}
			}
			
			//Button 3 of Menu State 1
			if(gsm.MouseClick[1] >= button13[0] && gsm.MouseClick[1] <= button13[2]) {
				if(gsm.MouseClick[2] >= button13[1] && gsm.MouseClick[2] <= button13[3]) { // Save3
					//Click.play();
					gsm.Rwr.CreateSave("Save3");
					gsm.ChosenSave = "Save3";
					//Audio.stop();
					gsm.setState(GameStateManager.PLAY);
				}
			}
			
			//Button 4 of Menu State 1
			if(gsm.MouseClick[1] >= button14[0] && gsm.MouseClick[1] <= button14[2]) {
				if(gsm.MouseClick[2] >= button14[1] && gsm.MouseClick[2] <= button14[3]) { // Back
					//Click.play();
					Back();
				}
			}
			
			
		}
		
		
		if(gsm.MouseDrag[0] == 1 && currentState == 2 && button21 != null) { //Runs all the button checks
			if(gsm.MouseDrag[1] >= Slider22[0] && gsm.MouseDrag[1] <= Slider22[2]) { // THIS IS THE SLIDER
				if(gsm.MouseDrag[2] >= Slider22[1] && gsm.MouseDrag[2] <= Slider22[3]) {
					//Click.play();
					float Slider22Valuetemp = ((float)(gsm.MouseDrag[1] - Slider22[0])/(Slider22[2] - Slider22[0]));
					Slider22Value =  Slider22Valuetemp;
					Common.print("Updated Value is " + Slider22Value);
				}
			}
		}
		if(gsm.MouseClick[0] == 1 && currentState == 2 && button21 != null) { //Runs all the button checks
					
			//Button 1 of Menu State 0
			if(gsm.MouseClick[1] >= button21[0] && gsm.MouseClick[1] <= button21[2]) {
				if(gsm.MouseClick[2] >= button21[1] && gsm.MouseClick[2] <= button21[3]) {  // The Placeholder Setting1
					//Click.play();
				}
			}
			
			//The slider code is moved up into a function that allows the dragging
			
			//Button 4 of Menu State 0
			if(gsm.MouseClick[1] >= button24[0] && gsm.MouseClick[1] <= button24[2]) {
				if(gsm.MouseClick[2] >= button24[1] && gsm.MouseClick[2] <= button24[3]) { // back
					//Click.play();
					Back();
				}
			}
			
			if(gsm.MouseClick[1] >= checkbox25[0] && gsm.MouseClick[1] <= checkbox25[2]) { // THE CHECK BOX
				if(gsm.MouseClick[2] >= checkbox25[1] && gsm.MouseClick[2] <= checkbox25[3]) {
					//Click.play();
					checkbox25Value = !checkbox25Value;
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