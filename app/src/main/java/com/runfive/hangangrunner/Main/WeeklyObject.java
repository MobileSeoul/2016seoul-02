package com.runfive.hangangrunner.Main;

/**
 * Created by JunHo on 2016-09-02.
 */
public class WeeklyObject {

    private String[] mData; // 배열로해도되고
    private String date;
    private String dayandmonth;
    private boolean mSelectable = true;
    private String km;
    private String point;
    private String gold;
    private String silver;
    private String bronze;

    /**
     *  넣는 정보가 확실하지 않아서 나중에 생성자 수정 삭제 필요
     * @param obj01
     */

    public WeeklyObject(String obj01) {
        date = obj01;
    }

    public WeeklyObject(String date, String dayandmonth) {
        this.date = date;
        this.dayandmonth = dayandmonth;
    }

    public WeeklyObject(String date, String dayandmonth, String km, String point, String gold, String silver, String bronze) {
        this.date = date;
        this.dayandmonth = dayandmonth;
        this.km = km;
        this.point = point;
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
    }

    public boolean isSelectable() {
        return mSelectable;
    }

    public void setSelectable(boolean selectable) {
        mSelectable = selectable;
    }

    public String getData() {
        return date;
    }

    public String getData(int index) {
        if (mData == null || index >= mData.length) {
            return null;
        }

        return mData[index];
    }

    public String getDayandmonth() {
        return this.dayandmonth;
    }

    public void setData(String[] obj) {
        mData = obj;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getSilver() {
        return silver;
    }

    public void setSilver(String silver) {
        this.silver = silver;
    }

    public String getBronze() {
        return bronze;
    }

    public void setBronze(String bronze) {
        this.bronze = bronze;
    }
}
