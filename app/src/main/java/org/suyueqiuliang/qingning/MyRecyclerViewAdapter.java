package org.suyueqiuliang.qingning;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    //② 创建ViewHolder

    List<String> mDatas;
    List<Double> time;
    List<Integer> date;
    List<Integer> stytle;
    List<String> enduptime;
    Integer test=0;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView title;
        public final TextView date;
        public final CardView cardview;
        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.text55);
            date=(TextView) v.findViewById(R.id.time);
            cardview=(CardView) v.findViewById(R.id.CardView);
        }
    }



    public MyRecyclerViewAdapter(Context context, List<String> data,List<Double> time,List<Integer> date,List<String> endtime,List<Integer> stytle) {
        this.mDatas = data;
        this.context=context;
        this.time=time;
        this.date=date;
        this.enduptime=endtime;
        this.stytle=stytle;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text=mDatas.get(position);
        int times=1;
        while (times<time.get(position)){
            times++;
            text=text+"\n";
        }
        holder.title.setText(text);
        holder.date.setText(String.valueOf(date.get(position)));
        if(test>0){
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) holder.cardview.getLayoutParams();
            lp.setMargins(54*3, 0, 16*3, 0);
            holder.cardview.setLayoutParams(lp);
        }
        test++;
        holder.cardview.setCardBackgroundColor(Color.WHITE);
        /*switch (stytle.get(position)){
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
        }*/

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
