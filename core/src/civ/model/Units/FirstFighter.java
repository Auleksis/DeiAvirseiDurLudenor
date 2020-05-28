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

public class FirstFighter extends Fighter {
    public static int COST = 10;
    private static int POWER = 1;
    static TextureRegion FIRST = Assets.textureAtlas.findRegion("v1");
    public FirstFighter(float x, float y, float w, float h) {
        super(FIRST, x, y, w, h);
        power = POWER;
        steps = 5;
        moveBounds = new Rectangle(x - steps, y - steps, 2 * steps, 2 * steps);
        costPerVerlauf = 5;
    }

    public void write(StringBuilder data) {
        data.append("\nwarriror I");
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
    public void setupGlobalTask(Country country){
        globalTask = new Task(null, this, EnumSet.of(Actions.ACTION_KILL_WORKER, Actions.ACTION_KILL_POSELENEC, Actions.ACTION_EXPLORE), country);
    }

    @Override
    public int getRealPower() {
        return POWER;
    }
}
