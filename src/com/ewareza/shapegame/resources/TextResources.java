package com.ewareza.shapegame.resources;

import android.content.Context;
import com.ewareza.android.R;

public enum TextResources implements Resources {
    INSTANCE;

    private static String circle;
    private static String rectangle;
    private static String triangle;

    public static TextResources getInstance() {
        return INSTANCE;
    }


    @Override
    public void init(Context context) {
        circle = context.getString(R.string.circle);
        rectangle = context.getString(R.string.rectangle);
        triangle = context.getString(R.string.triangle);
    }

    public String getRectangle() {
        return rectangle;
    }

    public String getTriangle() {
        return triangle;
    }

    public String getCircle() {
        return circle;
    }


}
