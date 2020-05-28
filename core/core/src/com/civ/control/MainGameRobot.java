package com.civ.control;

import com.civ.model.Map.Segment;
import com.civ.model.Units.Unit;

import java.util.Random;

public class MainGameRobot implements Robot {
    Player player;
    public MainGameRobot(){}

    @Override
    public void update() {
        Random rnd = new Random();
        for (Unit u :
                player.getCountry().getUnits()) {
            Segment s = player.getCountry().getAllRegion().get(rnd.nextInt(player.country.getAllRegion().size()));
            u.move(s);
            if(!player.getCountry().hasSegment(s))
                player.getCountry().addSegment(s);
        }
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

}
