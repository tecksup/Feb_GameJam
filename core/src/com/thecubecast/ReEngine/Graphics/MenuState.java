package com.thecubecast.ReEngine.Graphics;

public class MenuState {
	
	String Type;
	String Title;
	
	//If its a checkbox
	boolean checked;
	
	//if its a Slider
	
	
	int[] Data;
	
	public MenuState (String type, String title) {
		Title = title;
		Type = type;
	}
	
	public void SetBool(boolean check) {
		checked = check;
	}
	
	public boolean GetBool() {
		return checked;
	}
	
	public String getType() {
		return Type;
	}
	
	public String getString() {
		return Title;
	}
	
	public void draw(int[] drawed) {
		Data = drawed;
	}
	
	public int[] getData() {
		return Data;
	}
}