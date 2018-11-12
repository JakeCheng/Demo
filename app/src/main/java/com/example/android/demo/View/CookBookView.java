package com.example.android.demo.View;

import com.example.android.demo.Base.IBaseView;
import com.example.android.demo.Model.CookBookModel;

/**
 * Created by android on 2018/10/18.
 */

public interface CookBookView extends IBaseView {
    void onCookBookDateGet(CookBookModel bean);
}
