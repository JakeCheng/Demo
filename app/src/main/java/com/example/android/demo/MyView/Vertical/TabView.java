package com.example.android.demo.MyView.Vertical;

import android.content.Context;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 *
 */
public abstract class TabView extends FrameLayout implements Checkable {

    public TabView(Context context) {
        super(context);
    }

    public abstract TabView setBadge(int num);
}
