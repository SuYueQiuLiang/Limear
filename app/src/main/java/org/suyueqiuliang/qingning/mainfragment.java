package org.suyueqiuliang.qingning;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class mainfragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainfragment, container, false);
        TextView dat=(TextView)view.findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        String day=toString().valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        dat.setText(day);
        List<String> list=new ArrayList<String>();
        List<Double> time=new ArrayList<Double>();
        List<Integer> date=new ArrayList<Integer>();
        List<Integer> stytle=new ArrayList<Integer>();
        List<String> enduptime=new ArrayList<String>();
        list.add("sdaasd");
        list.add("sdaasd"+"\nsd ddf");
        time.add((double)1);
        time.add((double)2);
        date.add(1);
        date.add(2);
        enduptime.add("8:50");
        enduptime.add("");
        stytle.add(0);
        stytle.add(1);
        MyRecyclerViewAdapter adapter=new MyRecyclerViewAdapter(getContext(),list,time,date,enduptime,stytle);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.Recycler);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0));
        LinearLayoutManager l=new LinearLayoutManager(getContext());
        l.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(l);
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    public List<String> writedata(String text){
        SharedPreferences.Editor editor = getContext().getSharedPreferences("lifeday", MODE_PRIVATE).edit();
        editor.putString("code", text);
        editor.commit();
        //步骤1：创建一个SharedPreferences接口对象
        SharedPreferences read = getContext().getSharedPreferences("lifeday", MODE_PRIVATE);
        String value = read.getString("code", "");
        return null;
    }
}