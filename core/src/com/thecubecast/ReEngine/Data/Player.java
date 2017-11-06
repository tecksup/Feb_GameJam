package com.thecubecast.ReEngine.Data;

public class Player {
	
	int[] location = new int[] {0,0};
	int direction = 0;
	
	//INVENTORY AND OTHER STUFF
	
	
	//Get location
	public int[] getLocation() {
		int x = location[0];
		int y = location[1];
		return location;
		
	}
	
	public void setLocation(int x, int y) {
		location[0] = x;
		location[1] = y;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int direct) {
		direction = direct;
	}
	
}