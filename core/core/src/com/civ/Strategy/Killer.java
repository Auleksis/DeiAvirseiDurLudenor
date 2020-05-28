package com.civ.Strategy;

import com.civ.model.Settlements.Settlement;
import com.civ.model.Units.Unit;

public interface Killer extends CommonStrategy{
    void killUnit(Unit unit);
    boolean canKillUnit(Unit unit);
}
