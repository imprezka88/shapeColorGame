package com.ewareza.shapegame.app.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.ewareza.shapegame.app.utils.DimensionScaler;

public class ScaledFrameLayout extends FrameLayout {
    private ViewGroup.LayoutParams scaledParams;

    public ScaledFrameLayout(Context context) {
        super(context);
    }

    public ScaledFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaledFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        if(scaledParams == null )
            scaledParams = DimensionScaler.getScaledParams(params);
        super.setLayoutParams(scaledParams);
    }
}
