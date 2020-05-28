package com.civ.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.civ.Game;
import com.civ.control.GameInput;
import com.civ.control.MainGameRobot;
import com.civ.control.Player;
import com.civ.control.PlayerRobot;
import com.civ.control.UIInput;
import com.civ.model.Map.Country;
import com.civ.model.Map.Map;
import com.civ.model.Map.Point;
import com.civ.model.Settlements.Settlement;
import com.civ.model.UI.UI;
import com.civ.model.UI.UICreator;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.naming.Context;


public class GameScreen implements Screen{
    private static SpriteBatch batch;

    public static GameInput gameInput;

    public static UIInput uiInput;

    public static OrthographicCamera camera;

    public static float deltaT;


    public static Vector3 temp = new Vector3();
    public static Country currentCountry = null;
    public static int point = -1;
    public static int currentSettlement = -1;
    public static int currentUnit = -1;
    public static boolean moveUnit = false;
    public static boolean addSegmentCountryFlag = false;
    public static boolean readyToInf = false;


    public static ArrayList<Player>players;
    public static Player currentPlayer = null;
    static int currentPositionPayerList = 1;

    public static String gameMode = "";

    Game game;

    public static UI ui;

    InputMultiplexer multiplexer;

    public static Map map;

    public static MainGameRobot robot = new MainGameRobot();


    public GameScreen(Game game, String mode, int loadMode){
        this.game = game;
        batch = new SpriteBatch();
        gameInput = new GameInput();
        players = new ArrayList<>();
        uiInput = new UIInput();
        if(loadMode == 1) {
            map = new Map(100, 70);
            map.fillCountries();
        }
        else if(loadMode == 0){
            map = new Map();
        }
        if(mode.equals("creative")) {
            ui = new UICreator(this);
        }
        else if(mode.equals("survive")){

        }
        ui = new UICreator(this);

        gameMode = mode;

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(ui.getStage());
        multiplexer.addProcessor(gameInput);
    }

    public static void gibenVerlauf(){
        currentPlayer.finishVerlauf();
        moveUnit = false;
        if(!gameMode.equals("creative")) {
            do {
                currentPlayer = players.get(currentPositionPayerList);
                currentPositionPayerList++;
                if(currentPlayer.getRobot() != null) {
                    currentPlayer.getRobot().update();
                }
                if (currentPositionPayerList > players.size() - 1) {
                    currentPositionPayerList = 0;
                }
            } while (currentPlayer.getRobot().getClass() != PlayerRobot.class);
        }
        else{
            currentPlayer = players.get(currentPositionPayerList);
            currentPositionPayerList++;
            if (currentPositionPayerList > players.size() - 1) {
                currentPositionPayerList = 0;
            }
        }
        currentPlayer.startVerlauf();
        camera.position.set(currentPlayer.getCountry().getUnits().get(0).getBounds().getX(), currentPlayer.getCountry().getUnits().get(0).getBounds().getY(), 0);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        deltaT = delta;

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        map.draw(batch);
        batch.end();
        ui.draw();
    }


    @Override
    public void resize(int width, int height) {
        float aspecrRatio = (float) height / width;
        camera = new OrthographicCamera(20f, 20f * aspecrRatio);
        camera.position.set(currentPlayer.getCountry().getUnits().get(0).getBounds().getX(), currentPlayer.getCountry().getUnits().get(0).getBounds().getY(), 0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }


    public void exit(){
        game.set("2");
    }

    public void loadGame(Kryo kryo, Input input){
        game.loadGame();
    }

    public void saveGame(){
        game.saveGame();
    }
}
