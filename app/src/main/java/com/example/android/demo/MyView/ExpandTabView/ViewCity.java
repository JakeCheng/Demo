package com.example.android.demo.MyView.ExpandTabView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.demo.Adapter.ViewCityAdapter;
import com.example.android.demo.EventBus.CityChooseEventBus;
import com.example.android.demo.Model.MovieCityModel;
import com.example.android.demo.MyView.TabCloud.TagCloudLayout;
import com.example.android.demo.R;
import com.example.android.demo.Utils.SharePreferenceUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class ViewCity extends LinearLayout implements ViewBaseAction{
    private Context mContext;
    private TagCloudLayout mTagCloudLayout;
    private TextView tv_submit;
    private ViewCityAdapter mAdapter;
    private int choosePosition;
    private List<MovieCityModel.ResultBean> listDate = new ArrayList<>();
    public ViewCity(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ViewCity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ViewCity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_view_city, this, true);
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(getResources().getColor(R.color.white));
        mTagCloudLayout = findViewById(R.id.mTagCloudLayout);
        tv_submit = findViewById(R.id.tv_submit);

        mAdapter = new ViewCityAdapter(mContext,listDate,1);
        mTagCloudLayout.setAdapter(mAdapter);

        mTagCloudLayout.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                choosePosition = position;
                mAdapter.setSelected(position);
            }
        });

        tv_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferenceUtil.setChooseCity(mContext,new Gson().toJson(listDate.get(choosePosition)));
                EventBus.getDefault().post(new CityChooseEventBus(listDate.get(choosePosition).getId(),"1",listDate.get(choosePosition).getCity_name()));
            }
        });

    }
    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    public void upDate(List<MovieCityModel.ResultBean> listDate) {
        this.listDate = listDate;
        if (mAdapter != null){
            mAdapter.setData(listDate);
        }else{
            mAdapter = new ViewCityAdapter(mContext,listDate,2);
            mTagCloudLayout.setAdapter(mAdapter);
        }
    }
}
