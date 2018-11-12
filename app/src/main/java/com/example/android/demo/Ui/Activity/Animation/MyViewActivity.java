package com.example.android.demo.Ui.Activity.Animation;

import android.os.Bundle;
import android.util.Log;

import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.R;

public class MyViewActivity extends BaseActivity {

    @Override
    public int getRootViewId() {
        return R.layout.activity_my_view;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: MyViewActivity");
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

}
