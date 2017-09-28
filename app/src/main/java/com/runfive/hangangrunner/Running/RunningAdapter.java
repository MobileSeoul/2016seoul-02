package com.runfive.hangangrunner.Running;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Yongjun on 2016-08-03.
 */

public class RunningAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public RunningAdapter(FragmentManager fm, int mNumOfTabs)
    {
        super(fm);
        this.mNumOfTabs=mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle args = new Bundle();

        switch (position) {
            case 0:
                RunningFragment rf = new RunningFragment();
                return rf;
            case 1:
                RunningMapFragment runningMapFragment = new RunningMapFragment();

                return runningMapFragment;
                // 구글맵 조져

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

