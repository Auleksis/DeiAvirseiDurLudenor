package civ.model.Settlements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
