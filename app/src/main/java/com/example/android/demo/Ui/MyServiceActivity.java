package com.example.android.demo.Ui;

import android.os.Bundle;
import android.widget.Button;

import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.R;

public class MyServiceActivity extends BaseActivity {
    Button btn_start,btn_bind,btn_stop,btn_unbind;
    @Override
    public int getRootViewId() {
        return R.layout.activity_my_service;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        btn_bind = findViewById(R.id.btn_bind);
        btn_unbind = findViewById(R.id.btn_unbind);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCompleted() {

    }
}
