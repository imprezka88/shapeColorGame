package com.ewareza.shapegame.player;

import android.media.MediaPlayer;
import com.ewareza.shapegame.app.PersistentGameSettings;

class SoundPlayer implements Player {
    private final MediaPlayer delegate;
    private final int identifier;
    private final String soundName;

    private SoundPlayer(MediaPlayer delegate, int identifier, String soundName) {
        this.delegate = delegate;
        this.identifier = identifier;
        this.soundName = soundName;
    }

    static SoundPlayer createSoundPlayer(MediaPlayer mediaPlayer, int identifier, String soundName) {
        return new SoundPlayer(mediaPlayer, identifier, soundName);
    }

    @Override
    public String getSoundName() {
        return soundName;
    }

    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public void start() {
        if(PersistentGameSettings.getSoundsEnabled())
            delegate.start();
    }

    @Override
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        delegate.setOnCompletionListener(onCompletionListener);
    }

    @Override
    public boolean isPlaying() {
        return delegate.isPlaying();
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
