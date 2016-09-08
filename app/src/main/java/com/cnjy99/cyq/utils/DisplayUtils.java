package com.cnjy99.cyq.utils;

import android.content.Context;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class DisplayUtils {

    /**
     * 将px 转化成 dip 保证尺寸大小不变
     * @param context
     * @param pxValue
     * @return
     */
    public static int pxToDip(Context context,int pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale + 0.5f);
    }

    /**
     * 将dip 转化成 px  保证尺寸大小不变
     * @param context
     * @param dipValue
     * @return
     */
    public static int dipToPx(Context context,int dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(scale * dipValue + 0.5f);
    }

    /**
     * 将px 转化成 sp 保证文字大小不变
     * @param context
     * @param pxValue
     */
    public static int pxToSp(Context context,int pxValue){
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / scale +0.5f);
    }

    /**
     * 将sp 转化成 px 保证文字大小不变
     * @param context
     * @param spValue
     * @return
     */
    public static int spToPx(Context context,int spValue){
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * scale + 0.5f);
    }

}
