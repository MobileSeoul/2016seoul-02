package com.runfive.hangangrunner.Running;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.runfive.decoviewlib.DecoView;
import com.runfive.decoviewlib.charts.DecoDrawEffect;
import com.runfive.decoviewlib.charts.SeriesItem;
import com.runfive.decoviewlib.events.DecoEvent;
import com.runfive.hangangrunner.R;

/**
 * Created by Yongjun on 2016-08-03.
 */

public class RunningFragment extends SampleFragment {
    final private float[] mTrackBackWidth = {30f, 60f, 30f, 40f, 30f};
    final private float[] mTrackWidth = {30f, 30f, 30f, 30f, 30f};
    final private boolean[] mClockwise = {true, true, true, false, true};
    final private boolean[] mRounded = {true, true, true, true, true};
    final private boolean[] mPie = {false, false, false, false, true};
    final private int[] mTotalAngle = {360, 360, 320, 260, 360};
    final private int[] mRotateAngle = {0, 180, 180, 0, 270};
    private int mBackIndex;
    private int mSeries1Index;
    private int mSeries2Index;
    private int mStyleIndex;
    private TextView runningDistance;
    private float goalDistance = 0f;
    private TextView running_medal;     // 획득메달 TextView

    private TextWatcher watcher;
    private int chartDelay = 4000;

    public RunningFragment() {
    }

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.running_gauge, container, false);
        runningDistance = (TextView) getActivity().findViewById(R.id.distance_hidden);
        running_medal = (TextView) getActivity().findViewById(R.id.running_medal);


        return view;
    }

    @Override
    protected void createTracks() {
//        setDemoFinished(false);
        final DecoView decoView = getDecoView();
        final View view = getView();
        if (decoView == null || view == null) {
            return;
        }
        decoView.deleteAll();
        decoView.configureAngles(mTotalAngle[mStyleIndex], mRotateAngle[mStyleIndex]);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final float seriesMax = getActivity().getIntent().getExtras().getFloat("distance");
        goalDistance = seriesMax;
        SeriesItem arcBackTrack = new SeriesItem.Builder(Color.argb(255, 228, 228, 228))
                .setRange(0, seriesMax, seriesMax)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(mTrackBackWidth[mStyleIndex]))
                .setChartStyle(mPie[mStyleIndex] ? SeriesItem.ChartStyle.STYLE_PIE : SeriesItem.ChartStyle.STYLE_DONUT)
                .build();

        mBackIndex = decoView.addSeries(arcBackTrack);

        float inset = 0;
        if (mTrackBackWidth[mStyleIndex] != mTrackWidth[mStyleIndex]) {
            inset = getDimension((mTrackBackWidth[mStyleIndex] - mTrackWidth[mStyleIndex]) / 2);
        }
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 255, 165, 0))
                .setRange(0, seriesMax, 0)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(mTrackWidth[mStyleIndex]))
                .setInset(new PointF(-inset, -inset))
                .setSpinClockwise(mClockwise[mStyleIndex])
                .setCapRounded(mRounded[mStyleIndex])
                .setChartStyle(mPie[mStyleIndex] ? SeriesItem.ChartStyle.STYLE_PIE : SeriesItem.ChartStyle.STYLE_DONUT)
                .build();

        mSeries1Index = decoView.addSeries(seriesItem1);

        SeriesItem seriesItem2 = new SeriesItem.Builder(Color.argb(255, 255, 51, 51))
                .setRange(0, seriesMax, 0)
                .setInitialVisibility(false)
                .setCapRounded(true)
                .setLineWidth(getDimension(mTrackWidth[mStyleIndex]))
                .setInset(new PointF(inset, inset))
                .setCapRounded(mRounded[mStyleIndex])
                .build();

        mSeries2Index = decoView.addSeries(seriesItem2);

        final TextView textPercent = (TextView) view.findViewById(R.id.textPercentage);
        if (textPercent != null) {
            textPercent.setText(" ");
            addProgressListener(seriesItem1, textPercent, "%.0f%%");
        }

        final TextView textToGo = (TextView) view.findViewById(R.id.textRemaining);
        textToGo.setText(" ");
        addProgressRemainingListener(seriesItem1, textToGo, "        %.00fM\ndistance to goal", seriesMax);
    }

    @Override
    protected void setupEvents() {

        final DecoView decoView = getDecoView();
        final View view = getView();
        if (decoView == null || decoView.isEmpty() || view == null) {
            return;
        }

        mUpdateListeners = true;
        final TextView textPercent = (TextView) view.findViewById(R.id.textPercentage);
        final TextView textToGo = (TextView) view.findViewById(R.id.textRemaining);
        int fadeDuration = 2000;


        if (mPie[mStyleIndex]) {
            decoView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                    .setIndex(mBackIndex)
                    .setDuration(2000)
                    .build());
        } else {
            decoView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                    .setIndex(mBackIndex)
                    .setDuration(3000)
                    .build());

            decoView.addEvent(new DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
                    .setIndex(mSeries1Index)
                    .setFadeDuration(fadeDuration)
                    .setDuration(2000)
                    .setDelay(1000)
                    .build());
        }


        decoView.addEvent(new DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
                .setIndex(mSeries1Index)
                .setDuration(2000)
                .setDelay(1100)
                .build());

        decoView.addEvent(new DecoEvent.Builder(0).setIndex(mSeries1Index).setDelay(3900).build());
        runningDistance.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {


            }


            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int i_running_distance = Integer.parseInt(runningDistance.getText().toString());
                if(i_running_distance < goalDistance)
                {
                    decoView.addEvent(new DecoEvent.Builder(i_running_distance).setIndex(mSeries1Index)/*.setDelay(chartDelay)*/.build());
                    chartDelay += 1000;

                    // 메달따기
                    acquireMedal(Integer.parseInt(runningDistance.getText().toString()));
                }else if(i_running_distance >= goalDistance){
                    decoView.addEvent(new DecoEvent.Builder(goalDistance).setIndex(mSeries1Index)/*.setDelay(chartDelay)*/.build());
                    chartDelay += 1000;
                    acquireMedal(Integer.parseInt(runningDistance.getText().toString()));

                }


            }
        });
    }

    private void acquireMedal(int currentDistance) {

        String acquiredMedal = running_medal.getText().toString();
        if (currentDistance >= goalDistance && acquiredMedal.equals("B")) {
            running_medal.setText("S");
            Toast.makeText(getActivity().getApplicationContext(), "은메달 획득, 축하합니다\n목표를 달성했습니다!!.", Toast.LENGTH_SHORT).show();

       /* } else if (currentDistance > (goalDistance * 0.75) && acquiredMedal.equals("B")) {
            running_medal.setText("S");
            Toast.makeText(getActivity(), "은메달 획득, 금메달에 도전하세요.", Toast.LENGTH_SHORT).show();*/

        } else if (currentDistance > (goalDistance / 2) && acquiredMedal.equals("N")) {
            running_medal.setText("B");
            Toast.makeText(getActivity().getApplicationContext(), "동메달 획득, 은메달에 도전하세요.", Toast.LENGTH_SHORT).show();

        }
    }
}
