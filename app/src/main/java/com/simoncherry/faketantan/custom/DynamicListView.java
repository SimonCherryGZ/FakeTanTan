package com.simoncherry.faketantan.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Simon on 2016/11/17.
 */

public class DynamicListView extends ListView {

    public DynamicListView(Context context) {
        super(context);
    }

    public DynamicListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
