package civ.model.Settlements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import civ.model.GAmeObject;

public abstract class Settlement extends GAmeObject {
    public Settlement(TextureRegion texture, float x, float y, float w, float h) {
        super(texture, x, y, w, h);
    }

    public Settlement(float x, float y, float w, float h){
        super(null, x, y, w, h);
    }


    public void write(StringBuilder data) {
        data.append("\n" + Integer.valueOf(id).toString());
    }


    public void read() {

    }
}
