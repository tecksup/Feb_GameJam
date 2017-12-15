package com.thecubecast.ReEngine.Data;

public class Player {
	
	public int MaxGas = 45;
	public float Gas = 45;
	public int Cash = 50;
	
	public int MaxY;
	public int MaxX;
	
	float[] location = new float[] {0,0};
	String direction = "right";
	
	public Player (float x, float y, float SavedGas, int SavedCash, int maxY, int maxX) {
		location[0] = x;
		location[1] = y;
		Gas = SavedGas;
		Cash = SavedCash;
		MaxY = maxY;
		MaxX = maxX;
	}
	
	//INVENTORY AND OTHER STUFF
	
	
	//Gas 
	
	public float addGas(int gasToAdd) {
		float fakeGas = Gas + gasToAdd;
		if (fakeGas > MaxGas) {
			Gas = MaxGas;
			return fakeGas - MaxGas;
		} else {
			Gas = fakeGas;
			return 0;
		}
	}
	
	public int topUp() {
		int fuBalance = Cash - (MaxGas+45/2);
		if (fuBalance > 0) {
			Gas = MaxGas;
			Cash -= (MaxGas/2);
			return 45;
		} else {
			float fakeGas = (MaxGas+45) - Gas;
			int StartCash = Cash;
			float fBalance = Cash - fakeGas/2;
			if (fBalance > 0) {
				Gas += fakeGas;
				Cash -= fakeGas/2;
				return Cash - StartCash;
			}
			else {
				Gas += Cash*2;
				Cash = 0;
				return Cash - StartCash;
			}
		}
	}
	
	//Cash
	public boolean Purchase(int Cost) {
		int fuBalance = Cash - Cost;
		if (fuBalance > 0) {
			//Can buy it
			Cash -= Cost;
			return true;
		} else {
			return false;
		}
	}
	
	//Get location
	public float[] getLocation() {
		return location;
	}
	
	public void setLocation(float x, float y) {
		if (x < 0 || y < 0 || y > MaxY || x > MaxX) {
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