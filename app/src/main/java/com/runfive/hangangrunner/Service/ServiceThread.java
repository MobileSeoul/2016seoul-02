package com.runfive.hangangrunner.Service;

/**
 * Created by jinwo on 2016-08-20.
 */
import android.os.Handler;

public class ServiceThread extends Thread{
    Handler handler;
    boolean isRun = true;

    public ServiceThread(Handler handler){
        this.handler = handler;
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run(){
        //반복적으로 수행할 작업을 한다.

        if(isRun){
            handler.sendEmptyMessage(0);
            try{

            }catch(Exception e){}
        }


    }
}