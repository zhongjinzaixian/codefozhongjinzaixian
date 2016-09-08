package com.cnjy99.cyq.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cnjy99.cyq.R;
import com.cnjy99.cyq.utils.ResultInterface;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 *  极藏社区
 */
public class FirstFragment extends BaseFragmet implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private boolean isPrepared; //标志位，标志已经初始化完成


    public FirstFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        initView(view);
        return view;
    }

    /**
     *  设置刷新动画的触发回调
     */
    public void initView(View view){
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayoutFirst);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewFirst);
        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        refreshLayout.setProgressViewOffset(true,50,200);
        //设置下拉圆圈的大小,两个值 LARGE， DEFAULT
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        //设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        refreshLayout.setColorSchemeResources( android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置下拉圆圈的背景颜色
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.RED);
        //下拉手势的监听
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        Toast.makeText(getContext(),"123",Toast.LENGTH_SHORT).show();
        refreshLayout.setEnabled(false);

    }
}
