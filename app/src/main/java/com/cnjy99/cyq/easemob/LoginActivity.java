package com.cnjy99.cyq.easemob;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cnjy99.cyq.MyApplication;
import com.cnjy99.cyq.R;
import com.cnjy99.cyq.activity.MainActivity;
import com.cnjy99.cyq.easemob.helper.AppHelper;
import com.cnjy99.cyq.easemob.listener.MyConnectionListener;
import com.cnjy99.cyq.easemob.manager.DBManager;
import com.cnjy99.cyq.utils.LogUtil;
import com.cnjy99.cyq.utils.ReturnCodeUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView top_name;
    private TextView register_te;
    private Button login_button;
    private EditText login_phone_et;
    private EditText login_pwd_et;

    private boolean progressShow;
    private boolean autoLogin = false;

    public static Intent newInstance(Activity activity){
        return new Intent(activity,LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppHelper.getInstance().isLoggedIn()){
            autoLogin = true;
            startActivity(MainActivity.newInstance(LoginActivity.this));
            return;
        }
        setContentView(R.layout.activity_login);
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
        /**
         * 监听用户名改变，密码清空
         */
        login_phone_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login_pwd_et.setText(null);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /**
         * 获取本地用户名
         */
        if (AppHelper.getInstance().getCurrentUsernName() != null) {
            login_phone_et.setText(AppHelper.getInstance().getCurrentUsernName());
        }
        top_name.setText("用户登录");
    }

    private void clickListener(){
        register_te.setOnClickListener(this);
        login_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_te:
                startActivity(RegisterActivity.newInstance(this));
                break;
            case R.id.login_button:
                login(v);
                break;
        }
    }

    /**
     * 登录
     * @param view
     */
    public void login(View view) {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        String currentUsername = login_phone_et.getText().toString().trim();
        String currentPassword = login_pwd_et.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();

        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DBManager.getInstance().closeDB();

        // reset current user name before login
        AppHelper.getInstance().setCurrentUserName(currentUsername);

        final long start = System.currentTimeMillis();
        // call login method
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {

                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().updateCurrentUserNick(
                        MyApplication.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }

                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }
                // get user's info (this should be get from App's server or 3rd party service)
                AppHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();

                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);

                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d("TAG", "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d("TAG", "login: onError: " + code);
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (autoLogin) {
            return;
        }
    }
}
