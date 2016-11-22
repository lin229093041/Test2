package com.example.linruoyu.goodsdemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 用于初始化Fresco
 * Created by linruoyu on 2016/11/19.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
