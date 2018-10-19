package com.example.android.demo.Adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.android.demo.R;
import com.example.android.demo.Utils.ImageLoaderHelper;
import com.luck.picture.lib.photoview.PhotoViewAttacher;

import java.util.ArrayList;
import java.util.List;

public class ImageBrowserAdapter extends PagerAdapter {

	private Activity context;
	private List<String> picUrls;

	public ImageBrowserAdapter(Activity context, ArrayList<String> picUrls) {
		this.context = context;
		this.picUrls = picUrls;
	}


	@Override
	public int getCount() {

		return picUrls.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
		View view = View.inflate(context, R.layout.item_image_browser, null);
		ImageView pvShowImage = view.findViewById(R.id.pv_show_image);
		String picUrl = picUrls.get(position);
		final PhotoViewAttacher photoViewAttacher=new PhotoViewAttacher(pvShowImage);
		photoViewAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
		photoViewAttacher.setMinimumScale(1F);
		ImageLoaderHelper.displayPhotoView(context,pvShowImage,picUrl,photoViewAttacher);
		container.addView(view);
		return view;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((LinearLayout) object);
	}




}