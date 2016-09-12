package com.cnjy99.cyq.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.cnjy99.cyq.R;
import com.cnjy99.cyq.adapter.DesignListViewAdapter;
import com.cnjy99.cyq.utils.MyBGABannerUtil;
import com.zhy.autolayout.AutoRelativeLayout;
import java.util.ArrayList;
import java.util.List;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 *  原创设计
 */
public class SecondFragment extends BaseFragmet implements BGABanner.OnItemClickListener,BGABanner.Adapter,View.OnClickListener{

    private BGABanner mZoomStackBanner;
    private AutoRelativeLayout culture_toys;
    private AutoRelativeLayout jewelry;
    private AutoRelativeLayout carving;
    private AutoRelativeLayout originality;
    private AutoRelativeLayout inheritance;
    private ListView designListView;
    private SwipeRefreshLayout designSwipeRefreshLayout;
    private DesignListViewAdapter adapter;

    private String[] imgArray = new String[]{
            "http://img.taopic.com/uploads/allimg/140720/240467-140H00K62786.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3603943369,1952417318&fm=206&gp=0.jpg",
            "http://image.tianjimedia.com/uploadImages/2012/011/R5J8A0HYL5YV.jpg",
            "http://tupian.enterdesk.com/2013/mxy/12/10/15/3.jpg",
            "http://imgstore.cdn.sogou.com/app/a/100540002/714860.jpg",
    };

    public SecondFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        initView(view);
        setListener();
        return view ;
    }

    public void initView(View view){
        mZoomStackBanner = (BGABanner) view.findViewById(R.id.banner_main_zoomStack);
        setData(mZoomStackBanner);

        culture_toys = (AutoRelativeLayout)view.findViewById(R.id.culture_toys);
        jewelry = (AutoRelativeLayout)view.findViewById(R.id.jewelry);
        carving = (AutoRelativeLayout)view.findViewById(R.id.carving);
        originality = (AutoRelativeLayout)view.findViewById(R.id.originality);
        inheritance = (AutoRelativeLayout)view.findViewById(R.id.inheritance);

        designListView = (ListView)view.findViewById(R.id.designListView);
        designSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.designSwipeRefreshLayout);

        adapter = new DesignListViewAdapter(getContext(),getData(),R.layout.item_second);
        designListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        culture_toys.setSelected(true);
    }
    public List<String> getData(){
        List<String> list = new ArrayList<>();
        for (int i = 0;i < imgArray.length; i++){
            list.add(imgArray[i]);
        }
        return list;
    }

    public void setData(BGABanner bgaBanner){
        List<String> urls = new ArrayList<>();
        for (int i = 0;i<imgArray.length;i++){
            urls.add(imgArray[i]);
        }
        List<View> views = MyBGABannerUtil.getItemImageView(getContext(),urls);

        bgaBanner.setData(views);
    }

    private void setListener() {
        mZoomStackBanner.setOnItemClickListener(this);
        culture_toys.setOnClickListener(this);
        jewelry.setOnClickListener(this);
        carving.setOnClickListener(this);
        originality.setOnClickListener(this);
        inheritance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.culture_toys:
                culture_toys.setSelected(true);
                jewelry.setSelected(false);
                carving.setSelected(false);
                originality.setSelected(false);
                inheritance.setSelected(false);
                break;
            case R.id.jewelry:
                culture_toys.setSelected(false);
                jewelry.setSelected(true);
                carving.setSelected(false);
                originality.setSelected(false);
                inheritance.setSelected(false);
                break;
            case R.id.carving:
                culture_toys.setSelected(false);
                jewelry.setSelected(false);
                carving.setSelected(true);
                originality.setSelected(false);
                inheritance.setSelected(false);
                break;
            case R.id.originality:
                culture_toys.setSelected(false);
                jewelry.setSelected(false);
                carving.setSelected(false);
                originality.setSelected(true);
                inheritance.setSelected(false);
                break;
            case R.id.inheritance:
                culture_toys.setSelected(false);
                jewelry.setSelected(false);
                carving.setSelected(false);
                originality.setSelected(false);
                inheritance.setSelected(true);
                break;
        }
    }

    @Override
    public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
        Toast.makeText(getContext(), "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
        Toast.makeText(getContext(), "onBannerItemClick", Toast.LENGTH_SHORT).show();
    }

}
