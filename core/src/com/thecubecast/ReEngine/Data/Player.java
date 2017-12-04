package com.thecubecast.ReEngine.Data;

public class Player {
	
	public final int MaxY = 88;
	
	float[] location = new float[] {0,0};
	String direction = "right";
	
	public Player (int x, int y) {
		location[0] = x;
		location[1] = y;	
	}
	
	//INVENTORY AND OTHER STUFF
	
	
	//Get location
	public float[] getLocation() {
		return location;
	}
	
	public void setLocation(float x, float y) {
		if (x < 0 || y < 0 || y > MaxY || x > 99) {
			//DO NOT MOVE!
		} else {
			location[0] = x;
			location[1] = y;	
		}
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String direct) {
		direction = direct;
	}
	
}