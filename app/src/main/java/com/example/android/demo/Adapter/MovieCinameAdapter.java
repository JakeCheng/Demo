package com.example.android.demo.Adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.android.demo.Model.MovieCinameModel;
import com.example.android.demo.R;

import java.util.List;

public class MovieCinameAdapter extends BaseQuickAdapter<MovieCinameModel.ResultBean,BaseViewHolder> {
    public MovieCinameAdapter(int layoutResId, @Nullable List<MovieCinameModel.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MovieCinameModel.ResultBean item) {
        helper.setText(R.id.tv_name,item.getCinemaName());
        helper.setText(R.id.tv_address,item.getAddress());
        helper.setText(R.id.tv_tel,item.getTelephone());
        helper.setText(R.id.tv_route,item.getTrafficRoutes());
    }
}
