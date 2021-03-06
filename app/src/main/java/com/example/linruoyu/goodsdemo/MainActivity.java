package com.example.linruoyu.goodsdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG ="MainActivity" ;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private SimpleAdapter adapter;
    private LinearLayoutManager layoutManager;
    Goods g=new Goods();
    int num=0;
    /**
     * 消息队列
     */
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==10){//这里由于第一次取个数为10条，所以要new Adapter
                adapter=new SimpleAdapter(MainActivity.this,g);
                mRecyclerView.setAdapter(adapter);
            }else if (msg.what<=30&&msg.what>10){//加载状态
                adapter.notifyDataSetChanged();
            }
            super.handleMessage(msg);
        }
    };
    public MainActivity() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initinternet();
        /**
         * 上拉刷新时触发的监听
         */
        mSwipeRefreshLayout.setOnRefreshListener(this);
        /**
         * 加载更多时触发的监听
         */
        mRecyclerView.addOnScrollListener(new EndlessRecylerOnSrcollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {

                    initinternet();

            }
        });

    }

    private void initView() {
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.my_blue,R.color.my_green,R.color.my_red,R.color.my_yellow);//加载时进度条的颜色
        mRecyclerView=(RecyclerView) findViewById(R.id.goods_recycler);
        layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
    }


    /**
     * 访问数据库获取数据的方法
     */
    private void initinternet() {
        String url="https://ionic-book-store.herokuapp.com/api/v1/books/"+num+"/10";
        num++;
        OkHttpClient mOkHttpClient=new OkHttpClient();
        final Request request=new Request.Builder().url(url).build();
        Call call=mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(MainActivity.this, "访问服务器失败，请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Gson gson=new Gson();
                Goods g2;//用于接收新的数据
                g2=gson.fromJson(response.body().string(),Goods.class);
                Message msg=new Message();
                if(g.getData()!=null) {
                    g.getData().getBooks().addAll(g2.getData().getBooks());//把新的数据添加到与适配器绑定的数据中
                }else{
                    g=g2;
                }
                msg.what=g.getData().getBooks().size();
                mHandler.sendMessage(msg);//发送消息到队列中等待处理刷新界面

            }

        });


    }

    /**
     * 下拉刷新回调的方法
     */
    @Override
    public void onRefresh() {
//        list.add(0,"下拉出来的数据");
        mSwipeRefreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "刷新出了一条数据", Toast.LENGTH_SHORT).show();
    }
}
