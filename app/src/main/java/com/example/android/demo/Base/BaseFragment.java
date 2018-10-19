package com.example.android.demo.Base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.demo.MyApplication;

import butterknife.ButterKnife;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView{
    protected T mPresenter;
    protected View mRootView;
    protected BaseActivity mContext;
    protected MyApplication application;

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
}
