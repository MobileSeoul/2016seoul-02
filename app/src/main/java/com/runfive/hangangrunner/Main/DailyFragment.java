package com.runfive.hangangrunner.Main;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.runfive.hangangrunner.Common.RecordObject;
import com.runfive.hangangrunner.Common.UserObject;
import com.runfive.hangangrunner.R;
import com.runfive.hangangrunner.Running.RunningActivity;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by JunHo on 2016-08-07.
 */
public class DailyFragment extends Fragment {

    private TextView today;
    private Button running_button;
    private TextView daily_run_distance;
    private Double total_distacne = 0.0;
    private float selectedDistance = 3000f;
    private TextView mainRunDistance;
    private TextView mainRunTime;
    private TextView mainKcal;
    private String userId;
    private double totalKm=0.0;
    private String totalRunTime;
    private double totalKcal=0.0;

    public DailyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_daily_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //textView_shortWeather = (TextView)findViewById(R.id.shortWeather);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);
        userId = sharedPreferences.getString("id", null);

        new ReceiveShortWeather().execute();


        /**
         *  직렬화 객체 받기
         */
        Intent intent = getActivity().getIntent();
        UserObject userObject = (UserObject) intent.getSerializableExtra("UserObect");
        RecordObject[] recordObject = (RecordObject[]) intent.getSerializableExtra("RecordObject");

        if (recordObject != null) {
            for (int i = 0; i < recordObject.length; i++) {
                total_distacne += Double.parseDouble(recordObject[i].getDistance());
            }

            daily_run_distance.setText(total_distacne.toString());
        } else {
            //daily_run_distance.setText("0.0 km");
        }

        today = (TextView) getActivity().findViewById(R.id.main_today);
        running_button = (Button) getActivity().findViewById(R.id.main_running_button);

        //달리기 버튼 클릭
        running_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectDistanceDialog(v);
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일", Locale.getDefault());
        Calendar calendar = Calendar.getInstance(); // 오늘날짜
        String date = sdf.format(calendar.getTime());
        today.setText(date);

        /**
         *  메인 record 받기
         */

        mainRunDistance = (TextView) getActivity().findViewById(R.id.main_rundistance);
        mainRunTime = (TextView) getActivity().findViewById(R.id.main_runtime);
        mainKcal = (TextView) getActivity().findViewById(R.id.main_cal);

        getMainRecordData();

        super.onActivityCreated(savedInstanceState);
    }

    private void getMainRecordData() {

        class GetMainRecordData extends AsyncTask<String, Void, String> {
            private URL url;

            public GetMainRecordData(String url) throws MalformedURLException {
                this.url = new URL(url);
            }

            private String readStream(InputStream in) throws IOException {
                StringBuilder jsonHtml = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line = null;

                while((line = reader.readLine()) != null)
                    jsonHtml.append(line);

                reader.close();
                return jsonHtml.toString();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String postData = "user_id=" + params[0];
                    System.out.println("유저아이디 : "+params[0]);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(5000);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(postData.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();
                    String result = readStream(conn.getInputStream());

                    System.out.println(result);
                    conn.disconnect();

                    totalKm = 0.0;
                    totalKcal = 0.0;

                    return result;
                }
                catch (Exception e) {
                    Log.i("PHPRequest", "request was failed.");
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jObject = new JSONObject(s);
                    JSONArray results = jObject.getJSONArray("result");

                    DecimalFormat decimalFormat = new DecimalFormat("####.#");

                    for ( int i = 0; i < results.length(); ++i ) {
                        JSONObject temp = results.getJSONObject(i);

//                       데이터 없을경우 발생하는  예외처리
                        if(temp.getString("distance").equals("null")){
                            totalKm = 0D;
                            mainRunDistance.setText("0 km");
                        }else{
                            totalKm += Double.parseDouble(temp.getString("distance"));
                            mainRunDistance.setText(String.valueOf(decimalFormat.format(totalKm))+" Km");

                        }

                        if(temp.getString("kcal").equals("null")){
                            totalKcal = 0D;
                            mainKcal.setText("0 Kcal");
                        }else{
                            totalKcal += Double.parseDouble(temp.getString("kcal"));
                            mainKcal.setText(String.valueOf(decimalFormat.format(totalKcal)));

                        }

                        if(temp.getString("time").equals("null"))
                        {
                            totalRunTime="00:00:00";
                        }else{
                            totalRunTime = temp.getString("time");
                        }

                    }


                    mainRunTime.setText(totalRunTime);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "네트워크 연결이 불안정합니다", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetMainRecordData getMainRecordData = null;
        try {
            getMainRecordData = new GetMainRecordData("Your Server ULR");
            getMainRecordData.execute(userId);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private boolean enableGPSSetting() {
        ContentResolver res = getActivity().getContentResolver();

        boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled(res, LocationManager.GPS_PROVIDER);
        if(!gpsEnabled) {
            new android.app.AlertDialog.Builder(getActivity()).setTitle("GPS 설정").setMessage("GPS가 꺼져 있습니다.\nGPS를 켜시겠습니까?")
                    .setPositiveButton("GPS켜기", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //getActivity().finish();
                            Toast.makeText(getActivity(),"gps를 켜야합니다", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        } else {

        }

        boolean gpsEnabledReturn = Settings.Secure.isLocationProviderEnabled(res, LocationManager.GPS_PROVIDER);
        if(gpsEnabledReturn)
            return true;
        else
            return false;
    }

    private void selectDistanceDialog(View v) {

        boolean isGPSset = enableGPSSetting();

        if(isGPSset) {
            final String items[] = {"3Km", "5Km", "7Km", "10Km", "15Km"};
            AlertDialog.Builder ab = new AlertDialog.Builder(v.getContext());
            ab.setTitle("Select Distance");
            ab.setSingleChoiceItems(items, 0,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Distance 선택
                            switch (whichButton) {
                                case 0:
                                    selectedDistance = 3000f;
                                    break;
                                case 1:
                                    selectedDistance = 5000f;
                                    break;
                                case 2:
                                    selectedDistance = 7000f;
                                    break;
                                case 3:
                                    selectedDistance = 10000f;
                                    break;
                                case 4:
                                    selectedDistance = 15000f;
                                    break;
                                default:
                                    selectedDistance = 3000f;
                                    break;
                            }

                        }
                    }).setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // OK 버튼 클릭시 , 여기서 선택한 값을 메인 Activity 로 넘기면 된다.
                            Intent intent = new Intent(getContext(), RunningActivity.class);
                            intent.putExtra("distance", selectedDistance);
                            startActivity(intent);
                            getActivity().finish();


                        }
                    }).setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Cancel 버튼 클릭시
                        }
                    });
            ab.show();
        } else {
            //Toast.makeText(getActivity(), "GPS를 켜야 합니다", Toast.LENGTH_SHORT).show();
        }
    }

    public class ReceiveShortWeather extends AsyncTask<URL, Integer, Long> {

        ArrayList<ShortWeather> shortWeathers = new ArrayList<ShortWeather>();

        @Override
        protected Long doInBackground(URL... urls) {

            String url = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1156054000";

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;

            try {
                response = client.newCall(request).execute();
                parseXML(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "네트워크 연결이 불안정합니다", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Long result) {
            String data = "";

            TextView weather_status = (TextView) getActivity().findViewById(R.id.weather_status);
            TextView weather_tem = (TextView) getActivity().findViewById(R.id.weather_tem);
            TextView weather_rainrate = (TextView) getActivity().findViewById(R.id.weather_rainrate);
            TextView weather_reh = (TextView) getActivity().findViewById(R.id.weather_reh);

            // Calendar 객체로 현재시간 받아오기
            Calendar calendar = Calendar.getInstance();
            // HOUR_OF_DAY 타입으로 받아오면 1시~24시로 받아와서 hour 에 저장
            int hour = calendar.get(Calendar.HOUR_OF_DAY);


            if (hour < 20) {

                // 21시 이전이면 get(0) 오늘 날씨
                weather_status.setText(shortWeathers.get(0).getWfKor().toString());
                weather_tem.setText(shortWeathers.get(0).getTemp().toString());
                weather_rainrate.setText(shortWeathers.get(0).getPop().toString() + "%");
                weather_reh.setText(shortWeathers.get(0).getReh().toString() + "%");

            } else {
                // 21시 이후면 get(1)내일 날씨
                weather_status.setText(shortWeathers.get(1).getWfKor().toString());
                weather_tem.setText(shortWeathers.get(1).getTemp().toString());
                weather_rainrate.setText(shortWeathers.get(1).getPop().toString() + "%");
                weather_reh.setText(shortWeathers.get(1).getReh().toString() + "%");
            }

            /* 예외추가 */
            try{
                setWeather();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "네트워크 연결이 불안정합니다", Toast.LENGTH_SHORT).show();
            }
        }

        public void setWeather() {
            ImageView Iweather = (ImageView) getActivity().findViewById(R.id.weather_icon);
            // Tweather.setText(data);                                    //이곳은 텍스트를
            TextView weather_status = (TextView) getActivity().findViewById(R.id.weather_status);

            if (shortWeathers.get(0).getWfKor().equals("맑음")) {                        //이곳은 아이콘을
                Iweather.setImageResource(R.drawable.clear);
            } else if (shortWeathers.get(0).getWfKor().equals("흐림")) {
                Iweather.setImageResource(R.drawable.cloud);
            } else if (shortWeathers.get(0).getWfKor().equals("구름 많음")) {
                Iweather.setImageResource(R.drawable.most_cloudy);
            }
            else if (shortWeathers.get(0).getWfKor().equals("구름 조금")) {
                Iweather.setImageResource(R.drawable.cloud);
            } else if (shortWeathers.get(0).getWfKor().equals("눈")) {
                Iweather.setImageResource(R.drawable.snow);
            } else if (shortWeathers.get(0).getWfKor().equals("비")) {
                Iweather.setImageResource(R.drawable.rainy);
            } else if (shortWeathers.get(0).getWfKor().equals("눈/비")) {
                Iweather.setImageResource(R.drawable.snow_rain);
            } else {
                Iweather.setImageResource(R.drawable.base_icon);
            }
        }
        void parseXML(String xml) {
            try {
                String tagName = "";
                boolean onHour = false;
                boolean onDay = false;
                boolean onTem = false;
                boolean onWfKor = false;
                boolean onPop = false;
                boolean onEnd = false;
                boolean onReh = false;
                boolean isItemTag1 = false;
                int i = 0;

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(new StringReader(xml));

                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        tagName = parser.getName();
                        if (tagName.equals("data")) {
                            shortWeathers.add(new ShortWeather());
                            onEnd = false;
                            isItemTag1 = true;
                        }
                    } else if (eventType == XmlPullParser.TEXT && isItemTag1) {
                        if (tagName.equals("hour") && !onHour) {
                            shortWeathers.get(i).setHour(parser.getText());
                            onHour = true;
                        }
                        if (tagName.equals("day") && !onDay) {
                            shortWeathers.get(i).setDay(parser.getText());
                            onDay = true;
                        }
                        if (tagName.equals("temp") && !onTem) {
                            shortWeathers.get(i).setTemp(parser.getText());
                            onTem = true;
                        }
                        if (tagName.equals("wfKor") && !onWfKor) {
                            shortWeathers.get(i).setWfKor(parser.getText());
                            onWfKor = true;
                        }
                        if (tagName.equals("pop") && !onPop) {
                            shortWeathers.get(i).setPop(parser.getText());
                            onPop = true;
                        }
                        if (tagName.equals("reh") && !onReh) {
                            shortWeathers.get(i).setReh(parser.getText());
                            onReh = true;
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (tagName.equals("s06") && onEnd == false) {
                            i++;
                            onHour = false;
                            onDay = false;
                            onTem = false;
                            onWfKor = false;
                            onPop = false;
                            onReh = false;
                            isItemTag1 = false;
                            onEnd = true;
                        }
                    }

                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "네트워크 연결이 불안정합니다", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
