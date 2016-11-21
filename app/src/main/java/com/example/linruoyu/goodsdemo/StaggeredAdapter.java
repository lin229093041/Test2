package com.example.linruoyu.goodsdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linruoyu on 2016/11/18.
 */

public class StaggeredAdapter extends SimpleAdapter {

    private List<Integer> mHeights;
    public StaggeredAdapter(Context context, List<String> datas) {
        super(context,null);

        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < mDatas.size(); i++) {
            mHeights.add((int) (100+Math.random()*300));
        }
    }


@Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
          /*  ViewGroup.LayoutParams lp= holder.itemView.getLayoutParams();
            lp.height=mHeights.get(position);
            holder.itemView.setLayoutParams(lp);
            holder.tv.setText(mDatas.get(position));
            setUpItemEvent(holder);*/
    }


}
