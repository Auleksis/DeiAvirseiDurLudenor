package com.civ.model.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.civ.Strategy.BuildStrategy;
import com.civ.model.Map.Country;
import com.civ.model.Map.Segment;
import com.civ.model.Settlements.City;
import com.civ.model.Units.FirstFighter;
import com.civ.model.Units.Poselenec;
import com.civ.model.Units.SecondFighter;
import com.civ.model.Units.Ship;
import com.civ.model.Units.ThirdFighter;
import com.civ.model.Units.Unit;
import com.civ.model.Units.Worker;
import com.civ.utils.Assets;
import com.civ.view.GameScreen;

import java.util.ArrayList;

public abstract class UI {
    protected Stage stage;
    public static Skin defaultSkin = new Skin(Gdx.files.internal("cou.json"), Assets.textureAtlas);
    public static Skin defaultWindowSkin = new Skin(Gdx.files.internal("windowStyle.json"), Assets.textureAtlas);
    public static Skin defaultButtonSkin = new Skin(Gdx.files.internal("defbutton.json"), Assets.textureAtlas);
    public static Skin defaultLabelSkin = new Skin(Gdx.files.internal("labelstyle.json"));

    protected Window informWindow;
    TextButton moveOrStip;
    TextButton inf;
    TextButton skip;
    public static Label countryName;
    public static Label segmentType;
    public static Label segmentSettlement;
    ArrayList<TextButton> units;
    TextButton deleteUnit;
    protected ArrayList<Unit> tempUnits;
    public Label actions;

    TextButton []build;

    private TextButton city;
    public City CITY = null;

    public UI(){
        reset();
        skip = new TextButton("Skip", defaultButtonSkin);
        skip.align(Align.center);
        skip.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.gibenVerlauf();
            }
        });
        skip.setPosition(Gdx.graphics.getWidth() - skip.getWidth(), 0);

        moveOrStip = new TextButton("Move", defaultButtonSkin);
        moveOrStip.align(Align.center);
        moveOrStip.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(GameScreen.moveUnit){
                    GameScreen.moveUnit = false;
                }else{
                    GameScreen.moveUnit = true;
                }
            }
        });
        deleteUnit = new TextButton("Delete", defaultButtonSkin);
        deleteUnit.align(Align.center);
        deleteUnit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Country c:
                        GameScreen.map.countries) {
                    for (int i = 0; i < c.getUnits().size(); i++) {
                        Unit u = c.getUnits().get(i);
                        if(u.isActive()){
                            for (int j = 0; j < c.getCountry().size(); j++) {
                                if(c.getCountry().get(j).hasUnit(u.getId())){
                                    c.getCountry().get(j).removeUnit(u);
                                    tempUnits.remove(u);
                                    c.getUnits().remove(u);
                                    c.getCountry().get(j).setLastUnitDrawable();
                                    resetUnitsButtons();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });

        build = new TextButton[3];
        TextButton temp = new TextButton("City", defaultButtonSkin);
        temp.align(Align.center);
        temp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Unit u:
                     tempUnits) {
                    if(u instanceof BuildStrategy && u.isAlive() && u.isAbleToMove()){
                        BuildStrategy buildStrategy = (BuildStrategy)u;
                        buildStrategy.buildSettlement(0); //CITY
                    }
                }
            }
        });
        build[0] = temp;
        temp.setVisible(false);

        temp = new TextButton("Fort", defaultButtonSkin);
        temp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Unit u:
                        tempUnits) {
                    if(u instanceof BuildStrategy && u.isAlive() && u.isAbleToMove()){
                        BuildStrategy buildStrategy = (BuildStrategy)u;
                        buildStrategy.buildSettlement(1); //FORT
                    }
                }
            }
        });
        build[1] = temp;
        temp.setVisible(false);

        temp = new TextButton("Farm", defaultButtonSkin);
        temp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Unit u:
                        tempUnits) {
                    if(u instanceof BuildStrategy && u.isAlive() && u.isAbleToMove()){
                        BuildStrategy buildStrategy = (BuildStrategy)u;
                        buildStrategy.buildSettlement(2); //FARM
                    }
                }
            }
        });
        build[2] = temp;
        temp.setVisible(false);


        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        stage.addActor(actions);
        stage.addActor(inf);
        stage.addActor(informWindow);
        stage.addActor(skip);

        addToStage();
    }

    void reset(){
        informWindow = new Window("Information", defaultWindowSkin);
        informWindow.setPosition(0, 0);
        informWindow.setSize(Gdx.graphics.getWidth(), 400);
        informWindow.setVisible(false);
        inf = new TextButton("i", defaultButtonSkin);
        inf.setPosition(0, Gdx.graphics.getHeight() - 610);
        inf.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!GameScreen.readyToInf)
                    GameScreen.readyToInf = true;
                else {
                    GameScreen.readyToInf = false;
                    informWindow.setVisible(false);
                    GameScreen.moveUnit = false;
                }
            }
        });

        actions = new Label("", defaultLabelSkin);
        actions.setFontScale(0.5f);
        actions.setPosition(Gdx.graphics.getWidth() - actions.getWidth(), 0);

        city = new TextButton("Show city", defaultButtonSkin);
        city.align(Align.center);
        city.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                UICity iu = new UICity(CITY);
            }
        });

        segmentSettlement = new Label("", defaultLabelSkin);
        countryName = new Label("", defaultLabelSkin);
        segmentType = new Label("", defaultLabelSkin);
        informWindow.add(countryName).padRight(10);
        informWindow.add(segmentType).padRight(10);
        informWindow.add(segmentSettlement).padRight(10);
        informWindow.add(city).padRight(10);
    }

    public void setActionText(String text){
        actions.setText(text);
     //   actions.setPosition(Gdx.graphics.getWidth() - actions.getWidth(), 0);
        actions.setPosition(0, 30);
    }

    public void addUnitButton(ArrayList<Unit> un){
        remakeUnits();
        tempUnits = un;
        TextButton txt = null;
        resetUnitsButtons();
    }

    void helpInResetUnitButtons(ArrayList<Unit> un){
        TextButton txt = null;
        for (Unit u:
             un) {
            if(u.getClass() == FirstFighter.class){
                txt = new TextButton("Warriror I", defaultButtonSkin);
            }
            if(u.getClass() == SecondFighter.class){
                txt = new TextButton("Warriror II", defaultButtonSkin);
            }
            if(u.getClass() == ThirdFighter.class){
                txt = new TextButton("Warriror III", defaultButtonSkin);
            }
            if(u.getClass() == Ship.class){
                txt = new TextButton("Ship", defaultButtonSkin);
            }
            if(u.getClass() == Worker.class){
                txt = new TextButton("Worker", defaultButtonSkin);
            }
            if(u.getClass() == Poselenec.class){
                txt = new TextButton("Settler", defaultButtonSkin);
            }
            txt.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    for (Country c:
                         GameScreen.map.countries) {
                        for (Segment s:
                             c.getCountry()) {
                            if(s.hasUnit(u.getId())){
                                s.setUnitsDrawables(u.getId());
                                GameScreen.map.falseActivationUnits();
                                if(GameScreen.moveUnit) {
                                    moveOrStip.setText("Stop");
                                    u.setActive(false);
                                }else{
                                    moveOrStip.setText("Move");
                                    u.setActive(true);
                                }
                                informWindow.add(moveOrStip).padRight(10);
                                if(u.getClass() == Worker.class){
                                    build[1].setVisible(true);
                                    build[2].setVisible(true);
                                    build[0].setVisible(false);
                                }
                                else if(u.getClass() == Poselenec.class){
                                    build[0].setVisible(true);
                                    build[1].setVisible(false);
                                    build[2].setVisible(false);
                                }
                                else{
                                    build[0].setVisible(false);
                                    build[1].setVisible(false);
                                    build[2].setVisible(false);
                                }
                            }
                        }
                    }
                }
            });
            units.add(txt);
        }
    }

    void resetUnitsButtons(){
        remakeUnits();
        TextButton txt = null;
        helpInResetUnitButtons(tempUnits);

        int i = 0;
        for (TextButton t:
                units) {
            i++;
            if(i % 3 == 0){
                informWindow.add(t).row();
            }else
                informWindow.add(t).padRight(10);
        }
        if(units.size() != 0)
            informWindow.add(deleteUnit).padRight(10);
        informWindow.add(build[0]).padRight(10);
        informWindow.add(build[1]).padRight(10);
        informWindow.add(build[2]).padRight(10);
    }

    public void remakeUnits(){
        units = new ArrayList<>();
        informWindow.reset();
        build[0].setVisible(false);
        build[1].setVisible(false);
        build[2].setVisible(false);
        informWindow.add(countryName).padRight(30);
        informWindow.add(segmentType).padRight(30);
        informWindow.add(segmentSettlement).row();
    }

    public void draw(){
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.act();
        stage.draw();
    }

    protected abstract void addToStage();

    public void dispose(){
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public Window getInformWindow() {
        return informWindow;
    }
}
