package com.cnjy99.cyq.adapter;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cnjy99.cyq.R;
import com.cnjy99.cyq.utils.DisplayUtils;
import com.cnjy99.cyq.utils.ImageUrls;
import com.cnjy99.cyq.utils.LogUtil;
import com.cnjy99.cyq.utils.ViewHolder;
import com.cnjy99.cyq.view.NestedScrollGridView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class FirstRecycleViewAdapter extends RecyclerView.Adapter<FirstRecycleViewAdapter.FirstViewHolder>{

    private Context context;
    private FragmentManager fragmentManager;
    private int mGridColumnSize;
    private LayoutInflater layoutInflater;
    private List<String> data = new ArrayList<>();

    public FirstRecycleViewAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
        Point point = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(point);
        mGridColumnSize = (Math.min(point.x, point.y) - DisplayUtils.dipToPx(context,
                8 * 2/*item_padding*/ + 36/*photo_width*/ + 8/*photo_margin-right*/
                        + 12/*GridView_margin-right*/)) / 3;
        LogUtil.e("-------mGridColumnSize----->"+mGridColumnSize);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void adjustGridView(GridView gridView, int pictureCount){
        gridView.setColumnWidth(mGridColumnSize);
        gridView.setNumColumns(pictureCount >= 3 ? 3 : pictureCount%3);
    }

    private void adjustImageSize(ImageView view) {
        view.getLayoutParams().width = mGridColumnSize;
        view.getLayoutParams().height = mGridColumnSize;
    }

    @Override
    public FirstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FirstViewHolder(layoutInflater.inflate(R.layout.item_first,parent,false));
    }

    @Override
    public void onBindViewHolder(FirstViewHolder holder, int position) {
        List<String> picList = ImageUrls.getRandomData();
        GridAdapter adapter = new GridAdapter(context, picList,R.layout.item_first_picture);
        holder.gridViewFirst.setAdapter(adapter);
        adjustGridView(holder.gridViewFirst,picList.size());
    }

    public class FirstViewHolder extends RecyclerView.ViewHolder{

        CircleImageView headImageView;
        TextView nickTextView;
        TextView timeTextView;
        TextView contentTextView;
        NestedScrollGridView gridViewFirst;

        public FirstViewHolder(View itemView) {
            super(itemView);
            headImageView = (CircleImageView)itemView.findViewById(R.id.headImageView);
            nickTextView = (TextView)itemView.findViewById(R.id.nickTextView);
            timeTextView = (TextView)itemView.findViewById(R.id.timeTextView);
            contentTextView = (TextView)itemView.findViewById(R.id.contentTextView);
            gridViewFirst = (NestedScrollGridView)itemView.findViewById(R.id.gridViewFirst);
        }
    }

     class GridAdapter extends CommonAdapter<String> {

        public GridAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            ImageView gridImageView = helper.getView(R.id.gridImageView);
            adjustImageSize(gridImageView);
            Glide.with(mContext).load(item).into(gridImageView);
        }

    }

}
