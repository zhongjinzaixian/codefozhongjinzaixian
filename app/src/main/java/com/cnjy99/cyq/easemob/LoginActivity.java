package com.cnjy99.cyq.easemob;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cnjy99.cyq.R;
import com.cnjy99.cyq.activity.MainActivity;
import com.cnjy99.cyq.easemob.listener.MyConnectionListener;
import com.cnjy99.cyq.fragment.FirstFragment;
import com.cnjy99.cyq.preferences.AppStartedSp;
import com.cnjy99.cyq.utils.LogUtil;
import com.cnjy99.cyq.utils.ReturnCodeUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView top_name;
    private TextView register_te;
    private Button login_button;
    private EditText login_phone_et;
    private EditText login_pwd_et;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ReturnCodeUtil.LOGIN_SUCCESS:
                    LogUtil.toastMsg(LoginActivity.this, "登录成功了");
                    break;
                case ReturnCodeUtil.LOGIN_FAIL:
                    int code = msg.arg1;
                    switch (code) {
                        case 202:
                            LogUtil.toastMsg(LoginActivity.this, "用户名或密码错误");
                            break;
                    }
                    break;
            }
        }
    };

    public static Intent newInstance(Activity activity){
        return new Intent(activity,LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkIsLogin();
        initView();
        //注册一个监听连接状态的listener---------监听网络状态+账户是否在别处登录+请求服务器失败等.
        EMClient.getInstance().addConnectionListener(new MyConnectionListener(this));
        clickListener();
    }

    public void initView(){
        top_name = (TextView)this.findViewById(R.id.top_name);
        register_te = (TextView)this.findViewById(R.id.register_te);
        login_button = (Button)this.findViewById(R.id.login_button);
        login_phone_et = (EditText)this.findViewById(R.id.login_phone_et);
        login_pwd_et = (EditText)this.findViewById(R.id.login_pwd_et);
        top_name.setText("会员登录");
    }

    private void clickListener(){
        register_te.setOnClickListener(this);
        login_button.setOnClickListener(this);
    }

    /**
     * 检测是否已经登录过了,登录过了就进入主页面
     */
    private void checkIsLogin() {
        if (EMClient.getInstance().getInstance().isLoggedInBefore()) {
            /**
             * 不管如何,登录完成之后都要加载所有的组和所有的聊天信息
             */
            EMClient.getInstance().groupManager().loadAllGroups();
            EMClient.getInstance().chatManager().loadAllConversations();
            startActivity(MainActivity.newInstance(LoginActivity.this));
            LogUtil.toastMsg(LoginActivity.this, "已经登录过了,进入下一个页面");
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_te:
                startActivity(RegisterActivity.newInstance(this));
                break;
            case R.id.login_button:
                String userName = "" + login_phone_et.getText().toString().trim();
                String pwd = "" + login_pwd_et.getText().toString().trim();
                login(userName, pwd);
                break;
        }
    }

    /**
     * 登录
     * @param userName
     * @param pwd
     */
    private void login(final String userName, String pwd){

        EMClient.getInstance().login(userName, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                /**
                 * 登录成功就调用加载数据
                 */
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                /**
                 * 保存用户名
                 */
                AppStartedSp.getInstatnce().save("userName",userName);
                startActivity(MainActivity.newInstance(LoginActivity.this));
                mHandler.sendEmptyMessage(ReturnCodeUtil.LOGIN_SUCCESS);
                finish();
            }

            @Override
            public void onError(int code, String message) {
                Message msg = mHandler.obtainMessage();
                msg.obj = message;
                msg.arg1 = code;
                msg.what = ReturnCodeUtil.LOGIN_FAIL;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
