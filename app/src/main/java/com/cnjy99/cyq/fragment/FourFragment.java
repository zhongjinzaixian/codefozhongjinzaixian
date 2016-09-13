package com.cnjy99.cyq.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cnjy99.cyq.R;
import com.cnjy99.cyq.adapter.TypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类大全
 */
public class FourFragment extends BaseFragmet {

    private ListView listView;
    private TypeAdapter listViewAdapter;
    private List<String> listDate;

    public FourFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        initView(view);
        return view;
    }

    public List<String> getData(){
        listDate = new ArrayList<>();
        String[] data =  getResources().getStringArray(R.array.type_all);
        for (int i = 0;i < data.length; i++){
            listDate.add(data[i]);
        }
        return listDate;
    }

    public void initView(View view){

        listViewAdapter = new TypeAdapter(getContext(), getData(),R.layout.item_four);
        listView = (ListView)view.findViewById(R.id.type_listView);
        listView.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();

    }

}
