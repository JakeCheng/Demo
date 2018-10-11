package com.example.android.demo.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.android.demo.Bean.DemoItemBean;
import com.example.android.demo.R;

import java.util.List;

/**
 * Created by android on 2018/10/10.
 */

public class DemoAdapter extends BaseQuickAdapter<DemoItemBean.DateBean,BaseViewHolder>{
    public DemoAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    public void convert(BaseViewHolder helper, DemoItemBean.DateBean item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_age,item.getAge()+"");
    }
}
