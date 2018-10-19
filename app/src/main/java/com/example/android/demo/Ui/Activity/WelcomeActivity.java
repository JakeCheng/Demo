package com.example.android.demo.Ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.R;
import com.example.android.demo.Utils.SharePreferenceUtil;

import butterknife.BindView;

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    private boolean firstOpen;
    private Animation animation;
    private Handler h;
    @Override
    public int getRootViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        startNextActivity();
    }

    private void startNextActivity() {

        firstOpen = SharePreferenceUtil.getFirstOpen(mContext);

        animation = AnimationUtils.loadAnimation(mContext, R.anim.big);
        animation.setInterpolator(new DecelerateInterpolator());
        iv_logo.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                iv_logo.setVisibility(View.VISIBLE);
                h=new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (firstOpen) {
                            SharePreferenceUtil.setFirstOpen(mContext, false);
                            startActivity(new Intent(mContext, LoginActivity.class));
                        } else {
                            if (!TextUtils.isEmpty(SharePreferenceUtil.getEmail(mContext))) {
                                startActivity(new Intent(mContext,MainActivity.class));
                            } else {
                                startActivity(new Intent(mContext, LoginActivity.class));
                            }
                        }
                        finish();
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

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
