package com.runfive.hangangrunner.Route;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.runfive.hangangrunner.R;
import com.runfive.hangangrunner.RouteCourse.RouteCourseActivity;

import java.util.ArrayList;

public class RouteRecommand extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_one";
    RouteActivity routeActivity = new RouteActivity();

    ListView listView1;
    RouteAdapter adapter;
    private LocationManager manager;
    private ArrayList<LatLng> arrayPoints;
    double Lat;
    double Long;
    double tmp;
    RouteObject routeObject;


    public RouteRecommand(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.route_fragment_page2, container, false);
        // Paint paint = new Paint();
        // paint.setColor("@drawable/");
        // paint.setAlpha(70);
        //((LinearLayout)view.findViewById(R.id.ranking_daily_layout)).setBackgroundColor(paint.getColor());


        listView1 = (ListView) rootView.findViewById (R.id.listView2);

        // 어댑터 객체 생성
        //adapter = new RouteAdapter(this);
        adapter = new RouteAdapter(getActivity().getBaseContext());
        // 액티비티는 컨텍스트참조되는데 fragment여서 액티비티를 가져온후 컨택스트참조를한다

        // 아이템 데이터 만들기
        Resources res = getResources();

        RouteObject[] routeObjects = new RouteObject[8];

        routeObjects[0] = new RouteObject(res.getDrawable(R.drawable.route_banpo_image),"반포 공원로", "반포 공원", "2.98","37.51343","127.00239");
        routeObjects[1] = new RouteObject(res.getDrawable(R.drawable.route_gangnaru_image),"광나루 공원로", "광나루공원", "1.82","37.54869","127.12099");
        routeObjects[2] = new RouteObject(res.getDrawable(R.drawable.route_echon_image),"이촌 공원로", "이촌 공원", "2.78","37.51516","126.97673");
        routeObjects[3] = new RouteObject(res.getDrawable(R.drawable.route_gamsil_image),"잠실 공원로", "잠실 공원", "2.97","37.51853","127.08674");
        routeObjects[4] = new RouteObject(res.getDrawable(R.drawable.route_nanzi_image),"난지 공원로", "난지 공원", "2.58","37.5646","126.88064");
        routeObjects[5] = new RouteObject(res.getDrawable(R.drawable.route_ddoksum_image),"뚝섬 공원로", "뚝섬 공원", "2.74","37.52732","127.07517");
        routeObjects[6] = new RouteObject(res.getDrawable(R.drawable.route_yangha_image),"양화 공원로", "양화 공원", "2.27","37.5401","126.90045");
        routeObjects[7] = new RouteObject(res.getDrawable(R.drawable.route_yueeido_image),"여의도 공원로", "여의도공원", "4.77", "37.52696","126.93406");

        double[] Sum = new double[8];
        for(int i = 0; i<8; i++)
        {
            Sum[i] = Math.sqrt((Lat - Double.parseDouble(routeObjects[i].getData(3)))*(Lat - Double.parseDouble(routeObjects[i].getData(3))) + (Long - Double.parseDouble(routeObjects[i].getData(4)))*(Long - Double.parseDouble(routeObjects[i].getData(4))));
        }

        for(int i = 0; i<8; i++)
        {
            for(int j =0; j<7; j++)
            {
                if(Sum[j]<Sum[j+1])
                {
                    tmp = Sum[j];
                    Sum[j] = Sum[j+1];
                    Sum[j+1] = tmp;
                    routeObject = routeObjects[j];
                    routeObjects[j] = routeObjects[j+1];
                    routeObjects[j+1] = routeObject;
                }
            }
        }
        adapter.addItem(routeObjects[0]);
        adapter.addItem(routeObjects[1]);
        adapter.addItem(routeObjects[2]);
        adapter.addItem(routeObjects[3]);
        adapter.addItem(routeObjects[4]);
        adapter.addItem(routeObjects[5]);
        adapter.addItem(routeObjects[6]);
        adapter.addItem(routeObjects[7]);



        // adapter.sort(new Comparator<String>() {
        //    @Override
        //    public int compare(String arg1, String arg0) {
        //        return -arg1.compareTo(arg0);
        //   }
        // });
        // 리스트뷰에 어댑터 설정
        listView1.setAdapter(adapter);

        // 새로 정의한 리스너로 객체를 만들어 설정 (그냥 확인차 보여주는것 토스트메세지로)
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RouteObject curItem = (RouteObject) adapter.getItem(position);

                String[] course= curItem.getData();

                Toast.makeText(getActivity().getApplicationContext(), ""  +course[1] , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),RouteCourseActivity.class);
                intent.putExtra("routename", course[0]);
                intent.putExtra("parkname", course[1]);
                intent.putExtra("distance", course[2]);
                createBackStack(intent);
            }

        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startLocationService();
    }


    private void init() {
        String coordinates[] = {"37.517180", "127.041268"};        // 좌표 초기값

        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);

        LatLng position = new LatLng(lat, lng);
        arrayPoints = new ArrayList<LatLng>();
    }

    private void startLocationService(){
        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        MyListener gpsListener = new MyListener();
        long minTime = 100000;
        float minDistance = 0;

        try {
            // GPS 기반 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }
    private class MyListener implements LocationListener{

        public void onLocationChanged(Location location){
            Lat = location.getLatitude();
            Long = location.getLongitude();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }


    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(getActivity());
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            routeActivity.finish();
        }
    }


}
