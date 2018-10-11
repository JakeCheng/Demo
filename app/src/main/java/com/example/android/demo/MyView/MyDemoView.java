package com.example.android.demo.MyView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.android.demo.R;

public class MyDemoView extends View{
    private Paint mPaint;
    private int mX = 1000;
    private Path mPath;
    private Shader mShader;
    private PathEffect mPathEffect;
    public MyDemoView(Context context, @Nullable AttributeSet attrs) {
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
        mPaint.setStyle(Paint.Style.STROKE);//绘制模式
        mPaint.setStrokeWidth(10);//线条宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置点/线头的类型    1、BUTT 正方形   2、ROUND 圆形   3、SQUARE 正方形
        mPaint.setStrokeJoin(Paint.Join.ROUND);//设置拐角的形状 1、MITER 尖角  2、BEVEL 平角  3、ROUND 圆角  默认MITER
        // mPaint.setStrokeMiter();//对于.setStrokeJoin()的一个补充，它用于设置 MITER 型拐角的延长线的最大值
        mPaint.setTextSize(20);//文字大小
        //mPaint.setShadowLayer(10,0,0,Color.parseColor("#00FF00"));//设置阴影       取消：clearShadowLayer()
        //canvas.drawColor(getResources().getColor(R.color.color_red));//设置整个画布的颜色 覆盖之前的颜色
        //画圆
        mPaint.setStyle(Paint.Style.FILL);//绘制模式   1、STROKE 空心  2、FILL 实心
        canvas.drawCircle(40,40,30,mPaint);//（X轴坐标，Y轴坐标，半径，画笔）
        //矩形
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.STROKE);//绘制模式   1、STROKE 空心  2、FILL 实心
        canvas.drawRect(80,10,160,90,mPaint);//(Left,Top,Right,Bottom,画笔)
        //画点    设置绘制模式没用
        mPaint.setStrokeWidth(20);
        mPaint.setColor(Color.BLACK);
        //canvas.drawPoint(200,40,mPaint);//  画一个点(X轴坐标，Y轴坐标，画笔)
        float[] points = {200, 40, 260, 40, 200, 100, 260, 100};//坐标点集合  读取两个分别作为X/Y坐标
        canvas.drawPoints(points,2,6,mPaint);//画多个点  (坐标集合，跳过几个，一共画几个点（这个数是画的个数*2  读取集合的个数），画笔)
        //画椭圆
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL);//绘制模式   1、STROKE 空心  2、FILL 实心
        canvas.drawOval(300,10,450,100,mPaint);//(Left,Top,Right,Bottom,画笔)
        //画线  setStyle(style)没影响
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(10);
        canvas.drawLine(480,10,530,100,mPaint);// 单条线(startX，startY，stopX，stopY，画笔)
        //canvas.drawLines();和画多个点类似
        //画圆角矩形
        mPaint.setColor(Color.RED);
        canvas.drawRoundRect(550,10,700,100,50,50,mPaint);//(Left，Top，Right，Bottom，圆角的横向半径，圆角的纵向半径，画笔)
        //画弧形或扇形
        mPaint.setStyle(Paint.Style.FILL);//绘制模式   1、STROKE 画线模式  2、FILL 填充模式
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(750,10,950,150,0,145,true,mPaint);//(Left，Top，Right，Bottom，弧度的起始角度正右为0，划过的弧度，是否连接到圆心，画笔)



        //drawPath    Path方法
        mPath = new Path();
        mPath.setFillType(Path.FillType.WINDING);//设置图形相交时填充方式 1、EVEN_ODD 交叉填充  2、WINDING(默认值) 全填充 3、INVERSE_EVEN_ODD  4、INVERSE_WINDING
        //mPath.addCircle(250, 200, 450, Path.Direction.CCW);// 路径方向：Path.Direction.CCW 逆时针   Path.Direction.CW  顺时针
        //画心
        mPath.arcTo(50, 200, 250, 400, -225, 225,false);//添加弧形 和下面效果一样
        //mPath.addArc(250, 200, 450, 400, -180,225);//添加弧形
        mPath.arcTo(250, 200, 450, 400, -180, 225, false);//只用来画弧线(Left，Top，Right，Bottom，弧度的起始角度正右为0，划过的弧度，true:强制移动到弧形起点 无痕迹 false:拖动到弧形起点 有痕迹)
        mPath.lineTo(250, 542);
        canvas.drawPath(mPath,mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPath.lineTo(500,542);//从之前的位置 到 设置的位置画一条线（之前设置过的用之前的坐标 没设置的话用(0,0)）
        mPath.lineTo(600,300);
        mPath.rLineTo(-50,20);//相对于上个坐标相对的方向画线，相当于 mPath.lineTo(550,320);
        canvas.drawPath(mPath,mPaint);
        //画二次贝塞尔曲线
        mPaint.setColor(Color.BLUE);
        mPath.moveTo(650,320);//移动起点位置
        mPath.quadTo(650,320,850,450);//(控制点X，控制点Y，终点X，终点Y)
        canvas.drawPath(mPath,mPaint);


        //Shader 设置着色器  变换颜色时使用   设置的时候 .setColor()不起作用
        /**
         * 线性渐变
         * LinearGradient(float x0, float y0, float x1, float y1, int color0, int color1, Shader.TileMode tile)
         * x0 y0 x1 y1：渐变的两个端点的位置
         * color0 color1 是端点的颜色
         * tile：端点范围之外的着色规则，类型是 TileMode
         * 1、CLAMP
         * 2、MIRROR 是镜像模式
         * 3、REPEAT  重复模式
         */
        mShader = new LinearGradient(500,700,800,1300,Color.parseColor("#E91E63"),Color.parseColor("#2196F3"),Shader.TileMode.CLAMP);

        /**
         * 辐射渐变
         * RadialGradient(float centerX, float centerY, float radius, int centerColor, int edgeColor, TileMode tileMode)
         * centerX centerY：辐射中心的坐标
         * radius：辐射半径
         * centerColor：辐射中心的颜色
         * edgeColor：辐射边缘的颜色
         * tileMode：辐射范围之外的着色模式。
         */
        //mShader = new RadialGradient(500, 1000, 300, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        /**
         * 扫描渐变
         * SweepGradient(float cx, float cy, int color0, int color1)
         * cx cy ：扫描的中心
         * color0：扫描的起始颜色
         * color1：扫描的终止颜色
         */
        //mShader = new SweepGradient(500, 1000, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"));
        /**
         * Bitmap着色
         * BitmapShader(Bitmap bitmap, Shader.TileMode tileX, Shader.TileMode tileY)
         * bitmap：用来做模板的 Bitmap 对象
         * tileX：横向的 TileMode
         * tileY：纵向的 TileMode
         */
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image1);
        //mShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);



        mPaint.setShader(mShader);
        mPaint.setStyle(Paint.Style.FILL);//绘制模式   1、STROKE 空心  2、FILL 实心
        canvas.drawCircle(500,1000,300,mPaint);//（X轴坐标，Y轴坐标，半径，画笔）
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
