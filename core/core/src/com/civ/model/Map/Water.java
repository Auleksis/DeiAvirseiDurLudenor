package com.civ.model.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.civ.utils.Assets;
import com.civ.view.GameScreen;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Water extends Point{
    private static TextureRegion WATER = new TextureRegion(Assets.textureAtlas.findRegion("watertexture"));
    public Water(float x, float y, float w, float h) {
        super(WATER, x, y, w, h);
    }
    public Water(){
        super();
    }


    public void write(StringBuilder data) {
        data.append("\n" + "water");
       // super.write(data);
    }

    @Override
    public void read(Kryo kryo, Input input) {

    }
}
