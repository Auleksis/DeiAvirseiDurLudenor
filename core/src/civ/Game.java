package civ;
;

import civ.model.GAmeObject;
import civ.utils.Assets;
import civ.utils.IFileManager;
import civ.view.GameScreen;
import civ.view.MainMenu;


public class Game extends com.badlogic.gdx.Game {
	public GameScreen gameScreen;
	private Assets assets;
	private MainMenu m;
	public static IFileManager manager;

	private int [] preferences;
	private String playerCountryName;

	public Game(IFileManager manager) {
		this.manager = manager;
		preferences = new int[3];
	}
	@Override
	public void dispose() {
		super.dispose();
		if(gameScreen != null)
			gameScreen.dispose();
		m.dispose();
		assets.dispose();
	}

	@Override
	public void create() {
		assets = new Assets();
		m = new MainMenu(this);
		m.setSaves();
		m.addToStage();

		setScreen(m);
	}

	public void set(String s) {
		if(s.equals("1")){
			gameScreen = new GameScreen(this, "creati", 1);
			setScreen(gameScreen);
		}
		if(s.equals("1.5")){
			setScreen(gameScreen);
		}
		if(s.equals("2")){
			m.setSaves();
			m.resetMenu();
			m.setLoading(true);
			setScreen(m);
		}
		if(s.equals("3")){
			gameScreen = new GameScreen(this, "creative", 1);
			setScreen(gameScreen);
		}
		System.gc();
	}

	public void loadGame(String filename, String mode){
		gameScreen = new GameScreen(this, mode, 0);
		String data = manager.readFile(filename);
		GameScreen.map.read(data);
		gameScreen.setMenu();
		set("1.5");
	}

	public void saveGame(String filename){
		manager.writeFile(GameScreen.map.write(), filename + ".aus");
	}

	public int[] getPreferences() {
		return preferences;
	}

	public String getPlayerCountryName() {
		return playerCountryName;
	}

	public void setPlayerCountryName(String playerCountryName) {
		this.playerCountryName = playerCountryName;
	}
}


