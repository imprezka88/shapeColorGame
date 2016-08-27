package com.ewareza.shapegame.player;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;

class PlayerWithAnimation {
    private final Player player;
    private final AnimationDrawable animationDrawable;

    PlayerWithAnimation(Player player, AnimationDrawable animationDrawable) {
        this.player = player;
        this.animationDrawable = animationDrawable;
    }

    void start() {
        player.startAndRelease();

        if(animationDrawable != null) {
            animationDrawable.start();

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    animationDrawable.stop();
//                    mp.release();
                }
            });
        }
    }

    void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        player.setOnCompletionListener(listener);
    }
}
