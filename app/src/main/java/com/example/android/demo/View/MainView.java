package com.example.android.demo.View;

import com.example.android.demo.Base.BaseView;

/**
 * Created by android on 2018/10/17.
 */

public interface MainView extends BaseView {

    void addTab(int titleId,int drawableId,boolean isFirst,boolean isLast);
    void setTabDefaultSelect(int index);
    void changeFragment(int index,boolean isScroll);
}
