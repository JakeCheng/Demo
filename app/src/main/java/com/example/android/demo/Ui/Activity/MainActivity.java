package com.example.android.demo.Ui.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.android.demo.Adapter.MyPagerAdapter;
import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.R;
import com.example.android.demo.Service.NetworkReceiver;
import com.example.android.demo.Ui.Fragment.View1Fragment;
import com.example.android.demo.Ui.Fragment.View2Fragment;
import com.example.android.demo.Utils.Constants;
import com.example.android.demo.Utils.ScreenUtils;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{
    private static final int RC_CAMERA_AND_WIFI = 10;
    private NetworkReceiver mNetworkReceiver;
    private MyPagerAdapter mAdapter;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

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
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new View1Fragment(), Constants.ItemName[0]);
        mAdapter.addFragment(new View2Fragment(), Constants.ItemName[1]);
        for (String anItemName : Constants.ItemName) {
            mTabLayout.addTab(mTabLayout.newTab().setText(anItemName));
        }
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }

   /* @OnClick({R.id.btn_aidl,R.id.btn_view,R.id.btn_permission,R.id.btn_move,R.id.btn_recyclerview})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_aidl:
                startActivity(new Intent(mContext,AIDLActivity.class));
                break;
            case R.id.btn_view:
                startActivity(new Intent(mContext,MyViewActivity.class));
                break;
            case R.id.btn_permission:
                getPermission();
                break;
            case R.id.btn_move:
                startActivity(new Intent(mContext,MoveActivity.class));
                break;
            case R.id.btn_recyclerview:
                startActivity(new Intent(mContext,MyRecyclerViewActivity.class));
                break;
        }
    }*/

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
            startActivity(new Intent(mContext,MyViewActivity.class));
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
