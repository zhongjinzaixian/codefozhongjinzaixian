package com.cnjy99.cyq.utils;

import android.content.Context;

import com.cnjy99.cyq.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class NativeArraysUtil {

    /**
     * 获取个人页面的帖子，集市，收藏数组
     * @param context
     * @return
     */
    public static List<String> getMagicIndicatorName(Context context){
        List<String> list = new ArrayList<>();
        String[] data = context.getResources().getStringArray(R.array.name_magicIndicator);
        for (int i = 0;i<data.length;i++){
            list.add(data[i]);
        }
        return list;
    }
}
