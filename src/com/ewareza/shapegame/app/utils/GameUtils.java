package com.ewareza.shapegame.app.utils;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import com.ewareza.shapegame.domain.factory.ColorFactory;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.resources.DimenRes;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameUtils {
    public static final int LEARNING_FROG_WIDTH = 300;
    public static final int LEARNING_FROG_HEIGHT = 300;

    public static final int LEARNING_SHAPE_TOP = 0;
    public static final int LEARNING_SHAPE_LEFT = LEARNING_FROG_WIDTH + (DimenRes.getScreenWidth() - LEARNING_FROG_WIDTH) / 2 - 100;

    public static final String GAME_TYPE = "gameType";
    public static final String SHAPE = "shape";
    public static final String COLOR = "color";
    public static final int PADDING = 10;
    public static final String RESOURCE_PACKAGE = "com.ewareza.android";
    public static final String RESOURCE_TYPE_DRAWABLE = "drawable";
    public static final String RESOURCE_TYPE_ID = "id";
    public static final String RESOURCE_TYPE_DIMEN = "dimen";
    public static final int LEARNING_SHAPE_ANIMATION_FRAME_DURATION = 200;
    public static final String PHASE_ONE = "phaseOne";
    private static final Random random = new Random();
    public static final String RESOURCE_TYPE_RAW = "raw";
    private static Paint gameBackgroundPaint = initBackgroundPaint();

    private static Paint gameTitleLinePaint = initLinePaint();
    private static int paddingBetweenShapes;

    private GameUtils() {
    }

    private static Paint initBackgroundPaint() {
        gameBackgroundPaint = new Paint();
        gameBackgroundPaint.setColor(Color.WHITE);

        return gameBackgroundPaint;
    }

    public static void StopThread(Thread displayThread) {
        boolean retry = true;
        while (retry) {
            try {
                displayThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //@TODO
            }
        }
    }

    public static int getRandomInt(int from, int to) {
        return random.nextInt(1 + to - from) + from;
    }

    public static ColorFactory.Color getGameTitleShapeColor() {
        return ColorFactory.Color.SHAPE_TITLE;
    }

    public static Paint initLinePaint() {
        gameTitleLinePaint = new Paint();
        gameTitleLinePaint.setColor(Color.WHITE);
        gameTitleLinePaint.setStrokeWidth(5);

        return gameTitleLinePaint;
    }

    public static Paint getGameTitleLinePaint() {
        return gameTitleLinePaint;
    }

    public static boolean collidesWithExistingShapes(AbstractShape shapeToExamine, List<AbstractShape> shapes) {
        for (AbstractShape shape : shapes) {
            if (shape.intersects(shapeToExamine)) {
                return true;
            }
        }

        return false;
    }

    public static <E> E getRandomElement(Collection<E> collection) {
        int elementIndex = random.nextInt(collection.size());
        Iterator<E> iterator = collection.iterator();
        E element = null;

        for (int i = 0; i <= elementIndex; i++) {
            if (iterator.hasNext())
                element = iterator.next();
        }

        return element;
    }


    public static Rect getRectWithPadding(Rect rect, int padding) {
        return new Rect(rect.left + padding, rect.top + padding, rect.right - padding, rect.bottom - padding);
    }

    public static String getCurrentGameType(Bundle bundle) {
        String gameType = "";

        if (bundle != null)
            gameType = bundle.getString(GAME_TYPE);
        return gameType;
    }
}
