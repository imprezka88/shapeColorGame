package com.ewareza.shapegame.app.utils;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.ewareza.shapegame.resources.ScaledDimenRes;

public class DimensionScaler {
    public static ViewGroup.LayoutParams getScaledParams(ViewGroup.LayoutParams params) {
        params.width = (int) ScaledDimenRes.getScaledDimenXForValue(params.width);
        params.height = (int) ScaledDimenRes.getScaledDimenYForValue(params.height);

        if(params instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linearLayoutParams = (LinearLayout.LayoutParams) params;

            linearLayoutParams.leftMargin = (int) ScaledDimenRes.getScaledDimenXForValue(linearLayoutParams.leftMargin);
            linearLayoutParams.topMargin = (int) ScaledDimenRes.getScaledDimenYForValue(linearLayoutParams.topMargin);
            linearLayoutParams.rightMargin = (int) ScaledDimenRes.getScaledDimenXForValue(linearLayoutParams.rightMargin);
            linearLayoutParams.bottomMargin = (int) ScaledDimenRes.getScaledDimenYForValue(linearLayoutParams.bottomMargin);

            return linearLayoutParams;
        }

        return params;
    }
}
