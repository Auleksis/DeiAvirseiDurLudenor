package com.civ.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import civ.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// полноэкранный режим
		config.width = 1280;
		config.height = 1024;
		config.fullscreen = true;
		// вертикальная синхронизация
		//config.vSyncEnabled = true;
		new LwjglApplication(new Game(new FileHandlerHelper()), config);
	}
}
