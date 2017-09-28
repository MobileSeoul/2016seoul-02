package com.runfive.hangangrunner.Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.runfive.hangangrunner.R;

/**
 * Created by JunHo on 2016-09-02.
 */
public class WeeklyObjectView extends LinearLayout {

    private ImageView mIcon;
    private TextView textViewDate;
    private TextView textViewDay;
    private TextView textViewKm;
    private TextView textViewPoint;
    private TextView textViewGold;
    private TextView textViewSilver;
    private TextView textViewBronze;

    public WeeklyObjectView(Context context, WeeklyObject weeklyObject) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.weeklylayout_onecol, this, true);

        textViewDate = (TextView) findViewById(R.id.weekly_date);
        textViewDay = (TextView) findViewById(R.id.weekly_day);
        textViewKm = (TextView) findViewById(R.id.main_weekly_km);
        textViewPoint = (TextView) findViewById(R.id.main_weelky_point);
        textViewGold = (TextView) findViewById(R.id.main_weekly_gold);
        textViewSilver = (TextView) findViewById(R.id.main_weekly_silver);
        textViewBronze = (TextView) findViewById(R.id.main_weekly_bronze);
    }

    public void setText(String date, String dayandmonth, String km, String point, String gold, String silver, String bronze) {
        textViewDate.setText(date);
        textViewDay.setText(dayandmonth);
        textViewKm.setText(km);
        textViewPoint.setText(point);
        textViewGold.setText(gold);
        textViewSilver.setText(silver);
        textViewBronze.setText(bronze);
    }


}
