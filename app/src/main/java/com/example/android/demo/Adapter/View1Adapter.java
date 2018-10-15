package com.example.android.demo.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.android.demo.R;

import java.util.List;

/**
 * Created by android on 2018/10/10.
 */

public class View1Adapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public View1Adapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv,item);
    }
}
