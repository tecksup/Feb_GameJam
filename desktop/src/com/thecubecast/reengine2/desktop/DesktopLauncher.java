package com.thecubecast.reengine2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.thecubecast.ReEngine.mainclass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = false;
		//config.addIcon("icon.png", null );
		config.title = "ReEngine";
		config.width = 1920;
		config.height = 1200;
		config.forceExit = false;
		new LwjglApplication(new mainclass(), config);
	}
}
