package com.civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.civ.model.Settlements.Settlement;
import com.civ.utils.Assets;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SecondFighter extends Fighter {
    static TextureRegion SECOND = Assets.textureAtlas.findRegion("v2");
    public SecondFighter(float x, float y, float w, float h) {
        super(SECOND, x, y, w, h);
        power = 1;
        steps = 4;
        moveBounds = new Rectangle(x - steps, y - steps, 2 * steps, 2 * steps);
    }

    public void write(StringBuilder data) {
        data.append("\nwarriror II");
        super.write(data);
    }


    public void read() {

    }

    @Override
    public void killUnit(Unit unit) {
        if(unit.getClass() == Civil.class || unit.getClass() == FirstFighter.class){
            unit.setAlive(false);
        }
    }

    @Override
    public void destroyObject(Settlement settlement) {

    }

    @Override
    public boolean canKillUnit(Unit unit) {
        if(unit.getClass() == Civil.class || unit.getClass() == FirstFighter.class){
            return true;
        }
        return false;
    }
}
