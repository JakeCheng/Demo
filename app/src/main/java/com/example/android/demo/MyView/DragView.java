package com.example.android.demo.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

/**
 * Created by android on 2018/10/9.
 * 自定义跟随滑动View
 */
public class DragView extends android.support.v7.widget.AppCompatTextView {

    private int lastX;
    private int lastY;

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 屏幕高度
     */
    private int mScreenHeight;
    /**
     * 状态栏高度
     */
    private final static int STATUS_HEIGHT = 48;
    private int left,top,right,bottom;

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragView(Context context) {
        super(context);
        init(context);
    }
    private void init(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels - STATUS_HEIGHT;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取当前触摸的绝对坐标
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 上一次离开时的坐标
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                // 两次的偏移量
                int offsetX = rawX - lastX;
                int offsetY = rawY - lastY;
                moveView(offsetX, offsetY);
                // 不断修改上次移动完成后坐标
                lastX = rawX;
                lastY = rawY;
                break;
            default:
                break;
        }
        return true;
    }

    private void moveView(int offsetX, int offsetY) {
        // 方法一
        left = getLeft() + offsetX;
        top = getTop() + offsetY;
        right = getRight()+ offsetX;
        bottom = getBottom()+ offsetY;
        if (left < 0) {
            left = 0;
            right = left + getWidth();
        }

        if (right > mScreenWidth) {
            right = mScreenWidth;
            left = right - getWidth();
        }

        if (top < 0) {
            top = 0;
            bottom = top + getHeight();
        }

        if (bottom > mScreenHeight) {
            bottom = mScreenHeight;
            top = bottom - getHeight();
        }
        layout(left,top,right,bottom);

        // 方法二
        //offsetLeftAndRight(offsetX);
        //offsetTopAndBottom(offsetY);

        // 方法三
        // LinearLayout.LayoutParams layoutParams = (LayoutParams)
        // getLayoutParams();
        // layoutParams.leftMargin = getLeft() + offsetX;
        // layoutParams.topMargin = getTop() + offsetY;
        // setLayoutParams(layoutParams);

        // 方法四
        // ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams)
        // getLayoutParams();
        // layoutParams.leftMargin = getLeft() + offsetX;
        // layoutParams.topMargin = getLeft() + offsetY;
        // setLayoutParams(layoutParams);

        // 方法五
        //((View) getParent()).scrollBy(-offsetX, -offsetY);
    }
}
