package com.civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.civ.Strategy.Killer;
import com.civ.model.GAmeObject;
import com.civ.model.Map.Segment;
import com.civ.view.GameScreen;

public abstract class Unit extends GAmeObject {
    int steps;
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
        bounds.setPosition(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY());
        Unit unit = segment.mostPowerfulUnit();
        if (unit != null) {
            if (this instanceof Killer) {
                Killer killer = (Killer) this;
                killer.killUnit(unit);
                GameScreen.ui.setActionText("You have killed a unit");
            }
        }
        segment.addUnit(this);
        canMove = false;
        moveBounds.setPosition(bounds.getX() - steps, bounds.getY() - steps);

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
        if(moveBounds.contains(segment.getPoint().getBounds().getBoundingRectangle())){
            return true;
        }
        return false;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isAbleToMove() {
        return canMove;
    }

    public void leightSegements(){
        for (Segment s:
             GameScreen.map.getMap()) {
            if(moveBounds.contains(s.getPoint().getBounds().getBoundingRectangle())){
                s.changeAlphaSegement(0.05f);
            }
        }
    }
}
