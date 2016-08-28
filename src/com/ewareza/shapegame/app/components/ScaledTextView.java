package com.ewareza.shapegame.app.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ewareza.shapegame.app.utils.DimensionScaler;
import com.ewareza.shapegame.resources.ScaledDimenRes;

public class ScaledTextView extends TextView {
    private ViewGroup.LayoutParams scaledParams;

    public ScaledTextView(Context context) {
        super(context);
    }

    public ScaledTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaledTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        if(scaledParams == null )
            scaledParams = DimensionScaler.getScaledParams(params);
        super.setLayoutParams(scaledParams);
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize((float) ScaledDimenRes.getScaledDimenXForValue(size));
    }
}
