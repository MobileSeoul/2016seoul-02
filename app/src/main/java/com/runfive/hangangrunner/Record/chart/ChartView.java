package com.runfive.hangangrunner.Record.chart;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import com.runfive.hangangrunner.R;

import java.util.ArrayList;
import java.util.List;

public class ChartView extends View {
    private static final String TAG = "ChartView";
    private Context mContext;
    private int mDensity;
    private Scroller mScroller;
    private int mScreenIndex;

    private ChartViewConfig chartViewConfig;
    private Paint mPaintGrid;
    private Path[] mPathGridHorizontal;
    private Path[] mPathGridVerical;
    private Paint mPaintHorizontalKedu;

    private Paint mPaintLable;
    private Paint mPaintLableSub;
    private Paint mPaintLableUnit;
    private Paint mPaintVericalKedu;

    private int verical_unit_extral_x_space;

    private Paint mPaintHorizontalLable;
    private Paint mPaintHorizontalLableSub;

    private Path[] mPathSet;
    private Path[] mPathSetRegion;
    private Path mPath;
    private Paint mPaintPath;
    private Path mPathRegion;
    private Path mPathConnectRegion;
    private Paint mPaintPathConnectRegion;
    private  List<PointValue> listRegionTemp = new ArrayList<>();
    private Paint mPaintPathRegion;
    private Paint mPaintCircle, mPaintCircleOutSide;
    private Paint mPaintIndicator;
    private Paint mPaintIndicatorOutside;
    private Paint mPaintIndicatorTitle;
    private Paint mPaintIndicatorSubTitle;
    private Paint mPaintIndicatorTitleUnit;
    private Paint mPaintIndicatorLineTop;
    private Paint mPaintIndicatorLineBottom;
    private Paint mPaintLinearGradient;
    private GradientDrawable mGradientDrawableLeft;
    private GradientDrawable mGradientDrawableRight;

    private VelocityTracker mVelocityTracker = null;
    private static final int TOUCH_STATE_REST = 0;
    public static int SNAP_VELOCITY = 600;
    private float mLastionMotionX = 0;
    private float mLastionMotionY = 0;
    private int mLastScrollX = 0;
    protected boolean mIsPressd = false;
    private Bitmap mBitmapIndicator;
    private int mIndicatorExtraSpace=100;
    private int mCurrentIndex = 0;
    private float mIndicatorX;
    private float mIndicatroY;
    private float mIndicatorTitleTextSize=14;
    private float mIndicatorTitleSubTextSize = 10;
    private float mIndicatorUnitTextSize = 14;

    private boolean mIsCaculateValue = true;

    private OnChartViewChangeListener mListener;
    public void setOnChartViewChangeListener(OnChartViewChangeListener listener){
        mListener = listener;
    }

    public ChartView(Context context) {
        super(context);
        mContext = context;
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void init(ChartViewConfig config) {


        mDensity = (int) getResources().getDisplayMetrics().density;

        mScroller = new Scroller(mContext);
        //global config
        chartViewConfig = config;
        mPaintGrid = new Paint();
        mPaintHorizontalKedu = new Paint();

        verical_unit_extral_x_space = 20;
        mPaintVericalKedu = new Paint();
        mPaintLable = new Paint();
        if (config.getVerical_unit_lable_color() > 0)
            mPaintLable.setColor(getResources().getColor(config.getVerical_unit_lable_color()));
        mPaintLable.setTextSize(12 * mDensity);
        mPaintLable.setAntiAlias(true);
        mPaintLableSub = new Paint();
        if (config.getVerical_unit_lable_sub_color() > 0)
            mPaintLableSub.setColor(getResources().getColor(config.getVerical_unit_lable_sub_color()));
        mPaintLableSub.setTextSize(10 * mDensity);
        mPaintLableSub.setAntiAlias(true);
        mPaintLableUnit = new Paint();
        if (config.getVerical_unit_color() > 0)
            mPaintLableUnit.setColor(getResources().getColor(config.getVerical_unit_color()));
        mPaintLableUnit.setTextSize(15 * mDensity);
        mPaintLableUnit.setAntiAlias(true);
        mPaintHorizontalLable = new Paint();
        if (config.getVerical_unit_lable_color() > 0)
            mPaintHorizontalLable.setColor(getResources().getColor(config.getVerical_unit_lable_color()));
        mPaintHorizontalLable.setTextSize(12 * mDensity);
        mPaintHorizontalLable.setAntiAlias(true);
        mPaintHorizontalLableSub = new Paint();
        if (config.getVerical_unit_lable_sub_color() > 0)
            mPaintHorizontalLableSub.setColor(getResources().getColor(config.getVerical_unit_lable_sub_color()));
        mPaintHorizontalLableSub.setTextSize(10 * mDensity);
        mPaintHorizontalLableSub.setAntiAlias(true);

        mPaintLinearGradient = new Paint();
        mPaintLinearGradient.setStyle(Paint.Style.FILL);
        mPaintLinearGradient.setAntiAlias(true);
        mGradientDrawableLeft = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,config.getGridViewGradientColorLeft());
        mGradientDrawableRight = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT,config.getGridViewGradientColorRight());

        mPaintPath = new Paint();
        mPaintPath.setStyle(Paint.Style.STROKE);
        mPaintPath.setStrokeWidth(mDensity * 1.5f);
        if (config.getPath_line_color() > 0)
            mPaintPath.setColor(getResources().getColor(config.getPath_line_color()));
        mPaintPath.setAntiAlias(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO)
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mPaintPathRegion = new Paint();
        mPaintPathRegion.setStyle(Paint.Style.FILL);
        //mPaintPathRegion.setStrokeWidth(mDensity * 1.5f);
        mPaintPathRegion.setAntiAlias(true);
        if(chartViewConfig.getRegion_color()>0){
            mPaintPathRegion.setColor(getResources().getColor(config.getRegion_color()));
        }
        mPaintPathRegion.setAlpha(120);

        mPaintPathConnectRegion = new Paint();
        mPaintPathConnectRegion.setStyle(Paint.Style.FILL);
        mPaintPathConnectRegion.setAntiAlias(true);
        if(chartViewConfig.getRegion_connect_color()>0){
            mPaintPathConnectRegion.setColor(getResources().getColor(config.getRegion_connect_color()));
        }
        mPaintPathConnectRegion.setAlpha(80);

        mPaintCircle = new Paint();
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setStrokeWidth(mDensity * 2);
        if (config.getPoint_circle_color_interval() > 0)
            mPaintCircle.setColor(getResources().getColor(config.getPoint_circle_color_interval()));
        mPaintCircle.setTextSize(15 * mDensity);
        mPaintCircle.setAntiAlias(true);

        mPaintCircleOutSide = new Paint();
        mPaintCircleOutSide.setStyle(Paint.Style.FILL);
        mPaintCircleOutSide.setStrokeWidth(mDensity * 2f);
        if (config.getPoint_circle_color_outside() > 0)
            mPaintCircleOutSide.setColor(getResources().getColor(config.getPoint_circle_color_outside()));
        mPaintCircleOutSide.setAntiAlias(true);
        //mPaintCircleOutSide.setAlpha(80);

        mPaintIndicator = new Paint();
        mPaintIndicator.setStyle(Paint.Style.FILL);
        mPaintIndicator.setStrokeWidth(mDensity * 2.0f);
        if (config.getIndicatorLinecolor() > 0)
            mPaintIndicator.setColor(getResources().getColor(config.getIndicatorLinecolor()));
        mPaintIndicator.setAntiAlias(true);

        mPaintIndicatorOutside = new Paint();
        mPaintIndicatorOutside.setStyle(Paint.Style.FILL);
        mPaintIndicatorOutside.setStrokeWidth(mDensity * 2.0f);
        if (config.getIndicator_outside_circle_color() > 0)
            mPaintIndicatorOutside.setColor(getResources().getColor(config.getIndicator_outside_circle_color()));
        mPaintIndicatorOutside.setAntiAlias(true);


        mPaintIndicatorLineTop = new Paint();
        mPaintIndicatorLineTop.setStyle(Paint.Style.FILL);
        mPaintIndicatorLineTop.setStrokeWidth(mDensity * 2.0f);
        if (config.getIndicatorLinecolor() > 0)
            mPaintIndicatorLineTop.setColor(getResources().getColor(config.getIndicatorLinecolor()));
        mPaintIndicatorLineTop.setAntiAlias(true);
        mPaintIndicatorLineBottom = new Paint();
        mPaintIndicatorLineBottom.setStyle(Paint.Style.FILL);
        mPaintIndicatorLineBottom.setStrokeWidth(mDensity * 1.0f);
        if (config.getIndicatorLinecolor() > 0)
            mPaintIndicatorLineBottom.setColor(getResources().getColor(config.getIndicatorLinecolor()));
        mPaintIndicatorLineBottom.setAntiAlias(true);
        mPaintIndicatorTitle = new Paint();
        mPaintIndicatorTitle.setTextSize(mIndicatorTitleTextSize * mDensity);
        if (config.getIndicator_title_color() > 0)
            mPaintIndicatorTitle.setColor(getResources().getColor(chartViewConfig.getIndicator_title_color()));
        mPaintIndicatorTitleUnit = new Paint();
        mPaintIndicatorTitleUnit.setTextSize(mIndicatorUnitTextSize * mDensity);
        if (config.getIndicator_title_color() > 0)
            mPaintIndicatorTitleUnit.setColor(getResources().getColor(chartViewConfig.getIndicator_title_color()));
        mPaintIndicatorSubTitle = new Paint();
        mPaintIndicatorSubTitle.setTextSize(mIndicatorTitleSubTextSize * mDensity);
        if (config.getIndicator_title_color() > 0)
            mPaintIndicatorSubTitle.setColor(getResources().getColor(chartViewConfig.getIndicator_title_color()));

        update();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawGrid(canvas);
        drawHorizontalUnit(canvas);
        caculatePointValue();
        drawFillPointConnectRegion(canvas);
        drawPointRegion(canvas);
        drawPointAndPath(canvas);
//        drawLinearGradient(canvas);
        drawIndicator(canvas);
        drawVericalUnit(canvas);
        setSelection();
        mScreenIndex = getScrollX() / chartViewConfig.getItem_width();
        //setBackground(getResources().getDrawable(R.drawable.main_background));
        setBackgroundColor(getResources().getColor(R.color.colorGray));
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void drawLinearGradient(Canvas canvas){
        if(!chartViewConfig.isShowGridViewGradient())
            return;
        int left = getScrollX();
        int top = 0;
        int right = getScrollX()+20*mDensity;
        int bottom = chartViewConfig.getRow() * chartViewConfig.getItem_height();
        mGradientDrawableLeft.setBounds(left,top,right,bottom);
        mGradientDrawableLeft.draw(canvas);


        left = getScrollX()+getWidth()-20*mDensity;
        top = 0;
        right = getScrollX()+getWidth();
        bottom = chartViewConfig.getRow() * chartViewConfig.getItem_height();
        mGradientDrawableRight.setBounds(left,top,right,bottom);
        mGradientDrawableRight.draw(canvas);

    }

    protected void drawIndicator(Canvas canvas) {
        if(chartViewConfig.getListPoint()==null || chartViewConfig.getListPoint().size()==0)
            return;
        if(!chartViewConfig.isShowIndicator())
            return;
        int indicator_x = getWidth() / 2 + getScrollX();
        int indicator_y =chartViewConfig.getIndicator_radius() * 3 / 2;
        int radius=50;
        if(chartViewConfig.getIndicatorBgRes()>0) {
            if (mBitmapIndicator == null) {
                mBitmapIndicator = BitmapFactory.decodeResource(getResources(), chartViewConfig.getIndicatorBgRes());
            }
            if(mBitmapIndicator!=null)
                radius = mBitmapIndicator.getWidth()/2;
        }else{
            radius = chartViewConfig.getIndicator_radius();
        }
        float line_top_x_start = indicator_x;
        float line_top_x_end = indicator_x;
        float line_top_y_start = indicator_y + radius;
        float line_top_y_end = indicator_y + radius;

        int index = 0;
        boolean isAtPoint = false;
        for (int i = chartViewConfig.getListPoint().size() - 1; i >= 0; i--) {
            Log.d(TAG,"-->i:"+i+"--point x:"+chartViewConfig.getListPoint().get(i).x+"-->indicator_x:"+indicator_x);
            float x = chartViewConfig.getListPoint().get(i).x;
            if (indicator_x+5 >=(int)x) {
                index = i;
                if (indicator_x+5 >= (int)x && (int)x>indicator_x-5) {
                    isAtPoint = true;
                }else{
                    isAtPoint =false;
                }
                break;
            }else{
                isAtPoint =false;
            }
        }
        getMinAndManScrollerValue();
        if (getScrollX() <= minX) {
            line_top_y_end = chartViewConfig.getListPoint().get(0).y;
        } else if (getScrollX() >= maxX) {
            line_top_y_end = chartViewConfig.getListPoint().get(chartViewConfig.getListPoint().size() - 1).y;
        } else {
            if (index + 1 < chartViewConfig.getListPoint().size()) {
                int width_x = (int) (chartViewConfig.getListPoint().get(index + 1).x - chartViewConfig.getListPoint().get(index).x);
                int width_y = (int) (chartViewConfig.getListPoint().get(index + 1).y - chartViewConfig.getListPoint().get(index).y);
                int cha_x = indicator_x - (int) chartViewConfig.getListPoint().get(index).x;
                float progress = cha_x / (width_x * 1.0f);
                float cha_y = progress * width_y;
                line_top_y_end = chartViewConfig.getListPoint().get(index).y + cha_y;
                Log.d(TAG, "progress:" + progress + "-->cha_y:" + cha_y + "--chax:" + cha_x + "-->line_top_y_end:" + line_top_y_end + "-->index:" + index);
            } else {
                line_top_y_end = chartViewConfig.getListPoint().get(chartViewConfig.getListPoint().size() - 1).y;
            }
        }

        if(!chartViewConfig.isIndicatorMoveWithPoint()){
            canvas.drawLine(line_top_x_start, line_top_y_start, line_top_x_end, line_top_y_end, mPaintIndicatorLineTop);
        }else{
            line_top_y_start = line_top_y_end-radius-mIndicatorExtraSpace;
            canvas.drawLine(line_top_x_start, line_top_y_start, line_top_x_end, line_top_y_end, mPaintIndicatorLineTop);
        }

        if(chartViewConfig.getIndicatorBgRes()>0){
            float left = indicator_x - radius;
            if(mBitmapIndicator!=null){
                if(!chartViewConfig.isIndicatorMoveWithPoint()){
                    float top = line_top_y_start-radius;
                    mIndicatorX =left;
                    mIndicatroY = top;
                    canvas.drawBitmap(mBitmapIndicator, left, top, mPaintIndicator);
                }else{
                    float top = line_top_y_start-radius;
                    mIndicatorX =left;
                    mIndicatroY = top;
                    canvas.drawBitmap(mBitmapIndicator,left,top,mPaintIndicator);
                }
            }
        }else{
            if(!isAtPoint){
                if(!chartViewConfig.isIndicatorMoveWithPoint()){
                    if(chartViewConfig.getIndicator_outside_circle_color()>0){
                        canvas.drawCircle(indicator_x,line_top_y_start-radius , radius+6*mDensity, mPaintIndicatorOutside);

                    }
                }else{
                    if(chartViewConfig.getIndicator_outside_circle_color()>0){
                        canvas.drawCircle(indicator_x,line_top_y_start , radius+6*mDensity, mPaintIndicatorOutside);
                    }
                }
            }

            if(!chartViewConfig.isIndicatorMoveWithPoint()){
                line_top_y_start-=radius;
                mIndicatorX = indicator_x-radius;
                mIndicatroY = line_top_y_start-radius;
                canvas.drawCircle(indicator_x,line_top_y_start , radius, mPaintIndicator);
            }else{
                mIndicatorX = indicator_x-radius;
                mIndicatroY = line_top_y_start-radius;
                canvas.drawCircle(indicator_x, line_top_y_start, radius, mPaintIndicator);
            }

        }


        int line_bottom_x_end = indicator_x;
        int line_bottom_y_end = chartViewConfig.getRow() * chartViewConfig.getItem_height();
        canvas.drawLine(line_top_x_end, line_top_y_end, line_bottom_x_end, line_bottom_y_end, mPaintIndicatorLineBottom);

        //canvas.drawCircle(line_top_x_end, line_top_y_end,mDensity*3, mPaintIndicatorLineTop);
        if (isAtPoint) {
            canvas.drawCircle(line_top_x_end, line_top_y_end, mDensity * 4, mPaintIndicatorLineTop);
        } else {
            canvas.drawCircle(line_top_x_end, line_top_y_end, mDensity * 2, mPaintIndicatorLineTop);
        }

        String tilte = "无";
        if (index < chartViewConfig.getListPoint().size() && index >= 0) {
            tilte = chartViewConfig.getListPoint().get(index).title;
        }
        if (TextUtils.isEmpty(tilte)) {
            tilte = "无";
        }
        Rect rectTitle = new Rect();
        mPaintIndicatorTitle.getTextBounds(tilte, 0, tilte.length(), rectTitle);
        String title_unit = chartViewConfig.getIndicator_title_unit();
        Rect rectTitleUnit = new Rect();
        mPaintIndicatorTitleUnit.getTextBounds(title_unit, 0, title_unit.length(), rectTitleUnit);

        //canvas.drawText(tilte, indicator_x - (rectTitle.width() / 2), line_top_y_start, mPaintIndicatorTitle);
        canvas.drawText(tilte, indicator_x - ((rectTitle.width()+rectTitleUnit.width()+2*mDensity)/ 2), line_top_y_start, mPaintIndicatorTitle);
        canvas.drawText(title_unit, indicator_x - ((rectTitle.width()+rectTitleUnit.width()+2*mDensity)/ 2)+rectTitle.width()+2*mDensity, line_top_y_start, mPaintIndicatorTitleUnit);

    }

    /**
     *
     *
     * @param canvas
     */
    protected void drawGrid(Canvas canvas) {
        if(chartViewConfig.isShowGridLine()){
            if (chartViewConfig.getGrid_line_color() > 0)
                mPaintGrid.setColor(getResources().getColor(chartViewConfig.getGrid_line_color()));
            mPaintGrid.setStrokeWidth(mDensity * 0.5f);
            int len = 16;
            len = chartViewConfig.getCloumn() * 2 + chartViewConfig.getListHorizontalKedu().size() - 1;
            int duan = 10;
            if(chartViewConfig.isShowGridHorizontalLine()){
                if(chartViewConfig.isGridLinePathEffect()){
                    if(mPathGridHorizontal==null){
                        PathEffect  effects = new DashPathEffect(new float[]{5,5,5,5},1);
                        mPaintGrid.setAntiAlias(true);
                        mPaintGrid.setStyle(Paint.Style.STROKE);
                        mPaintGrid.setPathEffect(effects);
                        mPathGridHorizontal = new Path[chartViewConfig.getRow()*duan];
                        /*for (int i = 0; i < chartViewConfig.getRow(); i++) {
                            float startX = getScrollX()-chartViewConfig.getItem_width() * chartViewConfig.getCloumn();
                            float startY = i * chartViewConfig.getItem_height();
                            float stopX =  getScrollX()+ (chartViewConfig.getListHorizontalKedu().size()+chartViewConfig.getCloumn())* chartViewConfig.getItem_width();
                            float stopY = i * chartViewConfig.getItem_height();
                            Path path = new Path();
                            path.moveTo(startX, startY);
                            path.lineTo(stopX, stopY);
                            mPathGridHorizontal[i]=path;
                        }*/
                        int index = 0;
                        for (int i = 0; i < chartViewConfig.getRow(); i++) {
                            float start_X = getScrollX()-chartViewConfig.getItem_width() * chartViewConfig.getCloumn();
                            float stop_X =  getScrollX()+ (chartViewConfig.getListHorizontalKedu().size()+chartViewConfig.getCloumn())* chartViewConfig.getItem_width();
                            float jiange = (stop_X-start_X)/duan;
                            for(int j=0;j<duan;j++){
                                Path path = new Path();
                                float startX = start_X+j*jiange;
                                float startY = i * chartViewConfig.getItem_height();
                                float stopX =  start_X+(j+1)*jiange;//getScrollX()+ (chartViewConfig.getListHorizontalKedu().size()+chartViewConfig.getCloumn())* chartViewConfig.getItem_width();
                                float stopY = i * chartViewConfig.getItem_height();
                                path.moveTo(startX, startY);
                                path.lineTo(stopX, stopY);
                                mPathGridHorizontal[index+j]=path;
                            }
                            index=(index+duan);
                        }

                    }
                    final int count = (int) (chartViewConfig.getVerical_unit_end() - chartViewConfig.getVerical_unit_start()) / (int) chartViewConfig.getVerical_unit_incremetal();
                    int start = chartViewConfig.getRow()-count;
                    if(start>=0 && start<=chartViewConfig.getRow()-1){
                    }else{
                        start=0;
                    }
                    for (int i = start; i < chartViewConfig.getRow()*duan; i++) {
                        if (getScrollX() >= (mScreenIndex - chartViewConfig.getCloumn()/2) * chartViewConfig.getItem_width()
                                && getScrollX() <= (mScreenIndex + chartViewConfig.getCloumn() * 2) * chartViewConfig.getItem_width()) {
                            canvas.drawPath(mPathGridHorizontal[i],mPaintGrid);
                        }
                    }
                }else{
                    for (int i = 0; i < chartViewConfig.getRow(); i++) {
                        float startX = -chartViewConfig.getItem_width() * (chartViewConfig.getCloumn() / 2);
                        float startY = i * chartViewConfig.getItem_height();
                        float stopX = (len - chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width();//(chartViewConfig.getListHorizontalKeduValue().size() +chartViewConfig.getCloumn())* chartViewConfig.getItem_width();
                        float stopY = i * chartViewConfig.getItem_height();
                        if (getScrollX() >= (mScreenIndex - chartViewConfig.getCloumn()) * chartViewConfig.getItem_width()
                                && getScrollX() <= (mScreenIndex + chartViewConfig.getCloumn() * 2) * chartViewConfig.getItem_width())
                            canvas.drawLine(startX, startY, stopX, stopY, mPaintGrid);
                    }
                }
            }
            if(chartViewConfig.isShowGridVericalLine()){
                if(chartViewConfig.isGridLinePathEffect()){
                    if(mPathGridVerical==null){
                        PathEffect  effects = new DashPathEffect(new float[]{5,5,5,5},1);
                        mPaintGrid.setAntiAlias(true);
                        mPaintGrid.setStyle(Paint.Style.STROKE);
                        mPaintGrid.setPathEffect(effects);
                        mPathGridVerical = new Path[len+1];
                        for (int i = 0; i < len+1; i++) {
                            float startX = (i - chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width();
                            float startY = 0;
                            float stopX = (i - chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width();
                            float stopY = chartViewConfig.getRow() * chartViewConfig.getItem_height();Path path = new Path();
                            path.moveTo(startX, startY);
                            path.lineTo(stopX, stopY);
                            mPathGridVerical[i]=path;
                            //canvas.drawLine(startX, startY, stopX, stopY, mPaintGrid);
                        }
                    }
                    for (int i = 0; i < len + 1; i++) {
                        if (getScrollX() >= (mScreenIndex - chartViewConfig.getCloumn()) * chartViewConfig.getItem_width()
                                && getScrollX() <= (mScreenIndex + chartViewConfig.getCloumn() * 2) * chartViewConfig.getItem_width())
                            canvas.drawPath(mPathGridVerical[i],mPaintGrid);
                    }
                }else{
                    for (int i = 0; i < len + 1; i++) {
                        float startX = (i - chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width();
                        float startY = 0;
                        float stopX = (i - chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width();
                        float stopY = chartViewConfig.getRow() * chartViewConfig.getItem_height();
                        if (getScrollX() >= (mScreenIndex - chartViewConfig.getCloumn()) * chartViewConfig.getItem_width() && getScrollX() <= (mScreenIndex + chartViewConfig.getCloumn() * 2) * chartViewConfig.getItem_width())
                            canvas.drawLine(startX, startY, stopX, stopY, mPaintGrid);
                    }
                }
            }
        }
    }

    protected void drawVericalUnit(Canvas canvas) {

        if(chartViewConfig.isVerical_line_show()){
            if (chartViewConfig.getGrid_line_kedu_color() > 0)
                mPaintVericalKedu.setColor(getResources().getColor(chartViewConfig.getGrid_line_kedu_color()));
            mPaintVericalKedu.setStrokeWidth(mDensity);
            int bottomY = chartViewConfig.getRow() * chartViewConfig.getItem_height();
            float x_ = getScrollX() + chartViewConfig.getVerical_kedu_leftmargin();
            canvas.drawLine(x_, 0, x_, bottomY, mPaintVericalKedu);
        }

        int levelCount = 5;
        String unit_text = "";
        final int count = (int) (chartViewConfig.getVerical_unit_end() - chartViewConfig.getVerical_unit_start()) / (int) chartViewConfig.getVerical_unit_incremetal();

        for (int i = 0; i < count + 1; i++) {
            float big_value = chartViewConfig.getVerical_unit_start() + i * chartViewConfig.getVerical_unit_incremetal();
            float value = i * chartViewConfig.getVerical_unit_incremetal();
            if (chartViewConfig.getVerical_unit_incremetal() < 1) {
                value = value % 1;
            } else if (chartViewConfig.getVerical_unit_incremetal() < 10) {
                value = value % 10;
            } else if (chartViewConfig.getVerical_unit_incremetal() < 100) {
                value = value % 100;
            }
            Rect rect = new Rect();
            if (chartViewConfig.isVerical_need_to_fragment()) {
                if (i % levelCount == 0) {
                    if (i == 0)
                        continue;
                    if (chartViewConfig.verical_lable_use_integer) {
                        unit_text = String.valueOf((int) big_value);
                    } else if (chartViewConfig.verical_lable_use_integer) {
                        unit_text = String.valueOf(big_value);
                    } else {
                        unit_text = String.valueOf(big_value);
                    }
                    mPaintLable.getTextBounds(unit_text, 0, unit_text.length(), rect);
                    float x = getScrollX() + chartViewConfig.getVerical_kedu_leftmargin() - rect.width() - verical_unit_extral_x_space;
                    float y = (chartViewConfig.getRow() - i) * chartViewConfig.getItem_height();
                    canvas.drawText(unit_text, x, y, mPaintLable);
                } else {
                    if (value == 0) {
                        continue;
                    } else {
                        if (chartViewConfig.verical_lable_use_integer) {
                            unit_text = String.valueOf((int) value);
                        } else {
                            unit_text = String.valueOf(value);
                        }
                    }
                    mPaintLableSub.getTextBounds(unit_text, 0, unit_text.length(), rect);
                    float x = getScrollX() + chartViewConfig.getVerical_kedu_leftmargin() - rect.width() - verical_unit_extral_x_space;
                    if (unit_text.equals("1")) {
                        x -= (2 * mDensity);
                    }
                    float y = (chartViewConfig.getRow() - i) * chartViewConfig.getItem_height();
                    canvas.drawText(unit_text, x, y, mPaintLableSub);
                }
            } else {
                if (i == 0)
                    continue;
                if (chartViewConfig.verical_lable_use_integer) {
                    unit_text = String.valueOf((int) big_value);
                } else if (chartViewConfig.verical_lable_use_integer) {
                    unit_text = String.valueOf(big_value);
                } else {
                    unit_text = String.valueOf(big_value);
                }
                mPaintLable.getTextBounds(unit_text, 0, unit_text.length(), rect);
                float x = getScrollX() + chartViewConfig.getVerical_kedu_leftmargin() - rect.width() - verical_unit_extral_x_space;
                float y = (chartViewConfig.getRow() - i) * chartViewConfig.getItem_height()+rect.height()/2;
                canvas.drawText(unit_text, x, y, mPaintLable);

                if(chartViewConfig.isVerical_kedu_line_show()){
                    float startX = getScrollX() + chartViewConfig.getVerical_kedu_leftmargin();
                    float stopX = startX+15;
                    float startY =(chartViewConfig.getRow() - i) * chartViewConfig.getItem_height();
                    float stopY = (chartViewConfig.getRow() - i) * chartViewConfig.getItem_height();
                    canvas.drawLine(startX, startY, stopX, stopY, mPaintVericalKedu);
                }

            }
        }

        Rect rectUnit = new Rect();
        unit_text = chartViewConfig.getVerical_unit_text();
        mPaintLableUnit.getTextBounds(unit_text, 0, unit_text.length(), rectUnit);
        float x = getScrollX() + chartViewConfig.getVerical_kedu_leftmargin() - rectUnit.width() - verical_unit_extral_x_space;
        float y = (chartViewConfig.getRow() - count) * chartViewConfig.getItem_height() - verical_unit_extral_x_space*2;
        canvas.drawText(unit_text, x, y, mPaintLableUnit);
    }


    private void drawHorizontalUnit(Canvas canvas) {
        String unit_text = "";
        final int count = chartViewConfig.getListHorizontalKedu().size();
        int bottomY_y =  chartViewConfig.getRow() * chartViewConfig.getItem_height();
        //int bottomY = bottomY_y + 40;
        for (int i = 0; i < count; i++) {
            if (i >= mScreenIndex - chartViewConfig.getCloumn() / 2 && i <= mScreenIndex + chartViewConfig.getCloumn()) {
                KeduValue model = chartViewConfig.getListHorizontalKedu().get(i);
                unit_text = model.display_value;
                float kedu_x = (i + chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width();
                if (!TextUtils.isEmpty(unit_text)) {
                    Rect rect = new Rect();
                    mPaintHorizontalLable.getTextBounds(unit_text, 0, unit_text.length(), rect);
                    float x = kedu_x- rect.width() / 2;//- getScrollX();
                    model.current_x = x + rect.width() / 2;
                    Log.d(TAG, "-->mScreenIndex:" + mScreenIndex + "-->i:" + i + "-->x:" + x + "-->getScrollX():" + getScrollX());
                    canvas.drawText(unit_text, x, bottomY_y+rect.height()+5*mDensity, mPaintHorizontalLable);
                }
                if (chartViewConfig.isHorizontal_kedu_line_show()) {
                    canvas.drawLine(kedu_x, bottomY_y, kedu_x, bottomY_y-5*mDensity, mPaintHorizontalKedu);
                }
                unit_text = model.value_unit;
                if (!TextUtils.isEmpty(unit_text)) {
                    Rect rect = new Rect();
                    mPaintHorizontalLableSub.getTextBounds(unit_text, 0, unit_text.length(), rect);
                    float x = (i + chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width() - rect.width() / 2;//- getScrollX();
                    canvas.drawText(unit_text, x, bottomY_y+rect.height() + 5*mDensity, mPaintHorizontalLableSub);
                }
            }
        }

        if (chartViewConfig.getGrid_line_kedu_color() > 0)
            mPaintHorizontalKedu.setColor(getResources().getColor(chartViewConfig.getGrid_line_kedu_color()));
        mPaintHorizontalKedu.setStrokeWidth(mDensity);
        bottomY_y = chartViewConfig.getRow() * chartViewConfig.getItem_height();
        canvas.drawLine(getScrollX(), bottomY_y, getScrollX() + getWidth(), bottomY_y, mPaintHorizontalKedu);

    }

    protected void drawPointAndPath(Canvas canvas) {
        if(chartViewConfig.getListPoint()==null || chartViewConfig.getListPoint().size()==0)
            return;
        if(chartViewConfig.getListPoint().size()>=4 && chartViewConfig.isSmoothPoint()){
            if (mPathSet == null) {
                int size = chartViewConfig.getListPoint().size();
                PointValue[] mBezierControls = new PointValue[4];
                mPathSet = new Path[size];
                for (int i = 0; i < size; i++) {
                    Path mPathTrends = new Path();

                    PointValue l = null;
                    PointValue a = chartViewConfig.getListPoint().get(i);
                    int nextIndex = i+1<=size-1?i+1:size-1;
                    int nextNextIndex = i+2<=size-1?i+2:size-1;
                    PointValue b = chartViewConfig.getListPoint().get(nextIndex);
                    PointValue n = chartViewConfig.getListPoint().get(nextNextIndex);
                    if(i+1>size-1){
                        mPathSet[i]=mPathTrends;
                        break;
                    }
                    if(i+1==size-1){
                        PointValue nn = new PointValue(b.x+50,b.y);
                        n=nn;
                    }
                    if(i==0){
                        PointValue ll = new PointValue(a.x-50,a.y);
                        l = ll;
                        mPathTrends.moveTo(a.x,b.y);
                    }else{
                        l = chartViewConfig.getListPoint().get(i-1);
                    }
                    ChartViewHelper.caculateController(a, b, l, n, mBezierControls);
                    mPathTrends.moveTo(a.x, a.y);
                    mPathTrends.cubicTo(mBezierControls[1].x,mBezierControls[1].y,mBezierControls[2].x,mBezierControls[2].y,b.x,b.y);
                    mPathSet[i]=mPathTrends;
                }
            }
        }else{
            if (mPathSet == null) {
                mPathSet = new Path[chartViewConfig.getListPoint().size()];
                for (int i = 0; i < chartViewConfig.getListPoint().size(); i++) {
                    PointValue point = chartViewConfig.getListPoint().get(i);
                    PointValue pointLast = null;
                    if (i > 0) {
                        pointLast = chartViewConfig.getListPoint().get(i - 1);
                    }
                    Path mPathTrends = new Path();
                    if (i == 0) {
                        mPathTrends.moveTo(point.x, point.y);

                    } else {
                        mPathTrends.moveTo(pointLast.x, pointLast.y);
                        mPathTrends.lineTo(point.x, point.y);
                    }
                    Log.d(TAG, "path -->point.x:" + point.x + "-->point.y:" + point.y);
                    mPathSet[i] = mPathTrends;
                }
            }
        }
        int indicator_x = getWidth() / 2 + getScrollX();
        Log.d(TAG, "----indicator_x:" + indicator_x);

        for (int i = 0; i < chartViewConfig.getListPoint().size(); i++) {
            PointValue point = chartViewConfig.getListPoint().get(i);
            //if (point.x >= (mScreenIndex - chartViewConfig.getCloumn()) * chartViewConfig.getItem_width() && point.x <= (mScreenIndex + chartViewConfig.getCloumn() * 2) * chartViewConfig.getItem_width()) {
            canvas.drawPath(mPathSet[i], mPaintPath);
            //}
        }
        for (int i = 0; i < chartViewConfig.getListPoint().size(); i++) {
            PointValue point = chartViewConfig.getListPoint().get(i);
            if (point.x >= (mScreenIndex - chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width() && point.x <= (mScreenIndex + chartViewConfig.getCloumn() * 2) * chartViewConfig.getItem_width()) {
                canvas.drawCircle(point.x, point.y, mDensity * 7f, mPaintCircleOutSide);
                if(indicator_x+5>=point.x && indicator_x-5<=point.x){
                    //canvas.drawCircle(point.x, point.y, mDensity * 4f, mPaintCircle);
                }else{
                    if(chartViewConfig.isPointCircleIntervalStoke()){
                        mPaintCircle.setStyle(Paint.Style.STROKE);
                        canvas.drawCircle(point.x, point.y, mDensity * 4f, mPaintCircle);
                    }else{
                        mPaintCircle.setStyle(Paint.Style.FILL);
                        canvas.drawCircle(point.x, point.y, mDensity * 4f, mPaintCircle);
                    }
                }
            }
        }
    }


    protected void drawPointRegion(Canvas canvas) {
        if(chartViewConfig.getListPointRegion()==null){
            return;
        }

        if (mPathRegion == null) {
            mPathRegion  = new Path();
        }
        mPathRegion.reset();
        boolean bFirst = true;
        for (int i = 0; i < chartViewConfig.getListPointRegion().size(); i++) {
            PointValue point = chartViewConfig.getListPointRegion().get(i);
            if (point.x >= (mScreenIndex - chartViewConfig.getCloumn()) * chartViewConfig.getItem_width() && point.x <= (mScreenIndex + chartViewConfig.getCloumn() * 2) * chartViewConfig.getItem_width()) {
                PointValue value = chartViewConfig.getListPointRegion().get(i);
                if(bFirst){
                    mPathRegion.moveTo(value.x,value.y);
                    bFirst =false;
                }else{
                    mPathRegion.lineTo(value.x,value.y);
                }
            }
        }
        mPathRegion.close();
        canvas.drawPath(mPathRegion, mPaintPathRegion);
    }

    protected void drawFillPointConnectRegion(Canvas canvas) {
        if(!chartViewConfig.isFillPointRegion())
            return;
        if(chartViewConfig.getListPoint().size()>=4 && chartViewConfig.isSmoothPoint()){
            int bottomY = chartViewConfig.getRow() * chartViewConfig.getItem_height();
            if (mPathSetRegion == null) {
                int size = chartViewConfig.getListPoint().size();
                PointValue[] mBezierControls = new PointValue[4];
                mPathSetRegion = new Path[size];
                for (int i = 0; i < size; i++) {
                    Path mPathTrends = new Path();
                    PointValue l = null;
                    PointValue a = chartViewConfig.getListPoint().get(i);
                    int nextIndex = i+1<=size-1?i+1:size-1;
                    int nextNextIndex = i+2<=size-1?i+2:size-1;
                    PointValue b = chartViewConfig.getListPoint().get(nextIndex);
                    PointValue n = chartViewConfig.getListPoint().get(nextNextIndex);
                    if(i+1>size-1){
                        mPathSetRegion[i]=mPathTrends;
                        break;
                    }
                    if(i+1==size-1){
                        PointValue nn = new PointValue(b.x+50,b.y);
                        n=nn;
                    }
                    if(i==0){
                        PointValue ll = new PointValue(a.x-50,a.y);
                        l = ll;
                        mPathTrends.moveTo(a.x,b.y);
                    }else{
                        l = chartViewConfig.getListPoint().get(i-1);
                    }
                    ChartViewHelper.caculateController(a, b, l, n, mBezierControls);
                    mPathTrends.moveTo(a.x, a.y);
                    mPathTrends.cubicTo(mBezierControls[1].x,mBezierControls[1].y,mBezierControls[2].x,mBezierControls[2].y,b.x,b.y);
                    if(nextIndex>=1 && nextIndex<=size-1){
                        PointValue pointValueBottomRight = new PointValue(b.x,bottomY);
                        PointValue pointValueBottomLeft = new PointValue(a.x,bottomY);
                        mPathTrends.lineTo(pointValueBottomRight.x,pointValueBottomRight.y);
                        mPathTrends.lineTo(pointValueBottomLeft.x,pointValueBottomLeft.y);
                        mPathTrends.close();
                    }
                    mPathSetRegion[i]=mPathTrends;
                }
            }
            for (int i = 0; i < chartViewConfig.getListPoint().size(); i++) {
                PointValue point = chartViewConfig.getListPoint().get(i);
                if (point.x >= (mScreenIndex - chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width() && point.x <= (mScreenIndex + chartViewConfig.getCloumn() * 2) * chartViewConfig.getItem_width()) {
                    canvas.drawPath(mPathSetRegion[i], mPaintPathConnectRegion);
                }
            }

        }else{
            if (mPathConnectRegion == null) {
                mPathConnectRegion  = new Path();
            }
            mPathConnectRegion.reset();

            listRegionTemp.clear();
            listRegionTemp.addAll(chartViewConfig.getListPoint());
            PointValue firstValue = chartViewConfig.getListPoint().get(0);
            int bottomY = chartViewConfig.getRow() * chartViewConfig.getItem_height();
            PointValue firstBottomValue = new PointValue(firstValue.x,bottomY);
            listRegionTemp.add(0,firstBottomValue);

            PointValue lastValue = chartViewConfig.getListPoint().get( chartViewConfig.getListPoint().size()-1);
            PointValue lastBottomValue = new PointValue(lastValue.x,bottomY);
            listRegionTemp.add(lastBottomValue);
            boolean bFirst = true;
            for (int i = 0; i < listRegionTemp.size(); i++) {
                PointValue point = listRegionTemp.get(i);
                if (point.x >= (mScreenIndex - chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width() && point.x <= (mScreenIndex + chartViewConfig.getCloumn() * 2) * chartViewConfig.getItem_width()) {
                    PointValue value = listRegionTemp.get(i);
                    if(bFirst){
                        mPathConnectRegion.moveTo(value.x,value.y);
                        bFirst =false;
                    }else{
                        mPathConnectRegion.lineTo(value.x,value.y);
                    }
                }
            }
            mPathConnectRegion.close();
            canvas.drawPath(mPathConnectRegion,mPaintPathConnectRegion);
        }

    }

    private void calc(PointValue result, PointValue value1, PointValue value2, final float multiplier) {
        float diffX = value2.x - value1.x;
        float diffY = value2.y - value2.y;
        result.x = value1.x+ (diffX * multiplier);
        result.y =value1.y + (diffY * multiplier);


    }

    private int getHorizontalKeduIndex(String value){
        int size = chartViewConfig.getListHorizontalKedu().size();
        for(int i=0;i<size;i++){
            KeduValue keduValue  = chartViewConfig.getListHorizontalKedu().get(i);
            if(chartViewConfig.horizontal_lable_use_integer){
                int valueSrc = Integer.valueOf(keduValue.value);
                if(valueSrc==Integer.valueOf(value)){
                    return i;
                    /*
                    if(i==0){
                        return -1;
                    }else {
                        return i-1;
                    }
                    */
                }
            }
            if(chartViewConfig.horizontal_lable_use_float){
                float valueSrc = Float.valueOf(keduValue.value);
                if(valueSrc==Float.valueOf(value)){
                    /*
                    if(i==0){
                        return -1;
                    }else {
                        return i-1;
                    }*/
                    return i;
                }
            }
           /* if(chartViewConfig.horizontal_lable_use_calendar){

               *//* int valueSrc = Integer.valueOf(keduValue.value);
                if(valueSrc==Integer.valueOf(value)){
                    if(i==0){
                        return -1;
                    }else {
                        return i-1;
                    }
                }*//*
            }*/
        }
        return -1;
    }
    private void caculatePointValue() {
        if(mIsCaculateValue){
            if(chartViewConfig.getListPointRegion()!=null && chartViewConfig.getListPointRegion().size()>0){
                caculatePointRegionValue(chartViewConfig.getListPointRegion(),true);
            }
            caculatePointRegionValue(chartViewConfig.getListPoint(),false);
            mIsCaculateValue = false;
        }
    }

    public void update(){
        Log.d(TAG,"---------------UPDATE");
        mScreenIndex=0;
        mIsCaculateValue = true;
        mPathGridHorizontal= null;
        mPathGridVerical =null;
        mPathSet=null;
        mPathSetRegion=null;
        isFirst=true;
        mScroller.setFinalX(0);
        invalidate();
    }

    private void caculatePointRegionValue(List<PointValue> listPoint, boolean bOverHorizontalKeduRange) {
        if(listPoint==null || listPoint.size()==0)
            return;

        KeduValue firstKeduValue = chartViewConfig.getListHorizontalKedu().get(0);
        int index= 0;
        for (PointValue point : listPoint) {
            if (chartViewConfig.horizontal_lable_use_integer) {
                index++;
                int horizontal_value = Integer.valueOf(String.valueOf(point.horizontal_value));
                int horizontalKeduIndex = (horizontal_value-Integer.valueOf(firstKeduValue.value))/(int)chartViewConfig.horizontal_kedu_interval[0];
                if(horizontalKeduIndex>=0 && horizontalKeduIndex < chartViewConfig.getListHorizontalKedu().size()){
                    int horizontalKeduIndex_value =Integer.valueOf(chartViewConfig.getListHorizontalKedu().get(horizontalKeduIndex).value);//horizontalKeduIndex*(int)chartViewConfig.horizontal_kedu_interval[0];
                    int horizontalKeduIndex_x = (horizontalKeduIndex + chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width();
                    int value = horizontal_value - horizontalKeduIndex_value;
                    float percent = value / (chartViewConfig.horizontal_kedu_interval[0] * 1.0f);
                    point.x = horizontalKeduIndex_x + percent * chartViewConfig.getItem_width();
                }else{
                    if(!bOverHorizontalKeduRange){

                        return ;
                    }
                    int horizontalKeduIndex_value=0;
                    if(horizontalKeduIndex<0)
                        horizontalKeduIndex_value =Integer.valueOf(chartViewConfig.getListHorizontalKedu().get(0).value)-horizontalKeduIndex*(int)chartViewConfig.horizontal_kedu_interval[0];
                    else{
                        int size = chartViewConfig.getListHorizontalKedu().size();
                        int lastKeduvalue = Integer.valueOf(chartViewConfig.getListHorizontalKedu().get(size-1).value);
                        horizontalKeduIndex_value =lastKeduvalue+ (horizontalKeduIndex-size)*(int)chartViewConfig.horizontal_kedu_interval[0];
                    }
                    int horizontalKeduIndex_x = (horizontalKeduIndex + chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width();
                    int value = horizontal_value - horizontalKeduIndex_value;
                    float percent = value / (chartViewConfig.horizontal_kedu_interval[0] * 1.0f);
                    point.x = horizontalKeduIndex_x + percent * chartViewConfig.getItem_width();
                }

            }else if (chartViewConfig.horizontal_lable_use_float) {

                float horizontal_value = Float.valueOf(String.valueOf(point.horizontal_value));
                int horizontalKeduIndex = (int)(horizontal_value-Integer.valueOf(firstKeduValue.value))/(int)chartViewConfig.horizontal_kedu_interval[0];
                if(horizontalKeduIndex>=0 && horizontalKeduIndex < chartViewConfig.getListHorizontalKedu().size()){
                    float horizontalKeduIndex_value =Float.valueOf(chartViewConfig.getListHorizontalKedu().get(horizontalKeduIndex).value);// horizontalKeduIndex*(int)chartViewConfig.horizontal_kedu_interval[0];
                    float horizontalKeduIndex_x = (horizontalKeduIndex + chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width();

                    float value = horizontal_value - horizontalKeduIndex_value;
                    float percent = value / (chartViewConfig.horizontal_kedu_interval[0] * 1.0f);
                    point.x = horizontalKeduIndex_x + percent * chartViewConfig.getItem_width();
                }else{
                    float horizontalKeduIndex_value=0;
                    if(horizontalKeduIndex<0)
                        horizontalKeduIndex_value =Float.valueOf(chartViewConfig.getListHorizontalKedu().get(0).value)-horizontalKeduIndex*(int)chartViewConfig.horizontal_kedu_interval[0];
                    else{
                        int size = chartViewConfig.getListHorizontalKedu().size();
                        float lastKeduvalue = Float.valueOf(chartViewConfig.getListHorizontalKedu().get(size-1).value);
                        horizontalKeduIndex_value =lastKeduvalue+ (horizontalKeduIndex-size)*(int)chartViewConfig.horizontal_kedu_interval[0];
                    }
                    int horizontalKeduIndex_x = (horizontalKeduIndex + chartViewConfig.getCloumn() / 2) * chartViewConfig.getItem_width();
                    float value = horizontal_value - horizontalKeduIndex_value;
                    float percent = value / (chartViewConfig.horizontal_kedu_interval[0] * 1.0f);
                    point.x = horizontalKeduIndex_x + percent * chartViewConfig.getItem_width();
                }
            }
        }

        final int count_verical = (int) (chartViewConfig.getVerical_unit_end() - chartViewConfig.getVerical_unit_start()) / (int) chartViewConfig.getVerical_unit_incremetal();
        for (PointValue point : listPoint) {
            for (int i = count_verical; i >= 0; i--) {
                float value = chartViewConfig.getVerical_unit_start() + i * chartViewConfig.getVerical_unit_incremetal();
                if (chartViewConfig.verical_lable_use_integer) {
                    float verical_value = Float.valueOf(String.valueOf(point.verical_value));
                    if (verical_value >= (int) value) {
                        if (verical_value >= (int) (chartViewConfig.getVerical_unit_end())) {
                            point.y = (chartViewConfig.getRow() - count_verical) * chartViewConfig.getItem_height();
                        } else if (verical_value <= (int) (chartViewConfig.getVerical_unit_start())) {
                            point.y = (chartViewConfig.getRow()) * chartViewConfig.getItem_height();
                        } else {
                            float cha = verical_value - (int) value;
                            float percent = cha / (chartViewConfig.getVerical_unit_incremetal() * 1.0f);
                            int y = (chartViewConfig.getRow() - i) * chartViewConfig.getItem_height();
                            point.y = y - percent * chartViewConfig.getItem_height();
                        }
                    }
                } else if (chartViewConfig.verical_lable_use_float) {
                    float verical_value = Float.valueOf(String.valueOf(point.verical_value));
                    if (verical_value >= Float.valueOf(String.valueOf(value))) {
                        if (verical_value >= Float.valueOf(String.valueOf(chartViewConfig.getVerical_unit_end()))) {
                            point.y = (chartViewConfig.getRow() - count_verical) * chartViewConfig.getItem_height();
                        } else if (verical_value <= Float.valueOf(String.valueOf(chartViewConfig.getVerical_unit_start()))) {
                            point.y = (chartViewConfig.getRow()) * chartViewConfig.getItem_height();
                        } else {
                            float cha = verical_value - Float.valueOf(String.valueOf(value));
                            float percent = cha / (chartViewConfig.getVerical_unit_incremetal() * 1.0f);
                            int y = (chartViewConfig.getRow() - i) * chartViewConfig.getItem_height();
                            point.y = y - percent * chartViewConfig.getItem_height();
                        }
                    }
                }
            }
            if(point.y==0){
                float verical_value = Float.valueOf(String.valueOf(point.verical_value));
                if (chartViewConfig.verical_lable_use_integer) {
                    if (verical_value >= (int) (chartViewConfig.getVerical_unit_end())) {
                        point.y = (chartViewConfig.getRow() - count_verical) * chartViewConfig.getItem_height();
                    } else if (verical_value <= (int) (chartViewConfig.getVerical_unit_start())) {
                        point.y = (chartViewConfig.getRow()) * chartViewConfig.getItem_height();
                    }
                }else if (chartViewConfig.verical_lable_use_float) {
                    if (verical_value >= Float.valueOf(String.valueOf(chartViewConfig.getVerical_unit_end()))) {
                        point.y = (chartViewConfig.getRow() - count_verical) * chartViewConfig.getItem_height();
                    } else if (verical_value <= Float.valueOf(String.valueOf(chartViewConfig.getVerical_unit_start()))) {
                        point.y = (chartViewConfig.getRow()) * chartViewConfig.getItem_height();
                    }
                }
            }
        }

    }

    private boolean isFirst = true;

    private void setSelection() {
        if(chartViewConfig.getListPoint()==null || chartViewConfig.getListPoint().size()==0)
            return;
        Log.d(TAG,"----------------isFirst:"+isFirst);
        if (isFirst) {
            isFirst = false;
            mCurrentIndex = chartViewConfig.getItemSelection();
            if(mCurrentIndex<=0){
                mScroller.setFinalX((int) minX);
            }else{
                boolean has =false;
                for(int i=0;i<chartViewConfig.getListPoint().size();i++){
                    if(mCurrentIndex==i){
                        mScroller.setFinalX(getScrollX()+(int)chartViewConfig.getListPoint().get(i).x-getWidth()/2);
                        has =true;
                        break;
                    }
                }
                if(!has){
                    mScroller.setFinalX((int) maxX);
                }
            }
            invalidate();
        }
/*        if (chartViewConfig.getListPoint().size() == 1) {
            mScroller.setFinalX((int)minX);
            scrollToNearllyPoint();
        } else {
            mScroller.setFinalX((int)maxX);
        }*/


    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }


    private boolean isAtIndicatorRange(MotionEvent event){
        float left = mIndicatorX;
        float right = mIndicatorX+chartViewConfig.getIndicator_radius()*2;
        float top = mIndicatroY;
        float bottom = mIndicatroY+chartViewConfig.getIndicator_radius()*2;
        float x = event.getX()+getScrollX();
        float y = event.getY();
        Log.d(TAG,"left:"+left +"--right:"+right+"--x:"+x+"\\n"
                +"top"+top+"--bottom:"+bottom+"--y:"+y);
        if(x>=left && x<=right && y>=top && y<= bottom){
            return true;
        }
        return false;
    }

    private  boolean isAtInicatorRange =false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (mVelocityTracker == null) {
                mVelocityTracker = VelocityTracker.obtain();
            }
            mVelocityTracker.addMovement(event);
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:{
                    getParent().requestDisallowInterceptTouchEvent(true);
                    //mLastFingerPosition = event.getX();
                    mIsPressd = true;
                    mLastScrollX=0;
                    mLastionMotionX = x;
                    mLastionMotionY = y;
                    mScroller.forceFinished(true);
                    isAtInicatorRange = isAtIndicatorRange(event);
                    invalidate();
                    break;
                }
                case MotionEvent.ACTION_MOVE:{
                    int detaX = (int) (mLastionMotionX - x);
                    scrollBy(detaX, 0);
                    Log.d(TAG, "getScrollX:" + getScrollX());
                    int detaY = (int) (mLastionMotionY - y);
                    if (Math.abs(detaY) > 50) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    if(mLastScrollX!=0 && mListener!=null){
                        int delta = getScrollX()-mLastScrollX;
                        mListener.onChartViewScrolled(delta);
                    }else{
                        mLastScrollX = getScrollX();
                    }
                    mLastionMotionX = x;
                    break;
                }
                case MotionEvent.ACTION_UP:{
                    getParent().requestDisallowInterceptTouchEvent(false);
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000);
                    int velocityX = (int) velocityTracker.getXVelocity();
                    int velocityY = (int) velocityTracker.getYVelocity();
                    if(chartViewConfig.getListPoint()==null || chartViewConfig.getListPoint().size()==0){
                        mScroller.setFinalX((int) 0);
                        invalidate();
                        break;
                    }
                    Log.d(TAG, "velocityX:" + velocityX + "velocityY:" + velocityY + "--SNAP_VELOCITY:" + SNAP_VELOCITY);
                    if(isAtInicatorRange){
                        isAtInicatorRange = isAtIndicatorRange(event);
                        if(isAtInicatorRange && mListener!=null){
                            mListener.onIndicatorClick(mCurrentIndex);
                        }
                    }
                    getMinAndManScrollerValue();
                    if (velocityX > SNAP_VELOCITY) {
                        handleFling(velocityX, velocityY);
                        if(mListener!=null){
                            mListener.onChartViewScrollDirection(ScrollDirection.RIGHT);
                        }
                    }
                    else if (velocityX < -SNAP_VELOCITY) {
                        handleFling(velocityX, velocityY);
                        if(mListener!=null){
                            mListener.onChartViewScrollDirection(ScrollDirection.LEFT);
                        }
                    } else {
                        snapToDestination();
                    }

                    if (mVelocityTracker != null) {
                        mVelocityTracker.recycle();
                        mVelocityTracker = null;
                    }
                    mIsPressd = false;
                    invalidate();
                    break;
                }
                case MotionEvent.ACTION_CANCEL:
                    snapToDestination();
                    //mTouchState = TOUCH_STATE_REST;
                    break;
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private void scrollToNearby() {
        mCurrentIndex = 0;
        int indicator_x = getWidth() / 2 + getScrollX();
        for (int i = chartViewConfig.getListPoint().size() - 1; i >= 0; i--) {
            if (indicator_x > chartViewConfig.getListPoint().get(i).x) {
                mCurrentIndex = i;
                break;
            }
        }
        int width = 0;
        if (mCurrentIndex + 1 < chartViewConfig.getListPoint().size()) {
            width = (int) (chartViewConfig.getListPoint().get(mCurrentIndex + 1).x - chartViewConfig.getListPoint().get(mCurrentIndex).x);
        }
        int cha = indicator_x - (int) chartViewConfig.getListPoint().get(mCurrentIndex).x;
        Log.v(TAG, "--scrollToNearby cha:" + cha + "-->indicator_x:" + indicator_x + "-->index x:" + (int) chartViewConfig.getListPoint().get(mCurrentIndex).x + "-->indicator_x:" + indicator_x);
        if (Math.abs(cha) >= width / 2) {
            int dx = width - Math.abs(cha);
            mScroller.startScroll(getScrollX(), 0, dx, 0, 250);
            mCurrentIndex++;
            mCurrentIndex =Math.min(mCurrentIndex,chartViewConfig.getListPoint().size()-1);//mCurrentIndex++>chartViewConfig.getListPoint().size()-1?mCurrentIndex:mCurrentIndex--;
            invalidate();
        } else {
            int dx = -Math.abs(cha);
            mScroller.startScroll(getScrollX(), 0, dx, 0, 250);
            invalidate();
        }
        if(mListener!=null && chartViewConfig.getListPoint()!=null && chartViewConfig.getListPoint().size()>0){
            mListener.onItemSelected(mCurrentIndex,chartViewConfig.getListPoint().get(mCurrentIndex));
        }
    }
    private void getMinAndManScrollerValue() {
        if(chartViewConfig.getListPoint()==null || chartViewConfig.getListPoint().size()==0){
            int indicator_X = getWidth() / 2 + getScrollX();
            minX = (int) (chartViewConfig.getListPoint().get(0).x - getWidth() / 2);
            maxX = (int) chartViewConfig.getListPoint().get(chartViewConfig.getListPoint().size() - 1).x - getWidth() / 2;
            return;
        }
        int indicator_X = getWidth() / 2 + getScrollX();
        minX = (int) (chartViewConfig.getListPoint().get(0).x - getWidth() / 2);
        maxX = (int) chartViewConfig.getListPoint().get(chartViewConfig.getListPoint().size() - 1).x - getWidth() / 2;
        Log.d(TAG, "--getMinAndManScrollerValue minX:" + minX + "--maxX:" + maxX + "--all_width:"+chartViewConfig.getCloumn() * chartViewConfig.getItem_width()+"getScrollX():"+getScrollX()+"--indicator_X:"+indicator_X);
    }

    private void snapToDestination() {
        if(chartViewConfig.getListPoint()==null || chartViewConfig.getListPoint().size()==0)
            return;
        int nowScrollX = getScrollX();
        getMinAndManScrollerValue();
        Log.d(TAG, "-->snapToDestination minX:" + minX + "-->maxX:" + maxX + "-->nowScrollX:" + nowScrollX);
        maxX = Math.max(maxX, 0);
        if (nowScrollX < minX) {

            int dx = minX - nowScrollX;
            Log.d(TAG, "-->dx :" + dx);
            mScroller.startScroll(nowScrollX, 0, dx, 0, Math.abs(dx));
            invalidate();
            if(mListener!=null && chartViewConfig.getListPoint()!=null && chartViewConfig.getListPoint().size()>0){
                mCurrentIndex = 0;
                mListener.onItemSelected(mCurrentIndex,chartViewConfig.getListPoint().get(mCurrentIndex));
            }
        } else if (nowScrollX > maxX) {
            int dx = maxX - nowScrollX;
            if (maxX == 0) {
                dx = -nowScrollX + minX;
            }
            mScroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx));
            invalidate();
            if(mListener!=null && chartViewConfig.getListPoint()!=null && chartViewConfig.getListPoint().size()>0){
                mCurrentIndex = chartViewConfig.getListPoint().size()-1;
                mListener.onItemSelected(mCurrentIndex,chartViewConfig.getListPoint().get(mCurrentIndex));
            }

        } else if ((nowScrollX <= maxX) && (nowScrollX >= minX)) {
            scrollToNearby();
        }


    }


    private void handleFling(int velocityX, int velocityY) {
        int nowScrollX = getScrollX();
        Log.d(TAG, "--handleFling nowScrollX:" + nowScrollX);
        if ((nowScrollX <= maxX) && (nowScrollX >= minX)) {
            mScroller.fling(getScrollX(), getScrollY(), -velocityX, -velocityY, minX, maxX, 0, 0);
            final int mDurationTime = 2000;
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!mScroller.computeScrollOffset()) {
                        scrollToNearllyPoint();
                    } else {

                    }
                }
            }, mDurationTime);
            invalidate();
        }else{
            snapToDestination();
        }
/*
        else if (nowScrollX < minX) {
            int dx = minX - nowScrollX;
            int duration = Math.abs(dx);
            mScroller.startScroll(getScrollX(), 0, dx, 0, duration);
            if(mListener!=null && chartViewConfig.getListPoint()!=null && chartViewConfig.getListPoint().size()>0){
                mCurrentIndex = 0;
                mListener.onItemSelected(mCurrentIndex,chartViewConfig.getListPoint().get(mCurrentIndex));
            }
        } else if (nowScrollX > maxX) {
            int dx = maxX - nowScrollX;
            int duration = Math.abs(dx);
            mScroller.startScroll(getScrollX(), 0, dx, 0, duration);
            if(mListener!=null && chartViewConfig.getListPoint()!=null && chartViewConfig.getListPoint().size()>0){
                mCurrentIndex = chartViewConfig.getListPoint().size()-1;
                mListener.onItemSelected(mCurrentIndex,chartViewConfig.getListPoint().get(mCurrentIndex));
            }
        }*/


    }

    private void scrollToNearllyPoint() {
        getMinAndManScrollerValue();
        int nowScrollX = getScrollX();
        if ((nowScrollX <= maxX) && (nowScrollX >= minX)) {
            scrollToNearby();
        }
    }

    private int minX;
    private int maxX;



}
