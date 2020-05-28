package civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


import java.util.EnumSet;

import civ.Strategy.BuildStrategy;
import civ.Strategy.Task;
import civ.model.Map.Country;
import civ.model.Map.Segment;
import civ.model.Map.Water;
import civ.model.Settlements.City;
import civ.model.Settlements.Settlement;
import civ.utils.Assets;
import civ.view.GameScreen;

public class Poselenec extends Civil implements BuildStrategy {
    public static int COST = 30;
    private static int POWER = 0;
    static TextureRegion POSELENEC = Assets.textureAtlas.findRegion("p1");
    public Poselenec(float x, float y, float w, float h) {
        super(POSELENEC, x, y, w, h);
        steps = 5;
        moveBounds = new Rectangle(x - steps, y - steps, 2 * steps, 2 * steps);
        actions = EnumSet.of(Actions.ACTION_MOVE, Actions.ACTION_CREATE_CITY);
        power = POWER;
        costPerVerlauf = 15;
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
        s.removeUnit(this);
        Country c = GameScreen.map.getCountryFromSegment(s);
        c.getUnits().remove(this);
        canMove = false;
    }

    @Override
    public void destroyObject(Segment segment) {
        segment.setSettlement(null);
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
        globalTask = new Task(null, this, EnumSet.of(Actions.ACTION_CREATE_CITY), country);
    }

    @Override
    public int getRealPower() {
        return POWER;
    }
}
