package com.ewareza.shapegame.app.utils;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.ewareza.shapegame.resources.ScaledDimenRes;

public class DimensionScaler {
    public static ViewGroup.LayoutParams getScaledParams(ViewGroup.LayoutParams params) {
        params.width = ScaledDimenRes.getScaledDimenX(params.width);
        params.height = ScaledDimenRes.getScaledDimenY(params.height);

        if(params instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linearLayoutParams = (LinearLayout.LayoutParams) params;

            linearLayoutParams.leftMargin = ScaledDimenRes.getScaledDimenX(linearLayoutParams.leftMargin);
            linearLayoutParams.topMargin = ScaledDimenRes.getScaledDimenY(linearLayoutParams.topMargin);
            linearLayoutParams.rightMargin = ScaledDimenRes.getScaledDimenX(linearLayoutParams.rightMargin);
            linearLayoutParams.bottomMargin = ScaledDimenRes.getScaledDimenY(linearLayoutParams.bottomMargin);

            return linearLayoutParams;
        }

        return params;
    }
}
