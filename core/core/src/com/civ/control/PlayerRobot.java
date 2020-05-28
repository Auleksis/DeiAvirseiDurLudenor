package com.civ.control;

import com.civ.model.Units.Unit;
import com.civ.model.Units.Worker;

public class PlayerRobot implements Robot {
    Player player;
    @Override
    public void update() {
        for (Unit u:
             player.getCountry().getUnits()) {
            if(u.getClass() == Worker.class){
                Worker worker = (Worker)u;
                if(worker.isWorking()){
                    worker.progressBuilding();
                }
            }
        }
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }
}
