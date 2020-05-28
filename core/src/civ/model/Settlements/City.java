package civ.model.Settlements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import java.util.ArrayList;

import civ.Game;
import civ.model.Map.Segment;
import civ.model.Units.Unit;
import civ.view.GameScreen;

public class City extends Settlement {
    int costPerVerlauf;
    String name;
    Rectangle areaCity;
    ArrayList<Segment>areaNearCity;
    public City(TextureRegion texture, float x, float y, float w, float h) {
        super(texture, x, y, w, h);
        name = "aa";
        areaNearCity = new ArrayList<>();
        areaCity = new Rectangle((int)(x - 3), (int)(y - 3), (int)(w + 6), (int)(h + 6));
        costPerVerlauf = -5;
    }

    public City(float x, float y, float w, float h) {
        super(x, y, w, h);
        name = "aa";
        areaNearCity = new ArrayList<>();
        areaCity = new Rectangle((int)(x - 3), (int)(y - 3), (int)(w + 6), (int)(h + 6));
    }

    public void write(StringBuilder data) {
        data.append("\ncity");
        super.write(data);
    }

    public void update(){
        costPerVerlauf = -5;
        for (Segment s:
             areaNearCity) {
            if(s.getSettlement() != null){
                if(s.getSettlement().getClass() == Farm.class){
                   costPerVerlauf += 5;
                }
            }
        }
    }


    public void read() {

    }

    public String getName() {
        return name;
    }

    public ArrayList<Segment> getAreaNearCity() {
        return areaNearCity;
    }

    public ArrayList<Unit> unitsOnArea(){
        ArrayList<Unit>units = new ArrayList<>();
        for (Segment s:
             areaNearCity) {
            for (Unit u:
                 s.getUnits()) {
                units.add(u);
            }
        }
        return units;
    }

    public Rectangle getAreaCity() {
        return areaCity;
    }

    public boolean hasAreaThisUnit(Unit unit){
        for (Segment s:
             areaNearCity) {
            if(s.getUnits().contains(unit)){
                return true;
            }
        }
        return false;
    }

    public boolean hasAreaThisTypeOfUnit(Class t){
        for (Segment s:
                areaNearCity) {
            for (Unit u:
                 s.getUnits()) {
                if(u.getClass() == t){
                    return true;
                }
            }
        }
        return false;
    }


    public int getCostPerVerlauf() {
        return costPerVerlauf;
    }
}
