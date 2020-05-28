package com.civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.civ.Strategy.Killer;

public abstract class Fighter extends Unit implements Killer {
    int power;
    public Fighter(TextureRegion texture, float x, float y, float w, float h) {
        super(texture, x, y, w, h);
    }
}
