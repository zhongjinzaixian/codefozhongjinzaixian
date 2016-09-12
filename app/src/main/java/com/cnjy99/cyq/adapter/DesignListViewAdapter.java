package com.cnjy99.cyq.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cnjy99.cyq.MyApplication;
import com.cnjy99.cyq.R;
import com.cnjy99.cyq.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class DesignListViewAdapter extends CommonAdapter<String> {

    public DesignListViewAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, String item) {
        ImageView designImageView = helper.getView(R.id.designImageView);
        Glide.with(MyApplication.getContext()).load(item).into(designImageView);
    }
}
