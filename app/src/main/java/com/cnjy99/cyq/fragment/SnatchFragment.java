package com.cnjy99.cyq.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnjy99.cyq.R;

/**
 * 夺宝页面
 */
public class SnatchFragment extends BaseFragmet {


    public SnatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_snatch, container, false);
    }

}
