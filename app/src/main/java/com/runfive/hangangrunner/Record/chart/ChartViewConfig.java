package com.runfive.hangangrunner.Record.chart;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChartViewConfig {
    private static final String TAG ="ChartViewConfig" ;
    private String verical_unit_text = "℃";
    private String horizontal_unit_text = "日";
    private int row;
    private int cloumn = 8;
    private int item_width;
    private int item_height;
    private int grid_line_color;
    private int grid_line_kedu_color;
    private boolean isShowGridLine =true;
    private boolean isShowGridVericalLine=true;
    private boolean isShowGridHorizontalLine = true;
    private boolean isGridLinePathEffect = false;
    private boolean isShowGridViewGradient =true;
    private int end_color = 0x00ffffff;
    private int[] GridViewGradientColorLeft=new int[]{Color.WHITE,Color.parseColor("#00ffffff")};
    private int[] GridViewGradientColorRight=new int[]{Color.WHITE,Color.parseColor("#00ffffff")};
    private float verical_unit_start;
    private float verical_unit_end;
    private float verical_unit_incremetal;
    //public float verical_unit_level_count=5;
    private int verical_unit_color;
    private int verical_unit_lable_color;
    private int verical_unit_lable_sub_color;
    private int verical_kedu_leftmargin;
    private boolean verical_kedu_line_show=false;
    private boolean verical_line_show =true;

    public boolean verical_lable_use_integer = true;
    public boolean verical_lable_use_float = false;
    private boolean verical_need_to_fragment = false;

    private List<KeduValue> listHorizontalKedu = new ArrayList<>();
    public boolean horizontal_lable_use_integer = false;
    public boolean horizontal_lable_use_float = false;
    private boolean horizontal_kedu_line_show=true;
    public long horizontal_kedu_interval[];
    private SimpleDateFormat horizontal_simpleDateFormat;

    private int path_line_color;
    private List<PointValue> listPoint = new ArrayList<>();
    private boolean isSmoothPoint =false;
    private boolean isFillPointRegion =false;
    private int region_connect_color;
    private int point_circle_color_interval = 0;
    private int point_circle_color_outside = 0;
    private boolean isPointCircleIntervalStoke=true;


    private int indicator_line_color = 0;
    private int indicator_outside_circle_color=0;
    private int indicator_title_color = 0;
    private String indicator_title_unit = "";
    private boolean isIndicatorMoveWithPoint =false;
    private int indicatorBgRes;
    private Bitmap mBitmapIndicator;
    private int indicator_radius = 0;
    private boolean isShowIndicator = true;

    private List<PointValue> listPointRegion = new ArrayList<>();
    private int region_color;
    private int item_selection;


    public boolean isShowGridViewGradient() {
        return isShowGridViewGradient;
    }

    public ChartViewConfig setIsShowGridViewGradient(boolean isShowGridViewGradient) {
        this.isShowGridViewGradient = isShowGridViewGradient;
        return this;
    }

    public int[] getGridViewGradientColorLeft() {
        return GridViewGradientColorLeft;
    }

    public ChartViewConfig setGridViewGradientColorLeft(int[] gridViewGradientColorLeft) {
        GridViewGradientColorLeft = gridViewGradientColorLeft;
        return this;
    }

    public int[] getGridViewGradientColorRight() {
        return GridViewGradientColorRight;
    }

    public ChartViewConfig setGridViewGradientColorRight(int[] gridViewGradientColorRight) {
        GridViewGradientColorRight = gridViewGradientColorRight;
        return this;
    }

    public boolean isPointCircleIntervalStoke() {
        return isPointCircleIntervalStoke;
    }

    public ChartViewConfig setIsPointCircleIntervalStoke(boolean isPointCircleStoke) {
        this.isPointCircleIntervalStoke = isPointCircleStoke;
        return this;
    }

    public int getItemSelection() {
        return item_selection;
    }

    public ChartViewConfig setItemSelection(int item_selection) {
        this.item_selection = item_selection;
        return this;
    }

    public int getRegion_connect_color() {
        return region_connect_color;
    }

    public ChartViewConfig setRegion_connect_color(int region_connect_color) {
        this.region_connect_color = region_connect_color;
        return this;
    }

    public boolean isFillPointRegion() {
        return isFillPointRegion;
    }
    public ChartViewConfig setIsFillPointRegion(boolean isFillPointRegion) {
        this.isFillPointRegion = isFillPointRegion;
        return this;
    }
    public boolean isSmoothPoint() {
        return isSmoothPoint;
    }
    public ChartViewConfig setIsSmoothPoint(boolean isSmoothPoint) {
        this.isSmoothPoint = isSmoothPoint;
        return this;
    }

    public int getRegion_color() {
        return region_color;
    }

    public ChartViewConfig setRegion_color(int region_color) {
        this.region_color = region_color;
        return this;
    }

    public List<PointValue> getListPointRegion() {
        return listPointRegion;
    }

    public ChartViewConfig setListPointRegion(List<PointValue> listPointRegion) {
        this.listPointRegion = listPointRegion;
        return this;
    }

    public int getVerical_kedu_leftmargin() {
        return verical_kedu_leftmargin;
    }

    public ChartViewConfig setVerical_kedu_leftmargin(int verical_kedu_leftmargin) {
        this.verical_kedu_leftmargin = verical_kedu_leftmargin;
        return this;
    }

    public boolean isVerical_line_show() {
        return verical_line_show;
    }

    public ChartViewConfig setVerical_line_show(boolean verical_line_show) {
        this.verical_line_show = verical_line_show;
        return this;
    }

    public int getIndicator_radius() {
        return indicator_radius;
    }

    public ChartViewConfig setIndicator_radius(int indicator_radius) {
        this.indicator_radius = indicator_radius;
        return this;
    }

    public String getIndicator_title_unit() {
        return indicator_title_unit;
    }

    public ChartViewConfig setIndicator_title_unit(String indicator_title_unit) {
        this.indicator_title_unit = indicator_title_unit;
        return this;
    }

    public int getIndicator_title_color() {
        return indicator_title_color;
    }

    public ChartViewConfig setIndicator_title_color(int indicator_title_color) {
        this.indicator_title_color = indicator_title_color;
        return this;
    }

    public boolean isIndicatorMoveWithPoint() {
        return isIndicatorMoveWithPoint;
    }

    public ChartViewConfig setIsIndicatorMoveWithPoint(boolean isIndicatorMoveWithPoint) {
        this.isIndicatorMoveWithPoint = isIndicatorMoveWithPoint;
        return this;
    }

    public int getIndicator_outside_circle_color() {
        return indicator_outside_circle_color;
    }

    public ChartViewConfig setIndicator_outside_circle_color(int indicator_outside_circle_color) {
        this.indicator_outside_circle_color = indicator_outside_circle_color;
        return this;
    }

    public int getIndicatorLinecolor() {
        return indicator_line_color;
    }

    public ChartViewConfig setIndicator_Linecolor(int indicator_color) {
        this.indicator_line_color = indicator_color;
        return this;
    }

    public boolean isShowIndicator() {
        return isShowIndicator;
    }

    public ChartViewConfig setIsShowIndicator(boolean isShowIndicator) {
        this.isShowIndicator = isShowIndicator;
        return this;
    }

    public int getIndicatorBgRes() {
        return indicatorBgRes;
    }

    public ChartViewConfig setIndicatorBgRes(int indicatorBgRes) {
        this.indicatorBgRes = indicatorBgRes;
        return this;
    }

    public int getPoint_circle_color_interval() {
        return point_circle_color_interval;
    }

    public ChartViewConfig setPoint_circle_color_interval(int point_circle_color_interval) {
        this.point_circle_color_interval = point_circle_color_interval;
        return this;
    }

    public int getPoint_circle_color_outside() {
        return point_circle_color_outside;
    }

    public ChartViewConfig setPoint_circle_color_outside(int point_circle_color_outside) {
        this.point_circle_color_outside = point_circle_color_outside;
        return this;
    }

    public List<PointValue> getListPoint() {
        return listPoint;
    }

    public ChartViewConfig setListPoint(List<PointValue> listPoint) {
        this.listPoint = listPoint;
        return this;
    }

    public int getPath_line_color() {
        return path_line_color;
    }

    public ChartViewConfig setPath_line_color(int path_line_color) {
        this.path_line_color = path_line_color;
        return this;
    }

    public SimpleDateFormat getHorizontal_simpleDateFormat() {
        return horizontal_simpleDateFormat;
    }

    public ChartViewConfig setHorizontal_simpleDateFormat(SimpleDateFormat horizontal_simpleDateFormat) {
        this.horizontal_simpleDateFormat = horizontal_simpleDateFormat;
        return this;
    }

    public boolean isHorizontal_kedu_line_show() {
        return horizontal_kedu_line_show;
    }

    public ChartViewConfig setHorizontal_kedu_line_show(boolean horizontal_kedu_line_show) {
        this.horizontal_kedu_line_show = horizontal_kedu_line_show;
        return this;
    }

    public boolean isVerical_need_to_fragment() {
        return verical_need_to_fragment;
    }

    public ChartViewConfig setVerical_need_to_fragment(boolean verical_need_to_fragment) {
        this.verical_need_to_fragment = verical_need_to_fragment;
        return this;
    }

    public List<KeduValue> getListHorizontalKedu() {
        return listHorizontalKedu;
    }

    public ChartViewConfig setListHorizontalKeduAndValueType(List<KeduValue> listHorizontal, Object object,String defaultIntervalIfsizeOne) {
        if (object instanceof Integer) {
            horizontal_lable_use_integer = true;
            horizontal_lable_use_float = false;
            //horizontal_lable_use_calendar = false;
        } else if (object instanceof Calendar) {
            horizontal_lable_use_integer = false;
            horizontal_lable_use_float = false;
            //horizontal_lable_use_calendar = true;
        }
        if (object instanceof Float) {
            horizontal_lable_use_integer = false;
            horizontal_lable_use_float = true;
            //horizontal_lable_use_calendar = false;
        }
        this.listHorizontalKedu = listHorizontal;
        for (KeduValue pointValue : listHorizontal) {
            if (horizontal_lable_use_integer) {
                pointValue.value = String.valueOf(Integer.valueOf(pointValue.value));
            } else if (horizontal_lable_use_float) {
                pointValue.value = String.valueOf(Float.valueOf(pointValue.value));
            } /*else if (horizontal_lable_use_calendar) {
                pointValue.value = getHorizontal_simpleDateFormat().format(pointValue.calendar.getTime());
            }*/
        }
        horizontal_kedu_interval = new long[listHorizontal.size()];
        if (listHorizontal.size() > 1 ) {
            if (horizontal_lable_use_integer) {
                for (int i = 0; i <= listHorizontal.size() - 1; i++) {
                    if(i==listHorizontal.size()-1 && i!=0){
                        horizontal_kedu_interval[i] =  horizontal_kedu_interval[i-1];
                    }else{
                        int value1 = Integer.valueOf(listHorizontal.get(i + 1).value);
                        int value = Integer.valueOf(listHorizontal.get(i).value);
                        horizontal_kedu_interval[i] = value1 - value;
                    }
                }
            } else if (horizontal_lable_use_float) {
                for (int i = 0; i < listHorizontal.size() - 1; i++) {
                    if(i==listHorizontal.size()-1 && i!=0){
                        horizontal_kedu_interval[i] =  horizontal_kedu_interval[i-1];
                    }else{
                        horizontal_kedu_interval[i] = (long) (Float.valueOf(listHorizontal.get(i + 1).value) - Float.valueOf(listHorizontal.get(i).value));
                    }
                }
            }/* else if (horizontal_lable_use_calendar) {
                for (int i = 0; i < listHorizontal.size() - 1; i++) {
                    if(i==listHorizontal.size()-1 && i!=0){
                        horizontal_kedu_interval[i] =  horizontal_kedu_interval[i-1];
                    }else{
                        horizontal_kedu_interval[i] = listHorizontal.get(i + 1).calendar.getTimeInMillis() - listHorizontal.get(i).calendar.getTimeInMillis();
                    }
                }
            }*/
        }else{
            if (horizontal_lable_use_integer) {
                horizontal_kedu_interval[0] = Integer.valueOf(defaultIntervalIfsizeOne);
                //horizontal_kedu_interval[0]=Integer.valueOf(listHorizontal.get(0).value);
            }else if(horizontal_lable_use_float){
                //horizontal_kedu_interval[0]=(Long.valueOf(listHorizontal.get(0).value));
                horizontal_kedu_interval[0] = Long.valueOf(defaultIntervalIfsizeOne);
            }/*else if (horizontal_lable_use_calendar) {
                for (int i = 0; i < listHorizontal.size() - 1; i++) {
                    if(i==listHorizontal.size()-1 && i!=0){
                        horizontal_kedu_interval[i] =  horizontal_kedu_interval[i-1];
                    }else{
                        horizontal_kedu_interval[i] = listHorizontal.get(i + 1).calendar.getTimeInMillis() - listHorizontal.get(i).calendar.getTimeInMillis();
                    }
                }
            }*/
        }
        return this;
    }

    public ChartViewConfig setVerical_lable_value_type(Object object) {
        if (object instanceof Integer) {
            verical_lable_use_integer = true;
            verical_lable_use_float = false;
            //verical_lable_use_calendar =false;
        } else if (object instanceof Calendar) {
            verical_lable_use_integer = false;
            verical_lable_use_float = false;
            //verical_lable_use_calendar =true;
        }
        if (object instanceof Float) {
            verical_lable_use_integer = false;
            verical_lable_use_float = true;
            //verical_lable_use_calendar =false;
        }
        return this;
    }


    public int getVerical_unit_lable_sub_color() {
        return verical_unit_lable_sub_color;
    }

    public ChartViewConfig setVerical_unit_lable_sub_color(int verical_unit_lable_sub_color) {
        this.verical_unit_lable_sub_color = verical_unit_lable_sub_color;
        return this;
    }

    public int getVerical_unit_lable_color() {
        return verical_unit_lable_color;
    }

    public ChartViewConfig setVerical_unit_lable_color(int verical_unit_lable_color) {
        this.verical_unit_lable_color = verical_unit_lable_color;
        return this;
    }

    public int getVerical_unit_color() {
        return verical_unit_color;
    }

    public ChartViewConfig setVerical_unit_color(int verical_unit_color) {
        this.verical_unit_color = verical_unit_color;
        return this;
    }


    public float getVerical_unit_end() {
        return verical_unit_end;
    }

    public ChartViewConfig setVerical_unit_end(float verical_unit_end) {
        this.verical_unit_end = verical_unit_end;
        return this;
    }

    public boolean isVerical_kedu_line_show() {
        return verical_kedu_line_show;
    }

    public ChartViewConfig setVerical_kedu_line_show(boolean verical_kedu_line_show) {
        this.verical_kedu_line_show = verical_kedu_line_show;
        return this;
    }

    public float getVerical_unit_start() {
        return verical_unit_start;
    }

    public ChartViewConfig setVerical_unit_start(float verical_unit_start) {
        this.verical_unit_start = verical_unit_start;
        return this;
    }

    public float getVerical_unit_incremetal() {
        return verical_unit_incremetal;
    }

    public ChartViewConfig setVerical_unit_incremetal(float verical_unit_incremetal) {
        this.verical_unit_incremetal = verical_unit_incremetal;
        return this;
    }

    public int getGrid_line_kedu_color() {
        return grid_line_kedu_color;
    }

    public ChartViewConfig setGrid_line_kedu_color(int grid_line_label_color) {
        this.grid_line_kedu_color = grid_line_label_color;
        return this;
    }

    public boolean isShowGridLine() {
        return isShowGridLine;
    }

    public ChartViewConfig setIsShowGridLine(boolean isShowGridLine) {
        this.isShowGridLine = isShowGridLine;
        return this;
    }

    public int getGrid_line_color() {
        return grid_line_color;
    }

    public ChartViewConfig setGrid_line_color(int grid_line_color) {
        this.grid_line_color = grid_line_color;
        return this;
    }

    public boolean isShowGridVericalLine() {
        return isShowGridVericalLine;
    }

    public ChartViewConfig setIsShowGridVericalLine(boolean isShowGridVericalLine) {
        this.isShowGridVericalLine = isShowGridVericalLine;
        return this;
    }

    public boolean isShowGridHorizontalLine() {
        return isShowGridHorizontalLine;
    }

    public ChartViewConfig setIsShowGridHorizontalLine(boolean isShowGridHorizontalLine) {
        this.isShowGridHorizontalLine = isShowGridHorizontalLine;
        return this;
    }

    public boolean isGridLinePathEffect() {
        return isGridLinePathEffect;
    }

    public ChartViewConfig setIsGridLinePathEffect(boolean isGridLinePathEffect) {
        this.isGridLinePathEffect = isGridLinePathEffect;
        return this;
    }

    public String getVerical_unit_text() {
        return verical_unit_text;
    }

    public ChartViewConfig setVerical_unit_text(String verical_unit_text) {
        this.verical_unit_text = verical_unit_text;
        return this;
    }

    public int getRow() {
        return row;
    }

    public ChartViewConfig setRow(int row) {
        this.row = row;
        return this;
    }

    public int getCloumn() {
        return cloumn;
    }

    public ChartViewConfig setCloumn(int cloumn) {
        this.cloumn = cloumn;
        return this;
    }

    public int getItem_width() {
        return item_width;
    }

    public ChartViewConfig setItem_width(int item_width) {
        this.item_width = item_width;
        return this;
    }

    public int getItem_height() {
        return item_height;
    }

    public ChartViewConfig setItem_height(int item_height) {
        this.item_height = item_height;
        return this;
    }

}
