package com.cnjy99.cyq.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cnjy99.cyq.R;
import com.cnjy99.cyq.utils.DisplayUtils;
import com.cnjy99.cyq.utils.ImageUrls;
import com.cnjy99.cyq.utils.ViewHolder;
import com.cnjy99.cyq.view.NestedScrollGridView;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class TypeAdapter extends BaseAdapter {

    protected Context context;
    protected LayoutInflater mInflater;
    protected List<String> listData;
    protected final int mItemLayoutId;
    private int mGridColumnSize;

    public TypeAdapter(Context context,List<String> listData,int mItemLayoutId) {
        this.context = context;
        this.listData = listData;
        this.mItemLayoutId = mItemLayoutId;
        this.mInflater = LayoutInflater.from(context);
        Point point = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(point);
        mGridColumnSize = (Math.min(point.x, point.y) - DisplayUtils.dipToPx(context,
                8 * 2/*item_padding*/ + 36/*photo_width*/ + 8/*photo_margin-right*/
                        + 12/*GridView_margin-right*/)) / 2;
    }

    public void adjustGridView(GridView gridView, int pictureCount){
        gridView.setColumnWidth(mGridColumnSize);
        gridView.setNumColumns(pictureCount >= 2 ? 2 : 1);
    }

    private void adjustImageSize(ImageView view) {
        view.getLayoutParams().width = mGridColumnSize/2;
        view.getLayoutParams().height = mGridColumnSize/2;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder = getViewHolder(position,convertView,parent);
        TextView typeText =  holder.getView(R.id.typeText);
        typeText.setText(listData.get(position));
        List<String> picImage = ImageUrls.getRandomData();
        TypeGridViewAdapter adapter = new TypeGridViewAdapter(context,picImage,R.layout.item_four_content);
        NestedScrollGridView typeGridView = holder.getView(R.id.typeGridView);
        typeGridView.setAdapter(adapter);
        adjustGridView(typeGridView,picImage.size());
        return holder.getConvertView();
    }

    protected ViewHolder getViewHolder(int position,View convertView,ViewGroup parent){
        return ViewHolder.getViewHolder(context,convertView,parent,mItemLayoutId,position);
    }

    class TypeGridViewAdapter extends CommonAdapter<String>{

        public TypeGridViewAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
           ImageView imageView = helper.getView(R.id.type_imageView);
            adjustImageSize(imageView);
            Glide.with(context).load(item).into(imageView);
        }
    }

}
