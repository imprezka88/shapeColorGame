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
        player.start();

        if(animationDrawable != null) {
            animationDrawable.start();

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    animationDrawable.stop();
                    mp.release();
                }
            });
        }
    }
}
