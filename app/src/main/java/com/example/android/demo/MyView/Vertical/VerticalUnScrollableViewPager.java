package com.example.android.demo.MyView.Vertical;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不跟着滑动
 */

public class VerticalUnScrollableViewPager extends VerticalViewPager{
    private boolean mPageScrollEnable = true;

    public VerticalUnScrollableViewPager(Context context) {
        super(context);
    }

    public VerticalUnScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPageScrollEnable( boolean enable ){
        mPageScrollEnable = enable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if( !mPageScrollEnable ){
            return false;
        }else {
            return super.onInterceptTouchEvent(ev);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if( !mPageScrollEnable ){
            return false;
        }else {
            return super.onTouchEvent(ev);
        }
    }
}
