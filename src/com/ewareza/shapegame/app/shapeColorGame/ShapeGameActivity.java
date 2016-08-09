package com.ewareza.shapegame.app.shapeColorGame;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.ewareza.android.R;
import com.ewareza.shapegame.app.CountingLocalizedActivity;
import com.ewareza.shapegame.app.PersistentGameSettings;
import com.ewareza.shapegame.app.learning.LearningGameActivity;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.player.SoundResourcesManager;
import com.ewareza.shapegame.resources.ScaledDimenRes;
import com.ewareza.shapegame.resources.ImageResources;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShapeGameActivity extends CountingLocalizedActivity {
    private final static Logger Log = Logger.getLogger(ShapeGameActivity.class.getName());
    private GameView gameView;
    private CyclicBarrier gameOverCyclicBarrier = new CyclicBarrier(2);
    private CountDownLatch gameStartCountDownLatch = new CountDownLatch(1);
    private GameEngine gameEngine;
    private Game shapeColorGame;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle intersects the data it most
     *                           recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameStartCountDownLatch = new CountDownLatch(1);
        shapeColorGame = ShapeColorGameFactory.getGame(GameUtils.getCurrentGameType(getIntent().getExtras()));
        gameEngine = ShapeColorGame.getEngine();

        setContentView(R.layout.shape_color_game_screen);
        initView();
    }

    private void initView() {
        initGameView();
        initLearningButton();
        initNextGameImageButton();
    }

    private FrameLayout initGameView() {
        FrameLayout shapeGameLayout = (FrameLayout) findViewById(R.id.shape_color_game);
        gameView = new GameView(this.getApplicationContext(), gameStartCountDownLatch);
        shapeGameLayout.addView(gameView, 0);

        return shapeGameLayout;
    }

    private ImageView initLearningButton() {
        ImageButton learningButton = (ImageButton) findViewById(R.id.game_learning_frog);

        learningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShapeGameActivity.this, LearningGameActivity.class);
                finish();
                startActivity(intent);
            }
        });

        if(PersistentGameSettings.getSpeechEnabled()) {
            learningButton.setEnabled(true);
            learningButton.setClickable(true);
            AnimationDrawable frogAnimation = (AnimationDrawable) learningButton.getBackground().getCurrent();
            ImageResources.getInstance().setTalkingFrogAnimation(frogAnimation);
        }
        else {
            learningButton.setEnabled(false);
            learningButton.setClickable(false);
        }

        return learningButton;
    }

    private ImageButton initNextGameImageButton() {
        ImageButton nextGameButton = (ImageButton) findViewById(R.id.game_next_game);
        nextGameButton.setBackgroundResource(shapeColorGame.getNextGameImageIdentifier());
        nextGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Bundle b = new Bundle();
                b.putString(GameUtils.GAME_TYPE, shapeColorGame.getNextGameName());
                intent.putExtras(b);
                finish();
                startActivity(intent);
            }
        });

        return nextGameButton;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            if (x < ScaledDimenRes.getScreenWidthInPx() && y < ScaledDimenRes.getScreenHeightInPx()) {
                gameEngine.onScreenTouched(new Point(x, y));

                if (allShapesFound()) {
                    ShapeColorGame.setGameOver(true);
                    gameEngine.playWonGame();
                    tryToAwaitWithTimeoutOnBarrier(gameOverCyclicBarrier, 3, TimeUnit.SECONDS);
                    //@TODO move to oncompletion listerer od won game sound
                    gameEngine.generateNewGame();
                    ShapeColorGame.setGameOver(false);
                }
            }
        }

        return true;
    }

    private boolean allShapesFound() {
        return gameEngine.getNumberOfLookedForShapesOnScreen() == 0;
    }

    private void tryToAwaitWithTimeoutOnBarrier(CyclicBarrier cyclicBarrier, int timeout, TimeUnit timeUnit) {
        try {
            cyclicBarrier.await(timeout, timeUnit);
        } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
            Log.log(Level.WARNING, e.getMessage(), e);
        }
        finally {
            gameOverCyclicBarrier.reset();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SoundResourcesManager.stopPlayingSounds();
        SoundResourcesManager.stopPlayingMainMenuSoundIfPlaying();
        gameView.stopDisplayThread();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SoundResourcesManager.turnDownMainScreenSound();
        SoundResourcesManager.resumeMainMenuSound();
        shapeColorGame.setToInitialState();
        startNewGame();
        gameStartCountDownLatch.countDown();
    }

    private void startNewGame() {
        ShapeColorGame.setToFirstGame();
        gameEngine.generateNewGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

