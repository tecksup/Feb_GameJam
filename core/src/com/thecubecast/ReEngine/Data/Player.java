package com.thecubecast.ReEngine.Data;

public class Player {
	
	int[] location = new int[] {0,0};
	String direction = "up";
	
	//INVENTORY AND OTHER STUFF
	
	
	//Get location
	public int[] getLocation() {
		int x = location[0];
		int y = location[1];
		return location;
		
	}
	
	public void setLocation(int x, int y) {
		if (x < 0 || y < 0 || y > 88) {
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