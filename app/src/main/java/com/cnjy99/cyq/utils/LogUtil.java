package com.cnjy99.cyq.utils;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LogUtil
{

    public static final String LOG_TAG = "cyq";
    public static boolean DEBUG = true;
    public static boolean FILE_DEBUG = false;

    public static void d(String log)
    {
        if (DEBUG)
        {
            d(LOG_TAG, log);
        }
    }

    public static void e(String log)
    {
        if (DEBUG)
        {
            e(LOG_TAG, log);
        }
    }

    public static void i(String log)
    {
        if (DEBUG)
        {
            i(LOG_TAG, log);
        }
    }

    public static void v(String log)
    {
        if (DEBUG)
        {
            v(LOG_TAG, log);
        }
    }

    public static void w(String log)
    {
        if (DEBUG)
        {
            w(LOG_TAG, log);
        }
    }

    public static void i(String tag, String log)
    {
        if (DEBUG)
        {
            Log.i(tag, log);
        }
    }

    public static void v(String tag, String log)
    {
        if (DEBUG)
        {
            Log.v(tag, log);
        }
    }

    public static void d(String tag, String log)
    {
        if (DEBUG)
        {
            Log.d(tag, log);
        }
    }

    public static void w(String tag, String log)
    {
        if (DEBUG)
        {
            Log.w(tag, log);
        }
    }

    public static void e(String tag, String log)
    {
        if (DEBUG)
        {
            Log.e(tag, log);
        }
    }

    public static void toastMsg(Context context,String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
