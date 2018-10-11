package com.example.android.demo.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.example.android.demo.Bean.DemoItemBean;
import com.example.android.demo.R;

import java.util.List;

/**
 * Created by android on 2018/10/10.
 * 多种布局Item
 */

public class DemoItemTypeAdapter extends BaseQuickAdapter<DemoItemBean.DateBean,BaseViewHolder>{

    public DemoItemTypeAdapter(List<DemoItemBean.DateBean> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<DemoItemBean.DateBean>() {
            @Override
            protected int getItemType(DemoItemBean.DateBean dateBean) {
                return dateBean.getType();
            }
        });

        getMultiTypeDelegate().registerItemType(DemoItemBean.ITEM_TYPE_1,R.layout.item_demo);
        getMultiTypeDelegate().registerItemType(DemoItemBean.ITEM_TYPE_2,R.layout.item_demo2);
    }

    @Override
    public void convert(BaseViewHolder helper, DemoItemBean.DateBean item) {
        switch (helper.getItemViewType()){
            case DemoItemBean.ITEM_TYPE_1:
                helper.setText(R.id.tv_name,item.getName());
                helper.setText(R.id.tv_age,item.getAge()+"");
                break;
            case DemoItemBean.ITEM_TYPE_2:
                helper.setText(R.id.tv_name,item.getName());
                helper.setText(R.id.tv_age,item.getAge()+"");
                break;
        }
    }
}
