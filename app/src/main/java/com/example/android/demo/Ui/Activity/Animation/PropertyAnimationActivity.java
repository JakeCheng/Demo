package com.example.android.demo.Ui.Activity.Animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.MyView.Animotion.MyPointObjectView;
import com.example.android.demo.MyView.Animotion.MyPointView;
import com.example.android.demo.R;
import com.example.android.demo.Utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 属性动画
 * Interpolator属性  修改变化值的规则
 */
public class PropertyAnimationActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_a_z)
    TextView tv_a_z;
    @BindView(R.id.mMyPointView)
    MyPointView mMyPointView;
    @BindView(R.id.mMyPointObjectView)
    MyPointObjectView mMyPointObjectView;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    private ValueAnimator mAnimator;
    private boolean isOpen = false;
    @Override
    public int getRootViewId() {
        return R.layout.activity_property_animation;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("属性动画");
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }
    @OnClick({R.id.iv_left,R.id.start,R.id.tv,R.id.btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.start:
                /**
                 * 移动
                 */
//                mAnimator = ValueAnimator.ofInt(300,500);
//                mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        int curValue = (int) animation.getAnimatedValue();
//                        tv.layout(curValue,curValue,curValue+tv.getWidth(),curValue+tv.getHeight());
//                    }
//                });


                /**
                 * 修改背景色
                 */
//                mAnimator = ValueAnimator.ofInt(0xffffff00,0xff0000ff);
//                mAnimator.setEvaluator(new ArgbEvaluator());
//                mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        int curValue = (int) animation.getAnimatedValue();
//                        tv.setBackgroundColor(curValue);
//                    }
//                });

                /**
                 * 字母从A到Z转变
                 */
//                mAnimator = ValueAnimator.ofObject(new CharEvaluator(),new Character('A'),new Character('Z'));
//                mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        char curValue = (char) animation.getAnimatedValue();
//                        tv_a_z.setText(String.valueOf(curValue));
//                    }
//                });



//                mAnimator.setDuration(5000);
//                //设置插值器（前期慢 后期快）
//                mAnimator.setInterpolator(new AccelerateInterpolator());
//                //设置动画运行次数
//                mAnimator.setRepeatCount(1);
//                //延时运行
//                mAnimator.setStartDelay(1000);
//                mAnimator.start();
                /**
                 * 自定义弹性圆收缩效果
                 * ValueAnimator 调用
                 */
                // mMyPointView.doPointAnim();
                mMyPointObjectView.doPointViewAnimation(mMyPointObjectView);

                break;
            case R.id.tv:
                ToastUtils.showPhone(mContext,"点击前进了",1);
                break;
            case R.id.btn:
                if (!isOpen){
                    isOpen = !isOpen;
                    doAnimotionOpen(btn1,0,5,300);//(控件，当前index,总的index,圆的半径)
                    doAnimotionOpen(btn2,1,5,300);
                    doAnimotionOpen(btn3,2,5,300);
                    doAnimotionOpen(btn4,3,5,300);
                    doAnimotionOpen(btn5,4,5,300);
                }else{
                    isOpen = !isOpen;
                    doAnimotionClose(btn1,0,5,300);
                    doAnimotionClose(btn2,1,5,300);
                    doAnimotionClose(btn3,2,5,300);
                    doAnimotionClose(btn4,3,5,300);
                    doAnimotionClose(btn5,4,5,300);
                }
                break;
        }
    }

    /**
     * 关闭动画展示
     * @param view
     * @param index
     * @param total
     * @param radius
     */
    private void doAnimotionClose(View view, int index, int total, int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.PI * index / ((total - 1) * 2);
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));

        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.1f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.1f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0));
        //动画周期为500ms
        set.setDuration(1 * 500).start();
    }

    /**
     * 开始动画展示
     * @param view
     * @param index
     * @param total
     * @param radius
     */
    private void doAnimotionOpen(View view, int index, int total, int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.toRadians(90)/(total - 1) * index;
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));

        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
        //动画周期为500ms
        set.setDuration(1 * 500).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAnimator != null){
            mAnimator.cancel();
        }
    }
}
