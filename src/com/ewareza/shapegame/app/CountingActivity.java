package com.ewareza.shapegame.app;

import android.app.Activity;
import com.ewareza.shapegame.player.SoundResourcesManager;

import java.util.logging.Logger;

public class CountingActivity extends Activity {
    private static int count = 0;
    private static Logger logger = Logger.getLogger(CountingActivity.class.getName());

    @Override
    protected void onStart() {
        super.onStart();
        count = count + 1;
    }

    protected void onStop() {
        super.onStop();
        count = count - 1;
        if (count == 0) {
            SoundResourcesManager.stopPlayingMainMenuSoundIfPlaying();
            logger.info("Last activity was stopped. Application is not active");
        }
    }
}