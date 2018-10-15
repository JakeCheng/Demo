package com.example.android.demo.Ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.R;
import com.example.android.demo.Utils.MyAppManager;
import com.example.android.demo.Utils.SharePreferenceUtil;

public class WelcomeActivity extends BaseActivity {
    private boolean firstOpen;
    @Override
    public int getRootViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startNextActivity();
            }
        }, 1000);
    }

    private void startNextActivity() {
        firstOpen = SharePreferenceUtil.getFirstOpen(mContext);
        if (firstOpen) {
            SharePreferenceUtil.setFirstOpen(mContext, false);
            startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            if (!TextUtils.isEmpty(SharePreferenceUtil.getEmail(mContext))) {
                startActivity(new Intent(mContext,MainActivity.class));
            } else {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        }
        MyAppManager.getInstance().exitAll();
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
