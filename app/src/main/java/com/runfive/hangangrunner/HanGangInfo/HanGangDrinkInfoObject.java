package com.runfive.hangangrunner.HanGangInfo;

/**
 * Created by JunHo on 2016-08-10.
 */
public class HanGangDrinkInfoObject {
    private String gigu;
    private double latitude;   // 위도
    private double longitude;   // 경도

    public HanGangDrinkInfoObject(String gigu, double lng, double lat) {
        this.gigu = gigu;
        this.longitude = lng;
        this.latitude = lat;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
