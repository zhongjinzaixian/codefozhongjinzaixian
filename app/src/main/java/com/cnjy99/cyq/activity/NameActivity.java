package com.cnjy99.cyq.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cnjy99.cyq.R;

public class NameActivity extends AppCompatActivity {

    public static Intent newInstance(Context context) {
        return new Intent(context, NameActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_headimageview_info);
    }
}
