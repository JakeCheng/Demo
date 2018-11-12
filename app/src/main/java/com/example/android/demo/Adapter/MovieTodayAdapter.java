package com.example.android.demo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.android.demo.Model.MovieTodayModel;
import com.example.android.demo.R;
import com.example.android.demo.Utils.ImageLoaderHelper;
import com.example.android.demo.Utils.ScreenUtils;

import java.util.List;
public class MovieTodayAdapter extends PagerAdapter {
    private ImageView iv;
    private LinearLayout ll;
    private TextView tv;
    private Context mContext;
    private List<MovieTodayModel.ResultBean> listDate;

    public MovieTodayAdapter(Context mContext,List<MovieTodayModel.ResultBean> listDate){
       this.mContext = mContext;
       this.listDate = listDate;
    }
    @Override
    public int getCount() {
        return listDate.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_movie_today,null);
        ll = view.findViewById(R.id.ll);
        iv = view.findViewById(R.id.iv);
        tv = view.findViewById(R.id.tv);
//        ViewGroup.LayoutParams params = ll.getLayoutParams();
//        params.width = ScreenUtils.getScreenMetrics(mContext).widthPixels/2;
//        ll.setLayoutParams(params);
        ImageLoaderHelper.display(mContext,iv,listDate.get(position).getPic_url());
        tv.setText(listDate.get(position).getMovieName());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
