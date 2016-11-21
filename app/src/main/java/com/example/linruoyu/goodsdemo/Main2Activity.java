package com.example.linruoyu.goodsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> mList;
    private SimpleAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initDatas();
        initView();
//        mAdapter=new SimpleAdapter(this,mList);
        mRecyclerView.setAdapter(mAdapter);
        //设置RecyclerView的布局管理
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//        设置RecyclerView的item间分割线
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        /**
         * 设置RecyclerView的动画效果
         */
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        /**
         *设置监听
         */
        mAdapter.setOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(Main2Activity.this, "Click"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(Main2Activity.this, "LongClick"+position, Toast.LENGTH_SHORT).show();
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
            case R.id.action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
                break;
            case R.id.action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.action_staggered:
                Intent intent=new Intent(this,StaggeredGridLayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.action_hor_gridview:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(8,StaggeredGridLayoutManager.HORIZONTAL));
                break;
            case R.id.action_add:
                mAdapter.addData(1);
                break;
            case R.id.action_delete:
                mAdapter.deleteData(1);
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
