package com.civ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.civ.model.Map.Earth;
import com.civ.model.Map.Map;
import com.civ.model.Map.Point;
import com.civ.model.Map.Water;
import com.civ.model.Settlements.Settlement;
import com.civ.model.UI.UICreator;
import com.civ.model.Units.Unit;
import com.civ.utils.Assets;
import com.civ.utils.IFileManager;
import com.civ.view.GameScreen;
import com.civ.view.MainMenu;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Game extends com.badlogic.gdx.Game {
	public GameScreen gameScreen;
	private Assets assets;
	private MainMenu m;
	public static IFileManager manager;
	private Kryo kryo;

	public Game(IFileManager manager) {
		this.manager = manager;
	}
	@Override
	public void dispose() {
		super.dispose();
		gameScreen.dispose();
		m.dispose();
		assets.dispose();
	}

	@Override
	public void create() {
		kryo = new Kryo();
		kryo.register(Map.class);
		kryo.register(ArrayList.class);
		kryo.register(Unit.class);
		kryo.register(Settlement.class);
		kryo.register(Point.class);
		kryo.register(Water.class);
		kryo.register(Earth.class);
		assets = new Assets();
		m = new MainMenu(this);
		setScreen(m);
	}

	public void set(String s) {
		if(s.equals("1")){
			if(gameScreen == null)
				gameScreen = new GameScreen(this, "creati", 1);
			setScreen(gameScreen);
		}
		if(s.equals("2")){
			setScreen(m);
		}
	}

	public void loadGame(){
		gameScreen = new GameScreen(this, "creative", 0);
		String data = manager.readFile("detei.out");
		GameScreen.map.read(data);
		set("1");
	}

	public void saveGame(){
		manager.writeFile(GameScreen.map.write(), "detei.out");
	}
}


