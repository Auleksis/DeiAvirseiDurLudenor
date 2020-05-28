package civ.model.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Iterator;


import civ.Strategy.BuildStrategy;
import civ.Strategy.CommonStrategy;
import civ.control.SegmentController;
import civ.model.Map.Country;
import civ.model.Map.Earth;
import civ.model.Map.Segment;
import civ.model.Map.Water;
import civ.model.Settlements.City;
import civ.model.Settlements.Farm;
import civ.model.Settlements.Fort;
import civ.model.Settlements.Settlement;
import civ.model.Units.Actions;
import civ.model.Units.FirstFighter;
import civ.model.Units.Poselenec;
import civ.model.Units.SecondFighter;
import civ.model.Units.Ship;
import civ.model.Units.ThirdFighter;
import civ.model.Units.Unit;
import civ.model.Units.Worker;
import civ.utils.Assets;
import civ.view.GameScreen;

public class MainUI {
    GameScreen screen;

    Stage stage;

    Drawable switch_offSkin;

    Window gameWindow;

    Window informWindow;
    float prefInformSizeY;
    float prefInformSizeX;

    Window unitsWindow;
    Window unitActionsWindow;
    Window menuExitWindow;

    ImageButton informationButton;
    boolean isAbleToShowInformWindow;

    ImageButton continueButton;

    ImageButton menu;

    Label informationCountry;
    Label informationSegment;
    TextButton informationSettlement;

    Image money;
    Label moneyLabel;
    Label verlauf;

    ArrayList<MImageButton>units;
    ArrayList<ImageButton>unitsActions;

    ImageButton switch_off;
    ImageButton switch_off_Error;
    ImageButton hide;

    //TODO exitMenu
    ImageButton saveButton;
    Label saveText;
    ImageButton exitButton;
    Label exitLabel;
    TextField fileNameField;
    Window saveWindow;
    Label saveWindowLabel;
    ImageButton closeSaveWindowButton;
    ImageButton saveToFileButton;

    //TODO CITY
    Window cityInfo;
    Window unitsCityWindow;
    Window unitsBuyWindow;
    Window infoWindow;
    Label costWarrirorI, costWarrirorII, costWarrirorIII, costPoselenec, costWorker, costShip;


    //TODO ACTIONS
    boolean action_move;
    Unit actionUnit;
    Label actionInfoLabel;

    //TODO ERROR
    Window errorWindow;

    //TODO CREATIVE
    TextButton countriesButton;
    TextButton settlementsButton;
    TextButton unitsButton;
    Window countriesWindow;
    ImageButton suran, nidhon, morndol, cinagon, sieben, ilirin, reidhon, ruderan, telrani, luri, lodes;
    Label suranL, nidhonL, morndolL, cinagonL, siebenL, ilirinL, reidhonL, ruderanL, telraniL, luriL, lodesL, earthL, waterL, deleteL;
    ImageButton earthButton, waterButton, deleteCountryButton;
    boolean deleteCountrySegment, water, earth;
    Country readyCountry;
    //Settlements
    ImageButton ferma, fort, city;
    Label fermaL, fortL, cityL;
    Class readySettlement;
    boolean deleteSettlement;
    //Units
    ImageButton wF, wS, wT, w, p, s;
    Label wFL, wSL, wTL, wL, pL, sL;
    Class readyUnit;
    boolean deleteUnit;

    float div;

    //TODO MESSAGES
    ExitDialog messageDialog;
    ExitDialog winDialog;

    public MainUI(final GameScreen screen){
        this.screen = screen;
        div = (float)Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

        stage = new Stage();

        switch_offSkin = new TextureRegionDrawable(Assets.textureAtlas.findRegion("switch_off"));

        gameWindow = new Window("", Assets.windowsSkins, "darkThemeWindow");
        informWindow = new Window("", Assets.windowsSkins, "darkThemeWindow");
        informWindow.setVisible(false);
        prefInformSizeY = informWindow.getHeight();
        prefInformSizeX = informWindow.getWidth();
        unitsWindow = new Window("", Assets.windowsSkins, "darkThemeWindow");
        unitsWindow.setVisible(false);
        unitActionsWindow = new Window("", Assets.windowsSkins, "darkThemeWindow");
        unitActionsWindow.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 10);
        unitActionsWindow.setVisible(false);
        menuExitWindow = new Window("", Assets.windowsSkins, "default");
        menuExitWindow.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);
        menuExitWindow.setPosition(0, Gdx.graphics.getHeight() / 2 - menuExitWindow.getHeight() / 2);
        menuExitWindow.setVisible(false);
        saveWindow = new Window("", Assets.windowsSkins, "default");
        saveWindow.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        saveWindow.setPosition(Gdx.graphics.getWidth() / 2 - saveWindow.getWidth() / 2, Gdx.graphics.getHeight() / 2 - saveWindow.getHeight() / 2);
        saveWindow.setVisible(false);

        errorWindow = new Window("Ошибка!", Assets.windowsSkins, "default");
        errorWindow.setVisible(false);
        errorWindow.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);
        errorWindow.setPosition(Gdx.graphics.getWidth() / 2 - errorWindow.getWidth() / 2, Gdx.graphics.getHeight() / 2 - errorWindow.getHeight() / 2);

        cityInfo = new Window("", Assets.windowsSkins, "default");
        unitsCityWindow = new Window("", Assets.windowsSkins, "default");
        unitsBuyWindow = new Window("", Assets.windowsSkins, "default");
        infoWindow = new Window("", Assets.windowsSkins, "cityWindow");


        Drawable drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("menuExit"));
        menu = new ImageButton(drawable);
        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setExitMenu();
            }
        });
        menu.setSize(100, gameWindow.getHeight());

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("hide"));
        hide = new ImageButton(drawable);
        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("information"));
        informationButton = new ImageButton(drawable);
        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("continue"));
        continueButton = new ImageButton(drawable);
        switch_off_Error = new ImageButton(switch_offSkin);
        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("saveButtontest"));
        saveButton = new ImageButton(drawable);
        saveToFileButton = new ImageButton(drawable);
        saveToFileButton.setPosition(saveWindow.getWidth() / 2 - saveToFileButton.getWidth() / 2, 10);
        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("exitbutton"));
        exitButton = new ImageButton(drawable);
        closeSaveWindowButton = new ImageButton(switch_offSkin);
        closeSaveWindowButton.setPosition(saveWindow.getWidth() - closeSaveWindowButton.getWidth(), saveWindow.getHeight() - closeSaveWindowButton.getHeight());

        saveText = new Label("Сохранить", Assets.labelsSkins, "defGreen");
        saveText.setAlignment(Align.center);
        exitLabel = new Label("Выйти", Assets.labelsSkins, "defGreen");
        exitLabel.setAlignment(Align.center);

        money = new Image(Assets.textureAtlas.findRegion("money"));
        money.setSize(60, 60);
        moneyLabel = new Label(Integer.valueOf(GameScreen.currentPlayer.getCountry().getCost()).toString(), Assets.labelsSkins, "defGreen");
        verlauf = new Label("Ход: " + Integer.valueOf(GameScreen.VERLAUF).toString(), Assets.labelsSkins, "defGreen");

        units = new ArrayList<>();
        unitsActions = new ArrayList<>();

        action_move = false;
        actionUnit = null;
        isAbleToShowInformWindow = true;

        actionInfoLabel = new Label("", Assets.labelsSkins, "defGreen");

        saveWindowLabel = new Label("Введите имя файла", Assets.labelsSkins, "defBlack");
        saveWindowLabel.setPosition(saveWindow.getWidth() / 2 - saveWindowLabel.getWidth() / 2, saveWindow.getHeight() - saveWindowLabel.getHeight());
        fileNameField = new TextField("save " + Integer.valueOf(GameScreen.VERLAUF).toString(), Assets.textfieldsSkins, "default");
        fileNameField.setAlignment(Align.center);
        fileNameField.setSize(saveWindow.getWidth(), fileNameField.getHeight());
        fileNameField.setPosition(0, saveWindow.getHeight() / 2 + fileNameField.getHeight() / 2);

        informationSegment = new Label("", Assets.labelsSkins, "defGreen");
        informationCountry = new Label("", Assets.labelsSkins, "defGreen");
        informationSettlement = new TextButton("", Assets.buttonsSkins, "defButton");
        informationSettlement.align(Align.center);

        countriesWindow = new Window("", Assets.windowsSkins, "default");
        countriesWindow.setVisible(false);
        countriesWindow.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);

        costWarrirorI = new Label(Integer.valueOf(FirstFighter.COST).toString(),  Assets.labelsSkins, "defGreen");
        costWarrirorI.setAlignment(Align.center);
        costWarrirorII = new Label(Integer.valueOf(SecondFighter.COST).toString(),  Assets.labelsSkins, "defGreen");
        costWarrirorII.setAlignment(Align.center);
        costWarrirorIII = new Label(Integer.valueOf(ThirdFighter.COST).toString(),  Assets.labelsSkins, "defGreen");
        costWarrirorIII.setAlignment(Align.center);
        costPoselenec = new Label(Integer.valueOf(Poselenec.COST).toString(),  Assets.labelsSkins, "defGreen");
        costPoselenec.setAlignment(Align.center);
        costWorker = new Label(Integer.valueOf(Worker.COST).toString(),  Assets.labelsSkins, "defGreen");
        costWorker.setAlignment(Align.center);
        costShip = new Label(Integer.valueOf(SecondFighter.COST).toString(),  Assets.labelsSkins, "defGreen");
        costShip.setAlignment(Align.center);

        messageDialog = new ExitDialog("Вы проиграли", Assets.windowsSkins, "wrong");
        messageDialog.addButton("Выйти", new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.getGame().set("2");
                GameScreen.stopGame = false;
            }
        }, Assets.buttonsSkins, "defButton");

        messageDialog.addButton("Продолжить", new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.stopGame = false;
                messageDialog.hide();
            }
        }, Assets.buttonsSkins, "defButton");

        winDialog = new ExitDialog("Вы победили!", Assets.windowsSkins, "win");
        winDialog.addButton("Выйти", new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.getGame().set("2");
                GameScreen.stopGame = false;
            }
        }, Assets.buttonsSkins, "defButton");

        winDialog.addButton("Продолжить", new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.stopGame = false;
                winDialog.hide();
            }
        }, Assets.buttonsSkins, "defButton");
    }

    public void draw(){
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.act();
        stage.draw();
    }

    void resetCreativeMenu(){
        countriesWindow.reset();
    }

    void setCreativeUnitsMenu(){
        deleteCountryButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                deleteUnit = true;
                readyUnit = null;
                deleteCountrySegment = false;
                deleteSettlement = false;
            }
        });
        countriesWindow.add(wF).padRight(10);
        countriesWindow.add(wS).padRight(10);
        countriesWindow.add(wT).padRight(10);
        countriesWindow.add(p).padRight(10);
        countriesWindow.add(w).padRight(10);
        countriesWindow.add(s).padRight(10);
        countriesWindow.add(deleteCountryButton).row();
        countriesWindow.add(wFL).padRight(10);
        countriesWindow.add(wSL).padRight(10);
        countriesWindow.add(wTL).padRight(10);
        countriesWindow.add(pL).padRight(10);
        countriesWindow.add(wL).padRight(10);
        countriesWindow.add(sL).padRight(10);
        countriesWindow.add(deleteL).row();
    }

    void setCreativeCountriesMenu(){
        deleteCountryButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                earth = false;
                water = false;
                deleteCountrySegment = true;
                readyCountry = null;
                deleteSettlement = false;
                deleteUnit = false;
            }
        });
        countriesWindow.add(suran).padRight(10);
        countriesWindow.add(nidhon).padRight(10);
        countriesWindow.add(morndol).padRight(10);
        countriesWindow.add(cinagon).padRight(10);
        countriesWindow.add(sieben).padRight(10);
        countriesWindow.add(ilirin).padRight(10);
        countriesWindow.add(reidhon).row();
        countriesWindow.add(suranL).padRight(10);
        countriesWindow.add(nidhonL).padRight(10);
        countriesWindow.add(morndolL).padRight(10);
        countriesWindow.add(cinagonL).padRight(10);
        countriesWindow.add(siebenL).padRight(10);
        countriesWindow.add(ilirinL).padRight(10);
        countriesWindow.add(reidhonL).row();
        countriesWindow.add(ruderan).padRight(10);
        countriesWindow.add(telrani).padRight(10);
        countriesWindow.add(luri).padRight(10);
        countriesWindow.add(lodes).padRight(10);
        countriesWindow.add(earthButton).padRight(10);
        countriesWindow.add(waterButton).padRight(10);
        countriesWindow.add(deleteCountryButton).row();
        countriesWindow.add(ruderanL).padRight(10);
        countriesWindow.add(telraniL).padRight(10);
        countriesWindow.add(luriL).padRight(10);
        countriesWindow.add(lodesL).padRight(10);
        countriesWindow.add(earthL).padRight(10);
        countriesWindow.add(waterL).padRight(10);
        countriesWindow.add(deleteL);
    }

    void setCreativeSettlementsMenu(){
        deleteCountryButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                deleteSettlement = true;
                readySettlement = null;
                deleteCountrySegment = false;
                deleteUnit = false;
            }
        });
        countriesWindow.add(ferma).padRight(20);
        countriesWindow.add(fort).padRight(20);
        countriesWindow.add(city).padRight(20);
        countriesWindow.add(deleteCountryButton).row();
        countriesWindow.add(fermaL).padRight(20);
        countriesWindow.add(fortL).padRight(20);
        countriesWindow.add(cityL).padRight(20);
        countriesWindow.add(deleteL);
    }

    public void setCreativeMenu(){
        readyCountry = null;
        readySettlement = null;
        readyUnit = null;
        water = false;
        earth = false;
        deleteCountrySegment = false;
        deleteSettlement = false;
        deleteUnit = false;


        countriesButton = new TextButton("Гос-ва", Assets.buttonsSkins, "defButton");
        countriesButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetCreativeMenu();
                setCreativeCountriesMenu();
                if(!countriesWindow.isVisible()) {
                    countriesWindow.setVisible(true);
                }else{
                    countriesWindow.setVisible(false);
                }
                readyCountry = null;
                readySettlement = null;
                readyUnit = null;
                water = false;
                earth = false;
                deleteCountrySegment = false;
                deleteSettlement = false;
                deleteUnit = false;
            }
        });
        gameWindow.add(countriesButton).padRight(20);

        settlementsButton = new TextButton("Пос-ния", Assets.buttonsSkins, "defButton");
        settlementsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetCreativeMenu();
                setCreativeSettlementsMenu();
                if(!countriesWindow.isVisible()) {
                    countriesWindow.setVisible(true);
                }else{
                    countriesWindow.setVisible(false);
                }
                readyCountry = null;
                readySettlement = null;
                readyUnit = null;
                water = false;
                earth = false;
                deleteCountrySegment = false;
                deleteSettlement = false;
                deleteUnit = false;
            }
        });
        gameWindow.add(settlementsButton).padRight(20);

        unitsButton = new TextButton("Юниты", Assets.buttonsSkins, "defButton");
        unitsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetCreativeMenu();
                setCreativeUnitsMenu();
                if(!countriesWindow.isVisible()) {
                    countriesWindow.setVisible(true);
                }else{
                    countriesWindow.setVisible(false);
                }
                readyCountry = null;
                readySettlement = null;
                readyUnit = null;
                water = false;
                earth = false;
                deleteCountrySegment = false;
                deleteSettlement = false;
                deleteUnit = false;
            }
        });
        gameWindow.add(unitsButton);

        Drawable drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("earthTexture"));

        earthButton = new ImageButton(drawable);
        earthButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                earth = true;
                water = false;
                deleteCountrySegment = false;
                readyCountry = null;
            }
        });
        earthButton.setHeight(countriesWindow.getHeight() * 2);
        earthL = new Label("Земля", Assets.labelsSkins, "defGreen");
        earthL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("watertexture"));

        waterButton = new ImageButton(drawable);
        waterButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                earth = false;
                water = true;
                deleteCountrySegment = false;
                readyCountry = null;
            }
        });
        waterButton.setHeight(countriesWindow.getHeight() * 2);
        waterL = new Label("Вода", Assets.labelsSkins, "defGreen");
        waterL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("Suran"));

        suran = new ImageButton(drawable);
        suran.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyCountry = GameScreen.map.countries.get(0);
            }
        });
        suran.setHeight(countriesWindow.getHeight() * 2);
        suranL = new Label("Суран", Assets.labelsSkins, "defGreen");
        suranL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("Nidhon"));

        nidhon = new ImageButton(drawable);
        nidhon.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyCountry = GameScreen.map.countries.get(1);
            }
        });
        nidhon.setHeight(countriesWindow.getHeight() * 2);
        nidhonL = new Label("Нидзон", Assets.labelsSkins, "defGreen");
        nidhonL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("Morndol"));

        morndol = new ImageButton(drawable);
        morndol.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyCountry = GameScreen.map.countries.get(2);
            }
        });
        morndol.setHeight(countriesWindow.getHeight() * 2);
        morndolL = new Label("Морндоль", Assets.labelsSkins, "defGreen");
        morndolL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("Cinagon"));

        cinagon = new ImageButton(drawable);
        cinagon.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyCountry = GameScreen.map.countries.get(3);
            }
        });
        cinagon.setHeight(countriesWindow.getHeight() * 2);
        cinagonL = new Label("Синагон", Assets.labelsSkins, "defGreen");
        cinagonL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("Sieben"));

        sieben = new ImageButton(drawable);
        sieben.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyCountry = GameScreen.map.countries.get(4);
            }
        });
        sieben.setHeight(countriesWindow.getHeight() * 2);
        siebenL = new Label("Зибен", Assets.labelsSkins, "defGreen");
        siebenL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("Ilirin"));

        ilirin = new ImageButton(drawable);
        ilirin.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyCountry = GameScreen.map.countries.get(5);
            }
        });
        ilirin.setHeight(countriesWindow.getHeight() * 2);
        ilirinL = new Label("Илирин", Assets.labelsSkins, "defGreen");
        ilirinL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("Reidhon"));

        reidhon = new ImageButton((drawable));
        reidhon.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyCountry = GameScreen.map.countries.get(6);
            }
        });
        reidhon.setHeight(countriesWindow.getHeight() * 2);
        reidhonL = new Label("Райдзон", Assets.labelsSkins, "defGreen");
        reidhonL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("Ruderan"));

        ruderan = new ImageButton(drawable);
        ruderan.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyCountry = GameScreen.map.countries.get(7);
            }
        });
        ruderan.setHeight(countriesWindow.getHeight() * 2);
        ruderanL = new Label("Рудеран", Assets.labelsSkins, "defGreen");
        ruderanL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("Telrani"));

        telrani = new ImageButton(drawable);
        telrani.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyCountry = GameScreen.map.countries.get(8);
            }
        });
        telrani.setHeight(countriesWindow.getHeight() * 2);
        telraniL = new Label("Телрани", Assets.labelsSkins, "defGreen");
        telraniL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("Luri"));

        luri = new ImageButton(drawable);
        luri.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyCountry = GameScreen.map.countries.get(9);
            }
        });
        luri.setHeight(countriesWindow.getHeight() * 2);
        luriL = new Label("Люри", Assets.labelsSkins, "defGreen");
        luriL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("Lodes"));

        lodes = new ImageButton(drawable);
        lodes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyCountry = GameScreen.map.countries.get(10);
            }
        });
        lodes.setHeight(countriesWindow.getHeight() * 2);
        lodesL = new Label("Лодес", Assets.labelsSkins, "defGreen");
        lodesL.setFontScale(0.5f);

        deleteCountryButton = new ImageButton(switch_offSkin);
        deleteCountryButton.setHeight(countriesWindow.getHeight() * 2);
        deleteL = new Label("Удалить", Assets.labelsSkins, "defGreen");
        deleteL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("f1"));
        ferma = new ImageButton(drawable);
        ferma.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readySettlement = Farm.class;
            }
        });
        ferma.setHeight(countriesWindow.getHeight() * 2);
        fermaL = new Label("Ферма", Assets.labelsSkins, "defGreen");
        fermaL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("8"));
        city = new ImageButton(drawable);
        city.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readySettlement = City.class;
            }
        });
        city.setHeight(countriesWindow.getHeight() * 2);
        cityL = new Label("Город", Assets.labelsSkins, "defGreen");
        cityL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("k1"));
        fort = new ImageButton(drawable);
        fort.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readySettlement = Fort.class;
            }
        });
        fort.setHeight(countriesWindow.getHeight() * 2);
        fortL = new Label("Форт", Assets.labelsSkins, "defGreen");
        fortL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("v1"));
        wF = new ImageButton(drawable);
        wF.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyUnit = FirstFighter.class;
            }
        });
        wF.setHeight(countriesWindow.getHeight() * 2);
        wFL = new Label("Воин I", Assets.labelsSkins, "defGreen");
        wFL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("v2"));
        wS = new ImageButton(drawable);
        wS.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyUnit = SecondFighter.class;
            }
        });
        wS.setHeight(countriesWindow.getHeight() * 2);
        wSL = new Label("Воин II", Assets.labelsSkins, "defGreen");
        wSL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("v3"));
        wT = new ImageButton(drawable);
        wT.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyUnit = ThirdFighter.class;
            }
        });
        wT.setHeight(countriesWindow.getHeight() * 2);
        wTL = new Label("Воин III", Assets.labelsSkins, "defGreen");
        wTL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("p1"));
        p = new ImageButton(drawable);
        p.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyUnit = Poselenec.class;
            }
        });
        p.setHeight(countriesWindow.getHeight() * 2);
        pL = new Label("Поселенец", Assets.labelsSkins, "defGreen");
        pL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("w"));
        w = new ImageButton(drawable);
        w.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyUnit = Worker.class;
            }
        });
        w.setHeight(countriesWindow.getHeight() * 2);
        wL = new Label("Рабочий", Assets.labelsSkins, "defGreen");
        wL.setFontScale(0.5f);

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("s1"));
        s = new ImageButton(drawable);
        s.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                readyUnit = Ship.class;
            }
        });
        s.setHeight(countriesWindow.getHeight() * 2);
        sL = new Label("Корабль", Assets.labelsSkins, "defGreen");
        sL.setFontScale(0.5f);
    }


    public void setUnitActionsWindow(Unit unit){
        //TODO сделать
    }

    public void addToStage(){
        gameWindow.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 10);
        gameWindow.setPosition(0, 0);
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(0, Gdx.graphics.getHeight() - gameWindow.getHeight());
        moveToAction.setDuration(0.5f);
        gameWindow.addAction(moveToAction);

        switch_off_Error.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(errorWindow.isVisible()){
                    errorWindow.setVisible(false);
                }
            }
        });
        switch_off_Error.setPosition(errorWindow.getWidth() - switch_off_Error.getWidth(), errorWindow.getHeight() - switch_off_Error.getHeight());
        errorWindow.addActor(switch_off_Error);
        errorWindow.add(actionInfoLabel);

        money.setPosition(Gdx.graphics.getWidth() / 10, 8);
        moneyLabel.setPosition(money.getX() + 60, money.getY() - 25);
        verlauf.setPosition(Gdx.graphics.getWidth() / 10 * 7 - verlauf.getWidth(), -10);

        if(!GameScreen.gameMode.equals("creative")) {
            gameWindow.addActor(money);
            gameWindow.addActor(moneyLabel);
            gameWindow.addActor(verlauf);
        }else{

        }
        informationButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(isAbleToShowInformWindow){
                    isAbleToShowInformWindow = false;
                    informationButton.getImage().setColor(200, 200, 200, 0.5f);
                }else{
                    isAbleToShowInformWindow = true;
                    informationButton.getImage().setColor(200, 200, 200, 1);
                }
            }
        });
        informationButton.setPosition(0, Gdx.graphics.getHeight() / 2 + informationButton.getHeight() * 2);

        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.gibenVerlauf();
            }
        });
        continueButton.setPosition(0, Gdx.graphics.getHeight() / 2);

        informWindow.setVisible(false);

        saveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveWindow.setVisible(true);
                fileNameField.setText("save " + Integer.valueOf(GameScreen.VERLAUF).toString());
                menuExitWindow.setVisible(false);
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.getGame().set("2");
            }
        });

        menu.setPosition(gameWindow.getWidth() - menu.getWidth() * 1.5f,menu.getHeight() - 1.6f * gameWindow.getHeight());
        gameWindow.addActor(menu);

        if(GameScreen.gameMode.equals("creative")){
            setCreativeMenu();
        }

        closeSaveWindowButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveWindow.setVisible(false);
            }
        });
        saveToFileButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.saveGame(fileNameField.getText());
                saveWindow.setVisible(false);
            }
        });
        saveWindow.addActor(saveWindowLabel);
        saveWindow.addActor(fileNameField);
        saveWindow.addActor(closeSaveWindowButton);
        saveWindow.addActor(saveToFileButton);


        stage.addActor(gameWindow);
        stage.addActor(unitsWindow);
        stage.addActor(saveWindow);

        if(!GameScreen.gameMode.equals("creative")) {
            stage.addActor(errorWindow);
            stage.addActor(continueButton);
            stage.addActor(informationButton);
            stage.addActor(informWindow);
            stage.addActor(unitActionsWindow);
        }else{
            stage.addActor(countriesWindow);
        }

        stage.addActor(menuExitWindow);
    }

    public void dispose(){
        stage.dispose();
    }

    public void inform(final Segment segment){
        setUnitsFromSegment(segment);

        if (units.size() == 0) {
            unitsWindow.setVisible(false);
        }

        if (units.size() != 0) {
            unitsWindow.reset();
            float height = units.get(0).getImg().getHeight() * units.size();
            float width = units.get(0).getImg().getWidth() * 2;
            unitsWindow.setSize(width, height);
            unitsWindow.setPosition(Gdx.graphics.getWidth() - width, Gdx.graphics.getHeight() / 2 - height / 2);
            for (MImageButton mi :
                    units) {
                unitsWindow.add(mi.getImg()).row();
            }
            unitsWindow.setVisible(true);
        }

        if(action_move){
            SegmentController.handleUnitInputMove(segment, actionUnit);
            action_move = false;
        }

        if(GameScreen.gameMode.equals("creative")){
            if(countriesWindow.isVisible()) {
                if (readyCountry != null) {
                    Country c = GameScreen.map.getCountryFromSegment(segment);
                    if(c != null){
                        c.deleteSegment(segment);
                    }
                    readyCountry.addSegment(segment);
                } else if (!water && !earth && deleteCountrySegment) {
                    Country c = GameScreen.map.getCountryFromSegment(segment);
                    if (c != null) {
                        c.deleteSegment(segment);
                        segment.setColor(null);
                        segment.removeAllUnits();
                        segment.setSettlement(null);
                    }
                }
                else if(earth){
                    int id = segment.getPoint().getId();
                    for (int i = 0; i < segment.getUnits().size(); i++) {
                        segment.getUnits().remove(i);
                    }
                    segment.setSettlement(null);
                    segment.setPoint(new Earth(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height));
                    segment.getPoint().setId(id);
                }else if(water){
                    int id = segment.getPoint().getId();
                    for (int i = 0; i < segment.getUnits().size(); i++) {
                        segment.getUnits().remove(i);
                    }
                    segment.setSettlement(null);
                    segment.setPoint(new Water(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height));
                    segment.getPoint().setId(id);
                }
                if(readySettlement != null){
                    Country c = GameScreen.map.getCountryFromSegment(segment);
                    if(c != null){
                        if(readySettlement == Farm.class){
                            Farm farm = new Farm(Country.farm, segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                            segment.setSettlement(farm);
                        }
                        else if(readySettlement == Fort.class){
                            Fort fort = new Fort(Country.fort, segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                            segment.setSettlement(fort);
                        }
                        else if( readySettlement == City.class){
                            City city = new City(c.getCity(), segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                            segment.setSettlement(city);
                        }
                    }
                }
                else if(deleteSettlement){
                    segment.setSettlement(null);
                }

                if(readyUnit != null){
                    Country c = GameScreen.map.getCountryFromSegment(segment);
                    if(c != null){
                        if(readyUnit == FirstFighter.class){
                            FirstFighter fighter = new FirstFighter(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                            if(segment.addUnit(fighter)){
                                c.getUnits().add(fighter);
                            }
                        }
                        else if(readyUnit == SecondFighter.class){
                            SecondFighter fighter = new SecondFighter(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                            if(segment.addUnit(fighter)){
                                c.getUnits().add(fighter);
                            }
                        }
                        else if(readyUnit == ThirdFighter.class){
                            ThirdFighter fighter = new ThirdFighter(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                            if(segment.addUnit(fighter)){
                                c.getUnits().add(fighter);
                            }
                        }
                        else if(readyUnit == Poselenec.class){
                            Poselenec fighter = new Poselenec(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                            if(segment.addUnit(fighter)){
                                c.getUnits().add(fighter);
                            }
                        }
                        else if(readyUnit == Worker.class){
                            Worker fighter = new Worker(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                            if(segment.addUnit(fighter)){
                                c.getUnits().add(fighter);
                            }
                        }
                        else if(readyUnit == Ship.class){
                            Ship fighter = new Ship(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                            if(segment.addUnit(fighter)){
                                c.getUnits().add(fighter);
                            }
                        }
                    }
                }else if(deleteUnit){
                    Country c = GameScreen.map.getCountryFromSegment(segment);
                    if(c != null){
                        for (Unit u:
                             segment.getUnits()) {
                            c.getUnits().remove(u);
                        }
                    }
                    for (int i = 0; i < segment.getUnits().size(); i++) {
                        segment.getUnits().remove(i);
                    }
                }
            }
        }else {

            informWindow.reset();


            if (segment.getPoint().getClass() == Earth.class) {
                informationSegment.setText("Земля");
            } else {
                informationSegment.setText("Вода");
            }

            if (GameScreen.map.getCountryFromSegment(segment) != null) {
                informationCountry.setText(GameScreen.map.getCountryFromSegment(segment).getName());
            }else{
                informationCountry.setText("");
            }

            if (segment.getSettlement() != null) {
                Settlement s = segment.getSettlement();
                if (s.getClass() == City.class) {
                    informationSettlement.setText("Город");
                    if(GameScreen.currentPlayer.getCountry().hasSegment(segment)) {
                        informationSettlement.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                setCityWindow(segment);
                            }
                        });
                    }else{
                        if(informationSettlement.getListeners().size != 0)
                            informationSettlement.getListeners().removeRange(0, informationSettlement.getListeners().size - 1);
                    }
                }else{
                    informationSettlement.setText("");
                    if(informationSettlement.getListeners().size != 0)
                        informationSettlement.getListeners().removeRange(0, informationSettlement.getListeners().size - 1);
                }
            }

            informWindow.setSize(prefInformSizeX * 3, prefInformSizeY + informationSettlement.getHeight() * 1.5f);

            switch_off = new ImageButton(switch_offSkin);
            switch_off.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    informWindow.setVisible(false);
                }
            });
            switch_off.setPosition(informWindow.getWidth() - switch_off.getWidth(), informWindow.getHeight() - switch_off.getHeight());

            //TODO
            informWindow.add(informationCountry).row();
            informWindow.add(informationSegment).row();
            informWindow.addActor(switch_off);
            if (segment.getSettlement() != null) {
                informWindow.add(informationSettlement);
            }
            informWindow.setPosition(0, Gdx.graphics.getHeight() / 2 - informWindow.getHeight());

            informWindow.setVisible(isAbleToShowInformWindow);
        }
    }

    public void setExitMenu(){
        menuExitWindow.reset();
        menuExitWindow.setVisible(true);

        switch_off = new ImageButton(switch_offSkin);
        switch_off.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuExitWindow.setVisible(false);
            }
        });
        switch_off.setPosition(menuExitWindow.getWidth() - switch_off.getWidth(), menuExitWindow.getHeight() - switch_off.getHeight());

        saveButton.setPosition(menuExitWindow.getWidth() / 2 - saveButton.getWidth() / 2, menuExitWindow.getHeight() / 2 + saveButton.getHeight() / 1.5f);
        exitButton.setPosition(menuExitWindow.getWidth() / 2 - exitButton.getWidth() / 2, menuExitWindow.getHeight() / 2 - saveButton.getHeight());

        saveText.setFontScale(0.5f);
        saveText.setSize(saveButton.getWidth(), saveButton.getHeight());
        saveText.setPosition(saveButton.getX(), saveButton.getY() - saveText.getHeight() * 0.8f);
        exitLabel.setFontScale(0.5f);
        exitLabel.setSize(exitButton.getWidth(), exitButton.getHeight());
        exitLabel.setPosition(exitButton.getX(), exitButton.getY() - exitLabel.getHeight() * 0.8f);

        menuExitWindow.addActor(saveButton);
        menuExitWindow.addActor(exitButton);
        menuExitWindow.addActor(switch_off);
        menuExitWindow.addActor(saveText);
        menuExitWindow.addActor(exitLabel);
    }

    public void setUnitsFromSegment(Segment segment){
        units = new ArrayList<>();
        Drawable drawable = null;
        MImageButton txt = null;
        for ( final Unit u:
                segment.getUnits()) {
            if(u.getClass() == FirstFighter.class){
                drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("v1"));
                txt = new MImageButton(new ImageButton(drawable), u);
            }
            if(u.getClass() == SecondFighter.class){
                drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("v2"));
                txt = new MImageButton(new ImageButton(drawable), u);
            }
            if(u.getClass() == ThirdFighter.class){
                drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("v3"));
                txt = new MImageButton(new ImageButton(drawable), u);
            }
            if(u.getClass() == Ship.class){
                drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("s1"));
                txt = new MImageButton(new ImageButton(drawable), u);
            }
            if(u.getClass() == Worker.class){
                drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("w"));
                txt = new MImageButton(new ImageButton(drawable), u);
            }
            if(u.getClass() == Poselenec.class){
                drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("p1"));
                txt = new MImageButton(new ImageButton(drawable), u);
            }

            final MImageButton finalTxt = txt;
            txt.getImg().addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(GameScreen.currentPlayer.getCountry().getUnits().contains(u) && !GameScreen.gameMode.equals("creative")) {
                        createUnitActionMenu(finalTxt.unit);
                    }
                }
            });

            units.add(txt);
        }
    }

    public void createUnitActionMenu(final Unit unit){
        unitActionsWindow.reset();
        Drawable drawable = null;
        ImageButton button = null;

        Iterator<Actions> it = unit.getActions().iterator();
        while(it.hasNext()){
            Actions action = it.next();
            if(action == Actions.ACTION_MOVE){
                drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("action_move"));
                button = new ImageButton(drawable);
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        action_move = true;
                        actionUnit = unit;
                    }
                });
            }
            if(action == Actions.ACTION_DESTROY){
                drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("action_destroy"));
                button = new ImageButton(drawable);
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        for (Segment segment :
                                GameScreen.map.getMap()) {
                            if(segment.hasUnit(unit.getId())){
                                if(unit instanceof CommonStrategy){
                                    CommonStrategy c = (CommonStrategy)unit;
                                    if(unit.isAbleToMove()) {
                                        c.destroyObject(segment);
                                    }
                                }
                            }
                        }
                    }
                });
            }
            if(action == Actions.ACTION_KILL_THIS_UNIT){
                drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("action_kill_this_unit"));
                button = new ImageButton(drawable);
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        for (Segment segment :
                                GameScreen.map.getMap()) {
                            if(segment.hasUnit(unit.getId())){
                                segment.removeUnit(unit);
                            }
                        }

                        for (int i = 0; i < units.size(); i++) {
                            if(units.get(i).unit == unit){
                                units.remove(i);
                                if(units.size() == 0){
                                    unitsWindow.setVisible(false);
                                }

                                if(units.size() != 0){
                                    unitsWindow.reset();
                                    float height = units.get(0).getImg().getHeight() * units.size();
                                    float width = units.get(0).getImg().getWidth() * 2;
                                    unitsWindow.setSize(width, height);
                                    unitsWindow.setPosition(Gdx.graphics.getWidth() - width, Gdx.graphics.getHeight() / 2 - height / 2);
                                    for (MImageButton mi:
                                            units) {
                                        unitsWindow.add(mi.getImg()).row();
                                    }
                                    unitsWindow.setVisible(true);
                                }
                            }
                        }

                        GameScreen.currentPlayer.getCountry().getUnits().remove(unit);
                        unitActionsWindow.setVisible(false);
                    }
                });
            }
            if(action == Actions.ACTION_CREATE_CITY){
                drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("action_create_city"));
                button = new ImageButton(drawable);
                button.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        BuildStrategy b = (BuildStrategy)unit;
                        b.buildSettlement(0);
                        GameScreen.currentPlayer.getCountry().resetCityAreas();
                    }
                });
            }
            if(action == Actions.ACTION_CREATE_FARM){
                drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("action_create_farm"));
                button = new ImageButton(drawable);
                button.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        GameScreen.currentPlayer.getCountry().resetCityAreas();
                        Worker w = (Worker)unit;
                        if(GameScreen.currentPlayer.getCountry().isAbleToWorkHere(w.forBuildSegment())) {
                            BuildStrategy b = (BuildStrategy) unit;
                            b.buildSettlement(2);
                        }else {
                            actionInfoLabel.setText("Здесь нельзя строить!");
                            errorWindow.setVisible(true);
                        }
                    }
                });
            }
            if(action == Actions.ACTION_CREATE_FORT){
                drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("action_create_fort"));
                button = new ImageButton(drawable);
                button.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        BuildStrategy b = (BuildStrategy)unit;
                        b.buildSettlement(1);
                    }
                });
            }
            unitActionsWindow.add(button).padRight(40);
        }

        switch_off.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                unitActionsWindow.setVisible(false);
            }
        });
        switch_off.setPosition(unitActionsWindow.getWidth() - switch_off.getWidth(), unitActionsWindow.getHeight() - switch_off.getHeight());
        unitActionsWindow.addActor(switch_off);

        unitActionsWindow.setVisible(true);
    }

    public void hideInform(){
        informWindow.setVisible(false);
    }

    public void setCityWindow(Segment segment){
        cityInfo.reset();
        unitsCityWindow.reset();
        unitsBuyWindow.reset();
        infoWindow.reset();

        cityInfo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cityInfo.setVisible(true);


        unitsCityWindow.setPosition(0, 0);
        unitsCityWindow.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight());
        unitsCityWindow.setVisible(true);


        unitsBuyWindow.setPosition(Gdx.graphics.getWidth() / 4, 0);
        unitsBuyWindow.setSize(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4.5f);
        unitsBuyWindow.setVisible(true);
        setBuyUnits(segment);

        infoWindow.setName(((City)segment.getSettlement()).getName());
        infoWindow.setSize(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4.5f);
        infoWindow.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4.5f);
        infoWindow.setVisible(true);


        unitsWindow.setVisible(false);
        gameWindow.setVisible(false);
        unitActionsWindow.setVisible(false);
        informWindow.setVisible(false);
        menuExitWindow.setVisible(false);

        for (MImageButton u:
             units) {
            unitsCityWindow.add(u.img).row();
        }

        switch_off = new ImageButton(switch_offSkin);
        switch_off.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                cityInfo.setVisible(false);
                gameWindow.setVisible(true);
            }
        });
        switch_off.setPosition(infoWindow.getWidth() - switch_off.getWidth(), infoWindow.getHeight() - switch_off.getHeight());

        infoWindow.addActor(switch_off);
        cityInfo.addActor(infoWindow);
        cityInfo.addActor(unitsCityWindow);
        cityInfo.addActor(unitsBuyWindow);
        stage.addActor(cityInfo);
    }

    public void setBuyUnits(final Segment segment){

        Drawable drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("v1"));
        final ImageButton warrirorI = new ImageButton(drawable);
        warrirorI.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(GameScreen.map.getCountryFromSegment(segment).canBuyUnit(FirstFighter.COST)) {
                    FirstFighter f = new FirstFighter(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                    if (segment.addUnit(f)) {
                        GameScreen.map.getCountryFromSegment(segment).payForUnit(FirstFighter.COST);
                        GameScreen.currentPlayer.getCountry().getUnits().add(f);
                        GameScreen.map.getCountryFromSegment(segment).resetSegment(segment);
                        segment.resetUnitsDrawables();
                        resetCityMenu(segment);
                        updateGameWindow();
                    }
                }
            }
        });


        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("v2"));
        ImageButton warrirorII = new ImageButton(drawable);
        warrirorII.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(GameScreen.map.getCountryFromSegment(segment).canBuyUnit(SecondFighter.COST)) {
                    SecondFighter s = new SecondFighter(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                    if (segment.addUnit(s)) {
                        GameScreen.map.getCountryFromSegment(segment).payForUnit(SecondFighter.COST);
                        GameScreen.currentPlayer.getCountry().getUnits().add(s);
                        segment.resetUnitsDrawables();
                        GameScreen.map.getCountryFromSegment(segment).resetSegment(segment);
                        resetCityMenu(segment);
                        updateGameWindow();
                    }
                }
            }
        });


        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("v3"));
        ImageButton warrirorIII = new ImageButton(drawable);
        warrirorIII.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(GameScreen.map.getCountryFromSegment(segment).canBuyUnit(ThirdFighter.COST)) {
                    ThirdFighter t =new ThirdFighter(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                    if (segment.addUnit(t)) {
                        GameScreen.map.getCountryFromSegment(segment).payForUnit(ThirdFighter.COST);
                        GameScreen.map.getCountryFromSegment(segment).resetSegment(segment);
                        GameScreen.currentPlayer.getCountry().getUnits().add(t);
                        segment.resetUnitsDrawables();
                        resetCityMenu(segment);
                        updateGameWindow();
                    }
                }
            }
        });


        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("p1"));
        ImageButton poselenec = new ImageButton(drawable);
        poselenec.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(GameScreen.map.getCountryFromSegment(segment).canBuyUnit(Poselenec.COST)) {
                    Poselenec p = new Poselenec(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                    if (segment.addUnit(p)) {
                        GameScreen.map.getCountryFromSegment(segment).payForUnit(Poselenec.COST);
                        GameScreen.map.getCountryFromSegment(segment).resetSegment(segment);
                        GameScreen.currentPlayer.getCountry().getUnits().add(p);
                        segment.resetUnitsDrawables();
                        resetCityMenu(segment);
                        updateGameWindow();
                    }
                }
            }
        });

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("w"));
        ImageButton worker = new ImageButton(drawable);
        worker.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(GameScreen.map.getCountryFromSegment(segment).canBuyUnit(Worker.COST)) {
                    Worker w = new Worker(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                    if (segment.addUnit(w)) {
                        GameScreen.map.getCountryFromSegment(segment).payForUnit(Worker.COST);
                        GameScreen.map.getCountryFromSegment(segment).resetSegment(segment);
                        GameScreen.currentPlayer.getCountry().getUnits().add(w);
                        segment.resetUnitsDrawables();
                        resetCityMenu(segment);
                        updateGameWindow();
                    }
                }
            }
        });

        drawable = new TextureRegionDrawable(Assets.textureAtlas.findRegion("s1"));
        ImageButton ship = new ImageButton(drawable);
        ship.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(GameScreen.map.getCountryFromSegment(segment).canBuyUnit(Ship.COST)) {
                    Ship s = new Ship(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                    if (segment.addUnit(s)) {
                        GameScreen.map.getCountryFromSegment(segment).payForUnit(Ship.COST);
                        GameScreen.map.getCountryFromSegment(segment).resetSegment(segment);
                        GameScreen.currentPlayer.getCountry().getUnits().add(s);
                        segment.resetUnitsDrawables();
                        resetCityMenu(segment);
                        updateGameWindow();
                    }
                }
            }
        });

        unitsBuyWindow.add(warrirorI).padRight(10);
        unitsBuyWindow.add(warrirorII).padRight(10);
        unitsBuyWindow.add(warrirorIII).padRight(10);
        unitsBuyWindow.add(poselenec).padRight(10);
        unitsBuyWindow.add(worker).padRight(10);
        unitsBuyWindow.add(ship).row();

        unitsBuyWindow.add(costWarrirorI).padRight(10);
        unitsBuyWindow.add(costWarrirorII).padRight(10);
        unitsBuyWindow.add(costWarrirorIII).padRight(10);
        unitsBuyWindow.add(costPoselenec).padRight(10);
        unitsBuyWindow.add(costWorker).padRight(10);
        unitsBuyWindow.add(costShip);
    }

    public void resetCityMenu(Segment segment){
        unitsCityWindow.reset();
        setUnitsFromSegment(segment);

        for (MImageButton u:
                units) {
            unitsCityWindow.add(u.getImg()).row();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void updateGameWindow(){
        verlauf.setText("Ход: " + Integer.valueOf(GameScreen.VERLAUF).toString());
        moneyLabel.setText(Integer.valueOf(GameScreen.currentPlayer.getCountry().getCost()).toString());
        unitActionsWindow.setVisible(false);
        unitsWindow.setVisible(false);
        informWindow.setVisible(false);
    }


    public void showExitDialog(){
        messageDialog.show(stage);
    }
    public void showWinDialog(){winDialog.show(stage);}
}
