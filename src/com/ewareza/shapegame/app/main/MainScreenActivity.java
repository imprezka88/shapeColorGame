package com.ewareza.shapegame.app.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.ewareza.android.R;
import com.ewareza.shapegame.app.CountingActivity;
import com.ewareza.shapegame.app.PersistentGameSettings;
import com.ewareza.shapegame.app.learning.LearningGameActivity;
import com.ewareza.shapegame.app.shapeColorGame.ShapeGameActivity;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.player.SoundResourcesManager;
import com.ewareza.shapegame.resources.ScaledDimenRes;

public class MainScreenActivity extends CountingActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_screen);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        initListenersAndDimensionOfButtons();
        initTextFont();
    }

    private void initTextFont() {
        TextView learning = (TextView) findViewById(R.id.startLearningGameText);
        TextView shape = (TextView) findViewById(R.id.startShapeGameText);
        TextView colour = (TextView) findViewById(R.id.startColourGameText);

        Typeface font = Typeface.createFromAsset(getAssets(), "Kids_Club.ttf");
        learning.setTypeface(font);
        shape.setTypeface(font);
        colour.setTypeface(font);
    }

    private void initListenersAndDimensionOfButtons() {
        initNewGameButtons();
        initSoundEnabledButtons();
    }

    private void initSoundEnabledButtons() {
        addOnClickListenerToSoundsButton();
        addOnClickListenerToSpeechButton();
    }

    private void initNewGameButtons() {
        initLearningGameButton();
        initShapeGameButton();
        initColorGameButton();
    }

    private void initColorGameButton() {
        View colorGameButton = findViewById(R.id.newColorGameButton);

        colorGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, ShapeGameActivity.class);
                Bundle b = new Bundle();
                b.putString(GameUtils.GAME_TYPE, GameUtils.COLOR);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void initShapeGameButton() {
        ImageButton shapesGameButton = (ImageButton) findViewById(R.id.newShapesGameButton);
        shapesGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, ShapeGameActivity.class);
                Bundle b = new Bundle();
                b.putString(GameUtils.GAME_TYPE, GameUtils.SHAPE);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void initLearningGameButton() {
        ImageButton learningModeButton = (ImageButton) findViewById(R.id.newLearningGameButton);
        learningModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, LearningGameActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addOnClickListenerToSpeechButton() {
        final ImageButton speakingOnOffButton = (ImageButton) findViewById(R.id.speaking_on_off);

        if (PersistentGameSettings.getSpeechEnabled()) {
            speakingOnOffButton.setImageResource(R.drawable.speaking_on);
            setLearningGameButtonEnabled(true);
        } else {
            speakingOnOffButton.setImageResource(R.drawable.speaking_off);
            setLearningGameButtonEnabled(false);
        }

        speakingOnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PersistentGameSettings.getSpeechEnabled()) {
                    PersistentGameSettings.setSpeechEnabled(false);
                    speakingOnOffButton.setImageResource(R.drawable.speaking_off);
                    setLearningGameButtonEnabled(false);
                } else {
                    PersistentGameSettings.setSpeechEnabled(true);
                    speakingOnOffButton.setImageResource(R.drawable.speaking_on);
                    setLearningGameButtonEnabled(true);
                }
            }
        });
    }

    private void setLearningGameButtonEnabled(boolean enabled) {
        ImageButton learningModeButton = (ImageButton) findViewById(R.id.newLearningGameButton);
        learningModeButton.setClickable(enabled);
        learningModeButton.setEnabled(enabled);
    }

    private void addOnClickListenerToSoundsButton() {
        final ImageButton soundsOnOffButton = (ImageButton) findViewById(R.id.sounds_on_off);

        if (PersistentGameSettings.getSoundsEnabled()) {
            soundsOnOffButton.setImageResource(R.drawable.sounds_on);
        } else {
            soundsOnOffButton.setImageResource(R.drawable.sounds_off);
        }

        soundsOnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PersistentGameSettings.getSoundsEnabled()) {
                    PersistentGameSettings.setSoundsEnabled(false);
                    SoundResourcesManager.pauseMainMenuSoundIfPlaying();
                    soundsOnOffButton.setImageResource(R.drawable.sounds_off);
                } else {
                    PersistentGameSettings.setSoundsEnabled(true);
                    SoundResourcesManager.resumeMainMenuSound();
                    soundsOnOffButton.setImageResource(R.drawable.sounds_on);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundResourcesManager.playMainMenuSoundFullVolume();
    }
}