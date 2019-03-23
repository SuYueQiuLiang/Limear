package org.suyueqiuliang.qingning;

import android.content.Intent;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

public class mainfragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainfragment, container, false);
        List<String> list=new ArrayList<String>();
        List<Double> time=new ArrayList<Double>();
        List<Integer> date=new ArrayList<Integer>();
        List<Integer> stytle=new ArrayList<Integer>();
        list.add("sdaasd");
        list.add("sdaasd"+"\nsd ddf");
        time.add((double)1);
        time.add((double)2);
        date.add(1);
        date.add(2);
        MyRecyclerViewAdapter adapter=new MyRecyclerViewAdapter(getContext(),list,time,date,null,null);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.Recycler);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0));
        LinearLayoutManager l=new LinearLayoutManager(getContext());
        l.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(l);
        mRecyclerView.setAdapter(adapter);
        return view;
    }


}