package com.ewareza.shapegame.player;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import com.ewareza.shapegame.app.learning.LearningGame;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.domain.shape.Shape;
import com.ewareza.shapegame.resources.ImageResources;
import com.ewareza.shapegame.resources.SoundResources;

import java.util.logging.Logger;

public class SoundResourcesManager {
    private static Logger Log = Logger.getLogger(SoundResourcesManager.class.getName());

    private static boolean stopPlayingLearningSounds = false;

    public static void playLearningShapePhaseOneDescriptionSound(String shapeName) {
        if (!stopPlayingLearningSounds) {
            try {
                Player learningShapeDescription = SoundResources.INSTANCE.getLearningShapeDescription(shapeName);
                learningShapeDescription.start();
                ImageResources.getInstance().getTalkingFrogAnimation().start();

                learningShapeDescription.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        try {
                            learningShapeDescription.release();
                            ImageResources.getInstance().getTalkingFrogAnimation().stop();
                            Thread.sleep(2000);
                            LearningGame.learnNextShape();
                        } catch (InterruptedException e) {
                            //@TODO
                        }
                    }
                });
            }
            catch (Exception e) {
                Log.warning(String.format("Could not play learning shape description voice in phase one: %s", e.getMessage()));
            }
        }
    }

    public static void playLearningShapePhaseTwoSound(String shapeName, Class<? extends Shape> shapeClass) {
        Player learningShapeSelfDescription = null;
        try {
            learningShapeSelfDescription = SoundResources.INSTANCE.getLearningShapeSelfDescription(shapeName);
            AnimationDrawable talkingShapeAnimation = ImageResources.getInstance().getTalkingShapeAnimation(shapeClass);
            learningShapeSelfDescription.start();

            if (talkingShapeAnimation != null) {
                talkingShapeAnimation.start();

                final Player finalLearningShapeSelfDescription = learningShapeSelfDescription;
                learningShapeSelfDescription.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        finalLearningShapeSelfDescription.release();
                        talkingShapeAnimation.stop();
                    }
                });
            }
        } catch (PlayerFactory.UnknownSoundTypeException e) {
            e.printStackTrace();
        }

    }

    private static void stopPlayingSoundIfPlaying(Player player) {
        if (player != null && player.isPlaying()) {
            player.stop();
        }
    }

    public static void playMainMenuSoundFullVolume() {
        try {
            SoundResources.getInstance().resetMainMenuSound();
            Player mainMenuSound = SoundResources.INSTANCE.getMainMenuSound();
            mainMenuSound.setVolume((float) 0.5, (float) 0.5);
            mainMenuSound.start();
        } catch (PlayerFactory.UnknownSoundTypeException e) {

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
        Player startLearningPhaseOneSound = null;
        try {
            startLearningPhaseOneSound = SoundResources.INSTANCE.getResetStartLearningPhaseOneSound();
            startLearningPhaseOneSound.start();
            ImageResources.getInstance().getTalkingFrogAnimation().start();

            final Player finalStartLearningPhaseOneSound = startLearningPhaseOneSound;
            startLearningPhaseOneSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    finalStartLearningPhaseOneSound.release();
                    ImageResources.getInstance().getTalkingFrogAnimation().stop();
                    playPhaseOneSentenceTwo();
                }
            });
        } catch (PlayerFactory.UnknownSoundTypeException e) {
        }
    }

    private static void playPhaseOneSentenceTwo() {
        stopPlayingLearningSounds = false;
        Player phaseOneSentenceTwo = null;
        try {
            phaseOneSentenceTwo = SoundResources.INSTANCE.getResetPhaseOneSentenceTwo();
            phaseOneSentenceTwo.start();
            ImageResources.getInstance().getTalkingFrogAnimation().start();

            final Player finalPhaseOneSentenceTwo = phaseOneSentenceTwo;
            phaseOneSentenceTwo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    finalPhaseOneSentenceTwo.release();
                    ImageResources.getInstance().getTalkingFrogAnimation().stop();
                    LearningGame.startPresentingShapes();
                }
            });
        } catch (PlayerFactory.UnknownSoundTypeException e) {
        }
    }

    public static void playStartLearningPhaseTwoSound() {
        try {
            Player startLearningPhaseTwoSound = SoundResources.INSTANCE.getResetStartLearningPhaseTwoSound();

            startLearningPhaseTwoSound.start();
            ImageResources.getInstance().getTalkingFrogAnimation().start();

            startLearningPhaseTwoSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    startLearningPhaseTwoSound.release();
                    ImageResources.getInstance().getTalkingFrogAnimation().stop();
                }
            });
        } catch (PlayerFactory.UnknownSoundTypeException e) {
        }
    }

    public static void stopPlayingAllLearningSounds() {
        stopPlayingLearningSounds = true;
        stopPlayingSoundIfPlaying(SoundResources.INSTANCE.getStartLearningPhaseOneSound());
        stopPlayingSoundIfPlaying(SoundResources.INSTANCE.getPhaseOneSentenceTwo());
        stopPlayingSoundIfPlaying(SoundResources.INSTANCE.getStartLearningPhaseTwoSound());
        stopPlayingSoundIfPlaying(SoundResources.INSTANCE.getCurrentLearningShapeDescription());
        stopPlayingSoundIfPlaying(SoundResources.INSTANCE.getCurrentLearningShapeSelfDescription());
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
        stopPlayingSoundIfPlaying(SoundResources.INSTANCE.getCurrentColorGameTitleSound());
        stopPlayingSoundIfPlaying(SoundResources.INSTANCE.getCurrentShapeGameTitleSound());
        stopPlayingSoundIfPlaying(SoundResources.INSTANCE.getWonGameSound());
    }

    public static void playWonGame() {
        SoundResources.INSTANCE.getWonGameSound().start();
    }

    public static void playCorrectShapeClickedSound() {
        Player correctShapeFoundSound;
        try {
            correctShapeFoundSound = SoundResources.INSTANCE.getCorrectShapeFoundSound();
            correctShapeFoundSound.start();
        } catch (PlayerFactory.UnknownSoundTypeException e) {
            e.printStackTrace();
        }
    }

    public static void playWrongShapeClickedSound() {
        Player wrongShapeFoundSound = null;
        try {
            wrongShapeFoundSound = SoundResources.INSTANCE.getWrongShapeFoundSound();
            wrongShapeFoundSound.start();
        } catch (PlayerFactory.UnknownSoundTypeException e) {


        }
    }

    //@TODO cache sounds
    public static void playShapeGameTitleSound(AbstractShape currentLookedForShape) {
        Player shapeGameTitleSound = null;
        try {
            shapeGameTitleSound = SoundResources.INSTANCE.getShapeGameTitleSound(currentLookedForShape.getName());
            shapeGameTitleSound.start();
            final AnimationDrawable talkingFrogAnimation = ImageResources.getInstance().getTalkingFrogAnimation();

            if (talkingFrogAnimation != null) {
                talkingFrogAnimation.start();

                shapeGameTitleSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        talkingFrogAnimation.stop();
                    }
                });
            }
        } catch (PlayerFactory.UnknownSoundTypeException e) {

        }


    }

    public static void playColorGameTitleSound(ColorFactory.Color color) {
        Player colorGameTitleSound = null;
        try {
            colorGameTitleSound = SoundResources.INSTANCE.getColorGameTitleSound(color);
            colorGameTitleSound.start();
            ImageResources.getInstance().getTalkingFrogAnimation().start();

            colorGameTitleSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    ImageResources.getInstance().getTalkingFrogAnimation().stop();
                }
            });
        } catch (PlayerFactory.UnknownSoundTypeException e) {
        }

    }
}
