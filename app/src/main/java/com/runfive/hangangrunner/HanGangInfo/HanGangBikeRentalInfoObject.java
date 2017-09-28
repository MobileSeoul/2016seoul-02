package com.runfive.hangangrunner.HanGangInfo;

/**
 * Created by JunHo on 2016-08-16.
 */
public class HanGangBikeRentalInfoObject {
    private String gigu;
    private double latitude;   // 위도
    private double longitude;   // 경도
    private String fee; // 요금
    private String timeGubun; // 시간대
    private String tel; // 전화번호


    public HanGangBikeRentalInfoObject(String gigu, double lng, double lat, String fee, String timeGubun, String tel) {
        this.gigu = gigu;
        this.longitude = lng;
        this.latitude = lat;
        this.fee = fee;
        this.timeGubun = timeGubun;
        this.tel = tel;
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

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTimeGubun() {
        return timeGubun;
    }

    public void setTimeGubun(String timeGubun) {
        this.timeGubun = timeGubun;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGigu() {
        return gigu;
    }

    public void setGigu(String gigu) {
        this.gigu = gigu;
    }
}
