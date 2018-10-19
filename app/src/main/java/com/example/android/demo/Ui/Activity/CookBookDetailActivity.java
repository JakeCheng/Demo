package com.example.android.demo.Ui.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.android.demo.Adapter.CookBookDetailAdapter;
import com.example.android.demo.Adapter.TagsAdapter;
import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.Model.CookBookRightModel;
import com.example.android.demo.MyView.TabCloud.TagCloudLayout;
import com.example.android.demo.R;
import com.example.android.demo.Utils.Constants;
import com.example.android.demo.Utils.ImageLoaderHelper;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;

public class CookBookDetailActivity extends BaseActivity {
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.mTagCloudLayout)
    TagCloudLayout mTagCloudLayout;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_main)
    TextView tv_main;
    @BindView(R.id.tv_seasoning)
    TextView tv_seasoning;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private CookBookRightModel.ResultBean.DataBean bean;
    private CookBookDetailAdapter mAdapter;
    private TagsAdapter tAdapter;
    private String[] tagDate;
    private Intent intent;
    private ArrayList<String> images = new ArrayList<>();
    @Override
    public int getRootViewId() {
        return R.layout.activity_cook_book_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        bean = getIntent().getParcelableExtra("data");
        for (int i =0;i<bean.getSteps().size();i++){
            images.add(bean.getSteps().get(i).getImg());
        }
        Log.e(TAG, "initView: 打印当前的数据："+new Gson().toJson(bean));
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationIcon(R.mipmap.back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        mCollapsingToolbarLayout.setTitle(bean.getTitle());
        ImageLoaderHelper.display(mContext,iv_image,bean.getAlbums().get(0));
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);//设置收缩后Toolbar上字体的颜色

    }
    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {
        tv_content.setText("简介："+bean.getImtro());
        tv_main.setText("主要材料："+bean.getIngredients());
        tv_seasoning.setText("调味料："+bean.getBurden());

        tagDate = bean.getTags().split("[;]");
        tAdapter = new TagsAdapter(mContext,tagDate);
        mTagCloudLayout.setAdapter(tAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CookBookDetailAdapter(R.layout.item_cook_book_detail,bean.getSteps());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.iv:
                        intent = new Intent(mContext,ShowWebImageActivity.class);
                        intent.putExtra(Constants.IMAGE_URL,bean.getSteps().get(position).getImg());
                        intent.putStringArrayListExtra(Constants.IMAGE_URL_ALL,images);
                        startActivity(intent);
                        break;
                }
            }
        });

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCompleted() {

    }
}
