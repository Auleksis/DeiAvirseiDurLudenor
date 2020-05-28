package civ.model.Settlements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Fort extends Settlement {
    public static int COST_PER_VERLAUF = 20;
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
