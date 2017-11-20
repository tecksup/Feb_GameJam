package com.thecubecast.ReEngine.Data;

public class Achievement {
	private String text;
	private int IconID;
	
	private float TimeStart;
	private float DeltaTime;
	private float Durration;

	private float Opacity = 1;
	private boolean Animated;
	
	public Achievement(String tex, int ID, float timestart, float CoolDown, boolean Anim) {
		text = tex;
		IconID = ID;
		TimeStart = timestart;
		Durration = CoolDown;
		Animated = Anim;
	}
	
	public String getText() {
		return text;
	}
	
	public int getIconID() {
		return IconID;
	}
	
	public float getTime() {
		return DeltaTime;
	}
	
	public void setTime(float ti) {
		DeltaTime = ti - TimeStart;
	}
	
	public float getDuration () {
		return Durration;
	}
	
	public void setOpacity(float Opac) {
		Opacity = Opac;
	}
	
	public float getOpacity() {
		return Opacity;
	}
	
	public boolean getAnim() {
		return Animated;
	}
}