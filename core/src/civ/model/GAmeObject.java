package civ.model;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public abstract class GAmeObject {
    protected Polygon bounds;
    protected Sprite sprite;
    protected static int ID = 0;
    protected int id;

    public GAmeObject(TextureRegion texture, float x, float y, float w, float h){
        bounds = new Polygon(new float[]{0f,0f, w, 0f, w, h, 0f, h});
        bounds.setPosition(x, y);
        bounds.setOrigin(w / 2f, h / 2f);

        if(texture != null) {
            sprite = new Sprite(texture);
            sprite.setSize(w, h);
            sprite.setOrigin(w / 2f, h / 2f);
            sprite.setPosition(x, y);
        }else {
            sprite = null;
        }

        ID++;
        id = ID;
    }

    public GAmeObject(){
        bounds = null;
        sprite = null;
    }

    public void draw(SpriteBatch batch){
        if(sprite != null) {
            sprite.setPosition(bounds.getX(), bounds.getY());
            sprite.setRotation(bounds.getRotation());
            sprite.draw(batch);
        }
    }

    public void setSprite(TextureRegion region) {
        sprite = new Sprite(region);
        sprite.setSize(bounds.getBoundingRectangle().width, bounds.getBoundingRectangle().height);
    }

    public Polygon getBounds() {
        return bounds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void reboot(){
        ID = 0;
    }
}
