package com.runfive.hangangrunner.Main.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.runfive.calendarlib.view.CircularEventCellView;


public class ExampleCellView extends CircularEventCellView {

    public ExampleCellView(Context context) {
        super(context);
    }

    public ExampleCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExampleCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height =  (3*width)/5;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
