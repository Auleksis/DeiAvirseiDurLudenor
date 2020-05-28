package com.civ.control;

import com.badlogic.gdx.math.Polygon;


public class ButtonController {
    Polygon buttonBounds;
    public  ButtonController(Polygon bounds){
        buttonBounds = bounds;
    }

    public boolean handleButton(float tX, float tY){
        if(buttonBounds.contains(tX, tY))
            return true;
        else
            return false;
    }
}
