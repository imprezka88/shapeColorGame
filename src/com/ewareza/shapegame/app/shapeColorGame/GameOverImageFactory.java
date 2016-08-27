package com.ewareza.shapegame.app.shapeColorGame;

import android.graphics.drawable.Drawable;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.resources.ImageResources;

import java.util.ArrayList;
import java.util.List;

public enum GameOverImageFactory {
    INSTANCE;

    private List<Drawable> gameOverImages = new ArrayList<>();

    GameOverImageFactory() {
        gameOverImages = ImageResources.getInstance().getGameOverImages();
    }

    public Drawable getGameOverImage() {
        return GameUtils.getRandomElement(gameOverImages);
    }
}
