package com.example.android.demo.MyView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.demo.R;

import java.util.ArrayList;

public class ViewPageTabBar extends LinearLayout implements View.OnClickListener{

    public interface OnTabBarClickListener{
        void onTabBarClick(int index);
        void onTabBarDoubleClick();
    }
    private ArrayList<View> mTabItemList = new ArrayList<View>();
    private OnTabBarClickListener mListener;
    private LayoutParams mLayoutParams;
    private long mLastClickTime;
    public ViewPageTabBar(Context context) {
        super(context);
        init();
    }

    public ViewPageTabBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        this.setOrientation(HORIZONTAL);
    }

    public void setOnTabBarClickListener(OnTabBarClickListener listener){
        mListener = listener;
    }

    public void addCustomTab(View view){
        if(view == null){
            return;
        }
        int index = getChildCount();
        view.setTag(index);
        view.setOnClickListener(this);
        mTabItemList.add(view);
        this.addView(view);
    }

    public void addHomeTab(int titleId, int drawableId, boolean isFirst, boolean isLast){
        int index = getChildCount();
        HomeTabItem item = new HomeTabItem(getContext());
        item.setTag(index);
        item.setOnClickListener(this);
        item.bindData(titleId,drawableId);
        mTabItemList.add(item);
        mLayoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        mLayoutParams.weight = 1;
        this.addView(item,mLayoutParams);
    }

    public void setTabSelected(int index){
        if(index < 0 || index > getChildCount() - 1){
            return;
        }
        for(int i = 0;i < getChildCount();i++){
            if(index == i){
                getChildAt(i).setSelected(true);
            }else{
                getChildAt(i).setSelected(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Object o = v.getTag();
        if(!(o instanceof Integer) || o == null){
            return;
        }

        if(v.isSelected()){
            if(System.currentTimeMillis() - mLastClickTime < 200){
                if(mListener != null){
                    mListener.onTabBarDoubleClick();
                }
                mLastClickTime = 0;
            }else{
                mLastClickTime = System.currentTimeMillis();
            }
            return;
        }
        if(mListener != null){
            mListener.onTabBarClick(((Integer) o).intValue());
        }

        for(View view : mTabItemList){
            if(view == v){
                view.setSelected(true);
            }else{
                view.setSelected(false);
            }
        }

    }

   public static class HomeTabItem extends FrameLayout{

       private TextView mTextView;
       public HomeTabItem(Context context) {
           super(context);
           init();

       }

       public HomeTabItem(Context context, @Nullable AttributeSet attrs) {
           super(context, attrs);
           init();
       }

       private void init(){
           mTextView = new TextView(getContext());
           mTextView.setDuplicateParentStateEnabled(true);
           mTextView.setGravity(Gravity.CENTER);
           mTextView.setTextColor(getResources().getColorStateList(R.color.normal_black40_selected_14b9c7_color));
           mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.sp_12));
           LayoutParams flp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
           flp.gravity = Gravity.CENTER;
           mTextView.setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.dp_3));
           this.addView(mTextView,flp);
       }

       public void bindData(int titleId,int drawableId){
           mTextView.setText(titleId);
           mTextView.setCompoundDrawablesWithIntrinsicBounds(0,drawableId,0,0);
       }
   }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        for(int i = 0;i < this.getChildCount();i++){
            this.getChildAt(i).setClickable(clickable);
        }
    }
}
