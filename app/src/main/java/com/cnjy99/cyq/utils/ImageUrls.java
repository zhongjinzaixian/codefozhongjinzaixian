package com.cnjy99.cyq.utils;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class ImageUrls {

    private static String[] imgArray = new String[]{
            "http://img.taopic.com/uploads/allimg/140720/240467-140H00K62786.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3603943369,1952417318&fm=206&gp=0.jpg",
            "http://image.tianjimedia.com/uploadImages/2012/011/R5J8A0HYL5YV.jpg",
            "http://tupian.enterdesk.com/2013/mxy/12/10/15/3.jpg",
            "http://imgstore.cdn.sogou.com/app/a/100540002/714860.jpg",
    };

    //测试图片地址
    private static String[] picArray=new String[]{
            "http://img.taopic.com/uploads/allimg/110316/292-110316121G647.jpg",
            "http://pic4.nipic.com/20091215/2396136_140959028451_2.jpg",
            "http://pic24.nipic.com/20121003/10754047_140022530392_2.jpg",
            "http://img.taopic.com/uploads/allimg/140326/235113-1403260I33562.jpg",
            "http://img.taopic.com/uploads/allimg/140720/240467-140H00K62786.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3603943369,1952417318&fm=206&gp=0.jpg",
            "http://image.tianjimedia.com/uploadImages/2012/011/R5J8A0HYL5YV.jpg",
            "http://tupian.enterdesk.com/2013/mxy/12/10/15/3.jpg",
            "http://imgstore.cdn.sogou.com/app/a/100540002/714860.jpg",};

    public static List<String> getData(){
        List<String> list = new ArrayList<>();
        for(int i = 0; i < imgArray.length ; i++){
            list.add(imgArray[i]);
        }
        return list;
    }

    public static void setData(BGABanner bgaBanner, Context context){
        List<String> urls = new ArrayList<>();
        for (int i = 0;i<imgArray.length;i++){
            urls.add(imgArray[i]);
        }
        List<View> views = MyBGABannerUtil.getItemImageView(context,urls);

        bgaBanner.setData(views);
    }

    public static List<String> getRandomData(){
        Random random = new Random();
        int count = random.nextInt(10);
        LogUtil.d(""+count);
        List<String> picList = new ArrayList<>();
        for (int i = 0;i<count;i++){
            picList.add(picArray[i]);
        }
        return  picList;
    }
}
