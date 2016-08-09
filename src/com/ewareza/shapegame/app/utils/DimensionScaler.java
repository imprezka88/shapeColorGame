package com.ewareza.shapegame.app.utils;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.ewareza.shapegame.resources.ScaledDimenRes;

public class DimensionScaler {
    public static ViewGroup.LayoutParams getScaledParams(ViewGroup.LayoutParams params) {
        params.width = ScaledDimenRes.getScaledDimenXForValue(params.width);
        params.height = ScaledDimenRes.getScaledDimenYForValue(params.height);

        if(params instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linearLayoutParams = (LinearLayout.LayoutParams) params;

            linearLayoutParams.leftMargin = ScaledDimenRes.getScaledDimenXForValue(linearLayoutParams.leftMargin);
            linearLayoutParams.topMargin = ScaledDimenRes.getScaledDimenYForValue(linearLayoutParams.topMargin);
            linearLayoutParams.rightMargin = ScaledDimenRes.getScaledDimenXForValue(linearLayoutParams.rightMargin);
            linearLayoutParams.bottomMargin = ScaledDimenRes.getScaledDimenYForValue(linearLayoutParams.bottomMargin);

            return linearLayoutParams;
        }

        return params;
    }
}
