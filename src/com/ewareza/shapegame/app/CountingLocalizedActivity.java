package com.ewareza.shapegame.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import com.ewareza.shapegame.player.SoundResourcesManager;

import java.util.Locale;
import java.util.logging.Logger;

public class CountingLocalizedActivity extends Activity {
    private static int count = 0;
    private static Logger logger = Logger.getLogger(CountingLocalizedActivity.class.getName());

    @Override
    protected void onStart() {
        super.onStart();
        count = count + 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateLocale();
    }

    @Override
    protected void onStop() {
        super.onStop();
        count = count - 1;
        if (count == 0) {
            SoundResourcesManager.stopPlayingMainMenuSoundIfPlaying();
            logger.info("Last activity was stopped. Application is not active");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void updateLocale() {
        Locale locale = PersistentGameSettings.getLocale();
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration conf = new Configuration(resources.getConfiguration());
        conf.locale = locale;
        resources.updateConfiguration(conf, resources.getDisplayMetrics());
    }
}
