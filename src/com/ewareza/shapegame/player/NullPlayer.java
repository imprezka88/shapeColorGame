package com.ewareza.shapegame.player;

import android.media.MediaPlayer;

public class NullPlayer implements Player {
    @Override
    public int getIdentifier() {
        return 0;
    }

    @Override
    public String getSoundName() {
        return "";
    }

    @Override
    public void start() {
    }

    @Override
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void stop() {
    }

    @Override
    public void reset() {
    }

    @Override
    public void setVolume(float v, float v1) {
    }

    @Override
    public void setLooping(boolean b) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void release() {

    }

}
