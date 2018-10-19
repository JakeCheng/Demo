package com.example.android.demo.Adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.demo.R;

public class TagsAdapter extends BaseAdapter {

	private String[] listDate;
	private Context mContext;
	private int type = 2;
	private int selected = -1;
	// 用来保存选中项
	SparseBooleanArray arrSelected;

	public TagsAdapter(Context context, String[] listDate, int type){
		this.listDate = listDate;
		this.mContext = context;
		this.type = type;
		// 初始化集合
		arrSelected = new SparseBooleanArray();
	}
	public TagsAdapter(Context context, String[] listDate){
		this.listDate = listDate;
		this.mContext = context;
	}
	public void setData(String[] listDate){
		this.listDate = listDate;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listDate.length;
	}

	@Override
	public Object getItem(int position) {
		return listDate[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 此方法的逻辑为当选中某个item时，由于最开始old=-1,因此if里面的语句不执行,select集合里这个item的标签为tru。
	 * 此时这个old就不-1了，然后再点击别的item时，此时就会执行if里的语句，此时这个item的标签就为false了
	 *
	 * */
	public void setSelectedItem(int item) {
		arrSelected.put(item, true);
	}

	public void setSelectedItem2(int item) {
		arrSelected.put(item, false);
	}
	public void setSelected(int selected) {
		this.selected = selected;
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
		switch (type){
			case 1:
				if (arrSelected.get(position)) {
					holder.tv.setSelected(true);
				} else {
					holder.tv.setSelected(false);
				}
				break;
			case 2:
				if (selected == position) {
					holder.tv.setSelected(true);
				} else {
					holder.tv.setSelected(false);
				}
				break;
			default:
				holder.tv.setSelected(true);
				break;
		}

		String name = listDate[position];
		holder.tv.setText(name);
		return convertView;
	}

	class ViewHolder {
		TextView tv;
	}

}
