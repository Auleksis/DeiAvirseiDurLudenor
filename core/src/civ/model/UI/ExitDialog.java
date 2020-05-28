package civ.model.UI;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ExitDialog extends Dialog {
    public ExitDialog(String title, Skin skin) {
        super(title, skin);
    }

    public ExitDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
        init();
    }

    public ExitDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    public void init(){
        padTop(60);
        getButtonTable().defaults().height(100);
        setModal(true);
        setResizable(false);
        setMovable(false);
    }

    public ExitDialog addButton(String text, ClickListener listener, Skin skin, String styleName){
        TextButton textButton = new TextButton(text, skin, styleName);
        textButton.addListener(listener);
        button(textButton);
        return this;
    }

    @Override
    public float getPrefWidth() {
        return 960f;
    }

    @Override
    public float getPrefHeight() {
        return 480f;
    }
}
