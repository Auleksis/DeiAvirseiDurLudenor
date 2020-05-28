package com.civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.civ.Strategy.BuildStrategy;
import com.civ.model.Map.Segment;
import com.civ.model.Settlements.City;
import com.civ.model.Settlements.Settlement;
import com.civ.utils.Assets;
import com.civ.view.GameScreen;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Poselenec extends Civil implements BuildStrategy {
    static TextureRegion POSELENEC = Assets.textureAtlas.findRegion("p1");
    public Poselenec(float x, float y, float w, float h) {
        super(POSELENEC, x, y, w, h);
        steps = 5;
        moveBounds = new Rectangle(x - steps, y - steps, 2 * steps, 2 * steps);
    }

    public void write(StringBuilder data) {
        data.append("\nposelenec");
        super.write(data);
    }


    public void read() {

    }

    @Override
    public void buildSettlement(int type) {
        Segment s = forBuildSegment();
        if(type == 0){
             s.setSettlement(new City(getCountryTextureCity(), bounds.getX(), bounds.getY(), bounds.getBoundingRectangle().width, bounds.getBoundingRectangle().height));
        }
        canMove = false;
    }

    @Override
    public void destroyObject(Settlement settlement) {

    }
}
