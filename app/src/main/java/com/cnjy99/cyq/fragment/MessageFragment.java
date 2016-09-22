package com.cnjy99.cyq.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cnjy99.cyq.R;
import com.cnjy99.cyq.adapter.ChatFragmentPagerAdapter;
import com.cnjy99.cyq.easemob.ChatActivity;
import com.cnjy99.cyq.easemob.listener.MyConnectionListener;
import com.cnjy99.cyq.easemob.runtimepermissions.PermissionsManager;
import com.cnjy99.cyq.easemob.runtimepermissions.PermissionsResultAction;
import com.cnjy99.cyq.utils.LogUtil;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.exceptions.HyphenateException;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息页面
 */
public class MessageFragment extends BaseFragmet {

    private ViewPager chat_viewPager;
    private MagicIndicator chat_indicator;
    private EaseConversationListFragment easeConversationListFragment;
    private EaseContactListFragment easeContactListFragment;
    private EMMessageListener emMessageListener;

    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] dataArray;
    private ChatFragmentPagerAdapter adapter;

    public MessageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initView(view);
        /**
         * 请求所有必要的权限----
         */
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(getActivity(), new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
        /**
         * 连接状态监听
         */
        EMClient.getInstance().addConnectionListener(new MyConnectionListener(getActivity()));
        /**
         * 消息监听
         */
        emMessageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                //收到消息--->刷新页面---->保存消息记录到数据库
                easeConversationListFragment.refresh();
                EMClient.getInstance().chatManager().importMessages(list);
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {
                //收到透传消息
            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> list) {
                //收到已读回执
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> list) {
                //消息状态变动
            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        };
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
        return view;
    }

    private void initView(final View view){

        dataArray = getActivity().getResources().getStringArray(R.array.chat_top_text);

        chat_viewPager = (ViewPager)view.findViewById(R.id.chat_viewPager);
        chat_indicator = (MagicIndicator)view.findViewById(R.id.chat_indicator);
        /**
         * 会话界面
         */
        easeConversationListFragment = new EaseConversationListFragment();
        easeConversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {

            }
        });
        /**
         * 联系人列表
         */
        easeContactListFragment = new EaseContactListFragment();
        new Thread() {//需要在子线程中调用
            @Override
            public void run() {
                //需要设置联系人列表才能启动fragment
                easeContactListFragment.setContactsMap(getContact());
            }
        }.start();

        easeContactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {

            @Override
            public void onListItemClicked(EaseUser user) {
                startActivity(ChatActivity.newInstance(getActivity()).putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername()));
            }
        });
        /**
         * 监听好友状态事件
         */
        friendState();

        fragmentList.add(easeConversationListFragment);
        fragmentList.add(easeContactListFragment);

        adapter = new ChatFragmentPagerAdapter(getFragmentManager(),fragmentList);
        chat_viewPager.setAdapter(adapter);

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return dataArray.length;
            }

            @Override
            public IPagerTitleView getTitleView(final Context context,final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(getActivity());
                commonPagerTitleView.setContentView(R.layout.chat_layout_top);
                // 初始化
                final TextView titleText = (TextView) commonPagerTitleView.findViewById(R.id.chat_top_text);
                titleText.setText(dataArray[index]);

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextColor(ContextCompat.getColor(context,R.color.access_text_color_sel));
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
                        chat_viewPager.setCurrentItem(index);
                    }
                });
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });

        chat_indicator.setNavigator(commonNavigator);
        chat_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                chat_indicator.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                chat_indicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                chat_indicator.onPageScrollStateChanged(state);
            }
        });
    }

    public void friendState(){
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String s) {
                //同意好友请求
            }

            @Override
            public void onContactDeleted(String s) {
                //好友请求被拒绝
            }

            @Override
            public void onContactInvited(String s, String s1) {
                //收到好友请求
            }

            @Override
            public void onContactAgreed(String s) {
                //被删除时回调此方法
                new Thread(){
                    @Override
                    public void run() {
                        //需要设置联系人列表才能启动fragment
                        easeContactListFragment.setContactsMap(getContact());
                        easeContactListFragment.refresh();
                    }
                }.start();
            }

            @Override
            public void onContactRefused(String s) {
                new Thread() {//需要在子线程中调用
                    @Override
                    public void run() {
                        //需要设置联系人列表才能启动fragment
                        easeContactListFragment.setContactsMap(getContact());
                        easeContactListFragment.refresh();
                    }
                }.start();
            }
        });
    }

    private Map<String,EaseUser> getContact(){
        Map<String,EaseUser> map = new HashMap<>();
        try {
            List<String> userNames = EMClient.getInstance().contactManager().getAllContactsFromServer();
            for (String userId: userNames){
                map.put(userId,new EaseUser(userId));
            }
        }catch (HyphenateException e){
            e.printStackTrace();
        }
        return map;
    }
}
