package civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import civ.model.Map.Country;
import civ.model.Map.Segment;
import civ.view.GameScreen;

public abstract class Civil extends Unit {
    public Civil(TextureRegion texture, float x, float y, float w, float h) {
        super(texture, x, y, w, h);
    }
    public Segment forBuildSegment(){
        Segment s = null;

        for (Segment segment:
                GameScreen.map.getMap()) {
            if(segment.hasUnit(id)){
                s = segment;
            }
        }

        return s;
    }

    public TextureRegion getCountryTextureCity(){
        Segment s = forBuildSegment();
        for (Country c:
             GameScreen.map.countries) {
            if(c.hasSegment(s)){
                return c.getCity();
            }
        }
        return null;
    }

    public void build(){

    }
}
