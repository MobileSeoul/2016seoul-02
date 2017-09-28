package com.runfive.hangangrunner.Common;

import android.annotation.SuppressLint;
import android.os.StrictMode;

/**
 * Created by JunHo on 2016-08-22.
 */
public class NetworkUtil {
    @SuppressLint("NewApi")
    static public void setNetworkPolicy() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
}
