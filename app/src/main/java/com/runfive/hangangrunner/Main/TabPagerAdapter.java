package com.runfive.hangangrunner.Main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by JunHo on 2016-08-07.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {
    int tabCount;
    private Fragment result;

    public TabPagerAdapter(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.tabCount=numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        /*
        switch (position) {
            case 0:
                DailyFragment daily = new DailyFragment();
                return daily;

            case 1:
                WeeklyFragment weekly = new WeeklyFragment();
                return weekly;

            case 2:
                MonthlyFragment monthly = new MonthlyFragment();
                return monthly;

            default:
                return null;

        }
        */

        switch (position) {

            case 0:
                result = new DailyFragment();
                break;

            case 1:
                result = new WeeklyFragment();
                break;

            case 2:
                result = new MonthlyFragment();
                break;
        }

        return result;
    }

    @Override
    public int getCount(){

        return tabCount;
    }
}
