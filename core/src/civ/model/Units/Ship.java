package civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.EnumSet;

import civ.Strategy.Task;
import civ.model.Map.Country;
import civ.model.Map.Segment;
import civ.model.Map.Water;
import civ.model.Settlements.City;
import civ.model.Settlements.Settlement;
import civ.utils.Assets;

public class Ship extends Fighter {
    public static int COST = 20;
    private static int POWER = 1;
    public static TextureRegion SHIP = Assets.textureAtlas.findRegion("s1");
    private static final int MAX_PASSENGERS = 2;
    ArrayList<Unit> passengers;
    public Ship(float x, float y, float w, float h) {
        super(SHIP, x, y, w, h);
        steps = 4;
        power = POWER;
        passengers = new ArrayList<>();
        moveBounds = new Rectangle(x - steps, y - steps, 2 * steps, 2 * steps);
        costPerVerlauf = 10;
    }

    public boolean addPassenger(Unit unit){
        if(passengers.size() < MAX_PASSENGERS){
            if(unit.getClass() != Ship.class) {
                unit.setDrawable(false);
                passengers.add(unit);
                return true;
            }else {
                return false;
            }
        }else
            return false;
    }

    public void deletePassenger(int targetID){
        for (int i = 0; i < passengers.size(); i++) {
            if(passengers.get(i).getId() == targetID){
                passengers.remove(i);
            }
        }
    }

    public boolean hasUnit(Unit unit){
        for (Unit u:
                passengers) {
            if(unit == u){
                return true;
            }
        }
        return false;
    }

    public boolean hasPassengers(){
        if(passengers.size() != 0){
            return true;
        }
        return false;
    }

    public boolean isFreeSpace(){
        if(passengers.size() < MAX_PASSENGERS){
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<Unit> getPassengers() {
        return passengers;
    }

    public void write(StringBuilder data) {
        data.append("\nship");
        super.write(data);
    }

    @Override
    public void move(Segment segment) {
        super.move(segment);
        for (Unit u :
                passengers) {
            u.move(segment);
        }
    }

    public void read() {

    }


    @Override
    public void destroyObject(Segment segment) {

    }

    @Override
    public boolean isAbleToMoveToSegement(Segment segment) {
        if(segment.getPoint().getClass() == Water.class) {
            return super.isAbleToMoveToSegement(segment);
        }
        else if(segment.getSettlement() != null){
            if(segment.getSettlement().getClass() == City.class) {
                return super.isAbleToMoveToSegement(segment);
            }
        }
        return false;
    }

    @Override
    public void setupGlobalTask(Country country) {
        globalTask = new Task(null, this, EnumSet.of(Actions.ACTION_EXPLORE, Actions.ACTION_KILL_SHIP), country);
    }

    @Override
    public int getRealPower() {
        return POWER;
    }
}
