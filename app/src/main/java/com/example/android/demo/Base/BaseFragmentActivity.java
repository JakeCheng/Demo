package com.example.android.demo.Base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.android.demo.R;

public abstract class BaseFragmentActivity extends BaseActivity {

    @Override
    public int getRootViewId() {
        return R.layout.activity_container;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.frameLayout_fragment);

        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction().add(R.id.frameLayout_fragment, fragment).commit();
        }
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    abstract protected Fragment createFragment();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);

    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCompleted() {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
