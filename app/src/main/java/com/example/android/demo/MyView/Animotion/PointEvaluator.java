package com.example.android.demo.MyView.Animotion;

import android.animation.TypeEvaluator;

/**
 * Created by android on 2018/10/30.
 */

public class PointEvaluator implements TypeEvaluator<Point>{
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        int start = startValue.getRadius();
        int end  = endValue.getRadius();
        int curValue = (int)(start + fraction * (end - start));
        return new Point(curValue);
    }
}
