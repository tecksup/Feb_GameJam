package com.thecubecast.ReEngine.Data;

import com.badlogic.gdx.Input.Keys;

public class KeysDown {
	
	private float StartTime;
	int Key;	
	
	public KeysDown(int d, float Start) {
		Key = d;
		StartTime = Start;
	}
	
	public float GetKeyTime(float CurrentTime) {
		return (CurrentTime - StartTime);
	}
	
}