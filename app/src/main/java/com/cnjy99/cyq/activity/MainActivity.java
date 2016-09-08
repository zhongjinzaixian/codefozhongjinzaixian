package com.cnjy99.cyq.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cnjy99.cyq.R;
import com.cnjy99.cyq.fragment.FiveFragment;
import com.cnjy99.cyq.fragment.FourFragment;
import com.cnjy99.cyq.fragment.HomeFragment;
import com.cnjy99.cyq.fragment.MessageFragment;
import com.cnjy99.cyq.fragment.MyCommunityInfoFragment;
import com.cnjy99.cyq.fragment.SecondFragment;
import com.cnjy99.cyq.fragment.SendFragment;
import com.cnjy99.cyq.fragment.SnatchFragment;
import com.cnjy99.cyq.fragment.ThreeFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

/**
 * 首页容器
 */
public class MainActivity extends MyBaseActivity {

    //第一次
    @ViewInject(R.id.homeLayout)
    private AutoRelativeLayout homeLayout;
    @ViewInject(R.id.duobaoLayout)
    private AutoRelativeLayout duobaoLayout;
    @ViewInject(R.id.msgLayout)
    private AutoRelativeLayout msgLayout;
    @ViewInject(R.id.postedLayout)
    private AutoRelativeLayout postedLayout;
    @ViewInject(R.id.myLayout)
    private AutoRelativeLayout myLayout;

    private HomeFragment homeFragment;
    private SnatchFragment snatchFragment;
    private SendFragment sendFragment;
    private MessageFragment messageFragment;
    private MyCommunityInfoFragment myCommunityInfoFragment;

    private ArrayList<RelativeLayout> layoutList = new ArrayList<>();
    private ArrayList<Fragment> fragmentList=new ArrayList<>();
    private FragmentManager manager;

    private int currentTabIndex = -1;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        ViewUtils.inject(activity);
        initTiTle();
        textView.setText(getString(R.string.home_title));
        returnImageView.setVisibility(View.GONE);
    }

    @Override
    public void initValue() {
        layoutList.add(homeLayout);
        layoutList.add(duobaoLayout);
        layoutList.add(postedLayout);
        layoutList.add(msgLayout);
        layoutList.add(myLayout);

        homeLayout.setSelected(true);

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        homeFragment=new HomeFragment();
        snatchFragment=new SnatchFragment();
        sendFragment=new SendFragment();
        messageFragment=new MessageFragment();
        myCommunityInfoFragment=new MyCommunityInfoFragment();

        fragmentList.add(homeFragment);
        fragmentList.add(snatchFragment);
        fragmentList.add(sendFragment);
        fragmentList.add(messageFragment);
        fragmentList.add(myCommunityInfoFragment);

        if (transaction != null){
            for (int i = 0; i < fragmentList.size(); i++) {
                transaction.add(R.id.contentFragment,fragmentList.get(i));
                transaction.hide(fragmentList.get(i));
            }
            transaction.show(fragmentList.get(0));
            transaction.commit();
        }
    }

    private void changeFragment(int index){
        FragmentTransaction transaction=manager.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            transaction.hide(fragmentList.get(i));
        }
        transaction.show(fragmentList.get(index));
        transaction.commit();
    }



    @Override
    public void initEvent() {
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = layoutList.indexOf(homeLayout);
                switchTab(index);
                changeFragment(index);
            }
        });

        msgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = layoutList.indexOf(msgLayout);
                switchTab(index);
                changeFragment(index);
            }
        });

        duobaoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = layoutList.indexOf(duobaoLayout);
                switchTab(index);
                changeFragment(index);
            }
        });

        myLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = layoutList.indexOf(myLayout);
                switchTab(index);
                changeFragment(index);
            }
        });

        postedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = layoutList.indexOf(postedLayout);
                switchTab(index);
                changeFragment(index);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                myApplication.getActivityManager().popAllActivityExceptOne();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean switchTab(int index) {
        if(index == currentTabIndex) {
            return false;
        }
        for (int i = 0; i < layoutList.size(); i++) {
            if(i == index) {
                layoutList.get(i).setSelected(true);
            } else {
                layoutList.get(i).setSelected(false);
            }
        }
        currentTabIndex = index;
        return true;
    }
}
