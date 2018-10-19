package com.example.android.demo.MyView.Vertical;


/**
 *
 */
public interface TabAdapter {
    int getCount();

    int getBadge(int position);

    CategoryTabView.TabIcon getIcon(int position);

    CategoryTabView.TabTitle getTitle(int position);

    int getBackground(int position);
}
