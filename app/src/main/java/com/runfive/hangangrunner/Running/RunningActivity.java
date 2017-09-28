package com.runfive.hangangrunner.Running;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.runfive.hangangrunner.Main.MainActivity;
import com.runfive.hangangrunner.R;

import java.net.MalformedURLException;


/**
 * Created by JunHo on 2016-08-06.
 */

public class RunningActivity extends AppCompatActivity {

    // 임의로 정한 권한 상수
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;

    // 시간 타이머
    private Chronometer chronometer;
    boolean display;
    long timeWhenStopped = 0;

    //버튼
    private Button running_pause_btn;
    private Button running_stop_btn;


    final Context context = this;


    //    기록
    private TextView distance_view;
    private TextView running_kcal;
    private TextView running_medal;
    private TextView running_point;


    // DB DATA
        private String userID;
        private RunningPHPRequest runningPHPRequest;
        private boolean isRecordRunning;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        userID = sharedPreferences.getString("id", null);

        setContentView(R.layout.activity_running);
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        display = pref.getBoolean("display", false);
        if (display == true) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

//        타이머 시작
        chronometer = (Chronometer) findViewById(R.id.running_timer);

//        타이버 양식 Format
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String hh = h < 10 ? "0" + h : h + "";
                String mm = m < 10 ? "0" + m : m + "";
                String ss = s < 10 ? "0" + s : s + "";
                cArg.setText(hh + ":" + mm + ":" + ss);
            }
        });
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();


//      타이머 끝

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {

        }

// Button 이벤트 리스너 추가
        running_pause_btn = (Button) findViewById(R.id.running_pause_btn);
        running_stop_btn = (Button) findViewById(R.id.running_stop_btn);

        running_pause_btn.setOnClickListener(mClickListener);
        running_stop_btn.setOnClickListener(mClickListener);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("RUN"));
        tabLayout.addTab(tabLayout.newTab().setText("MAP"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        final RunningAdapter adapter = new RunningAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
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

        distance_view = (TextView) findViewById(R.id.distance);
        running_kcal = (TextView) findViewById(R.id.running_kcal);
        running_medal = (TextView) findViewById(R.id.running_medal);
        running_point = (TextView) findViewById(R.id.running_point);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                return;
            }
        }
    }


    /**
     * PAUSE, STOP 버튼 이벤트핸들러
     */
    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.running_pause_btn:

                    if (("START").equals(running_pause_btn.getText())) {
                        //타이머 시작
                        chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                        chronometer.start();

                        running_pause_btn.setText("PAUSE");
                        running_pause_btn.setTextColor(Color.BLACK);

                    } else {
                        //타이머 정지
                        timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
                        chronometer.stop();

                        running_pause_btn.setText("START");
                        running_pause_btn.setTextColor(Color.BLUE);

                    }
                    break;
                case R.id.running_stop_btn:
                    stopRunningDialog();
                    break;

            }
        }
    };


    private void stopRunningDialog() {
//        종료할때 포인트 계산하기
        String medal = running_medal.getText().toString();
        int point = 0;
        double finalDistance = Double.parseDouble(distance_view.getText().toString());

        switch (medal) {
            case "G":
                point = (int) (finalDistance * 70);
                running_point.setText(point + "");
                break;
            case "S":
                point = (int) (finalDistance * 50);
                running_point.setText(point + "");
                break;
            case "B":
                point = (int) (finalDistance * 30);
                running_point.setText(point + "");
                break;

            default:
                point = (int) (finalDistance * 10);
                running_point.setText(point + "");
                break;
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("달리기를 종료하시겠습니까?");

        // set dialog message
        alertDialogBuilder
                .setMessage("총 달린거리: " + distance_view.getText() +
                        "\n\n달린 시간: " + chronometer.getText() +
                        "\n\n소모 칼로리: " + running_kcal.getText() +
                        "\n\n포인트: " + running_point.getText() +
                        "\n\n획득 메달: " + running_medal.getText())
                .setCancelable(false)
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();


                    }
                })
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            isRecordRunning = runningDataInput();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        RunningActivity.this.finish();


                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        stopRunningDialog();

    }


    /**
     * 달리기 기록 서버로 보내기
     *
     * @return
     * @throws MalformedURLException
     */
    private boolean runningDataInput() throws MalformedURLException {
        runningPHPRequest = new RunningPHPRequest("Your Server ULR");
        AsyncTask<String, Void, String> result = runningPHPRequest.execute(userID,
                running_point.getText().toString(),
                running_medal.getText().toString(),
                distance_view.getText().toString(),
                chronometer.getText().toString(),
                running_kcal.getText().toString());
        return true;
    }
}




