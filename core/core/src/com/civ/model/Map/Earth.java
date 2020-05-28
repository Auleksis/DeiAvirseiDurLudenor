package com.civ.model.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.civ.utils.Assets;
import com.civ.view.GameScreen;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Earth extends Point {
    private final static TextureRegion EARTH = new TextureRegion(Assets.textureAtlas.findRegion("earthTexture"));
    public Earth(float x, float y, float w, float h) {
        super(EARTH, x, y, w, h);
    }
    public Earth(){
        super();
    }

    public void write(StringBuilder data) {
        data.append("\n" + "earth");
       // super.write(data);
    }

    @Override
    public void read(Kryo kryo, Input input) {

    }
}
