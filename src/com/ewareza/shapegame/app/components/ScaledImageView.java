package com.ewareza.shapegame.app.components;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.ewareza.shapegame.app.utils.DimensionScaler;

public class ScaledImageView extends ImageView {
    private ViewGroup.LayoutParams scaledParams;

    public ScaledImageView(Context context) {
        super(context);
    }

    public ScaledImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaledImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        if(scaledParams == null )
            scaledParams = DimensionScaler.getScaledParams(params);
        super.setLayoutParams(scaledParams);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        Drawable drawable = this.getDrawable();

        if(drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).stop();
        }
    }
}
