package com.example.android.demo.Presenter;

import com.example.android.demo.Api.Api;
import com.example.android.demo.Api.SimpleCallback;
import com.example.android.demo.Base.IBasePresenter;
import com.example.android.demo.Model.CookBookModel;
import com.example.android.demo.Utils.Constants;
import com.example.android.demo.View.CookBookView;

/**
 * Created by android on 2018/10/17.
 */

public class CookBookPresenter extends IBasePresenter<CookBookView> {
    public void initCookBookDateGet(String parentid) {
        Api.initCookBookDateGet(parentid, Constants.CookBookKey,new SimpleCallback<CookBookModel>(mView) {
            @Override
            public void onSuccess(CookBookModel bean) {
                mView.onCookBookDateGet(bean);
            }

            @Override
            public void onError(String msg) {
                mView.showError(msg);
            }
        });
    }

}
