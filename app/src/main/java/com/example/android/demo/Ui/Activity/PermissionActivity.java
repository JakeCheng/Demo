package com.example.android.demo.Ui.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.EventBus.PermissionEventBus;
import com.example.android.demo.R;
import com.example.android.demo.Utils.Constants;
import com.example.android.demo.Utils.ScreenUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class PermissionActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{
    private final int GET_PERMISSION_REQUEST_CODE = 10;

    @Override
    public int getRootViewId() {
        return R.layout.activity_permission;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, GET_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case GET_PERMISSION_REQUEST_CODE:
                EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
                break;
        }
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        EventBus.getDefault().post(new PermissionEventBus(Constants.View2FragmentToPermissionActivity,Constants.PermissionOK));
        finish();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            EventBus.getDefault().post(new PermissionEventBus(Constants.View2FragmentToPermissionActivity,Constants.PermissionCancal));
            finish();
        }
    }
}
