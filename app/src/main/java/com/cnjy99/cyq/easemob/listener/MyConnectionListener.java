package com.cnjy99.cyq.easemob.listener;

import android.app.Activity;
import android.util.Log;

import com.cnjy99.cyq.easemob.LoginActivity;
import com.cnjy99.cyq.utils.LogUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MyConnectionListener implements EMConnectionListener {

    private Activity context;

    public MyConnectionListener(Activity context){
        this.context = context;
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected(final int error) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(error == EMError.USER_REMOVED){
                    LogUtil.toastMsg(context,"帐号已经被移除!");
                }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    LogUtil.toastMsg(context,"帐号在其他设备登录,你已经被迫下线！");
                    EMClient.getInstance().logout(true, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            Log.e("main", "下线成功了");
                        }

                        @Override
                        public void onError(int i, String s) {
                            Log.e("main", "下线失败了！" + s);
                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                    //下线
                    context.startActivity(LoginActivity.newInstance(context));
                    context.finish();
                } else {
                    if (NetUtils.hasNetwork(context)){
                        //LogUtil.toastMsg(context,"连接不到聊天服务器!");
                    }
                    else{
                        LogUtil.toastMsg(context,"当前网络不可用，请检查网络设置!");
                    }
                }
            }
        });
    }
}
