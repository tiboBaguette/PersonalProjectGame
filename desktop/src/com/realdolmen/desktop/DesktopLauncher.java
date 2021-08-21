package com.realdolmen.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.realdolmen.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "MyGame";
		config.resizable = true;
		config.forceExit = false;
		config.vSyncEnabled = true;

		// windowed
//		config.width = 1280;
//		config.height = 720;
//		config.fullscreen = false;

		// fullscreen
		config.width = 1920;
		config.height = 1080;
		config.fullscreen = true;

		new LwjglApplication(new MyGdxGame(), config);
	}
}
