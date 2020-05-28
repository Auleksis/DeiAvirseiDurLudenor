package com.civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.civ.model.Settlements.Settlement;
import com.civ.utils.Assets;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class ThirdFighter extends Fighter {
    static TextureRegion THIRD = Assets.textureAtlas.findRegion("v3");
    public ThirdFighter(float x, float y, float w, float h) {
        super(THIRD, x, y, w, h);
        power = 1;
        steps = 3;
        moveBounds = new Rectangle(x - steps, y - steps, 2 * steps, 2 * steps);
    }

    public void write(StringBuilder data) {
        data.append("\nwarriror III");
        super.write(data);
    }

    public void read() {

    }

    @Override
    public void killUnit(Unit unit) {
        if(unit.getClass() == Civil.class || unit.getClass() == FirstFighter.class || unit.getClass() == SecondFighter.class || unit.getClass() == ThirdFighter.class){
            unit.setAlive(false);
        }
    }

    @Override
    public void destroyObject(Settlement settlement) {

    }

    @Override
    public boolean canKillUnit(Unit unit) {
        if(unit.getClass() == Civil.class || unit.getClass() == FirstFighter.class || unit.getClass() == SecondFighter.class || unit.getClass() == ThirdFighter.class){
            return true;
        }
        return false;
    }
}
