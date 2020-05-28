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

public class ThirdFighter extends Fighter {
    public static int COST = 30;
    private static int POWER = 3;
    static TextureRegion THIRD = Assets.textureAtlas.findRegion("v3");
    public ThirdFighter(float x, float y, float w, float h) {
        super(THIRD, x, y, w, h);
        power = 3;
        steps = 3;
        moveBounds = new Rectangle(x - steps, y - steps, 2 * steps, 2 * steps);
        costPerVerlauf = 15;
    }

    public void write(StringBuilder data) {
        data.append("\nwarriror III");
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
        globalTask = new Task(null, this, EnumSet.of(Actions.ACTION_DESTROY_CITY, Actions.ACTION_KILL_SECONDFIGHTER, Actions.ACTION_KILL_THIRDFIGHTER), country);
    }

    @Override
    public int getRealPower() {
        return POWER;
    }
}
