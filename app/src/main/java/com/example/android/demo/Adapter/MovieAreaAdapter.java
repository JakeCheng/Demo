package com.example.android.demo.Adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.android.demo.R;

import java.util.Arrays;

public class MovieAreaAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    private int selectedPos = -1;
    public MovieAreaAdapter(int layoutResId, @Nullable String[] data) {
        super(layoutResId, Arrays.asList(data));
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv = helper.getView(R.id.tv);
        tv.setText(item);
        final int position = helper.getAdapterPosition();
        if (position == selectedPos){
            tv.setBackgroundColor(mContext.getResources().getColor(R.color.color_14b9c7));
        }else{
            tv.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedPos = selectedItem;
        notifyDataSetChanged();
    }
}
