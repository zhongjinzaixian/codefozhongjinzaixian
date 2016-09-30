package com.cnjy99.cyq;

import android.app.Application;
import android.content.Context;

import com.cnjy99.cyq.easemob.helper.AppHelper;
import com.cnjy99.cyq.utils.MyActivityManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class MyApplication extends Application {

    public static Context mContext = null;
    private static MyActivityManager myActivityManager = null;
    private static MyApplication instance;
    /**
     * nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
     */
    public static String currentUserNick = "";
    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();
        myActivityManager = MyActivityManager.getScreenManager();
        instance = this;
        /**
         * 初始化AppHelper
         */
        AppHelper.getInstance().init(mContext);
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
