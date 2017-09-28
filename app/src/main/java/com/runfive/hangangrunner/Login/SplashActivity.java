package com.runfive.hangangrunner.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.runfive.hangangrunner.R;

/**
 * Created by JunHo on 2016-08-08.
 */
public class SplashActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        Handler hd = new Handler();
        hd.postDelayed(new splashHandler(), 3000);
    }

    private class splashHandler implements Runnable {
        @Override
        public void run() {
            startActivity(new Intent(getApplication(),LoginActivity.class));
            finish();
        }
    }
}
