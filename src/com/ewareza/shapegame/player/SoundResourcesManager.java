package com.ewareza.shapegame.player;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import com.ewareza.shapegame.app.learning.LearningGame;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.shape.Shape;
import com.ewareza.shapegame.resources.ImageResources;
import com.ewareza.shapegame.resources.SoundResources;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SoundResourcesManager {
    private static Logger Log = Logger.getLogger(SoundResourcesManager.class.getName());

    private static boolean stopPlayingLearningSounds = false;

    public static void playLearningShapePhaseOneDescriptionSound(String shapeName) {
        try {
            Player learningShapeDescription = SoundResources.INSTANCE.getLearningShapeDescription(shapeName);
            learningShapeDescription.start();
            animateTalkingFrogIfPossible(learningShapeDescription);

            learningShapeDescription.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    try {
                        Thread.sleep(2000);
                        if (!stopPlayingLearningSounds)
                            LearningGame.getInstance().learnNextShape();
                    } catch (InterruptedException e) {
                        //@TODO
                    }
                }
            });
        }
        catch (Exception e) {
            Log.warning(String.format("Could not play learning shape description voice in phase one for shape: %s. : %s", shapeName, e.getMessage()));
        }
    }

    public static void playLearningShapeSelfDescription(String shapeName, Class<? extends Shape> shapeClass) {
        try {
            Player learningShapeSelfDescription = SoundResources.INSTANCE.getLearningShapeSelfDescription(shapeName);
            AnimationDrawable talkingShapeAnimation = ImageResources.getInstance().getTalkingShapeAnimation(shapeClass);
            PlayerWithAnimation playerWithAnimation = new PlayerWithAnimation(learningShapeSelfDescription, talkingShapeAnimation);

            playerWithAnimation.start();
        } catch (PlayerFactory.UnknownSoundTypeException e) {
            Log.warning(String.format("Could not play learning shape self description voice in phase one for shape: %s. : %s", shapeName, e.getMessage()));
        }
    }

    private static void stopPlayingSoundIfPlaying(Player player) {
        if (player != null && player.isPlaying()) {
            player.stop();
            player.release();
        }
    }

    public static void playMainMenuSoundFullVolume() {
        try {
            SoundResources.getInstance().resetMainMenuSound();
            Player mainMenuSound = SoundResources.INSTANCE.getMainMenuSound();
            mainMenuSound.setVolume((float) 0.5, (float) 0.5);
            mainMenuSound.setLooping(true);
            mainMenuSound.start();
        } catch (PlayerFactory.UnknownSoundTypeException e) {
            Log.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public static void stopPlayingMainMenuSoundIfPlaying() {
        stopPlayingSoundIfPlaying(SoundResources.INSTANCE.getMainMenuSound());
    }

    public static void turnDownMainScreenSound() {
        Player mainMenuSound = SoundResources.INSTANCE.getMainMenuSound();
        mainMenuSound.setVolume((float) 0.1, (float) 0.1);
    }

    public static void playStartLearningPhaseOneSound() {
        stopPlayingLearningSounds = false;
        try {
            Player startLearningPhaseOneSound = SoundResources.INSTANCE.getResetStartLearningPhaseOneSound();
            startLearningPhaseOneSound.start();
            animateTalkingFrogIfPossible(startLearningPhaseOneSound);

            startLearningPhaseOneSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playPhaseOneSentenceTwo();
                }
            });
        } catch (PlayerFactory.UnknownSoundTypeException e) {
            Log.warning(String.format("Could not play learning phase one start voice in phase one: %s", e.getMessage()));

        }
    }

    private static void playPhaseOneSentenceTwo() {
        stopPlayingLearningSounds = false;
        try {
            Player phaseOneSentenceTwo = SoundResources.INSTANCE.getResetPhaseOneSentenceTwo();
            phaseOneSentenceTwo.start();
            animateTalkingFrogIfPossible(phaseOneSentenceTwo);

            phaseOneSentenceTwo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    LearningGame.getInstance().startPresentingShapes();
                }
            });
        } catch (PlayerFactory.UnknownSoundTypeException e) {
            Log.warning(String.format("Could not play learning phase one sentence two voice in phase one for shape: %s", e.getMessage()));
        }
    }

    public static void playStartLearningPhaseTwoSound() {
        try {
            Player startLearningPhaseTwoSound = SoundResources.INSTANCE.getResetStartLearningPhaseTwoSound();
            startLearningPhaseTwoSound.start();
            animateTalkingFrogIfPossible(startLearningPhaseTwoSound);
        } catch (PlayerFactory.UnknownSoundTypeException e) {
            Log.warning(String.format("Could not play learning phase two voice in phase one: %s", e.getMessage()));
        }
    }

    public static void stopPlayingAllLearningSounds() {
        stopPlayingLearningSounds = true;
        for (Player player : SoundResources.getInstance().getNameToLearningSound().values()) {
            stopPlayingSoundIfPlaying(player);
        }
    }

    public static void resumeMainMenuSound() {
        Player mainMenuSound = SoundResources.INSTANCE.getMainMenuSound();
        if (!mainMenuSound.isPlaying())
            mainMenuSound.start();
    }

    public static void pauseMainMenuSoundIfPlaying() {
        Player mainMenuSound = SoundResources.INSTANCE.getMainMenuSound();
        if (mainMenuSound.isPlaying())
            mainMenuSound.pause();
    }

    public static void stopPlayingShapeGameSounds() {
        for (Player player : SoundResources.getInstance().getNameToShapeColorGameSound().values()) {
            stopPlayingSoundIfPlaying(player);
        }
    }

    public static void playWonGame() {
        Player wonGameSound = SoundResources.INSTANCE.getWonGameSound();
        wonGameSound.start();
        addShapeColorGameSound(wonGameSound);
    }

    private static void addShapeColorGameSound(Player player) {
        SoundResources.getInstance().addShapeColorGameSound(player.getSoundName(), player);
    }

    public static void playCorrectShapeClickedSound() {
        try {
            Player correctShapeFoundSound = SoundResources.INSTANCE.getCorrectShapeFoundSound();
            correctShapeFoundSound.start();
            addShapeColorGameSound(correctShapeFoundSound);
        } catch (PlayerFactory.UnknownSoundTypeException e) {
            Log.warning(String.format("Could not play correct shape clicked sound: %s", e.getMessage()));
        }
    }

    public static void playWrongShapeClickedSound() {
        try {
            Player wrongShapeFoundSound = SoundResources.INSTANCE.getWrongShapeFoundSound();
            wrongShapeFoundSound.start();
            addShapeColorGameSound(wrongShapeFoundSound);
        } catch (PlayerFactory.UnknownSoundTypeException e) {
            Log.warning(String.format("Could not play wrong shape clicked sound: %s", e.getMessage()));
        }
    }

    //@TODO cache sounds
    public static void playShapeGameTitleSound(String currentLookedForShapeName) {
        try {
            Player shapeGameTitleSound = SoundResources.INSTANCE.getShapeGameTitleSound(currentLookedForShapeName);
            shapeGameTitleSound.start();
            animateTalkingFrogIfPossible(shapeGameTitleSound);
        } catch (PlayerFactory.UnknownSoundTypeException e) {
            Log.warning(String.format("Could not play shape game title for shape: %s. %s", currentLookedForShapeName, e.getMessage()));
        }
    }

    public static void playColorGameTitleSound(ColorFactory.Color color) {
        try {
            Player colorGameTitleSound = SoundResources.INSTANCE.getColorGameTitleSound(color);
            colorGameTitleSound.start();
            animateTalkingFrogIfPossible(colorGameTitleSound);
        } catch (PlayerFactory.UnknownSoundTypeException e) {
            Log.warning(String.format("Could not play color game title for color: %s. %s", color.getColorName(), e.getMessage()));
        }
    }

    private static void animateTalkingFrogIfPossible(Player player) {
        final AnimationDrawable talkingFrogAnimation = ImageResources.getInstance().getTalkingFrogAnimation();
        if(talkingFrogAnimation != null) {
            talkingFrogAnimation.start();

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    talkingFrogAnimation.stop();
                    SoundResources.getInstance().removeSound(player.getSoundName());
                    mp.release();
                }
            });
        }
    }
}
