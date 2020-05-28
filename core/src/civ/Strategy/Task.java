package civ.Strategy;

import java.util.EnumSet;
import java.util.Random;

import civ.model.Map.Country;
import civ.model.Map.Segment;
import civ.model.Map.Water;
import civ.model.Settlements.City;
import civ.model.Units.Actions;
import civ.model.Units.FirstFighter;
import civ.model.Units.Poselenec;
import civ.model.Units.SecondFighter;
import civ.model.Units.ThirdFighter;
import civ.model.Units.Unit;
import civ.model.Units.Worker;
import civ.view.GameScreen;

public class Task {
    Segment taskSegment;

    Segment finalGlobalTaskSegment;
    float prevXUn;
    float prevYUn;

    Unit taskUnit;
    EnumSet<Actions> taskActions;
    Country taskCountry;
    boolean done;
    int count; //VERLAUF

    public Task(Segment taskSegment, Unit taskUnit, EnumSet<Actions> taskAction, Country taskCountry) {
        this.taskSegment = taskSegment;
        this.taskUnit = taskUnit;
        this.taskActions = taskAction;
        this.taskCountry = taskCountry;
        finalGlobalTaskSegment = null;
        done = false;
        count = 0;
        prevXUn = taskUnit.getBounds().getX();
        prevYUn = taskUnit.getBounds().getY();
    }

    public void execute(){

        //TODO FIRSTFIGHTER'S GLOBAL TASK
        if(taskUnit.getClass() == FirstFighter.class){
            float currentX = taskUnit.getBounds().getX();
            float currentY = taskUnit.getBounds().getY();
            if(finalGlobalTaskSegment != null && taskCountry.getCountry().contains(finalGlobalTaskSegment) || count > 10){
                finalGlobalTaskSegment = null;
                count = 0;
            }
            for (Segment s:
                 taskCountry.getAbroad()) {
                if(s.containsFoesThisTypeOfUnits(Poselenec.class, taskCountry) || s.containsFoesThisTypeOfUnits(Worker.class, taskCountry) && s.canMoveToSegment(taskUnit) && taskUnit.isAbleToMoveToSegement(s)){
                    finalGlobalTaskSegment = s;
                    break;
                }
            }
            if(finalGlobalTaskSegment == null) {
                int i = 0;
                Random rnd = new Random();
                finalGlobalTaskSegment = taskCountry.getAbroad().get(rnd.nextInt(taskCountry.getAbroad().size()));
                while (!taskUnit.isAbleToMoveToSegement(finalGlobalTaskSegment) || !finalGlobalTaskSegment.canMoveToSegment(taskUnit) || !finalGlobalTaskSegment.isAbleToHaveMoreUnits(taskCountry) || finalGlobalTaskSegment.getPoint().getClass() == Water.class) {
                    finalGlobalTaskSegment = taskCountry.getAbroad().get(rnd.nextInt(taskCountry.getAbroad().size()));
                    if (i > GameScreen.map.WIDTH) {
                        break;
                    }
                    i++;
                }
            }

            if(finalGlobalTaskSegment == null){
                for (Segment s:
                     taskCountry.getCountry()) {
                    if(s.canMoveToSegment(taskUnit) && taskUnit.isAbleToMoveToSegement(s) && s.isAbleToHaveMoreUnits(taskCountry) && s.getPoint().getClass() != Water.class){
                        finalGlobalTaskSegment = s;
                        break;
                    }
                }
            }

            if(taskUnit.isAbleToMoveToSegement(finalGlobalTaskSegment) && finalGlobalTaskSegment.isAbleToHaveMoreUnits(taskCountry) && finalGlobalTaskSegment.canMoveToSegment(taskUnit) && taskCountry.canUnitToGoToSegment(finalGlobalTaskSegment)){
                if(finalGlobalTaskSegment.getUnits().size() != 0 && !taskCountry.getCountry().contains(finalGlobalTaskSegment)){
                    if(taskUnit instanceof Killer){
                        Killer killer = (Killer)taskUnit;
                        Unit mostPowerfulUnit = finalGlobalTaskSegment.mostPowerfulUnit();
                        killer.killUnit(mostPowerfulUnit, finalGlobalTaskSegment);
                    }
                }
                else{
                    taskUnit.move(finalGlobalTaskSegment);
                }
                if(finalGlobalTaskSegment.getUnits().size() == 0){
                    taskUnit.move(finalGlobalTaskSegment);
                }
                if(finalGlobalTaskSegment.getUnits().contains(taskUnit)){
                    doGoodMovement(finalGlobalTaskSegment);
                    if(!taskCountry.getCountry().contains(finalGlobalTaskSegment)){
                        taskCountry.getCountry().add(finalGlobalTaskSegment);
                        taskCountry.addSegment(finalGlobalTaskSegment);
                        taskCountry.fillAbroad();
                    }
                }
            }

            else {
                Segment temp = taskUnit.getNearestSegmentToTask(finalGlobalTaskSegment, taskCountry);
                if(temp != null){
                    if (temp.getUnits().size() != 0 && !taskCountry.getCountry().contains(temp)) {
                        if (taskUnit instanceof Killer) {
                            Killer killer = (Killer) taskUnit;
                            Unit mostPowerfulUnit = temp.mostPowerfulUnit();
                            killer.killUnit(mostPowerfulUnit, temp);
                        }
                    }

                    if (temp.getUnits().size() == 0 || taskCountry.getCountry().contains(temp)) {
                        taskUnit.move(temp);
                    }
                    doGoodMovement(temp);
                    if (!taskCountry.getCountry().contains(temp)) {
                        taskCountry.getCountry().add(temp);
                        taskCountry.addSegment(temp);
                        taskCountry.fillAbroad();
                    }

                    if(prevYUn == currentY && prevXUn == currentX){
                        finalGlobalTaskSegment = taskUnit.getTheNearestSegment(taskCountry, temp);
                        taskUnit.move(finalGlobalTaskSegment);
                        doGoodMovement(finalGlobalTaskSegment);
                        if(!taskCountry.getCountry().contains(finalGlobalTaskSegment)) {
                            taskCountry.getCountry().add(finalGlobalTaskSegment);
                            taskCountry.addSegment(finalGlobalTaskSegment);
                            taskCountry.fillAbroad();
                        }

                    }
                }
                else{
                    int i = 0;
                    Random rnd = new Random();
                    finalGlobalTaskSegment = taskCountry.getCountry().get(rnd.nextInt(taskCountry.getCountry().size()));
                    while (!taskUnit.isAbleToMoveToSegement(finalGlobalTaskSegment) || !finalGlobalTaskSegment.canMoveToSegment(taskUnit) || !finalGlobalTaskSegment.isAbleToHaveMoreUnits(taskCountry)) {
                        finalGlobalTaskSegment = taskCountry.getCountry().get(rnd.nextInt(taskCountry.getCountry().size()));
                        if (i > 20) {
                            break;
                        }
                        i++;
                    }
                }
            }

            count++;
            prevXUn = currentX;
            prevYUn = currentY;
        }


        //TODO SECONDFIGHTER'S GLOBAL TASK
        if(taskUnit.getClass() == SecondFighter.class){
            float currentX = taskUnit.getBounds().getX();
            float currentY = taskUnit.getBounds().getY();
            if(finalGlobalTaskSegment != null && taskCountry.getCountry().contains(finalGlobalTaskSegment) && count > 10){
                finalGlobalTaskSegment = null;
                count = 0;
            }
            for (Segment s:
                    taskCountry.getAbroad()) {
                if(s.containsFoesThisTypeOfUnits(FirstFighter.class, taskCountry) || s.getSettlement() != null){
                    finalGlobalTaskSegment = s;
                    break;
                }
            }
            if(finalGlobalTaskSegment == null) {
                int i = 0;
                Random rnd = new Random();
                finalGlobalTaskSegment = taskCountry.getAbroad().get(rnd.nextInt(taskCountry.getAbroad().size()));
                while (!taskUnit.isAbleToMoveToSegement(finalGlobalTaskSegment) || !finalGlobalTaskSegment.canMoveToSegment(taskUnit) || !finalGlobalTaskSegment.isAbleToHaveMoreUnits(taskCountry) || finalGlobalTaskSegment.getPoint().getClass() == Water.class) {
                    finalGlobalTaskSegment = taskCountry.getAbroad().get(rnd.nextInt(taskCountry.getAbroad().size()));
                    if (i > GameScreen.map.WIDTH) {
                        break;
                    }
                    i++;
                }
            }

            if(finalGlobalTaskSegment == null){
                for (Segment s:
                        taskCountry.getCountry()) {
                    if(s.canMoveToSegment(taskUnit) && taskUnit.isAbleToMoveToSegement(s) && s.isAbleToHaveMoreUnits(taskCountry) && s.getPoint().getClass() == Water.class){
                        finalGlobalTaskSegment = s;
                        break;
                    }
                }
            }

            if(taskUnit.isAbleToMoveToSegement(finalGlobalTaskSegment) && finalGlobalTaskSegment.isAbleToHaveMoreUnits(taskCountry) && finalGlobalTaskSegment.canMoveToSegment(taskUnit) && taskCountry.canUnitToGoToSegment(finalGlobalTaskSegment)){
                if(finalGlobalTaskSegment.getUnits().size() != 0 && !taskCountry.getCountry().contains(finalGlobalTaskSegment)){
                    if(taskUnit instanceof Killer){
                        Killer killer = (Killer)taskUnit;
                        Unit mostPowerfulUnit = finalGlobalTaskSegment.mostPowerfulUnit();
                        killer.killUnit(mostPowerfulUnit, finalGlobalTaskSegment);
                    }
                }
                else{
                    taskUnit.move(finalGlobalTaskSegment);
                }
                if(finalGlobalTaskSegment.getUnits().size() == 0){
                    taskUnit.move(finalGlobalTaskSegment);
                }
                if(finalGlobalTaskSegment.getUnits().contains(taskUnit)){
                    doGoodMovement(finalGlobalTaskSegment);
                    if(!taskCountry.getCountry().contains(finalGlobalTaskSegment)){
                        taskCountry.getCountry().add(finalGlobalTaskSegment);
                        taskCountry.addSegment(finalGlobalTaskSegment);
                        taskCountry.fillAbroad();
                    }
                }
            }

            else {
                Segment temp = taskUnit.getNearestSegmentToTask(finalGlobalTaskSegment, taskCountry);
                if(temp != null){
                    if (temp.getUnits().size() != 0 && !taskCountry.getCountry().contains(temp)) {
                        if (taskUnit instanceof Killer) {
                            Killer killer = (Killer) taskUnit;
                            Unit mostPowerfulUnit = temp.mostPowerfulUnit();
                            killer.killUnit(mostPowerfulUnit, temp);
                        }
                    }

                    if (temp.getUnits().size() == 0 || taskCountry.getCountry().contains(temp)) {
                        taskUnit.move(temp);
                    }
                    doGoodMovement(temp);
                    if (!taskCountry.getCountry().contains(temp)) {
                        taskCountry.getCountry().add(temp);
                        taskCountry.addSegment(temp);
                        taskCountry.fillAbroad();
                    }

                    if(prevYUn == currentY && prevXUn == currentX){
                        finalGlobalTaskSegment = taskUnit.getTheNearestSegment(taskCountry, temp);
                        taskUnit.move(finalGlobalTaskSegment);
                        doGoodMovement(finalGlobalTaskSegment);
                        if(!taskCountry.getCountry().contains(finalGlobalTaskSegment)) {
                            taskCountry.getCountry().add(finalGlobalTaskSegment);
                            taskCountry.addSegment(finalGlobalTaskSegment);
                            taskCountry.fillAbroad();
                        }

                    }
                }
                else{
                    int i = 0;
                    Random rnd = new Random();
                    finalGlobalTaskSegment = taskCountry.getCountry().get(rnd.nextInt(taskCountry.getCountry().size()));
                    while (!taskUnit.isAbleToMoveToSegement(finalGlobalTaskSegment) || !finalGlobalTaskSegment.canMoveToSegment(taskUnit) || !finalGlobalTaskSegment.isAbleToHaveMoreUnits(taskCountry)) {
                        finalGlobalTaskSegment = taskCountry.getCountry().get(rnd.nextInt(taskCountry.getCountry().size()));
                        if (i > 20) {
                            break;
                        }
                        i++;
                    }
                }
            }

            count++;
            prevXUn = currentX;
            prevYUn = currentY;
        }


        //TODO THIRDFIGHTER'S
        if(taskUnit.getClass() == ThirdFighter.class){
            float currentX = taskUnit.getBounds().getX();
            float currentY = taskUnit.getBounds().getY();
            if(finalGlobalTaskSegment != null && taskCountry.getCountry().contains(finalGlobalTaskSegment) && count > 15){
                finalGlobalTaskSegment = null;
                count = 0;
            }
            for (Segment s:
                    taskCountry.getAbroad()) {
                if(s.containsFoesThisTypeOfUnits(SecondFighter.class, taskCountry) || s.containsFoesThisTypeOfUnits(FirstFighter.class, taskCountry) || s.containsFoesThisTypeOfUnits(ThirdFighter.class, taskCountry) || s.getSettlement() != null){
                    finalGlobalTaskSegment = s;
                    break;
                }
            }
            if(finalGlobalTaskSegment == null) {
                int i = 0;
                Random rnd = new Random();
                finalGlobalTaskSegment = taskCountry.getAbroad().get(rnd.nextInt(taskCountry.getAbroad().size()));
                while (!taskUnit.isAbleToMoveToSegement(finalGlobalTaskSegment) || !finalGlobalTaskSegment.canMoveToSegment(taskUnit) || !finalGlobalTaskSegment.isAbleToHaveMoreUnits(taskCountry) || finalGlobalTaskSegment.getPoint().getClass() == Water.class) {
                    finalGlobalTaskSegment = taskCountry.getAbroad().get(rnd.nextInt(taskCountry.getAbroad().size()));
                    if (i > GameScreen.map.WIDTH) {
                        break;
                    }
                    i++;
                }
            }

            if(finalGlobalTaskSegment == null){
                for (Segment s:
                        taskCountry.getCountry()) {
                    if(s.canMoveToSegment(taskUnit) && taskUnit.isAbleToMoveToSegement(s) && s.isAbleToHaveMoreUnits(taskCountry) && s.getPoint().getClass() == Water.class){
                        finalGlobalTaskSegment = s;
                        break;
                    }
                }
            }

            if(taskUnit.isAbleToMoveToSegement(finalGlobalTaskSegment) && finalGlobalTaskSegment.isAbleToHaveMoreUnits(taskCountry) && finalGlobalTaskSegment.canMoveToSegment(taskUnit) && taskCountry.canUnitToGoToSegment(finalGlobalTaskSegment)){
                if(finalGlobalTaskSegment.getUnits().size() != 0 && !taskCountry.getCountry().contains(finalGlobalTaskSegment)){
                    if(taskUnit instanceof Killer){
                        Killer killer = (Killer)taskUnit;
                        Unit mostPowerfulUnit = finalGlobalTaskSegment.mostPowerfulUnit();
                        killer.killUnit(mostPowerfulUnit, finalGlobalTaskSegment);
                    }
                }
                else{
                    taskUnit.move(finalGlobalTaskSegment);
                }
                if(finalGlobalTaskSegment.getUnits().size() == 0){
                    taskUnit.move(finalGlobalTaskSegment);
                }
                if(finalGlobalTaskSegment.getUnits().contains(taskUnit)){
                    doGoodMovement(finalGlobalTaskSegment);
                    if(!taskCountry.getCountry().contains(finalGlobalTaskSegment)){
                        taskCountry.getCountry().add(finalGlobalTaskSegment);
                        taskCountry.addSegment(finalGlobalTaskSegment);
                        taskCountry.fillAbroad();
                    }
                }
            }

            else {
                Segment temp = taskUnit.getNearestSegmentToTask(finalGlobalTaskSegment, taskCountry);
                if(temp != null){
                    if (temp.getUnits().size() != 0 && !taskCountry.getCountry().contains(temp)) {
                        if (taskUnit instanceof Killer) {
                            Killer killer = (Killer) taskUnit;
                            Unit mostPowerfulUnit = temp.mostPowerfulUnit();
                            killer.killUnit(mostPowerfulUnit, temp);
                        }
                    }

                    if (temp.getUnits().size() == 0 || taskCountry.getCountry().contains(temp)) {
                        taskUnit.move(temp);
                    }
                    doGoodMovement(temp);
                    if (!taskCountry.getCountry().contains(temp)) {
                        taskCountry.getCountry().add(temp);
                        taskCountry.addSegment(temp);
                        taskCountry.fillAbroad();
                    }

                    if(prevYUn == currentY && prevXUn == currentX){
                        finalGlobalTaskSegment = taskUnit.getTheNearestSegment(taskCountry, temp);
                        taskUnit.move(finalGlobalTaskSegment);
                        doGoodMovement(finalGlobalTaskSegment);
                        if(!taskCountry.getCountry().contains(finalGlobalTaskSegment)) {
                            taskCountry.getCountry().add(finalGlobalTaskSegment);
                            taskCountry.addSegment(finalGlobalTaskSegment);
                            taskCountry.fillAbroad();
                        }

                    }
                }
                else{
                    int i = 0;
                    Random rnd = new Random();
                    finalGlobalTaskSegment = taskCountry.getCountry().get(rnd.nextInt(taskCountry.getCountry().size()));
                    while (!taskUnit.isAbleToMoveToSegement(finalGlobalTaskSegment) || !finalGlobalTaskSegment.canMoveToSegment(taskUnit) || !finalGlobalTaskSegment.isAbleToHaveMoreUnits(taskCountry)) {
                        finalGlobalTaskSegment = taskCountry.getCountry().get(rnd.nextInt(taskCountry.getCountry().size()));
                        if (i > 20) {
                            break;
                        }
                        i++;
                    }
                }
            }

            count++;
            prevXUn = currentX;
            prevYUn = currentY;
        }

        //TODO SET WORKER'S TASK
        if(taskUnit.getClass() == Worker.class){
            City taskCity = null;
            for (City city:
                    taskCountry.getCountryCities()) {
                if(city.hasAreaThisUnit(taskUnit)){
                    taskCity = city;
                }
            }

            if(taskCity != null) {
                for (Segment segment :
                        taskCity.getAreaNearCity()) {
                    if (segment.getSettlement() == null && taskUnit.isAbleToMoveToSegement(segment) && segment.isAbleToHaveMoreUnits(taskCountry) && segment.canMoveToSegment(taskUnit)) {
                        finalGlobalTaskSegment = segment;
                        break;
                    }
                }

                if (finalGlobalTaskSegment != null) {

                    Worker worker = (Worker) taskUnit;

                    if (!worker.isWorking()) {
                        worker.move(finalGlobalTaskSegment);
                        doGoodMovement(finalGlobalTaskSegment);
                        if (!taskCountry.getCountry().contains(finalGlobalTaskSegment)) {
                            taskCountry.getCountry().add(finalGlobalTaskSegment);
                            taskCountry.addSegment(finalGlobalTaskSegment);
                            taskCountry.fillAbroad();
                        }
                    }

                    if (finalGlobalTaskSegment.getSettlement() != null) {
                        //System.out.println(finalGlobalTaskSegment.getSettlement().getClass().getName() + " my country is " + taskCountry.getName());
                    }


                    if (finalGlobalTaskSegment.getSettlement() == null) {
                        if (!worker.isWorking()) {
                            worker.buildSettlement(2);
                        } else {
                            worker.progressBuilding();
                        }
                    }
                }
            }
        }



        if(taskActions.contains(Actions.ACTION_MOVE)){
            if(taskUnit.getClass() == Worker.class) {
                Worker worker = (Worker) taskUnit;
                if (!worker.isWorking()) {
                    worker.move(taskSegment);
                }
            }else {
                taskUnit.move(taskSegment);
                taskCountry.getCountry().add(taskSegment);
                taskCountry.addSegment(taskSegment);
                taskCountry.fillAbroad();
            }
            for (Segment s:
                    taskCountry.getCountry()) {
                if(s != taskSegment && s.hasUnit(taskUnit.getId())){
                    s.removeUnit(taskUnit);
                }
            }
            for (Country coun :
                    GameScreen.map.countries) {
                if (coun.hasSegment(taskSegment) && coun != taskCountry) {
                    coun.deleteSegment(taskSegment);
                    coun.getCountry().remove(taskSegment);
                }
            }
            if(taskActions.size() <= 1){
                done = true;
            }
            taskSegment.resetUnitsDrawables();
        }

        /*
        if(taskActions.contains(Actions.ACTION_CREATE_FARM)){
            Worker worker = (Worker)taskUnit;
            if(!worker.isWorking()) {
                worker.buildSettlement(2);
            }else{
                worker.progressBuilding();
            }
            if(!worker.isWorking()) {
                done = true;
            }
        }

         */
        count++;
    }

    public void doGoodMovement(Segment taskSegment){
        for (Segment s:
                taskCountry.getCountry()) {
            if(s != taskSegment && s.hasUnit(taskUnit.getId())){
                s.removeUnit(taskUnit);
            }
        }
        for (Country coun :
                GameScreen.map.countries) {
            if (coun.hasSegment(taskSegment) && coun != taskCountry) {
                coun.deleteSegment(taskSegment);
                coun.getCountry().remove(taskSegment);
                if(taskSegment.getSettlement() != null && taskSegment.getSettlement().getClass() == City.class){
                    coun.getCountryCities().remove(taskSegment.getSettlement());
                }
            }
        }
        taskSegment.resetUnitsDrawables();
    }

    public boolean isDone() {
        return done;
    }

    public int getCount() {
        return count;
    }

    public Segment getTaskSegment() {
        return taskSegment;
    }

    public EnumSet<Actions> getTaskActions() {
        return taskActions;
    }
}
