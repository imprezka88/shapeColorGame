package com.ewareza.shapegame.player;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import com.ewareza.shapegame.app.learning.LearningGame;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.factory.ShapeFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.domain.shape.Shape;
import com.ewareza.shapegame.resources.ImageResources;
import com.ewareza.shapegame.resources.SoundResources;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoundResourcesManager {
    private static Logger Log = Logger.getLogger(SoundResourcesManager.class.getName());

    private static AtomicBoolean stopPlayingLearningSounds = new AtomicBoolean(false);

    public static void playLearningShapePhaseOneDescriptionSound(String shapeName, ColorFactory.Color color) {
        if (!stopPlayingLearningSounds.get()) {
            try {
                Player learningShapeDescription = SoundResources.INSTANCE.getLearningShapeDescription(shapeName);

                learningShapeDescription.startAndRelease();
                animateTalkingFrogIfPossible(learningShapeDescription);

                learningShapeDescription.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        playLearningShapePhaseOneColorDescription(shapeName, color);
                    }
                });

            } catch (Exception e) {
                Log.log(Level.WARNING, String.format("Could not play learning shape description voice in phase one for shape: %s. : %s", shapeName, e.getMessage()), e);
                tryToLearnNextShape();
            }
        }
    }

    private static void playLearningShapePhaseOneColorDescription(String shapeName, ColorFactory.Color color) {
        if(!stopPlayingLearningSounds.get()) {
            try {
                Player learningShapeColor = SoundResources.INSTANCE.getLearningShapeColorDescription(shapeName, color);

                learningShapeColor.startAndRelease();
                animateTalkingFrogIfPossible(learningShapeColor);

                learningShapeColor.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        tryToLearnNextShape();
                    }
                });
            } catch (Exception e) {
                Log.log(Level.WARNING, String.format("Could not play learning shape color description voice in phase one for shape: %s, color: %s. : %s", shapeName, color, e.getMessage()), e);
                tryToLearnNextShape();
            }
        }
    }

    private static void tryToLearnNextShape() {
        try {
            Thread.sleep(2000);
            if (!stopPlayingLearningSounds.get())
                LearningGame.getInstance().learnNextShape();
        } catch (InterruptedException e) {
            Log.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public static void playLearningShapeSelfDescription(ShapeFactory shapeFactory, ColorFactory.Color color) {
        String shapeName = shapeFactory.getShapeName();
        try {
            Player learningShapeSelfDescription = SoundResources.INSTANCE.getLearningShapeSelfDescription(shapeName);
            AnimationDrawable talkingShapeAnimation = ImageResources.getInstance().getTalkingShapeAnimation(shapeFactory.getShapeClass());
            PlayerWithAnimation playerWithAnimation = new PlayerWithAnimation(learningShapeSelfDescription, talkingShapeAnimation);

            playerWithAnimation.start();

            playerWithAnimation.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playLearningShapeColorSelfDescription(shapeName, color.getColorName(), shapeFactory.getShapeClass());
                }
            });

        } catch (PlayerFactory.UnknownSoundTypeException e) {
            Log.warning(String.format("Could not play learning shape self description voice in phase one for shape: %s. : %s", shapeName, e.getMessage()));
        }
    }

    private static void playLearningShapeColorSelfDescription(String shapeName, String color, Class<? extends AbstractShape> shapeClass) {
        try {
            Player learningShapeSelfDescription = SoundResources.INSTANCE.getLearningShapeColorSelfDescription(shapeName, color);
            AnimationDrawable talkingShapeAnimation = ImageResources.getInstance().getTalkingShapeAnimation(shapeClass);
            PlayerWithAnimation playerWithAnimation = new PlayerWithAnimation(learningShapeSelfDescription, talkingShapeAnimation);

            playerWithAnimation.start();
        } catch (PlayerFactory.UnknownSoundTypeException e) {
            Log.warning(String.format("Could not play learning shape color self description voice in phase one for shape: %s, color: %s: %s", shapeName, color, e.getMessage()));
        }
    }

    private static void stopPlayingSoundIfPlaying(Player player) {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    public static void playMainMenuSoundFullVolume() {
        try {
            SoundResources.getInstance().resetMainMenuSound();
            Player mainMenuSound = SoundResources.INSTANCE.getMainMenuSound();
            mainMenuSound.setVolume((float) 0.5, (float) 0.5);
            mainMenuSound.setLooping(true);
            mainMenuSound.start();
        } catch (Exception e) {
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
        stopPlayingLearningSounds.set(false);
        try {
            Player startLearningPhaseOneSound = SoundResources.INSTANCE.getResetStartLearningPhaseOneSound();
            startLearningPhaseOneSound.startAndRelease();
            animateTalkingFrogIfPossible(startLearningPhaseOneSound);

            startLearningPhaseOneSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playPhaseOneSentenceTwo();
                }
            });

        } catch (Exception e) {
            Log.warning(String.format("Could not play learning phase one start voice in phase one: %s", e.getMessage()));
        }
    }

    private static void playPhaseOneSentenceTwo() {
        stopPlayingLearningSounds.set(false);
        try {
            Player phaseOneSentenceTwo = SoundResources.INSTANCE.getResetPhaseOneSentenceTwo();

            phaseOneSentenceTwo.startAndRelease();
            animateTalkingFrogIfPossible(phaseOneSentenceTwo);

            phaseOneSentenceTwo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    LearningGame.getInstance().startPresentingShapes();
                }
            });
        } catch (Exception e) {
            Log.warning(String.format("Could not play learning phase one sentence two voice in phase one for shape: %s", e.getMessage()));
        }
    }

    public static void playStartLearningPhaseTwoSound() {
        try {
            Player startLearningPhaseTwoSound = SoundResources.INSTANCE.getResetStartLearningPhaseTwoSound();
            startLearningPhaseTwoSound.startAndRelease();
            animateTalkingFrogIfPossible(startLearningPhaseTwoSound);
        } catch (Exception e) {
            Log.warning(String.format("Could not play learning phase two voice in phase one: %s", e.getMessage()));
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

    public static void playWonGame() {
        Player wonGameSound = SoundResources.INSTANCE.getWonGameSound();
        wonGameSound.start();
    }

    public static void playCorrectShapeClickedSound() {
        try {
            Player correctShapeFoundSound = SoundResources.INSTANCE.getCorrectShapeFoundSound();
            correctShapeFoundSound.startAndRelease();
        } catch (Exception e) {
            Log.warning(String.format("Could not play correct shape clicked sound: %s", e.getMessage()));
        }
    }

    public static void playWrongShapeClickedSound() {
        try {
            Player wrongShapeFoundSound = SoundResources.INSTANCE.getWrongShapeFoundSound();
            wrongShapeFoundSound.startAndRelease();
        } catch (Exception e) {
            Log.warning(String.format("Could not play wrong shape clicked sound: %s", e.getMessage()));
        }
    }

    //@TODO cache sounds
    public static void playShapeGameTitleSound(String currentLookedForShapeName) {
        try {
            Player shapeGameTitleSound = SoundResources.INSTANCE.getShapeGameTitleSound(currentLookedForShapeName);
            shapeGameTitleSound.startAndRelease();
            animateTalkingFrogIfPossible(shapeGameTitleSound);
        } catch (Exception e) {
            Log.warning(String.format("Could not play shape game title for shape: %s. %s", currentLookedForShapeName, e.getMessage()));

        }
    }

    public static void playColorGameTitleSound(ColorFactory.Color color) {
        try {
            Player colorGameTitleSound = SoundResources.INSTANCE.getColorGameTitleSound(color);
            colorGameTitleSound.startAndRelease();
            animateTalkingFrogIfPossible(colorGameTitleSound);
        } catch (Exception e) {
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
                }
            });
        }
    }

    public static void stopPlayingSounds() {
        stopPlayingLearningSounds.set(true);

        for (Player player : SoundResources.getInstance().getSounds()) {
            try {
                if(player.isPlaying())
                    player.stop();
            }
            catch (IllegalStateException e){
                Log.log(Level.WARNING, String.format("Could not stop player: %s", player.getSoundName()), e);
            }
        }
    }
}
