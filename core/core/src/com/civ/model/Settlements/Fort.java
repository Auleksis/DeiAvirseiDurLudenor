package com.civ.model.Settlements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Fort extends Settlement {
    public Fort(TextureRegion texture, float x, float y, float w, float h) {
        super(texture, x, y, w, h);
    }

    public Fort(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void write(StringBuilder data) {
        data.append("\nfort");
        super.write(data);
    }

    public void read() {

    }
}
