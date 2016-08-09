package com.ewareza.shapegame.resources;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import com.ewareza.android.R;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.player.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//@TODO cache media players retrieved dynamically, one play method with sound enability check
public enum SoundResources implements Resources {
    INSTANCE;

    private static Logger Log = Logger.getLogger(SoundResources.class.getName());
    private SoundPool soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

    private Context context;
    private PlayerFactory playerFactory;

    private Player mainMenuSound;

    private Player wonGameSound;
    private Player correctShapeFoundSound;
    private Player wrongShapeFoundSound;

    private List<Player> sounds = new ArrayList<>();

    @Override
    public void init(Context context) {
        this.context = context;
        playerFactory = new PlayerFactory(context);

        mainMenuSound = tryToGetPlayer(R.raw.sound_background_music);
        mainMenuSound.setLooping(true);

        wonGameSound = tryToGetPlayer(R.raw.sound_won_game);
        correctShapeFoundSound = tryToGetPlayer(R.raw.sound_correct_shape_clicked);
        wrongShapeFoundSound = tryToGetPlayer(R.raw.sound_wrong_shape_clicked);
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
        Player player = tryToGetPlayer(R.raw.speech_learning_phase1_sentence1);
        return player;
    }

    public Player getResetStartLearningPhaseTwoSound() throws PlayerFactory.UnknownSoundTypeException {
        Player player = tryToGetPlayer(R.raw.speech_learning_phase2);
        return player;
    }

    private String getResourceNameById(int id) {
        return context.getResources().getResourceEntryName(id);
    }

    public Player getWonGameSound() {
        return wonGameSound;
    }

    public Player getCorrectShapeFoundSound() throws PlayerFactory.UnknownSoundTypeException {
        return resetSound(correctShapeFoundSound);
    }

    public Player getWrongShapeFoundSound() throws PlayerFactory.UnknownSoundTypeException {
        return resetSound(wrongShapeFoundSound);
    }

    public Player getLearningShapeDescription(String shapeName) throws PlayerFactory.UnknownSoundTypeException {
        String soundName = String.format("%s_its_%s", PlayerType.SPEECH.getPrefix(), shapeName);
        return playerFactory.getPlayer(soundName);
    }

    public Player getMainMenuSound() {
        return mainMenuSound;
    }

    public Player getLearningShapeSelfDescription(String shapeName) throws PlayerFactory.UnknownSoundTypeException {
        String soundName = getLearningShapeSelfDescriptionSoundName(shapeName);
        return playerFactory.getPlayer(soundName);
    }

    private static String getLearningShapeSelfDescriptionSoundName(String shapeName) {
        return String.format("%s_im_%s", PlayerType.SPEECH.getPrefix(), shapeName);
    }

    public Player createPlayerForId(int id) {
        return tryToGetPlayer(id);
    }

    public Player getShapeGameTitleSound(String lookedForShapeName) throws PlayerFactory.UnknownSoundTypeException {
        String soundName = String.format("%s_find_%s", PlayerType.SPEECH.getPrefix(), lookedForShapeName);
        return playerFactory.getPlayer(soundName);

    }

    public Player getColorGameTitleSound(ColorFactory.Color color) throws PlayerFactory.UnknownSoundTypeException {
        String soundName = String.format("%s_find_%s", PlayerType.SPEECH.getPrefix(), color.getColorName());
        return playerFactory.getPlayer(soundName);
    }

    public static Player resetSound(Player player) throws PlayerFactory.UnknownSoundTypeException {
        return SoundResources.INSTANCE.createPlayerForId(player.getIdentifier());
    }

    public void resetMainMenuSound() throws PlayerFactory.UnknownSoundTypeException {
        mainMenuSound = resetSound(mainMenuSound);
    }

    public Player getResetPhaseOneSentenceTwo() throws PlayerFactory.UnknownSoundTypeException {
        Player player = tryToGetPlayer(R.raw.speech_learning_phase1_sentence2);
        return player;
    }

    public void addSound(Player player) {
        sounds.add(player);
    }

    public void removeSound(Player player) {
        sounds.remove(player);
    }

    public List<Player> getSounds() {
        return sounds;
    }
}
