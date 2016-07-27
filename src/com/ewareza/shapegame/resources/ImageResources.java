package com.ewareza.shapegame.resources;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.ewareza.android.R;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.shape.Shape;

import java.util.HashMap;
import java.util.Map;

public enum ImageResources implements Resources {
    INSTANCE;

    private Map<Class<? extends Shape>, AnimationDrawable> shapeToAnimationMap = new HashMap<>();

    private static Context context;
    private AnimationDrawable talkingFrogAnimation;
    private static int colorGameImageIdentifier;
    private static int shapeGameImageIdentifier;
    private static int learningImageButtonIdentifier;
    private Drawable shapeGameImage;
    private Drawable colorGameImage;
    private Drawable balloons;
    private Drawable balloons2;
    private Drawable flowers;
    private Drawable learningBackgroundImage;
    private Drawable flowers2;
    private Drawable sun;
    private Drawable butterflies;
    private Drawable butterflies2;
    private Drawable bird;
    private Drawable hearts;
    private Drawable balloons3;
    private Drawable balloons4;
    private Drawable balloonToy;
    private Drawable hearts2;
    private Drawable bird2;
    private Drawable bird3;
    private Drawable airplane;
    private Drawable birthdayCake;
    private ImageView learningFrogView;
    private Drawable gameBackground;

    public ImageView getLearningFrogView() {
        return learningFrogView;
    }

    public void setLearningFrogView(ImageView learningFrogView) {
        this.learningFrogView = learningFrogView;
        talkingFrogAnimation = (AnimationDrawable) learningFrogView.getBackground();
    }

    public Drawable getBalloons3() {
        return balloons3;
    }

    public Drawable getBalloons4() {
        return balloons4;
    }

    public Drawable getBalloonToy() {
        return balloonToy;
    }

    public Drawable getHearts2() {
        return hearts2;
    }

    public Drawable getBird2() {
        return bird2;
    }

    public Drawable getBird3() {
        return bird3;
    }

    public Drawable getAirplane() {
        return airplane;
    }

    public Drawable getBirthdayCake() {
        return birthdayCake;
    }

    public Drawable getFlowers2() {
        return flowers2;
    }

    public Drawable getSun() {
        return sun;
    }

    public Drawable getButterflies() {
        return butterflies;
    }

    public Drawable getButterflies2() {
        return butterflies2;
    }

    public Drawable getBird() {
        return bird;
    }

    public Drawable getHearts() {
        return hearts;
    }

    public Drawable getLearningBackgroundImage() {
        return learningBackgroundImage;
    }

    public Drawable getBalloons() {
        return balloons;
    }

    public Drawable getColorGameImage() {
        return colorGameImage;
    }

    public Drawable getShapeGameImage() {
        return shapeGameImage;
    }

    public static int getShapeGameImageIdentifier() {
        return shapeGameImageIdentifier;
    }

    public static int getLearningImageButtonIdentifier() {
        return learningImageButtonIdentifier;
    }

    public static void setLearningImageButtonIdentifier(int learningImageButtonIdentifier) {
        ImageResources.learningImageButtonIdentifier = learningImageButtonIdentifier;
    }

    public static int getColorGameImageIdentifier() {
        return colorGameImageIdentifier;
    }

    public static ImageResources getInstance() {
        return INSTANCE;
    }

    public AnimationDrawable getTalkingFrogAnimation() {
        return talkingFrogAnimation;
    }

    public void setTalkingFrogAnimation(AnimationDrawable talkingFrogAnimation) {
        this.talkingFrogAnimation = talkingFrogAnimation;
    }

    public Drawable getBalloons2() {
        return balloons2;
    }

    public Drawable getFlowers() {
        return flowers;
    }

    public Drawable getResource(String shapeName, ColorFactory.Color color) {
        String fileName = String.format("%s_%s", shapeName, color.getColorName());
        int identifier = context.getResources().getIdentifier(fileName, GameUtils.RESOURCE_TYPE_DRAWABLE, GameUtils.RESOURCE_PACKAGE);

        if (identifier != 0)
            return context.getResources().getDrawable(identifier);

        throw new IllegalArgumentException(String.format("Identifier for drawable: %s not found", fileName));
    }

    public boolean hasResource(String shapeClassName, ColorFactory.Color color) {
        String fileName = String.format("%s_%s", shapeClassName, color.getColorName());
        int identifier = context.getResources().getIdentifier(fileName, GameUtils.RESOURCE_TYPE_DRAWABLE, GameUtils.RESOURCE_PACKAGE);

        return identifier != 0;
    }

    public void setTalkingShapeAnimation(Class<? extends Shape> shapeClass, AnimationDrawable animationDrawable) {
        shapeToAnimationMap.put(shapeClass, animationDrawable);
    }

    public AnimationDrawable getTalkingShapeAnimation(Class<? extends Shape> shapeClass) {
        return shapeToAnimationMap.get(shapeClass);
    }

    @Override
    public void init(Context context) {
        ImageResources.context = context;
        gameBackground = context.getResources().getDrawable(R.drawable.game_background_screen);
        colorGameImageIdentifier = R.drawable.color_game_button_image;

        shapeGameImageIdentifier = R.drawable.shape_game_button_image;
        learningImageButtonIdentifier = R.drawable.main_menu_learning_button_enabled;

        shapeGameImage = context.getResources().getDrawable(shapeGameImageIdentifier);
        colorGameImage = context.getResources().getDrawable(colorGameImageIdentifier);
        initGameOverImages(context);
        learningBackgroundImage = context.getResources().getDrawable(R.drawable.learning_background);
    }

    private void initGameOverImages(Context context) {
        balloons = context.getResources().getDrawable(R.drawable.game_over_balloons);
        balloons2 = context.getResources().getDrawable(R.drawable.game_over_balloons2);
        balloons3 = context.getResources().getDrawable(R.drawable.game_over_balloons3);
        balloons4 = context.getResources().getDrawable(R.drawable.game_over_balloons4);
        balloonToy = context.getResources().getDrawable(R.drawable.game_over_balloon_toy);

        flowers = context.getResources().getDrawable(R.drawable.game_over_flowers);
        flowers2 = context.getResources().getDrawable(R.drawable.game_over_flowers2);

        sun = context.getResources().getDrawable(R.drawable.game_over_sun);

        butterflies = context.getResources().getDrawable(R.drawable.game_over_butterflies);
        butterflies2 = context.getResources().getDrawable(R.drawable.game_over_butterflies2);

        bird = context.getResources().getDrawable(R.drawable.game_over_bird);
        bird2 = context.getResources().getDrawable(R.drawable.game_over_bird2);
        bird3 = context.getResources().getDrawable(R.drawable.game_over_bird3);

        airplane = context.getResources().getDrawable(R.drawable.game_over_airplane);

        hearts = context.getResources().getDrawable(R.drawable.game_over_hearts);
        hearts2 = context.getResources().getDrawable(R.drawable.game_over_hearts2);

        birthdayCake = context.getResources().getDrawable(R.drawable.game_over_birthday_cake);
    }

    public Drawable getGameBackground() {
        return gameBackground;
    }
}
