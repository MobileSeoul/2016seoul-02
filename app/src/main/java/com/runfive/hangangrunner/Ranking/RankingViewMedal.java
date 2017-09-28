package com.runfive.hangangrunner.Ranking;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.runfive.hangangrunner.R;

/**
 * Created by JunHo on 2016-09-17.
 */
public class RankingViewMedal extends LinearLayout {

    private ImageView mIcon;

    private TextView mText01;

    private TextView mText02;

    private TextView mText03;

    public RankingViewMedal(Context context, RankingObject aItem) {
        super(context);

        // Layout Inflation
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.rankinglayout_medal_onecol, this, true); // 리스트뷰 한 조각을 인플레이션

        // Set Icon
        mIcon = (ImageView) findViewById(R.id.iconItem);
        //mIcon.setImageDrawable(aItem.getIcon());
        mIcon.setImageBitmap(aItem.getIcon());

        // Set Text 01
        mText01 = (TextView) findViewById(R.id.rankingtext_medal);
        mText01.setText(aItem.getData(0));

        // Set Text 02
        mText02 = (TextView) findViewById(R.id.user_id_medal);
        mText02.setText(aItem.getData(1));

        mText03 = (TextView) findViewById(R.id.medal_ranking);
        mText03.setText(aItem.getData(2));
    }

    public void setText(int index, String data) {
        if (index == 0) {
            mText01.setText(data);
        } else if (index == 1) {
            mText02.setText(data);
        } else if (index == 2) {
            mText03.setText(data);
        } else {
            throw new IllegalArgumentException();
        }
    }
    public void setIcon(Bitmap icon) {
        //mIcon.setImageDrawable(icon);
        mIcon.setImageBitmap(icon);
    }
}
