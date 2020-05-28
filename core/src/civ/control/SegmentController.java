package civ.control;

import com.badlogic.gdx.Gdx;

import civ.Strategy.Killer;
import civ.model.Map.Country;
import civ.model.Map.Segment;
import civ.model.Units.Unit;
import civ.view.GameScreen;


public class SegmentController {

    public static void handleUnitInputMove(Segment segment, Unit u){
        Country c = GameScreen.currentPlayer.country;
        if(u.isAbleToMove()){
            if(u.isAbleToMoveToSegement(segment)){
                if(c.canUnitToGoToSegment(segment)) {
                    if (segment.canMoveToSegment(u)) {
                        if (segment.isAbleToHaveMoreUnits(c)) {
                            for (Segment countryCegment :
                                    c.getCountry()) {
                                if (segment == countryCegment && segment.hasUnit(u.getId())) {
                                    return;
                                }
                            }
                            if(segment.getUnits().size() != 0 && !c.getCountry().contains(segment)){
                                Unit mostPowerfulUnit = segment.mostPowerfulUnit();
                                Killer killer = (Killer) u;
                                killer.killUnit(mostPowerfulUnit, segment);
                            }
                            if(segment.getUnits().size() == 0 || c.getCountry().contains(segment)) {
                                u.move(segment);
                            }
                            for (Segment seg :
                                    c.getCountry()) {
                                if (seg.hasUnit(u.getId()) && seg != segment && segment.getUnits().contains(u)) {
                                    seg.removeUnit(u);
                                    seg.resetUnitsDrawables();
                                }
                            }
                            if (!c.hasSegment(segment) && segment.getUnits().contains(u)) {
                                for (Country coun :
                                        GameScreen.map.countries) {
                                    if (coun.hasSegment(segment)) {
                                        coun.deleteSegment(segment);
                                    }
                                }
                                c.addSegment(segment);
                                c.getCountry().add(segment);
                                c.fillAbroad();
                            }
                        }
                    }
                }
            }else{
                Segment temp = u.getNearestSegmentToTask(segment, GameScreen.currentPlayer.country);
                for (Segment countryCegment :
                        c.getCountry()) {
                    if (temp == countryCegment && temp.hasUnit(u.getId())) {
                        return;
                    }
                }
                u.move(temp);
                for (Segment seg :
                        c.getCountry()) {
                    if (seg.hasUnit(u.getId()) && seg != temp && temp.getUnits().contains(u)) {
                        seg.removeUnit(u);
                        seg.resetUnitsDrawables();
                    }
                }
                if (!c.hasSegment(temp) && temp.getUnits().contains(u)) {
                    for (Country coun :
                            GameScreen.map.countries) {
                        if (coun.hasSegment(temp)) {
                            coun.deleteSegment(temp);
                        }
                    }
                    c.addSegment(temp);
                    c.getCountry().add(temp);
                    c.fillAbroad();
                }
            }
        }
    }

    public static void handleGameInput(){
        GameScreen.temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        GameScreen.camera.unproject(GameScreen.temp);
        float touchX = GameScreen.temp.x;
        float touchY = GameScreen.temp.y;
        Segment s;

        for (int i = 0; i < GameScreen.map.getMap().size(); i++){
            s = GameScreen.map.getMap().get(i);
            if(s.getPoint().clicked(touchX, touchY)){
                GameScreen.mainUI.inform(s);
            }
        }
    }

}
