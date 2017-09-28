package com.runfive.hangangrunner.Main;


import com.runfive.calendarlib.entity.Event;

public class CustomEvent implements Event {

    private int color;

    public CustomEvent(int color){
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
