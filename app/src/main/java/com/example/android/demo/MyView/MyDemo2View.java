package com.example.android.demo.MyView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyDemo2View extends View{
    private Paint mPaint;
    private int mX = 1000;
    private Path mPath;
    private String text = "自定义View文字的设置";
    public MyDemo2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),measuredHeight(heightMeasureSpec));
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @SuppressLint("DrawAllocation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        //设置画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿   或者通过.setAntiAlias(true)设置
        mPaint.setColor(Color.RED);//画笔颜色
        mPaint.setStrokeWidth(2);//线条宽度
        mPaint.setTextSize(30);//文字大小
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTypeface(Typeface.DEFAULT);//文字格式 设置字体
        mPaint.setFakeBoldText(false);//设置文字是否使用粗体
        //mPaint.setStrikeThruText(true);//是否添加删除线
        //mPaint.setUnderlineText(true);//是否添加下划线
        //mPaint.setTextSkewX(0.5f);//添加倾斜度
        //mPaint.setTextScaleX(1);//文字横向缩放
        //mPaint.setLetterSpacing(0.2f);//字符间距
        //canvas.drawText(text,50,100,mPaint);//文字设置坐标是文字的左下角偏上一点

        mPath = new Path();
        mPath.addCircle(200,200,150,Path.Direction.CW);
        canvas.drawPath(mPath,mPaint);
        canvas.drawTextOnPath(text,mPath,50,10,mPaint);
    }

    private int measuredHeight(int measureSpec) {
        int result = mX;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode){
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                result = mX * 2 / 3;
                break;
        }
        return result;
    }
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode){
            case MeasureSpec.EXACTLY:
                mX = specSize;
                break;
        }
        return mX;
    }
}
