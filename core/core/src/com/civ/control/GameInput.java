package com.civ.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.civ.model.Map.Earth;
import com.civ.model.Map.Point;
import com.civ.model.Map.Segment;
import com.civ.model.Map.Water;
import com.civ.view.GameScreen;

public class GameInput implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
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
        if(GameScreen.gameMode.equals("creative"))
            SegmentController.handleInput();
        else{
            SegmentController.handleGameInput();
        }
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
        return false;
    }
}
