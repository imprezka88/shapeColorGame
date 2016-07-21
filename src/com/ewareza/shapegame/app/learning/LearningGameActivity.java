package com.ewareza.shapegame.app.learning;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.ewareza.android.R;
import com.ewareza.shapegame.app.CountingActivity;
import com.ewareza.shapegame.app.GameSettings;
import com.ewareza.shapegame.app.shapeColorGame.ShapeGameActivity;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.domain.factory.CircleFactory;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.player.SoundResourcesManager;
import com.ewareza.shapegame.resources.DimenRes;
import com.ewareza.shapegame.resources.ImageResources;

import java.util.ArrayList;
import java.util.List;

public class LearningGameActivity extends CountingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_screen);
        LearningGame.setLearningActivity(this);
        initView();
    }

    private void initView() {
        initShapesView();
        initStartShapeGameButton();
        initStartColorGameButton();
        setFrogViewAnimation();
    }

    public void initShapeAnimations() {
        for (ShapeFactory shapeFactory : GameSettings.getShapeFactories()) {
            addTalkingShapeAnimation(shapeFactory);
        }
    }

    private void addTalkingShapeAnimation(final ShapeFactory shapeFactory) {
        String shapeName = shapeFactory.getShapeName();
        int shapeId = getResources().getIdentifier(shapeName, GameUtils.RESOURCE_TYPE_ID, GameUtils.RESOURCE_PACKAGE);
        ImageView shape = (ImageView) findViewById(shapeId);
        ColorFactory.Color learningShapeColor = LearningShapesGenerator.getLearningShapeColor(shapeFactory);
        Drawable shapeImage = ImageResources.getInstance().getResource(shapeName, learningShapeColor);

        if (hasLearningShapeAnimation(shapeName, learningShapeColor)) {
            shapeImage = createTalkingShapeAnimation(shapeName, learningShapeColor);
        }

        shape.setImageDrawable(shapeImage);

        shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundResourcesManager.playLearningShapePhaseTwoSound(shapeName, shapeFactory.getShapeClass());
            }
        });

        setShapeDimensions(shapeName, shape);
    }

    private void setShapeDimensions(String shapeName, ImageView shape) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) shape.getLayoutParams();

        layoutParams.width = DimenRes.getScaledDimenX(String.format("%s_width", shapeName));
        layoutParams.height = DimenRes.getScaledDimenY(String.format("%s_height", shapeName));
        layoutParams.setMargins(DimenRes.getScaledDimenX(String.format("%s_margin_left", shapeName)), DimenRes.getScaledDimenY(String.format("%s_margin_top", shapeName)), 0, 0);

        shape.setLayoutParams(layoutParams);
    }

    private AnimationDrawable createTalkingShapeAnimation(String shapeName, ColorFactory.Color learningShapeColor) {
        AnimationDrawable talkingShapeAnimation = new AnimationDrawable();
        talkingShapeAnimation.setOneShot(false);
        talkingShapeAnimation.setVisible(true, true);
        talkingShapeAnimation.addFrame(ImageResources.getInstance().getResource(getMouthOpenedFrameName(shapeName), learningShapeColor), GameUtils.LEARNING_SHAPE_ANIMATION_FRAME_DURATION);
        talkingShapeAnimation.addFrame(ImageResources.getInstance().getResource(getMouthClosedFrameName(shapeName), learningShapeColor), GameUtils.LEARNING_SHAPE_ANIMATION_FRAME_DURATION);

        ImageResources.getInstance().setTalkingShapeAnimation(CircleFactory.getInstance().getShapeClass(), talkingShapeAnimation);

        return talkingShapeAnimation;
    }

    private boolean hasLearningShapeAnimation(String shapeName, ColorFactory.Color learningShapeColor) {
        return ImageResources.hasResource(getMouthOpenedFrameName(shapeName), learningShapeColor) && ImageResources.hasResource(getMouthClosedFrameName(shapeName), learningShapeColor);
    }

    private String getMouthOpenedFrameName(String shapeName) {
        return String.format("%s_mouth_opened", shapeName);
    }

    private String getMouthClosedFrameName(String shapeName) {
        return String.format("%s_mouth_closed", shapeName);
    }

    private void initShapesView() {
        FrameLayout learningScreen = (FrameLayout) findViewById(R.id.learningScreen);
        learningScreen.addView(new LearningView(this), 0);
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

    @Override
    protected void onPause() {
        super.onPause();
        SoundResourcesManager.stopPlayingAllLearningSounds();
//        SoundResourcesManager.stopPlayingMainMenuSoundIfPlaying();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LearningGame.setToInitialState();
        SoundResourcesManager.turnDownMainScreenSound();
        SoundResourcesManager.playStartLearningPhaseOneSound();
    }
}
