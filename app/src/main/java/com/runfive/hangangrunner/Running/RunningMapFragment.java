package com.runfive.hangangrunner.Running;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.runfive.hangangrunner.Common.NetworkUtil;
import com.runfive.hangangrunner.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by JunHo on 2016-08-12.
 */
public class RunningMapFragment extends Fragment {

    private GoogleMap map;
    private String intentKey = "testProximity"; // 근접 경보 테스트 키
    private LocationManager manager;
    private ArrayList mPendingIntentList;
    public static int userRouteCnt = 0; // 사용자루트기록 개수
    private IntentReceiver intentReceiver;

    //
    private TextView distance_view;     // 총 달린거리보여주는 TextView
    private TextView distance_hidden;     // 미터기준으로 계산을 위한 invisible TextView
    private Button running_pause_btn;     // 일시정지버튼
    private TextView running_kcal;      // 소모 칼로리

    private PolylineOptions polylineOptions;
    private ArrayList<LatLng> arrayPoints;
    DecimalFormat kcalFormat = new DecimalFormat("#0.00");
    DecimalFormat kmFormat = new DecimalFormat("#0.000");



    private ProgressDialog dialog ;
    ;

    /**
     *  루트 테스트 변수들
     *  그리고 루트 진입시 안내 멘트는 한번만 재생되어야 하기 때문에
     *  boolean 변수를 다 주고 시작점 , 끝점 boolean변수도 준다
     */
    private boolean ifRoutestart = false;
    private MediaPlayer mediaPlayer;

    private ThemeRouteCheck themeRouteCheck;
    private String userID;
    private static final int YEOUIDO_START = 1005;
    private static final int YEOUIDO_END = 1006;
    private static final int NANGI_START = 1007;
    private static final int NANGI_END = 1008;
    private static final int YANGHWA_START = 1009;
    private static final int YANGHWA_END = 1010;
    private static final int GWANGNARU_START = 1011;
    private static final int GWANGNARU_END = 1012;
    private static final int LEECHON_START = 1013;
    private static final int LEECHON_END = 1014;
    private static final int TTUKSUM_START = 1015;
    private static final int TTUKSUM_END = 1016;
    private static final int JAMSIL_START = 1017;
    private static final int JAMSIL_END = 1018;
    private static final int BANPO_START = 1019;
    private static final int BANPO_END = 1020;

    private final GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {

        public void onGpsStatusChanged(int event) {

            switch (event) {
                case GpsStatus.GPS_EVENT_STARTED:
                    Toast.makeText(getActivity().getApplicationContext(), "GPS연결을 시도합니다.", Toast.LENGTH_SHORT).show();
                    dialog.setMessage("GPS 연결중...");
                    dialog.show();

                    // gps 연결 시도를 시작하면 발생하는 이벤트
                    // mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this); 를 호출하면 발생한다.
                    break;
                case GpsStatus.GPS_EVENT_STOPPED:
                    // gps 연결이 끝났을때 발생하는 이벤트
                    // mLocationManager.removeUpdates(ms_Instance); 를 호출하면 발생한다.
                    break;
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    // gps 연결이 되면 발생하는 이벤트
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                        dialog.cancel();
                    }

                    Toast.makeText(getActivity().getApplicationContext(), "GPS연결이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                    break;
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    // gps 와 연결이 되어 있는 위성의 상태를 넘겨받는 이벤트
                    // gps 수신상태를 체크할 수 있다.
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        distance_view = (TextView) getActivity().findViewById(R.id.distance);
        distance_hidden = (TextView) getActivity().findViewById(R.id.distance_hidden);
        running_kcal = (TextView) getActivity().findViewById(R.id.running_kcal);
        running_pause_btn = (Button) getActivity().findViewById(R.id.running_pause_btn);
        return inflater.inflate(R.layout.running_map_fragment, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NetworkUtil.setNetworkPolicy();
        SupportMapFragment fm = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.running_map);
        map = fm.getMap();
        startLocationService();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        dialog = new ProgressDialog(getActivity());
        manager.addGpsStatusListener(gpsStatusListener);
// gps 의 상태를 처리하는 콜백함수

        mPendingIntentList = new ArrayList();
        intentReceiver = new IntentReceiver(intentKey);
        getActivity().registerReceiver(intentReceiver, intentReceiver.getFilter()); // 인텐트 리시버 등록

        MapsInitializer.initialize(getActivity().getApplicationContext());
        this.init();
        ///////////////////////////////////////////////////////////////////

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("id", null);

        /**
         *  근접경보 테스트
         */
        Context context = getActivity().getApplicationContext();
        themeRouteCheck = new ThemeRouteCheck(context, userID); // 진동때문에 일반클래스에 넘긴 것

        //register(1001, 37.222769, 127.187626, 5f, -1); // 5공 앞 삼거리
        register(1001, 37.221862, 127.186880, 5f, -1); // 5공 뒷길 아래
        register(1002, 37.222344, 127.186519, 5f ,-1); // 5공 뒷길 위
        register(1003, 37.223271, 127.187992, 5f, -1);
        // 근접경보테스트끝 ////////////////////////////////////////////////////////////////


        register(YEOUIDO_START, 37.53502, 126.91326, 10f, -1);
        register(YEOUIDO_END, 37.51534, 126.9531, 10f, -1);
        register(NANGI_START, 37.57126, 126.87046, 10f, -1);
        register(NANGI_END, 37.55907, 126.89205, 10f, -1);
        register(YANGHWA_START, 37.54626, 126.89093, 10f, -1);
        register(YANGHWA_END, 37.53336, 126.90988, 10f, -1);
        register(GWANGNARU_START, 37.55505, 127.12383, 10f, -1);
        register(GWANGNARU_END, 37.54147, 127.11588, 10f, -1);
        register(LEECHON_START, 37.51745, 126.99182, 10f, -1);
        register(LEECHON_END, 37.51977, 126.96298, 10f, -1);
        register(TTUKSUM_START, 37.528, 127.08837, 10f, -1);
        register(TTUKSUM_END, 37.53214, 127.05967, 10f, -1);
        register(JAMSIL_START, 37.52313, 127.10064, 10f, -1);
        register(JAMSIL_END, 37.52006, 127.06913, 10f, -1);
        register(BANPO_START, 37.5223, 127.01213, 10f, -1);
        register(BANPO_END, 37.50633, 126.98818, 10f, -1);

    }

    private void init() {
        String coordinates[] = {"37.566535 ", "126.977969"};        // 좌표 초기값 서울시청

        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);

        LatLng position = new LatLng(lat, lng);
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());

        // 맵 위치이동.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));

        arrayPoints = new ArrayList<LatLng>();


    }
    @Override
    public void onResume() {
        super.onResume();

        try {
            map.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            map.setMyLocationEnabled(false);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void startLocationService() {
        // 위치 관리자 객체 참조
        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // 리스너 객체 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 1000;
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

    private class GPSListener implements LocationListener {
        private Location previousLocation = null;
        private double totalDistance = 0D;
        private double totalDistance_hidden = 0D;
        private double distance = 0D;
        private String i_distance = " ";
        private int i_distance_hidden = 0;
        private double kcal=0.0;

        public void onLocationChanged(Location location) {

            if(location != null) {
                if((location.getAccuracy() > 15 && getActivity()!=null)) {
                        Toast.makeText(getActivity(), "GPS수신이 약합니다 거리측정이 어렵습니다.", Toast.LENGTH_SHORT).show();
                }

//        소모 칼로리 계산: 1km 달리기당 84kcal 소모
                kcal = totalDistance_hidden*0.084;
                running_kcal.setText(""+kcalFormat.format(kcal));
                if ((previousLocation != null && location.getAccuracy() < 15)
                        && (running_pause_btn.getText().equals("PAUSE"))) {


                    //////////////////////이동경로 그리기
                    polylineOptions = new PolylineOptions();
                    polylineOptions.color(Color.RED);
                    polylineOptions.width(7);
                    arrayPoints.add(new LatLng(location.getLatitude(), location.getLongitude()));
                    polylineOptions.addAll(arrayPoints);
                    map.addPolyline(polylineOptions);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));

// 현재 위치 거리 및 속도 구하기.

                    //////////////////////////////////////////
                    // 실시간 이동경로 그리기
                    if (totalDistance < 10.0) {
                        MarkerOptions marker = new MarkerOptions();
                        marker.position(new LatLng(location.getLatitude(), location.getLongitude()));
                    }
                    map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                    ///////////////////////////////////////
                    distance = location.distanceTo(previousLocation);
                    if (distance < 1.0) {
                        distance = 0D;
                    }
                    totalDistance += distance/1000;
                    totalDistance_hidden += distance;
                    i_distance = ""+kmFormat.format(totalDistance);
                    i_distance_hidden = (int)totalDistance_hidden;
//                Toast.makeText(getActivity(), "" + i_distance, Toast.LENGTH_SHORT).show();
                    distance_view.setText("" + i_distance);
                    distance_hidden.setText("" + i_distance_hidden);
                }

                // Update stored location
                this.previousLocation = location;
            } else {

            }
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }





    // 근접 경보 테스트

    private void register(int id, double latitude, double longitude, float radius, long expiration) {
        Intent proximityIntent = new Intent(intentKey);

        proximityIntent.putExtra("id", id);
        proximityIntent.putExtra("latitude", latitude);
        proximityIntent.putExtra("longitude", longitude);

        PendingIntent intent = PendingIntent.getBroadcast(getActivity(), id, proximityIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        /*
        // 권한체크때문
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        */
        try {
            manager.addProximityAlert(latitude, longitude, radius, expiration, intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        mPendingIntentList.add(intent);
    }

    public void onStop() {
        super.onStop();

        try {
            unregister();
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
        } finally {
        }

    }

    private void unregister() {
        if (mPendingIntentList != null) {
            for (int i = 0; i < mPendingIntentList.size(); i++) {
                PendingIntent curIntent = (PendingIntent) mPendingIntentList.get(i);


                /*
                if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                */

                try {
                    manager.removeProximityAlert(curIntent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

                mPendingIntentList.remove(i);
            }
        }

        if (intentReceiver != null) {
            try {
                getActivity().unregisterReceiver(intentReceiver);
            } catch (IllegalArgumentException e) {
            } catch (Exception e) {
            } finally {
            }
            intentReceiver = null;
        }
    }

    public class IntentReceiver extends BroadcastReceiver {

        private String mExpectedAction;
        private Intent mLastReceivedIntent;

        public IntentReceiver() {

        }

        public IntentReceiver(String expectedAction) {
            mExpectedAction = expectedAction;
            mLastReceivedIntent = null;

            System.out.println("인텐트 리시버 생성자");
        }

        public IntentFilter getFilter() {
            IntentFilter filter = new IntentFilter(mExpectedAction);
            return filter;
        }



        public void onReceive(Context context, Intent intent) {

            /**
            mediaPlayer = new MediaPlayer(); // idle 상태
            mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.hakgwan); // initialize
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();
            }
            });

             mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override public void onCompletion(MediaPlayer mp) {
            mediaPlayer.release();
            }
            });
*/


            Vibrator vibrator;
            vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

            if (intent != null) {
                mLastReceivedIntent = intent;

                int id = intent.getIntExtra("id", 0);
                double latitude = intent.getDoubleExtra("latitude", 0.0D);
                double longitude = intent.getDoubleExtra("longitude", 0.0D);

                themeRouteCheck.RouteCheck(id);
            }
        }


        public Intent getLastReceivedIntent() {
            return mLastReceivedIntent;
        }

        public void clearReceivedIntents() {
            mLastReceivedIntent = null;
        }
    }
}
