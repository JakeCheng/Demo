package com.example.android.demo.Api;


import android.app.Dialog;
import com.example.android.demo.MyApplication;
import com.example.android.demo.R;
import com.example.android.demo.Base.BaseView;
import com.example.android.demo.Utils.MyAppManager;
import com.example.android.demo.Utils.ProgressDialog;

public abstract class SimpleCallback<T> implements ApiCallback<T> {

    private BaseView mView;
    private Dialog dialog;

    public SimpleCallback(BaseView mView) {
        this(mView,true);
    }

    public SimpleCallback(BaseView mView, boolean isShowDialog) {
        this.mView = mView;
        if (dialog == null) {
            dialog = ProgressDialog.createLoadingDialog(MyAppManager.getInstance().getTopAct(), MyApplication.getInstance().getResources().getString(R.string.loading));
        }
        if (isShowDialog && dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onError(Throwable e) {
        mView.onError(e);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onCompleted() {
        mView.onCompleted();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
