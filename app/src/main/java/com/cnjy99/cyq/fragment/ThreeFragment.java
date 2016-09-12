package com.cnjy99.cyq.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.cnjy99.cyq.R;
import com.cnjy99.cyq.adapter.CommonAdapter;
import com.cnjy99.cyq.utils.ImageUrls;
import com.cnjy99.cyq.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 极藏专访
 */
public class ThreeFragment extends BaseFragmet {

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
        return view;
    }

    public void initView(View view){
        accessListViewAdapter = new AccessListViewAdapter(getContext(), ImageUrls.getData(),R.layout.item_three);
        accessListView = (ListView)view.findViewById(R.id.accessListView);
        accessListView.setAdapter(accessListViewAdapter);
        accessListViewAdapter.notifyDataSetChanged();
        accessSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.accessSwipeRefreshLayout);
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
