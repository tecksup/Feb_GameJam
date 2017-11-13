package com.thecubecast.ReEngine.Data;


import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import com.badlogic.gdx.Gdx;

public class Common {
	
	public static void ProperShutdown() {
		Common.print("Proper Shutdown Commenced!");
		Common.print("Running all pre-shutdown protocalls...");
		//Then run all the code u want to do before it ends
		//Cleanup(); SaveAll();
		//Now Finish It!
		Common.print("Terminating Program!");
		Gdx.app.exit();
	}
	
	public static int GetMonitorSizeW() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		return gd.getDisplayMode().getWidth();
	}
	public static int GetMonitorSizeH() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		return gd.getDisplayMode().getHeight();
	}
	
	public static void print(String Output) {
		System.out.println(Output);
	}
	
	public static void sleep(int Time) {
		 try {
			 // thread to sleep for 1000 milliseconds
	         Thread.sleep(Time);
	     } catch (Exception e) {
	    	 System.out.println(e);
	     }
	}
	
	public static int roundDown(float x) {
		
		int output = (int) Math.floor(x);
		
		return output;
	}
}