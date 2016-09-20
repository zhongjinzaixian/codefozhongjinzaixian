package com.cnjy99.cyq.easemob;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cnjy99.cyq.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView top_name;
    private TextView register_te;

    public static Intent newInstance(Activity activity){
        return new Intent(activity,LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        clickListener();
    }

    public void initView(){
        top_name = (TextView)this.findViewById(R.id.top_name);
        register_te = (TextView)this.findViewById(R.id.register_te);

        top_name.setText("会员登录");
    }

    private void clickListener(){
        register_te.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_te:
                startActivity(RegisterActivity.newInstance(this));
                break;
        }
    }
}
