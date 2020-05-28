package civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.EnumSet;

import civ.Strategy.BuildStrategy;
import civ.Strategy.Task;
import civ.model.Map.Country;
import civ.model.Map.Segment;
import civ.model.Map.Water;
import civ.model.Settlements.Farm;
import civ.model.Settlements.Fort;
import civ.model.Settlements.Settlement;
import civ.utils.Assets;

public class Worker extends Civil implements BuildStrategy {
    public static int COST = 10;
    private static int POWER = 0;
    int flag;
    int settlementFlag;
    boolean working;
    public static TextureRegion WORKER = Assets.textureAtlas.findRegion("w");
    public Worker(float x, float y, float w, float h) {
        super(WORKER, x, y, w, h);
        steps = 4;
        power = 0;
        moveBounds = new Rectangle(x - steps, y - steps, 2 * steps, 2 * steps);
        flag = 0;
        settlementFlag = 0;
        working = false;
        actions = EnumSet.of(Actions.ACTION_MOVE, Actions.ACTION_DESTROY, Actions.ACTION_CREATE_FARM, Actions.ACTION_CREATE_FORT, Actions.ACTION_KILL_THIS_UNIT);
        costPerVerlauf = 5;
    }

    public void write(StringBuilder data) {
        data.append("\nworker");
        super.write(data);
    }


    public void read() {

    }

    public void progressBuilding(){
        flag++;
        build();
    }

    public boolean isWorking() {
        return working;
    }

    @Override
    public void buildSettlement(int type) {
        flag = 1;
        settlementFlag = type;
        working = true;
        canMove = false;
    }

    public void build(){
        int d = settlementFlag == BUILD_FARM?BUILD_FARM:BUILD_FORT;
        if(flag == d) {
            Segment s = forBuildSegment();
            if(s != null) {
                if (settlementFlag == 1) {
                    s.setSettlement(new Fort(Country.fort, bounds.getX(), bounds.getY(), bounds.getBoundingRectangle().width, bounds.getBoundingRectangle().height));
                }
                if (settlementFlag == 2) {
                    s.setSettlement(new Farm(Country.farm, bounds.getX(), bounds.getY(), bounds.getBoundingRectangle().width, bounds.getBoundingRectangle().height));
                }
            }
            flag = 0;
            working = false;
        }
    }

    @Override
    public void destroyObject(Segment segment) {
        segment.setSettlement(null);
    }

    @Override
    public void move(Segment segment) {
        super.move(segment);
        working = false;
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
        globalTask = new Task(null, this, EnumSet.of(Actions.ACTION_CREATE_FARM, Actions.ACTION_CREATE_FORT), country);
    }

    @Override
    public int getRealPower() {
        return POWER;
    }
}
