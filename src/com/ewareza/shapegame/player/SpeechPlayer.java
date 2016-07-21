package com.ewareza.shapegame.player;

import android.media.MediaPlayer;
import com.ewareza.shapegame.app.Game;

public class SpeechPlayer implements Player {
    private int identifier;
    private MediaPlayer delegate = new MediaPlayer();

    private SpeechPlayer(MediaPlayer mediaPlayer, int identifier) {
        this.delegate = mediaPlayer;
        this.identifier = identifier;
    }

    public static SpeechPlayer createSpeechPlayer(MediaPlayer mediaPlayer, int identifier) {
        return new SpeechPlayer(mediaPlayer, identifier);
    }

    public int getIdentifier() {
        return identifier;
    }

    @Override
    public void start() {
        if(Game.getSpeechEnabled())
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
