package com.runfive.hangangrunner.HanGangInfo;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.runfive.hangangrunner.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by JunHo on 2016-08-13.
 */

public class HangGangInfoMapActivity extends AppCompatActivity {

    private GoogleMap map;
    private Document doc;
    private LocationManager manager;
    private GPSListener gpsListener;

    HanGangDrinkInfoObject[] hanGangDrinkInfoObjects = null; // 식수대
    HanGangLawnInfoObject[] hanGangLawnInfoObjects = null; // 잔디밭
    HanGangFootVolleyBallInfoObject[] hanGangFootVolleyBallInfoObjects = null; // 족구장
    HanGangBikeRentalInfoObject[] hanGangBikeRentalInfoObjects = null; // 자전거 대여소
    HanGangTennisInfoObject[] hanGangTennisInfoObjects = null; // 테니스장
    HanGangStoreInfoObject[] hanGangStoreInfoObjects = null; // 매점
    HanGangParkingInfoObject[] hanGangParkingInfoObjects = null; // 주차장
    HanGangSoccerInfoObject[] hanGangSoccerInfoObjects = null;// 축구장
    HanGangBadmintonInfoObject[] hanGangBadmintonInfoObjects = null; // 배드민턴장
    HanGangPlaygroundObjectInfo[] hanGangPlaygroundObjectInfos = null; // 어린이 놀이터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hanganginfo_map);
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.hanganginfo_map)).getMap();

        Toolbar toolbar = (Toolbar) findViewById(R.id.hangang_toolbar);
        setSupportActionBar(toolbar);

        LatLng startingPoint = new LatLng(37.5879075, 126.8151565);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(startingPoint, 16));

        // 스니펫 더 큰게 필요
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View myContentView = getLayoutInflater().inflate(
                        R.layout.custommarker, null);
                TextView tvTitle = ((TextView) myContentView
                        .findViewById(R.id.title));
                tvTitle.setText(marker.getTitle());
                TextView tvSnippet = ((TextView) myContentView
                        .findViewById(R.id.snippet));
                tvSnippet.setText(marker.getSnippet());
                return myContentView;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            map.setMyLocationEnabled(false);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            map.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //this.menu=menu;
        getMenuInflater().inflate(R.menu.hanganginfo_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.action_my_location:
                // 내 위치
                startLocationService();
                return true;



            case R.id.lawn_position:
                try {
                    showLawnPosition();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.water_position:
                try {
                    showDrinkPosition();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.footvolleyball_position:

                try {
                    showFootVolleyBallPosition();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.bike_rent_position:

                try {
                    showBikeRentPosition();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.tennis_position:
                try {
                    showTennisPosition();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.parking_position:
                try {
                    showParkingPosition();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.px_position:
                try {
                    showStorePosition();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.soccer_position:
                try {
                    showSoccerPosition();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.badminton_position:
                try {
                    showBadmintonPosition();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;


        }

        return false;
    }

/*
    private void showRouteTest() {
        PolylineOptions options = new PolylineOptions().color(Color.RED).width(3);
        options.add(new LatLng(37.530520, 126.927714));
        options.add(new LatLng(37.531570, 126.925777));
        Polyline polyline = map.addPolyline(options);

        LatLng curPoint = new LatLng(37.531570, 126.925777);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

    }
*/


    // 현재 위치
    private void startLocationService() {
        // 위치 관리자 객체 참조
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 리스너 객체 생성
        gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;

        try {
            // GPS 기반 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            // 네트워크 기반 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);
        } catch(SecurityException ex) {
            ex.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "위치 확인 시작", Toast.LENGTH_SHORT).show();
    }

    private class GPSListener implements LocationListener {
        // 위치 정보가 확인 되었을때
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
            Log.i("GPSLocationService", msg);

            // 현재 위치의 지도를 보여주기 위해 정의한 메소드 호출
            showCurrentLocation(latitude, longitude);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

    private void showCurrentLocation(Double latitude, Double longitude) {
        // 현재 위치를 이용해 LatLon 객체 생성
        LatLng curPoint = new LatLng(latitude, longitude);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        // 지도 유형 설정. 지형도인 경우에는 GoogleMap.MAP_TYPE_TERRAIN, 위성 지도인 경우에는 GoogleMap.MAP_TYPE_SATELLITE
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }


    private void showDrinkPosition() throws ExecutionException, InterruptedException {
        GetDrinkXML getDrinkXML = new GetDrinkXML();
        getDrinkXML.execute("http://openapi.seoul.go.kr:8088/754a485a6c706a68373866644b5963/xml/GeoInfoDrinkWaterWGS/1/74").get();
    }


    private class GetDrinkXML extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]); // <---------------
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 빌더팩토리생성
                DocumentBuilder builder = factory.newDocumentBuilder(); // xml문서 빌더 객체를 생성
                doc = builder.parse(new InputSource(url.openStream())); // xml문서 파싱
                doc.getDocumentElement();
                //doc.getDocumentElement().normalize(); // 정렬

            } catch (Exception e) {
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {     // 백그라운드 작업이 완료되면 자동실행됨

            String gigu;
            double lat;
            double lng;

            NodeList nodeList = document.getElementsByTagName("row");

            int length = nodeList.getLength();

            hanGangDrinkInfoObjects = new HanGangDrinkInfoObject[length];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node rowNode = nodeList.item(i);
                Element firstElem = (Element) rowNode;

                NodeList giguList = firstElem.getElementsByTagName("GIGU");
                Element nameElem = (Element) giguList.item(0);

                giguList = nameElem.getChildNodes();

                gigu = ((Node) giguList.item(0)).getNodeValue();

                NodeList latitude = firstElem.getElementsByTagName("LAT");
                lat = Double.parseDouble(latitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList longitude = firstElem.getElementsByTagName("LNG");
                lng = Double.parseDouble(longitude.item(0).getChildNodes().item(0).getNodeValue());


                hanGangDrinkInfoObjects[i] = new HanGangDrinkInfoObject(gigu, lng, lat); // 배열 완성
            }

            // 지도찍기

            showAllDrinkPosition();

            super.onPostExecute(document);
        }
    }

    private void showAllDrinkPosition() {

        MarkerOptions marker = new MarkerOptions();
        //PolylineOptions options = new PolylineOptions();

        for (int i = 0; i < hanGangDrinkInfoObjects.length; i++) {
            marker.position(new LatLng(hanGangDrinkInfoObjects[i].getLatitude(),
                    hanGangDrinkInfoObjects[i].getLongitude()));
            marker.title("식수대");
            marker.draggable(true);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.drink_position));

            map.addMarker(marker);

            //options.add(new LatLng(hanGangDrinkInfoObjects[i].getLatitude(), hanGangDrinkInfoObjects[i].getLongitude()));
        }

        LatLng showDrinkPositionfromHere = new LatLng(hanGangDrinkInfoObjects[0].getLatitude(),
                hanGangDrinkInfoObjects[0].getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(showDrinkPositionfromHere, 16));

        //Polyline polyline = map.addPolyline(options);

        //

    }

    private void showLawnPosition() throws ExecutionException, InterruptedException {
        GetLawnXML getLawnXML = new GetLawnXML();
        getLawnXML.execute("http://openapi.seoul.go.kr:8088/754a485a6c706a68373866644b5963/xml/GeoInfoLawnWGS/1/350").get();
    }

    // 잔디밭
    private class GetLawnXML extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]); // <---------------
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 빌더팩토리생성
                DocumentBuilder builder = factory.newDocumentBuilder(); // xml문서 빌더 객체를 생성
                doc = builder.parse(new InputSource(url.openStream())); // xml문서 파싱
                doc.getDocumentElement();
                //doc.getDocumentElement().normalize(); // 정렬

            } catch (Exception e) {
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {     // 백그라운드 작업이 완료되면 자동실행됨

            String gigu;
            double lat;
            double lng;

            NodeList nodeList = document.getElementsByTagName("row");

            int length = nodeList.getLength();

            hanGangLawnInfoObjects = new HanGangLawnInfoObject[length];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node rowNode = nodeList.item(i);
                Element firstElem = (Element) rowNode;

                NodeList giguList = firstElem.getElementsByTagName("GIGU");
                Element nameElem = (Element) giguList.item(0);

                giguList = nameElem.getChildNodes();

                gigu = ((Node) giguList.item(0)).getNodeValue();

                NodeList latitude = firstElem.getElementsByTagName("LAT");
                lat = Double.parseDouble(latitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList longitude = firstElem.getElementsByTagName("LNG");
                lng = Double.parseDouble(longitude.item(0).getChildNodes().item(0).getNodeValue());


                hanGangLawnInfoObjects[i] = new HanGangLawnInfoObject(gigu, lng, lat); // 배열 완성
            }

            showAllLawnPosition();

            super.onPostExecute(document);

        }
    }

    private void showAllLawnPosition() {
        MarkerOptions marker = new MarkerOptions();

        for (int i = 0; i < hanGangLawnInfoObjects.length; i++) {
            marker.position(new LatLng(hanGangLawnInfoObjects[i].getLatitude(),
                    hanGangLawnInfoObjects[i].getLongitude()));
            marker.title("잔디밭");
            marker.draggable(true);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.lawn_position));

            map.addMarker(marker);
        }

        LatLng showLawnPositionfromHere = new LatLng(hanGangLawnInfoObjects[0].getLatitude(),
                hanGangLawnInfoObjects[0].getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(showLawnPositionfromHere, 16));

    }

    private void showFootVolleyBallPosition() throws ExecutionException, InterruptedException {
        GetFootVolleyBallXML getFootVolleyBallXML = new GetFootVolleyBallXML();
        getFootVolleyBallXML.execute("http://openAPI.seoul.go.kr:8088/754a485a6c706a68373866644b5963/xml/GeoInfoJokguWGS/1/5/").get();
    }

    private class GetFootVolleyBallXML extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]); // <---------------
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 빌더팩토리생성
                DocumentBuilder builder = factory.newDocumentBuilder(); // xml문서 빌더 객체를 생성
                doc = builder.parse(new InputSource(url.openStream())); // xml문서 파싱
                doc.getDocumentElement().normalize(); // 정렬

            } catch (Exception e) {
                e.printStackTrace();
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {

            String gigu;
            double lat;
            double lng;

            NodeList nodeList = document.getElementsByTagName("row");

            int length = nodeList.getLength();

            hanGangFootVolleyBallInfoObjects = new HanGangFootVolleyBallInfoObject[length];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node rowNode = nodeList.item(i);
                Element firstElem = (Element) rowNode;

                NodeList giguList = firstElem.getElementsByTagName("GIGU");
                Element nameElem = (Element) giguList.item(0);

                giguList = nameElem.getChildNodes();

                gigu = ((Node) giguList.item(0)).getNodeValue();

                NodeList latitude = firstElem.getElementsByTagName("LAT");
                lat = Double.parseDouble(latitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList longitude = firstElem.getElementsByTagName("LNG");
                lng = Double.parseDouble(longitude.item(0).getChildNodes().item(0).getNodeValue());


                hanGangFootVolleyBallInfoObjects[i] = new HanGangFootVolleyBallInfoObject(gigu, lng, lat); // 배열 완성
            }

            showAllFootVolleyBallPosition();


            super.onPostExecute(document);
        }

        private void showAllFootVolleyBallPosition() {

            MarkerOptions marker = new MarkerOptions();

            for (int i = 0; i < hanGangFootVolleyBallInfoObjects.length; i++) {
                marker.position(new LatLng(hanGangFootVolleyBallInfoObjects[i].getLatitude(),
                        hanGangFootVolleyBallInfoObjects[i].getLongitude()));
                marker.title("족구장");
                marker.draggable(true);
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.volleyball_position));

                map.addMarker(marker);
            }

            LatLng showFootVolleyBallPositionfromHere = new LatLng(hanGangFootVolleyBallInfoObjects[0].getLatitude(),
                    hanGangFootVolleyBallInfoObjects[0].getLongitude());

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(showFootVolleyBallPositionfromHere, 16));

        }
    }

    private void showBikeRentPosition() throws ExecutionException, InterruptedException {
        GetBikeRentXML getBikeRentXML = new GetBikeRentXML();
        getBikeRentXML.execute("http://openAPI.seoul.go.kr:8088/754a485a6c706a68373866644b5963/xml/GeoInfoBicycleLendWGS/1/10").get();
    }

    private class GetBikeRentXML extends AsyncTask<String, Void, Document> {

        URL url;

        @Override
        protected Document doInBackground(String... urls) {

            try {
                url = new URL(urls[0]); // <---------------
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 빌더팩토리생성
                DocumentBuilder builder = factory.newDocumentBuilder(); // xml문서 빌더 객체를 생성
                doc = builder.parse(new InputSource(url.openStream())); // xml문서 파싱
                doc.getDocumentElement().normalize(); // 정렬

            } catch (Exception e) {
                e.printStackTrace();
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {

            String gigu;
            double lat;
            double lng;
            String fee; // 요금
            String timeGubun; // 시간대
            String tel; // 전화번호


            NodeList nodeList = document.getElementsByTagName("row");

            int length = nodeList.getLength();

            hanGangBikeRentalInfoObjects = new HanGangBikeRentalInfoObject[length];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node rowNode = nodeList.item(i);
                Element firstElem = (Element) rowNode;

                NodeList giguList = firstElem.getElementsByTagName("GIGU");
                Element nameElem = (Element) giguList.item(0);

                giguList = nameElem.getChildNodes();

                gigu = ((Node) giguList.item(0)).getNodeValue();

                NodeList latitude = firstElem.getElementsByTagName("LAT");
                lat = Double.parseDouble(latitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList longitude = firstElem.getElementsByTagName("LNG");
                lng = Double.parseDouble(longitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList feeList = firstElem.getElementsByTagName("FEEGUBUN");
                Element feeElem = (Element) feeList.item(0);
                feeList = feeElem.getChildNodes();
                fee = (feeList.item(0)).getNodeValue();

                NodeList timeGubunList= firstElem.getElementsByTagName("TIMEGUBUN");
                Element timeGubunElem = (Element) timeGubunList.item(0);
                timeGubunList = timeGubunElem.getChildNodes();
                timeGubun = (timeGubunList.item(0)).getNodeValue();

                NodeList telList = firstElem.getElementsByTagName("TEL");
                Element telElem = (Element) telList.item(0);
                telList = telElem.getChildNodes();
                tel = (telList.item(0)).getNodeValue();

                hanGangBikeRentalInfoObjects[i] = new HanGangBikeRentalInfoObject(gigu, lng, lat, fee, timeGubun, tel); // 배열 완성
            }

            showAllBikeRentalPosition();

            super.onPostExecute(document);
        }
    }

    private void showAllBikeRentalPosition() {

        MarkerOptions marker = new MarkerOptions();

        // 말풍선에 다 안들어가서 새로이 정의해야될듯
        for (int i = 0; i < hanGangBikeRentalInfoObjects.length; i++) {
            marker.position(new LatLng(hanGangBikeRentalInfoObjects[i].getLatitude(),
                    hanGangBikeRentalInfoObjects[i].getLongitude()));
            marker.title("자전거 대여소");
            marker.snippet("전화번호: "+hanGangBikeRentalInfoObjects[i].getTel() + "\n" +
                    "이용시간대 : "+hanGangBikeRentalInfoObjects[i].getTimeGubun() + "\n" +
                    "요금 : " + hanGangBikeRentalInfoObjects[i].getFee());
            marker.draggable(true);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.bicycle_position));

            map.addMarker(marker);
        }

        LatLng showBikeRentalPositionfromHere = new LatLng(hanGangBikeRentalInfoObjects[0].getLatitude(),
                hanGangBikeRentalInfoObjects[0].getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(showBikeRentalPositionfromHere, 16));
    }

    private void showTennisPosition() throws ExecutionException, InterruptedException {
        GetTennisXML getTennisXML = new GetTennisXML();
        getTennisXML.execute("http://openAPI.seoul.go.kr:8088/754a485a6c706a68373866644b5963/xml/GeoInfoTennisWGS/1/29").get();
    }

    private class GetTennisXML extends AsyncTask<String, Void, Document> {
        URL url;

        @Override
        protected Document doInBackground(String... urls) {
            try {
                url = new URL(urls[0]); // <---------------
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 빌더팩토리생성
                DocumentBuilder builder = factory.newDocumentBuilder(); // xml문서 빌더 객체를 생성
                doc = builder.parse(new InputSource(url.openStream())); // xml문서 파싱
                doc.getDocumentElement().normalize(); // 정렬

            } catch (Exception e) {
                e.printStackTrace();
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {

            String gigu;
            double lat;
            double lng;
            String tennis_name;
            String tel;
            String fee;

            NodeList nodeList = document.getElementsByTagName("row");

            int length = nodeList.getLength();

            hanGangTennisInfoObjects = new HanGangTennisInfoObject[length];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node rowNode = nodeList.item(i);
                Element firstElem = (Element) rowNode;

                NodeList giguList = firstElem.getElementsByTagName("GIGU");
                Element nameElem = (Element) giguList.item(0);

                giguList = nameElem.getChildNodes();

                gigu = ((Node) giguList.item(0)).getNodeValue();

                NodeList latitude = firstElem.getElementsByTagName("LAT");
                lat = Double.parseDouble(latitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList longitude = firstElem.getElementsByTagName("LNG");
                lng = Double.parseDouble(longitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList nameList = firstElem.getElementsByTagName("TENAME");
                Element tennisnameElem = (Element) nameList.item(0);
                nameList = tennisnameElem.getChildNodes();
                tennis_name = (nameList.item(0)).getNodeValue();

                NodeList telList= firstElem.getElementsByTagName("TEL");
                Element telElem = (Element) telList.item(0);
                telList = telElem.getChildNodes();
                tel = (telList.item(0)).getNodeValue();

                NodeList feeList = firstElem.getElementsByTagName("RMK");
                Element feeElem = (Element) feeList.item(0);
                feeList = feeElem.getChildNodes();
                fee = (feeList.item(0)).getNodeValue();

                hanGangTennisInfoObjects[i] = new HanGangTennisInfoObject(gigu, lng, lat, tennis_name, tel, fee); // 배열 완성
            }

            showAllTennisPosition();

            super.onPostExecute(document);
        }
    }

    private void showAllTennisPosition() {

        MarkerOptions marker = new MarkerOptions();

        // 말풍선에 다 안들어가서 새로이 정의해야될듯
        for (int i = 0; i < hanGangTennisInfoObjects.length; i++) {
            marker.position(new LatLng(hanGangTennisInfoObjects[i].getLatitude(),
                    hanGangTennisInfoObjects[i].getLongitude()));
            marker.title("테니스장");

            marker.snippet("테니스장명 : " + hanGangTennisInfoObjects[i].getName()+ "\n" +
                    "전화번호: " + hanGangTennisInfoObjects[i].getTel() + "\n" +
                    "요금 : " + hanGangTennisInfoObjects[i].getFee());

            marker.draggable(true);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.tennis_position));

            map.addMarker(marker);

        }

        LatLng showTennisPositionfromHere = new LatLng(hanGangTennisInfoObjects[0].getLatitude(),
                hanGangTennisInfoObjects[0].getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(showTennisPositionfromHere, 16));
    }

    private void showStorePosition() throws ExecutionException, InterruptedException {
        GetStoreXML getStoreXML = new GetStoreXML();
        getStoreXML.execute("http://openAPI.seoul.go.kr:8088/754a485a6c706a68373866644b5963/xml/GeoInfoStoreWGS/1/63").get();
    }

    private class GetStoreXML extends AsyncTask<String, Void, Document> {
        URL url;

        @Override
        protected Document doInBackground(String... urls) {
            try {
                url = new URL(urls[0]); // <---------------
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 빌더팩토리생성
                DocumentBuilder builder = factory.newDocumentBuilder(); // xml문서 빌더 객체를 생성
                doc = builder.parse(new InputSource(url.openStream())); // xml문서 파싱
                doc.getDocumentElement().normalize(); // 정렬

            } catch (Exception e) {
                e.printStackTrace();
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {
            String gigu;
            double lat;
            double lng;

            NodeList nodeList = document.getElementsByTagName("row");

            int length = nodeList.getLength();

            hanGangStoreInfoObjects = new HanGangStoreInfoObject[length];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node rowNode = nodeList.item(i);
                Element firstElem = (Element) rowNode;

                NodeList giguList = firstElem.getElementsByTagName("GIGU");
                Element nameElem = (Element) giguList.item(0);

                giguList = nameElem.getChildNodes();

                gigu = ((Node) giguList.item(0)).getNodeValue();

                NodeList latitude = firstElem.getElementsByTagName("LAT");
                lat = Double.parseDouble(latitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList longitude = firstElem.getElementsByTagName("LNG");
                lng = Double.parseDouble(longitude.item(0).getChildNodes().item(0).getNodeValue());


                hanGangStoreInfoObjects[i] = new HanGangStoreInfoObject(gigu, lng, lat);
            }

            showAllStorePosition();
            super.onPostExecute(document);
        }
    }

    private void showAllStorePosition() {
        MarkerOptions marker = new MarkerOptions();

        // 말풍선에 다 안들어가서 새로이 정의해야될듯
        for (int i = 0; i < hanGangStoreInfoObjects.length; i++) {
            marker.position(new LatLng(hanGangStoreInfoObjects[i].getLatitude(),
                    hanGangStoreInfoObjects[i].getLongitude()));
            marker.title("px");
            marker.draggable(true);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.shop_position));

            map.addMarker(marker);
        }

        LatLng showAllStorePositionfromHere = new LatLng(hanGangStoreInfoObjects[0].getLatitude(),
                hanGangStoreInfoObjects[0].getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(showAllStorePositionfromHere, 16));
    }

    private void showParkingPosition() throws ExecutionException, InterruptedException {
        GetParkingXML getParkingXML = new GetParkingXML();
        getParkingXML.execute("http://openAPI.seoul.go.kr:8088/754a485a6c706a68373866644b5963/xml/GeoInfoParkParkingWGS/1/5").get();

    }

    private class GetParkingXML extends AsyncTask<String, Void, Document> {
        URL url;

        @Override
        protected Document doInBackground(String... urls) {

            try {
                url = new URL(urls[0]); // <---------------
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 빌더팩토리생성
                DocumentBuilder builder = factory.newDocumentBuilder(); // xml문서 빌더 객체를 생성
                doc = builder.parse(new InputSource(url.openStream())); // xml문서 파싱
                doc.getDocumentElement().normalize(); // 정렬

            } catch (Exception e) {
                e.printStackTrace();
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {

            String gigu;
            double lat;
            double lng;
            String tel;
            String fee;

            NodeList nodeList = document.getElementsByTagName("row");

            int length = nodeList.getLength();

            hanGangParkingInfoObjects = new HanGangParkingInfoObject[length];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node rowNode = nodeList.item(i);
                Element firstElem = (Element) rowNode;

                NodeList giguList = firstElem.getElementsByTagName("GIGU");
                Element nameElem = (Element) giguList.item(0);

                giguList = nameElem.getChildNodes();

                gigu = ((Node) giguList.item(0)).getNodeValue();

                NodeList latitude = firstElem.getElementsByTagName("LAT");
                lat = Double.parseDouble(latitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList longitude = firstElem.getElementsByTagName("LNG");
                lng = Double.parseDouble(longitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList telList= firstElem.getElementsByTagName("TEL");
                Element telElem = (Element) telList.item(0);
                telList = telElem.getChildNodes();
                tel = (telList.item(0)).getNodeValue();

                NodeList feeList = firstElem.getElementsByTagName("RMK");
                Element feeElem = (Element) feeList.item(0);
                feeList = feeElem.getChildNodes();
                fee = (feeList.item(0)).getNodeValue();

                hanGangParkingInfoObjects[i] = new HanGangParkingInfoObject(gigu, lng, lat, tel, fee); // 배열 완성
            }

            showAllParkingPosition();

            super.onPostExecute(document);
        }
    }

    private void showAllParkingPosition() {
        MarkerOptions marker = new MarkerOptions();

        // 말풍선에 다 안들어가서 새로이 정의해야될듯
        for (int i = 0; i < hanGangParkingInfoObjects.length; i++) {
            marker.position(new LatLng(hanGangParkingInfoObjects[i].getLatitude(),
                    hanGangParkingInfoObjects[i].getLongitude()));
            marker.title("주차장");
            marker.draggable(true);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_position));
            marker.snippet("전화번호 :  " + hanGangParkingInfoObjects[i].getTel() + "\n"+
                            "요금 : " + hanGangParkingInfoObjects[i].getFee());
            map.addMarker(marker);
        }

        LatLng showAllParkingPositionfromHere = new LatLng(hanGangParkingInfoObjects[0].getLatitude(),
                hanGangParkingInfoObjects[0].getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(showAllParkingPositionfromHere, 16));
    }

    private void showRoute1() {
        PolylineOptions options = new PolylineOptions().color(Color.RED).width(25);
        options.add(new LatLng(37.533070, 126.923386));
        options.add(new LatLng(37.532938, 126.924024));
        options.add(new LatLng(37.532643, 126.924562));
        options.add(new LatLng(37.531656, 126.925503));
        options.add(new LatLng(37.531422, 126.925852));
        options.add(new LatLng(37.530714, 126.927264));
        options.add(new LatLng(37.530408, 126.927843));
        options.add(new LatLng(37.531139, 126.929016));
        options.add(new LatLng(37.532313, 126.926377));
        options.add(new LatLng(37.532177, 126.925980));
        options.add(new LatLng(37.532688, 126.925164));
        options.add(new LatLng(37.532917, 126.925196));
        options.add(new LatLng(37.533573, 126.923115));
        options.add(new LatLng(37.533624, 126.922954));

        Polyline polyline = map.addPolyline(options);

        LatLng showMapFromHere = new LatLng(37.533070,126.9233860);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(showMapFromHere, 16));
    }

    private void showSoccerPosition() throws ExecutionException, InterruptedException {
        GetSoccerXML getSoccerXML = new GetSoccerXML();
        getSoccerXML.execute("http://openAPI.seoul.go.kr:8088/754a485a6c706a68373866644b5963/xml/GeoInfoSoccerWGS/1/20").get();
    }

    private class GetSoccerXML extends AsyncTask<String, Void, Document> {

        URL url;
        @Override
        protected Document doInBackground(String... urls) {

            try {
                url = new URL(urls[0]); // <---------------
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 빌더팩토리생성
                DocumentBuilder builder = factory.newDocumentBuilder(); // xml문서 빌더 객체를 생성
                doc = builder.parse(new InputSource(url.openStream())); // xml문서 파싱
                doc.getDocumentElement().normalize(); // 정렬

            } catch (Exception e) {
                e.printStackTrace();
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {

            String gigu;
            double lat;
            double lng;
            String tel;
            String fee;
            String name;

            NodeList nodeList = document.getElementsByTagName("row");

            int length = nodeList.getLength();

            hanGangSoccerInfoObjects = new HanGangSoccerInfoObject[length];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node rowNode = nodeList.item(i);
                Element firstElem = (Element) rowNode;

                NodeList giguList = firstElem.getElementsByTagName("GIGU");
                Element giguElem = (Element) giguList.item(0);
                giguList = giguElem.getChildNodes();
                gigu = ((Node) giguList.item(0)).getNodeValue();

                NodeList nameList = firstElem.getElementsByTagName("SSONAME");
                Element nameElem = (Element) nameList.item(0);
                nameList = nameElem.getChildNodes();
                name = nameList.item(0).getNodeValue();

                NodeList latitude = firstElem.getElementsByTagName("LAT");
                lat = Double.parseDouble(latitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList longitude = firstElem.getElementsByTagName("LNG");
                lng = Double.parseDouble(longitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList telList= firstElem.getElementsByTagName("TEL");
                Element telElem = (Element) telList.item(0);
                telList = telElem.getChildNodes();
                tel = (telList.item(0)).getNodeValue();

                NodeList feeList = firstElem.getElementsByTagName("RMK");
                Element feeElem = (Element) feeList.item(0);
                feeList = feeElem.getChildNodes();
                fee = (feeList.item(0)).getNodeValue();

                hanGangSoccerInfoObjects[i] = new HanGangSoccerInfoObject(gigu, lat, lng, name, tel, fee); // 배열 완성
            }

            showAllSoccerPosition();

            super.onPostExecute(document);
        }
    }

    private void showAllSoccerPosition() {
        MarkerOptions marker = new MarkerOptions();

        // 말풍선에 다 안들어가서 새로이 정의해야될듯
        for (int i = 0; i < hanGangSoccerInfoObjects.length; i++) {
            marker.position(new LatLng(hanGangSoccerInfoObjects[i].getLatitude(),
                    hanGangSoccerInfoObjects[i].getLongitude()));
            marker.title("축구장");
            marker.draggable(true);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.soccer_position));
            marker.snippet("전화번호 :  " + hanGangSoccerInfoObjects[i].getTel() + "\n"+
                    "요금 : " + hanGangSoccerInfoObjects[i].getFee());
            map.addMarker(marker);
        }

        LatLng showAllSoccerPositionfromHere = new LatLng(hanGangSoccerInfoObjects[0].getLatitude(),
                hanGangSoccerInfoObjects[0].getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(showAllSoccerPositionfromHere, 16));
    }

    private void showBadmintonPosition() throws ExecutionException, InterruptedException {
        GetBadmintonXML getBadmintonXML = new GetBadmintonXML();
        getBadmintonXML.execute("http://openAPI.seoul.go.kr:8088/754a485a6c706a68373866644b5963/xml/GeoInfoBadmintonWGS/1/19").get();
    }

    private class GetBadmintonXML extends AsyncTask<String, Void, Document> {
        URL url;

        @Override
        protected Document doInBackground(String... urls) {

            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 빌더팩토리생성
                DocumentBuilder builder = factory.newDocumentBuilder(); // xml문서 빌더 객체를 생성
                doc = builder.parse(new InputSource(url.openStream())); // xml문서 파싱
                doc.getDocumentElement().normalize(); // 정렬

            } catch (Exception e) {
                e.printStackTrace();
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {
            String gigu;
            double lat;
            double lng;
            String tel;
            String fee;
            String name;

            NodeList nodeList = document.getElementsByTagName("row");

            int length = nodeList.getLength();

            hanGangBadmintonInfoObjects = new HanGangBadmintonInfoObject[length];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node rowNode = nodeList.item(i);
                Element firstElem = (Element) rowNode;

                NodeList giguList = firstElem.getElementsByTagName("GIGU");
                Element giguElem = (Element) giguList.item(0);
                giguList = giguElem.getChildNodes();
                gigu = ((Node) giguList.item(0)).getNodeValue();

                NodeList nameList = firstElem.getElementsByTagName("BADNAME");
                Element nameElem = (Element) nameList.item(0);
                nameList = nameElem.getChildNodes();
                name = nameList.item(0).getNodeValue();

                NodeList latitude = firstElem.getElementsByTagName("LAT");
                lat = Double.parseDouble(latitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList longitude = firstElem.getElementsByTagName("LNG");
                lng = Double.parseDouble(longitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList telList= firstElem.getElementsByTagName("TEL");
                Element telElem = (Element) telList.item(0);
                telList = telElem.getChildNodes();
                tel = (telList.item(0)).getNodeValue();

                NodeList feeList = firstElem.getElementsByTagName("RMK");
                Element feeElem = (Element) feeList.item(0);
                feeList = feeElem.getChildNodes();
                fee = (feeList.item(0)).getNodeValue();

                hanGangBadmintonInfoObjects[i] = new HanGangBadmintonInfoObject(gigu, lat, lng                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             , name, tel, fee); // 배열 완성
            }

            showAllBadmintonPosition();

            super.onPostExecute(document);
        }
    }

    private void showAllBadmintonPosition() {
        MarkerOptions marker = new MarkerOptions();

        // 말풍선에 다 안들어가서 새로이 정의해야될듯
        for (int i = 0; i < hanGangBadmintonInfoObjects.length; i++) {
            marker.position(new LatLng(hanGangBadmintonInfoObjects[i].getLatitude(),
                    hanGangBadmintonInfoObjects[i].getLongitude()));
            marker.title("배드민턴장");
            marker.draggable(true);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.badminton_position));
            marker.snippet("전화번호 :  " + hanGangBadmintonInfoObjects[i].getTel() + "\n"+
                    "요금 : " + hanGangBadmintonInfoObjects[i].getFee());
            map.addMarker(marker);
        }

        LatLng showAllBadmintonPositionfromHere = new LatLng(hanGangBadmintonInfoObjects[0].getLatitude(),
                hanGangBadmintonInfoObjects[0].getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(showAllBadmintonPositionfromHere, 16));
    }

    private void showPlaygroundPosition() throws ExecutionException, InterruptedException {
        GetPlaygourndXML getPlaygourndXML = new GetPlaygourndXML();
        getPlaygourndXML.execute("http://openAPI.seoul.go.kr:8088/754a485a6c706a68373866644b5963/xml/GeoInfoPlaygroundWGS/1/79").get();
    }

    private class GetPlaygourndXML extends AsyncTask<String, Void, Document> {
        URL url;

        @Override
        protected Document doInBackground(String... urls) {
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 빌더팩토리생성
                DocumentBuilder builder = factory.newDocumentBuilder(); // xml문서 빌더 객체를 생성
                doc = builder.parse(new InputSource(url.openStream())); // xml문서 파싱
                doc.getDocumentElement().normalize(); // 정렬

            } catch (Exception e) {
                e.printStackTrace();
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {
            String gigu;
            double lat;
            double lng;

            NodeList nodeList = document.getElementsByTagName("row");

            int length = nodeList.getLength();

            hanGangPlaygroundObjectInfos = new HanGangPlaygroundObjectInfo[length];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node rowNode = nodeList.item(i);
                Element firstElem = (Element) rowNode;

                NodeList giguList = firstElem.getElementsByTagName("GIGU");
                Element nameElem = (Element) giguList.item(0);

                giguList = nameElem.getChildNodes();

                gigu = ((Node) giguList.item(0)).getNodeValue();

                NodeList latitude = firstElem.getElementsByTagName("LAT");
                lat = Double.parseDouble(latitude.item(0).getChildNodes().item(0).getNodeValue());

                NodeList longitude = firstElem.getElementsByTagName("LNG");
                lng = Double.parseDouble(longitude.item(0).getChildNodes().item(0).getNodeValue());


                hanGangPlaygroundObjectInfos[i] = new HanGangPlaygroundObjectInfo(gigu, lng, lat); // 배열 완성
            }

            showAllPlaygroundPosition();

            super.onPostExecute(document);
        }
    }

    private void showAllPlaygroundPosition() {
        MarkerOptions marker = new MarkerOptions();

        // 말풍선에 다 안들어가서 새로이 정의해야될듯
        for (int i = 0; i < hanGangPlaygroundObjectInfos.length; i++) {
            marker.position(new LatLng(hanGangPlaygroundObjectInfos[i].getLatitude(),
                    hanGangPlaygroundObjectInfos[i].getLongitude()));
            marker.title("어린이 놀이터");
            marker.draggable(true);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.playground_position));
            map.addMarker(marker);
        }

        LatLng showAllPlaygroundfromHere = new LatLng(hanGangPlaygroundObjectInfos[0].getLatitude(),
                hanGangPlaygroundObjectInfos[0].getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(showAllPlaygroundfromHere, 16));
    }

}

