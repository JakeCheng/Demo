package com.example.android.demo.Ui.Activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.android.demo.Adapter.FragmentPagerAdapter;
import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.Base.BaseFragment;
import com.example.android.demo.MyView.ViewPageTabBar;
import com.example.android.demo.Presenter.MainPresenter;
import com.example.android.demo.R;
import com.example.android.demo.Service.NetworkReceiver;
import com.example.android.demo.Ui.Fragment.View1Fragment;
import com.example.android.demo.Ui.Fragment.View2Fragment;
import com.example.android.demo.View.MainView;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView,
        ViewPageTabBar.OnTabBarClickListener,ViewPager.OnPageChangeListener{
    @BindView(R.id.tab_bar)
    ViewPageTabBar mTabBar;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    private static final int RC_CAMERA_AND_WIFI = 10;
    private NetworkReceiver mNetworkReceiver;
    private FragmentPagerAdapter mPagerAdapter;
    private BaseFragment mCurrentFragment;
    private int mCurrentPageIndex;

    @Override
    public int getRootViewId() {
        return R.layout.activity_main;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNetworkReceiver = new NetworkReceiver();
        mNetworkReceiver.register(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Log.e(TAG, "initView: "+ Process.myUid());
        mTabBar.setOnTabBarClickListener(this);
        mPagerAdapter = new FragmentPagerAdapter(mCurrentFragment,this,getSupportFragmentManager(),mViewPager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void initPresenter() {
        mPresenter = new MainPresenter();
    }

    @Override
    public void initData() {
        mCurrentPageIndex = 0;
        mPresenter.initData(mCurrentPageIndex);
        initFragments();
    }

    private void initFragments() {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        mPagerAdapter.addFragment(getResources().getString(R.string.main_tab_cookbook), View1Fragment.class, null);
        mPagerAdapter.addFragment(getResources().getString(R.string.main_tab_movie), View2Fragment.class, null);
        mPagerAdapter.addFragment(getResources().getString(R.string.main_tab_composition), View2Fragment.class, null);
        mPagerAdapter.addFragment(getResources().getString(R.string.main_tab_data), View2Fragment.class, null);
        trans.commitAllowingStateLoss();
        if (mCurrentPageIndex != 0) {
            mViewPager.setCurrentItem(mCurrentPageIndex, false);
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetworkReceiver.unregister(this);
    }

    @Override
    public void onTabBarClick(int index) {
        mPresenter.onTabBarClick(index);
    }

    @Override
    public void onTabBarDoubleClick() {
        if (mCurrentFragment == null) {
            mCurrentFragment = (BaseFragment) mPagerAdapter.getFragment(mCurrentPageIndex, false);
            if (mCurrentFragment == null) {
                return;
            }
        }
    }

    @Override
    public void addTab(int titleId, int drawableId, boolean isFirst, boolean isLast) {
        mTabBar.addHomeTab(titleId, drawableId, isFirst, isLast);
    }

    @Override
    public void setTabDefaultSelect(int index) {
        mTabBar.setTabSelected(index);
        changeFragment(index, false);
    }

    @Override
    public void changeFragment(int index, boolean isScroll) {
        if (mCurrentPageIndex == index) {
            return;
        }
        mCurrentPageIndex = index;
        mCurrentFragment = (BaseFragment) mPagerAdapter.getFragment(index, false);
        mViewPager.setCurrentItem(index, isScroll);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPageIndex = position;
        if (null != mTabBar) {
            mTabBar.setTabSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
