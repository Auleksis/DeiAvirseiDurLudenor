package com.civ.model.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.civ.model.Map.Country;
import com.civ.model.Map.Segment;
import com.civ.model.Settlements.City;
import com.civ.model.Units.FirstFighter;
import com.civ.view.GameScreen;

import java.util.ArrayList;

public class UICity extends UI {
    private Window mainWindow;

    private City targetCity;
    private Window cityWindow;
    private String cityName;
    private String information;
    private Label informLabel;

    private Window unitsWindow;

    private Window factoryWindow; //окно для производста
    ArrayList<TextButton>objects; //объекты

    UICity(City city){
        mainWindow = new Window("", defaultWindowSkin);

        targetCity = city;

        cityName = "";
        cityWindow = new Window(city.getName(), defaultWindowSkin);
        cityWindow.setPosition(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3);
        cityWindow.setSize(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3);

        information = "";
        informLabel = new Label(information, defaultLabelSkin);

        unitsWindow = new Window("", defaultWindowSkin);
        unitsWindow.setPosition(0, 0);
        unitsWindow.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());

        factoryWindow = new Window("you can make...", defaultWindowSkin);
        factoryWindow.setPosition(Gdx.graphics.getWidth() / 3, 0);
        factoryWindow.setSize(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3);


        objects = new ArrayList<>();
        TextButton warrirorI = new TextButton("Warriror I", defaultButtonSkin);
        warrirorI.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Country c:
                        GameScreen.map.countries) {
                    for (Segment s:
                         c.getCountry()) {
                        if(s.getSettlement() == targetCity){
                            FirstFighter firstFighter = new FirstFighter(s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height);
                            s.addUnit(firstFighter);
                            c.getUnits().add(firstFighter);
                        }
                    }
                }
            }
        });

        mainWindow.addActor(cityWindow);
        mainWindow.addActor(informWindow);
        mainWindow.addActor(unitsWindow);
        addToStage();
    }

    public void setCity(City city){
        targetCity = city;
        cityName = city.getName();
        cityWindow.setName(cityName);
    }

    @Override
    protected void addToStage() {
        informWindow.setVisible(false);
        stage.addActor(mainWindow);
    }
}
