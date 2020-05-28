package com.civ.control;

import com.badlogic.gdx.Gdx;
import com.civ.model.Map.Country;
import com.civ.model.Map.Earth;
import com.civ.model.Map.Point;
import com.civ.model.Map.Segment;
import com.civ.model.Map.Water;
import com.civ.model.Settlements.City;
import com.civ.model.Settlements.Farm;
import com.civ.model.Settlements.Fort;
import com.civ.model.Settlements.Settlement;
import com.civ.model.UI.UI;
import com.civ.model.Units.FirstFighter;
import com.civ.model.Units.Poselenec;
import com.civ.model.Units.SecondFighter;
import com.civ.model.Units.Ship;
import com.civ.model.Units.ThirdFighter;
import com.civ.model.Units.Unit;
import com.civ.model.Units.Worker;
import com.civ.view.GameScreen;

public class SegmentController {

    public static void handleGameInput(){
        GameScreen.temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        GameScreen.camera.unproject(GameScreen.temp);
        float touchX = GameScreen.temp.x;
        float touchY = GameScreen.temp.y;
        Segment s;
        for (int i = 0; i < GameScreen.currentPlayer.getCountry().getAllRegion().size(); i++) {
            s = GameScreen.currentPlayer.getCountry().getAllRegion().get(i);
            if(s.getPoint().clicked(touchX, touchY)) {
                if (GameScreen.readyToInf) {
                    String countryName = "";
                    countryName = GameScreen.currentPlayer.country.getName();
                    UI.countryName.setText(countryName.equals("") ? "" : countryName);
                    UI.segmentType.setText(s.getPoint().getClass() == Earth.class ? "Eatrh" : "Water");
                    if (s.getSettlement() != null) {
                        if (s.getSettlement().getClass() == City.class) {
                            UI.segmentSettlement.setText("City");
                            GameScreen.ui.CITY = (City) s.getSettlement();
                        } else if (s.getSettlement().getClass() == Fort.class) {
                            UI.segmentSettlement.setText("Fort");
                        } else if (s.getSettlement().getClass() == Farm.class) {
                            UI.segmentSettlement.setText("Farm");
                        } else {
                            UI.segmentSettlement.setText("");
                        }
                    } else {
                        UI.segmentSettlement.setText("");
                    }
                    GameScreen.ui.addUnitButton(s.getUnits());
                    GameScreen.ui.getInformWindow().setVisible(true);
                }

                if(GameScreen.moveUnit){
                    for (Country c:
                            GameScreen.map.countries) {
                        for (Unit u:
                                c.getUnits()) {
                            if(u.isActive()) {
                                if (u.isAbleToMove()) {
                                    if (u.isAbleToMoveToSegement(s)) {
                                        if (c.canUnitToGoToSegment(s)) {
                                            if (s.canMoveToSegment(u)) {
                                                for (Segment countryCegment :
                                                        c.getCountry()) {
                                                    if (s == countryCegment && s.hasUnit(u.getId())) {
                                                        return;
                                                    }
                                                }
                                                u.move(s);
                                                for (Segment segment :
                                                        c.getCountry()) {
                                                    if (segment.hasUnit(u.getId()) && segment != s) {
                                                        segment.removeUnit(u);
                                                    }
                                                }
                                                if (!c.hasSegment(s)) {
                                                    if (s.getSettlement() != null && s.getSettlement().getClass() == City.class) {
                                                        Settlement settlement = s.getSettlement();
                                                        settlement.setSprite(c.getCity());
                                                        s.setSettlement(settlement);
                                                    }
                                                    c.addSegment(s);
                                                    c.getCountry().add(s);
                                                    c.fillAbroad();
                                                    for (Country coun :
                                                            GameScreen.map.countries) {
                                                        if (coun.hasSegment(s)) {
                                                            coun.deleteSegment(s);
                                                        }
                                                    }
                                                }
                                                GameScreen.ui.addUnitButton(s.getUnits());
                                                UI.countryName.setText(c.getName());
                                                UI.segmentType.setText(s.getPoint().getClass() == Earth.class ? "Eatrh" : "Water");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    public static void handleInput(){
        GameScreen.temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        GameScreen.camera.unproject(GameScreen.temp);
        float touchX = GameScreen.temp.x;
        float touchY = GameScreen.temp.y;
        Segment s;
        for (int i = 0; i < GameScreen.map.getMap().size(); i++) {
            s = GameScreen.map.getMap().get(i);
            if(s.getPoint().clicked(touchX, touchY)){
                if(GameScreen.readyToInf){
                    String countryName = "";
                    for (Country c:
                         GameScreen.map.countries) {
                        if(c.getCountry().contains(s)){
                            countryName = c.getName();
                        }
                    }
                    UI.countryName.setText(countryName.equals("")?"":countryName);
                    UI.segmentType.setText(s.getPoint().getClass() == Earth.class? "Eatrh": "Water");
                    if(s.getSettlement() != null){
                        if(s.getSettlement().getClass() == City.class){
                            UI.segmentSettlement.setText("City");
                        }
                        else if(s.getSettlement().getClass() == Fort.class){
                            UI.segmentSettlement.setText("Fort");
                        }
                        else if(s.getSettlement().getClass() == Farm.class){
                            UI.segmentSettlement.setText("Farm");
                        }
                        else {
                            UI.segmentSettlement.setText("");
                        }
                    }else{
                        UI.segmentSettlement.setText("");
                    }
                    GameScreen.ui.addUnitButton(s.getUnits());
                    GameScreen.ui.getInformWindow().setVisible(true);
                }
                if(GameScreen.currentCountry != null && GameScreen.addSegmentCountryFlag) {
                    for (Country c:
                         GameScreen.map.countries) {
                        if(c.getCountry().contains(s)){
                            c.getCountry().remove(s);
                        }
                    }
                    if(s.getSettlement() != null && s.getSettlement().getClass() == City.class){
                        Settlement settlement = s.getSettlement();
                        settlement.setSprite(GameScreen.currentCountry.getCity());
                        s.setSettlement(settlement);
                    }
                    GameScreen.currentCountry.addSegment(s);
                }else {
                    if(GameScreen.addSegmentCountryFlag) {
                        s.setColor(null);
                        s.setSettlement(null);
                        for (Country c :
                                GameScreen.map.countries) {
                            if (c.getCountry().contains(s)) {
                                c.getCountry().remove(s);
                            }
                        }
                    }
                }
                if(GameScreen.point == 0){
                    s.setPoint(new Water(s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height));
                }
                if(GameScreen.point == 1){
                    s.setPoint(new Earth(s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height));
                }
                if(GameScreen.currentSettlement != 0){
                    for (Country c:
                        GameScreen.map.countries) {
                        if(c.getCountry().contains(s)){
                            if(s.getPoint().getClass() == Earth.class) {
                                if(GameScreen.currentSettlement == 1) {
                                    s.setSettlement(new City(c.getCity(), s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height));
                                }
                                if(GameScreen.currentSettlement == 2){
                                    s.setSettlement(new Farm(Country.farm, s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height));
                                }
                                if(GameScreen.currentSettlement == 3){
                                    s.setSettlement(new Fort(Country.fort, s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height));
                                }
                                GameScreen.currentSettlement = 0;
                            }
                         }
                    }
                }

                if(GameScreen.currentUnit != -1){
                    for (Country c:
                         GameScreen.map.countries) {
                        if(c.getCountry().contains(s)){
                            Unit unit = null;
                            if(GameScreen.currentUnit == 1){
                                unit = new FirstFighter(s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height);
                            }
                            if(GameScreen.currentUnit == 2){
                                unit = new SecondFighter(s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height);
                            }
                            if(GameScreen.currentUnit == 3){
                                unit = new ThirdFighter(s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height);
                            }
                            if(GameScreen.currentUnit == 4){
                                unit = new Worker(s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height);
                            }
                            if(GameScreen.currentUnit == 5 && s.getPoint().getClass() == Water.class){
                                unit = new Ship(s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height);
                            }
                            if(GameScreen.currentUnit == 6){
                                unit = new Poselenec(s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height);
                            }
                            if(s.addUnit(unit)){
                                c.getUnits().add(unit);
                            }
                            GameScreen.currentUnit = -1;
                        }
                    }
                }

                if(GameScreen.moveUnit){
                    for (Country c:
                         GameScreen.map.countries) {
                        for (Unit u:
                             c.getUnits()) {
                            if(u.isActive()) {
                                for (Segment countryCegment :
                                        c.getCountry()) {
                                    if (s == countryCegment && s.hasUnit(u.getId())) {
                                        return;
                                    }
                                }
                                u.move(s);
                                for (Segment segment :
                                        c.getCountry()) {
                                    if (segment.hasUnit(u.getId()) && segment != s) {
                                        segment.removeUnit(u);
                                    }
                                }
                                if (!c.hasSegment(s)) {
                                    if (s.getSettlement() != null && s.getSettlement().getClass() == City.class) {
                                        Settlement settlement = s.getSettlement();
                                        settlement.setSprite(c.getCity());
                                        s.setSettlement(settlement);
                                    }
                                    c.addSegment(s);
                                    c.getCountry().add(s);
                                    c.fillAbroad();
                                    for (Country coun :
                                            GameScreen.map.countries) {
                                        if (coun.hasSegment(s)) {
                                            coun.deleteSegment(s);
                                        }
                                    }
                                }
                                GameScreen.ui.addUnitButton(s.getUnits());
                                UI.countryName.setText(c.getName());
                                UI.segmentType.setText(s.getPoint().getClass() == Earth.class ? "Eatrh" : "Water");

                            }
                        }
                    }
                }
            }
        }
        for (Country country:
             GameScreen.map.countries) {
            country.update();
            for (Segment segment:
                    country.getCountry()) {
                segment.update();
            }
        }
    }

}
