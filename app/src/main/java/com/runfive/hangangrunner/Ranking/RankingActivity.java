package com.runfive.hangangrunner.Ranking;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.runfive.hangangrunner.Common.BaseActivity;
import com.runfive.hangangrunner.R;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by JunHo on 2016-08-02.
 */

// 랭킹 액티비티제어
public class RankingActivity extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_rank); // activity_rank 는 네비랑 액션바받은 레이아웃

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container); // 컨테이너는 리스트뷰쪽
        try {
            mViewPager.setAdapter(mSectionsPagerAdapter);
        } catch (Exception e){e.printStackTrace();}


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //


        mNavigationView.getMenu().getItem(3).setChecked(true);
    }

        // 옵션메뉴 없이?
      @Override
      public boolean onCreateOptionsMenu(Menu menu) {
           //Inflate the menu; this adds items to the action bar if it is present.
           getMenuInflater().inflate(R.menu.menu_main, menu);
           return true;
      }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {

            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // 여기수정 ranking_main_fragment 빈 레이아웃인데 참조해야함...
            View rootView = inflater.inflate(R.layout.ranking_main_fragment, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            Fragment fragment = null;
            Bundle args = new Bundle();
            // args.putInt(Fragment1.ARG_SECTION_NUMBER, position + 1);
            //fragment.setArguments(args);
            // r//eturn fragment;

            //Toast.makeText(getApplicationContext(), position, Toast.LENGTH_LONG).show();
            // 매개변수로 받은 포지션별로 섹션을 나눈다 여기선 총 3화면
            // 밑에 ARG... 는 상수인데 그냥 어떤 프래그먼트가 넘어가는지를 나타내준다
            // 케이스별로 프래그먼트생성해서 번들로 넘김
            switch (position) {
                case 0:
                    fragment = new RankingPointFragment();
                    args = new Bundle();
                    args.putInt(RankingPointFragment.ARG_SECTION_NUMBER, position + 1);
                    break;
                case 1:
                    fragment = new RankingDistanceFragment();
                    args.putInt(RankingDistanceFragment.ARG_SECTION_NUMBER, position + 1);
                    args = new Bundle();
                    break;
                case 2:
                    fragment = new RankingMedalFragment();
                    args.putInt(RankingMedalFragment.ARG_SECTION_NUMBER, position + 1);
                    args = new Bundle();
            }

            return fragment;

            // args.putInt(Fragment1.ARG_SECTION_NUMBER, position + 1);
            //fragment.setArguments(args);
            // r//eturn fragment;



            // return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        // 탭 화면 구성
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "POINT";
                case 1:
                    return "DISTANCE";
                case 2:
                    return "MEDAL";
            }
            return null;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}


    /** HIDE TOOLBAR **/
//    @Override
//    protected boolean useToolbar() {
//        return false;
//    }



    /** HIDE hamburger menu **/
//    @Override
//    protected boolean useDrawerToggle() {
//        return false;
//    }

