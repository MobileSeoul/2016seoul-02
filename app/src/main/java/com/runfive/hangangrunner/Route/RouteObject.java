package com.runfive.hangangrunner.Route;

import android.graphics.drawable.Drawable;

public class RouteObject {
    private Drawable mIcon;
    private String[] mData; // 코스명, 위치, 거리
    private boolean mSelectable = true;

    public RouteObject(Drawable icon, String[] obj) {
        mIcon = icon;
        mData = obj;
    }


    /**
     * Initialize with icon and strings
     *
     * @param icon
     * @param obj01
     * @param obj02
     */

    public RouteObject(Drawable icon, String obj01, String obj02, String obj03) {
        mIcon = icon;

        mData = new String[3];
        mData[0] = obj01;
        mData[1] = obj02;
        mData[2] = obj03;
    }

    public RouteObject(Drawable icon) {
        mIcon = icon;
    }

    public RouteObject(Drawable icon, String obj01, String obj02, String obj03, String lat, String longi) {
        mIcon = icon;
        mData = new String[5];
        mData[0] = obj01;
        mData[1] = obj02;
        mData[2] = obj03;
        mData[3] = lat;
        mData[4] = longi;
    }

    public boolean isSelectable() {
        return mSelectable;
    }

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
    public void setIcon(Drawable icon) {
        mIcon = icon;
    }

    /**
     * Get icon
     *
     * @return
     */
    public Drawable getIcon() {
        return mIcon;
    }

    /**
     * Compare with the input object
     *
     * @param other
     * @return
     */
    public int compareTo(RouteObject other) {
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

