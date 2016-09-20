package com.cnjy99.cyq.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.cnjy99.cyq.R;
import com.cnjy99.cyq.adapter.NameFragmentPagerAdapter;
import com.cnjy99.cyq.fragment.CollectionFragment;
import com.cnjy99.cyq.fragment.DynamicFragment;
import com.cnjy99.cyq.fragment.MarketFragment;
import com.cnjy99.cyq.utils.NativeArraysUtil;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class NameActivity extends MyBaseActivity {

    private MagicIndicator indicator;
    private ViewPager mViewPager;
    private NameFragmentPagerAdapter pagerAdapter;
    public static Intent newInstance(Context context) {
        return new Intent(context, NameActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.home_headimageview_info);
    }

    public List<String> getData(){
        return NativeArraysUtil.getMagicIndicatorName(NameActivity.this);
    }

    @Override
    public void initView() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new DynamicFragment());
        fragmentList.add(new MarketFragment());
        fragmentList.add(new CollectionFragment());

        indicator =(MagicIndicator) this.findViewById(R.id.name_magicIndicator);
        mViewPager = (ViewPager) this.findViewById(R.id.name_viewPager);
        pagerAdapter = new NameFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(pagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return getData().size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(NameActivity.this);
               // badgePagerTitleView.setBackground();
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(getData().get(index));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setNormalColor(Color.parseColor("#b3b3b3"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#2b2b2b"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(Color.parseColor("#40c4ff"));
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator, mViewPager);

    }

    @Override
    public void initValue() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initTiTle() {
        super.initTiTle();
    }

}
