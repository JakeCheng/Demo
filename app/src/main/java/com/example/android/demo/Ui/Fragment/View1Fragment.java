package com.example.android.demo.Ui.Fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.android.demo.Adapter.View1Adapter;
import com.example.android.demo.Base.BaseFragment;
import com.example.android.demo.R;
import com.example.android.demo.Ui.Activity.MyRecyclerViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by android on 2018/10/15.
 */

public class View1Fragment extends BaseFragment {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private GridLayoutManager layoutManager;
    private List<String> listDate;
    private View1Adapter mAdapter;
    @Override
    public void onLazyLoad() {

    }
    @Override
    public int getRootViewId() {
        return R.layout.view_1_fragment;
    }

    @Override
    public void initView() {
        mSwipeRefreshLayout.setEnabled(false);
        layoutManager = new GridLayoutManager(mContext,2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext,DividerGridItemDecoration.GRID_DIVIDER_VERTICAL));

        listDate = new ArrayList<>();
        listDate.add("上拉加载更多");

        mAdapter = new View1Adapter(R.layout.item_view1,listDate);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(mContext, MyRecyclerViewActivity.class));
                        break;
                }
            }
        });
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
