package com.ewareza.shapegame.player;

import android.media.MediaPlayer;
import com.ewareza.shapegame.app.Game;

public class SoundPlayer implements Player {
    private MediaPlayer delegate = new MediaPlayer();
    private int identifier;

    private SoundPlayer(MediaPlayer delegate, int identifier) {
        this.delegate = delegate;
        this.identifier = identifier;

    }

    public static SoundPlayer createSoundPlayer(MediaPlayer mediaPlayer, int identifier) {
        return new SoundPlayer(mediaPlayer, identifier);
    }

    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public void start() {
        if(Game.getSoundsEnabled())
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
