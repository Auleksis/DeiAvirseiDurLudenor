package com.civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.civ.model.Map.Segment;
import com.civ.model.Map.Water;
import com.civ.model.Settlements.Settlement;
import com.civ.utils.Assets;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.ArrayList;

public class Ship extends Fighter {
    public static TextureRegion SHIP = Assets.textureAtlas.findRegion("s1");
    private static final int MAX_PASSENGERS = 2;
    ArrayList<Unit> passengers;
    public Ship(float x, float y, float w, float h) {
        super(SHIP, x, y, w, h);
        steps = 4;
        passengers = new ArrayList<>();
        moveBounds = new Rectangle(x - steps, y - steps, 2 * steps, 2 * steps);
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
        if(segment.getPoint().getClass() == Water.class) {
            super.move(segment);
            for (Unit u :
                    passengers) {
                u.move(segment);
            }
        }
    }

    public void read() {

    }

    @Override
    public void killUnit(Unit unit) {

    }

    @Override
    public void destroyObject(Settlement settlement) {

    }

    @Override
    public boolean canKillUnit(Unit unit) {
        return false;
    }
}
