package civ.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    private AssetManager manager;
    public static TextureAtlas textureAtlas;
    public static Skin windowsSkins;
    public static Skin buttonsSkins;
    public static Skin labelsSkins;
    public static Skin textfieldsSkins;
    public static Skin scrollPaneSkins;
    public static Skin listsSkins;

    public Assets(){
        manager = new AssetManager();
        manager.load("mainAtlas.atlas", TextureAtlas.class);
        manager.finishLoading();
        textureAtlas = manager.get("mainAtlas.atlas", TextureAtlas.class);
        windowsSkins = new Skin(Gdx.files.internal("windowStyle.json"), textureAtlas);
        buttonsSkins = new Skin(Gdx.files.internal("buttonsSkins.json"), textureAtlas);
        labelsSkins = new Skin(Gdx.files.internal("labelsSkins.json"), textureAtlas);
        textfieldsSkins = new Skin(Gdx.files.internal("textfieldsSkins.json"), textureAtlas);
        scrollPaneSkins = new Skin(Gdx.files.internal("scrollpanesSkins.json"), textureAtlas);
        listsSkins = new Skin(Gdx.files.internal("listsSkins.json"), textureAtlas);
    }



    public AssetManager getManager() {
        return manager;
    }

    public void dispose(){
        manager.dispose();
        textureAtlas.dispose();
        textfieldsSkins.dispose();
        listsSkins.dispose();
        buttonsSkins.dispose();
        windowsSkins.dispose();
        scrollPaneSkins.dispose();
        listsSkins.dispose();
    }
}
