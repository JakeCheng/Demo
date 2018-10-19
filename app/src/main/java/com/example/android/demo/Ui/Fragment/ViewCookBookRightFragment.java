package com.example.android.demo.Ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.android.demo.Adapter.CookBookRightAdapter;
import com.example.android.demo.Base.BaseFragment;
import com.example.android.demo.Model.CookBookRightModel;
import com.example.android.demo.Presenter.CookBookRightPresenter;
import com.example.android.demo.R;
import com.example.android.demo.Ui.Activity.CookBookDetailActivity;
import com.example.android.demo.Utils.ACache;
import com.example.android.demo.Utils.Constants;
import com.example.android.demo.Utils.NetWorkUtils;
import com.example.android.demo.View.CookBookRightView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 菜单页面
 */

public class ViewCookBookRightFragment extends BaseFragment<CookBookRightPresenter> implements CookBookRightView{
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private String id,name;
    private int index;
    private GridLayoutManager layoutManager;
    private List<CookBookRightModel.ResultBean.DataBean> listDate = new ArrayList<>();
    private CookBookRightAdapter mAdapter;
    private ACache mACache;
    private String cache;
    private CookBookRightModel bean;
    private Intent intent;
    private View view;
    private TextView tv_content;
    @Override
    public void onLazyLoad() {
        initDateGet();
    }

    private void initDateGet() {
        cache = mACache.getAsString(Constants.CookBookDate+id);
        if (!TextUtils.isEmpty(cache)){
            bean = new Gson().fromJson(cache,CookBookRightModel.class);
            initShow();
        }else {
            if (NetWorkUtils.isConnected(mContext)) {
                mPresenter.initCookBookRightDateGet(id);
            }
        }
    }

    @Override
    public int getRootViewId() {
        return R.layout.view_cook_book_right_fragment;
    }

    @Override
    public void initView() {
        mACache = ACache.get(mContext);
        view = LayoutInflater.from(mContext).inflate(R.layout.headerview,null);
        tv_content = view.findViewById(R.id.tv_content);
        Bundle bundle = getArguments();
        if(bundle == null){
            return;
        }
        id = bundle.getString("id");
        index = bundle.getInt("index");
        name = bundle.getString("title");
        layoutManager = new GridLayoutManager(mContext,2);
        mRecyclerView.setLayoutManager(layoutManager);

    }
    public void recyclerViewScroll(){
        if(null != mRecyclerView){
            mRecyclerView.scrollToPosition(0);
        }
    }
    @Override
    public void initPresenter() {
        mPresenter = new CookBookRightPresenter();
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
    public void onCookBookRightDateGet(CookBookRightModel bean) {
        this.bean = bean;
        mACache.put(Constants.CookBookDate+id,new Gson().toJson(bean));
        initShow();
    }

    private void initShow() {
        if (bean.getResultcode().equals("200")){
            listDate = bean.getResult().getData();
            mAdapter = new CookBookRightAdapter(R.layout.item_cook_book_right,listDate);
            mRecyclerView.setAdapter(mAdapter);
            tv_content.setText(name);
            mAdapter.addHeaderView(view);
            if (mAdapter != null){
                mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        intent = new Intent(mContext, CookBookDetailActivity.class);
                        intent.putExtra("data", listDate.get(position));
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
