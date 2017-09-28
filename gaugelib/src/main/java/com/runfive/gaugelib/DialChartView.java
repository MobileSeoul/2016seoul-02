package com.runfive.gaugelib;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Locale;

/**
 * Created by Yongjun on 2016-08-08.
 */

public class DialChartView extends View {

    private static final String TAG = "DialChartView";

    private String mTitleText = "无标题";
    private int mTitleTextSize;
    /** 标题在Y 方向的比例值 getHeight() * mTitleLocYFrac */
    private float mTitleLocYFrac;
    /** 百分比值 */
    private float mPrecentValue = 50f;
    private float mPrecentTextSize;

    //    private List<String> mTexts;
    //    private Paint mLabelPaint;
    private Paint mPaint;
    private TextPaint mTitlePaint;

    /** 刻度个数 */
    private int mRuleCount = 10;
    private int mGlobalColor;

    private boolean isShowShadow = true;


    public DialChartView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        mPaint = new Paint();
//        mLabelPaint = new Paint();
        mTitlePaint = new TextPaint();
        // setLayerShadow 这个方法不支持硬件加速，所以我们要测试时必须先关闭硬件加速。
        if(isShowShadow) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DialChartView, 0, 0);
        mGlobalColor = a.getColor(R.styleable.DialChartView_golbal_color, Color.WHITE);
        mTitleText = a.getString(R.styleable.DialChartView_chart_title);
        if(TextUtils.isEmpty(mTitleText))
            mTitleText = "No Text Set";
        mPrecentValue = a.getFloat(R.styleable.DialChartView_init_precent,50);
        mTitleTextSize = a.getDimensionPixelOffset(R.styleable.DialChartView_chart_title_textSize,15);
        mTitleLocYFrac = a.getFloat(R.styleable.DialChartView_chart_title_locationY_frac,0.1f);
        mPrecentTextSize = a.getDimensionPixelOffset(R.styleable.DialChartView_precent_text_size,1);
        mRuleCount = a.getInteger(R.styleable.DialChartView_rule_num,10);
        a.recycle();
//
//        mLabelPaint.setColor(Color.RED);
//        mLabelPaint.setAntiAlias(true);
//        mLabelPaint.setStrokeWidth(4);

    }


    public DialChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DialChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DialChartView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public float getPrecentValue() {
        return mPrecentValue;
    }

    public void setPrecentValue(float precentValue) {
        if(precentValue > 100 || precentValue < 0){
            return;
        }
        this.mPrecentValue = precentValue;

        invalidate();
    }

    public void setGlobalColor(int mGlobalColor) {
        this.mGlobalColor = mGlobalColor;
        invalidate();
    }

    public String getTitleText() {
        return mTitleText;
    }

    public void setTitleText(String mTitleText) {
        this.mTitleText = mTitleText;
        invalidate();
    }


    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();

        drawAxis_1(canvas);
        drawAxis_2(canvas);
        drawLabel(canvas);
        drawTitle(canvas);
        drawPointer(canvas);

        super.onDraw(canvas);
    }

    private void setPointCirclePaintAttr() {
        mPaint.setColor(mGlobalColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setShadowLayer(0, 0, 0, 0xFFFF00FF);
    }

    private void setPointerPaintAttr() {
        mPaint.setColor(mGlobalColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShadowLayer(0, 0, 0, Color.BLACK);
    }

    private void setTitlePaintAttr() {
        mTitlePaint.setColor(mGlobalColor);
        mTitlePaint.setAntiAlias(true);
        mTitlePaint.setTextSize(mTitleTextSize);
        mTitlePaint.setShadowLayer(0, 1, 1, Color.GRAY);
    }

    private void setPrecentPaintAttr() {
        mTitlePaint.setColor(mGlobalColor);
        mTitlePaint.setAntiAlias(true);
        mTitlePaint.setTextSize(mPrecentTextSize);
        mPaint.setShadowLayer(0, 0, 0, 0xFFFF00FF);
    }

    private void setAxis2PaintAttr() {
        mPaint.setColor(mGlobalColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(getResources().getDimensionPixelOffset(R.dimen.arc_rule_height));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShadowLayer(2, 2, 2, Color.GRAY);
    }

    private void setAxisPaintAttr() {
        mPaint.setColor(mGlobalColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(getResources().getDimensionPixelOffset(R.dimen.arc_line_width));
        // Stroke 绘制圆弧
        mPaint.setStyle(Paint.Style.STROKE);//

        mPaint.setShadowLayer(5, 2, 2, Color.GRAY);
    }


    /**
     * 角度转弧度
     *
     * @param angle
     * @return
     */
    private double getRad(float angle) {
        return Math.PI / 180f * angle;
    }

    /**
     * 绘制指针
     *
     * @param canvas
     */
    private void drawPointer(Canvas canvas) {
        setPointerPaintAttr();

        // 绘制中心圆点
        float radius = getResources().getDimension(R.dimen.pointer_circle_radius);
        canvas.drawCircle(getChartCenterX(), getChartCenterY(), radius, mPaint);
        // 绘制指针
        Path path = new Path();
        double rad = getRad(precentToAngle(mPrecentValue));
        // 게이지 화살표 X축
        float newXDelta = (float) (Math.sin(rad) * radius);
        // 게이지 화살표 Y축
        float newYDelta = (float) (Math.cos(rad) * radius);

        Log.d(TAG, "newXDelta:" + newXDelta + ",newYDelta:" + newYDelta);
        // 左边点
        path.moveTo(getChartCenterX() - newXDelta, getChartCenterY() + newYDelta);
        // 右边点
        path.lineTo(getChartCenterX() + newXDelta, getChartCenterY() - newYDelta);
        //确定指针顶点位置 cos(0) = 1 , sin(0)=0
        float pointLegth = getPointLegth();
        float topX = (float) (getChartCenterX() - pointLegth * Math.cos(rad));
        float topY = (float) (getChartCenterY() - pointLegth * Math.sin(rad));
        path.lineTo(topX, topY);
        path.close();
        canvas.drawPath(path, mPaint);

        // 绘制中心圆环
        setPointCirclePaintAttr();
        float circleRadius = radius + 4;
        canvas.drawCircle(getChartCenterX(), getChartCenterY(), circleRadius, mPaint);

        // 绘制百分比文字
        setPrecentPaintAttr();
        String precent = String.format(Locale.CHINA,"%.2f%%",mPrecentValue);
        float textHeight = getResources().getDimension(R.dimen.default_text_size);
        float textWidth = mTitlePaint.measureText(precent);
        canvas.drawText(precent,
                getChartCenterX() - textWidth/2f,
                getChartCenterY() + circleRadius + textHeight,mTitlePaint);

    }

    /**
     * 获取指针长度
     * @return
     */
    private float getPointLegth() {
//        return getUsableWidth() * 0.56f / 2;
        return getInnerArcRadius() - getResources().getDimensionPixelOffset(R.dimen.chart_point_margin_top);
    }

    private float precentToAngle(float precent){
        return 1.8f * precent;
    }

    private void drawTitle(Canvas canvas) {
        setTitlePaintAttr();

        // 绘制底部文字
        float y = getHeight() * mTitleLocYFrac;

        float textWidth = mTitlePaint.measureText(mTitleText);

        float x = (getWidth() - textWidth) / 2;
        canvas.drawText(mTitleText, x, y, mTitlePaint);
    }

    private void drawLabel(Canvas canvas) {
    }

    /**
     * 绘制刻度线
     *
     * @param canvas
     */
    private void drawAxis_2(Canvas canvas) {
        setAxis2PaintAttr();

        RectF rect = new RectF();
        float radius = getInnerArcRadius();
        float centerX = getChartCenterX();
        float centerY = getChartCenterY();

        float left = centerX - radius;
        float top = centerY - radius;
        float right = centerX + radius;
        float bottom = centerY + radius;

        rect.set(left, top, right, bottom);

        float angleWidth = getResources().getInteger(R.integer.default_rule_width);
        float startAngle = 180;
        // 绘制刻度数
        float fillCount = (float) (getPrecentValue()*0.1);

        float deltaAngle = 180f/ mRuleCount;
        for (int i = 0; i <= mRuleCount; i++) {
            float delta = deltaAngle * i;
            if (i != 0) {
                delta -= angleWidth / 2f;
            }
            if (i == mRuleCount) {
                delta -= angleWidth / 2f;
            }
            startAngle = 180 + delta;

            if (i == 0 || i == mRuleCount) {
                canvas.drawArc(rect, startAngle, angleWidth, false, mPaint);
            } else {
                canvas.drawArc(rect, startAngle, angleWidth, false, mPaint);
            }
        }
        for (int i = 0; i <= mRuleCount; i++) {
            float delta = deltaAngle * i;
            if (i != 0) {
                delta -= angleWidth / 2f;
            }
            if (i == mRuleCount) {
                delta -= angleWidth / 2f;
            }
            startAngle = 180 + delta;

            if ( i > fillCount) {
                mPaint.setColor(0xaaaaaa);
                canvas.drawArc(rect, startAngle, angleWidth, false, mPaint);
            } else {
                mPaint.setColor(Color.RED);
                canvas.drawArc(rect, startAngle, angleWidth, false, mPaint);
            }
        }

    }


    /**
     * 绘制第一个圆弧
     *
     * @param canvas
     */
    private void drawAxis_1(Canvas canvas) {
        setAxisPaintAttr();
        float percent = getPrecentValue()*1.8f;
        RectF rect = new RectF();
        // 半径
        float radius = 1.0f * getUsableWidth() / 2;
        float centerX = getChartCenterX();
        float centerY = getChartCenterY();

        float left = centerX - radius;
        float top = centerY - radius;

        float right = centerX + radius;
        float bottom = centerY + radius;

        rect.set(left, top, right, bottom);

        canvas.drawArc(rect, 180, 180, false, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(rect, 180, percent, false, mPaint);



    }

    private float getInnerArcRadius() {
        float outArcWidth = getResources().getDimensionPixelOffset(R.dimen.arc_line_width);
        // 半径
        return getUsableWidth() /2 -  outArcWidth - getResources().getDimensionPixelOffset(R.dimen.chart_rule_maring_top);
    }

    /**
     * 获取可用的绘制区域宽度
     * @return
     */
    private float getUsableWidth(){
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        return width > height ? width : height;
    }

    /**
     * 绘制圆心的X
     * @return
     */
    private float getChartCenterX(){
        return getPaddingLeft() + getUsableWidth()/2;
    }

    /**
     * 绘制圆心的Y
     * @return
     */
    private float getChartCenterY(){
        return getPaddingTop() + getUsableWidth()/2;
    }

}
