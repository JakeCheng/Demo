package com.example.android.demo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.Service.NetworkReceiver;
import com.example.android.demo.Ui.AIDLActivity;
import com.example.android.demo.Ui.DBGreenDaoActivity;
import com.example.android.demo.Ui.MoveActivity;
import com.example.android.demo.Ui.MyRecyclerViewActivity;
import com.example.android.demo.Ui.MyServiceActivity;
import com.example.android.demo.Ui.MyView2Activity;
import com.example.android.demo.Ui.MyViewActivity;
import com.example.android.demo.Ui.MyViewGroupActivity;
import com.example.android.demo.Utils.ScreenUtils;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{
    private static final int RC_CAMERA_AND_WIFI = 10;
    private NetworkReceiver mNetworkReceiver;
    @Override
    public int getRootViewId() {
        return R.layout.activity_main;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNetworkReceiver = new NetworkReceiver();
        mNetworkReceiver.register(this);
        Log.e(TAG, "onCreate: MainActivity");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Log.e(TAG, "initView: "+ Process.myUid());



        findViewById(R.id.btn_greenDao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,DBGreenDaoActivity.class));
            }
        });

        findViewById(R.id.btn_aidl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,AIDLActivity.class));
            }
        });
        findViewById(R.id.btn_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,MyViewActivity.class));
            }
        });
        findViewById(R.id.btn_view2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,MyView2Activity.class));
            }
        });
        findViewById(R.id.btn_viewgroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,MyViewGroupActivity.class));
            }
        });
        findViewById(R.id.btn_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });
        findViewById(R.id.btn_move).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,MoveActivity.class));
            }
        });
        findViewById(R.id.btn_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,MyServiceActivity.class));
            }
        });
        findViewById(R.id.btn_recyclerview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,MyRecyclerViewActivity.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            ScreenUtils.showPermission(mContext,"没有此权限，无法开启这个功能，请开启权限。WRITE_EXTERNAL_STORAGE");
        }
    }
    @AfterPermissionGranted(RC_CAMERA_AND_WIFI)
    public void getPermission() {
            if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Have permission, do the thing!
               startActivity(new Intent(mContext,MyView2Activity.class));
            } else {
                // Ask for one permission
                ActivityCompat.requestPermissions(mContext, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_CAMERA_AND_WIFI);
            }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetworkReceiver.unregister(this);
    }
}
