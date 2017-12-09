package com.thecubecast.ReEngine.Data;

public class Player {
	
	public int MaxGas = 45;
	public int Gas = 45;
	public int Cash = 50;
	
	public final int MaxY = 88;
	
	float[] location = new float[] {0,0};
	String direction = "right";
	
	public Player (int x, int y) {
		location[0] = x;
		location[1] = y;	
	}
	
	//INVENTORY AND OTHER STUFF
	
	
	//Gas 
	
	public int addGas(int gasToAdd) {
		int fakeGas = Gas + gasToAdd;
		if (fakeGas > MaxGas) {
			Gas = MaxGas;
			return fakeGas - MaxGas;
		} else {
			Gas = fakeGas;
			return 0;
		}
	}
	
	public int topUp() {
		int fuBalance = Cash - (MaxGas/2);
		if (fuBalance > 0) {
			Gas = MaxGas;
			Cash -= (MaxGas/2);
			return 60;
		} else {
			int fakeGas = MaxGas - Gas;
			int StartCash = Cash;
			int fBalance = Cash - fakeGas/2;
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