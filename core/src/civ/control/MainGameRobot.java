package civ.control;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

import civ.Strategy.Task;
import civ.model.Map.Country;
import civ.model.Map.Segment;
import civ.model.Settlements.City;
import civ.model.Units.Actions;
import civ.model.Units.Civil;
import civ.model.Units.FirstFighter;
import civ.model.Units.SecondFighter;
import civ.model.Units.Ship;
import civ.model.Units.ThirdFighter;
import civ.model.Units.Unit;
import civ.model.Units.Worker;
import civ.utils.Assets;
import civ.view.GameScreen;

public class MainGameRobot implements Robot {
    Player player;
    ArrayList<Task>globalWorkersTasks;
    Random random;

    public MainGameRobot(){
        globalWorkersTasks = new ArrayList<>();
        random = new Random();
    }

    @Override
    public void update() {

        //TODO UPDATE CITIES
        player.getCountry().resetCountry();
        player.getCountry().resetCityAreas();

        //TODO ECONOMICS
        player.getCountry().update();

        //TODO EXECUTE TASKS

        for (int i = 0; i < player.getCountry().getUnits().size(); i++) {
            Unit u = player.country.getUnits().get(i);
            //if(u.getClass() == FirstFighter.class || u.getClass() == SecondFighter.class || u.getClass() == ThirdFighter.class)
                u.executeTask();
        }



         //TODO Условия покупки юнита
        int worksers = 0;
        int wF = 0;
        int wS = 0;
        int wT = 0;
        for (Unit u:
                player.getCountry().getUnits()) {
            if(u.getClass() == Worker.class){
                worksers++;
            }
            if(u.getClass() == FirstFighter.class){
                wF++;
            }
            if(u.getClass() == SecondFighter.class){
                wS++;
            }
            if(u.getClass() == ThirdFighter.class){
                wT++;
            }
        }



        //TODO BUY WORKERS
        for (int i = worksers; i < player.getCountry().getCountryCities().size(); i++){
            if(player.getCountry().canBuyUnit(Worker.COST)){
                for (City city:
                        player.getCountry().getCountryCities()) {
                    if(!city.hasAreaThisTypeOfUnit(Worker.class)){
                        Segment segment = null;
                        for (Segment s:
                             city.getAreaNearCity()) {
                            if(s.getSettlement() == city){
                                segment = s;
                                Worker worker = new Worker(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height);
                                if (segment.addUnit(worker)) {
                                    player.getCountry().getUnits().add(worker);
                                    player.getCountry().payForUnit(Worker.COST);
                                }
                            }
                        }

                    }
                }
            }
        }
        //TODO END WORKERS




        //TODO BUY THIRDFIGTERS

        int inNeedWT = 0;
        for (Segment s:
                player.getCountry().getAbroad()) {
            for (Unit u:
                    s.getUnits()) {
                if(u.getClass() == ThirdFighter.class || u.getClass() == SecondFighter.class) {
                    inNeedWT++;
                }
            }
        }

        for (int i = wT; i < inNeedWT; i++) {
            if(player.getCountry().canBuyUnit(ThirdFighter.COST)){
                int show = (int)(Math.random() * player.getCountry().getCountryCities().size());
                City city = null;
                if(player.getCountry().getCountryCities().size() != 0)
                    city = player.getCountry().getCountryCities().get(show);
                if(city != null) {
                    for (Segment s :
                            city.getAreaNearCity()) {
                        if (s.getSettlement() == city) {
                            ThirdFighter secondFighter = new ThirdFighter(s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height);
                            if (s.addUnit(secondFighter)) {
                                player.getCountry().getUnits().add(secondFighter);
                                player.getCountry().payForUnit(ThirdFighter.COST);
                            }
                        }
                    }
                }
            }
        }



        //TODO END THIRDFIGHTERS



        //TODO BUY SECONDFIGHTERS
        int inNeedWS = 0;

        for (Segment s:
                player.getCountry().getAbroad()) {
            for (Unit u:
                    s.getUnits()) {
                if(u.getClass() != Civil.class && u.getClass() != Ship.class && u.getClass() != ThirdFighter.class && u.getClass() != SecondFighter.class) {
                    inNeedWS++;
                }
            }
        }

        for (int i = wS; i < inNeedWS; i++) {
            if(player.getCountry().canBuyUnit(SecondFighter.COST)){
                int show = (int)(Math.random() * player.getCountry().getCountryCities().size());
                City city = null;
                if(player.getCountry().getCountryCities().size() != 0)
                    city = player.getCountry().getCountryCities().get(show);
                if(city != null) {
                    for (Segment s :
                            city.getAreaNearCity()) {
                        if (s.getSettlement() == city) {
                            SecondFighter secondFighter = new SecondFighter(s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height);
                            if (s.addUnit(secondFighter)) {
                                player.getCountry().getUnits().add(secondFighter);
                                player.getCountry().payForUnit(SecondFighter.COST);
                            }
                        }
                    }
                }
            }
        }
        //TODO END SECONDFIGHTERS



        //TODO BUY FIRSTFIGTERS
        int inNeedwF = player.country.getCountry().size() / 5;
        int maxWF = GameScreen.map.HEIGHT * GameScreen.map.WIDTH / ((GameScreen.map.WIDTH + GameScreen.map.HEIGHT) * 5);
        if(inNeedwF > maxWF){
            inNeedwF = maxWF;
        }
        for (int i = wF; i < inNeedwF; i++) {
            if(player.getCountry().canBuyUnit(FirstFighter.COST)){
                int show = (int)(Math.random() * player.getCountry().getCountryCities().size());
                City city = null;
                if(player.getCountry().getCountryCities().size() != 0)
                    city = player.getCountry().getCountryCities().get(show);
                if(city != null) {
                    for (Segment s :
                            city.getAreaNearCity()) {
                        if (s.getSettlement() == city) {
                            FirstFighter firstFighter = new FirstFighter(s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height);
                            if (s.addUnit(firstFighter)) {
                                player.getCountry().getUnits().add(firstFighter);
                                player.getCountry().payForUnit(FirstFighter.COST);
                            }
                        }
                    }
                }
            }
        }
        //TODO END FIRSTFIGHTERS


/*
        //TODO SET TASKS
        for (Unit u:
             player.getCountry().getUnits()) {
            if(u.getTask() == null || u.getTask().isDone() || u.getTask().getCount() > 10){
                Task task = null;
                //TODO SET WORKER'S TASK
                if(u.getClass() == Worker.class){
                    City taskCity = null;
                    for (City city:
                         player.getCountry().getCountryCities()) {
                        if(city.hasAreaThisUnit(u)){
                            taskCity = city;
                        }
                    }

                    if(taskCity != null) {
                        for (Segment segment :
                                taskCity.getAreaNearCity()) {
                            if (segment.getSettlement() == null && u.isAbleToMoveToSegement(segment) && segment.isAbleToHaveMoreUnits() && segment.canMoveToSegment(u) && player.getCountry().canUnitToGoToSegment(segment)) {
                                task = new Task(segment, u, EnumSet.of(Actions.ACTION_MOVE, Actions.ACTION_CREATE_FARM), player.country);
                                u.setTask(task);
                                break;
                            }
                        }
                    }
                }
                //TODO FIRSTFIGHTER'S TASK
                if(u.getClass() == FirstFighter.class){
                    for (Segment segment:
                         player.getCountry().getAbroad()) {
                        if(u.isAbleToMoveToSegement(segment) && segment.isAbleToHaveMoreUnits() && segment.canMoveToSegment(u) && player.getCountry().canUnitToGoToSegment(segment)) {
                            boolean isTheSameTask = false;
                            for (Unit unit:
                                 player.getCountry().getUnits()) {
                                if(unit.getTask() != null && unit.getTask().getTaskSegment() == segment){
                                    isTheSameTask = true;
                                }
                            }
                            if(!isTheSameTask) {
                                task = new Task(segment, u, EnumSet.of(Actions.ACTION_MOVE), player.country);
                                u.setTask(task);
                            }
                        }
                    }
                }

                //TODO SECONDFIGHTER'S
                if(u.getClass() == SecondFighter.class){
                    Unit targetFoesUnit = player.country.getTheNearestFoesUnitTo(u);
                    if(targetFoesUnit != null) {
                        Segment targetSegment = player.country.getSegmentWithThisUnit(targetFoesUnit);
                        if (targetSegment != null && u.isAbleToMoveToSegement(targetSegment) && targetSegment.isAbleToHaveMoreUnits() && targetSegment.canMoveToSegment(u) && player.getCountry().canUnitToGoToSegment(targetSegment)) {
                            boolean isTheSameTask = false;
                            for (Unit unit:
                                    player.getCountry().getUnits()) {
                                if(unit.getTask() != null && unit.getTask().getTaskSegment() == targetSegment){
                                    isTheSameTask = true;
                                }
                            }
                            if(!isTheSameTask) {
                                task = new Task(targetSegment, u, EnumSet.of(Actions.ACTION_MOVE), player.country);
                                u.setTask(task);
                            }
                        } else if (targetSegment != null) {
                            Segment t = player.country.getTheNearestSegmentToTarget(targetSegment, u);
                            if (t != null) {

                                boolean isTheSameTask = false;
                                for (Unit unit:
                                        player.getCountry().getUnits()) {
                                    if(unit.getTask() != null && unit.getTask().getTaskSegment() == t){
                                        isTheSameTask = true;
                                    }
                                }
                                if(!isTheSameTask) {
                                    task = new Task(t, u, EnumSet.of(Actions.ACTION_MOVE), player.country);
                                    u.setTask(task);
                                }
                            }
                        }
                    }
                }
            }
        }

 */

        //TODO MAKE CORRECTLY DRAW
        for (Segment s:
             player.getCountry().getCountry()) {
            s.resetUnitsDrawables();
        }

        //TODO SETUP GLOBAL TASKS
        for (Unit u:
             player.getCountry().getUnits()) {
            if(u.getGlobalTask() == null){
                u.setupGlobalTask(player.country);
            }
        }

        //TODO UPDATE POWERS
        player.getCountry().updateUnitsPowers();
    }


    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void freeSpacefromCountry() {
        for (int i = 0; i < player.getCountry().getCountry().size(); i++) {
            Segment s = player.getCountry().getCountry().get(i);
            player.getCountry().getCountry().remove(i);
            if(s.getSettlement() != null && s.getSettlement().getClass() != City.class)
                s.setSettlement(null);
            for (int j = 0; j < s.getUnits().size(); j++) {
                Unit u = s.getUnits().get(j);
                s.getUnits().remove(u);
                player.getCountry().getUnits().remove(u);
            }
            s.setColor(null);
        }
        GameScreen.map.deleteCountry(player.getCountry());
    }

    public Segment getTheNearestCityToTarget(float tx, float ty, ArrayList<Segment>segmentsWithCity){
        Segment segment = null;


        double min = Math.sqrt(Math.pow(tx - segmentsWithCity.get(0).getSettlement().getBounds().getX(), 2) + Math.pow(ty - segmentsWithCity.get(0).getSettlement().getBounds().getY(), 2));
        double temp = 0;
        for (int i = 1; i < segmentsWithCity.size(); i++) {
            temp = Math.sqrt(Math.pow(tx - segmentsWithCity.get(i).getSettlement().getBounds().getX(), 2) + Math.pow(ty - segmentsWithCity.get(i).getSettlement().getBounds().getY(), 2));
            if(temp < min){
                min = temp;
                segment = segmentsWithCity.get(i);
            }
        }
        return segment;
    }

}
