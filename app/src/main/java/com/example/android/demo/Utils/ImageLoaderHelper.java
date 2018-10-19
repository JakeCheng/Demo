package com.example.android.demo.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.android.demo.R;
import com.luck.picture.lib.photoview.PhotoViewAttacher;

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

	public static void displayPhotoView(Context context, final ImageView imageView, String picUrl, final PhotoViewAttacher photoViewAttacher) {
		if (imageView == null) {
			throw new IllegalArgumentException("argument error");
		}
		Glide.with(context).load(picUrl)
				.into(new SimpleTarget<Drawable>() {
					@Override
					public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
						if (photoViewAttacher != null) {
							photoViewAttacher.update();
						}
						imageView.setImageDrawable(resource);
					}
				});
	}
}
