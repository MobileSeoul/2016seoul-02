package com.runfive.hangangrunner.Main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.runfive.hangangrunner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JunHo on 2016-08-07.
 */
public class WeeklyFragment extends Fragment {

    ListView weeklyListView;
    //private ArrayAdapter<String> arrayAdapter;
    private WeeklyAdapter weeklyAdapter;



    private TextView[] weeklyDate = new TextView[7];
    private TextView[] weeklyDay = new TextView[7];
    private TextView[] weeklyKm = new TextView[7];
    private TextView[] weeklyPoint = new TextView[7];
    private TextView[] weeklyGold = new TextView[7];
    private TextView[] weeklySilver = new TextView[7];
    private TextView[] weeklyBronze = new TextView[7];

    private String[] weeklyDateStr;
    private String[] weeklyDayStr;
    private String[] weeklyKmStr;
    private String[] weeklyPointStr;
    private String[] weeklyGoldStr;
    private String[] weeklySilverStr;
    private String[] weeklyBronzeStr;
    private String[] number; // 어디에 붙일지

    private String userId;
    private View[] subLayout = new View[7];
    private String day[] = {"일", "월", "화", "수", "목", "금","토"};
    private int dayCount = 0;
    private String dayStart; // 서버에서 받는 날짜 시작일
    private Date date;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public WeeklyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_weekly_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        userId = sharedPreferences.getString("id", null);

        LinearLayout linearLayoutControl = (LinearLayout) getActivity().findViewById(R.id.weekly_control_view); // 부모 뷰
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 인플레이터획득

        //int parentHeight = linearLayoutControl.getHeight();

        try {
            WeeklyRecordRequest weeklyRecordRequest = new WeeklyRecordRequest("Your Server ULR");
            weeklyRecordRequest.execute(userId);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "네트워크 연결이 불안정합니다", Toast.LENGTH_SHORT).show();
        }

        for(int i=0; i<7; i++) {

            subLayout[i] = inflater.inflate(R.layout.weeklylayout_onecol, null); // 인플레이트하지만 아직 부모뷰에 붙지는 않음
            subLayout[i].setPadding(50,40,10,0);

            weeklyDate[i] = (TextView) subLayout[i].findViewById(R.id.weekly_date); // 서브에 있는 뷰 변경
            weeklyDay[i] = (TextView) subLayout[i].findViewById(R.id.weekly_day);
            weeklyKm[i] = (TextView) subLayout[i].findViewById(R.id.main_weekly_km);
            weeklyPoint[i] = (TextView) subLayout[i].findViewById(R.id.main_weelky_point);
            weeklyGold[i] = (TextView) subLayout[i].findViewById(R.id.main_weekly_gold);
            weeklySilver[i] = (TextView) subLayout[i].findViewById(R.id.main_weekly_silver);
            weeklyBronze[i] = (TextView) subLayout[i].findViewById(R.id.main_weekly_bronze);

            weeklyDay[i].setText(day[dayCount++]); // 요일 설정

            DisplayMetrics dm = getActivity().getApplicationContext().getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            int height = dm.heightPixels;

            linearLayoutControl.addView(subLayout[i], width, height/9);
        }
    }

    private class WeeklyRecordRequest extends AsyncTask<String, Void, String> {
        private URL url;

        public WeeklyRecordRequest(String url) throws MalformedURLException {
            this.url = new URL(url);
            // this.user_id = user_id;
        }

        private String readStream(InputStream in) throws IOException {
            StringBuilder jsonHtml = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = null;

            while((line = reader.readLine()) != null)
                jsonHtml.append(line);

            reader.close();
            System.out.println("result=====================================>>>>>>>>>>>>>>"+jsonHtml.toString());
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

                conn.disconnect();

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
            System.out.print("스트링"+s);
            try {
                JSONObject jObject = new JSONObject(s);
                JSONArray results = jObject.getJSONArray("result");

                JSONObject lengthCheck = results.getJSONObject(0);

                if(lengthCheck.length() != 1) {

                    weeklyDateStr = new String[results.length()];
                    weeklyDayStr = new String[results.length()];
                    weeklyKmStr = new String[results.length()];
                    weeklyPointStr = new String[results.length()];
                    weeklyGoldStr = new String[results.length()];
                    weeklySilverStr = new String[results.length()];
                    weeklyBronzeStr = new String[results.length()];
                    number = new String[results.length()];

                    for ( int i = 0; i < results.length(); i++ ) {

                        JSONObject temp = results.getJSONObject(i);
                        //System.out.println(temp.length()+"json길이");
                        weeklyDateStr[i] = temp.getString("date");
                        weeklyKmStr[i] = temp.getString("distance");
                        weeklyPointStr[i] = temp.getString("point");
                        weeklyGoldStr[i] = temp.getString("gold");
                        weeklySilverStr[i] = temp.getString("silver");
                        weeklyBronzeStr[i] = temp.getString("bronze");
                        weeklyDayStr[i] = temp.getString("day");
                        number[i] = temp.getString("number");
                        dayStart = temp.getString("day_start");
                    }

                    try {

                        date = dateFormat.parse(dayStart);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);

                        for(int i=0; i<7; i++) {
                            String dateString = dateFormat.format(date);

                            Date d = calendar.getTime();
                            dateString = dateFormat.format(d);
                            String month = dateString.substring(5,7);
                            String day = dateString.substring(8,10);
                            String dateStringParsed = month.concat("/"+day);
                            weeklyDate[i].setText(dateStringParsed);
                            calendar.add(Calendar.DATE,1);
                            weeklySilver[i].setText("0");
                            weeklyGold[i].setText("0");
                            weeklyBronze[i].setText("0");
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "네트워크 연결이 불안정합니다", Toast.LENGTH_SHORT).show();
                    }

                    for(int i=0; i<results.length(); i++) {
                        String month;
                        String day;
                        String dateStringParsed;

                        switch (Integer.parseInt(number[i])) {
                            case 0:
                                month = weeklyDateStr[i].substring(5,7);
                                day = weeklyDateStr[i].substring(8,10);
                                dateStringParsed = month.concat("/"+day);
                                weeklyDate[0].setText(dateStringParsed);
                                weeklyDay[0].setText(weeklyDayStr[i]);
                                weeklyKm[0].setText(weeklyKmStr[i]+"km");
                                weeklyPoint[0].setText(weeklyPointStr[i]+"point");
                                weeklyGold[0].setText(weeklyGoldStr[i]);
                                weeklySilver[0].setText(weeklySilverStr[i]);
                                weeklyBronze[0].setText(weeklyBronzeStr[i]);
                                break;

                            case 1:
                                month = weeklyDateStr[i].substring(5,7);
                                day = weeklyDateStr[i].substring(8,10);
                                dateStringParsed = month.concat("/"+day);
                                weeklyDate[1].setText(dateStringParsed);
                                weeklyDate[1].setText(weeklyDateStr[i].substring(5));
                                weeklyDay[1].setText(weeklyDayStr[i]);
                                weeklyKm[1].setText(weeklyKmStr[i]+"km");
                                weeklyPoint[1].setText(weeklyPointStr[i]+"point");
                                weeklyGold[1].setText(weeklyGoldStr[i]);
                                weeklySilver[1].setText(weeklySilverStr[i]);
                                weeklyBronze[1].setText(weeklyBronzeStr[i]);
                                break;

                            case 2:
                                month = weeklyDateStr[i].substring(5,7);
                                day = weeklyDateStr[i].substring(8,10);
                                dateStringParsed = month.concat("/"+day);
                                weeklyDate[2].setText(dateStringParsed);
                                weeklyDate[2].setText(weeklyDateStr[i].substring(5));
                                weeklyDay[2].setText(weeklyDayStr[i]);
                                weeklyKm[2].setText(weeklyKmStr[i]+"km");
                                weeklyPoint[2].setText(weeklyPointStr[i]+"point");
                                weeklyGold[2].setText(weeklyGoldStr[i]);
                                weeklySilver[2].setText(weeklySilverStr[i]);
                                weeklyBronze[2].setText(weeklyBronzeStr[i]);
                                break;

                            case 3:
                                month = weeklyDateStr[i].substring(5,7);
                                day = weeklyDateStr[i].substring(8,10);
                                dateStringParsed = month.concat("/"+day);
                                weeklyDate[3].setText(dateStringParsed);
                                weeklyDate[3].setText(weeklyDateStr[i].substring(5));
                                weeklyDay[3].setText(weeklyDayStr[i]);
                                weeklyKm[3].setText(weeklyKmStr[i]+"km");
                                weeklyPoint[3].setText(weeklyPointStr[i]+"point");
                                weeklyGold[3].setText(weeklyGoldStr[i]);
                                weeklySilver[3].setText(weeklySilverStr[i]);
                                weeklyBronze[3].setText(weeklyBronzeStr[i]);
                                break;

                            case 4:
                                month = weeklyDateStr[i].substring(5,7);
                                day = weeklyDateStr[i].substring(8,10);
                                dateStringParsed = month.concat("/"+day);
                                weeklyDate[4].setText(dateStringParsed);
                                weeklyDate[4].setText(weeklyDateStr[i].substring(5));
                                weeklyDay[4].setText(weeklyDayStr[i]);
                                weeklyKm[4].setText(weeklyKmStr[i]+"km");
                                weeklyPoint[4].setText(weeklyPointStr[i]+"point");
                                weeklyGold[4].setText(weeklyGoldStr[i]);
                                weeklySilver[4].setText(weeklySilverStr[i]);
                                weeklyBronze[4].setText(weeklyBronzeStr[i]);
                                break;

                            case 5:
                                month = weeklyDateStr[i].substring(5,7);
                                day = weeklyDateStr[i].substring(8,10);
                                dateStringParsed = month.concat("/"+day);
                                weeklyDate[5].setText(dateStringParsed);
                                weeklyDate[5].setText(weeklyDateStr[i].substring(5));
                                weeklyDay[5].setText(weeklyDayStr[i]);
                                weeklyKm[5].setText(weeklyKmStr[i]+"km");
                                weeklyPoint[5].setText(weeklyPointStr[i]+"point");
                                weeklyGold[5].setText(weeklyGoldStr[i]);
                                weeklySilver[5].setText(weeklySilverStr[i]);
                                weeklyBronze[5].setText(weeklyBronzeStr[i]);
                                break;

                            case 6:
                                month = weeklyDateStr[i].substring(5,7);
                                day = weeklyDateStr[i].substring(8,10);
                                dateStringParsed = month.concat("/"+day);
                                weeklyDate[6].setText(dateStringParsed);
                                weeklyDate[6].setText(weeklyDateStr[i].substring(5));
                                weeklyDay[6].setText(weeklyDayStr[i]);
                                weeklyKm[6].setText(weeklyKmStr[i]+"km");
                                weeklyPoint[6].setText(weeklyPointStr[i]+"point");
                                weeklyGold[6].setText(weeklyGoldStr[i]);
                                weeklySilver[6].setText(weeklySilverStr[i]);
                                weeklyBronze[6].setText(weeklyBronzeStr[i]);
                                break;
                        }
                    }
                } else {
                    JSONObject temp = results.getJSONObject(0);
                    dayStart = temp.getString("day_start");

                    try {
                        Date date = dateFormat.parse(dayStart);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);

                        for(int i=0; i<7; i++) {
                            String dateString = dateFormat.format(date);
                            Date d = calendar.getTime();
                            dateString = dateFormat.format(d);
                            String month = dateString.substring(5,7);
                            String day = dateString.substring(8,10);
                            String dateStringParsed = month.concat("/"+day);
                            weeklyDate[i].setText(dateStringParsed);

                            calendar.add(Calendar.DATE,1);

                            weeklySilver[i].setText("0");
                            weeklyGold[i].setText("0");
                            weeklyBronze[i].setText("0");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "네트워크 연결이 불안정합니다", Toast.LENGTH_SHORT).show();
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "네트워크 연결이 불안정합니다", Toast.LENGTH_SHORT).show();
            }

        }
    }
}

