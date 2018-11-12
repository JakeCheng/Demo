package com.example.android.demo.Ui.Activity.Animation;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 属性动画的基本操作，不改变控件的位置（点击事件还是在原来位置）
 *
 * Interpolator属性  修改变化值的规则
 * AccelerateDecelerateInterpolator   在动画开始与介绍的地方速率改变比较慢，在中间的时候加速   @android:anim/accelerate_decelerate_interpolator
 * AccelerateInterpolator             在动画开始的地方速率改变比较慢，然后开始加速            @android:anim/accelerate_interpolator
 * AnticipateInterpolator             开始的时候向后然后向前甩                              @android:anim/anticipate_interpolator
 * AnticipateOvershootInterpolator    开始的时候向后然后向前甩一定值后返回最后的值            @android:anim/anticipate_overshoot_interpolator
 * BounceInterpolator                 动画结束的时候弹起                                   @android:anim/bounce_interpolator
 * CycleInterpolator                  动画循环播放特定的次数，速率改变沿着正弦曲线            @android:anim/cycle_interpolator
 * DecelerateInterpolator             在动画开始的地方快然后慢                              @android:anim/decelerate_interpolator
 * LinearInterpolator                 以常量速率改变                                       @android:anim/linear_interpolator
 * OvershootInterpolator              向前甩一定值后再回到原来位置                          @android:anim/overshoot_interpolator
 */
public class TweenAnimationActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv)
    TextView tv;
    private Animation animation;
    @Override
    public int getRootViewId() {
        return R.layout.activity_tween_animation;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("补间动画");
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }
    @OnClick({R.id.iv_left,R.id.alpha,R.id.scale,R.id.translate,R.id.rotate,R.id.set})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.alpha:
                //透明度

                //第一种方式 .xml
                animation = AnimationUtils.loadAnimation(mContext,R.anim.demo_tween_alpha);

                //第二种方式
                animation = new AlphaAnimation(1.0f,0.1f);
                animation.setDuration(1000);
                animation.setFillBefore(true);
                //修改动画效果
                //animation.setInterpolator(new AnticipateInterpolator());
                tv.startAnimation(animation);


                break;
            case R.id.scale:
                //缩放

                //第一种方式
                animation = AnimationUtils.loadAnimation(mContext,R.anim.demo_tween_scale);
                //第二种方式
                animation = new ScaleAnimation(0.0f,1.4f,0.0f,1.4f);
                animation.setDuration(1000);

                tv.startAnimation(animation);
                break;
            case R.id.translate:
                //移动
                animation = AnimationUtils.loadAnimation(mContext,R.anim.demo_tween_translate);
                tv.startAnimation(animation);
                break;
            case R.id.rotate:
                //旋转
                animation = AnimationUtils.loadAnimation(mContext,R.anim.demo_tween_rotate);
                tv.startAnimation(animation);
                break;
            case R.id.set:
                animation = AnimationUtils.loadAnimation(mContext,R.anim.demo_tween_set);
                tv.startAnimation(animation);
                break;
        }
    }
}
