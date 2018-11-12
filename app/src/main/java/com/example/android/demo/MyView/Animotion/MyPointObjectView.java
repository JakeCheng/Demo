package com.example.android.demo.MyView.Animotion;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义弹性圆收缩效果
 * ObjectAnimator 调用
 */

public class MyPointObjectView extends View{

    private Point mCurPoint = new Point(100);

    public MyPointObjectView(Context context) {
        super(context);
    }

    public MyPointObjectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPointObjectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void setPointRadius(int radius){
        mCurPoint.setRadius(radius);
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurPoint != null){
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(300,300,mCurPoint.getRadius(),paint);
        }
    }
    public void doPointViewAnimation(MyPointObjectView mPointView){
        ObjectAnimator animator = ObjectAnimator.ofInt(mPointView, "pointRadius", 0, 300, 100);
        animator.setDuration(2000);
        animator.start();
    }
}
