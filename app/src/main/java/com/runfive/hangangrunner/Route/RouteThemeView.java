package com.runfive.hangangrunner.Route;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.runfive.hangangrunner.R;

/**
 * Created by jinwo on 2016-08-03.
 */
public class RouteThemeView extends LinearLayout
{

    private ImageView mIcon;
    public RouteThemeView(Context context, RouteObject aItem) {
        super(context);

        // Layout Inflation
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.routelayout_onlyicon, this, true); // 리스트뷰 한 조각을 인플레이션

        // Set Icon
        mIcon = (ImageView) findViewById(R.id.iconItem2);
        mIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mIcon.setImageDrawable(aItem.getIcon());
    }

    public void setIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
    }
}
