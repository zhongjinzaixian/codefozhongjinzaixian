package com.cnjy99.cyq.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cnjy99.cyq.R;
import com.cnjy99.cyq.adapter.CommonAdapter;
import com.cnjy99.cyq.utils.ImageUrls;
import com.cnjy99.cyq.utils.ViewHolder;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 极藏专访
 */
public class ThreeFragment extends BaseFragmet implements View.OnClickListener{


    private TextView people_access;
    private TextView brand_access;
    private TextView hidden_access;
    private SwipeRefreshLayout accessSwipeRefreshLayout;
    private ListView accessListView;

    private AccessListViewAdapter accessListViewAdapter;

    public ThreeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        initView(view);
        clickListener();
        return view;
    }

    public void initView(View view){
        people_access =(TextView) view.findViewById(R.id.people_access);
        people_access.setTextColor(ContextCompat.getColor(getContext(),R.color.access_text_color_sel));
        brand_access =(TextView) view.findViewById(R.id.brand_access);
        hidden_access =(TextView) view.findViewById(R.id.hidden_access);
        accessListViewAdapter = new AccessListViewAdapter(getContext(), ImageUrls.getData(),R.layout.item_three);
        accessListView = (ListView)view.findViewById(R.id.accessListView);
        accessListView.setAdapter(accessListViewAdapter);
        accessListViewAdapter.notifyDataSetChanged();
        accessSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.accessSwipeRefreshLayout);
    }

    public void clickListener(){
        people_access.setOnClickListener(this);
        brand_access.setOnClickListener(this);
        hidden_access.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.people_access:
                people_access.setTextColor(ContextCompat.getColor(getContext(),R.color.access_text_color_sel));
                brand_access.setTextColor(ContextCompat.getColor(getContext(),R.color.access_text_color_nor));
                hidden_access.setTextColor(ContextCompat.getColor(getContext(),R.color.access_text_color_nor));
                break;
            case R.id.brand_access:
                people_access.setTextColor(ContextCompat.getColor(getContext(),R.color.access_text_color_nor));
                brand_access.setTextColor(ContextCompat.getColor(getContext(),R.color.access_text_color_sel));
                hidden_access.setTextColor(ContextCompat.getColor(getContext(),R.color.access_text_color_nor));
                break;
            case R.id.hidden_access:
                people_access.setTextColor(ContextCompat.getColor(getContext(),R.color.access_text_color_nor));
                brand_access.setTextColor(ContextCompat.getColor(getContext(),R.color.access_text_color_nor));
                hidden_access.setTextColor(ContextCompat.getColor(getContext(),R.color.access_text_color_sel));             break;
        }
    }

    class AccessListViewAdapter extends CommonAdapter<String>{

        public AccessListViewAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            ImageView imageView =  helper.getView(R.id.access_imageView);
            Glide.with(getContext()).load(item).into(imageView);
        }
    }

}
