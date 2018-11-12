package com.example.android.demo.Ui.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.android.demo.Adapter.ImageBrowserAdapter;
import com.example.android.demo.Base.BaseActivity;
import com.example.android.demo.MyView.SlideCloseLayout;
import com.example.android.demo.R;
import com.example.android.demo.Utils.Constants;
import com.example.android.demo.Utils.Logger;
import com.example.android.demo.Utils.NetWorkUtils;
import com.example.android.demo.Utils.ScreenUtils;
import com.example.android.demo.Utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ShowWebImageActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{
	@BindView(R.id.vp_image_browser)
	ViewPager vpImageBrowser;
	@BindView(R.id.tv_image_index)
	TextView tvImageIndex;
	@BindView(R.id.tv_image_download)
	TextView tvImageDownload;
	@BindView(R.id.slideCloseLayout)
	SlideCloseLayout mSlideCloseLayout;
	private static final String TAG = "ShowWebImageActivity";
	private ImageBrowserAdapter adapter;
	private ArrayList<String> imgUrls;
	private String url,message;
	private final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 10;
	@Override
	public int getRootViewId() {
		return R.layout.web_image_activity;
	}

	@Override
	public void initView(Bundle savedInstanceState) {
		getWindow().getDecorView().setBackgroundColor(Color.BLACK);
		//给控件设置需要渐变的背景。如果没有设置这个，则背景不会变化
		mSlideCloseLayout.setGradualBackground(getWindow().getDecorView().getBackground());
	}

	@Override
	public void initPresenter() {

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			mSlideCloseLayout.exitLayoutAnim(500, false);
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
	private Handler messageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.e(TAG, message);
			if (message.equals(getResources().getString(R.string.saved_success))){
				ToastUtils.showPhone(mContext,message,1);
			}else{
				ToastUtils.showPhone(mContext,message,2);
			}
		}
	};

	/**
	 * 网络可用状态下，下载图片并保存在本地
	 *
	 * @param strUrl 图片网址
	 * @param file 本地保存的图片文件
	 * @return 图片
	 */
	private Bitmap getNetBitmap(String strUrl, File file) {
		Bitmap bitmap = null;
		if(NetWorkUtils.isConnected(mContext)){
			try {
				URL url = new URL(strUrl);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoInput(true);
				con.connect();
				InputStream in = con.getInputStream();
				bitmap = BitmapFactory.decodeStream(in);
				FileOutputStream out = new FileOutputStream(file.getPath());
				bitmap.compress(Bitmap.CompressFormat.PNG,100, out);
				out.flush();
				out.close();
				in.close();
				message = getResources().getString(R.string.saved_success);
			} catch (Exception e) {
				e.printStackTrace();
				message = getResources().getString(R.string.saved_failed);
			}
		}
		return bitmap;
	}
	/**
	 * 获取图片的存储目录，在有sd卡的情况下为 “/sdcard/apps_images/本应用包名/cach/images/”
	 * 没有sd的情况下为“/data/data/本应用包名/cach/images/”
	 *
	 * @return 本地图片存储目录
	 */
	private static String getPath(){
		String path= Environment.getExternalStorageDirectory()+"/Demo/";
		File file = new File(path);
		boolean isExist = file.exists();
		if(!isExist){
			file.mkdir();
		}
		return file.getPath();
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(0, R.anim.exitalpha);
	}

	public void initData() {
		imgUrls = getIntent().getStringArrayListExtra(Constants.IMAGE_URL_ALL);
		Logger.error(TAG, "initData: imgUrls.size() = " + imgUrls.size());
		url = getIntent().getStringExtra(Constants.IMAGE_URL);
		int position = imgUrls.indexOf(url);
		adapter = new ImageBrowserAdapter(this, imgUrls);
		vpImageBrowser.setAdapter(adapter);
		final int size = imgUrls.size();
		if (size > 1) {
			tvImageIndex.setVisibility(View.VISIBLE);
			tvImageIndex.setText((position + 1) + "/" + size);
		} else {
			tvImageIndex.setVisibility(View.GONE);
		}
		vpImageBrowser.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				int index = arg0 % size;
				tvImageIndex.setText((index + 1) + "/" + size);
				url = imgUrls.get(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		vpImageBrowser.setCurrentItem(position);

		tvImageDownload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getPermission();
			}
		});
		mSlideCloseLayout.setLayoutScrollListener(new SlideCloseLayout.LayoutScrollListener() {
			@Override
			public void onLayoutClosed() {
				onBackPressed();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	@AfterPermissionGranted(WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
	public void getPermission() {
		if (EasyPermissions.hasPermissions(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
			new Thread(new Runnable() {
				@Override
				public void run() {
					String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
					File file = new File(getPath(), imageName);
					getNetBitmap(url, file);
					messageHandler.sendMessage(messageHandler.obtainMessage());
				}
			}).start();
		}else{
			ActivityCompat.requestPermissions(mContext, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	/**
	 * 权限允许
	 * @param requestCode
	 * @param perms
	 */
	@Override
	public void onPermissionsGranted(int requestCode, List<String> perms) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
				File file = new File(getPath(), imageName);
				getNetBitmap(url, file);
				messageHandler.sendMessage(messageHandler.obtainMessage());
			}
		}).start();
	}

	/**
	 * 权限拒绝
	 * @param requestCode
	 * @param perms
	 */
	@Override
	public void onPermissionsDenied(int requestCode, List<String> perms) {
		if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
			ScreenUtils.showPermission(mContext,getResources().getString(R.string.permission_write));
		}
	}
}
