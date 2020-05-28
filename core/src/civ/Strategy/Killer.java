package civ.Strategy;


import civ.model.Map.Segment;
import civ.model.Units.Unit;

public interface Killer extends CommonStrategy{
    void killUnit(Unit unit, Segment segment);
    boolean canKillUnit(Unit unit);
}
