package com.runfive.hangangrunner.RouteCourse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.kml.KmlLayer;
import com.runfive.hangangrunner.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by JunHo on 2016-08-17.
 */

public class RouteCourseMapFragment extends Fragment {

    private GoogleMap map;
    private String routeName;
    private Bundle bundle;
    private LatLng curPoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.route_map_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        bundle = getArguments();
        routeName = bundle.getString("routeName");

        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.route_map)).getMap();

        try {
            KmlLayer kmlLayer = new KmlLayer(map, R.raw.themeroute, getActivity().getApplicationContext());
            kmlLayer.addLayerToMap();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (routeName) {
            case "반포 공원로":
                curPoint = new LatLng(37.50944, 126.99512);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
                break;

            case "광나루 공원로":
                curPoint = new LatLng(37.55046, 127.12163);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
                break;

            case "이촌 공원로":
                curPoint = new LatLng(37.51528, 126.98711);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
                break;

            case "잠실 공원로":
                curPoint = new LatLng(37.51857, 127.08717);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
                break;
            case "난지 공원로":
                curPoint = new LatLng(37.56856, 126.87358);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
                break;
            case "뚝섬 공원로":
                curPoint = new LatLng(37.52673, 127.08039);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
                break;
            case "양화 공원로":
                curPoint = new LatLng(37.54254, 126.89589);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
                break;
            case "여의도 공원로":
                curPoint = new LatLng( 37.5319, 126.92598);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
                break;
        }

        super.onActivityCreated(savedInstanceState);
    }



}
