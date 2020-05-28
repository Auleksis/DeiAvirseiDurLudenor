package civ.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.io.InputStream;

import civ.Game;
import civ.view.GameScreen;

public class GameInput implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.A){
            GameScreen.gibenVerlauf();
        }
        if(keycode == Input.Keys.SPACE){
            if(GameScreen.mainPlayerKilled){
                GameScreen.mainPlayerKilled = false;
            }
            else {
                GameScreen.mainPlayerKilled = true;
            }
        }
        if(keycode == Input.Keys.RIGHT){
            GameScreen.camera.translate(50 * GameScreen.deltaT, 0 * GameScreen.deltaT);
        }
        else if(keycode == Input.Keys.LEFT){
            GameScreen.camera.translate(-50 * GameScreen.deltaT, 0 * GameScreen.deltaT);
        }
        else if(keycode == Input.Keys.UP){
            GameScreen.camera.translate(0 * GameScreen.deltaT, 50 * GameScreen.deltaT);
        }
        else if(keycode == Input.Keys.DOWN){
            GameScreen.camera.translate(0 * GameScreen.deltaT, -50 * GameScreen.deltaT);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        /*
        if(GameScreen.gameMode.equals("creative"))
            SegmentController.handleInput();
        else{
            SegmentController.handleGameInput();
        }

         */
        SegmentController.handleGameInput();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(pointer == 0) {
            float x = Gdx.input.getDeltaX();
            float y = Gdx.input.getDeltaY();
            GameScreen.camera.translate(-x * GameScreen.deltaT, y * GameScreen.deltaT);
            return true;
        }
        else if(pointer == 1){
            float x1 = Gdx.input.getDeltaX(0);
            float x2 = Gdx.input.getDeltaX(1);

            float zoom = (x2 - x1) * GameScreen.deltaT;
            GameScreen.camera.zoom += zoom;
            if(GameScreen.camera.zoom > 5){
                GameScreen.camera.zoom = 5;
            }
            if(GameScreen.camera.zoom < 1){
                GameScreen.camera.zoom = 1;
            }
            GameScreen.camera.update();
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if(amount != 0) {
            if (amount > 0) {
                GameScreen.camera.zoom += 0.4f;
                if (GameScreen.camera.zoom > 5) {
                    GameScreen.camera.zoom = 5;
                }
            } else if (amount < 0) {
                GameScreen.camera.zoom -= 0.4f;
                if (GameScreen.camera.zoom < 1) {
                    GameScreen.camera.zoom = 1;
                }
            }
            return true;
        }
        else
            return false;
    }
}
