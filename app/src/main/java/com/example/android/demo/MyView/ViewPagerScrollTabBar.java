package com.example.android.demo.MyView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.demo.Interface.ITabBarView;
import com.example.android.demo.MyApplication;
import com.example.android.demo.R;

/**
 * 横向滑动TabBar
 */
public class ViewPagerScrollTabBar extends HorizontalScrollView {

    public interface TabColorizer {
        int getIndicatorColor(int position);
    }

    private static final int TITLE_OFFSET_DIPS = 24;

    private int mTitleOffset;

    private int mTabViewLayoutId;
    private int mTabViewTextViewId;
    private boolean mDistributeEvenly;
    private boolean isNeedSelectBold;

    private ViewPager mViewPager;
    private SparseArray<String> mContentDescriptions = new SparseArray<String>();
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

    private final ViewPagerScrollTabStrip mTabStrip;

    private int frgCount;
    private int mSelectTextColor;
    private int mUnSelectTextColor;
    private int mTitleSize = MyApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
    private int mTitleSelectSize = 0;
    private boolean mFakeBoldText = false;
    private Typeface mTypeface = Typeface.DEFAULT_BOLD;
    private int mLeftPadding = 0;
    private int mRightPadding = 0;

    private boolean mIsDiffWithTab;//如果tab是不同长度的，设置为true可以防止滑动结束之后tab的宽度回到初始宽度

    private OnTabClickListener mOnTabClickListener;

    private int mTabRedDotId;
    private int mNeedRedDotPos;

    private boolean isNeedMatchParent = false;

    public ViewPagerScrollTabBar(Context context) {
        this(context, null);
    }

    public ViewPagerScrollTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerScrollTabBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);

        mTitleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

        mTabStrip = new ViewPagerScrollTabStrip(context);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        llp.bottomMargin = 0;

        addView(mTabStrip, llp);

        mSelectTextColor = getResources().getColor(R.color.color_14b9c7);
        mUnSelectTextColor = getResources().getColor(R.color.color_black_trans_40);
    }

    public void setTitleColor(int selectTextColor, int unSelectTextColor) {
        mSelectTextColor = selectTextColor;
        mUnSelectTextColor = unSelectTextColor;
    }

    public void setStripGradient(boolean stripGradient) {
        mTabStrip.setStriopGradient(stripGradient);
    }

    public void setIndicatorMarginTop(int indicatorMarginTop) {
       mTabStrip.setIndicatorMarginTop(indicatorMarginTop);
    }

    public void setTabStripWidth(int width) {
        mTabStrip.setWidth(width);
    }

    public void setIsDiffWithTab(boolean isDiffWithTab) {
        mIsDiffWithTab = isDiffWithTab;
    }


    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        mTabStrip.setCustomTabColorizer(tabColorizer);
    }

    public void setTabStripHeight(int height) {
        mTabStrip.setSelectedIndicatorThickness(height);
    }

    public void setTabStripRadius(int radius) {
        mTabStrip.setRadius(radius);
    }

    public void setDistributeEvenly(boolean distributeEvenly) {
        mDistributeEvenly = distributeEvenly;
    }

    /**
     * 设置颜色用于指明选中的选项卡。
     * 这些颜色被视为一个循环数组。
     * 如果只设置一种颜色则所有下划线都用同一种颜色。
     */
    public void setSelectedIndicatorColors(int... colors) {
        mTabStrip.setSelectedIndicatorColors(colors);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }

    public void setOnTabClickListener(OnTabClickListener listener){
        mOnTabClickListener=listener;
    }

    public void setTypeface(Typeface typeface) {
        mTypeface = typeface;
    }

    /**
     * Set the custom layout to be inflated for the tab views.
     *
     * @param layoutResId Layout id to be inflated
     * @param textViewId  id of the {@link TextView} in the inflated view
     */
    public void setCustomTabView(int layoutResId, int textViewId) {
        mTabViewLayoutId = layoutResId;
        mTabViewTextViewId = textViewId;
    }

    public void setCustomTabView(int layoutResId, int textViewId, int redDotId, int needRedDotPos) {
        mTabViewLayoutId = layoutResId;
        mTabViewTextViewId = textViewId;
        mTabRedDotId = redDotId;
        mNeedRedDotPos = needRedDotPos;
    }

    public void setTabLeftPadding(int leftMargin) {
        mLeftPadding = leftMargin;
    }

    public void setTabRightPadding(int rightMargin) {
        mRightPadding = rightMargin;
    }

    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();

        mViewPager = viewPager;
        if (viewPager != null) {
            populateTabStrip();
            viewPager.addOnPageChangeListener(new InternalViewPagerListener());
        }
    }

    public void removeChild(int index) {
        if (mTabStrip != null && index < mTabStrip.getChildCount() && index >= 0) {
            mTabStrip.removeViewAt(index);
        }
    }

    public void clearTabs() {
        mTabStrip.removeAllViews();
    }

    public void setTitleSize(int size) {
        mTitleSize = size;
    }

    public void setTitleSelectSize(int size) {
        mTitleSelectSize = size;
    }

    public void setFakeBoldText(boolean fakeBoldText) {
        mFakeBoldText = fakeBoldText;
        setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }

    public void setNeedMatchPrent(boolean isNeedMatchParent){
        this.isNeedMatchParent = isNeedMatchParent;
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * {@link #setCustomTabView(int, int)}.
     */
    protected TextView createDefaultTabView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
        textView.setTypeface(mTypeface);

        textView.setTextColor(mUnSelectTextColor);
        textView.setPadding(mLeftPadding, 0, mRightPadding, 0);

        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, isNeedMatchParent ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.gravity = Gravity.CENTER;


        textView.setLayoutParams(llp);

        return textView;
    }

    private void populateTabStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        frgCount = adapter.getCount();

        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = null;
            TextView tabTitleView = null;

            if (mTabViewLayoutId != 0) {
                // If there is a custom tab view layout id set, try and inflate it
                tabView = LayoutInflater.from(getContext()).inflate(mTabViewLayoutId, mTabStrip,
                        false);
                tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);
            }

            if (tabView == null) {
                tabView = createDefaultTabView(getContext());
            }

            if (tabTitleView == null && TextView.class.isInstance(tabView)) {
                tabTitleView = (TextView) tabView;
            }

            //        TODO-会不会引入坑
            if(mDistributeEvenly) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.weight = 1;
            }
            tabTitleView.setText(adapter.getPageTitle(i));
            tabTitleView.setMaxLines(1);
            tabView.setOnClickListener(new TabClickListener());
            String desc = mContentDescriptions.get(i, null);
            if (desc != null) {
                tabView.setContentDescription(desc);
            }

            mTabStrip.addView(tabView);

            if(i == mViewPager.getCurrentItem()) {
                tabTitleView.setTextColor(mSelectTextColor);
                if(mFakeBoldText) {
                    tabTitleView.getPaint().setFakeBoldText(true);
                } else {
                    tabTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }

                if (0 < mTitleSelectSize) {
                    tabTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSelectSize);
                }
            } else {
                tabTitleView.setTextColor(mUnSelectTextColor);
                if(mFakeBoldText) {
                    tabTitleView.getPaint().setFakeBoldText(false);
                } else {
                    tabTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }

                if (0 < mTitleSelectSize) {
                    tabTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
                }
            }
        }
        mTabStrip.onViewPagerPageChanged(mViewPager.getCurrentItem(), 0);
    }


    public View getTabView(int index) {
        if (mTabStrip != null && index >= 0 && index < mTabStrip.getChildCount()) {
            return mTabStrip.getChildAt(index);
        }
        return null;
    }

    public int getTabViewCount() {
        if (mTabStrip != null) {
            return mTabStrip.getChildCount();
        }
        return 0;
    }

    public void setContentDescription(int i, String desc) {
        mContentDescriptions.put(i, desc);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mViewPager != null) {
            scrollToTab(mViewPager.getCurrentItem(), 0);
        }
    }

    public void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = mTabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedChild = mTabStrip.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                targetScrollX -= mTitleOffset;
            }

            scrollTo(targetScrollX, 0);
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = mTabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }
            if(!mIsDiffWithTab){
                mTabStrip.onViewPagerPageChanged(position, positionOffset);
            }else if(positionOffset!=0){
                mTabStrip.onViewPagerPageChanged(position, positionOffset);
            }

            View selectedTitle = mTabStrip.getChildAt(position);
            int extraOffset = (selectedTitle != null)
                    ? (int) (positionOffset * selectedTitle.getWidth())
                    : 0;
            scrollToTab(position, extraOffset);

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            boolean isTextView = mTabStrip.getChildAt(0) instanceof TextView;
            for (int i = 0; i < frgCount; i++) {
                if (mTabStrip.getChildAt(i) == null) {
                    continue;
                }
                TextView textView = null;
                if(isTextView) {
                    textView = (TextView) mTabStrip.getChildAt(i);
                } else {
                    textView = mTabStrip.getChildAt(i).findViewById(mTabViewTextViewId);
                }

                if(position == i) {
                    textView.setTextColor(mSelectTextColor);
                    if (mFakeBoldText) {
                        textView.getPaint().setFakeBoldText(true);//中文仿“粗体”--使用TextPaint的仿“粗体”设置setFakeBoldText为true。
                    }else {
                        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    }
                    if (0 < mTitleSelectSize) {
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSelectSize);
                    }
                } else {
                    textView.setTextColor(mUnSelectTextColor);
                    if (mFakeBoldText) {
                        textView.getPaint().setFakeBoldText(false);//中文仿“粗体”--使用TextPaint的仿“粗体”设置setFakeBoldText为true。
                    }else {
                        textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    }
                    if (0 < mTitleSelectSize) {
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
                    }
                }
            }
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
            }
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    public void showRedDot() {
        TextView tabRedDotTv = mTabStrip.getChildAt(mNeedRedDotPos).findViewById(mTabRedDotId);
        if(tabRedDotTv != null) {
            tabRedDotTv.setVisibility(VISIBLE);
        }
    }

    public void hideRedDot() {
        TextView tabRedDotTv = mTabStrip.getChildAt(mNeedRedDotPos).findViewById(mTabRedDotId);
        if(tabRedDotTv != null) {
            tabRedDotTv.setVisibility(GONE);
        }
    }

    private class TabClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                if (v == mTabStrip.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    if (mViewPagerPageChangeListener != null) {
                        mViewPagerPageChangeListener.onPageSelected(i);
                    }
                    if (mOnTabClickListener!=null) {
                        mOnTabClickListener.onClick(i);
                    }
                    return;
                }
            }
        }
    }

    public void updateTabs(int selectIndex) {
        final PagerAdapter adapter = mViewPager.getAdapter();
        frgCount = adapter.getCount();

        int count = Math.abs(mTabStrip.getChildCount() - frgCount);
        if (frgCount < mTabStrip.getChildCount()) {
            if (mTabStrip.getChildCount() > count) {
                mTabStrip.removeViews(mTabStrip.getChildCount() - 1 - count, count);
            }
        } else if (frgCount > mTabStrip.getChildCount()) {
            for (int i = 0; i < count; i++) {
                View tabView = null;
                TextView tabTitleView = null;

                if (mTabViewLayoutId != 0) {
                    // If there is a custom tab view layout id set, try and inflate it
                    tabView = LayoutInflater.from(getContext()).inflate(mTabViewLayoutId, mTabStrip,
                            false);
                    tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);
                }

                if (tabView == null) {
                    tabView = createDefaultTabView(getContext());
                }

                if (tabTitleView == null && TextView.class.isInstance(tabView)) {
                    tabTitleView = (TextView) tabView;
                }

                //        TODO-会不会引入坑
                if (mDistributeEvenly) {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                    lp.width = 0;
                    lp.weight = 1;
                }
                tabTitleView.setMaxLines(1);
                tabView.setOnClickListener(new TabClickListener());

                mTabStrip.addView(tabView);
            }
        }
        refreshTabColor(selectIndex);
    }


    public void refreshTabColor(int selectIndex){
        final PagerAdapter adapter = mViewPager.getAdapter();
        View view;
        for (int i = 0; i < frgCount; i++) {
            view = mTabStrip.getChildAt(i);
            if (view == null) {
                continue;
            }
            if (view instanceof TextView) {
                ((TextView) view).setText(adapter.getPageTitle(i));
                if (i == selectIndex) {
                    ((TextView) view).setTextColor(mSelectTextColor);
                    if (mFakeBoldText) {
                        ((TextView) view).getPaint().setFakeBoldText(true);//中文仿“粗体”--使用TextPaint的仿“粗体”设置setFakeBoldText为true。
                    }else {
                        ((TextView) view).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    }
                    if (0 < mTitleSelectSize) {
                        ((TextView) mTabStrip.getChildAt(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSelectSize);
                    }
                } else {
                    ((TextView) view).setTextColor(mUnSelectTextColor);
                    if (mFakeBoldText) {
                        ((TextView) view).getPaint().setFakeBoldText(false);//中文仿“粗体”--使用TextPaint的仿“粗体”设置setFakeBoldText为true。
                    }else {
                        ((TextView) view).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    }
                    if (0 < mTitleSelectSize) {
                        ((TextView) mTabStrip.getChildAt(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
                    }
                }
            } else if (view instanceof ITabBarView) {
                ((ITabBarView) view).setTitle(adapter.getPageTitle(i));
                if (i == selectIndex) {
                    ((ITabBarView) view).setTabSelected(true);
                } else {
                    ((ITabBarView) view).setTabSelected(false);
                }
            }
        }
    }


    public interface OnTabClickListener{
        void onClick(int i);
    }
}
