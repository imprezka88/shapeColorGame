package com.ewareza.shapegame.player;

import android.content.Context;
import android.media.MediaPlayer;
import com.ewareza.shapegame.app.utils.GameUtils;

public class PlayerFactory {
    private Context context;

    public PlayerFactory(Context context) {
        this.context = context;
    }

    public Player getPlayer(String soundName) throws UnknownSoundTypeException {
        int identifier = context.getResources().getIdentifier(soundName, GameUtils.RESOURCE_TYPE_RAW, GameUtils.RESOURCE_PACKAGE);
        if(identifier == 0)
            throw new IllegalArgumentException(String.format("Could not find sound resource with name: %s", soundName));

        MediaPlayer mediaPlayer = getMediaPlayer(identifier);

        if(soundName.startsWith(PlayerType.SOUND.getPrefix()))
            return SoundPlayer.createSoundPlayer(mediaPlayer, identifier, soundName);
        if(soundName.startsWith(PlayerType.SPEECH.getPrefix()))
            return SpeechPlayer.createSpeechPlayer(mediaPlayer, identifier, soundName);

        throw new UnknownSoundTypeException(String.format("Could not create player for sound name: %s", soundName));
    }

    private MediaPlayer getMediaPlayer(int identifier) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, identifier);
        return mediaPlayer == null ?  new NullMediaPlayer() : mediaPlayer;
    }

    public class UnknownSoundTypeException extends Exception {
        UnknownSoundTypeException(String detailMessage) {
            super(detailMessage);
        }
    }
}
