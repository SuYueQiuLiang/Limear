package org.suyueqiuliang.qingning;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class mainfragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainfragment, container, false);
        List<String> list=new ArrayList<String>();
        List<Double> intlist=new ArrayList<Double>();
        list.add("sdaasd");
        list.add("sdaasd");
        intlist.add(1.5);
        intlist.add(2.0);
        MyRecyclerViewAdapter adapter=new MyRecyclerViewAdapter(getContext(),list,intlist);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.Recycler);
        LinearLayoutManager l=new LinearLayoutManager(getContext());
        l.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(l);
        mRecyclerView.setAdapter(adapter);
        return view;
    }


}