package com.thecubecast.ReEngine.Data;

public class Player {
	
	float[] location = new float[] {0,0};
	String direction = "up";
	
	//INVENTORY AND OTHER STUFF
	
	
	//Get location
	public float[] getLocation() {
		return location;
	}
	
	public void setLocation(float x, float y) {
		if (x < 0 || y < 0 || y > 88 || x > 99) {
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