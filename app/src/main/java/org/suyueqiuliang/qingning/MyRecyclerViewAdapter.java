package org.suyueqiuliang.qingning;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    //② 创建ViewHolder

    List<String> mDatas;
    List<Double> time;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView title;
        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.text55);
        }
    }



    public MyRecyclerViewAdapter(Context context, List<String> data,List<Double> time) {
        this.mDatas = data;
        this.context=context;
        this.time=time;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mDatas.get(position));
        if((int)(time.get(position)*60)>95)
            holder.title.setHeight((int)(time.get(position)*60));
        else
            holder.title.setHeight(95);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void update(List<String> list){
        this.mDatas=list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyc, parent, false);
        return new ViewHolder(v);
    }
}
