package com.example.linruoyu.goodsdemo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by linruoyu on 2016/11/19.
 * 加载更多的抽象类
 */

public abstract class EndlessRecylerOnSrcollListener extends RecyclerView.OnScrollListener {
    private int previousTotal=0;
    private boolean loading=true;//加载状态
    int firstVisisbleItem,visibleItemCount,totalItemCount;//layout的第一条数据下表，layout,recycler的item数，
    private int currentPage=1;//页数
    private LinearLayoutManager mLayoutManager;//所使用的layoutmanager

    public EndlessRecylerOnSrcollListener(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    /**
     * 上拉加载时回调该方法
     * @param recyclerView
     * @param dx
     * @param dy
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount=recyclerView.getChildCount();//recycler的item数
        totalItemCount=mLayoutManager.getItemCount();//layout的item数
        firstVisisbleItem=mLayoutManager.findFirstVisibleItemPosition();//第一个layout的item id
        if(loading){
            if(totalItemCount>previousTotal){//确保layout要加载的时候item数比上一次记录的要大，并刷新内置的标志位item
                loading=false;
                previousTotal=totalItemCount;
            }
        }
        if(!loading&&(totalItemCount-visibleItemCount)<=firstVisisbleItem){//意思差不多是你显示的第一条数据合法
            currentPage++;
            onLoadMore(currentPage);//回调页数给使用者
            loading=true;
        }


    }
    public abstract void onLoadMore(int currentPage);
}
