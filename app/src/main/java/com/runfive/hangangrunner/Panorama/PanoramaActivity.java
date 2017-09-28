package com.runfive.hangangrunner.Panorama;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.runfive.hangangrunner.R;

/**
 * Created by JunHo on 2016-10-15.
 */

public class PanoramaActivity extends AppCompatActivity implements OnStreetViewPanoramaReadyCallback,
        StreetViewPanorama.OnStreetViewPanoramaChangeListener {

    private StreetViewPanorama sp;
    private TextView textView;
    private AlertDialog.Builder dialog;
    private static final LatLng HANGANG_BRIDGE = new LatLng(37.5157531,126.9573837);
    private static final LatLng YANGHWA_BRIDGE = new LatLng(37.539718,126.9010997);
    private static final LatLng SEOGANG_BRIDGE = new LatLng(37.533572,126.9229994);
    private static final LatLng MAPO_BRIDGE = new LatLng(37.5310783,126.9328384);
    private static final LatLng ONEHYO_BRIDGE = new LatLng(37.523761,126.9398892);
    private static final LatLng BANPO_BRIDGE = new LatLng(37.5187723,126.9942813);
    private static final LatLng SEONGSAN_BRIDGE = new LatLng(37.5559007,126.8934179);
    private static final LatLng JAMSIL_BRIDGE = new LatLng(37.5205286,127.0942127);
    private static final LatLng JAMSILSUBWAY_BRIDGE = new LatLng(37.5255899,127.1007349); // 잠실37.5262761,127.090309
    private static final LatLng OLYMPIC_BRIDGE = new LatLng(37.5341221,127.1033858);
    private static final LatLng DONGJAK_BRIDGE = new LatLng(37.5109653,126.9818519);
    private static final LatLng DONGHO_BRIDGE = new LatLng(37.5336889,127.0227059);
    private static final LatLng SEONGSU_BRIDGE = new LatLng(37.538756, 127.035290);
    private static final LatLng CHUNGDAM_BRIDGE = new LatLng(37.524877, 127.063808);
    //private static final LatLng TEST = new LatLng(37.524877, 127.063808);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama);

        Toolbar toolbar = (Toolbar) findViewById(R.id.panorama_toolbar);
        setSupportActionBar(toolbar);

        dialog = new AlertDialog.Builder(PanoramaActivity.this);
        dialog.setTitle("한강대교");
        dialog.setMessage("길이 : 1,005m \n완공연도 : 1937년/1981년 \n특징 : 서울 용산구 이촌동과 동작구 본동을 연결하는 교량. 교량 남단과 북단의 모습이 다른 형태로 되어있다");
        dialog.setPositiveButton("ok",null);

        textView = (TextView) findViewById(R.id.panorama_textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
    }


    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        sp = streetViewPanorama;
        sp.setOnStreetViewPanoramaChangeListener(PanoramaActivity.this);
        System.out.println(sp.getLocation()+"좌표");

        sp.setPosition(HANGANG_BRIDGE);
    }

    @Override
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.panorama_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return true;
    }

    public void setDiaglog(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.hangang_bridge:
                sp.setPosition(HANGANG_BRIDGE);
                textView.setText("한강대교");
                return true;

            case R.id.yanghwa_bridge:
                sp.setPosition(YANGHWA_BRIDGE);
                textView.setText("양화대교");
                setDiaglog("양화대교","길이 : 1053m \n완공연도 : 1982년 \n특징 : 서울 마포구 합정동과 영등포구 양평동을 연결하는 교량");
                return true;

            case R.id.seogang_bridge:
                sp.setPosition(SEOGANG_BRIDGE);
                textView.setText("서강대교");
                setDiaglog("서강대교","길이 : 1320m \n완공연도 : 1999년 \n특징 : 서울특별시 영등포구 여의도동과 마포구 신정동을 잇는 다리");
                return true;

            case R.id.mapo_bridge:
                sp.setPosition(MAPO_BRIDGE);
                textView.setText("마포대교");
                setDiaglog("마포대교","길이 : 1053m \n완공연도 : 1982년 \n특징 : 서울 마포구 합정동과 영등포구 양평동을 연결하는 교량");
                return true;

            case R.id.onehyo_bridge:
                sp.setPosition(ONEHYO_BRIDGE);
                textView.setText("원효대교");
                setDiaglog("원효대교","길이 : 1470m \n완공연도 : 1981년 \n특징 : 서울특별시 용산구 원효로4가와 영등포구 여의도동(汝矣島洞) 사이를 잇는 교량");
                return true;

            case R.id.banpo_bridge:
                sp.setPosition(BANPO_BRIDGE);
                textView.setText("반포대교");
                setDiaglog("반포대교","길이 : 1490m \n완공연도 : 1982년 \n특징 : 서울 용산구 서빙고동과 서초구 반포동 사이를 잇는 교량");
                return true;

            case R.id.seongsan_bridge:
                sp.setPosition(SEONGSAN_BRIDGE);
                textView.setText("성산대교");
                setDiaglog("성산대교","길이 : 1410m \n완공연도 : 1980년 \n특징 : 서울 마포구 망원동(望遠洞)과 영등포구 양평동(楊坪洞)을 잇는 다리");
                return true;

            case R.id.jamsil_bridge:
                sp.setPosition(JAMSIL_BRIDGE);
                textView.setText("잠실대교");
                setDiaglog("잠실대교","길이 : 1280m \n완공연도 : 1972년 \n특징 : 서울특별시 광진구 자양동(紫陽洞)과 송파구 신천동(新川洞)을 잇는 한강의 다리");
                return true;

            case R.id.jamsilsubway_bridge:
                sp.setPosition(JAMSILSUBWAY_BRIDGE);
                textView.setText("잠실철교");
                setDiaglog("잠실철교","길이 : 1270m \n완공연도 : 1979년 \n특징 :서울특별시 광진구 구의동과 송파구 신천동을 잇는 한강 위의 복선철교");
                return true;

            case R.id.olympic_bridge:
                sp.setPosition(OLYMPIC_BRIDGE);
                textView.setText("올림픽대교");
                setDiaglog("올림픽대교","길이 : 1225m \n완공연도 : 1990년 \n특징 : 서울 광진구 구의동(九宜洞)과 송파구 풍납동(風納洞)을 연결하는 다리");
                return true;

            case R.id.dongjak_bridge:
                sp.setPosition(DONGJAK_BRIDGE);
                textView.setText("동작대교");
                setDiaglog("동작대교","길이 : 1330m \n완공연도 : 1984년 \n특징 : 서울 용산구 서빙고동, 이촌동과 동작구 동작동을 잇는 다리");
                return true;

            case R.id.dongho_bridge:
                sp.setPosition(DONGHO_BRIDGE);
                textView.setText("동호대교");
                setDiaglog("동호대교","길이 : 1095m \n완공연도 : 1985년 \n특징 : 서울 성동구 옥수동과 강남구 압구정동을 잇는 다리");
                return true;

            case R.id.seongsu_bridge:
                sp.setPosition(SEONGSU_BRIDGE);
                textView.setText("성수대교");
                setDiaglog("성수대교","길이 : 1161m \n완공연도 : 1979년 \n특징 : 서울시 성동구 성수동(聖水洞)과 강남구 압구정동(狎鷗亭洞)을 연결하는 다리");
                return true;

            case R.id.chungdam_bridge:
                sp.setPosition(CHUNGDAM_BRIDGE);
                textView.setText("청담대교");
                setDiaglog("청담대교","길이 : 1211m \n완공연도 : 1999년 \n특징 : 서울특별시 광진구 자양동(紫陽洞)과 강남구 청담동(淸潭洞) 사이를 연결하는 교량");
                return true;

        }
        return false;
    }

}
