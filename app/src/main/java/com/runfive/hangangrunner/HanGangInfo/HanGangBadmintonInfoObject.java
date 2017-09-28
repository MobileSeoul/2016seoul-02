package com.runfive.hangangrunner.HanGangInfo;

/**
 * Created by JunHo on 2016-08-19.
 */
public class HanGangBadmintonInfoObject {
    private String gigu;    // 한강 지구번호
    private double latitude;   // 위도
    private double longitude;   // 경도
    private String name; // 축구장 이름
    private String tel; // 전화번호
    private String fee; // 요금

    public HanGangBadmintonInfoObject(String gigu, double latitude, double longitude, String name, String tel, String fee) {
        this.gigu = gigu;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.tel = tel;
        this.fee = fee;
    }

    public String getGigu() {
        return gigu;
    }

    public void setGigu(String gigu) {
        this.gigu = gigu;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
