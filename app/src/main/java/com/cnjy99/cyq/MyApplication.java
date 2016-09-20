package com.cnjy99.cyq;

import android.app.Application;
import android.content.Context;

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

    @Override
    public void onCreate() {
        super.onCreate();

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证,true:自动验证,false,手动验证
        options.setAcceptInvitationAlways(true);
        //初始化
        EaseUI.getInstance().init(this,options);
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

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
