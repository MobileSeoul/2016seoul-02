package com.runfive.hangangrunner.Ranking;

//import android.app.Fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.runfive.hangangrunner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by JunHo on 2016-08-02.
 */
public class RankingPointFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_one";
    //section_number

    private ListView listView1;
    private RankingAdapter adapter;
    private String[] username;
    private String[] totalPoint;
    private View view;
    private String[] imagepath;


    public RankingPointFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.ranking_point_fragment,null);

        // 리스트뷰 객체 참조 프래그먼트여서 인플레이션해야함 뷰객체 위에서 받아옴

        showRankingByPoint();

        // 새로 정의한 리스너로 객체를 만들어 설정 (그냥 확인차 보여주는것 토스트메세지로)


        return  view;
    }

    private void showRankingByPoint() {

        class RankingPHPRequest extends AsyncTask<String, Void, String> {

            private URL url;
            public RankingPHPRequest(String url) throws MalformedURLException {
                this.url=new URL(url);
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

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(5000);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

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
                Bitmap bitmap;
                listView1 = (ListView) view.findViewById (R.id.ranking_point_listview);

                adapter = new RankingAdapter(getActivity().getBaseContext(),1);
                // 액티비티는 컨텍스트참조되는데 fragment여서 액티비티를 가져온후 컨택스트참조를한다

                Resources res = getResources();

                // 리스트뷰에 어댑터 설정
                listView1.setAdapter(adapter);

                super.onPostExecute(s);

                try {
                    JSONObject jObject = new JSONObject(s);
                    JSONArray results = jObject.getJSONArray("result");
                    username = new String[results.length()];
                    totalPoint = new String[results.length()];
                    imagepath = new String[results.length()];

                    for ( int i = 0; i < results.length(); ++i ) {
                        JSONObject temp = results.getJSONObject(i);
                        username[i] = temp.getString("name");
                        totalPoint[i] = temp.getString("sum(user_point)");
                        imagepath[i] = temp.getString("imagepath");
                    }

                    for(int i=0; i<results.length(); ++i) {
                        try {

                            URL url = new URL(imagepath[i]);
                            URLConnection conn = url.openConnection();
                            conn.connect();
                            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                            bitmap = BitmapFactory.decodeStream(bis);
                            bis.close();

                            adapter.addItem(new RankingObject(bitmap, String.valueOf(i+1), username[i], totalPoint[i]));

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "네트워크 연결이 불안정합니다",Toast.LENGTH_LONG);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "네트워크 연결이 불안정합니다",Toast.LENGTH_LONG);
                }

                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        RankingObject curItem = (RankingObject) adapter.getItem(position);
                        String[] curData = curItem.getData();


                    }

                });
            }
        }

        RankingPHPRequest rankingPHPRequest = null;
        try {
            rankingPHPRequest = new RankingPHPRequest("Your Server ULR");
            rankingPHPRequest.execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),
                    "네트워크 연결이 불안정합니다",Toast.LENGTH_LONG);
        }
    }

}
