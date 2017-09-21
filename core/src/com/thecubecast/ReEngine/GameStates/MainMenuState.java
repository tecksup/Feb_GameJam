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
	
	Sound Click;
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
	//The buttons state 0     MAIN MENU
	int[] button01 = null;
	int[] button02 = null;
	int[] button03 = null;
	int[] button04 = null;
	
	//The buttons state 1     SINGLEPLAYER MENU
	int[] button11 = null;
	int[] button12 = null;
	int[] button13 = null;
	int[] button14 = null;
	
	//The buttons state 2      AUDIO MENU
	int[] Slider21 = null;
	int[] Slider22 = null;
	int[] Slider23 = null;
	float MasterVolume = 1.0f;
	float MusicVolume = 0.4f;
	float SoundVolume = 0.8f;
	
	int[] button24 = null;
	int[] checkbox25 = null;
	boolean checkbox25Value = false;
	
	//the buttons state 3      OPTIONS MENU
	int[] button31 = null; // Opens the Audio menu
	int[] button32 = null; // Opens the Video menu
	int[] button33 = null; // Blank for another menu Controls?
	int[] button34 = null; // BACK BUTTON
	
	//The buttons state 4      VIDEO MENU
	int[] checkbox41 = null;
	boolean checkbox41Value = false;
	
	public MainMenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		//Grab data from preferences file, Set Slider22Value accordingly
		
		Click = Gdx.audio.newSound(Gdx.files.internal("Music/click.wav"));
		
		Audio = Gdx.audio.newMusic(Gdx.files.internal("Music/The-8-Bit-Digger.wav"));

		//Music Audio = Gdx.audio.newMusic(Gdx.files.internal("Music/The-8-Bit-Digger.wav"));
		Audio.play();
		//Audio.setVolume(Slider22Value);
		Audio.setVolume(MusicVolume * MasterVolume);
		Audio.setLooping(true);
		
	}
	
	public void update() {
		handleInput();
		if (Audio.getVolume() != (MusicVolume * MasterVolume)) {
			Audio.setVolume(MusicVolume * MasterVolume);
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
			
			
			NullDrawingGUI(1);//The buttons state 1
			
			NullDrawingGUI(2);//The buttons state 2
			
			NullDrawingGUI(3);//The buttons state 3
		} 		
		if (currentState == 1) { 
			button11 = gsm.Render.GUIButton(bbg, width/2, (height/20)*12, 5, true, "Save 1");
			button12 = gsm.Render.GUIButton(bbg, width/2, (height/20)*10, 5, true, "Save 2");
			button13 = gsm.Render.GUIButton(bbg, width/2, (height/20)*8, 5, true, "Save 3");
			button14 = gsm.Render.GUIButton(bbg, width/2, (height/20)*6, 5, true, "Back");
			
			
			NullDrawingGUI(0);//The buttons state 0
			
			NullDrawingGUI(2);//The buttons state 2
			
			NullDrawingGUI(3);//The buttons state 3
		}
		
		if (currentState == 2) { 
			Slider21 = gsm.Render.GUISlider(bbg, (width/2), (height/20)*12, 5, true, MasterVolume, "Master Volume", MasterVolume*100);
			Slider22 = gsm.Render.GUISlider(bbg, (width/2), (height/20)*10, 5, true, MusicVolume, "Music", MusicVolume*100);
			Slider23 = gsm.Render.GUISlider(bbg, (width/2), (height/20)*8, 5, true, SoundVolume, "Sound Effects", SoundVolume*100);
			button24 = gsm.Render.GUIButton(bbg, width/2, (height/20)*6, 5, true, "Back");
			checkbox25 = gsm.Render.GUICheckBox(bbg, width/2, (height/20)*4, checkbox25Value);
			
			NullDrawingGUI(0);//The buttons state 0
			
			NullDrawingGUI(1);//The buttons state 1
			
			NullDrawingGUI(3);//The buttons state 3
		}
		
		if (currentState == 3) { 
			button31 = gsm.Render.GUIButton(bbg, width/2, (height/20)*12, 5, true, "Audio Settings");
			button32 = gsm.Render.GUIButton(bbg, width/2, (height/20)*10, 5, true, "Video Settings");
			button33 = gsm.Render.GUIButton(bbg, width/2, (height/20)*8, 5, true, "Controls");
			button34 = gsm.Render.GUIButton(bbg, width/2, (height/20)*6, 5, true, "Back");
			
			NullDrawingGUI(0);//The buttons state 0
			
			NullDrawingGUI(1);//The buttons state 1
			
			NullDrawingGUI(2);//The buttons state 2
		}
		
		
		gsm.Render.DrawAny(bbg, 72, "Tiles", gsm.MouseX, gsm.MouseY);
	}
	
	public void handleInput() {
		
		if(gsm.MouseClick[0] == 1 && currentState == 0 && button01 != null) { //Runs all the button checks
			
			//Button 1 of Menu State 0
			if (GUIButtonCheck(gsm.MouseClick, button01)) { // Singleplayer
				Click.play((SoundVolume * MasterVolume),1,0);
				changeState(1);
			}
			
			//Button 2 of Menu State 0
			if (GUIButtonCheck(gsm.MouseClick, button02)) { // Future Multiplayer Button, Currently Blank
				Click.play((SoundVolume * MasterVolume),1,0);
				Common.print("Button 2 Was clicked!");
			}
			
			//Button 3 of Menu State 0
			if (GUIButtonCheck(gsm.MouseClick, button03)) { // Options
				Click.play((SoundVolume * MasterVolume),1,0);
				changeState(3);
			}
			
			//Button 4 of Menu State 0
			if (GUIButtonCheck(gsm.MouseClick, button04)) {  // EXIT/QUIT
				//Click.play((SoundVolume * MasterVolume),1,0);
				Common.print("Game Quit Button Pressed in menu!");
				Common.ProperShutdown();
			}
		}
		
		
		if(gsm.MouseClick[0] == 1 && currentState == 1 && button11 != null) { //Runs all the button checks in the SinglePlayer Menu
			
			//Button 1 of Menu State 1
			if (GUIButtonCheck(gsm.MouseClick, button11)) {  //SAVE 1
				Click.play((SoundVolume * MasterVolume),1,0);
				gsm.Rwr.CreateSave("Save1");
				gsm.ChosenSave = "Save1";
				//Audio.stop();
				gsm.setState(GameStateManager.PLAY);
			}
			
			//Button 2 of Menu State 1
			if (GUIButtonCheck(gsm.MouseClick, button12)) {  //SAVE 2
				Click.play((SoundVolume * MasterVolume),1,0);
				gsm.Rwr.CreateSave("Save2");
				gsm.ChosenSave = "Save2";
				//Audio.stop();
				gsm.setState(GameStateManager.PLAY);
			}
			
			//Button 3 of Menu State 1
			if (GUIButtonCheck(gsm.MouseClick, button13)) {  //SAVE 3
				Click.play((SoundVolume * MasterVolume),1,0);
				gsm.Rwr.CreateSave("Save3");
				gsm.ChosenSave = "Save3";
				//Audio.stop();
				gsm.setState(GameStateManager.PLAY);
			}
			
			//Button 4 of Menu State 1
			if (GUIButtonCheck(gsm.MouseClick, button14)) { //BACK
				Click.play((SoundVolume * MasterVolume),1,0);
				Back();
			}
			
			
		}
		
		
		if(gsm.MouseDrag[0] == 1 && currentState == 2 && Slider21 != null) { //Runs all the button checks
			if(gsm.MouseDrag[1] >= Slider21[0] && gsm.MouseDrag[1] <= Slider21[2]) { // THIS IS THE MasterVolume SLIDER
				if(gsm.MouseDrag[2] >= Slider21[1] && gsm.MouseDrag[2] <= Slider21[3]) {
					float SliderValuetemp = ((float)(gsm.MouseDrag[1] - Slider21[0])/(Slider21[2] - Slider21[0]));
					MasterVolume =  SliderValuetemp;
					//Common.print("Updated Value is " + Slider22Value);
				}
			}
			if(gsm.MouseDrag[1] >= Slider22[0] && gsm.MouseDrag[1] <= Slider22[2]) { // THIS IS THE MasterVolume SLIDER
				if(gsm.MouseDrag[2] >= Slider22[1] && gsm.MouseDrag[2] <= Slider22[3]) {
					float SliderValuetemp = ((float)(gsm.MouseDrag[1] - Slider22[0])/(Slider22[2] - Slider22[0]));
					MusicVolume =  SliderValuetemp;
					//Common.print("Updated Value is " + Slider22Value);
				}
			}
			if(gsm.MouseDrag[1] >= Slider23[0] && gsm.MouseDrag[1] <= Slider23[2]) { // THIS IS THE MusicVolume SLIDER
				if(gsm.MouseDrag[2] >= Slider23[1] && gsm.MouseDrag[2] <= Slider23[3]) {
					float SliderValuetemp = ((float)(gsm.MouseDrag[1] - Slider23[0])/(Slider23[2] - Slider23[0]));
					SoundVolume =  SliderValuetemp;
					//Common.print("Updated Value is " + Slider22Value);
				}
			}
		}
		if(gsm.MouseClick[0] == 1 && currentState == 2 && button24 != null) { //Runs all the button checks

			//The slider21 and slider22 and slider23 code was moved up into a function that allows the dragging
			
			//Button 4 of Menu State 2
			if (GUIButtonCheck(gsm.MouseClick, button24)) {
				Click.play((SoundVolume * MasterVolume),1,0);
				Back();
			}
			
			//Checkbox 5 of Menu State 2
			if(GUICheckBoxCheck(gsm.MouseClick, checkbox25)) {
				Click.play((SoundVolume * MasterVolume),1,0);
				checkbox25Value = !checkbox25Value;
				if (checkbox25Value) {
					Audio.pause();
					Common.print("Audio Paused");
				} else {
					Audio.play();
				}
			}
		}
		
		if(gsm.MouseClick[0] == 1 && currentState == 3 && button31 != null) { //Runs all the button checks
			
			//Button 1 of Menu State 3
			if(GUIButtonCheck(gsm.MouseClick, button31)) { //Audio Settings
				Click.play((SoundVolume * MasterVolume),1,0);
				changeState(2);
			}
			
			//Button 2 of Menu State 3
			if(GUIButtonCheck(gsm.MouseClick, button32)) { //Video Settings
				Click.play((SoundVolume * MasterVolume),1,0);
				//changeState(4);
			}
			
			//Button 3 of Menu State 3
			if(GUIButtonCheck(gsm.MouseClick, button33)) { //Blank Maybe Controls?
				Click.play((SoundVolume * MasterVolume),1,0);
			}
			
			//Button 4 of Menu State 3
			if(GUIButtonCheck(gsm.MouseClick, button34)) { //Blank Maybe Controls?
				//Click.play((SoundVolume * MasterVolume),1,0);
				changeState(0);
			}
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			//JukeBox.stop("MenuNavigate");
			Click.play((SoundVolume * MasterVolume),1,0);
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
	
	public void NullDrawingGUI(int State) {
		if(State == 0) {
			button01 = null;
			button02 = null;
			button03 = null;
			button04 = null;
		}
		if(State == 1) {
			button11 = null;
			button12 = null;
			button13 = null;
			button14 = null;
		}
		if(State == 2) {
			Slider21 = null;
			Slider22 = null;
			Slider23 = null;
			button24 = null;
			checkbox25 = null;
		}
		if(State == 3) {
			button31 = null;
			button32 = null;
			button33 = null;
			button34 = null;
		}
		if(State == 4) {
			checkbox41 = null;
		}
	}
	
	public boolean GUIButtonCheck(int[] mouse, int[] button) {
		if(mouse[1] >= button[0] && mouse[1] <= button[2]) {
			if(mouse[2] >= button[1] && mouse[2] <= button[3]) { //Audio Settings
				return true;
			}
		}
		return false;
	}
	
	public boolean GUICheckBoxCheck(int[] mouse, int[] checkbox) {
		if(mouse[1] >= checkbox[0] && mouse[1] <= checkbox[2]) { // THE CHECK BOX
			if(mouse[2] >= checkbox[1] && mouse[2] <= checkbox[3]) {
				return true;
			}
		}
		return false;
	}
	
}