package com.ewareza.shapegame.resources;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.ewareza.android.R;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.shape.Shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private Drawable learningBackgroundImage;
    private ImageView learningFrogView;
    private Drawable gameBackground;
    private List<Drawable> gameOverImages = new ArrayList<>();

    public ImageView getLearningFrogView() {
        return learningFrogView;
    }

    public void setLearningFrogView(ImageView learningFrogView) {
        this.learningFrogView = learningFrogView;
        talkingFrogAnimation = (AnimationDrawable) learningFrogView.getBackground();
    }

    public Drawable getLearningBackgroundImage() {
        return learningBackgroundImage;
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

    public List<Drawable> getGameOverImages() {
        return gameOverImages;
    }

    @Override
    public void init(Context context) {
        ImageResources.context = context;
        gameBackground = context.getResources().getDrawable(R.drawable.game_background_screen);
        colorGameImageIdentifier = R.drawable.main_menu_start_color_game_button;

        shapeGameImageIdentifier = R.drawable.game_shape_game_button;
        learningImageButtonIdentifier = R.drawable.main_menu_learning_button_enabled;

        shapeGameImage = context.getResources().getDrawable(shapeGameImageIdentifier);
        colorGameImage = context.getResources().getDrawable(colorGameImageIdentifier);
        initGameOverImages(context);
        learningBackgroundImage = context.getResources().getDrawable(R.drawable.learning_background);
    }

    private void initGameOverImages(Context context) {
        int resId;
        int imageNumber = 1;

        do {
            resId = context.getResources().getIdentifier("game_over" + imageNumber, GameUtils.RESOURCE_TYPE_DRAWABLE, GameUtils.RESOURCE_PACKAGE);
            if (resId != 0)
                gameOverImages.add(context.getResources().getDrawable(resId));
            imageNumber++;
        }
        while (resId != 0);
    }

    public Drawable getGameBackground() {
        return gameBackground;
    }
}
