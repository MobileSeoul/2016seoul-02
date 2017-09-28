package com.runfive.hangangrunner.Common;

import java.io.Serializable;

/**
 * Created by JunHo on 2016-09-03.
 */
public class RecordObject implements Serializable {
    private String user_id;
    private String point;
    private String medal;
    private String distance;
    private String time;
    private String kcal;
    private String date;

    public RecordObject(String user_id, String point, String medal, String distance, String time, String kcal, String date) {
        this.user_id = user_id;
        this.point = point;
        this.medal = medal;
        this.distance = distance;
        this.time = time;
        this.kcal = kcal;
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getMedal() {
        return medal;
    }

    public void setMedal(String medal) {
        this.medal = medal;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }
}
