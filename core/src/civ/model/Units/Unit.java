package civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.EnumSet;

import civ.Strategy.Killer;
import civ.Strategy.Task;
import civ.model.GAmeObject;
import civ.model.Map.Country;
import civ.model.Map.Segment;
import civ.model.Map.Water;
import civ.model.Settlements.Fort;
import civ.view.GameScreen;

public abstract class Unit extends GAmeObject {
    EnumSet<Actions> actions;

    //TODO Task
    Task localTask;
    Task globalTask;
    boolean local;

    protected int costPerVerlauf;
    int steps;
    int power;
    boolean alive;
    boolean drawable;
    boolean active;
    boolean canMove;
    Rectangle moveBounds;
    public Unit(TextureRegion texture, float x, float y, float w, float h) {
        super(texture, x, y, w, h);
        ID++;
        id = ID;
        alive = true;
        drawable = true;
        active = false;
        canMove = true;
        local = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDrawable() {
        return drawable;
    }

    public void setDrawable(boolean drawable) {
        this.drawable = drawable;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void move(float x, float y){
        bounds.setPosition(x, y);
    }

    public void move(Segment segment){
        if(segment != null) {
            resetPower();
            segment.addUnit(this);
            segment.resetUnitsDrawables();
            canMove = false;
            bounds.setPosition(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY());
            moveBounds.setPosition(bounds.getX() - steps, bounds.getY() - steps);
        }
    }


    public void write(StringBuilder data) {
        data.append("\n" + Integer.valueOf(id).toString());
        data.append("\n" + (canMove?"yes":"no"));
    }

    public void writeOnlyID(StringBuilder data){
        data.append("\n" + Integer.valueOf(id).toString());
    }


    public void read() {

    }

    public Rectangle getMoveBounds() {
        return moveBounds;
    }

    public boolean isAbleToMoveToSegement(Segment segment){
        if (moveBounds.contains(segment.getPoint().getBounds().getBoundingRectangle())) {
            return true;
        }

        return false;
    }

    public Segment getNearestSegmentToTask(Segment targetSegment, Country country){
        ArrayList<Segment>moveSegments = new ArrayList<>();
        Segment targetTaskSegment = null;
        for (Segment s:
                country.getAllRegion()) {
            if(isAbleToMoveToSegement(s)) {
                moveSegments.add(s);
            }
        }
        if(moveSegments.size() != 0) {
            double distance = Math.sqrt(Math.pow(targetSegment.getPoint().getBounds().getY() - moveSegments.get(0).getPoint().getBounds().getY(), 2) + Math.pow(targetSegment.getPoint().getBounds().getY() - moveSegments.get(0).getPoint().getBounds().getY(), 2));
            targetTaskSegment = moveSegments.get(0);
            for (Segment s :
                    moveSegments) {
                double tempDistance = Math.sqrt(Math.pow(targetSegment.getPoint().getBounds().getY() - s.getPoint().getBounds().getY(), 2) + Math.pow(targetSegment.getPoint().getBounds().getY() - s.getPoint().getBounds().getY(), 2));
                if (distance > tempDistance) {
                    targetTaskSegment = s;
                    distance = tempDistance;
                }
            }
        }
        return targetTaskSegment;
    }

    public Segment getTheNearestSegment(Country country, Segment currentSegment){
        for (Segment segment:
                country.getAllRegion()) {
            if(isAbleToMoveToSegement(segment) && segment.isAbleToHaveMoreUnits(country) && country.canUnitToGoToSegment(segment) && segment.canMoveToSegment(this) && segment != currentSegment){
                return segment;
            }
        }
        return null;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isAbleToMove() {
        return canMove;
    }

    public EnumSet getActions() {
        return actions;
    }

    public void leightSegements(){
        for (Segment s:
             GameScreen.map.getMap()) {
            if(moveBounds.contains(s.getPoint().getBounds().getBoundingRectangle())){
                s.changeAlphaSegement(0.05f);
            }
        }
    }

    public int getCostPerVerlauf() {
        return costPerVerlauf;
    }

    /*

    public void setLocalTask(Task task) {
        if(this.localTask == null || this.localTask.isDone())
            this.localTask = task;
    }

     */

    public void setLocalTask(Task task){
        localTask = task;
        local = true;
    }

    public void executeTask(){
        if(local){
            localTask.execute();
            local = false;
        }else {
            if(globalTask != null)
                globalTask.execute();
        }
    }

    public Task getLocalTask() {
        return localTask;
    }

    public abstract void setupGlobalTask(Country country);

    public Task getGlobalTask() {
        return globalTask;
    }

    public abstract int getRealPower();
    public void resetPower(){
        power = getRealPower();
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
