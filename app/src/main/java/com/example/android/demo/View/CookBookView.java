package com.example.android.demo.View;

import com.example.android.demo.Base.BaseView;
import com.example.android.demo.Model.CookBookModel;

/**
 * Created by android on 2018/10/18.
 */

public interface CookBookView extends BaseView{
    void onCookBookDateGet(CookBookModel bean);
}
