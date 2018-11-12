package com.example.android.demo.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.demo.Model.MovieCityModel;
import com.example.android.demo.R;
import com.example.android.demo.Utils.SharePreferenceUtil;

import java.util.List;

public class ViewCityAdapter extends BaseAdapter {

	private List<MovieCityModel.ResultBean> listDate;
	private Context mContext;
	private int selected = -1;
	private int type;
	public ViewCityAdapter(Context context, List<MovieCityModel.ResultBean> listDate,int type){
		this.listDate = listDate;
		this.mContext = context;
		this.type = type;
	}
	public void setData(List<MovieCityModel.ResultBean> listDate){
		this.listDate = listDate;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listDate.size();
	}

	@Override
	public Object getItem(int position) {
		return listDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	public void setSelected(int selected) {
		this.selected = selected;
		type = 2;
		notifyDataSetChanged();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.item_tags, null);
			holder.tv = convertView.findViewById(R.id.tv_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (type == 1) {
			if (SharePreferenceUtil.getCityID(mContext).equals(listDate.get(position).getId())) {
				selected = position;
			}
		}
		if (selected == position) {
			holder.tv.setSelected(true);
		} else {
			holder.tv.setSelected(false);
		}

		String name = listDate.get(position).getCity_name();
		holder.tv.setText(name);
		return convertView;
	}

	public int getSelected() {
		return selected;
	}

	class ViewHolder {
		TextView tv;
	}

}
