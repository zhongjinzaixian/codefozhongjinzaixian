package com.cnjy99.cyq.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnjy99.cyq.MyApplication;
import com.cnjy99.cyq.R;
import com.cnjy99.cyq.utils.LogUtil;
import com.cnjy99.cyq.utils.NetRequestUtil;
import com.lidroid.xutils.HttpUtils;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public abstract class MyBaseActivity extends AppCompatActivity{

    public HttpUtils http;
    public Activity activity;
    public MyApplication myApplication;
    public NetRequestUtil netRequestUtil;
    public ImageView returnImageView;
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        http = new HttpUtils(50000);
        netRequestUtil = new NetRequestUtil(activity);
        myApplication = MyApplication.getInstance();
        getWindow().setBackgroundDrawable(null);
        setContentView();
        initView();
        initValue();
        initEvent();
    }
    /**
     * 加载布局文件
     */
    public abstract void setContentView();
    /**
     * 初始化布局文件
     */
    public abstract void initView();
    /**
     * 视图数据初始化
     */
    public abstract void initValue();
    /**
     * 初始化事件
     */
    public abstract void initEvent();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 释放资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("----------onDestroy--------------");
        netRequestUtil = null;
      // myApplication.getActivityManager().popActivity(this);
    }

    public void initTiTle(){
        returnImageView = (ImageView)findViewById(R.id.returnImageView);
        textView = (TextView)findViewById(R.id.titleText);
    }
}
