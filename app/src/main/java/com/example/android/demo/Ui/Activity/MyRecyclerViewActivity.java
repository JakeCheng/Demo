package com.example.android.demo.Ui.Activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.android.demo.Adapter.DemoItemTypeAdapter;
import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.Bean.DemoItemBean;
import com.example.android.demo.MyView.CustomLoadMoreView;
import com.example.android.demo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyRecyclerViewActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    DemoItemBean bean;
    List<DemoItemBean.DateBean> listDate = new ArrayList<>();
    DemoItemBean.DateBean itemBean;
    //DemoAdapter mAdapter;
    DemoItemTypeAdapter mAdapter;
    LinearLayoutManager layoutManager;
    View headerView;

    @Override
    public int getRootViewId() {
        return R.layout.activity_my_recycler_view;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("上拉加载更多");
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {
        bean = new DemoItemBean();
        bean.setErrCode(200);
        for (int i =0;i<10;i++){
            itemBean = new DemoItemBean.DateBean();
            itemBean.setName("张三"+i);
            itemBean.setAge(i+10);
            if (i % 2 == 1) {
                itemBean.setType(DemoItemBean.ITEM_TYPE_1);
            } else {
                itemBean.setType(DemoItemBean.ITEM_TYPE_2);
            }
            listDate.add(itemBean);
        }
        bean.setData(listDate);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        //单一一种item
        //mAdapter = new DemoAdapter(R.layout.item_demo,bean.getData());
        //多种Item
        mAdapter = new DemoItemTypeAdapter(bean.getData());
        //添加头部HeaderView
        //headerView = LayoutInflater.from(this).inflate(R.layout.headerview,null);
        //mAdapter.addHeaderView(headerView);
        mRecyclerView.setAdapter(mAdapter);
        //预加载 滑动到倒数第1个时回调加载
        mAdapter.setPreLoadNumber(1);
        //自定义加载更多布局
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        // 没有数据的时候默认显示该布局
        //mAdapter.setEmptyView(getView());
        //设置颜色  好像不管用....
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary,R.color.color_red,R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listDate != null){
                    listDate.clear();
                }
                for (int i =0;i<10;i++){
                    itemBean = new DemoItemBean.DateBean();
                    itemBean.setName("张三"+i);
                    itemBean.setAge(i+10);
                    if (i % 2 == 1) {
                        itemBean.setType(DemoItemBean.ITEM_TYPE_1);
                    } else {
                        itemBean.setType(DemoItemBean.ITEM_TYPE_2);
                    }
                    listDate.add(itemBean);
                }
                //  * 如果数据加载完成 下拉刷新后需要设置
                mAdapter.setNewData(listDate);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //数据全部加载完毕
                        if (listDate.size()>=50) {
                            mAdapter.loadMoreEnd();
                        }else{
                            for (int i = 0;i<5;i++){
                                itemBean = new DemoItemBean.DateBean();
                                itemBean.setName("李四"+i);
                                itemBean.setAge(i+20);
                                if (i % 2 == 1) {
                                    itemBean.setType(DemoItemBean.ITEM_TYPE_1);
                                } else {
                                    itemBean.setType(DemoItemBean.ITEM_TYPE_2);
                                }
                                listDate.add(itemBean);
                            }
                            mAdapter.loadMoreComplete();
                        }
                    }
                },1000);
            }
        },mRecyclerView);


    }
    @OnClick({R.id.iv_left})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }
    }
    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCompleted() {

    }
}
