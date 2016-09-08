package com.cnjy99.cyq.adapter;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnjy99.cyq.R;
import com.cnjy99.cyq.utils.DisplayUtils;
import com.cnjy99.cyq.view.NestedScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class FirstRecycleViewAdapter extends RecyclerView.Adapter<FirstRecycleViewAdapter.FirstViewHolder>{

    private Context context;
    private FragmentManager fragmentManager;
    private int mGridColumnSize;
    private LayoutInflater layoutInflater;
    private List<String> data = new ArrayList<>();

    //测试图片地址
    private String[] picArray=new String[]{"http://img.taopic.com/uploads/allimg/110316/292-110316121G647.jpg","http://pic4.nipic.com/20091215/2396136_140959028451_2.jpg",
            "http://pic24.nipic.com/20121003/10754047_140022530392_2.jpg","http://img.taopic.com/uploads/allimg/140326/235113-1403260I33562.jpg",
            "http://img.taopic.com/uploads/allimg/140720/240467-140H00K62786.jpg","http://img5.imgtn.bdimg.com/it/u=3603943369,1952417318&fm=206&gp=0.jpg",
            "http://image.tianjimedia.com/uploadImages/2012/011/R5J8A0HYL5YV.jpg","http://tupian.enterdesk.com/2013/mxy/12/10/15/3.jpg",
            "http://imgstore.cdn.sogou.com/app/a/100540002/714860.jpg",};

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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void adjustGrideview(GridView gridView, int pictureCount){
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

    }

    public class FirstViewHolder extends RecyclerView.ViewHolder{

        ImageView headImageView;
        TextView nickTextView;
        TextView timeTextView;
        TextView contentTextView;
        NestedScrollGridView gridViewFirst;

        public FirstViewHolder(View itemView) {
            super(itemView);
            headImageView = (ImageView)itemView.findViewById(R.id.headImageView);
            nickTextView = (TextView)itemView.findViewById(R.id.nickTextView);
            timeTextView = (TextView)itemView.findViewById(R.id.timeTextView);
            contentTextView = (TextView)itemView.findViewById(R.id.contentTextView);
            gridViewFirst = (NestedScrollGridView)itemView.findViewById(R.id.gridViewFirst);
        }
    }

}
