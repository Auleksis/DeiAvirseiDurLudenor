package com.civ.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
    private AssetManager manager;
    public static TextureAtlas textureAtlas;

    public Assets(){
        manager = new AssetManager();
        manager.load("asset1.atlas", TextureAtlas.class);
        manager.finishLoading();
        textureAtlas = manager.get("asset1.atlas", TextureAtlas.class);
    }



    public AssetManager getManager() {
        return manager;
    }

    public void dispose(){
        manager.dispose();
        textureAtlas.dispose();
    }
}
