package com.civ.control;

import com.badlogic.gdx.math.Polygon;

import java.io.Serializable;

public class PointController implements Serializable {
    //TODO поинты статичны, поэтому здесь не нуден полигон
    private Polygon pointBounds;
    public PointController(Polygon pointBounds){
        this.pointBounds = pointBounds;
    }
    public PointController(){
        pointBounds  = null;
    }

    public boolean handleClicked(float tx, float ty){
        if(pointBounds.contains(tx, ty)){
            return true;
        }
        else{
            return false;
        }
    }

}
