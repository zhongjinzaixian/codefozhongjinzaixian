package com.cnjy99.cyq.preferences;

import android.content.Context;

import com.cnjy99.cyq.MyApplication;
import com.cnjy99.cyq.utils.SpBase;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class AppStartedSp extends SpBase {

    private static AppStartedSp mSelf = null;

    public AppStartedSp(Context context, String spName) {
        super(context, spName);
    }

    public static AppStartedSp getInstatnce() {
        if(mSelf == null) {
            mSelf = new AppStartedSp(MyApplication.getContext(), "sp_myfile");
        }
        return mSelf;
    }
}
