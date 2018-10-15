package com.example.android.demo.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.demo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 带图片的Toast提示框
 */
public class ToastUtils {
	/**
	 * 提示框
	 * @param context
	 * @param msg
	 * @param i   1、正确  2、错误  3、提示
	 */
	public static void showPhone(Context context, String msg, int i){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.toast_view, null);
		ImageView imageView = view.findViewById(R.id.toast_image);
		if (i == 1){
			imageView.setImageResource(R.mipmap.tishi_dui);
		}else if(i == 2){
			imageView.setImageResource(R.mipmap.tishi_cuowu);
		}else{
			imageView.setImageResource(R.mipmap.tishi_tishi);
		}
		TextView t = view.findViewById(R.id.toast_text);
		t.setText(msg);
		final Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER,0,0);
		toast.setView(view);
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				toast.show();
			}
		}, 0, 2000);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				toast.cancel();
				timer.cancel();
			}
		}, 1500);
	}
}
