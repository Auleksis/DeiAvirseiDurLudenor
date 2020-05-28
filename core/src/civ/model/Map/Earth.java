package civ.model.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


import civ.utils.Assets;

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

}
