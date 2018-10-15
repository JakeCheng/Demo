package com.example.android.demo.MyView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.android.demo.R;


public class LoadingView extends RelativeLayout {

    private ImageView mLoadingImageView = null;
    private ObjectAnimator mObjectAnimator;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.loadingview_layout, this, true);
        initViews();
    }

    protected void initViews() {
        mLoadingImageView = findViewById(R.id.loading);

        mObjectAnimator = ObjectAnimator.ofFloat(mLoadingImageView,"rotation",0,719);
        mObjectAnimator.setDuration(1600);
        mObjectAnimator.setRepeatCount(-1);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
    }

    public void reLayoutInflater (int layout_id){
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(layout_id, this, true);
        mLoadingImageView = findViewById(R.id.loading);
    }


    public void showCenterProgress(){
        if(null != mLoadingImageView){
            mLoadingImageView.setVisibility(View.VISIBLE);
        }
        if(mLoadingImageView != null){
            mObjectAnimator.start();
        }
    }

    public void cancelCenterProgress(){
        if(null != mLoadingImageView){
            mLoadingImageView.setVisibility(View.GONE);
        }
        if(mLoadingImageView != null){
            mObjectAnimator.cancel();
        }
    }

}
