package com.cnjy99.cyq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cnjy99.cyq.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position,convertView,parent);
        convert(viewHolder,getItem(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder helper, T item);

    protected ViewHolder getViewHolder(int position,View convertView,ViewGroup parent){
        return ViewHolder.getViewHolder(mContext,convertView,parent,mItemLayoutId,position);
    }

    public void setDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void removeListItem(int position){
        mDatas.remove(position);
    }

    public void updateAdapter(List<T> mDatas){
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
}
