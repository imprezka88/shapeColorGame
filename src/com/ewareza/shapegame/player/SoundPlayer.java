package com.ewareza.shapegame.player;

import android.media.MediaPlayer;
import com.ewareza.shapegame.app.PersistentGameSettings;
import com.ewareza.shapegame.resources.SoundResources;

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
    public void startAndRelease() {
        if(PersistentGameSettings.getSoundsEnabled()) {
            delegate.start();
            SoundResources.getInstance().addSound(this);
            MediaPlayer.OnCompletionListener removingListener = new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    SoundResources.getInstance().removeSound(SoundPlayer.this);
                    SoundPlayer.this.release();
                }
            };
            this.setOnCompletionListener(removingListener);
        }
    }

    @Override
    public void start() {
        if(PersistentGameSettings.getSoundsEnabled()) {
            delegate.start();
        }
    }

    @Override
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        MediaPlayer.OnCompletionListener removingListener = new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                onCompletionListener.onCompletion(mp);
                SoundResources.getInstance().removeSound(SoundPlayer.this);
                SoundPlayer.this.release();
            }
        };

        delegate.setOnCompletionListener(removingListener);
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
