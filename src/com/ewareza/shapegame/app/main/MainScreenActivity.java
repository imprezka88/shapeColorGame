package com.ewareza.shapegame.app.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ewareza.android.R;
import com.ewareza.shapegame.app.CountingLocalizedActivity;
import com.ewareza.shapegame.app.LanguageState;
import com.ewareza.shapegame.app.PersistentGameSettings;
import com.ewareza.shapegame.app.ShapeGameApplication;
import com.ewareza.shapegame.app.learning.LearningGameActivity;
import com.ewareza.shapegame.app.shapeColorGame.ShapeGameActivity;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.player.SoundResourcesManager;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainScreenActivity extends CountingLocalizedActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_screen);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        initListenersOfButtons();
        initTextFont();
        addAd();
    }

    private void addAd() {
        LinearLayout container = (LinearLayout) findViewById(R.id.adViewContainer);
        AdView adView = new AdView(getApplicationContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.LEFT;
        adView.setLayoutParams(layoutParams);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
        container.addView(adView);

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        adView.loadAd(adRequest);
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

    private void initListenersOfButtons() {
        initNewGameButtons();
        initSoundEnabledButtons();
        initLanguageChangeButton();
    }

    private void initLanguageChangeButton() {
        ImageButton languageChangeButton = (ImageButton) findViewById(R.id.language_change);

        languageChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShapeGameApplication application = (ShapeGameApplication) getApplication();
                LanguageState languageState = application.getLanguageState();
                languageState.change(application);

                languageChangeButton.setImageDrawable(getResources().getDrawable(R.drawable.language_flag));
                TextView startShapeGameTextView = (TextView) findViewById(R.id.startShapeGameText);
                startShapeGameTextView.setText(getResources().getString(R.string.shape_game));

                TextView startColorGameTextView = (TextView) findViewById(R.id.startColourGameText);
                startColorGameTextView.setText(getResources().getString(R.string.color_game));

                TextView startLearningTextView = (TextView) findViewById(R.id.startLearningGameText);
                startLearningTextView.setText(getResources().getString(R.string.learning));
            }
        });
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
            speakingOnOffButton.setBackgroundResource(R.drawable.speaking_on);
            setLearningGameButtonEnabled(true);
        } else {
            speakingOnOffButton.setBackgroundResource(R.drawable.speaking_off);
            setLearningGameButtonEnabled(false);
        }

        speakingOnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PersistentGameSettings.getSpeechEnabled()) {
                    PersistentGameSettings.setSpeechEnabled(false);
                    speakingOnOffButton.setBackgroundResource(R.drawable.speaking_off);
                    setLearningGameButtonEnabled(false);
                } else {
                    PersistentGameSettings.setSpeechEnabled(true);
                    speakingOnOffButton.setBackgroundResource(R.drawable.speaking_on);
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
            soundsOnOffButton.setBackgroundResource(R.drawable.sounds_on);
        } else {
            soundsOnOffButton.setBackgroundResource(R.drawable.sounds_off);
        }

        soundsOnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PersistentGameSettings.getSoundsEnabled()) {
                    PersistentGameSettings.setSoundsEnabled(false);
                    SoundResourcesManager.pauseMainMenuSoundIfPlaying();
                    soundsOnOffButton.setBackgroundResource(R.drawable.sounds_off);
                } else {
                    PersistentGameSettings.setSoundsEnabled(true);
                    SoundResourcesManager.resumeMainMenuSound();
                    soundsOnOffButton.setBackgroundResource(R.drawable.sounds_on);
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