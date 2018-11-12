package com.example.android.demo.Presenter;

import com.example.android.demo.Base.IBasePresenter;
import com.example.android.demo.Utils.Constants;
import com.example.android.demo.View.MainView;

/**
 * Created by android on 2018/10/17.
 */

public class MainPresenter extends IBasePresenter<MainView> {
    public void initData(int currentIndex) {
        for(int i = 0; i < Constants.ItemName.length; i++){
            mView.addTab(Constants.ItemName[i],Constants.ItemIconName[i],i == 0,i == Constants.ItemIconName.length - 1);
        }
        mView.setTabDefaultSelect(currentIndex);
    }

    public void onTabBarClick(int index){
        mView.changeFragment(index,true);
    }
}
