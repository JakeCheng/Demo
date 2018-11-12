package com.example.android.demo.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.demo.Base.BaseFragment;
import com.example.android.demo.MyView.Vertical.VerticalViewPager;

import java.util.ArrayList;


public class FragmentPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		mDefaultIndex = position;
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	class FragmentInfo {
		FragmentInfo(String tag, Class<? extends Fragment> clazz, Bundle args) {
			this.tag = tag;
			this.clazz = clazz;
			this.fragment = null;
			this.args = args;
		}

		String tag;
		Class<? extends Fragment> clazz;
		Fragment fragment;
		Bundle args;
	}

	private Context mContext;
	private FragmentManager mFragmentManager;
	private ViewPager mViewPager;
	private VerticalViewPager vViewPager;
	private ArrayList<FragmentInfo> mFragmentInfos = new ArrayList<>();
	private FragmentTransaction mCurTransaction = null;
	private Fragment mCurrentPrimaryItem = null;
	private BaseFragment mParentFragment;
	private int mDefaultIndex = 0;

	public FragmentPagerAdapter(BaseFragment parentFragment, Context context, FragmentManager fm,
                                ViewPager viewPager){
		mParentFragment = parentFragment;
		if(mParentFragment != null){
			mParentFragment.setOnVisibleListener(new BaseFragment.OnVisibleListener() {
				@Override
				public void onVisibilityChanged(boolean isVisibileToUser) {
					Fragment fragment = getFragment(mDefaultIndex,false);
					if(fragment != null){
						fragment.setUserVisibleHint(isVisibileToUser);
						fragment.setMenuVisibility(isVisibileToUser);
					}
				}
			});
		}
		mContext = context;
		mFragmentManager = fm;
		mViewPager = viewPager;
		mViewPager.addOnPageChangeListener(this);
		mViewPager.setAdapter(this);
	}

	/**
	 * 菜单中竖向ViewPager
	 * @param parentFragment
	 * @param context
	 * @param fm
	 * @param viewPager
	 */
	public FragmentPagerAdapter(BaseFragment parentFragment, Context context, FragmentManager fm,
                                VerticalViewPager viewPager){
		mParentFragment = parentFragment;
		if(mParentFragment != null){
			mParentFragment.setOnVisibleListener(new BaseFragment.OnVisibleListener() {
				@Override
				public void onVisibilityChanged(boolean isVisibileToUser) {
					Fragment fragment = getFragment(mDefaultIndex,false);
					if(fragment != null){
						fragment.setUserVisibleHint(isVisibileToUser);
						fragment.setMenuVisibility(isVisibileToUser);
					}
				}
			});
		}
		mContext = context;
		mFragmentManager = fm;
		vViewPager = viewPager;
		vViewPager.setAdapter(this);
	}
	/**
	 *
	 * @param context
	 * @param fm
	 * @param viewPager
	 *
	 * Fragment嵌套Fragment时，强烈不建议使用此方法，防止setUserVisibleHint方法出现混乱
	 * 请使用FragmentPagerAdapter(BaseFragment parentFragment,Context context, FragmentManager fm,ViewPager viewPager)
	 *
	 */
	@Deprecated
	public FragmentPagerAdapter(Context context, FragmentManager fm,
								ViewPager viewPager) {
		this(null,context,fm,viewPager);
	}

	@Override
	public CharSequence getPageTitle(int arg0) {
		return mFragmentInfos.get(arg0).tag;
	}

	@Override
	public void startUpdate(ViewGroup container) {
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (mCurTransaction == null) {
			mCurTransaction = mFragmentManager.beginTransaction();
		}
		mCurTransaction.detach((Fragment) object);
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		Fragment fragment = (Fragment) object;
		if (fragment != mCurrentPrimaryItem) {
			if (mCurrentPrimaryItem != null) {
				mCurrentPrimaryItem.setMenuVisibility(false);
				mCurrentPrimaryItem.setUserVisibleHint(false);
			}
			if (fragment != null) {
				if(mParentFragment != null && !mParentFragment.getUserVisibleHint()){
					fragment.setMenuVisibility(false);
					fragment.setUserVisibleHint(false);
				}else{
					fragment.setMenuVisibility(true);
					fragment.setUserVisibleHint(true);
				}
			}
			mCurrentPrimaryItem = fragment;
		}
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		if (mCurTransaction != null) {
			mCurTransaction.commitAllowingStateLoss();
			mCurTransaction = null;
			mFragmentManager.executePendingTransactions();
		}
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return ((Fragment) object).getView() == view;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		if (mCurTransaction == null) {
			mCurTransaction = mFragmentManager.beginTransaction();
		}

		Fragment fragment = getFragment(position, true);
		if (fragment.getFragmentManager() != null) {
			mCurTransaction.attach(fragment);
		} else {
			mCurTransaction.add(container.getId(), fragment,
					mFragmentInfos.get(position).tag);
		}
		if (fragment != mCurrentPrimaryItem) {
			fragment.setMenuVisibility(false);
			fragment.setUserVisibleHint(false);
		}

		return fragment;
	}

	@Override
	public int getCount() {
		return mFragmentInfos.size();
	}

	@Override
	public int getItemPosition(Object object) {
		final int size = mFragmentInfos.size();
		for (int i = 0; i < size; ++i) {
			if (object == mFragmentInfos.get(i).fragment) {
				return i;
			}
		}
		return POSITION_NONE;
	};

	public int getItemPositionByTag(String tag) {
		final int size = mFragmentInfos.size();
		for (int i = 0; i < size; ++i) {
			if (TextUtils.equals(mFragmentInfos.get(i).tag, tag)) {
				return i;
			}
		}
		return POSITION_NONE;
	}

	public Fragment getFragment(int position, boolean create) {
		if(mFragmentInfos == null || mFragmentInfos.size()<1 || position < 0 || position >= mFragmentInfos.size()){
			return null;
		}
		FragmentInfo fi = mFragmentInfos.get(position);
		if (fi.fragment == null) {
			// Do we already have this fragment?
			fi.fragment = mFragmentManager.findFragmentByTag(fi.tag);
			if (fi.fragment == null && create) {
				fi.fragment = Fragment.instantiate(mContext,
						fi.clazz.getName(), fi.args);
				fi.clazz = null;
				fi.args = null;
			}
		}
		return fi.fragment;
	}

	public boolean addFragment(String tag, Class<? extends Fragment> clazz,
							   Bundle args) {
		return addFragment(tag, clazz, args, false);
	}

	public boolean addFragment(String tag, Class<? extends Fragment> clazz,
							   Bundle args, boolean hasActionMenu) {
		FragmentInfo info = findFragmentInfoByTag(tag);
		if (info == null) {
			mFragmentInfos.add(new FragmentInfo(tag, clazz, args));
			notifyDataSetChanged();
			return true;
		}
		return false;
	}

	public void addFragmentWithoutNotifyDataSetChanged(String tag, Class<? extends Fragment> clazz,
													   Bundle args){
		FragmentInfo info = findFragmentInfoByTag(tag);
		if (info == null) {
			mFragmentInfos.add(new FragmentInfo(tag, clazz, args));
		}
	}

	public boolean removeFragment(String tag) {
		FragmentInfo info = findFragmentInfoByTag(tag);
		if (info != null) {
			mFragmentInfos.remove(info);
			notifyDataSetChanged();
			return true;
		}
		return false;
	}

	private FragmentInfo findFragmentInfoByTag(String tag) {
		for (FragmentInfo info : mFragmentInfos) {
			if (TextUtils.equals(info.tag, tag)) {
				return info;
			}
		}
		return null;
	}

	public int addFragment(String tag, int index,
						   Class<? extends Fragment> clazz, Bundle args, boolean hasActionMenu) {
		mFragmentInfos.add(index, new FragmentInfo(tag, clazz, args));
		notifyDataSetChanged();
		return index;
	}

	public void clearFragmens(){
		if(mFragmentInfos != null && mFragmentInfos.size()>0){
			mFragmentInfos.clear();
		}
		notifyDataSetChanged();
	}

	public Fragment getCurrentPrimaryItem(){
		return mCurrentPrimaryItem;
	}
}
