package com.runfive.hangangrunner.Record;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.runfive.hangangrunner.Common.BaseActivity;
import com.runfive.hangangrunner.R;

/**
 * Created by Yongjun on 2016-08-01.
 */

public class RecordActivity extends BaseActivity {
    private static final String TAG ="RecordActivity" ;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        rootView = findViewById(R.id.record_container);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.record_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Distance"));
        tabLayout.addTab(tabLayout.newTab().setText("Calorie"));
        tabLayout.addTab(tabLayout.newTab().setText("Point"));



        final ViewPager viewPager = (ViewPager) findViewById(R.id.record_pager);
        final PagerAdapter adapter = new RecordAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });



        //Set nav drawer selected to first item in list
        mNavigationView.getMenu().getItem(2).setChecked(true);
    }
}
