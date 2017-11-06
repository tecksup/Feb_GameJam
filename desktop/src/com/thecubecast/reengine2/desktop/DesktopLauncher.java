package com.thecubecast.reengine2.desktop;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.thecubecast.ReEngine.mainclass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		//set variables from the settings file
		boolean Display_Fullscreen = true;
		boolean Display_WindowedFull = false;
		int[] Windowed_Size = {900,600};
		
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		if (Display_Fullscreen) { //Fullscreen
			config.width = GetMonitorSizeW();
			config.height = GetMonitorSizeH();
			config.fullscreen = true;
		} else {
			if (Display_WindowedFull) { //Windowed Fullscreen
				
			} else { //Windowed
				config.fullscreen = false;
				config.width = Windowed_Size[0];
				config.height = Windowed_Size[1];
			}
		}
		
		config.addIcon("icon.png", null);
		config.title = "ReEngine";
		config.forceExit = false;
		new LwjglApplication(new mainclass(), config);
	}
	
	public static int GetMonitorSizeW() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		return gd.getDisplayMode().getWidth();
	}
	public static int GetMonitorSizeH() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		return gd.getDisplayMode().getHeight();
	}
}
