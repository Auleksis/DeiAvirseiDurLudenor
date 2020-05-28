package civ.model.UI;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import civ.model.Units.Unit;

public class MImageButton {
    ImageButton img;
    Unit unit;

    public MImageButton(ImageButton imageButton, Unit unit){
        img = imageButton;
        this.unit = unit;
    }

    public ImageButton getImg() {
        return img;
    }

    public Unit getUnit() {
        return unit;
    }
}
