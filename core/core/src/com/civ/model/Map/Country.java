package com.civ.model.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.civ.model.Units.Unit;
import com.civ.utils.Assets;
import com.civ.view.GameScreen;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;



public class Country{
    Sprite image;
    ArrayList<Segment> country;
    ArrayList<Unit> units;
    ArrayList<Segment> abroad;
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
        this.city = city;
    }

    public void addSegment(Segment s){
        s.setColor(image);
        country.add(s);
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
        for (Segment segment:
                country) {
            Segment abroadSegmentLeft = null;
            Segment abroadSegmentRight = null;
            Segment abroadSegmentUp = null;
            Segment abroadSegmentDown = null;

                abroadSegmentLeft = GameScreen.map.map.get(segment.id - 2);

                abroadSegmentRight = GameScreen.map.map.get(segment.id);

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


    public void update(){
        for (int i = 0; i < units.size(); i++) {
            Unit u = units.get(i);
            if(!u.isAlive()){
                units.remove(i);
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
        for (Segment s:
             country) {
            if(s.getSettlement() != null){
                return true;
            }
        }
        return false;
    }
}
