package com.ewareza.shapegame.resources;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.ewareza.android.R;
import com.ewareza.shapegame.app.utils.GameUtils;

import java.util.logging.Logger;

public enum ScaledDimenRes implements Resources {
    INSTANCE;

    private static final Logger logger = Logger.getLogger(ScaledDimenRes.class.getName());

    private static Logger Log = Logger.getLogger(ScaledDimenRes.class.getName());

    private static int screenHeightInPx;
    private static int screenWidthInPx;
    private static int gameTitleHeightInPx;
    private static int defaultScreenWidthInPx;
    private static int defaultScreenHeightInPx;
    private static int mainScreenGameNameTextSize;
    private static int learningPhaseOneShapePaddingBottom;
    private static int learningPhaseOneShapePaddingLeft;
    private static int learningPhaseOneShapePaddingRight;
    private static int shapeSize1;
    private static int shapeSize2;
    private static int shapeSize3;

    private static int gameOverImageVerticalSpeed;
    private static int gameOverImageHorizontalSpeed;

    private static Context context;

    @Override
    public void init(Context context) {
        ScaledDimenRes.context = context;

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        screenWidthInPx = metrics.widthPixels;
        screenHeightInPx = metrics.heightPixels;
        defaultScreenWidthInPx = context.getResources().getDimensionPixelSize(R.dimen.default_screen_width);
        defaultScreenHeightInPx = context.getResources().getDimensionPixelSize(R.dimen.default_screen_height);

        gameTitleHeightInPx = getScaledDimenYById(R.dimen.gameTitleHeight);
        mainScreenGameNameTextSize = getScaledDimenYForValue(context.getResources().getDimensionPixelSize(R.dimen.main_screen_game_name_text_size));

        learningPhaseOneShapePaddingBottom = getScaledDimenYById(R.dimen.learning_phase_one_shape_padding_bottom);
        learningPhaseOneShapePaddingLeft = getScaledDimenXById(R.dimen.learning_phase_one_shape_padding_left);
        learningPhaseOneShapePaddingRight = getScaledDimenXById(R.dimen.learning_phase_one_shape_padding_right);

        shapeSize1 = getScaledDimenXById(R.dimen.shapeSize1);
        shapeSize2 = getScaledDimenXById(R.dimen.shapeSize2);
        shapeSize3 = getScaledDimenXById(R.dimen.shapeSize3);
        gameOverImageVerticalSpeed = getScaledDimenYById(R.dimen.gameOverImageVerticalSpeed);
        gameOverImageHorizontalSpeed = getScaledDimenYById(R.dimen.gameOverImageHorizontalSpeed);

        logger.info(String.format("Current screen width: %d px, current screen height: %d px", screenWidthInPx, screenHeightInPx));
    }

    public static int getGameOverImageHorizontalSpeed() {
        return gameOverImageHorizontalSpeed;
    }

    public static int getGameOverImageVerticalSpeed() {
        return gameOverImageVerticalSpeed;
    }

    public static int getShapeSize1() {
        return shapeSize1;
    }

    public static int getShapeSize2() {
        return shapeSize2;
    }

    public static int getShapeSize3() {
        return shapeSize3;
    }

    public static int getLearningPhaseOneShapePaddingRight() {
        return learningPhaseOneShapePaddingRight;
    }

    public static int getLearningPhaseOneShapePaddingLeft() {
        return learningPhaseOneShapePaddingLeft;
    }

    public static int getLearningPhaseOneShapePaddingBottom() {
        return learningPhaseOneShapePaddingBottom;
    }

    public static int getMainScreenGameNameTextSize() {
        return mainScreenGameNameTextSize;
    }

    public static int getScreenHeightInPx() {
        return screenHeightInPx;
    }

    public static int getScreenWidthInPx() {
        return screenWidthInPx;
    }

    public static int getGameTitleHeightInPx() {
        return gameTitleHeightInPx;
    }

    public static ScaledDimenRes getInstance() {
        return INSTANCE;
    }

    public static int getDimenById(int id) {
        if(id == 0)
            throw new IllegalArgumentException(String.format("Resource with id: %s not found", id));
        return context.getResources().getDimensionPixelSize(id);
    }

    public static int getScaledDimenXForValue(int value) {
        int scaledValue = (int) (((double) value / defaultScreenWidthInPx) * ScaledDimenRes.getScreenWidthInPx());

        return scaledValue;
    }

    public static int getScaledDimenYForValue(int value) {
        int scaledValue = (int) (((double) value / defaultScreenHeightInPx) * ScaledDimenRes.getScreenHeightInPx());

        return scaledValue;
    }

    public static int getScaledDimenXById(int dimenId) {
        int dimen = getDimenById(dimenId);
        return getScaledDimenXForValue(dimen);
    }

    public static int getScaledDimenYById(int dimenId) {
        int dimen = getDimenById(dimenId);
        return getScaledDimenYForValue(dimen);
    }

    public static int getScaledDimenXByName(String dimenName) {
        return getScaledDimenXForValue(getDimenByName(dimenName));
    }

    public static int getScaledDimenYByName(String dimenName) {
        return getScaledDimenYForValue(getDimenByName(dimenName));
    }

    private static int getDimenByName(String dimenName) {
        int dimenId = context.getResources().getIdentifier(dimenName, GameUtils.RESOURCE_TYPE_DIMEN, GameUtils.RESOURCE_PACKAGE);

        int dimen = 0;

        try {
            dimen = ScaledDimenRes.getDimenById(dimenId);
        }
        catch (Exception e) {
            Log.warning(String.format("Resource with name: %s not found.\n %s", dimenName, e.getMessage()));
        }

        return dimen;
    }

    public static int getLearningFrogLeft() {
        return getScaledDimenXById(R.dimen.learning_frog_margin_left) + getScaledDimenXById(R.dimen.learning_frog_width);
    }
}
