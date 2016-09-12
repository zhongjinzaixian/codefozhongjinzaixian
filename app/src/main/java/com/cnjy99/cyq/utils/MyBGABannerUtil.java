package com.cnjy99.cyq.utils;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class MyBGABannerUtil {

    public MyBGABannerUtil(){

    }

    public static List<View> getItemImageView(Context context,List<String> urls){
        return getItemImageView(context,urls, ImageView.ScaleType.CENTER_CROP);
    }

    public static List<View> getItemImageView(Context context, List<String> urls, ImageView.ScaleType scaleType){
        List<View> views = new ArrayList<>();
        ImageView imageView = null;
        for (int i = 0;i<urls.size();i++){
            imageView = new ImageView(context);
            Glide.with(context).load(urls.get(i)).into(imageView);
            imageView.setClickable(true);
            imageView.setScaleType(scaleType);
            views.add(imageView);
        }
        return views;
    }
}
