package com.cnjy99.cyq.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnjy99.cyq.R;
import com.cnjy99.cyq.adapter.MyFragmentPagerAdapter;
import com.cnjy99.cyq.utils.LogUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragmet {

    @ViewInject(R.id.indicator)
    private MagicIndicator indicator;
    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;

    private String[] dataArray;
    private int[] imgArray = {R.drawable.home_community_selector,R.drawable.home_design_selector,
            R.drawable.home_access_selector,R.drawable.home_type_selector};

    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;

    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    public void initView(View view){
        ViewUtils.inject(this,view);
        dataArray = getActivity().getResources().getStringArray(R.array.home_title);
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        threeFragment = new ThreeFragment();
        fourFragment = new FourFragment();

        fragmentList.add(firstFragment);
        fragmentList.add(secondFragment);
        fragmentList.add(threeFragment);
        fragmentList.add(fourFragment);

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(),fragmentList);
        viewPager.setAdapter(myFragmentPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return dataArray == null ? 0 : dataArray.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(getActivity());
                commonPagerTitleView.setContentView(R.layout.layout_home_title_top);
                // 初始化
                final ImageView titleImg = (ImageView) commonPagerTitleView.findViewById(R.id.titleImageView);
                titleImg.setImageResource(imgArray[index]);
                final TextView titleText = (TextView) commonPagerTitleView.findViewById(R.id.titleTextView);
                titleText.setText(dataArray[index]);

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextColor(Color.RED);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextColor(Color.BLACK);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });
                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });

        indicator.setNavigator(commonNavigator);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                indicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                indicator.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("fragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.e("fragment onDetach");
    }

}
