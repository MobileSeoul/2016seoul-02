package com.runfive.hangangrunner.Record;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runfive.hangangrunner.R;
import com.runfive.hangangrunner.Record.chart.ChartView;
import com.runfive.hangangrunner.Record.chart.ChartViewConfig;
import com.runfive.hangangrunner.Record.chart.KeduValue;
import com.runfive.hangangrunner.Record.chart.PointValue;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

public class RecordCalorie extends Fragment {
    private String user_id;
    private ChartView chartview;
    private Calendar cal;
    private int dayNum; // 이번달 날짜
    private int today; // 오늘 날짜
    private Hashtable kcalTable;


    public RecordCalorie() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //  id 가져오기
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("id", null);


        //날짜 계산 가져오기
        cal = Calendar.getInstance();
//        오늘날짜구하기
        today = cal.get(Calendar.DATE);
//        이번달의 마지막 날짜
        dayNum = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        View rootView = inflater.inflate(R.layout.record_calorie_fragment, container, false);
        chartview = (ChartView) rootView.findViewById(R.id.kcal_chartview);


        showRecordByDistance();

        initChartView();

        return rootView;
    }

    private void initChartView() {
        float density = getResources().getDisplayMetrics().density;
        int px = getResources().getDisplayMetrics().widthPixels;
        int mScreenWidth = (int) (px / density);
        List<KeduValue> listHorizontal = getHorizontalKedu();

        List<PointValue> listPoint = getPoint();

        List<PointValue> listRegion = getRegion();

        config(mScreenWidth, listHorizontal, listPoint, listRegion);
    }

    private List<KeduValue> getHorizontalKedu() {
        List<KeduValue> list = new ArrayList<>();
        for (int i = 1; i <= dayNum; i++) {
            KeduValue pointValue = new KeduValue();
            pointValue.display_value = i + "일";// X축 날짜
            pointValue.value = i + "";
            list.add(pointValue);
        }
        return list;
    }

    // 들어갈 데이터값
    private List<PointValue> getPoint() {
        List<PointValue> list = new ArrayList<>();
        int temp = 1;
        for (int i = 1; i <= dayNum; i++) {
            PointValue pointValue = new PointValue();
            pointValue.horizontal_value = "" + i;
            if (kcalTable != null && kcalTable.get(i) != null) {
                pointValue.verical_value = "" + kcalTable.get(i);
            } else {
                pointValue.verical_value = "" + 0;
            }
           /* temp++;
            pointValue.verical_value=temp+"4";
            // 입시값
            if(temp==5){
                temp=1;
            }*/
            pointValue.title = pointValue.verical_value + "";
            pointValue.title_sub = "소모 칼로리";
            list.add(pointValue);
        }
        return list;
    }


    private List<PointValue> getRegion() {
        List<PointValue> list = new ArrayList<>();
        int temp = 5;
        for (int i = -5; i < 20; i++) {
            PointValue pointValue = new PointValue();
            pointValue.horizontal_value = i + "";
            pointValue.verical_value = 10 + "";
            list.add(pointValue);
        }

        for (int i = 30; i >= 0; i--) {
            PointValue pointValue = new PointValue();
            pointValue.horizontal_value = i + "1";
            pointValue.verical_value = 5 + "";
            list.add(pointValue);
        }
        return list;
    }


    private void config(int mScreenWidth, List<KeduValue> listHorizontal, List<PointValue> listPoint, List<PointValue> listPointRegion) {
        ChartViewConfig config = new ChartViewConfig();
        config
                // col 개수
                .setCloumn(dayNum)
                // row 개수
                .setRow(14)
                // 한 행 간격
                .setItem_height(90)
                // 한 열 간격
                .setItem_width(mScreenWidth / 2)
                // 행 점선 색
                .setGrid_line_color(Color.BLUE)
                // X축 색
                .setGrid_line_kedu_color(Color.BLUE)
                // 구분선 점선표시 여부
                .setIsShowGridLine(true)
                // 행 구분선 여부
                .setIsShowGridHorizontalLine(true)
                // 열 구분선 여부
                .setIsShowGridVericalLine(false)
                // 구분선 효과
                .setIsGridLinePathEffect(true)
                // 왼쪽 Y 축 margin
                .setVerical_kedu_leftmargin(mScreenWidth / 2)
                // Y축 Text
                .setVerical_unit_text("Kcal")
                // Y축 시작 기준
                .setVerical_unit_start(0)
                // Y 축 끝
                .setVerical_unit_end(82*40)
                // 몇씩 증가
                .setVerical_unit_incremetal(250)
                //
                .setVerical_lable_value_type(1)
                // 1의자리만 보여줌
                .setVerical_need_to_fragment(false)
                // km 색
                .setVerical_unit_color(R.color.colorPrimary)
                // 데이터 글자색
                .setVerical_unit_lable_color(R.color.colorPrimary)

                //.setVerical_unit_lable_sub_color(R.color.clear_red)
                // Y 축 선
                .setVerical_line_show(true)
                // Y축 표시 점선
                .setVerical_kedu_line_show(true)
                //
                .setListHorizontalKeduAndValueType(listHorizontal, 0, "1")
                // X축 표시 점선
                .setHorizontal_kedu_line_show(true)
                // 포인트 배경 표현 방식
                .setListPointRegion(listPoint)
                .setRegion_color(R.color.colorLightBlue)

                .setListPoint(listPoint)
                // 차트 연결선 곡선으로 부드럽게
                .setIsSmoothPoint(true)
                // 포인트 밑에 색으로 채우기
                .setIsFillPointRegion(true)
                .setRegion_connect_color(R.color.colorLightBlue)

                // 연결선 색
                .setPath_line_color(R.color.colorinsidecircle)
                // 포인트 원 안에 색
                .setPoint_circle_color_interval(R.color.colorinsidecircle)
                // 포인트 원 밖 색
                .setPoint_circle_color_outside(R.color.transparent)
                // 포인트 원 중심 색 채울건가
                .setIsPointCircleIntervalStoke(false)
                // 풍선 메인 타이틀
              //  .setIndicator_title_unit("kcal")
                // 풍선 색
                .setIndicator_Linecolor(R.color.colorPrimary)
                // 풍선 옮길때 겉 색
                // .setIndicator_outside_circle_color(R.color.clear_red)
                // 풍선 타이틀 글자 색
                .setIndicator_title_color(R.color.colorGray)
                // 풍선 그래프 높이 따라서 움직일 것인가
                .setIsIndicatorMoveWithPoint(true)
                //.setIndicatorBgRes(R.drawable.tree50)
                // 풍선 크기
                .setIndicator_radius(100)
                // 선택할 날짜 위치
                .setItemSelection(today - 1)
        ;
        // 셋팅하고 시작
        chartview.init(config);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////
    private void showRecordByDistance() {

        class RecordPHPRequest extends AsyncTask<String, Void, String> {
            private String day;
            private int dayInteger;
            private URL url;
            public RecordPHPRequest(String url) throws MalformedURLException {
                this.url = new URL(url);
            }

            private String readStream(InputStream in) throws IOException {
                StringBuilder jsonHtml = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line = null;

                while ((line = reader.readLine()) != null)
                    jsonHtml.append(line);

                reader.close();
                System.out.println(jsonHtml.toString() + "READING");
                return jsonHtml.toString();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String postData = "user_id=" + user_id;
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
                } catch (Exception e) {
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
                    kcalTable = new Hashtable();
                    System.out.println(results.toString());

                    for (int i = 0; i < results.length(); ++i) {
                        JSONObject temp = results.getJSONObject(i);
                        day = temp.getString("date");
                        day = day.substring(8, 10);
                        dayInteger = Integer.parseInt(day);
                        kcalTable.put(dayInteger, temp.getString("kcal"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                initChartView();

            }
        }

        RecordPHPRequest recordPHPRequest = null;
        try {
            recordPHPRequest = new RecordPHPRequest("Your Server ULR");
            recordPHPRequest.execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
