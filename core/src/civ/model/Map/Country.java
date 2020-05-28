package civ.model.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

import civ.model.Settlements.City;
import civ.model.Settlements.Fort;
import civ.model.Units.Unit;
import civ.utils.Assets;
import civ.view.GameScreen;


public class Country{
    int cost;
    int costPerVerlauf;
    Sprite image;
    ArrayList<Segment> country;
    ArrayList<Unit> units;
    ArrayList<Segment> abroad;
    ArrayList<City>countryCities;
    String name;
    TextureRegion city;
    Color color;
    public static TextureRegion defTexture = Assets.textureAtlas.findRegion("defulttexture");
    public static TextureRegion farm = Assets.textureAtlas.findRegion("f1");
    public static TextureRegion fort = Assets.textureAtlas.findRegion("k1");

    public Country(Color color, String name){
        image = new Sprite(defTexture);
        this.color = color;
        image.setColor(this.color);
        this.name = name;
        country = new ArrayList<>();
        abroad = new ArrayList<>();
        units = new ArrayList<>();
        city = null;
    }

    public Country(){
        image = null;
        country = null;
        units = null;
        name = null;
        city = null;
        color = null;
    }

    public Country(Color color, String name, TextureRegion city){
        image = new Sprite(defTexture);
        this.color = color;
        image.setColor(this.color);
        this.name = name;
        country = new ArrayList<>();
        units = new ArrayList<>();
        abroad = new ArrayList<>();
        countryCities = new ArrayList<>();
        this.city = city;
        cost = 100;
        costPerVerlauf = 0;
    }

    public void addSegment(Segment s){
        s.setColor(image);
        country.add(s);
        if(s.getSettlement()!= null){
            if(s.getSettlement().getClass() == City.class){
                countryCities.add((City)s.getSettlement());
                s.getSettlement().setSprite(city);
                resetCityAreas();
            }
        }
        fillAbroad();
    }

    public void resetAbroad(){
        for (int i = 0; i < country.size(); i++) {
            Segment s = country.get(i);
            if(abroad.contains(s)){
                abroad.remove(s);
            }
        }
    }

    public void fillAbroad(){
        abroad = new ArrayList<>();
        for (Segment segment:
                country) {
            Segment abroadSegmentLeft = null;
            Segment abroadSegmentRight = null;
            Segment abroadSegmentUp = null;
            Segment abroadSegmentDown = null;

            if(segment.id > 1) {
                abroadSegmentLeft = GameScreen.map.map.get(segment.id - 2);
            }

            if(segment.id < GameScreen.map.WIDTH * GameScreen.map.HEIGHT) {
                abroadSegmentRight = GameScreen.map.map.get(segment.id);
            }

            if(segment.id > GameScreen.map.WIDTH){
                abroadSegmentDown = GameScreen.map.map.get(segment.id - GameScreen.map.WIDTH - 1);
            }
            if(segment.id < GameScreen.map.WIDTH * (GameScreen.map.HEIGHT - 1)){
                abroadSegmentUp = GameScreen.map.map.get(segment.id + GameScreen.map.WIDTH - 1);
            }
            if(abroadSegmentDown != null && !country.contains(abroadSegmentDown) && !abroad.contains(abroadSegmentDown)){
                abroad.add(abroadSegmentDown);
            }
            if(abroadSegmentLeft != null && !country.contains(abroadSegmentLeft) && !abroad.contains(abroadSegmentLeft)){
                abroad.add(abroadSegmentLeft);
            }
            if(abroadSegmentRight != null && !country.contains(abroadSegmentRight) && !abroad.contains(abroadSegmentRight)){
                abroad.add(abroadSegmentRight);
            }
            if(abroadSegmentUp != null && !country.contains(abroadSegmentUp) && !abroad.contains(abroadSegmentUp)){
                abroad.add(abroadSegmentUp);
            }
        }

        resetAbroad();

    }

    public boolean hasSegment(Segment s){
        if(country.contains(s)){
            return true;
        }
        return false;
    }

    public void deleteSegment(Segment s){
        if(s.getSettlement() != null && s.getSettlement().getClass() == City.class){
            countryCities.remove(s.getSettlement());
        }
        country.remove(s);
    }

    public ArrayList<Segment> getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public void setCity(TextureRegion city) {
        this.city = city;
    }

    public TextureRegion getCity() {
        return city;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void write(StringBuilder data) {
        data.append("\n" + name);
        data.append("\n" + Integer.valueOf(cost).toString());
        data.append("\n" + Integer.valueOf(country.size()).toString());
        data.append("\n" + Integer.valueOf(units.size()).toString());
        for (Segment s:
             country) {
            s.writeOnlyID(data);
        }
        for (Unit u:
             units) {
            u.writeOnlyID(data);
        }
    }

    public void updateUnitsPowers(){
        for (Unit u:
                units) {
            u.resetPower();
        }
        for (Segment s:
             country) {
            if(s.getSettlement() != null && s.getSettlement().getClass() == Fort.class) {
                for (Unit u :
                        s.getUnits()) {
                    u.setPower(u.getRealPower() + 1);
                }
            }
        }
    }


    public void update(){
        costPerVerlauf = 0;
        updateUnitsPowers();
        for (Segment s:
             country) {
            if(s.getSettlement() != null){
                if(s.getSettlement().getClass() == City.class){
                    City c = (City)s.getSettlement();
                    c.update();
                    costPerVerlauf += c.getCostPerVerlauf();
                }
                else if(s.getSettlement().getClass() == Fort.class){
                    costPerVerlauf -= Fort.COST_PER_VERLAUF;
                    continue;
                }
                for (Unit u:
                     s.getUnits()) {
                    costPerVerlauf -= u.getCostPerVerlauf();
                }
            }
        }

        cost += costPerVerlauf;

        if(cost < 0){
            for (int i = 0; i < units.size(); i++) {
                units.remove(i);
            }

            for (Segment s:
                 country) {
                if(s.getUnits().size() != 0){
                    for (int i = 0; i < s.getUnits().size(); i++) {
                        s.getUnits().remove(i);
                    }
                }
            }
        }


    }

    public boolean canUnitToGoToSegment(Segment segment){
        if(country.contains(segment) || abroad.contains(segment)){
            return true;
        }
        return false;
    }

    //TODO With abroad
    public ArrayList<Segment> getAllRegion(){
        ArrayList<Segment> arrayList = new ArrayList();
        for (Segment s:
             country) {
            arrayList.add(s);
        }
        for (Segment s:
                abroad) {
            arrayList.add(s);
        }
        return arrayList;
    }

    public boolean hasCities(){
        if(countryCities.size() != 0){
            return true;
        }
        return false;
    }

    public void resetSegment(Segment segment){
        for (Segment s:
             country) {
            if(s.getId() == segment.id){
                s = segment;
            }
        }
    }

    public static boolean isNearWater(Segment segment){
        Segment s = GameScreen.map.getMap().get(segment.id - 1 - GameScreen.map.WIDTH); //downSegment
        if(s.getPoint().getClass() == Water.class){
            return true;
        }
        s = GameScreen.map.getMap().get(segment.id - 1 + GameScreen.map.WIDTH); //upSegment
        if(s.getPoint().getClass() == Water.class){
            return true;
        }
        s = GameScreen.map.getMap().get(segment.id - 2); //leftSegment
        if(s.getPoint().getClass() == Water.class){
            return true;
        }
        s = GameScreen.map.getMap().get(segment.id); //rightSegment
        if(s.getPoint().getClass() == Water.class){
            return true;
        }
        return false;
    }

    public boolean canBuyUnit(int costUnit){
        if(cost >= costUnit){
            return true;
        }
        return false;
    }

    public void payForUnit(int costUnit){
        cost -= costUnit;
    }

    public int getCost() {
        return cost;
    }

    public void resetCityAreas(){
        for (Segment segment:
             country) {
            if(segment.getSettlement() != null && segment.getSettlement().getClass() == City.class){
                City city = (City) segment.getSettlement();
                for (Segment seg:
                     country) {
                    if(city.getAreaCity().contains(seg.getPoint().getBounds().getBoundingRectangle()) && !city.getAreaNearCity().contains(seg)){
                        city.getAreaNearCity().add(seg);
                    }
                }
            }
        }
    }

    public void resetCountry(){
        countryCities = new ArrayList<>();
        for (Segment s:
             country) {
            if(s.getSettlement() != null && s.getSettlement().getClass() == City.class){
                countryCities.add((City)s.getSettlement());
            }
        }
    }

    public boolean isAbleToWorkHere(Segment s){
        for (Segment segment:
                country) {
            if(segment.getSettlement() != null && segment.getSettlement().getClass() == City.class){
                City city = (City) segment.getSettlement();
                if(city.getAreaNearCity().contains(s)){
                    return true;
                }
            }
        }
        return false;
    }

    public Unit getTheNearestFoesUnitTo(Unit unit){
        Unit foesUnit = null;
        ArrayList<Unit>foesUnits = new ArrayList<>();
        for (Segment s:
             abroad) {
            for (Unit u:
                 s.getUnits()) {
                if(!units.contains(u)){
                    foesUnits.add(u);
                }
            }
        }

        if(foesUnits.size() != 0) {
            double distance = Math.sqrt(Math.pow(unit.getBounds().getX() - foesUnits.get(0).getBounds().getX(), 2) + Math.pow(unit.getBounds().getY() - foesUnits.get(0).getBounds().getY(), 2));
            double temp = 0;
            foesUnit = foesUnits.get(0);
            for (Unit u :
                    foesUnits) {
                temp = Math.sqrt(Math.pow(unit.getBounds().getX() - u.getBounds().getX(), 2) + Math.pow(unit.getBounds().getY() - u.getBounds().getY(), 2));
                if(temp < distance){
                    distance = temp;
                    foesUnit = u;
                }
            }
        }

        return foesUnit;
    }

    public Segment getTheNearestSegmentToTarget(Segment targetSegment, Unit unit){
        Segment nearestSegment = null;
        ArrayList<Segment>moveAbroad = new ArrayList<>();

        for (Segment s:
             getAllRegion()) {
            if(unit.isAbleToMoveToSegement(s) && s.isAbleToHaveMoreUnits(this) && s.canMoveToSegment(unit) && canUnitToGoToSegment(s)){
                moveAbroad.add(s);
            }
        }

        if(moveAbroad.size() != 0) {
            nearestSegment = moveAbroad.get(0);
            double distance = Math.sqrt(Math.pow(nearestSegment.getPoint().getBounds().getY() - moveAbroad.get(0).getPoint().getBounds().getY(), 2) + Math.pow(nearestSegment.getPoint().getBounds().getY() - moveAbroad.get(0).getPoint().getBounds().getY(), 2));
            for (Segment s :
                    moveAbroad) {
                double tempDistance = Math.sqrt(Math.pow(nearestSegment.getPoint().getBounds().getY() - s.getPoint().getBounds().getY(), 2) + Math.pow(nearestSegment.getPoint().getBounds().getY() - s.getPoint().getBounds().getY(), 2));
                if (distance > tempDistance) {
                    nearestSegment = s;
                    distance = tempDistance;
                }
            }
        }

        return nearestSegment;
    }

    public Segment getSegmentWithThisUnit(Unit unit){
        Segment targetSegment = null;
        for (Segment s:
             getAllRegion()) {
            if(s.getUnits().contains(unit)){
                targetSegment = s;
                break;
            }
        }
        return targetSegment;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public ArrayList<Segment> getAbroad() {
        return abroad;
    }

    public ArrayList<City> getCountryCities() {
        return countryCities;
    }

    public void removeAllUnits(){
        for (int i = 0; i < units.size(); i++) {
            units.remove(i);
        }
    }

}
