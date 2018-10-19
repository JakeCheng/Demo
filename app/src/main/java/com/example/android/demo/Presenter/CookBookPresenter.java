package com.example.android.demo.Presenter;

import com.example.android.demo.Api.Api;
import com.example.android.demo.Api.SimpleCallback;
import com.example.android.demo.Base.BasePresenter;
import com.example.android.demo.Model.CookBookModel;
import com.example.android.demo.View.CookBookView;

/**
 * Created by android on 2018/10/17.
 */

public class CookBookPresenter extends BasePresenter<CookBookView>{
    public void initCookBookDateGet(String parentid) {
        Api.initCookBookDateGet(parentid,"463f34303af2e185be3085a54b5713a3",new SimpleCallback<CookBookModel>(mView) {
            @Override
            public void onNext(CookBookModel bean) {
                mView.onCookBookDateGet(bean);
            }
        });
    }

}
