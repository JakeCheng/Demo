package com.example.android.demo.Base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.demo.Model.BaseModel;
import com.example.android.demo.MyApplication;
import com.example.android.demo.R;
import com.example.android.demo.Utils.ProgressDialog;
import com.example.android.demo.Utils.ScreenUtils;
import com.example.android.demo.Utils.ToastUtils;

import butterknife.ButterKnife;

public abstract class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView {
    protected T mPresenter;
    protected View mRootView;
    protected BaseActivity mContext;
    protected MyApplication application;
    private Dialog dialog;
    /**
     * 懒加载过
     */
    private boolean isLazyLoaded;

    private boolean isPrepared;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }
    //显示页面时加载数据
    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
            onLazyLoad();
            isLazyLoaded = true;
        }
    }
    @UiThread
    public abstract void onLazyLoad();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getRootViewId(),container,false);
        mContext = (BaseActivity) getActivity();
        ButterKnife.bind(this,mRootView);
        application = (MyApplication) mContext.getApplication();
        initPresenter();
        if (mPresenter != null) mPresenter.attachView(this);
        initView();
        initData();
        return mRootView;
    }

    public abstract @LayoutRes
    int getRootViewId();
    public abstract void initView();
    public abstract void initPresenter();
    public abstract void initData();
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }


    /**
     * 弹出backstack
     */
    protected void back() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 0 && getActivity() != null) {
            getActivity().onBackPressed();
        } else {
            fragmentManager.popBackStackImmediate();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }




    private OnVisibleListener mOnVisibleListener;
    public interface OnVisibleListener {
        void onVisibilityChanged(boolean isVisibileToUser);
    }
    public void setOnVisibleListener(OnVisibleListener onVisibleListener) {
        mOnVisibleListener = onVisibleListener;
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
