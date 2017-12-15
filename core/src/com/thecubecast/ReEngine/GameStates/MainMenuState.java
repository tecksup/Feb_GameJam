// GameState that shows Main Menu.

package com.thecubecast.ReEngine.GameStates;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Input.Keys;
import com.thecubecast.ReEngine.Data.Common;
import com.thecubecast.ReEngine.Data.GameStateManager;
import com.thecubecast.ReEngine.Graphics.MenuState;


public class MainMenuState extends GameState {
	
	OrthographicCamera cameraGui;

	//The Menu states
	private int OldState;
	private int currentState;
	
	//The buttons only run functions and contain drawing object, the Value variables hold and change data
	//The buttons state 0     MAIN MENU
	List<MenuState> MainMenu = new ArrayList<MenuState>();
	List<MenuState> AudioMenu = new ArrayList<MenuState>();
	List<MenuState> Options = new ArrayList<MenuState>();
	List<MenuState> SinglePlayer = new ArrayList<MenuState>();
	
	public MainMenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		MenuInit();
		
		cameraGui = new OrthographicCamera();
		cameraGui.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		gsm.Audio.playMusic("8-bit-Digger", true);

		if(AudioMenu.get(4).GetBool()) {
			gsm.Audio.pauseMusic("8-bit-Digger");
		}
		
	}
	
	public void update() {
		handleInput();
		
		//if (AudioMenu.get(4).GetBool() && gsm.Audio.isPlaying("8-bit-Digger")) {
		//	gsm.Audio.pauseMusic("8-bit-Digger");
		//	Common.print("Audio Paused");
		//} else if (gsm.Audio.isPlaying("8-bit-Digger") != true){
		//	gsm.Audio.playMusic("8-bit-Digger", true);
		//}
	}
	
	public void draw(SpriteBatch bbg, int width, int height, float Time) {
		bbg.begin();
		bbg.setProjectionMatrix(cameraGui.combined);
		
		//gsm.Render.DrawBackground(bbg, width, height);
		bbg.draw(gsm.Render.Images[03], 0, 0, width, height);
		
		MenuDraw(bbg, width, height, Time);
		
		bbg.end();
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
		
		MenuHandle();
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			//JukeBox.stop("MenuNavigate");
			//Click.play((SoundVolume * MasterVolume),1,0);
			//Check what button the user is on, runs its function
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			//JukeBox.stop("MenuNavigate");
			
			//Moves the Chosen button RIGHT
		}
		
	}
	
	public void reSize(SpriteBatch g, int H, int W) {
		cameraGui.setToOrtho(false);
	}
	
	//BELOW IS MENU CODE
	//SHOULD BE SOMEWHAT PORTABLE
	
	public void changeState(int state) {
		OldState = currentState;
		currentState = state;
	}
	
	public void Back() {
		int state = OldState;
		OldState = currentState;
		currentState = state;
	}
	
	public void MenuInit() {
		//Menu 1 / Main window
		MainMenu.add(MainMenu.size(), new MenuState("Button", "Quit")); //Button 4
		MainMenu.add(MainMenu.size(), new MenuState("Button", "Options")); //Button 3
		MainMenu.add(MainMenu.size(), new MenuState("Button", "")); //Button 2
		MainMenu.add(MainMenu.size(), new MenuState("Button", "Singleplayer")); //Button 1
		
		//Menu 3 / Options
		AudioMenu.add(AudioMenu.size(), new MenuState("Button", "Back")); //Button 1
		AudioMenu.add(AudioMenu.size(), new MenuState("Slider", "SoundVolume"));
		AudioMenu.add(AudioMenu.size(), new MenuState("Slider", "MusicVolume"));
		AudioMenu.add(AudioMenu.size(), new MenuState("Slider", "MasterVolume"));
		AudioMenu.add(AudioMenu.size(), new MenuState("CheckBox", "Muted")); //CheckBox
			AudioMenu.get(AudioMenu.size()-1).SetBool(true);			// Set it to false
		
		//Menu 2 / Options
		Options.add(Options.size(), new MenuState("Button", "Back")); //Button 1
		Options.add(Options.size(), new MenuState("Button", "Controls")); //Button 2
		Options.add(Options.size(), new MenuState("Button", "Graphics")); //Button 3
		Options.add(Options.size(), new MenuState("Button", "Audio")); //Button 3
		
		//Menu 4 / Options
		SinglePlayer.add(SinglePlayer.size(), new MenuState("Button", "Back")); //Button 1
		SinglePlayer.add(SinglePlayer.size(), new MenuState("Button", "Test State")); //Button 2
		SinglePlayer.add(SinglePlayer.size(), new MenuState("Button", "Save 2")); //Button 3
		SinglePlayer.add(SinglePlayer.size(), new MenuState("Button", "Save 1")); //Button 3
	}
	
	public void MenuDraw(SpriteBatch bbg, int width, int height, float Time) {
		//Each if statement is another part of the menu
		if (currentState == 0) { 
			gsm.Render.drawGuiMenus(MainMenu, bbg, height, width, gsm);
		}
		if (currentState == 1) { 
			gsm.Render.drawGuiMenus(Options, bbg, height, width, gsm);
		}
		if (currentState == 2) { 
			gsm.Render.drawGuiMenus(AudioMenu, bbg, height, width, gsm);
		}
		if (currentState == 3) { 
			gsm.Render.drawGuiMenus(SinglePlayer, bbg, height, width, gsm);
		}
	}

	public void MenuHandle() {
		if(currentState == 0) { //Runs all the button checks
			
			for (int i = 0; i < MainMenu.size(); i++) {
				if (gsm.MouseDrag[0] == 1) {
					if (MainMenu.get(i).getType().equals("Slider") && gsm.Render.GuiSliderCheck(gsm.MouseDrag, MainMenu.get(i).getData())) {
					}
					
				}
				if (gsm.MouseClick[0] == 1) {
					if (MainMenu.get(i).getType().equals("Button") && gsm.Render.GUIButtonCheck(gsm.MouseClick, MainMenu.get(i).getData())) {
						gsm.Audio.play("Click");
						if (MainMenu.get(i).getString().equals("Singleplayer")) {
							changeState(3);
						}
						if (MainMenu.get(i).getString().equals("")) {
							
						}
						if (MainMenu.get(i).getString().equals("Options")) {
							changeState(1);
						}
						if (MainMenu.get(i).getString().equals("Quit")) {
							Common.print("Game Quit Button Pressed in Main Menu!");
							Common.ProperShutdown();
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
		} else if(currentState == 3) { //Runs all the button checks in the Audio Menu
			
			for (int i = 0; i < SinglePlayer.size(); i++) {
				if (gsm.MouseDrag[0] == 1) {
					if (SinglePlayer.get(i).getType().equals("Slider") && gsm.Render.GuiSliderCheck(gsm.MouseDrag, SinglePlayer.get(i).getData())) {
					}
				}
				if (gsm.MouseClick[0] == 1) {
					if (SinglePlayer.get(i).getType().equals("Button") && gsm.Render.GUIButtonCheck(gsm.MouseClick, SinglePlayer.get(i).getData())) {
						gsm.Audio.play("Click");
						if (SinglePlayer.get(i).getString().equals("Save 1")) {
							gsm.ChosenSave = "Save1";
							gsm.Audio.stopMusic("8-bit-Digger");
							gsm.setState(GameStateManager.PLAY);
						}
						if (SinglePlayer.get(i).getString().equals("Save 2")) {
							gsm.ChosenSave = "Save2";
							gsm.Audio.stopMusic("8-bit-Digger");
							gsm.setState(GameStateManager.PLAY);
						}
						if (SinglePlayer.get(i).getString().equals("Test State")) {
							gsm.ChosenSave = "TEST";
							gsm.Rwr.CreateWorld("TEST", 200, 200);
							gsm.Audio.stopMusic("8-bit-Digger");
							gsm.setState(GameStateManager.TEST);
						}
						if (SinglePlayer.get(i).getString().equals("Back")) {
							Back();
						}
						
					}
					if (SinglePlayer.get(i).getType().equals("CheckBox")  && gsm.Render.GUICheckBoxCheck(gsm.MouseClick, SinglePlayer.get(i).getData())) {
						gsm.Audio.play("Click");
						SinglePlayer.get(i).SetBool(!SinglePlayer.get(i).GetBool());
					}
				}
			}
		}
	}
	
	//Ends the Gui Shit
	
}