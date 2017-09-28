package com.runfive.hangangrunner.Ranking;

import android.graphics.Bitmap;

/**
 * Created by JunHo on 2016-07-31.
 */
public class RankingObject {

    //private Drawable mIcon;
    private Bitmap profileImage;


    private String[] mData; // 순위, 주행거리, 포인트

    private boolean mSelectable = true;

    /**
     * Initialize with icon and data array
     *
     * @param icon
     * @param obj
     */
    public RankingObject(Bitmap icon, String[] obj) {
        profileImage = icon;
        mData = obj;
    }

    /**
     * Initialize with icon and strings
     *
     * @param icon
     * @param obj01
     * @param obj02
     * @param obj03
     */
    public RankingObject(Bitmap icon, String obj01, String obj02, String obj03) {
        profileImage = icon;

        mData = new String[3];
        mData[0] = obj01;
        mData[1] = obj02;
        mData[2] = obj03;
    }

    /**
     * True if this item is selectable
     */
    public boolean isSelectable() {
        return mSelectable;
    }

    /**
     * Set selectable flag
     */
    public void setSelectable(boolean selectable) {
        mSelectable = selectable;
    }

    /**
     * Get data array
     *
     * @return
     */
    public String[] getData() {
        return mData;
    }

    /**
     * Get data
     */
    public String getData(int index) {
        if (mData == null || index >= mData.length) {
            return null;
        }

        return mData[index];
    }

    /**
     * Set data array
     *
     * @param obj
     */
    public void setData(String[] obj) {
        mData = obj;
    }

    /**
     * Set icon
     *
     * @param icon
     */
    public void setIcon(Bitmap icon) {
        profileImage = icon;
    }

    /**
     * Get icon
     *
     * @return
     */
    public Bitmap getIcon() {
        return profileImage;
    }

    /**
     * Compare with the input object
     *
     * @param other
     * @return
     */
    public int compareTo(RankingObject other) {
        if (mData != null) {
            String[] otherData = other.getData();
            if (mData.length == otherData.length) {
                for (int i = 0; i < mData.length; i++) {
                    if (!mData[i].equals(otherData[i])) {
                        return -1;
                    }
                }
            } else {
                return -1;
            }
        } else {
            throw new IllegalArgumentException();
        }

        return 0;
    }

}
