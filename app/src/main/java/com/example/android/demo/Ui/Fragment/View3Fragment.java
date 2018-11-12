package com.example.android.demo.Ui.Fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.demo.Base.BaseFragment;
import com.example.android.demo.R;
import com.example.android.demo.Ui.Activity.Animation.PropertyAnimationActivity;
import com.example.android.demo.Ui.Activity.Animation.TweenAnimationActivity;
import com.example.android.demo.Ui.Activity.Animation.MoveActivity;
import com.example.android.demo.Ui.Activity.Animation.MyViewActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class View3Fragment extends BaseFragment {
    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    public void onLazyLoad() {

    }

    @Override
    public int getRootViewId() {
        return R.layout.view_3_fragment;
    }

    @Override
    public void initView() {
        iv_left.setVisibility(View.GONE);
        tv_title.setText("自定义View");
    }
    @OnClick({R.id.btn_tween_animation,R.id.btn_property_animation,R.id.btn_view,R.id.btn_move})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tween_animation:
                startActivity(new Intent(mContext, TweenAnimationActivity.class));
                break;
            case R.id.btn_property_animation:
                startActivity(new Intent(mContext, PropertyAnimationActivity.class));
                break;
            case R.id.btn_view:
                startActivity(new Intent(mContext, MyViewActivity.class));
                break;
            case R.id.btn_move:
                startActivity(new Intent(mContext, MoveActivity.class));
                break;
        }
    }
    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }
}
