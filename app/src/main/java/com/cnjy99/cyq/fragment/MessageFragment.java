package com.cnjy99.cyq.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cnjy99.cyq.R;
import com.cnjy99.cyq.adapter.ChatFragmentPagerAdapter;
import com.cnjy99.cyq.easemob.ChatActivity;
import com.cnjy99.cyq.easemob.listener.MyConnectionListener;
import com.cnjy99.cyq.easemob.runtimepermissions.PermissionsManager;
import com.cnjy99.cyq.easemob.runtimepermissions.PermissionsResultAction;
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
    private ImageView message_add_img;

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

        message_add_img = (ImageView)view.findViewById(R.id.message_add_img);

        /**
         * 添加好友
         */
        message_add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });
        /**
         * 会话界面
         */
        easeConversationListFragment = new EaseConversationListFragment();
        easeConversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(ChatActivity.newInstance(getActivity()).putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName()));
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
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return dataArray.length;
            }

            @Override
            public IPagerTitleView getTitleView(final Context context,final int index) {

                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(getActivity());
                // badgePagerTitleView.setBackground();
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(dataArray[index]);
                simplePagerTitleView.setTextSize(20);
                simplePagerTitleView.setNormalColor(Color.parseColor("#b3b3b3"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#2b2b2b"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chat_viewPager.setCurrentItem(index);
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

        chat_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(chat_indicator, chat_viewPager);
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


    /**
     * 添加好友
     */
    private void addFriend() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("添加好友");
        final EditText newFriendName = new EditText(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        newFriendName.setLayoutParams(layoutParams);
        newFriendName.setHint("新好友用户名");
        builder.setView(newFriendName);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread() {
                    @Override
                    public void run() {
                        String friendName = newFriendName.getText().toString().trim();
                        try {
                            EMClient.getInstance().contactManager().addContact(friendName, "我是你的朋友");
                            //  KLog.e("添加好友成功,等待回应:" + friendName);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
    }
}
