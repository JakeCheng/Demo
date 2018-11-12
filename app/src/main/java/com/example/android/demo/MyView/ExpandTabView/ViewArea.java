package com.example.android.demo.MyView.ExpandTabView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.android.demo.Adapter.MovieAreaAdapter;
import com.example.android.demo.R;


public class ViewArea extends RelativeLayout implements ViewBaseAction{
    private Context mContext;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private String[] listDate = new String[]{"不限","500","1000","2000","3000"};
    private MovieAreaAdapter mAdapter;
    private String showText = "周边";
    private OnSelectListener mOnSelectListener;
    public ViewArea(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ViewArea(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ViewArea(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_view_area, this, true);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAdapter = new MovieAreaAdapter(R.layout.item_view_area,listDate);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.setSelectedItem(position);
                if (mOnSelectListener != null) {
                    if (position == 0){
                        showText = "不限";
                    }else {
                        showText = listDate[position];
                    }
                    mOnSelectListener.getValue(showText);
                }
            }
        });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void getValue(String showText);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }
}
