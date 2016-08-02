package com.ewareza.shapegame.player;

import android.media.MediaPlayer;
import com.ewareza.shapegame.app.PersistentGameSettings;
import com.ewareza.shapegame.resources.SoundResources;

import java.util.logging.Level;
import java.util.logging.Logger;

class SpeechPlayer implements Player {
    private Logger logger = Logger.getLogger(SpeechPlayer.class.getName());
    private int identifier;
    private String soundName;
    private MediaPlayer delegate = new MediaPlayer();

    private SpeechPlayer(MediaPlayer mediaPlayer, int identifier, String soundName) {
        this.delegate = mediaPlayer;
        this.identifier = identifier;
        this.soundName = soundName;
    }

    static SpeechPlayer createSpeechPlayer(MediaPlayer mediaPlayer, int identifier, String soundName) {
        return new SpeechPlayer(mediaPlayer, identifier, soundName);
    }

    @Override
    public String getSoundName() {
        return soundName;
    }

    public int getIdentifier() {
        return identifier;
    }

    @Override
    public void startAndRelease() {
        if(PersistentGameSettings.getSpeechEnabled()) {
            delegate.start();
            SoundResources.getInstance().addSound(this);

            MediaPlayer.OnCompletionListener removingListener = new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    SoundResources.getInstance().removeSound(SpeechPlayer.this);
                    SpeechPlayer.this.release();
                }
            };
            this.setOnCompletionListener(removingListener);
        }
    }

    @Override
    public void start() {
        if(PersistentGameSettings.getSpeechEnabled()) {
            delegate.start();
     /*       SoundResources.getInstance().addSound(this);

            MediaPlayer.OnCompletionListener removingListener = new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    SoundResources.getInstance().removeSound(SpeechPlayer.this);
                }
            };
            this.setOnCompletionListener(removingListener);*/
        }
    }

    @Override
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        MediaPlayer.OnCompletionListener removingListener = new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                onCompletionListener.onCompletion(mp);
                SoundResources.getInstance().removeSound(SpeechPlayer.this);
                SpeechPlayer.this.release();
            }
        };

        delegate.setOnCompletionListener(removingListener);
    }

    @Override
    public boolean isPlaying() {
        try {
            return delegate.isPlaying();
        }
        catch (IllegalStateException e) {
            logger.log(Level.WARNING, String.format("Illegal state for sound %s", getSoundName()), e);
        }

        return false;
    }

    @Override
    public void stop() {
        delegate.stop();
    }

    @Override
    public void reset() {
        delegate.reset();
    }

    @Override
    public void setVolume(float v, float v1) {
        delegate.setVolume(v, v1);
    }

    @Override
    public void setLooping(boolean b) {
        delegate.setLooping(b);
    }

    @Override
    public void pause() {
        delegate.pause();
    }

    @Override
    public void release() {
        delegate.release();
    }

}
