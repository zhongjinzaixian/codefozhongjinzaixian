package com.cnjy99.cyq.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class MyActivityManager {

    private static Stack<Activity> activityStack;
    private static MyActivityManager instance;

    private MyActivityManager(){
    }

    public static MyActivityManager getScreenManager(){
        if (instance == null){
            instance = new MyActivityManager();
        }
        return instance;
    }

    /**
     *退出栈顶Activity
     * @param activity
     */
    public void popActivity(Activity activity){
        LogUtil.e("ActivityManager", "popActivity: " + activity.getClass().getName());
        if(activity != null){
            //在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }
    /**
     * 获得当前栈顶的Activity
     */
    public Activity currentActivity(){
        Activity activity = null;
        if (!activityStack.empty()){
            activity = activityStack.lastElement();
        }
        return activity;
    }
    /**
     * 将当前Activity压入栈中
     * @param activity
     */
    public void pushActivity(Activity activity){
        if (activityStack == null){
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }
    /**
     * 退出栈中除了HomeActivity之外的所有Activity
     * @param cls
     */
    public void popAllActivityExceptOne(Class cls){
        while (true){
            Activity activity = currentActivity();
            if (activity == null){
                break;
            }
            //if (activity.getClass().equals(cls) || activity instanceof HomeActivity) {  //当前页和首页除外
            //      break;
            // }
            popActivity(activity);
        }
    }
    /**
     * 退出栈中所有的Activity
     */
    public void popAllActivityExceptOne(){
        while (true){
            Activity activity = currentActivity();
            if (activity == null){
                break;
            }
            popActivity(activity);
        }
    }
    /**
     * 退出栈中除了当前的 activity
     * @param cls
     */
    public void popAllActivityExceptnow(Class cls){
        while (true){
            Activity activity = currentActivity();
            if (activity == null){
                break;
            }
            if (activity.getClass().equals(cls)){ //当前页和首页除外
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 退出栈中所有的Activity
     * @param cls
     * @param isPopCls
     */
    public boolean popAllActivityExceptOne(Class cls,boolean isPopCls){
        boolean isPopActivity = false;
        while (true){
            Activity activity = currentActivity();
            if (activity == null){
                break;
            }
            if (activity.getClass().equals(cls)){
                if (isPopCls) popActivity(activity);
                break;
            }
            popActivity(activity);
            isPopActivity = true;
        }
        return isPopActivity;
    }

}
