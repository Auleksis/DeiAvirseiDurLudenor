package com.civ.model.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.civ.utils.Assets;

import java.util.ArrayList;

public class MainUI {
    public Skin defaultWindowSkin;
    Skin defaultDarkThemeWindowSkin;
    Skin defaultButtonSkin;
    Skin defaultLabelSkin;

    Window gameWindow;
    Window informWindow;
    Window unitsWindow;
    Window unitActionsWindow;
    Window menuExitWindow;

    ImageButton menu;

    Label informationCountry;
    Label informationSegment;
    TextButton informationSettlement;

    Image money;
    Label verlauf;

    ArrayList<TextButton>units;

    ImageButton switch_off;
    ImageButton hide;

    MainUI(){
        defaultDarkThemeWindowSkin = new Skin(Gdx.files.internal("darkThemeWindowskin.json"));
        defaultButtonSkin = new Skin(Gdx.files.internal("defbutton.json"));
        defaultLabelSkin = new Skin(Gdx.files.internal("labelstyle.json"));
        defaultWindowSkin = new Skin(Gdx.files.internal("windowStyle.json"), Assets.textureAtlas);

        gameWindow = new Window("", defaultDarkThemeWindowSkin);
        informWindow = new Window("", defaultDarkThemeWindowSkin);
        informWindow.setVisible(false);
        unitsWindow = new Window("", defaultDarkThemeWindowSkin);
        unitsWindow.setVisible(false);
        unitActionsWindow = new Window("", defaultDarkThemeWindowSkin);
        unitActionsWindow.setVisible(false);
        menuExitWindow = new Window("", defaultWindowSkin);

        menu = new ImageButton((Drawable) Assets.textureAtlas.findRegion("menuExit"));
        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuExitWindow.setVisible(true);
            }
        });
        menu.setSize(60, 60);


        switch_off = new ImageButton((Drawable) Assets.textureAtlas.findRegion("switch_off"));
        hide = new ImageButton((Drawable) Assets.textureAtlas.findRegion("hide"));

        informationSegment = new Label("", defaultLabelSkin);
        informationCountry = new Label("", defaultLabelSkin);
        informationSettlement = new TextButton("", defaultButtonSkin);
        informationSettlement.align(Align.center);

        money = new Image(Assets.textureAtlas.findRegion("money"));
        money.setSize(60, 60);
        verlauf = new Label("", defaultLabelSkin);

        units = new ArrayList<>();
    }
}
