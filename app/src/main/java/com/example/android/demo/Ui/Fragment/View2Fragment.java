package com.example.android.demo.Ui.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.demo.Base.BaseFragment;
import com.example.android.demo.R;

import butterknife.BindView;


public class View2Fragment extends BaseFragment{
    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onLazyLoad() {

    }

    @Override
    public int getRootViewId() {
        return R.layout.activity_my_recycler_view;
    }

    @Override
    public void initView() {
        iv_left.setVisibility(View.GONE);
        tv_title.setText(getResources().getString(R.string.main_tab_movie));
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }
}
