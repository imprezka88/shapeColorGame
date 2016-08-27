package com.ewareza.shapegame.app.learning;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.ewareza.android.R;
import com.ewareza.shapegame.app.CountingLocalizedActivity;
import com.ewareza.shapegame.app.GameSettings;
import com.ewareza.shapegame.app.shapeColorGame.ShapeGameActivity;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.player.SoundResourcesManager;
import com.ewareza.shapegame.resources.ImageResources;

import java.util.HashMap;
import java.util.Map;

public class LearningGameActivity extends CountingLocalizedActivity {
    private LearningGame learningGame = LearningGame.INSTANCE;
    private Map<String, ImageView> shapes = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_screen);
        initView();
    }

    private void initView() {
        initFirstLearningPhaseView();
        initStartShapeGameButton();
        initStartColorGameButton();
        setFrogViewAnimation();
    }

    private void initFirstLearningPhaseView() {
        FrameLayout learningScreen = (FrameLayout) findViewById(R.id.learningScreen);
        learningScreen.addView(new LearningView(this.getApplicationContext()), 0);
    }

    private void initStartShapeGameButton() {
        ImageButton startGameButton = (ImageButton) findViewById(R.id.startShapeGameButton);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningGameActivity.this, ShapeGameActivity.class);
                Bundle b = new Bundle();
                b.putString(GameUtils.GAME_TYPE, GameUtils.SHAPE);
                intent.putExtras(b);
                finish();
                startActivity(intent);
            }
        });
    }

    private void initStartColorGameButton() {
        ImageButton startGameButton = (ImageButton) findViewById(R.id.startColorGameButton);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningGameActivity.this, ShapeGameActivity.class);
                Bundle b = new Bundle();
                b.putString(GameUtils.GAME_TYPE, GameUtils.COLOR);
                intent.putExtras(b);
                finish();
                startActivity(intent);
            }
        });
    }

    private void setFrogViewAnimation() {
        ImageView frogView = (ImageView) findViewById(R.id.learningFrog);
        ImageResources imageResources = ImageResources.getInstance();
        imageResources.setTalkingFrogAnimation((AnimationDrawable) frogView.getBackground());
    }

    private void initShapeAnimations() {
        for (ShapeFactory shapeFactory : GameSettings.getShapeFactories()) {
            shapes.put(shapeFactory.getShapeName(), addTalkingShapeAnimation(shapeFactory));
        }

        learningGame.setSecondPhaseShapes(shapes);
    }

    private ImageView addTalkingShapeAnimation(final ShapeFactory shapeFactory) {
        String shapeName = shapeFactory.getShapeName();
        int shapeId = getResources().getIdentifier(shapeName, GameUtils.RESOURCE_TYPE_ID, GameUtils.RESOURCE_PACKAGE);
        ImageView shape = (ImageView) findViewById(shapeId);
        shape.setVisibility(View.INVISIBLE);
        ColorFactory.Color learningShapeColor = LearningShapesGenerator.getLearningShapeColor(shapeFactory);
        Drawable shapeImage = ImageResources.getInstance().getResource(shapeName, learningShapeColor);

        if (hasLearningShapeAnimation(shapeName, learningShapeColor)) {
            shapeImage = createTalkingShapeAnimation(shapeName, learningShapeColor);
            ImageResources.getInstance().setTalkingShapeAnimation(shapeFactory.getShapeClass(), (AnimationDrawable) shapeImage);
        }

        shape.setImageDrawable(shapeImage);

        shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundResourcesManager.playLearningShapeSelfDescription(shapeFactory, learningShapeColor);
            }
        });


        return shape;
    }

    private static AnimationDrawable createTalkingShapeAnimation(String shapeName, ColorFactory.Color learningShapeColor) {
        AnimationDrawable talkingShapeAnimation = new AnimationDrawable();
        talkingShapeAnimation.setOneShot(false);
        talkingShapeAnimation.addFrame(ImageResources.getInstance().getResource(getMouthClosedFrameName(shapeName), learningShapeColor), GameUtils.LEARNING_SHAPE_ANIMATION_FRAME_DURATION);
        talkingShapeAnimation.addFrame(ImageResources.getInstance().getResource(getMouthOpenedFrameName(shapeName), learningShapeColor), GameUtils.LEARNING_SHAPE_ANIMATION_FRAME_DURATION);

        return talkingShapeAnimation;
    }

    private static boolean hasLearningShapeAnimation(String shapeName, ColorFactory.Color learningShapeColor) {
        return ImageResources.getInstance().hasResource(getMouthOpenedFrameName(shapeName), learningShapeColor) && ImageResources.getInstance().hasResource(getMouthClosedFrameName(shapeName), learningShapeColor);
    }

    private static String getMouthOpenedFrameName(String shapeName) {
        return String.format("%s_mouth_opened", shapeName);
    }

    private static String getMouthClosedFrameName(String shapeName) {
        return String.format("%s_mouth_closed", shapeName);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SoundResourcesManager.stopPlayingSounds();
        SoundResourcesManager.stopPlayingMainMenuSoundIfPlaying();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initShapeAnimations();
        LearningGame.getInstance().setToInitialState();
        SoundResourcesManager.turnDownMainScreenSound();
        SoundResourcesManager.resumeMainMenuSound();
        SoundResourcesManager.playStartLearningPhaseOneSound();
    }
}
