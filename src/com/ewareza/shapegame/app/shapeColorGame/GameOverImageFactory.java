package com.ewareza.shapegame.app.shapeColorGame;

import android.graphics.drawable.Drawable;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.resources.ImageResources;

import java.util.ArrayList;
import java.util.List;

public enum GameOverImageFactory {
    INSTANCE;

    private List<Drawable> gameOverImages = new ArrayList<>();

    {
        gameOverImages.add(ImageResources.getInstance().getBalloons());
        gameOverImages.add(ImageResources.getInstance().getBalloons2());
        gameOverImages.add(ImageResources.getInstance().getBalloons3());
        gameOverImages.add(ImageResources.getInstance().getBalloons4());
        gameOverImages.add(ImageResources.getInstance().getBalloonToy());

        gameOverImages.add(ImageResources.getInstance().getFlowers());
        gameOverImages.add(ImageResources.getInstance().getFlowers2());

        gameOverImages.add(ImageResources.getInstance().getButterflies());
        gameOverImages.add(ImageResources.getInstance().getButterflies2());

        gameOverImages.add(ImageResources.getInstance().getSun());
        gameOverImages.add(ImageResources.getInstance().getHearts());
        gameOverImages.add(ImageResources.getInstance().getHearts2());

        gameOverImages.add(ImageResources.getInstance().getBird());
        gameOverImages.add(ImageResources.getInstance().getBird2());
        gameOverImages.add(ImageResources.getInstance().getBird3());

        gameOverImages.add(ImageResources.getInstance().getAirplane());
        gameOverImages.add(ImageResources.getInstance().getBirthdayCake());
    }

    public Drawable getGameOverImage() {
        return GameUtils.getRandomElement(gameOverImages);
    }
}
