package com.example.android.demo.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.demo.Interface.Refreshable;
import com.example.android.demo.R;


public class EmptyView extends LinearLayout {
    private OnCustomActionButtonClickListener mOnCustomActionButtonClickListener;

    protected ImageView mImageView = null;
    protected TextView mButton = null;
    protected TextView mTextView = null;

    private Refreshable mRefreshable = null;

    private LinearLayout mNormalLayout = null;
    private LinearLayout mContainerLayout = null;

    private View mCustomEmptyView = null;


    public EmptyView(Context context) {
        super(context);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    protected void init(Context context) {
        View.inflate(context, R.layout.empty_view_layout, this);
        initViews();
    }

    protected void initViews() {
        mImageView = findViewById(R.id.image_view);
        mButton = findViewById(R.id.action_button);
        mTextView = findViewById(R.id.text);

        mButton.setOnClickListener(mOnRefreshListener);
        mButton.setVisibility(View.GONE);

        mNormalLayout = findViewById(R.id.normal_empty_view);
        mContainerLayout = findViewById(R.id.normal_empty_view_1);
    }

    public void setEmptyDrawable(Drawable drawable) {
        if (null != mImageView) {
            mImageView.setImageDrawable(drawable);
        }
    }

    public void setEmptyText(CharSequence text) {
        if (null != mTextView) {
            mTextView.setText(text);
        }
    }

    public CharSequence getEmptyText() {
        return mTextView.getText();
    }

    public void setRefreshable(Refreshable refreshable) {
        this.mRefreshable = refreshable;
        if (null != mRefreshable) {
            mButton.setVisibility(View.VISIBLE);
        }
    }

    protected OnClickListener mOnRefreshListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mOnCustomActionButtonClickListener != null) {
                mOnCustomActionButtonClickListener.onCustomActionButtonClick();
                return;
            }

            if (mRefreshable != null) {
                mRefreshable.refreshData();
            }
        }
    };

    public void setCustomEmptyView(View view) {
        mCustomEmptyView = view;
    }

    public void showViewForEmptyResult() {
        if (null != mCustomEmptyView) {
            mNormalLayout.setVisibility(View.GONE);
            mContainerLayout.removeAllViews();
            mContainerLayout.addView(mCustomEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        } else {
            setShowRefreshButton(false);
        }
    }

    public void setShowRefreshButton(boolean show) {
        if (null != mButton) {
            if (show) {
                mButton.setVisibility(View.VISIBLE);
            } else {
                mButton.setVisibility(View.GONE);
            }
        }
    }

    public void setDarkTheme() {
        //mTextView.setTextColor(getResources().getColor(R.color.color_black_trans_50));
       // mButton.setBackgroundResource(R.drawable.bg_corner_12_14b9c7_selector);
       // mButton.setTextColor(getResources().getColorStateList(R.color.white));
        //mImageView.setImageResource(R.drawable.empty_network_error_icon);
    }

    public TextView getEmptyButtom() {
        return mButton;
    }

    public void setOnCustomActionButtonClickListener(OnCustomActionButtonClickListener l) {
        this.mOnCustomActionButtonClickListener = l;
    }

    public interface OnCustomActionButtonClickListener {
        void onCustomActionButtonClick();
    }
}
