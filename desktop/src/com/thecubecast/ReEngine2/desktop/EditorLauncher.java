package com.thecubecast.ReEngine2.desktop;

import Editor.EditorMain;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowListener;
import com.thecubecast.ReEngine.Data.Common;
import com.thecubecast.ReEngine.mainclass;

public class EditorLauncher {
    public static void main (String[] args) {

        final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration ();

        config.setMaximized(true);
        config.setWindowIcon("icon.png");
        config.setTitle("ReEngine Editor");
        config.useVsync(true);
        new Lwjgl3Application(new EditorMain(), config);
    }
}
