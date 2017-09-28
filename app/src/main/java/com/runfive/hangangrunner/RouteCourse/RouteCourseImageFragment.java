package com.runfive.hangangrunner.RouteCourse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.runfive.hangangrunner.R;

/**
 * Created by jinwo on 2016-08-17.
 */

public class RouteCourseImageFragment extends Fragment {

    private String routeName;
    private Bundle bundle;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.route_course_image, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        bundle = getArguments();
        routeName = bundle.getString("routeName");
        imageView = (ImageView)getActivity().findViewById(R.id.route_image);

        switch (routeName)
        {
            case "반포 공원로":
                imageView.setImageResource(R.drawable.img_banpo);
                break;

            case "광나루 공원로":
                imageView.setImageResource(R.drawable.img_gwangnaru);
                break;

            case "이촌 공원로":
                imageView.setImageResource(R.drawable.img_ichon);
                break;

            case "잠실 공원로":
                imageView.setImageResource(R.drawable.img_jamsil);
                break;

            case "난지 공원로":
                imageView.setImageResource(R.drawable.img_nanji);
                break;

            case "뚝섬 공원로":
                imageView.setImageResource(R.drawable.img_ttuksum);
                break;

            case "양화 공원로":
                imageView.setImageResource(R.drawable.img_yanghwa);
                break;

            case "여의도 공원로":
                imageView.setImageResource(R.drawable.img_yeodo);
                break;
        }
        super.onActivityCreated(savedInstanceState);
    }
}
