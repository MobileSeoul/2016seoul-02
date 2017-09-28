package com.runfive.hangangrunner.Common;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.runfive.hangangrunner.HanGangInfo.HangGangInfoMapActivity;
import com.runfive.hangangrunner.Login.LoginActivity;
import com.runfive.hangangrunner.Main.MainActivity;
import com.runfive.hangangrunner.Panorama.PanoramaActivity;
import com.runfive.hangangrunner.R;
import com.runfive.hangangrunner.Ranking.RankingActivity;
import com.runfive.hangangrunner.Record.RecordActivity;
import com.runfive.hangangrunner.Route.RouteActivity;
import com.runfive.hangangrunner.Setting.SettingActivity;
import com.tsengvn.typekit.Typekit;

import org.json.JSONArray;


public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar mActionBarToolbar;
    private DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;
    private UserObject userObject;
    private RecordObject[] recordObject;
    private String loginID;

    /**
     *  user data db
     */
    private String userData = null;
    private JSONArray jsonArrayData = null;
    private String user_id_user = null;
    private String email = null;
    private String name = null;
    private String gender = null;
    private String birth = null;
    private static final String TAG_RESULT="result";
    private static final String TAG_ID = "user_id";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_NAME = "name";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_BIRTH = "birth";

    /**
     *  record data db
     */
    private String recordData = null; // json
    private JSONArray jsonArrayRecord = null;
    private String user_id_record = null;
    private String point = null;
    private String medal = null;
    private String distance = null;
    private String time = null;
    private String kcal = null;
    private String date = null;
    private static final String TAG_POINT = "point";
    private static final String TAG_MEDAL = "medal";
    private static final String TAG_DISTANCE = "distance";
    private static final String TAG_TIME = "time";
    private static final String TAG_KCAL = "kcal";
    private static final String TAG_DATE = "date";


    private static int i=0;

    /**
     *  nav 헤더
     */
    private TextView user_name;
    private String username;

    /**
     * Helper method that can be used by child classes to
     * specify that they don't want a {@link Toolbar}
     * @return true
     */
    protected boolean useToolbar() {
        return true;
    }


    /**
     * Helper method to allow child classes to opt-out of having the
     * hamburger menu.
     * @return
     */
    protected boolean useDrawerToggle() {
        return true;
    }




    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        getActionBarToolbar();

        setupNavDrawer();
    }//end setContentView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumGothicBold.ttf"))
                .addCustom1(Typekit.createFromAsset(this, "fonts/InterparkGothicBold.ttf"))
                .addCustom2(Typekit.createFromAsset(this, "fonts/JejuGothic.ttf"))
                .addCustom3(Typekit.createFromAsset(this, "fonts/beantown.regular.ttf"));
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mActionBarToolbar != null) {
                // Depending on which version of Android you are on the Toolbar or the ActionBar may be
                // active so the a11y description is set here.
                mActionBarToolbar.setNavigationContentDescription(getResources()
                        .getString(R.string.navdrawer_description_a11y));
                //setSupportActionBar(mActionBarToolbar);

                if (useToolbar()) { setSupportActionBar(mActionBarToolbar);
                } else { mActionBarToolbar.setVisibility(View.GONE); }

            }
        }

        return mActionBarToolbar;
    }

    private void setupNavDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }

        // use the hamburger menu
        if( useDrawerToggle()) {
            mToggle = new ActionBarDrawerToggle(
                    this, mDrawerLayout, mActionBarToolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            mDrawerLayout.setDrawerListener(mToggle);
            mToggle.syncState();
        }
        else if(useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(ContextCompat
                    .getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        }

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        View view1 = findViewById(R.id.nav_imageView); // 프로필수정으로 넣은것

        switch (id) {
            case R.id.nav_home:
                /**
                 *  sending user,record info
                 */
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("UserObject", userObject);
                intent.putExtra("RecordObject", recordObject);
                createBackStack(intent);
                finish();
                break;

            case R.id.nav_route:
                createBackStack(new Intent(this, RouteActivity.class));
                finish();
                break;

            // 러닝화면테스트
            case R.id.nav_record:
                createBackStack(new Intent(this, RecordActivity.class));
                finish();
                break;

            case R.id.nav_rank:
                createBackStack(new Intent(this, RankingActivity.class));
                finish();
                break;

            case R.id.nav_info:
                createBackStack(new Intent(this, HangGangInfoMapActivity.class));
                finish();
                break;

            case R.id.nav_option:
                createBackStack(new Intent(this, SettingActivity.class));
                finish();
                break;

            case R.id.nav_logout:

                LoginManager.getInstance().logOut();    //페이스북 로그아웃
                LoginActivity.mOAuthLoginInstance.logout(LoginActivity.mContext); // 네이버 로그아웃
                createBackStack(new Intent(this, LoginActivity.class));
                finish();
                break;

            case R.id.nav_panorama:
                createBackStack(new Intent(this, PanoramaActivity.class));
                finish();
                break;

            //nav_rank
        }

        closeNavDrawer();
        //   overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);

        return true;
    }


    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    /**
     * Enables back navigation for activities that are launched from the NavBar. See
     * {@code AndroidManifest.xml} to find out the parent activity names for each activity.
     * @param intent
     */
    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }



}//end BaseActivity
