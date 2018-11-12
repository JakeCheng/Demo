package com.example.android.demo.Ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.android.demo.Adapter.FragmentPagerAdapter;
import com.example.android.demo.Base.BaseFragment;
import com.example.android.demo.Model.CookBookModel;
import com.example.android.demo.MyView.Vertical.CategoryVTabAdapter;
import com.example.android.demo.MyView.Vertical.TabView;
import com.example.android.demo.MyView.Vertical.VerticalTabLayout;
import com.example.android.demo.MyView.Vertical.VerticalViewPager;
import com.example.android.demo.R;

import butterknife.BindView;

/**
 * 菜单页面
 */

public class ViewCookBookFragment extends BaseFragment {
    @BindView(R.id.category_v_tablayout)
    VerticalTabLayout mTabLayout;
    @BindView(R.id.category_viewpager)
    VerticalViewPager mViewPager;
    @BindView(R.id.category_v_line)
    View mVLine;
    private FragmentPagerAdapter mAdapter;
    private CategoryVTabAdapter mVAdapter;
    private CookBookModel.ResultBean bean;
    @Override
    public void onLazyLoad() {
        mViewPager.setOffscreenPageLimit(bean.getList().size());
        mVLine.setVisibility(View.VISIBLE);
        initFragments();
        mVAdapter.upDate(bean.getList());
        mTabLayout.setTabAdapter(mVAdapter);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public int getRootViewId() {
        return R.layout.view_cook_book_fragment;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        if(bundle == null){
            return;
        }
        bean = bundle.getParcelable("data");
        mVLine.setVisibility(View.INVISIBLE);
        mAdapter = new FragmentPagerAdapter(this,mContext,getChildFragmentManager(),mViewPager);
        mViewPager.setAdapter(mAdapter);
        mVAdapter = new CategoryVTabAdapter(mContext);
        mTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                mViewPager.setCurrentItem(position);
                if(mAdapter.getCurrentPrimaryItem() instanceof ViewCookBookRightFragment){
                    ViewCookBookRightFragment fragment = (ViewCookBookRightFragment) mAdapter.getCurrentPrimaryItem();
                    fragment.recyclerViewScroll();
                }
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setTabSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    private void initFragments() {
        if(getActivity() == null || getActivity().isDestroyed()){
            return;
        }
        if(mAdapter.getCount() != 0){
            mAdapter.clearFragmens();
        }
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        int i = 0;
        for(CookBookModel.ResultBean.ListBean info : bean.getList()){
            addFragment(info, i);
            i++;
        }
        ft.commitAllowingStateLoss();
    }

    private void addFragment(CookBookModel.ResultBean.ListBean bean, int index) {
        Bundle bundle = new Bundle();
        bundle.putString("id",bean.getId());
        bundle.putInt("index", index);
        bundle.putString("title",bean.getName());
        mAdapter.addFragment(bean.getName(), ViewCookBookRightFragment.class, bundle);
    }
}
