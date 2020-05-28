package civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.EnumSet;

import civ.Strategy.Task;
import civ.model.Map.Country;
import civ.model.Map.Segment;
import civ.model.Map.Water;
import civ.model.Settlements.Settlement;
import civ.utils.Assets;

public class SecondFighter extends Fighter {
    public static int COST = 20;
    private static int POWER = 2;
    static TextureRegion SECOND = Assets.textureAtlas.findRegion("v2");
    public SecondFighter(float x, float y, float w, float h) {
        super(SECOND, x, y, w, h);
        power = POWER;
        steps = 4;
        moveBounds = new Rectangle(x - steps, y - steps, 2 * steps, 2 * steps);
        costPerVerlauf = 10;
    }

    public void write(StringBuilder data) {
        data.append("\nwarriror II");
        super.write(data);
    }


    public void read() {

    }


    @Override
    public void destroyObject(Segment segment) {

    }
    @Override
    public boolean isAbleToMoveToSegement(Segment segment) {
        if(segment.getPoint().getClass() != Water.class || segment.isHereShip()) {
            return super.isAbleToMoveToSegement(segment);
        }
        return false;
    }

    @Override
    public void setupGlobalTask(Country country) {
        globalTask = new Task(null, this, EnumSet.of(Actions.ACTION_DESTROY_CITY, Actions.ACTION_KILL_FIRSTFIGHTER), country);
    }

    @Override
    public int getRealPower() {
        return POWER;
    }
}
