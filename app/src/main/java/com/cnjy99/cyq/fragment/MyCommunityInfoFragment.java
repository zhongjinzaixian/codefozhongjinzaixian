package com.cnjy99.cyq.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cnjy99.cyq.R;
import com.cnjy99.cyq.easemob.LoginActivity;
import com.cnjy99.cyq.utils.LogUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * 我的社区信息页面
 */
public class MyCommunityInfoFragment extends BaseFragmet {

    private Button test_downLine;
    private Button add_fr;

    public MyCommunityInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_community_info, container, false);
        initView(view);
        return view;
    }

    public void initView(View view){
        test_downLine = (Button)view.findViewById(R.id.test_downLine);
        add_fr = (Button)view.findViewById(R.id.add_fr);
        test_downLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMClient.getInstance().logout(true, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        //handler.sendEmptyMessage(1);
                        startActivity(LoginActivity.newInstance(getActivity()));
                        getActivity().finish();
                    }

                    @Override
                    public void onError(int i, String s) {
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });

        add_fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });
    }

    /**
     * 添加好友
     */
    private void addFriend() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("添加好友");
        final EditText newFirendName = new EditText(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        newFirendName.setLayoutParams(layoutParams);
        newFirendName.setHint("新好友用户名");
        builder.setView(newFirendName);
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
                        String firendName = newFirendName.getText().toString().trim();
                        try {
                            EMClient.getInstance().contactManager().addContact(firendName, "我是你的朋友");
                          //  KLog.e("添加好友成功,等待回应:" + firendName);
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
}
