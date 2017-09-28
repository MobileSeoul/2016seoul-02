package com.runfive.hangangrunner.Record.chart;

import java.util.Calendar;

public class PointValue {
    public String horizontal_value;
    public Calendar horizontal_calendar;
    public String verical_value;

    public String title;
    public String title_sub;
    public float x;
    public float y;
    public boolean bEmpty;

    public PointValue(){

    }

    public PointValue(float x,float y){
        this.x = x;
        this.y = y;
    }
}
