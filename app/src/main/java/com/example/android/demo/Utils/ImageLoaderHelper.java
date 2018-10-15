package com.example.android.demo.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.demo.R;

/**
 *  网络图片加载
 */
public class ImageLoaderHelper {
	@SuppressLint("CheckResult")
	public static void display(Context context, ImageView imageView, String url) {
		if(imageView == null) {
			throw new IllegalArgumentException("argument error");
		}
		RequestOptions options = new RequestOptions();
		options.error(R.mipmap.ic_launcher);
		imageView.measure(0,0);
		options.override(imageView.getMeasuredWidth(),imageView.getMeasuredHeight());
		Glide.with(context).load(url).apply(options).into(imageView);
	}
	@SuppressLint("CheckResult")
	public static void display(Context context, ImageView imageView, String url, int picture_moren) {
		if(imageView == null) {
			throw new IllegalArgumentException("argument error");
		}
		RequestOptions options = new RequestOptions();
		options.placeholder(picture_moren);
		options.error(picture_moren);
		Glide.with(context).load(url).apply(options).into(imageView);
	}

	@SuppressLint("CheckResult")
	public static void displayCircle(final Context context, final ImageView imageView, String url, int picture_moren) {
		if(imageView == null) {
			throw new IllegalArgumentException("argument error");
		}
		RequestOptions options = new RequestOptions();
		options.placeholder(picture_moren);
		options.optionalCircleCrop();
		Glide.with(context).load(url).apply(options).into(imageView);
	}

	@SuppressLint("CheckResult")
	public static void displayByteCircle(final Context context, final ImageView imageView, byte[] data, int picture_moren) {
		if(imageView == null) {
			throw new IllegalArgumentException("argument error");
		}
		RequestOptions options = new RequestOptions();
		options.placeholder(picture_moren);
		options.optionalCircleCrop();
		Glide.with(context).load(data).apply(options).into(imageView);
	}
}
