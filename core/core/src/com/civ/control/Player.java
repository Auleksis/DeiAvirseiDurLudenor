package com.civ.control;

import com.civ.model.Map.Country;
import com.civ.model.Units.Unit;

public class Player{
    Robot robot;
    Country country;
    public Player(Country country){
        this.country = country;
        robot = null;
    }

    public Player(Robot robot, Country country) {
        this.robot = robot;
        this.country = country;
        this.robot.setPlayer(this);
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Robot getRobot() {
        return robot;
    }

    public void finishVerlauf(){
        for (Unit u :
                country.getUnits()) {
            u.setCanMove(false);
            u.setActive(false);
        }
    }

    public void startVerlauf(){
        for (Unit u:
             country.getUnits()) {
            u.setCanMove(true);
        }
    }
}
