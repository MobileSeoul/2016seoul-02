package com.runfive.hangangrunner.Main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.runfive.calendarlib.FlexibleCalendarView;
import com.runfive.calendarlib.entity.CalendarEvent;
import com.runfive.calendarlib.view.BaseCellView;
import com.runfive.calendarlib.view.SquareCellView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

/**
 * Created by JunHo on 2016-08-07.
 */

public class MonthlyFragment extends Fragment implements
        FlexibleCalendarView.OnDateClickListener {

    private FlexibleCalendarView calendarView;
    private TextView monthTextView;
    private TextView daily_distance;
    private TextView daily_time;
    private TextView daily_kcal;
    private TextView count_medal_gold;
    private TextView count_medal_silver;
    private TextView count_medal_bronze;
    private Hashtable attendanceTable;
    private String user_id;
    private String StringDate;

    public MonthlyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        //  id 가져오기
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("id", null);

        View view = inflater.inflate(R.layout.main_monthly_fragment, container, false);

        // 달력
        calendarView = (FlexibleCalendarView) view.findViewById(R.id.calendar_view);

        // 거리
        daily_distance = (TextView) view.findViewById(R.id.daily_distance);
        //시간
        daily_time = (TextView) view.findViewById(R.id.daily_time);

        // 칼로리
        daily_kcal = (TextView) view.findViewById(R.id.daily_kcal);

        //금메달 수
        count_medal_gold = (TextView) view.findViewById(R.id.count_medal_gold);

        //은메달 수
        count_medal_silver = (TextView) view.findViewById(R.id.count_medal_silver);

        //동메달 수
        count_medal_bronze = (TextView) view.findViewById(R.id.count_medal_bronze);

        // 이전달 화살표
        ImageView leftArrow = (ImageView) view.findViewById(R.id.main_left_arrow);
        // 다음달 화살표
        ImageView rightArrow = (ImageView) view.findViewById(R.id.main_right_arrow);

        monthTextView = (TextView) view.findViewById(R.id.month_text_view);

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.moveToPreviousMonth();
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.moveToNextMonth();
            }
        });

        Calendar cal = Calendar.getInstance();
        cal.set(calendarView.getSelectedDateItem().getYear(), calendarView.getSelectedDateItem().getMonth(), 1);
        monthTextView.setText(cal.getDisplayName(Calendar.MONTH,
                Calendar.LONG, Locale.ENGLISH) + " " + calendarView.getSelectedDateItem().getYear());
        calendarView.setOnMonthChangeListener(new FlexibleCalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month, @FlexibleCalendarView.Direction int direction) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, Calendar.DAY_OF_MONTH);
                monthTextView.setText(cal.getDisplayName(Calendar.MONTH,
                        Calendar.LONG, Locale.ENGLISH) + " " + year);

            }
        });
        calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, @BaseCellView.CellType int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar1_date_cell_view, null);
                }
                if (cellType == BaseCellView.OUTSIDE_MONTH) {
                    cellView.setTextColor(getResources().getColor(R.color.date_outside_month_text_color_activity_1));
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    cellView = (SquareCellView) inflater.inflate(R.layout.calendar1_week_cell_view, null);
                }
                return cellView;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return String.valueOf(defaultValue.charAt(0));
            }
        });


        calendarView.setOnDateClickListener(this);


        Button goToCurrentDayBtn = (Button) view.findViewById(R.id.go_to_current_day);


        goToCurrentDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();

                calendarView.goToCurrentDay();
                onDateClick(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            }
        });




        /* 전달 다음달 숨겨진 날짜  보기
        Button showDatesOutSideMonthBtn = (Button) view.findViewById(R.id.show_dates_outside_month);
        showDatesOutSideMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView.getShowDatesOutsideMonth()) {
                    calendarView.setShowDatesOutsideMonth(false);
                    ((Button) v).setText("Show dates outside month");
                } else {
                    ((Button) v).setText("Hide dates outside month");
                    calendarView.setShowDatesOutsideMonth(true);
                }

            }
        });
        */
        showAttendance();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onDateClick(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        if ((month + 1) < 10) {
            if (day < 10) {
                StringDate = year + "-0" + (month + 1) + "-0" + day;

            } else {
                StringDate = year + "-0" + (month + 1) + "-" + day;

            }
        } else {
            if (day < 10) {
                StringDate = year + "-" + (month + 1) + "-0" + day;

            } else {
                StringDate = year + "-" + (month + 1) + "-" + day;

            }
        }


        Toast.makeText(getActivity(), StringDate, Toast.LENGTH_SHORT).show();

        if (attendanceTable.get(StringDate + "distance") != null && attendanceTable.get(StringDate + "time") != null) {
            daily_distance.setText((String) attendanceTable.get(StringDate + "distance") + " Km");
            daily_time.setText((String) attendanceTable.get(StringDate + "time"));
            daily_kcal.setText((String) attendanceTable.get(StringDate + "kcal")+" Kcal");
            count_medal_gold.setText((String) attendanceTable.get(StringDate + "gold"));
            count_medal_silver.setText((String) attendanceTable.get(StringDate + "silver"));
            count_medal_bronze.setText((String) attendanceTable.get(StringDate + "bronze"));
        } else {
            daily_distance.setText("0.0 Km");
            daily_kcal.setText("0 Kcal");
            daily_time.setText("00:00:00");
            count_medal_gold.setText("0");
            count_medal_silver.setText("0");
            count_medal_bronze.setText("0");
        }

    }

    private void showAttendance() {
        class MonthlyPHPRequest extends AsyncTask<String, Void, String> {
            private int resultYear;
            private int resultMonth;
            private int resultDay;
            private String countGold;
            private String countSilver;
            private String countBronze;
            private String dailyDistance;
            private String dailyTime;
            private String dailyKcal;
            private String resultDate;
            private URL url;
            private int resultLength = 0;
            private ProgressDialog dialog;

            public MonthlyPHPRequest(String url) throws MalformedURLException {
                this.url = new URL(url);
                dialog = new ProgressDialog(getContext());
            }

            private String readStream(InputStream in) throws IOException {
                StringBuilder jsonHtml = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line = null;

                while ((line = reader.readLine()) != null)
                    jsonHtml.append(line);

                reader.close();
                return jsonHtml.toString();
            }

            @Override
            protected void onPreExecute() {
                // 로딩화면띄우기
                Log.v("ProgressTask", "start on pre execute");
                this.dialog.setMessage("데이터 불러오는중...");
                this.dialog.show();
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
                    attendanceTable = new Hashtable();
                    resultLength = results.length();
                    for (int i = 0; i < resultLength; ++i) {
                        JSONObject temp = results.getJSONObject(i);
                        resultDate = temp.getString("date");
                        resultYear = Integer.parseInt(temp.getString("date").substring(0, 4));
                        attendanceTable.put("resultYear" + i, resultYear);
                        resultMonth = Integer.parseInt(temp.getString("date").substring(5, 7));
                        attendanceTable.put("resultMonth" + i, resultMonth - 1);

                        resultDay = Integer.parseInt(temp.getString("date").substring(8, 10));
                        attendanceTable.put("resultDay" + i, resultDay);
//                        거리
                        dailyDistance = temp.getString("distance");
                        attendanceTable.put(resultDate + "distance", dailyDistance);
//                      시간
                        dailyTime = temp.getString("time");
                        attendanceTable.put(resultDate + "time", dailyTime);
//                      칼로리
                        dailyKcal = temp.getString("kcal");
                        attendanceTable.put(resultDate + "kcal", dailyKcal);
//                      금메달 개수
                        countGold = temp.getString("gold");
                        attendanceTable.put(resultDate + "gold", countGold);
//                      은메달 개수
                        countSilver = temp.getString("silver");
                        attendanceTable.put(resultDate + "silver", countSilver);
//                      동메달 개수
                        countBronze = temp.getString("bronze");
                        attendanceTable.put(resultDate + "bronze", countBronze);

                    }
                    calendarView.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
                        @Override
                        public List<CalendarEvent> getEventsForTheDay(int year, int month, int day) {
                            for (int i = 0; i < resultLength; i++) {
                                if (attendanceTable != null && year == (int) attendanceTable.get("resultYear" + i)
                                        && month == (int) attendanceTable.get("resultMonth" + i) && day == (int) attendanceTable.get("resultDay" + i)) {
                                    List<CalendarEvent> eventColors = new ArrayList<>(1);
                                    eventColors.add(new CalendarEvent(android.R.color.holo_red_light));
                                    return eventColors;
                                }
                            }

                            return null;
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "네트워크 연결이 불안정합니다", Toast.LENGTH_SHORT).show();
                }

                calendarView.moveToNextMonth();
                calendarView.moveToNextMonth();
                calendarView.goToCurrentDay();
                Calendar cal = Calendar.getInstance();
                onDateClick(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

//                로딩화면 종료
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

        }

        MonthlyPHPRequest montlyPHPRequest = null;
        try {
            montlyPHPRequest = new MonthlyPHPRequest("Your Server ULR");
            montlyPHPRequest.execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "네트워크 연결이 불안정합니다", Toast.LENGTH_SHORT).show();
        }
    }


}