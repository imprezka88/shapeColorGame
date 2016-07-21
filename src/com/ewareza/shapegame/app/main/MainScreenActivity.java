package com.ewareza.shapegame.app.main;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.ewareza.android.R;
import com.ewareza.shapegame.app.CountingActivity;
import com.ewareza.shapegame.app.Game;
import com.ewareza.shapegame.app.learning.LearningGameActivity;
import com.ewareza.shapegame.app.shapeColorGame.ShapeGameActivity;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.player.SoundResourcesManager;

public class MainScreenActivity extends CountingActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_screen);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        addOnClickListenersToButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundResourcesManager.playMainMenuSoundFullVolume();
    }

    private void addOnClickListenerToColorGameButton() {
        ImageButton colorGameButton = (ImageButton) findViewById(R.id.newColorGameButton);
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

    private void addOnClickListenerToShapeGameButton() {
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

    private void addOnClickListenerToLearningGameButton() {
        ImageButton learningModeButton = (ImageButton) findViewById(R.id.newLearningGameButton);
        learningModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, LearningGameActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addOnClickListenersToButtons() {
        addOnClickListenersToStartNewGameButtons();
        addOnClickListenersToSoundEnabledButtons();
    }

    private void addOnClickListenersToSoundEnabledButtons() {
        addOnClickListenerToSoundsButton();
        addOnClickListenerToSpeakingButton();
    }

    private void addOnClickListenersToStartNewGameButtons() {
        addOnClickListenerToLearningGameButton();
        addOnClickListenerToShapeGameButton();
        addOnClickListenerToColorGameButton();
    }

    private void addOnClickListenerToSpeakingButton() {
        final ImageButton speakingOnOffButton = (ImageButton) findViewById(R.id.speaking_on_off);

        speakingOnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Game.getSpeechEnabled()) {
                    Game.setSpeechEnabled(false);
                    speakingOnOffButton.setImageResource(R.drawable.speaking_off);
                } else {
                    Game.setSpeechEnabled(true);
                    speakingOnOffButton.setImageResource(R.drawable.speaking_on);

                }
            }
        });
    }

    private void addOnClickListenerToSoundsButton() {
        final ImageButton soundsOnOffButton = (ImageButton) findViewById(R.id.sounds_on_off);

        soundsOnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Game.getSoundsEnabled()) {
                    Game.setSoundsEnabled(false);
                    SoundResourcesManager.pauseMainMenuSoundIfPlaying();
                    soundsOnOffButton.setImageResource(R.drawable.sounds_off);
                } else {
                    Game.setSoundsEnabled(true);
                    SoundResourcesManager.resumeMainMenuSound();
                    soundsOnOffButton.setImageResource(R.drawable.sounds_on);
                }
            }
        });
    }
}