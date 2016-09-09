package com.cnjy99.cyq.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cnjy99.cyq.R;

/**
 *  原创设计
 */
public class SecondFragment extends Fragment {

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.secondImageView);
        Glide.with(getContext()).load("http://img.taopic.com/uploads/allimg/110316/292-110316121G647.jpg").into(imageView);

        return view ;
    }





}
