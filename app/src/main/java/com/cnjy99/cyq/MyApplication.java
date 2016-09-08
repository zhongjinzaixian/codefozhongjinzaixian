package com.cnjy99.cyq;

import android.app.Application;
import android.content.Context;

import com.cnjy99.cyq.utils.MyActivityManager;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class MyApplication extends Application {

    public static Context mContext = null;
    private static MyActivityManager myActivityManager = null;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        myActivityManager = MyActivityManager.getScreenManager();
        instance = this;
    }

    public static MyApplication getInstance(){
        return instance;
    }

    public static Context getContext(){
        return mContext;
    }

    public MyActivityManager getActivityManager(){
        return myActivityManager;
    }

    public void setActivityManager(MyActivityManager activityManager){
        this.myActivityManager = activityManager;
    }
}
