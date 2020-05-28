package com.civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.civ.model.Settlements.Settlement;
import com.civ.utils.Assets;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class FirstFighter extends Fighter {
    static TextureRegion FIRST = Assets.textureAtlas.findRegion("v1");
    public FirstFighter(float x, float y, float w, float h) {
        super(FIRST, x, y, w, h);
        power = 1;
        steps = 5;
        moveBounds = new Rectangle(x - steps, y - steps, 2 * steps, 2 * steps);
    }

    public void write(StringBuilder data) {
        data.append("\nwarriror I");
        super.write(data);
    }


    public void read() {

    }

    @Override
    public void killUnit(Unit unit) {
        if(unit.getClass() == Civil.class){
            unit.setAlive(false);
        }
    }

    @Override
    public void destroyObject(Settlement settlement) {

    }

    @Override
    public boolean canKillUnit(Unit unit) {
        if(unit.getClass() == Civil.class){
            return true;
        }
        return false;
    }
}
