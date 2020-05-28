package com.civ.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.civ.model.Map.Point;
import com.civ.view.GameScreen;

public class GameObj {
    protected Circle bounds;
    protected Sprite sprite;
    static float vel;

    public GameObj(Texture texture, float x, float y, float r){
        bounds = new Circle(x, y, r);

        sprite = new Sprite(texture);
        sprite.setSize(2* r, 2 * r);
        sprite.setPosition(x - r, y); // так как всё начинается с левого нижнего угла, то
        // от координат спрайта вычтем радиус(половину размера спрайта), тогда цента спрайта совпадёт с центром
        //окружности
        //Тут я не уверен, повыбирай так, чтобы центр спрайта совпал с центром Circle
        vel = -1f;
    }



    void handle(){
        bounds.x+=vel;
    }

    public static void handleCircle(GameObj c, Rectangle r){
        if(c.intersects(r)){
            vel = -vel;
        }
    }

    public boolean intersects(Rectangle r){
        if(Intersector.overlaps(bounds, r)){
            return true;
        }else{
            return false;
        }
    }

    public void draw(SpriteBatch batch){
        handle();
        sprite.setPosition(bounds.x - bounds.radius, bounds.y);
        sprite.draw(batch);
    }

    public Circle getBounds() {
        return bounds;
    }

}
