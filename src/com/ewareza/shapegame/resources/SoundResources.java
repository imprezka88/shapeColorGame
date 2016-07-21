package com.ewareza.shapegame.resources;

import android.content.Context;
import com.ewareza.android.R;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.player.NullPlayer;
import com.ewareza.shapegame.player.Player;
import com.ewareza.shapegame.player.PlayerFactory;
import com.ewareza.shapegame.player.PlayerType;

import java.util.logging.Logger;

//@TODO cache media players retrieved dynamically, one play method with sound enability check
public enum SoundResources implements Resources {
    INSTANCE;

    private static Logger Log = Logger.getLogger(SoundResources.class.getName());

    private Context context;
    private PlayerFactory playerFactory;

    private Player mainMenuSound;

    private Player wonGameSound;
    private Player correctShapeFoundSound;
    private Player wrongShapeFoundSound;
    private Player currentShapeGameTitleSound;
    private Player currentColorGameTitleSound;
    private Player phaseOneSentenceTwo;

    private Player startLearningPhaseTwoSound;
    private Player startLearningPhaseOneSound;
    private Player currentLearningShapeDescription;
    private Player currentLearningShapeSelfDescription;

    @Override
    public void init(Context context) {
        this.context = context;
        playerFactory = new PlayerFactory(context);
        wonGameSound = tryToGetPlayer(R.raw.sound_won_game);
        correctShapeFoundSound = tryToGetPlayer(R.raw.sound_correct_shape_clicked);
        wrongShapeFoundSound = tryToGetPlayer(R.raw.sound_wrong_shape_clicked);
        mainMenuSound = tryToGetPlayer(R.raw.sound_background_music);
        mainMenuSound.setLooping(true);
        startLearningPhaseOneSound = tryToGetPlayer(R.raw.speech_learning_phase1_sentence1);
        phaseOneSentenceTwo = tryToGetPlayer(R.raw.speech_learning_phase1_sentence2);
        startLearningPhaseTwoSound = tryToGetPlayer(R.raw.speech_learning_phase2);
    }

    private Player tryToGetPlayer(int id) {
        Player player = new NullPlayer();

        try {
            player = playerFactory.getPlayer(getResourceNameById(id));
        } catch (PlayerFactory.UnknownSoundTypeException e) {
            e.printStackTrace();
        }

        return player;
    }

    public Player getCurrentLearningShapeSelfDescription() {
        return currentLearningShapeSelfDescription;
    }

    public static SoundResources getInstance() {
        return INSTANCE;
    }

    public Player getResetStartLearningPhaseOneSound() throws PlayerFactory.UnknownSoundTypeException {
        startLearningPhaseTwoSound = resetSound(startLearningPhaseOneSound);
        return startLearningPhaseOneSound;
    }

    private static Player resetSound(Player player) throws PlayerFactory.UnknownSoundTypeException {
        player.reset();
//        player.release();
        player = SoundResources.INSTANCE.createPlayerForId(player.getIdentifier());
        return player;
    }

    public Player getStartLearningPhaseTwoSound() {
        return startLearningPhaseTwoSound;
    }

    public Player getStartLearningPhaseOneSound() {
        return startLearningPhaseOneSound;
    }

    public Player getResetStartLearningPhaseTwoSound() throws PlayerFactory.UnknownSoundTypeException {
        startLearningPhaseTwoSound = resetSound(startLearningPhaseTwoSound);
        return startLearningPhaseTwoSound;
    }

    private String getResourceNameById(int id) {
        return context.getResources().getResourceEntryName(id);
    }

    public Player getWonGameSound() {
        return wonGameSound;
    }

    public Player getCurrentLearningShapeDescription() {
        return currentLearningShapeDescription;
    }

    public Player getCorrectShapeFoundSound() throws PlayerFactory.UnknownSoundTypeException {
        return resetSoundIfIsPlaying(correctShapeFoundSound);
    }

    public Player getWrongShapeFoundSound() throws PlayerFactory.UnknownSoundTypeException {
        return resetSoundIfIsPlaying(wrongShapeFoundSound);
    }

    public Player getLearningShapeDescription(String shapeName) throws PlayerFactory.UnknownSoundTypeException {
        String soundName = String.format("%s_its_%s", PlayerType.SPEECH.getPrefix(), shapeName);
        currentLearningShapeDescription = playerFactory.getPlayer(soundName);
        return currentLearningShapeDescription;
    }

    public Player getMainMenuSound() {
        return mainMenuSound;
    }

    public Player getCurrentShapeGameTitleSound() {
        return currentShapeGameTitleSound;
    }

    public Player getCurrentColorGameTitleSound() {
        return currentColorGameTitleSound;
    }

    public Player getPhaseOneSentenceTwo() {
        return phaseOneSentenceTwo;
    }

    public Player getLearningShapeSelfDescription(String shapeName) throws PlayerFactory.UnknownSoundTypeException {
        String soundName = getLearningShapeSelfDescriptionSoundName(shapeName);
        currentLearningShapeSelfDescription = playerFactory.getPlayer(soundName);
        return currentLearningShapeSelfDescription;
    }

    private static String getLearningShapeSelfDescriptionSoundName(String shapeName) {
        return String.format("%s_im_%s", PlayerType.SPEECH.getPrefix(), shapeName);
    }

    public Player createPlayerForId(int id) {
        return tryToGetPlayer(id);
    }

    public Player getShapeGameTitleSound(String lookedForShapeName) throws PlayerFactory.UnknownSoundTypeException {
        String soundName = String.format("%s_find_%s", PlayerType.SPEECH.getPrefix(), lookedForShapeName);
        currentShapeGameTitleSound = playerFactory.getPlayer(soundName);
        return currentShapeGameTitleSound;

    }

    public Player getColorGameTitleSound(ColorFactory.Color color) throws PlayerFactory.UnknownSoundTypeException {
        String soundName = String.format("%s_find_%s", PlayerType.SPEECH.getPrefix(), color.getColorName());
        currentColorGameTitleSound = playerFactory.getPlayer(soundName);
        return currentColorGameTitleSound;
    }

    public static Player resetSoundIfIsPlaying(Player player) throws PlayerFactory.UnknownSoundTypeException {
        if (player.isPlaying()) {
            player.stop();
        }

        player.reset();
        player.release();
        player = SoundResources.INSTANCE.createPlayerForId(player.getIdentifier());

        return player;
    }

    public void resetMainMenuSound() throws PlayerFactory.UnknownSoundTypeException {
        mainMenuSound = resetSoundIfIsPlaying(mainMenuSound);
    }

    public Player getResetPhaseOneSentenceTwo() throws PlayerFactory.UnknownSoundTypeException {
        phaseOneSentenceTwo = resetSound(phaseOneSentenceTwo);
        return phaseOneSentenceTwo;
    }
}
