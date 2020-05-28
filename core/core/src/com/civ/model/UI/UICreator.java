package com.civ.model.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.civ.Game;
import com.civ.model.Map.Earth;
import com.civ.model.Map.Segment;
import com.civ.utils.Assets;
import com.civ.view.GameScreen;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class UICreator extends UI {
    Window window;
    Window windowSetl;
    Window windowUnit;

    float X;
    GameScreen screen;

    TextButton saveButton;

    TextButton c1;
    TextButton c2;
    TextButton addCountry;
    TextButton showCountries;
    TextButton exit;
    TextButton addSettl;
    TextButton city;
    TextButton ferma;
    TextButton fort;
    TextButton exitCo;
    TextButton tNull;

    TextButton v1;
    TextButton v2;
    TextButton v3;
    TextButton w;
    TextButton ship;
    TextButton p1;
    TextButton showUnits;

    TextButton c3;
    TextButton c4;
    TextButton c5;
    TextButton c6;
    TextButton c7;
    TextButton c8;
    TextButton c9;
    TextButton c10;
    TextButton c11;
    TextButton ce;
    TextButton cw;

    public UICreator(GameScreen screen){
        super();
        this.screen = screen;
        X = Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
    }

    protected void addToStage(){
        window = new Window("Countries", defaultWindowSkin);
        window.setPosition(0, 0);
        window.setSize(Gdx.graphics.getWidth(), 500);

        windowSetl = new Window("Settlements", defaultWindowSkin);
        windowSetl.setPosition(0, 0);
        windowSetl.setSize(Gdx.graphics.getWidth(), 400);

        windowUnit = new Window("Units", defaultWindowSkin);
        windowUnit.setPosition(0, 0);
        windowUnit.setSize(Gdx.graphics.getWidth(), 400);

        v1 = new TextButton("Warrior I", defaultButtonSkin);
        v1.align(Align.center);
        v1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentUnit = 1;
            }
        });
        windowUnit.add(v1).padRight(10);

        v2 = new TextButton("Warrior II", defaultButtonSkin);
        v2.align(Align.center);
        v2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentUnit = 2;
            }
        });
        windowUnit.add(v2).padRight(10);

        v3 = new TextButton("Warrior III", defaultButtonSkin);
        v3.align(Align.center);
        v3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentUnit = 3;
            }
        });
        windowUnit.add(v3).padRight(10);

        w = new TextButton("Worker", defaultButtonSkin);
        w.align(Align.center);
        w.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentUnit = 4;
            }
        });
        windowUnit.add(w).padRight(10);

        ship = new TextButton("Ship", defaultButtonSkin);
        ship.align(Align.center);
        ship.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentUnit = 5;
            }
        });
        windowUnit.add(ship).padRight(10);

        p1 = new TextButton("Settler", defaultButtonSkin);
        p1.align(Align.center);
        p1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentUnit = 6;
            }
        });
        windowUnit.add(p1).padRight(10);

        addCountry = new TextButton("C", defaultSkin);
        addCountry.setPosition(0, Gdx.graphics.getHeight() - addCountry.getHeight());
        addCountry.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(window.isVisible()){
                    window.setVisible(false);
                }else {
                    windowSetl.setVisible(false);
                    window.setVisible(true);
                    windowUnit.setVisible(false);
                    informWindow.setVisible(false);
                }
            }
        });
        stage.addActor(addCountry);

        showCountries = new TextButton("Show", defaultSkin);
        showCountries.setPosition(0, Gdx.graphics.getHeight() - addCountry.getHeight() - 10 - showCountries.getHeight());
        showCountries.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Segment.ALPHA == 0.4f) {
                    Segment.ALPHA = 1f;
                }else {
                    Segment.ALPHA = 0.4f;
                }
                Segment.changeAlpha(GameScreen.map.getMap());
            }
        });
        stage.addActor(showCountries);

        addSettl = new TextButton("S", defaultSkin);
        addSettl.setPosition(0, Gdx.graphics.getHeight() - addCountry.getHeight() - 10 - showCountries.getHeight() - 10 - addSettl.getHeight());
        addSettl.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(windowSetl.isVisible()){
                    windowSetl.setVisible(false);
                }else {
                    window.setVisible(false);
                    windowUnit.setVisible(false);
                    GameScreen.addSegmentCountryFlag = false;
                    GameScreen.currentCountry = null;
                    GameScreen.point = -1;
                    windowSetl.setVisible(true);
                    informWindow.setVisible(false);
                }
            }
        });
        stage.addActor(addSettl);

        showUnits = new TextButton("U", defaultButtonSkin);
        showUnits.setPosition(0, Gdx.graphics.getHeight() - addCountry.getHeight() - 10 - showCountries.getHeight() - 10 - addSettl.getHeight() - 10 - showUnits.getHeight());
        showUnits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(windowUnit.isVisible()){
                    windowUnit.setVisible(false);
                }else {
                    window.setVisible(false);
                    windowSetl.setVisible(false);
                    GameScreen.addSegmentCountryFlag = false;
                    GameScreen.currentCountry = null;
                    GameScreen.point = -1;
                    windowUnit.setVisible(true);
                    informWindow.setVisible(false);
                }
            }
        });
        stage.addActor(showUnits);

        exit = new TextButton("Exit", defaultSkin);
        exit.setPosition(Gdx.graphics.getWidth() - exit.getWidth(), Gdx.graphics.getHeight() - exit.getHeight());
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.exit();
            }
        });
        stage.addActor(exit);

        city = new TextButton("City", new Skin(Gdx.files.internal("city.json"), Assets.textureAtlas));
        city.align(Align.center);
        city.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentSettlement = 1;
            }
        });
        windowSetl.add(city).padRight(40);

        ferma = new TextButton("Farm", new Skin(Gdx.files.internal("ferma.json"), Assets.textureAtlas));
        ferma.align(Align.center);
        ferma.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentSettlement = 2;
            }
        });
        windowSetl.add(ferma).padRight(40);

        fort = new TextButton("Fort", new Skin(Gdx.files.internal("fort.json"), Assets.textureAtlas));
        fort.align(Align.center);
        fort.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentSettlement = 3;
            }
        });
        windowSetl.add(fort).padRight(40);

        c1 = new TextButton("Suran", defaultButtonSkin);
        c1.align(Align.center);
        c1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = GameScreen.map.getCountry("Suran");
                GameScreen.addSegmentCountryFlag = true;
            }
        });
        window.add(c1);

        c2 = new TextButton("Nydhon", defaultButtonSkin);
        c2.align(Align.center);
        c2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = GameScreen.map.getCountry("Nydhon");
                GameScreen.addSegmentCountryFlag = true;
            }
        });
        window.add(c2);

        c3 = new TextButton("Morndol", defaultButtonSkin);
        c3.align(Align.center);
        c3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = GameScreen.map.getCountry("Morndol");
                GameScreen.addSegmentCountryFlag = true;
            }
        });
        window.add(c3);

        c4 = new TextButton("Cinagon", defaultButtonSkin);
        c4.align(Align.center);
        c4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = GameScreen.map.getCountry("Cinagon");
                GameScreen.addSegmentCountryFlag = true;
            }
        });
        window.add(c4);

        c5 = new TextButton("Sieben", defaultButtonSkin);
        c5.align(Align.center);
        c5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = GameScreen.map.getCountry("Sieben");
                GameScreen.addSegmentCountryFlag = true;
            }
        });
        window.add(c5).row();

        c6 = new TextButton("Irilin", defaultButtonSkin);
        c6.align(Align.center);
        c6.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = GameScreen.map.getCountry("Irilin");
                GameScreen.addSegmentCountryFlag = true;
            }
        });
        window.add(c6);

        c7 = new TextButton("Reidhon", defaultButtonSkin);
        c7.align(Align.center);
        c7.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = GameScreen.map.getCountry("Reidhon");
                GameScreen.addSegmentCountryFlag = true;
            }
        });
        window.add(c7);

        c8 = new TextButton("Ruderan", defaultButtonSkin);
        c8.align(Align.center);
        c8.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = GameScreen.map.getCountry("Ruderan");
                GameScreen.addSegmentCountryFlag = true;
            }
        });
        window.add(c8);

        c9 = new TextButton("Telrani", defaultButtonSkin);
        c9.align(Align.center);
        c9.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = GameScreen.map.getCountry("Telrani");
                GameScreen.addSegmentCountryFlag = true;
            }
        });
        window.add(c9);

        c10 = new TextButton("Luri", defaultButtonSkin);
        c10.align(Align.center);
        c10.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = GameScreen.map.getCountry("Luri");
                GameScreen.addSegmentCountryFlag = true;
            }
        });
        window.add(c10).row();

        c11 = new TextButton("Lodes", defaultButtonSkin);
        c11.align(Align.center);
        c11.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = GameScreen.map.getCountry("Lodes");
                GameScreen.addSegmentCountryFlag = true;
            }
        });
        window.add(c11);

        ce = new TextButton("Earth", defaultSkin);
        ce.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = null;
                GameScreen.point = 1;
            }
        });
        window.add(ce);

        cw = new TextButton("Water", defaultSkin);
        cw.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = null;
                GameScreen.point = 0;
            }
        });
        window.add(cw);

        tNull = new TextButton("Null", defaultSkin);
        tNull.align(Align.center);
        tNull.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.currentCountry = null;
                GameScreen.point = -1;
                GameScreen.addSegmentCountryFlag = true;
            }
        });
        window.add(tNull);

        exitCo = new TextButton("Exit", defaultSkin);
        exitCo.align(Align.center);
        exitCo.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window.setVisible(false);
                GameScreen.addSegmentCountryFlag = false;
                GameScreen.currentCountry = null;
                GameScreen.point = -1;
            }
        });
        window.add(exitCo);

        saveButton = new TextButton("Save", defaultButtonSkin);
        saveButton.align(Align.center);
        saveButton.setPosition(Gdx.graphics.getWidth() - saveButton.getWidth(), Gdx.graphics.getHeight() - exit.getHeight() - 10 - saveButton.getHeight());
        saveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.saveGame();
            }
        });
        stage.addActor(saveButton);

        windowUnit.setVisible(false);
        window.setVisible(false);
        windowSetl.setVisible(false);
        stage.addActor(windowUnit);
        stage.addActor(window);
        stage.addActor(windowSetl);
    }

}
