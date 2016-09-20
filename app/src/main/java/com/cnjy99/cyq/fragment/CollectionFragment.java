package com.cnjy99.cyq.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnjy99.cyq.R;

/**
 * 收集
 */
public class CollectionFragment extends BaseFragmet {


    public CollectionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collection, container, false);
    }

}
