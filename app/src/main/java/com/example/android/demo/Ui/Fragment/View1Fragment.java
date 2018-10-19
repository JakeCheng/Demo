package com.example.android.demo.Ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.demo.Adapter.FragmentPagerAdapter;
import com.example.android.demo.Base.BaseFragment;
import com.example.android.demo.Model.CookBookModel;
import com.example.android.demo.MyView.ViewPagerScrollTabBar;
import com.example.android.demo.Presenter.CookBookPresenter;
import com.example.android.demo.R;
import com.example.android.demo.Utils.ACache;
import com.example.android.demo.Utils.Constants;
import com.example.android.demo.Utils.NetWorkUtils;
import com.example.android.demo.View.CookBookView;
import com.google.gson.Gson;

import butterknife.BindView;

public class View1Fragment extends BaseFragment<CookBookPresenter> implements CookBookView,ViewPager.OnPageChangeListener{
    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tab_bar)
    ViewPagerScrollTabBar mTabBar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_bar_line)
    View mTabBarLine;
    private FragmentPagerAdapter mAdapter;
    private ACache mACache;
    private String cache;
    private CookBookModel bean;
    @Override
    public void onLazyLoad() {
        initDateGet();
    }

    private void initDateGet() {
        cache = mACache.getAsString(Constants.CookBookDate);
        if (!TextUtils.isEmpty(cache)){
            bean = new Gson().fromJson(cache,CookBookModel.class);
            initShow();
        }else {
            if (NetWorkUtils.isConnected(mContext)) {
                //获取标签信息
                mPresenter.initCookBookDateGet("");
            }
        }
    }

    private void initShow() {
        initViewPager();
        initTab();
    }

    @Override
    public int getRootViewId() {
        return R.layout.view_1_fragment;
    }

    @Override
    public void initView() {
        mACache = ACache.get(mContext);
        iv_left.setVisibility(View.GONE);
        tv_title.setText(getResources().getString(R.string.main_tab_cookbook));
    }

    private void initViewPager() {
        mAdapter = new FragmentPagerAdapter(this,mContext,getChildFragmentManager(),mViewPager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(bean.getResult().size());
        if (mAdapter.getCount() != 0) {
            mAdapter.clearFragmens();
        }
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        for (CookBookModel.ResultBean resultBean : bean.getResult()) {
            addFragment(resultBean);
        }
        ft.commitAllowingStateLoss();

    }

    private void addFragment(CookBookModel.ResultBean resultBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("data",resultBean);
        mAdapter.addFragment(resultBean.getName(),ViewCookBookFragment.class,bundle);

    }

    private void initTab() {
        mTabBar.setFakeBoldText(true);
        mTabBar.setTabLeftPadding(getResources().getDimensionPixelSize(R.dimen.activity_tab_bar_padding));
        mTabBar.setTabRightPadding(getResources().getDimensionPixelSize(R.dimen.activity_tab_bar_padding));

        mTabBar.setCustomTabColorizer(new ViewPagerScrollTabBar.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.color_14b9c7);
            }
        });

        mTabBar.setTitleColor(getResources().getColor(R.color.color_14b9c7), getResources().getColor(R.color.color_black_trans_40));
        mTabBar.setOnPageChangeListener(this);
        mTabBar.setIsDiffWithTab(true);
        mTabBar.setNeedMatchPrent(true);
        mTabBar.setViewPager(mViewPager);
    }

    @Override
    public void initPresenter() {
        mPresenter = new CookBookPresenter();
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTabBarLine.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCookBookDateGet(CookBookModel bean) {
        if (bean.getResultcode().equals("200")){
            this.bean = bean;
            mACache.put(Constants.CookBookDate,new Gson().toJson(bean));
            initShow();
        }
    }
}
