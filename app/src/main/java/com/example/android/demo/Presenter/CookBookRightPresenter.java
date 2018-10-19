package com.example.android.demo.Presenter;

import com.example.android.demo.Api.Api;
import com.example.android.demo.Api.SimpleCallback;
import com.example.android.demo.Base.BasePresenter;
import com.example.android.demo.Model.CookBookModel;
import com.example.android.demo.Model.CookBookRightModel;
import com.example.android.demo.View.CookBookRightView;
import com.example.android.demo.View.CookBookView;

/**
 * Created by android on 2018/10/17.
 */

public class CookBookRightPresenter extends BasePresenter<CookBookRightView>{
    public void initCookBookRightDateGet(String cid) {
        Api.initCookBookRightDateGet(cid,"463f34303af2e185be3085a54b5713a3",new SimpleCallback<CookBookRightModel>(mView,true) {
            @Override
            public void onNext(CookBookRightModel bean) {
                mView.onCookBookRightDateGet(bean);
            }
        });
    }
}
