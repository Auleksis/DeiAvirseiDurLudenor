package civ.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.File;
import java.util.ArrayList;

import civ.Game;
import civ.utils.Assets;
import civ.utils.IFileManager;
import civ.view.help.FilesAdapter;

public class MainMenu implements Screen {
    Game game;
    private Stage stage;

    Drawable switch_offSkin;
    Drawable loadFileSkin;
    Drawable informationSpiel;

    private TextButton startButton;
    private TextButton exitButton;
    private TextButton loadButton;
    private TextButton newGameButton;
    private TextButton playButton;
    private TextButton creativeButton;

    private ScrollPane loadFilesWindowScrollPane;
    private ScrollPane countriesScrollPane;

    private List<String> countries;

    ImageButton switch_off;

    ImageButton information;
    private Window informationWindow;
    private Table content;
    private TextButton aboutButton;
    private ScrollPane informationContentScrollPane;
    private List<String>aboutList;
    private TextButton aboutUnitsButton;
    private Label aboutUnitsLabel;

    private ScrollPane aboutScrollPane;


    private Window startWindow;
    private Table loadFilesTable;
    private Window loadFilesWindow;
    private Window modeWindow;
    private FilesAdapter [] files;
    private boolean loading;
    private String filename;
    private TextButton deleteFile;


    private Label prefWidthLabel;
    private TextField prefWidthTextField;
    private Label prefHeightLabel;
    private TextField prefHeightTextField;
    private Label generationTypeLabel;
    private TextField generationTypeTextField;

    float div;

    public MainMenu(final Game game){
        this.game = game;
        div = Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        switch_offSkin = new TextureRegionDrawable(Assets.textureAtlas.findRegion("switch_off"));
        loadFileSkin = new TextureRegionDrawable(Assets.textureAtlas.findRegion("loadFile"));
        informationSpiel = new TextureRegionDrawable(Assets.textureAtlas.findRegion("informationSpiel"));

        startWindow = new Window("", Assets.windowsSkins, "greenWindow");
        startWindow.setVisible(false);
        startWindow.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        startWindow.setPosition(0, 0);

        loadFilesWindow = new Window("", Assets.windowsSkins, "beublue");
        loadFilesWindow.setVisible(false);
        loadFilesWindow.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        loadFilesTable = new Table(Assets.windowsSkins);

        informationWindow = new Window("", Assets.windowsSkins, "beublue");
        informationWindow.setVisible(false);
        informationWindow.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        informationWindow.setPosition(0, 0);

        content = new Table(Assets.windowsSkins);

        aboutButton = new TextButton("ОБ ИГРЕ", Assets.buttonsSkins, "defButton");
        aboutButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setAboutWindow();
            }
        });

        aboutList = new List<String>(Assets.listsSkins, "default");
        aboutList.setItems(new String[]{"Игра написана в жанре пошаговой стратегии.", "Автор: Ганиев Александр", "https://vk.com/comynist555", "Версия: 1.0", "© Все права защищены."});
        aboutList.getStyle().background = null;

        aboutUnitsButton = new TextButton("ЮНИТЫ", Assets.buttonsSkins, "defButton");
        aboutUnitsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setAboutUnitsWindow();
            }
        });

        informationContentScrollPane = new ScrollPane(content, Assets.scrollPaneSkins, "default");
        informationContentScrollPane.setVisible(false);
        informationContentScrollPane.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        informationContentScrollPane.setScrollingDisabled(true, false);
        informationContentScrollPane.getStyle().background = null;


        startButton = new TextButton(" И Г Р А Т Ь ", Assets.buttonsSkins.get("menuStart", TextButton.TextButtonStyle.class));
        startButton.align(Align.center);
        startButton.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - startButton.getHeight() / 2 + 100);


        playButton = new TextButton(" Играть! ", Assets.buttonsSkins, "playButton");
        playButton.setPosition(Gdx.graphics.getWidth() - playButton.getWidth(), 0);
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!loading) {
                    if (Character.isDigit(prefHeightTextField.getText().charAt(0)) && Character.isDigit(prefWidthTextField.getText().charAt(0)) && Integer.valueOf(prefHeightTextField.getText()) >= 20 && Integer.valueOf(prefWidthTextField.getText()) >= 20) {
                        game.getPreferences()[0] = Integer.valueOf(prefWidthTextField.getText());
                        game.getPreferences()[1] = Integer.valueOf(prefHeightTextField.getText());
                        game.getPreferences()[2] = Integer.valueOf(generationTypeTextField.getText());
                        game.setPlayerCountryName(countries.getSelected());
                        game.set("1");
                        GameScreen.VERLAUF = 0;
                    } else {
                        prefWidthTextField.setText("Ширина > 19");
                        prefHeightTextField.setText("Высота > 19");
                    }
                }else{
                    game.loadGame(filename, "cr");
                    loading = false;
                }
            }
        });

        creativeButton = new TextButton(" Редактор ", Assets.buttonsSkins, "playButton");
        creativeButton.setPosition(0, 0);
        creativeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!loading) {
                    if (Character.isDigit(prefHeightTextField.getText().charAt(0)) && Character.isDigit(prefWidthTextField.getText().charAt(0)) && Integer.valueOf(prefHeightTextField.getText()) >= 20 && Integer.valueOf(prefWidthTextField.getText()) >= 20) {
                        game.getPreferences()[0] = Integer.valueOf(prefWidthTextField.getText());
                        game.getPreferences()[1] = Integer.valueOf(prefHeightTextField.getText());
                        game.getPreferences()[2] = Integer.valueOf(generationTypeTextField.getText());
                        game.setPlayerCountryName(countries.getSelected());
                        game.set("3");
                        GameScreen.VERLAUF = 0;
                    } else {
                        prefWidthTextField.setText("Ширина > 19");
                        prefHeightTextField.setText("Высота > 19");
                    }
                }else{
                    game.loadGame(filename, "creative");
                    loading = false;
                }
            }
        });


        exitButton = new TextButton(" В Ы Й Т И ", Assets.buttonsSkins.get("menuExit", TextButton.TextButtonStyle.class));
        exitButton.align(Align.center);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - exitButton.getHeight() / 2 - exitButton.getHeight());

        information = new ImageButton(informationSpiel);
        information.setPosition(0, Gdx.graphics.getHeight() - information.getHeight());
        information.setVisible(true);

        loadButton = new TextButton("Загрузить", Assets.buttonsSkins, "playButton");
        loadButton.setPosition(Gdx.graphics.getWidth() / 2 - loadButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 + (loadButton.getHeight() / 2 * 4));
        loadButton.setVisible(false);

        newGameButton = new TextButton(" Начать новую \nигру", Assets.buttonsSkins, "playButton");
        newGameButton.align(Align.center);
        newGameButton.setVisible(false);
        newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - newGameButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - newGameButton.getWidth() / 2);

        prefWidthLabel = new Label("Ширина карты", Assets.labelsSkins, "defBlack");
        prefWidthLabel.setPosition(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10 * 8.5f);
        prefHeightLabel = new Label("Высота карты", Assets.labelsSkins, "defBlack");
        prefHeightLabel.setPosition(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10 * 5.5f);
        prefWidthTextField = new TextField("50", Assets.textfieldsSkins, "default");
        prefWidthTextField.setPosition(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10 * 7.5f);
        prefWidthTextField.setSize(prefWidthLabel.getWidth(), prefWidthTextField.getHeight());
        prefHeightTextField = new TextField("50", Assets.textfieldsSkins, "default");
        prefHeightTextField.setPosition(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10 * 4.5f);
        prefHeightTextField.setSize(prefHeightLabel.getWidth(), prefHeightTextField.getHeight());

        generationTypeLabel = new Label("Тип генерации (0, 1, 2, 3, 4)", Assets.labelsSkins, "defBlack");
        generationTypeLabel.setPosition(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10 * 2.5f);
        generationTypeTextField = new TextField("0", Assets.textfieldsSkins, "default");
        generationTypeTextField.setPosition(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10 * 1.5f);
        generationTypeTextField.setSize(generationTypeLabel.getWidth(), generationTypeTextField.getHeight());

        modeWindow = new Window("", Assets.windowsSkins, "beublue");
        modeWindow.setVisible(false);
        modeWindow.setSize(Gdx.graphics.getWidth(), 500);
        modeWindow.setPosition(Gdx.graphics.getWidth() / 2 - modeWindow.getWidth() / 2, Gdx.graphics.getHeight() / 2 - modeWindow.getHeight() / 2);
        loading = false;
        filename = "";
        deleteFile = new TextButton(" Удалить ", Assets.buttonsSkins, "playButton");
        deleteFile.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.manager.deleteFile(filename);
                setSaves();
                modeWindow.setVisible(false);
                setLoadWindow();
            }
        });


        loadFilesWindowScrollPane = new ScrollPane(loadFilesTable, Assets.scrollPaneSkins, "default");
        loadFilesWindowScrollPane.setVisible(false);
        loadFilesWindowScrollPane.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        loadFilesWindowScrollPane.getStyle().background = null;
        loadFilesWindowScrollPane.setScrollingDisabled(true, false);

        countries = new List<>(Assets.listsSkins, "default");
        countries.setItems(new String[]{"Морндоль", "Суран", "Илирин", "Зибен", "Люри", "Синагон", "Телрани", "Лодес", "Рудеран", "Нидзон", "Райдзон"});
        countries.getStyle().background = null;

        countriesScrollPane = new ScrollPane(countries, Assets.scrollPaneSkins, "default");
        countriesScrollPane.setVisible(false);
        countriesScrollPane.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        countriesScrollPane.getStyle().background = null;
        countriesScrollPane.setScrollingDisabled(true, false);
        countriesScrollPane.getStyle().background = null;

        aboutScrollPane = new ScrollPane(aboutList, Assets.scrollPaneSkins, "default");
        aboutScrollPane.setVisible(false);
        aboutScrollPane.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        aboutScrollPane.getStyle().background = null;
        aboutScrollPane.setScrollingDisabled(false, false);
        aboutScrollPane.getStyle().background = null;
    }


    public void addToStage(){
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetStartWindow();
            }
        });

        newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setPlayWindow();
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
                setLoadWindow();
            }
        });

        information.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setInformationWindow();
            }
        });

        stage.addActor(information);
        stage.addActor(informationWindow);
        stage.addActor(startButton);
        stage.addActor(startWindow);
        stage.addActor(exitButton);
        stage.addActor(loadFilesWindow);
        stage.addActor(modeWindow);
    }

    public void resetMenu(){
        information.setVisible(true);
    }

    public void setAboutWindow(){
        informationWindow.reset();
        switch_off = new ImageButton(switch_offSkin);
        switch_off.setPosition(informationWindow.getWidth() - switch_off.getWidth(), informationWindow.getHeight() - switch_off.getHeight());
        switch_off.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setInformationWindow();
            }
        });

        aboutScrollPane.setVisible(true);
        aboutList.setWidth(Gdx.graphics.getWidth());

        informationWindow.addActor(switch_off);
        informationWindow.add(aboutScrollPane).width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight() - 2* switch_off.getHeight());
    }

    public void setAboutUnitsWindow(){
        informationWindow.reset();
        switch_off = new ImageButton(switch_offSkin);
        switch_off.setPosition(informationWindow.getWidth() - switch_off.getWidth(), informationWindow.getHeight() - switch_off.getHeight());
        switch_off.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setInformationWindow();
            }
        });


    }

    public void setInformationWindow(){
        informationWindow.reset();
        content.reset();
        exitButton.setVisible(false);
        information.setVisible(false);
        startButton.setVisible(false);
        switch_off = new ImageButton(switch_offSkin);
        switch_off.setPosition(informationWindow.getWidth() - switch_off.getWidth(), informationWindow.getHeight() - switch_off.getHeight());
        switch_off.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startWindow.setVisible(false);
                startButton.setVisible(true);
                exitButton.setVisible(true);
                information.setVisible(true);
                loading = true;
                informationWindow.setVisible(false);
            }
        });

        content.add(aboutButton).row();

        content.setFillParent(true);

        informationContentScrollPane.setVisible(true);
        informationContentScrollPane.layout();
        informationContentScrollPane.scrollTo(0,content.bottom().getY(), 0, 0);
        informationWindow.add(informationContentScrollPane).expandX().height(Gdx.graphics.getHeight() - 2* switch_off.getHeight());
        informationWindow.row();


        informationWindow.addActor(switch_off);
        informationWindow.setVisible(true);
    }

    public void setPlayWindow(){
        startWindow.reset();
        playButton.setPosition(Gdx.graphics.getWidth() - playButton.getWidth() - 100, 30);
        creativeButton.setPosition(prefHeightLabel.getX(), 30);
        switch_off = new ImageButton(switch_offSkin);
        switch_off.setPosition(startWindow.getWidth() - switch_off.getWidth() - 100, startWindow.getHeight() - switch_off.getHeight() - switch_off.getHeight());
        switch_off.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startWindow.setVisible(false);
                startButton.setVisible(true);
                exitButton.setVisible(true);
                information.setVisible(true);
                loading = true;
            }
        });

        playButton.setVisible(true);
        countriesScrollPane.setVisible(true);
        loading = false;

        startWindow.addActor(prefWidthLabel);
        startWindow.addActor(prefHeightLabel);
        startWindow.addActor(prefWidthTextField);
        startWindow.addActor(prefHeightTextField);
        startWindow.addActor(switch_off);
        startWindow.addActor(generationTypeLabel);
        startWindow.addActor(generationTypeTextField);
        startWindow.addActor(creativeButton);
        startWindow.padRight(100);
        startWindow.addActor(playButton);
        startWindow.add(countriesScrollPane).expandX().height(100).right().setActorX(Gdx.graphics.getWidth() - 50 - countries.getWidth());
    }


    void setLoadWindow(){
        switch_off = new ImageButton(switch_offSkin);
        switch_off.setPosition(loadFilesWindow.getWidth() - switch_off.getWidth(), loadFilesWindow.getHeight() - switch_off.getHeight());
        switch_off.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadFilesWindow.setVisible(false);
                startButton.setVisible(true);
                exitButton.setVisible(true);
                information.setVisible(true);
                loading = false;
            }
        });
        loadFilesWindow.reset();
        loadFilesTable.reset();

        boolean labels = false;
        int checkFiles = 0;
        int checkLabels = 0;
        for (int i = 0; i < files.length % 3 + files.length / 3; i++) {
            if(!labels) {
                for (int j = checkFiles; j < files.length; j++) {
                    if((j + 1) % 3 != 0 && j + 1 != files.length) {
                        loadFilesTable.add(files[j].getButton()).padRight(90);
                        checkFiles++;
                    }
                    else if(j + 1 == files.length){
                        loadFilesTable.add(files[j].getButton()).row();
                        checkFiles++;
                        labels = true;
                        break;
                    }
                    else {
                        loadFilesTable.add(files[j].getButton()).row();
                        checkFiles++;
                        labels = true;
                        break;
                    }
                }
            }
            if(labels){
                for (int j = checkLabels; j < files.length; j++) {
                    if((j + 1) % 3 != 0 && j + 1 != files.length) {
                        loadFilesTable.add(files[j].getName()).padRight(90);
                        checkLabels++;
                    }
                    else if( j + 1 == files.length){
                        loadFilesTable.add(files[j].getName()).row();
                        checkLabels++;
                        labels = false;
                        break;
                    }
                    else {
                        loadFilesTable.add(files[j].getName()).row();
                        checkLabels++;
                        labels = false;
                        break;
                    }
                }
            }

        }
        loadFilesTable.padRight(100);
        loadFilesTable.setFillParent(true);

        loadFilesWindowScrollPane.setVisible(true);
        loadFilesWindowScrollPane.layout();
        loadFilesWindowScrollPane.scrollTo(0,loadFilesTable.bottom().getY(), 0, 0);

        loadFilesWindow.addActor(switch_off);
        loadFilesWindow.setVisible(true);
        loadFilesWindow.add(loadFilesWindowScrollPane).expandX().height(Gdx.graphics.getHeight() - 2* switch_off.getHeight());
        loadFilesWindow.row();

        startWindow.setVisible(false);
    }

    public void resetStartWindow(){
        startWindow.reset();
        exitButton.setVisible(false);
        information.setVisible(false);
        startButton.setVisible(false);
        startWindow.setVisible(true);
        switch_off = new ImageButton(switch_offSkin);
        switch_off.setPosition(startWindow.getWidth() - switch_off.getWidth(), startWindow.getHeight() - switch_off.getHeight());
        switch_off.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startWindow.setVisible(false);
                startButton.setVisible(true);
                exitButton.setVisible(true);
                information.setVisible(true);
            }
        });

        newGameButton.setVisible(true);
        loadButton.setVisible(true);

        startWindow.addActor(switch_off);
        startWindow.addActor(newGameButton);
        startWindow.addActor(loadButton);
    }

    public void draw(float delta){
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.act(delta);
        stage.draw();
    }

    void setModeWindow(){
        modeWindow.reset();
        loading = true;
        switch_off = new ImageButton(switch_offSkin);
        switch_off.setPosition(modeWindow.getWidth() - switch_off.getWidth(), modeWindow.getHeight() - switch_off.getHeight());
        switch_off.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                modeWindow.setVisible(false);
                setLoadWindow();
            }
        });
        modeWindow.add(playButton).padRight(50);
        modeWindow.add(creativeButton).padRight(50);
        modeWindow.add(deleteFile).row();
        modeWindow.addActor(switch_off);
        modeWindow.setVisible(true);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        GameScreen.VERLAUF = 0;
        startButton.setVisible(true);
        exitButton.setVisible(true);
        loading = false;
        loadFilesWindow.setVisible(false);
        startWindow.setVisible(false);
        modeWindow.setVisible(false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        draw(delta);
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
    }

    public void setSaves(){
        File [] saves = Game.manager.getallSaves();
        files = new FilesAdapter[saves.length];
        for (int i = 0; i < saves.length; i++) {
            final Label label = new Label(saves[i].getName(), Assets.labelsSkins, "defGreen");
            ImageButton bt = new ImageButton(loadFileSkin);
            bt.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    filename = label.getText().toString();
                    loadFilesWindow.setVisible(false);
                    setModeWindow();
                }
            });
            files[i] = new FilesAdapter(bt, label);
            files[i].getName().setFontScale(0.7f);
        }
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
