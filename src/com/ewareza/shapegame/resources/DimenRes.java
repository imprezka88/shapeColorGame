package com.ewareza.shapegame.resources;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.ewareza.android.R;
import com.ewareza.shapegame.app.utils.GameUtils;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public enum DimenRes implements Resources {
    INSTANCE;

    private static Logger Log = Logger.getLogger(DimenRes.class.getName());

    private static int screenHeightInPixels;
    private static int screenWidthInPixels;
    private static int gameTitleHeight;
    private static int defaultScreenWidth;
    private static int defaultScreenHeight;

    private static Context context;

    @Override
    public void init(Context context) {
        this.context = context;
        gameTitleHeight = context.getResources().getDimensionPixelSize(R.dimen.gameTitleHeight);

        Display display = ((WindowManager) context
                        .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        int realWidth = metrics.widthPixels;
        int realHeight = metrics.heightPixels;

        if (Build.VERSION.SDK_INT >= 17){
            DisplayMetrics realMetrics = new DisplayMetrics();
            display.getRealMetrics(realMetrics);
            realWidth = realMetrics.widthPixels;
            realHeight = realMetrics.heightPixels;

        } else if (Build.VERSION.SDK_INT >= 14) {
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } catch (Exception e) {
                realWidth = metrics.widthPixels;
                realHeight = metrics.heightPixels;
            }
        }

        screenWidthInPixels = realWidth;
        screenHeightInPixels = realHeight;
        defaultScreenWidth = context.getResources().getDimensionPixelSize(R.dimen.default_screen_width);
        defaultScreenHeight = context.getResources().getDimensionPixelSize(R.dimen.default_screen_height);
    }

    public static int getDefaultScreenWidth() {
        return defaultScreenWidth;
    }
    public static int getDefaultScreenHeight() {
        return defaultScreenHeight;
    }

    public static int getScreenHeight() {
        return screenHeightInPixels;
    }

    public static int getScreenWidth() {
        return screenWidthInPixels;
    }

    public static int getGameTitleHeight() {
        return gameTitleHeight;
    }

    public static DimenRes getInstance() {
        return INSTANCE;
    }

    public static int getDimenById(int id) {
        if(id == 0)
            throw new IllegalArgumentException(String.format("Resource with id: %s not found", id));
        return context.getResources().getDimensionPixelSize(id);
    }

    public static int getScaledDimenX(String dimenName) {
        int dimen = getDimenByName(dimenName);

        return (int) (((double) dimen / DimenRes.getDefaultScreenWidth()) * DimenRes.getScreenWidth());
    }

    private static int getDimenByName(String dimenName) {
        int dimenId = context.getResources().getIdentifier(dimenName, GameUtils.RESOURCE_TYPE_DIMEN, GameUtils.RESOURCE_PACKAGE);

        int dimen = 0;

        try {
            dimen = DimenRes.getDimenById(dimenId);
        }
        catch (Exception e) {
            Log.warning(String.format("Resource with name: %s not found.\n %s", dimenName, e.getMessage()));
        }
        return dimen;
    }

    public static int getScaledDimenY(String dimenName) {
        int dimen = getDimenByName(dimenName);
        return (int) (((double) dimen / DimenRes.getDefaultScreenHeight()) * DimenRes.getScreenHeight());
    }
}
