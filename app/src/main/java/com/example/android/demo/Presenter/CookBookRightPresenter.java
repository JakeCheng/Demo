package com.example.android.demo.Presenter;

import com.example.android.demo.Api.Api;
import com.example.android.demo.Api.SimpleCallback;
import com.example.android.demo.Base.IBasePresenter;
import com.example.android.demo.Model.CookBookRightModel;
import com.example.android.demo.Utils.Constants;
import com.example.android.demo.View.CookBookRightView;

/**
 * Created by android on 2018/10/17.
 */

public class CookBookRightPresenter extends IBasePresenter<CookBookRightView> {
    public void initCookBookRightDateGet(String cid) {
        Api.initCookBookRightDateGet(cid, Constants.CookBookKey,new SimpleCallback<CookBookRightModel>(mView) {
            @Override
            public void onSuccess(CookBookRightModel bean) {
                mView.onCookBookRightDateGet(bean);
            }

            @Override
            public void onError(String msg) {
                mView.showError(msg);
            }
        });
    }
}
