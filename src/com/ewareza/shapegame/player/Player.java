package com.ewareza.shapegame.player;

import android.media.MediaPlayer;

public interface Player {
    int getIdentifier();

    String getSoundName();

    void startAndRelease();

    void start();

    void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener);

    boolean isPlaying();

    void stop();

    void reset();

    void setVolume(float v, float v1);

    void setLooping(boolean b);

    void pause();

    void release();
}
