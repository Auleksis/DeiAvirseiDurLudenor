package com.civ.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.civ.Game;
import com.civ.model.Map.Map;
import com.civ.model.UI.UI;
import com.civ.utils.Assets;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainMenu implements Screen {
    Game game;
    private Stage stage;
    private Skin startSkin;
    private Skin exitSkin;
    private TextButton startButton;
    private TextButton exitButton;
    private TextButton loadButton;

    void addButtonToStage(){
        startButton = new TextButton("S t a r t", startSkin.get("default", TextButton.TextButtonStyle.class));
        startButton.align(Align.center);
        startButton.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - startButton.getHeight() / 2 + 100);
        stage.addActor(startButton);
        exitButton = new TextButton("E x i t", exitSkin.get("default", TextButton.TextButtonStyle.class));
        exitButton.align(Align.center);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - exitButton.getHeight() / 2 - exitButton.getHeight());
        stage.addActor(exitButton);
        loadButton = new TextButton("L o a d", exitSkin);
        loadButton.setPosition(Gdx.graphics.getWidth() / 2 - loadButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 + (loadButton.getHeight() / 2 * 4));
        stage.addActor(loadButton);
    }

    public void draw(){
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.act();
        stage.draw();
    }

    public MainMenu(Game game){
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        startSkin = new Skin(Gdx.files.internal("mystylet.json"), Assets.textureAtlas);
        exitSkin = new Skin(Gdx.files.internal("exitstyle.json"), Assets.textureAtlas);
        addButtonToStage();
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.set("1");
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        loadButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.loadGame();
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
        startSkin.dispose();
        exitSkin.dispose();
    }
}
