package civ.view.help;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.io.File;

public class FilesAdapter {
    ImageButton button;
    Label name;

    public FilesAdapter(ImageButton button, Label name) {
        this.button = button;
        this.name = name;
        this.name.setFontScale(0.3f);
    }

    public ImageButton getButton() {
        return button;
    }

    public Label getName() {
        return name;
    }
}
