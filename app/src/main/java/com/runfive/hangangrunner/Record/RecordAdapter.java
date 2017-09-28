package com.runfive.hangangrunner.Record;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Yongjun on 2016-08-08.
 */
public class RecordAdapter extends FragmentPagerAdapter {
    private int recordTabCount;

    public RecordAdapter(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.recordTabCount =numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RecordDistance distance = new RecordDistance();
                return distance;

            case 1:
                RecordCalorie calorie = new RecordCalorie();
                return calorie;

            case 2:
                RecordPoint point = new RecordPoint();
                return point;

            default:
                return null;
        }
    }

    @Override
    public int getCount(){

        return recordTabCount;
    }
}


