package com.example.android.demo.MyView.Vertical;

import android.content.Context;
import android.view.Gravity;

import com.example.android.demo.Model.CookBookModel;
import com.example.android.demo.R;
import java.util.List;

public class CategoryVTabAdapter implements TabAdapter {

    private List<CookBookModel.ResultBean.ListBean> mLeftModels;
    private Context mContext;

    public CategoryVTabAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if(null != mLeftModels){
            return mLeftModels.size();
        }
        return 0;
    }

    @Override
    public int getBadge(int position) {
        if(null != mLeftModels && position == mLeftModels.size()){
            return position;
        }
        return 0;
    }

    @Override
    public CategoryTabView.TabIcon getIcon(int position) {
        return new CategoryTabView.TabIcon.Builder()
                .setIconGravity(Gravity.RIGHT)
                .setIcon(R.mipmap.category_arrow, 0)
                .setIconSize(mContext.getResources().getDimensionPixelOffset(R.dimen.view_dimen_5),
                        mContext.getResources().getDimensionPixelOffset(R.dimen.view_dimen_7))
                .setIconMargin(mContext.getResources().getDimensionPixelOffset(R.dimen.view_dimen_7))
                .build();
    }

    @Override
    public CategoryTabView.TabTitle getTitle(int position) {
        return new CategoryTabView.TabTitle.Builder(mContext)
                .setContent(mLeftModels.get(position).getName())
                .setTextColor(mContext.getResources().getColor(R.color.color_14b9c7),
                        mContext.getResources().getColor(R.color.color_black_trans_40))
                .build();
    }

    @Override
    public int getBackground(int position) {
        return 0;
    }

    public void upDate(List<CookBookModel.ResultBean.ListBean> mLeftModels){
        this.mLeftModels = mLeftModels;
    }
}
