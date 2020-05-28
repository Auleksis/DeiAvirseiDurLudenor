package com.civ;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import civ.Game;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidFileManager manager = new AndroidFileManager(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Game(manager), config);
	}
}
