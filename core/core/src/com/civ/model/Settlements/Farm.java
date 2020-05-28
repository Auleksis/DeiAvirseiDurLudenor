package com.civ.model.Settlements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Farm extends Settlement {
    public Farm(TextureRegion texture, float x, float y, float w, float h) {
        super(texture, x, y, w, h);
    }

    public Farm(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void write(StringBuilder data) {
        data.append("\nfarm");
        super.write(data);
    }


    public void read() {

    }
}
