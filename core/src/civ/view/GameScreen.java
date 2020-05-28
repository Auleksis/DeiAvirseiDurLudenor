package civ.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import civ.Game;
import civ.control.GameInput;
import civ.control.MainGameRobot;
import civ.control.Player;
import civ.control.PlayerRobot;
import civ.model.GAmeObject;
import civ.model.Map.Country;
import civ.model.Map.Map;
import civ.model.Map.Segment;
import civ.model.UI.MainUI;


public class GameScreen implements Screen{
    private static SpriteBatch batch;

    public static GameInput gameInput;

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

    InputMultiplexer multiplexer;

    public static Map map;

    public static MainGameRobot robot = new MainGameRobot();

    public static MainUI mainUI;

    public static int VERLAUF = 0;

    public static boolean mainPlayerKilled = false;

    public static boolean stopGame = false;


    public GameScreen(Game game, String mode, int loadMode){
        this.game = game;
        batch = new SpriteBatch();
        multiplexer = new InputMultiplexer();
        gameInput = new GameInput();
        players = new ArrayList<>();
        gameMode = mode;
        mainPlayerKilled = false;
        if(loadMode == 1) {
            variablesUpdate();
            map = new Map(game.getPreferences()[0], game.getPreferences()[1], game.getPreferences()[2], game.getPlayerCountryName());
            if(game.getPreferences()[2] != 4) {
                map.fillCountries();
            }
            setMenu();
        }
        else if(loadMode == 0){
            map = new Map();
        }
    }

    public void setMenu(){
        mainUI = new MainUI(this);
        mainUI.addToStage();
        multiplexer.addProcessor(mainUI.getStage());
        multiplexer.addProcessor(gameInput);
    }

    public static void gibenVerlauf(){
        currentPlayer.finishVerlauf();
        moveUnit = false;
        if(!gameMode.equals("creative")) {
            do {
                boolean stop = true;
                for (Player p:
                     players) {
                    if(p.getRobot().getClass() == PlayerRobot.class){
                        stop = false;
                    }
                }
                if(stop){
                    mainPlayerKilled = true;
                    mainUI.showExitDialog();
                    break;
                }
                if (currentPositionPayerList > players.size() - 1) {
                    currentPositionPayerList = 0;
                }
                currentPlayer = players.get(currentPositionPayerList);
                currentPositionPayerList++;
                if(!currentPlayer.getCountry().hasCities()){
                    currentPlayer.getRobot().freeSpacefromCountry();
                    players.remove(currentPlayer);
                    currentPositionPayerList++;
                    continue;
                }
                if(currentPlayer.getRobot() != null) {
                    currentPlayer.getRobot().update();
                }
                if (currentPositionPayerList > players.size() - 1) {
                    currentPositionPayerList = 0;
                }
                if(players.size() == 1 && currentPlayer.getRobot().getClass() == PlayerRobot.class){
                    mainUI.showWinDialog();
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
        //TODO нет первого юнита
        try {
            //camera.position.set(currentPlayer.getCountry().getUnits().get(0).getBounds().getX(), currentPlayer.getCountry().getUnits().get(0).getBounds().getY(), 0);
        }catch (IndexOutOfBoundsException e){

        }
        VERLAUF++;
        mainUI.updateGameWindow();
    }

    public void gibenVerlaufTest(){
        currentPlayer.finishVerlauf();
        moveUnit = false;
        if(!gameMode.equals("creative") && players.size() != 0) {
            if (currentPositionPayerList > players.size() - 1) {
                currentPositionPayerList = 0;
            }
            currentPlayer = players.get(currentPositionPayerList);
            currentPositionPayerList++;
            if(!currentPlayer.getCountry().hasCities()){
                currentPlayer.getRobot().freeSpacefromCountry();
                players.remove(currentPlayer);
                currentPositionPayerList++;
                return;
            }
            if(currentPlayer.getRobot() != null) {
                currentPlayer.getRobot().update();
            }
        }
        else{
            currentPlayer = players.get(currentPositionPayerList);
            currentPositionPayerList++;
            if (currentPositionPayerList > players.size() - 1) {
                currentPositionPayerList = 0;
            }
        }

        currentPlayer.startVerlauf();
        //TODO нет первого юнита
        try {
       //     camera.position.set(currentPlayer.getCountry().getUnits().get(0).getBounds().getX(), currentPlayer.getCountry().getUnits().get(0).getBounds().getY(), 0);
        }catch (IndexOutOfBoundsException e){

        }
        if (!gameMode.equals("creative"))
            VERLAUF++;
        mainUI.updateGameWindow();
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
        if(mainPlayerKilled && !stopGame) {
            gibenVerlaufTest();
        }

        batch.begin();
        map.draw(batch);
        batch.end();
        //ui.draw();
        mainUI.draw();
    }


    @Override
    public void resize(int width, int height) {
        float aspecrRatio = (float) height / width;
        camera = new OrthographicCamera(20f, 20f * aspecrRatio);
        try {
            camera.position.set(currentPlayer.getCountry().getUnits().get(0).getBounds().getX(), currentPlayer.getCountry().getUnits().get(0).getBounds().getY(), 0);
        }catch (IndexOutOfBoundsException e){
            camera.position.set(0, 0, 0);
        }
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
        mainUI.dispose();
    }


    public void exit(){
        game.set("2");
    }

    public void saveGame(String filename){
        game.saveGame(filename);
    }

    public Game getGame() {
        return game;
    }

    public static void variablesUpdate(){
        GAmeObject.reboot();
        Segment.reboot();
    }

    public static Player getPlayerofName(String countryName){
        for (Player p:
             players) {
            if(p.getCountry().getName().equals(countryName)){
                return p;
            }
        }
        return null;
    }

    public static Player getPlayerRobotPlayer(){
        for (Player p:
             players) {
            if(p.getRobot().getClass() == PlayerRobot.class){
                return p;
            }
        }
        return null;
    }
}
