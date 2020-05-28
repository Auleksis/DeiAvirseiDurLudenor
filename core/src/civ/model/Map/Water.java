package civ.model.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import civ.utils.Assets;

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

}
