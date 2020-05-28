package civ.model.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import java.util.ArrayList;

import civ.Strategy.Killer;
import civ.model.Settlements.City;
import civ.model.Settlements.Farm;
import civ.model.Settlements.Fort;
import civ.model.Settlements.Settlement;
import civ.model.Units.Civil;
import civ.model.Units.FirstFighter;
import civ.model.Units.Poselenec;
import civ.model.Units.SecondFighter;
import civ.model.Units.Ship;
import civ.model.Units.ThirdFighter;
import civ.model.Units.Unit;
import civ.model.Units.Worker;
import civ.view.GameScreen;

public class Segment  {
    Point point;
    Settlement settlement;
    public static float ALPHA = 0.4f;
    Sprite country;
    static Sprite tempVerlauf = new Sprite(Country.defTexture);
    boolean leightVersion;

    ArrayList<Unit> units;
    public static final int MAX_UNITS_SEGMENT = 5;
    public static final int MAX_UNITS_EARTH_WATER = 2;
    public static final int MAX_UNITS_FORT = 4;
    public static final int MAX_UNITS_CITY = 5;

    static int ID = 0;
    int id;


    public Segment(Point p){
        point = p;

        units = new ArrayList<>();

        settlement = null;
        country = null;
        ID++;
        id = ID;

        leightVersion = false;
    }


    public void setPoint(Point point) {
        this.point = point;
    }

    public void setColor(Sprite sprite) {
        if(sprite != null) {
            country = new Sprite(sprite);
            country.setPosition(point.getBounds().getX(), point.getBounds().getY());
            country.setSize(point.width, point.height);
            country.setAlpha(ALPHA);
        }else{
            country = null;
        }
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }


    public void draw(SpriteBatch batch){
        point.draw(batch);
        if(settlement != null)
            settlement.draw(batch);
        if(country != null)
            country.draw(batch);
        if(leightVersion){
            tempVerlauf.draw(batch);
        }
        for (Unit u:
             units) {
            if(u != null) {
                if(u.getClass() == Ship.class){
                    Ship s = (Ship) u;
                    for (Unit unit:
                            s.getPassengers()) {
                        if(unit.isDrawable())
                            unit.draw(batch);
                    }
                }
                if (u.isDrawable())
                    u.draw(batch);
            }
        }

    }

    public Point getPoint() {
        return point;
    }

    public static void changeAlpha(ArrayList<Segment> map){
        for (Segment s:
             map) {
            if(s.country != null) {
                s.country.setAlpha(ALPHA);
            }
        }
    }

    public void setLeightVersion(boolean leightVersion) {
        this.leightVersion = leightVersion;
    }

    public void changeAlphaSegement(float alpha){
        leightVersion = true;
        tempVerlauf.setAlpha(alpha);
    }

    public Settlement getSettlement() {
        return settlement;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public boolean hasUnit(int id){
        for (Unit u:
             units) {
            if(u.getId() == id){
                return true;
            }
            else if(u.getClass() == Ship.class){
                Ship s = (Ship) u;
                for (Unit un:
                     s.getPassengers()) {
                    if(un.getId() == id){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Unit getUnit(int targetID){
        for (Unit u:
             units) {
            if(u.getId() == targetID){
                return u;
            }
            else if(u.getClass() == Ship.class){
                Ship ship = (Ship)u;
                for (Unit y:
                     ship.getPassengers()) {
                    if(y.getId() == targetID){
                        return y;
                    }
                }
            }
        }
        return null;
    }

    public void setUnitsDrawables(int targetId){
        for (Unit u:
             units) {
            if(u != null) {
                u.setDrawable(false);
                u.setActive(false);
            }
            if(u.getId() == targetId) {
                u.setDrawable(true);
                u.setActive(true);
                if(u.getClass() == Ship.class){
                    Ship s = (Ship) u;
                    for (Unit un:
                         s.getPassengers()) {
                        un.setDrawable(false);
                        un.setActive(false);
                    }
                }
            }else{
                if(u.getClass() == Ship.class){
                    Ship s = (Ship) u;
                    for (Unit un:
                         s.getPassengers()) {
                        if(un.getId() == targetId){
                            un.setDrawable(true);
                            un.setActive(true);
                        }
                    }
                }
            }
        }
    }

    public void setLastUnitDrawable(){
        if(units.size() != 0){
            units.get(units.size() - 1).setDrawable(true);
        }
    }

    public boolean isHereShip(){
        for (Unit u:
             units) {
            if(u.getClass() == Ship.class)
                return true;
        }
        return false;
    }

    public Ship getNearFreeShip(){
        for (Unit u:
             units) {
            if(u.getClass() == Ship.class){
                Ship s = (Ship) u;
                if(s.isFreeSpace()){
                    return s;
                }
            }
        }
        return null;
    }

    public Ship getSomeShip(){
        for (Unit u:
             units) {
            if(u.getClass() == Ship.class){
                return (Ship)u;
            }
        }
        return null;
    }

    //TODO reset, сделать проверку на воду
    public void resetUnitsDrawables(){
        for (Unit u:
             units) {
            if(u.getClass() != Ship.class)
                u.setDrawable(false);
        }
        if(units.size() != 0 && point.getClass() != Water.class) {
            units.get(units.size() - 1).setDrawable(true);
        }
    }

    public boolean addUnit(Unit unit){
        if(units == null){
            units = new ArrayList<>();
        }
        int i = 0;
        for (Unit u:
             units) {
            i++;
        }

        if(settlement != null) {
            if(settlement.getClass() == City.class) {
                if (i <= MAX_UNITS_CITY - 1) {
                    if(unit.getClass() == Ship.class && Country.isNearWater(this)){
                        units.add(unit);
                        return true;
                    }
                    else {
                        units.add(unit);
                        return true;
                    }
                } else {
                    return false;
                }
            }else if(settlement.getClass() == Fort.class){
                if (i <= MAX_UNITS_FORT - 1) {
                    if(unit.getClass() == Ship.class && Country.isNearWater(this)){
                        units.add(unit);
                        unit.setPower(unit.getRealPower() + 1);
                        return true;
                    }
                    else {
                        units.add(unit);
                        unit.setPower(unit.getRealPower() + 1);
                        return true;
                    }
                } else {
                    return false;
                }
            }
            else if(settlement.getClass() == Farm.class){
                if(i <= MAX_UNITS_EARTH_WATER - 1){
                    units.add(unit);
                    return true;
                }
            }
        }else {
            if (point.getClass() == Water.class) {
                if (unit.getClass() == Ship.class && i <= MAX_UNITS_EARTH_WATER - 1) {
                    units.add(unit);
                    return true;
                }
                else if (isHereShip()) {
                        Ship s;
                        s = getSomeShip();
                        if(s != null){
                            if(s.hasUnit(unit)){
                                addShipUnitToSegment(unit);
                                return true;
                            }
                        }
                        s = getNearFreeShip();
                        if (s != null) {
                            unit.setDrawable(false);
                            s.addPassenger(unit);
                            addShipUnitToSegment(unit);
                            return true;
                        } else {
                            return false;
                        }
                    }
                    else {
                        return false;
                    }
            }
            else{
                if(i <= MAX_UNITS_EARTH_WATER - 1){
                    units.add(unit);
                    return true;
                }
            }
        }

        return false;
    }

    public void removeUnit(Unit u){
        if(u.getClass() != Ship.class) {
            for (Unit y :
                    units) {
                if (y.getClass() == Ship.class) {
                    Ship ship = (Ship) y;
                    ship.deletePassenger(u.getId());
                }
            }
        }else{
            Ship ship = (Ship) u;
            for (int i = 0; i < units.size(); i++) {
                /*
                for (int j = 0; j < ship.getPassengers().size(); j++) {
                    if(units.get(i) == ship.getPassengers().get(j)){
                        units.remove(i);
                    }
                }

                 */
                for (Unit unit:
                     ship.getPassengers()) {
                    if(units.get(i) == unit){
                        units.remove(i);
                    }
                }
            }
        }
        units.remove(u);
    }

    public boolean canMoveToSegment(Unit unit){
        Killer killer = null;
        if(unit instanceof Killer){
            killer = (Killer)unit;
        }
        Country c = GameScreen.map.getCountryFromSegment(this);
        if(killer != null) {
            for (Unit u :
                    units) {
                if(c != null) {
                    if (!killer.canKillUnit(u) && !c.getUnits().contains(killer)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Unit mostPowerfulUnit(){
        for (Unit u:
             units) {
            if(u.getClass() == ThirdFighter.class){
                return u;
            }
            else if(u.getClass() == SecondFighter.class){
                return u;
            }
            else if(u.getClass() == FirstFighter.class){
                return u;
            }
            else if(u.getClass() == Worker.class){
                return u;
            }
            else if(u.getClass() == Poselenec.class){
                return u;
            }
        }
        return null;
    }

    void addShipUnitToSegment(Unit u){
        units.add(u);
    }

    public void write(StringBuilder data) {
        point.write(data);
        if(settlement != null) {
            settlement.write(data);
        }
        if(settlement == null){
            data.append("\nnull");
        }
        data.append("\n" + (units.size() != 0?Integer.valueOf(units.size()).toString(): "null"));
        for (Unit u:
             units) {
            u.write(data);
        }
    }

    public void writeOnlyID(StringBuilder data){
        data.append("\n" + Integer.valueOf(id).toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void reboot(){
        ID = 0;
    }

    public void update(){
        for (int i = 0; i < units.size(); i++) {
            Unit u = units.get(i);
            if(!u.isAlive()){
                units.remove(i);
            }
        }
    }

    public boolean isAbleToHaveMoreUnits(Country country){
        if(country.getCountry().contains(this)) {
            if (settlement == null) {
                if (units.size() <= MAX_UNITS_EARTH_WATER - 1) {
                    return true;
                }
            } else {
                if (settlement.getClass() == City.class) {
                    if (units.size() <= MAX_UNITS_CITY - 1) {
                        return true;
                    }
                } else if (settlement.getClass() == Fort.class) {
                    if (units.size() <= MAX_UNITS_FORT - 1)
                        return true;
                } else {
                    if (units.size() <= MAX_UNITS_EARTH_WATER - 1) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    public void removeAllUnits(){
        units.retainAll(units);
    }

    public boolean containsThisTypeOfUnits(Class unit){
        for (Unit u:
             units) {
            if(u.getClass() == unit){
                return true;
            }
        }
        return false;
    }

    public boolean containsFoesThisTypeOfUnits(Class unit, Country country){
        for (Unit u:
                units) {
            if(u.getClass() == unit && !country.getUnits().contains(u)){
                return true;
            }
        }
        return false;
    }

    public Sprite getCountry() {
        return country;
    }
}
