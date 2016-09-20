package com.cnjy99.cyq.easemob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cnjy99.cyq.R;
import com.cnjy99.cyq.activity.MyBaseActivity;

public class RegisterActivity extends MyBaseActivity{

    private TextView top_name;

    public static Intent newInstance(Activity activity){
        return new Intent(activity,RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initView() {
        top_name = (TextView)this.findViewById(R.id.top_name);
        top_name.setText("注册界面");
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initValue() {

    }
}
