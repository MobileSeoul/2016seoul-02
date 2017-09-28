package com.runfive.hangangrunner.RouteCourse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by jinwo on 2016-08-08.
 */
public class RouteCourseAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    String routeName;

    public RouteCourseAdapter(FragmentManager fragmentManager, int mNumOfTabs, String routeName)
    {
        super(fragmentManager);
        this.mNumOfTabs = mNumOfTabs;
        this.routeName = routeName;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle args = new Bundle();

        switch (position) {
            case 0:
                RouteCourseMapFragment routeCourseMapFragment = new RouteCourseMapFragment();
                args.putString("routeName", routeName);
                routeCourseMapFragment.setArguments(args);
                return routeCourseMapFragment;
            case 1:
                RouteCourseImageFragment routeCourseImageFragment = new RouteCourseImageFragment();
                args.putString("routeName", routeName);
                routeCourseImageFragment.setArguments(args);
                return routeCourseImageFragment;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
