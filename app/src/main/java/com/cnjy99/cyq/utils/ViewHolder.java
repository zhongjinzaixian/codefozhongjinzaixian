package com.cnjy99.cyq.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, ViewGroup parent,int layoutId,int position){
            mContext = context;
            mViews = new SparseArray<>();
            mPosition = position;
            mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
            mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder getViewHolder(Context context,View convertView,ViewGroup parent,
                                           int layoutId,int position){
        ViewHolder holder = null;
        if (holder == null){
            holder = new ViewHolder(context,parent,layoutId,position);
        }else {
            holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
        }
        return holder;
    }

    public View getConvertView(){
        return mConvertView;
    }

    /**
     * 通过 viewId 获取对应的View控件，如果没有，则加入！
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View>T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            mViews.put(viewId,mConvertView.findViewById(viewId));
        }
        return (T)view;
    }

    /**
     * 为TextView设置字符串
     * @param viewId
     * @param value
     * @return
     */
    public ViewHolder setText(int viewId,String value){
        TextView textView = getView(viewId);
        textView.setText(value);
        return this;
    }

    /**
     * 为ImageView设置图片
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImageResource(int viewId,int resId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawble(int viewId, Drawable drawable){
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    /**
     * Glide加载图片库 默认Bitmap格式是RGB_565（图片质量稍差RGB_8888）
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageUrl(int viewId,String url){
        ImageView imageView = getView(viewId);
        Glide.with(mContext).
                load(url).
                into(imageView);
       return this;
    }

}
