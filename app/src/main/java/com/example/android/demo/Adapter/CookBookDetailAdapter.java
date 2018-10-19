package com.example.android.demo.Adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.android.demo.Model.CookBookRightModel;
import com.example.android.demo.R;
import com.example.android.demo.Utils.ImageLoaderHelper;

import java.util.List;

public class CookBookDetailAdapter extends BaseQuickAdapter<CookBookRightModel.ResultBean.DataBean.StepsBean,BaseViewHolder> {
    ImageView iv;
    public CookBookDetailAdapter(int layoutResId, @Nullable List<CookBookRightModel.ResultBean.DataBean.StepsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CookBookRightModel.ResultBean.DataBean.StepsBean item) {
        iv = helper.getView(R.id.iv);
        ImageLoaderHelper.display(mContext,iv,item.getImg());
        helper.setText(R.id.tv,item.getStep());
        helper.addOnClickListener(R.id.iv);
    }
}
