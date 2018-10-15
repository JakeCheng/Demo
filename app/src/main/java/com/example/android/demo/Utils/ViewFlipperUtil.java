package com.example.android.demo.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.android.demo.R;

import java.util.List;

/**
 * 滚动展示
 */

public class ViewFlipperUtil {
    public static void showViewFlipper(Context mContext, ViewFlipper mViewFlipper, List<String> listDate) {
        View view;
        TextView tv_1,tv_2;
        for (int i = 0; i < listDate.size(); i = i + 2) {
            //设置滚动的单个布局
            view = ((Activity) mContext).getLayoutInflater().inflate(R.layout.item_viewflipper, null);
            //初始化布局的控件
            tv_1 = view.findViewById(R.id.tv_1);
            tv_2 = view.findViewById(R.id.tv_2);
            //进行对控件赋值
            tv_1.setText(listDate.get(i));
            if (listDate.size() > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                tv_2.setText(listDate.get(i + 1));
            }
            //添加到循环滚动数组里面去
            mViewFlipper.addView(view);
        }
        mViewFlipper.startFlipping();
    }
}
