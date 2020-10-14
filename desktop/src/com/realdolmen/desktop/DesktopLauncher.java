package com.realdolmen.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.realdolmen.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "MyGame";
		config.width = 960;
		config.height = 540;
		config.resizable = true;

		new LwjglApplication(new MyGdxGame(), config);
	}
}
