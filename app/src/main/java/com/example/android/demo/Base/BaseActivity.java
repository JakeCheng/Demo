package com.example.android.demo.Base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.demo.Model.BaseModel;
import com.example.android.demo.MyApplication;
import com.example.android.demo.R;
import com.example.android.demo.Service.NetWorkChangeEvent;
import com.example.android.demo.Utils.MyAppManager;
import com.example.android.demo.Utils.NetWorkManager;
import com.example.android.demo.Utils.ProgressDialog;
import com.example.android.demo.Utils.ScreenUtils;
import com.example.android.demo.Utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends IBasePresenter> extends AppCompatActivity implements IBaseView {
    protected T mPresenter;
    protected BaseActivity mContext;
    protected MyApplication application;
    protected String TAG;
    protected Dialog dialog;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        mContext = this;
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
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
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        if (mPresenter != null){
            mPresenter.detachView();
        }
        MyAppManager.getInstance().deleteLastActivity();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NetWorkChangeEvent event) {
        switch (NetWorkManager.getNetworkState()) {
            case NetWorkChangeEvent.NET_NO:
                Log.e(TAG, "onEventMainThread: 没有网络");
                break;
            case NetWorkChangeEvent.NET_DATA:
                Log.e(TAG, "onEventMainThread: 移动网络");
                break;
            case NetWorkChangeEvent.NET_WIFI:
                Log.e(TAG, "onEventMainThread: WiFI");
                break;
        }
    }
    private void showLoadingDialog() {
        if (dialog == null) {
            dialog = ProgressDialog.createLoadingDialog(mContext,getResources().getString(R.string.loading));
        }
        dialog.setCancelable(false);
        dialog.show();
    }
    private void closeLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    @Override
    public void showLoading() {
        showLoadingDialog();
    }


    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }


    @Override
    public void showError(String msg) {
        ToastUtils.showPhone(mContext,msg,2);
    }

    @Override
    public void onErrorCode(BaseModel model) {
        ToastUtils.showPhone(mContext, ScreenUtils.showErrMovie(model.getError_code()),2);
    }

}
