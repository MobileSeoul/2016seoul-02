package com.runfive.hangangrunner.Running;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Yongjun on 2016-09-15.
 */
public class RunningPHPRequest extends AsyncTask<String, Void, String> {
    private URL url;
    private String time;
    public RunningPHPRequest(String url) throws MalformedURLException {
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
//            MYSQL에 TIME 타입으로 저장하기위한 데이터포맷팅
            time = params[4];
            String[] timeFormat = time.split(":");
            time = "";
            for(int j=0;j<timeFormat.length ;j++ ){
                time+=timeFormat[j];
            }
            for(int i=0; i<5; i++){
            }
            String postData = "user_id=" + params[0] + "&user_point=" + params[1] + "&medal=" + params[2] + "&distance=" + params[3]+ "&time=" + time + "&kcal=" + params[5];

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
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

    }

}
