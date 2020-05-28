package civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.EnumSet;

import civ.Game;
import civ.Strategy.Killer;
import civ.control.Player;
import civ.model.Map.Country;
import civ.model.Map.Segment;
import civ.view.GameScreen;

public abstract class Fighter extends Unit implements Killer {
    public Fighter(TextureRegion texture, float x, float y, float w, float h) {
        super(texture, x, y, w, h);
        actions = EnumSet.of(Actions.ACTION_MOVE, Actions.ACTION_DESTROY, Actions.ACTION_KILL_THIS_UNIT);
    }

    @Override
    public void killUnit(Unit unit, Segment segment) {
        Country country = GameScreen.map.getCountryFromSegment(segment);
        if(country != null) {
            country.getUnits().remove(unit);
        }
        segment.removeUnit(unit);
    }

    @Override
    public boolean canKillUnit(Unit unit) {
        if(this.getRealPower() > unit.power || (unit.getClass() == ThirdFighter.class && this.getClass() == ThirdFighter.class)){
            return true;
        }
        return false;
    }
}
