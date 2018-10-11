package com.example.android.demo.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.demo.R;


public class ProgressDialog {

    public static Dialog createLoadingDialog(Context context, String str) {
        if (context == null) {
            return null;
        }
        View v = RelativeLayout.inflate(context, R.layout.progressdialog, null);
        ImageView imganim = v.findViewById(R.id.iv_progressdialogs);
        TextView tv_hint = v.findViewById(R.id.tv_progressdialog_hint);
        imganim.setBackgroundResource(R.drawable.spinner);
        AnimationDrawable anim = (AnimationDrawable) imganim.getBackground();
        anim.start();
        tv_hint.setText(str);
        tv_hint.setTextColor(Color.WHITE);
        final Dialog loadingDialog = new Dialog(context, R.style.ProgressDialogStyle);
        WindowManager.LayoutParams params = loadingDialog.getWindow().getAttributes();
        loadingDialog.getWindow().setGravity(Gravity.CENTER);
        params.dimAmount = 0.2f;
        loadingDialog.getWindow().setAttributes(params);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(v);
        return loadingDialog;

    }

}
