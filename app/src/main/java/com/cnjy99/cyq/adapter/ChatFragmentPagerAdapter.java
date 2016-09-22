package com.cnjy99.cyq.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class ChatFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public ChatFragmentPagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

}
