package com.ewareza.shapegame.resources;

import android.content.Context;
import com.ewareza.android.R;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.player.NullPlayer;
import com.ewareza.shapegame.player.Player;
import com.ewareza.shapegame.player.PlayerFactory;
import com.ewareza.shapegame.player.PlayerType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private Map<String, Player> nameToLearningSound = new HashMap<>();
    private Map<String, Player> nameToShapeColorGameSound = new HashMap<>();

    @Override
    public void init(Context context) {
        this.context = context;
        playerFactory = new PlayerFactory(context);

        mainMenuSound = tryToGetPlayer(R.raw.sound_background_music);
        mainMenuSound.setLooping(true);

        wonGameSound = tryToGetPlayer(R.raw.sound_won_game);
        correctShapeFoundSound = tryToGetPlayer(R.raw.sound_correct_shape_clicked);
        wrongShapeFoundSound = tryToGetPlayer(R.raw.sound_wrong_shape_clicked);

        startLearningPhaseOneSound = tryToGetPlayer(R.raw.speech_learning_phase1_sentence1);
        phaseOneSentenceTwo = tryToGetPlayer(R.raw.speech_learning_phase1_sentence2);
        startLearningPhaseTwoSound = tryToGetPlayer(R.raw.speech_learning_phase2);

//        initLearningSoundsList();
//        initShapeColorGameSoundsList();
    }

    public Map<String, Player> getNameToLearningSound() {
        return nameToLearningSound;
    }

    public Map<String, Player> getNameToShapeColorGameSound() {
        return nameToShapeColorGameSound;
    }

    private void initShapeColorGameSoundsList() {
        nameToShapeColorGameSound.put(wonGameSound.getSoundName(), wonGameSound);
    }

    private void initLearningSoundsList() {
        nameToLearningSound.put(startLearningPhaseOneSound.getSoundName(), startLearningPhaseOneSound);
        nameToLearningSound.put(startLearningPhaseTwoSound.getSoundName(), startLearningPhaseTwoSound);
        nameToLearningSound.put(phaseOneSentenceTwo.getSoundName(), phaseOneSentenceTwo);
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

    public static SoundResources getInstance() {
        return INSTANCE;
    }

    public Player getResetStartLearningPhaseOneSound() throws PlayerFactory.UnknownSoundTypeException {
        startLearningPhaseOneSound = resetSound(startLearningPhaseOneSound);
        nameToLearningSound.put(startLearningPhaseOneSound.getSoundName(), startLearningPhaseOneSound);
        return startLearningPhaseOneSound;
    }

    private static Player resetSound(Player player) throws PlayerFactory.UnknownSoundTypeException {
        player.reset();
        player = SoundResources.INSTANCE.createPlayerForId(player.getIdentifier());
        return player;
    }

    public Player getResetStartLearningPhaseTwoSound() throws PlayerFactory.UnknownSoundTypeException {
        startLearningPhaseTwoSound = resetSound(startLearningPhaseTwoSound);
        nameToLearningSound.put(startLearningPhaseTwoSound.getSoundName(), startLearningPhaseTwoSound);
        return startLearningPhaseTwoSound;
    }

    private String getResourceNameById(int id) {
        return context.getResources().getResourceEntryName(id);
    }

    public Player getWonGameSound() {
        return wonGameSound;
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
        nameToLearningSound.put(currentLearningShapeDescription.getSoundName(), currentLearningShapeDescription);
        return currentLearningShapeDescription;
    }

    public Player getMainMenuSound() {
        return mainMenuSound;
    }

    public Player getLearningShapeSelfDescription(String shapeName) throws PlayerFactory.UnknownSoundTypeException {
        String soundName = getLearningShapeSelfDescriptionSoundName(shapeName);
        currentLearningShapeSelfDescription = playerFactory.getPlayer(soundName);
        nameToLearningSound.put(currentLearningShapeSelfDescription.getSoundName(), currentLearningShapeSelfDescription);
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
        nameToShapeColorGameSound.put(currentShapeGameTitleSound.getSoundName(), currentShapeGameTitleSound);
        return currentShapeGameTitleSound;

    }

    public Player getColorGameTitleSound(ColorFactory.Color color) throws PlayerFactory.UnknownSoundTypeException {
        String soundName = String.format("%s_find_%s", PlayerType.SPEECH.getPrefix(), color.getColorName());
        currentColorGameTitleSound = playerFactory.getPlayer(soundName);
        nameToShapeColorGameSound.put(currentColorGameTitleSound.getSoundName(), currentColorGameTitleSound);
        return currentColorGameTitleSound;
    }

    public static Player resetSoundIfIsPlaying(Player player) throws PlayerFactory.UnknownSoundTypeException {
        if (player.isPlaying()) {
            player.stop();
        }

        player.reset();
        player = SoundResources.INSTANCE.createPlayerForId(player.getIdentifier());

        return player;
    }

    public void resetMainMenuSound() throws PlayerFactory.UnknownSoundTypeException {
        mainMenuSound = resetSoundIfIsPlaying(mainMenuSound);
    }

    public Player getResetPhaseOneSentenceTwo() throws PlayerFactory.UnknownSoundTypeException {
        phaseOneSentenceTwo = resetSound(phaseOneSentenceTwo);
        nameToLearningSound.put(phaseOneSentenceTwo.getSoundName(), phaseOneSentenceTwo);
        return phaseOneSentenceTwo;
    }

    public void removeSound(String soundName) {
        if(nameToLearningSound.containsKey(soundName))
            nameToLearningSound.remove(soundName);
        if(nameToShapeColorGameSound.containsKey(soundName))
            nameToShapeColorGameSound.remove(soundName);
    }

    public void addShapeColorGameSound(String soundName, Player player) {
        nameToLearningSound.put(soundName, player);
    }
}
