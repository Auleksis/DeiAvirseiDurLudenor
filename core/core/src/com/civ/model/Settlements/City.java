package com.civ.model.Settlements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class City extends Settlement {
    String name;
    public City(TextureRegion texture, float x, float y, float w, float h) {
        super(texture, x, y, w, h);
        name = "aa";
    }

    public City(float x, float y, float w, float h) {
        super(x, y, w, h);
        name = "aa";
    }

    public void write(StringBuilder data) {
        data.append("\ncity");
        super.write(data);
    }


    public void read() {

    }

    public String getName() {
        return name;
    }
}
