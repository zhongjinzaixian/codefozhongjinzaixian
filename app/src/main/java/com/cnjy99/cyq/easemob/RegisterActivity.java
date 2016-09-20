package com.cnjy99.cyq.easemob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cnjy99.cyq.R;
import com.cnjy99.cyq.activity.MyBaseActivity;
import com.cnjy99.cyq.easemob.listener.MyConnectionListener;
import com.cnjy99.cyq.utils.LogUtil;
import com.cnjy99.cyq.utils.ReturnCodeUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class RegisterActivity extends MyBaseActivity {

    private TextView top_name;
    private Button register_button;
    private EditText register_phone;
    private EditText register_pwd;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ReturnCodeUtil.REGISTER_SUCCESS:
                    LogUtil.toastMsg(activity, "注册成功,请先登录");
                    break;
                case ReturnCodeUtil.REGISTER_FAIL:
                    //if (mActivity == null) KLog.e("是空了");
                    LogUtil.toastMsg(activity, "该用户已经注册过了,请换一个号码再试");
                    break;
            }
        }
    };

    public static Intent newInstance(Activity activity){
        return new Intent(activity,RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        initView();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initView() {
        top_name = (TextView)this.findViewById(R.id.top_name);
        register_button = (Button)this.findViewById(R.id.register_button);
        register_phone = (EditText)this.findViewById(R.id.register_phone);
        register_pwd = (EditText)this.findViewById(R.id.register_pwd);
        top_name.setText("注册界面");

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPhone = register_phone.getText().toString().trim();
                String pwd = register_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(userPhone)) {
                    LogUtil.toastMsg(activity, "用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    LogUtil.toastMsg(activity, "密码不能为空");
                    return;
                }
                register(userPhone, pwd);
            }
        });
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener(this));
    }
    /**
     * 用户注册(这里只是demo,没有自己写服务器,实际开发中是要通过后台服务器来注册,注册成功之后服务器再注册环信账户,为了简化,这里直接注册[官方都不建议这样做哦])
     * @param phoneNum
     * @param pwd
     */
    private void register(final String phoneNum,final String pwd){
        new Thread(new Runnable() { //网络访问需要在子线程中进行
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(phoneNum,pwd);
                    mHandler.sendEmptyMessage(ReturnCodeUtil.REGISTER_SUCCESS);
                    startActivity(LoginActivity.newInstance(activity));
                    finish();//结束当前Activity
                }catch (HyphenateException e){
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(ReturnCodeUtil.REGISTER_FAIL);
                }

            }
        }).start();

    }

    @Override
    public void initEvent() {
    }

    @Override
    public void initValue() {
    }
}
