package com.example.android.demo.MyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.demo.Interface.ProgressNotifiable;
import com.example.android.demo.Interface.Refreshable;
import com.example.android.demo.R;
import com.example.android.demo.Utils.Logger;
import com.example.android.demo.Utils.NetWorkUtils;
import com.example.android.demo.Utils.NetworkSuccessStatus;

public class EmptyLoadingView extends RelativeLayout implements ProgressNotifiable {

    private static final String TAG = "EmptyLoadingView";
    protected EmptyView mEmptyView = null;
    protected LoadingView mLoadingView = null;
    private boolean mAnimation = true;
    private CharSequence mText;

    private EmptyLoadingViewListener mEmptyLoadingViewListener;

    private boolean isShowToast = true;

    public interface EmptyLoadingViewListener {
        void emptyViewIsShow(boolean isShow);
    }

    public void setEmptyLoadingViewListener(EmptyLoadingViewListener listener) {
        mEmptyLoadingViewListener = listener;
    }

    public EmptyLoadingView(Context context) {
        super(context);
        initViews(context);
    }

    public EmptyLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    protected void initViews(Context context){
        LayoutInflater.from(context).inflate(R.layout.empty_loading, this, true);

        mLoadingView = findViewById(R.id.loading_view);
        mEmptyView = findViewById(R.id.empty_display);
        mText = getResources().getString(R.string.no_content);
    }

    public void reLoadingViewLayoutInflater (int layout_id){
        mLoadingView.reLayoutInflater(layout_id);
    }

    public void setAnimationable(boolean hasAnimation) {
        this.mAnimation = hasAnimation;
    }

    public void setCustomEmptyView(View view)
    {
        if(null != mEmptyView)
        {
            mEmptyView.setCustomEmptyView(view);
        }
    }

    @Override
    public void startLoading(boolean hasData,boolean showProgress) {
        mEmptyView.setVisibility(GONE);
        showProgressView(hasData, showProgress);

        showView(this);
    }

    @Override
    public void stopLoading(boolean hasData, boolean hasNext) {
        if (!hasNext) {
            if (hasData) {
                showProgressView(hasData, false);//XXX 需要加这个 否则圆圈会一直转
                hideView(this);
            } else {
                showView(this);
                showProgressView(hasData, false);
                mEmptyView.setVisibility(View.VISIBLE);
                if(mEmptyLoadingViewListener != null){
                    mEmptyLoadingViewListener.emptyViewIsShow(true);
                }
            }
        }
    }
    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            boolean hasData = msg.arg1 == 0 ? false : true;
            NetworkSuccessStatus err = (NetworkSuccessStatus) msg.obj;
            int pageIndex = msg.arg2;
            stopLoading(hasData, pageIndex, err);
        }
    };
    @Override
    public void stopHandleMessage(Message msg) {
        mHandler.sendMessage(msg);
    }

    public void setTextSuccessDefault(String text) {
        mEmptyView.setEmptyText(text);
    }

    public void setEmptyDrawable(Drawable drawable) {
        mEmptyView.setEmptyDrawable(drawable);
    }


    public void setEmptyText(CharSequence text, boolean forceShowEmptyView) {
        if(forceShowEmptyView) {
            mEmptyView.setVisibility(VISIBLE);
        }
        mEmptyView.setEmptyText(text);
        mText = text;
    }

    public void setEmptyText(CharSequence text) {
        this.setEmptyText(text, true);
    }

    public void setRefreshable(Refreshable refreshable) {
        mEmptyView.setRefreshable(refreshable);
    }

    public void showEmptyView(){
        String textNoActiveNetWork = getResources().getString(R.string.no_network_connect);
        stopLoading(false, false);
        noNetwork(textNoActiveNetWork);
    }

    public EmptyView getEmptyView() {
        return mEmptyView;
    }

    public void hideEmptyView() {
        mEmptyView.setVisibility(GONE);
    }

    public void stopLoading(boolean hasData, int pageIndex, NetworkSuccessStatus err) {
        switch (err) {
            case FIRST_REQUEST:
                stopLoading(hasData, false);
                break;
            case IO_ERROR:
                String textNoActiveNetWork = getResources().getString(R.string.no_network_connect);
                Logger.error(TAG,"IO_ERROR");
                if (pageIndex > 1) {
                    stopLoading(true, false);
                } else {
                    if(!hasData){
                        stopLoading(false, false);
                        noNetwork(textNoActiveNetWork);
                    }else{
                        stopLoading(hasData,false);
                    }
                }
                if(isShowToast){
                    Toast.makeText(getContext(),textNoActiveNetWork, Toast.LENGTH_SHORT);
                }
                break;
            case NO_ANYMORE:
                stopLoading(true, false);
                break;
            case OK:
                stopLoading(hasData, false);
                mEmptyView.showViewForEmptyResult();
                break;
            case RESULT_EMPTY_ERROR:
                Logger.error(TAG,"RESULT_EMPTY_ERROR");
                stopLoading(hasData, false);
                mEmptyView.setEmptyText(mText);
                mEmptyView.showViewForEmptyResult();
                break;
            default:
                stopLoading(true, false);
                break;
        }
    }

    protected void noNetwork(CharSequence textNoActiveNetWork) {

        stopLoading(false, false);
        if(!NetWorkUtils.isConnected(getContext().getApplicationContext())) {
            mEmptyView.setEmptyText(getResources().getString(R.string.network_connect_error));
            mEmptyView.setEmptyText(textNoActiveNetWork);
            mEmptyView.setEmptyDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        }else{
            mEmptyView.setEmptyDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
            mEmptyView.setEmptyText(getResources().getString(R.string.server_in_error));
        }
    }

    @Override
    public void init(boolean hasData, boolean isLoading) {
        if (isLoading) {
            setVisibility(VISIBLE);
            showProgressView(hasData, true);
            mEmptyView.setVisibility(GONE);
        } else {
            if (hasData) {
                setVisibility(GONE);
            } else {
                setVisibility(VISIBLE);
                showProgressView(hasData, false);
                mEmptyView.setVisibility(VISIBLE);
                if(mEmptyLoadingViewListener != null){
                    mEmptyLoadingViewListener.emptyViewIsShow(true);
                }
            }
        }
    }


    private void showProgressView(boolean hasData, boolean visibile) {
        if (visibile) {
            mLoadingView.setVisibility(VISIBLE);
            mLoadingView.showCenterProgress();
        } else {
            mLoadingView.cancelCenterProgress();
            mLoadingView.setVisibility(GONE);
        }
    }

    private void hideView(View view) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() == VISIBLE) {
            if (view.isShown()) {
                if (mAnimation) {
                    view.startAnimation(AnimationUtils
                            .loadAnimation(getContext(), R.anim.disappear));
                }
            }
            view.setVisibility(GONE);
        }
    }


    private void showView(View view) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() == GONE) {
            if (mAnimation) {
                view.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.appear));
            }
            view.setVisibility(VISIBLE);
        }
    }


    public void showLoadingView(){
        startLoading(false,true);
    }

    public void stopLoadingView(){
        stopLoading(true,false);
    }

    public TextView getEmptyButton() {
        return mEmptyView.getEmptyButtom();
    }

    public void setOnCustomActionButtonClickListener(EmptyView.OnCustomActionButtonClickListener l) {
        if(mEmptyView != null) {
            mEmptyView.setOnCustomActionButtonClickListener(l);
        }
    }

    public void setShowToast(boolean showToast) {
        isShowToast = showToast;
    }
}
