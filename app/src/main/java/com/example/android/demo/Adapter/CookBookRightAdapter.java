package com.example.android.demo.Adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.android.demo.Model.CookBookRightModel;
import com.example.android.demo.R;
import com.example.android.demo.Utils.ImageLoaderHelper;

import java.util.List;

/**
 * Created by android on 2018/10/18.
 */

public class CookBookRightAdapter extends BaseQuickAdapter<CookBookRightModel.ResultBean.DataBean,BaseViewHolder> {
    ImageView iv;
    public CookBookRightAdapter(int layoutResId, @Nullable List<CookBookRightModel.ResultBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CookBookRightModel.ResultBean.DataBean item) {
        iv = helper.getView(R.id.iv);
        ImageLoaderHelper.display(mContext,iv,item.getAlbums().get(0));
        helper.setText(R.id.tv,item.getTitle());
    }
}
