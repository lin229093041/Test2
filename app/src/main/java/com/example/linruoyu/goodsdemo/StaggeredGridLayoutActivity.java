package com.example.linruoyu.goodsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class StaggeredGridLayoutActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> mList;
    private StaggeredAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initDatas();
        initView();
        mAdapter=new StaggeredAdapter(this,mList);
        mRecyclerView.setAdapter(mAdapter);
        //设置RecyclerView的布局管理
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
//        设置RecyclerView的item间分割线
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mAdapter.setOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
                    mAdapter.deleteData(position);
            }
        });
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);

    }

    private void initDatas() {
        mList=new ArrayList<>();
        for (int i = 'A'; i <'z' ; i++) {
            mList.add(""+(char)i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id){

        }
        return super.onOptionsItemSelected(item);
    }
}
