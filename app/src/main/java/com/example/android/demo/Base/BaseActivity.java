package com.example.android.demo.Base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.demo.BuildConfig;
import com.example.android.demo.Db.DbManager;
import com.example.android.demo.GreenDao.DaoSession;
import com.example.android.demo.MyApplication;
import com.example.android.demo.R;
import com.example.android.demo.Utils.MyAppManager;
import com.example.android.demo.Utils.ProgressDialog;

import butterknife.ButterKnife;

public abstract class BaseActivity <T extends BasePresenter> extends AppCompatActivity implements BaseView{
    protected T mPresenter;
    protected BaseActivity mContext;
    protected DaoSession mDaoSession;
    protected MyApplication application;
    protected Dialog dialog;
    protected String TAG;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //严格模式  优化改善代码
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork() // or .detectAll() for all detectable problems
                    .penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedRegistrationObjects()
                    .detectActivityLeaks()
                    .detectFileUriExposure()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build());
        }
        super.onCreate( savedInstanceState);
        mContext = this;
        mDaoSession = DbManager.getDaoSession(mContext);
        setContentView(getRootViewId());
        ButterKnife.bind(this);
        application = (MyApplication) mContext.getApplication();
        MyAppManager.getInstance().addAllActivity(mContext);
        initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        TAG = this.getLocalClassName();
        initView(savedInstanceState);
        initData();
    }
    public abstract @LayoutRes
    int getRootViewId();
    public abstract void initView(Bundle savedInstanceState);
    public abstract void initPresenter();
    public abstract void initData();
    /**
     * 接收传递过来的Intent对象
     *
     * @return：Intent对象
     */
    protected Intent getPrevIntent() {
        return getIntent();
    }

    /**
     * 直接接收Intent对象中的Bundle对象，getgetExtras();
     *
     * @return
     */
    protected Bundle getPrevExtras() {
        return getPrevIntent().getExtras();
    }

    /**
     * 展示加载中
     */
    protected void showLoading(){
        dialog = ProgressDialog.createLoadingDialog(mContext,getResources().getString(R.string.loading));
        dialog.show();
    }

    /**
     * 去下加载中
     */
    protected void dissmissLoading(){
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyAppManager.getInstance().deleteLastActivity();
    }
}
