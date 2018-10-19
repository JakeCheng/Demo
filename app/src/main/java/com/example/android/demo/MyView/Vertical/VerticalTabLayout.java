package com.example.android.demo.MyView.Vertical;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.android.demo.R;
import com.example.android.demo.Utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * capy from https://github.com/qstumn/VerticalTabLayout
 * 垂直的TabLayout
 */
public class VerticalTabLayout extends NestedScrollView {
    private static final String TAG = "VerticalTabLayout";
    private Context mContext;
    private TabStrip mTabStrip;
    private int mColorIndicator;
    private TabView mSelectedTab;
    private int mTabMargin;
    private int mIndicatorWidth;
    private int mIndicatorGravity;
    private float mIndicatorCorners;
    private int mTabMode;
    private int mTabHeight;

    public static int TAB_MODE_FIXED = 10;
    public static int TAB_MODE_SCROLLABLE = 11;

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private TabAdapter mTabAdapter;

    private List<OnTabSelectedListener> mTabSelectedListeners;
    private OnTabPageChangeListener mTabPageChangeListener;
    private DataSetObserver mPagerAdapterObserver;

    public VerticalTabLayout(Context context) {
        this(context, null);
    }

    public VerticalTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setFillViewport(true);
        mTabSelectedListeners = new ArrayList<>();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerticalTabLayout);
        mColorIndicator = typedArray.getColor(R.styleable.VerticalTabLayout_indicator_color,
                context.getResources().getColor(R.color.white));
        mIndicatorWidth = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_indicator_width, dp2px(3));
        mIndicatorCorners = typedArray.getDimension(R.styleable.VerticalTabLayout_indicator_corners, 0);
        mIndicatorGravity = typedArray.getInteger(R.styleable.VerticalTabLayout_indicator_gravity, Gravity.LEFT);
        mTabMargin = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_tab_margin, 0);
        Logger.error(TAG,"==========tab margin========" + mTabMargin);
        mTabMode = typedArray.getInteger(R.styleable.VerticalTabLayout_tab_mode, TAB_MODE_FIXED);
        mTabHeight = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_tab_height, LayoutParams.WRAP_CONTENT);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) removeAllViews();
        initTabStrip();
    }

    private void initTabStrip() {
        mTabStrip = new TabStrip(mContext);
        addView(mTabStrip, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void removeAllTabs() {
        mTabStrip.removeAllViews();
        mSelectedTab = null;
    }

    public TabView getTabAt(int position) {
        return (TabView) mTabStrip.getChildAt(position);
    }

    private void addTabWithMode(TabView tabView) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        initTabWithMode(params);
        mTabStrip.addView(tabView, params);
        if (mTabStrip.indexOfChild(tabView) == 0) {
            tabView.setChecked(true);
            params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
            //整体距离上边的距离
            params.setMargins(0, getResources().getDimensionPixelOffset(R.dimen.view_dimen_7), 0, 0);
            Logger.error(TAG,"==========tab margin===111=====" + mTabMargin);
            tabView.setLayoutParams(params);
            mSelectedTab = tabView;
        }
    }

    private void initTabWithMode(LinearLayout.LayoutParams params) {
        if (mTabMode == TAB_MODE_FIXED) {
            params.height = 0;
            params.weight = 1.0f;
            params.setMargins(0, mTabMargin, 0, 0);
        } else if (mTabMode == TAB_MODE_SCROLLABLE) {
            params.height = mTabHeight;
            params.weight = 0f;
            params.setMargins(0, mTabMargin, 0, 0);
        }
    }

    private void scrollToTab(int position) {
        final TabView tabView = getTabAt(position);
        tabView.post(new Runnable() {
            @Override
            public void run() {
                int y = getScrollY();
                int tabTop = tabView.getTop() + tabView.getHeight() / 2 - y;
                int target = getHeight() / 2;
                if (tabTop > target) {
                    scrollBy(0, tabTop - target);
                } else if (tabTop < target) {
                    scrollBy(0, tabTop - target);
                }
            }
        });
    }

    private float mLastPositionOffset;

    private void scrollByTab(int position, final float positionOffset) {
        final TabView tabView = getTabAt(position);
        int y = getScrollY();
        int tabTop = tabView.getTop() + tabView.getHeight() / 2 - y;
        int target = getHeight() / 2;
        int nextScrollY = tabView.getHeight() + mTabMargin;
        if (positionOffset > 0) {
            float percent = positionOffset - mLastPositionOffset;
            if (tabTop > target) {
                smoothScrollBy(0, (int) (nextScrollY * percent));
            }
        }
        mLastPositionOffset = positionOffset;
    }

    public void addTab(TabView tabView) {
        if (tabView != null) {
            addTabWithMode(tabView);
            tabView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = mTabStrip.indexOfChild(view);
                    setTabSelected(position);
                }
            });
        } else {
            throw new IllegalStateException("tabview can't be null");
        }
    }

    public void setTabSelected(int position) {
        TabView view = getTabAt(position);
        for (int i = 0; i < mTabSelectedListeners.size(); i++) {
            OnTabSelectedListener listener = mTabSelectedListeners.get(i);
            if (listener != null) {
                if (view == mSelectedTab) {
                    listener.onTabReselected(view, position);
                } else {
                    listener.onTabSelected(view, position);
                }
            }
        }
        if (view != mSelectedTab) {
            mSelectedTab.setChecked(false);
            view.setChecked(true);
//            if (mViewPager == null)
            mTabStrip.moveIndicator(position);
            mSelectedTab = view;
            scrollToTab(position);
        }
    }

    public void setTabBadge(int tabPosition, int badgeNum) {
        getTabAt(tabPosition).setBadge(badgeNum);
    }

    public void setTabMode(int mode) {
        if (mode != TAB_MODE_FIXED && mode != TAB_MODE_SCROLLABLE) {
            throw new IllegalStateException("only support TAB_MODE_FIXED or TAB_MODE_SCROLLABLE");
        }
        if (mode == mTabMode) return;
        mTabMode = mode;
        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
            View view = mTabStrip.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            initTabWithMode(params);
            if (i == 0) {
                params.setMargins(0, 0, 0, 0);
            }
            view.setLayoutParams(params);
        }
        mTabStrip.invalidate();
        mTabStrip.post(new Runnable() {
            @Override
            public void run() {
                mTabStrip.updataIndicatorMargin();
            }
        });
    }

    /**
     * only in TAB_MODE_SCROLLABLE mode will be supported
     *
     * @param margin margin
     */
    public void setTabMargin(int margin) {
        if (margin == mTabMargin) return;
        mTabMargin = margin;
        if (mTabMode == TAB_MODE_FIXED) return;
        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
            View view = mTabStrip.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.setMargins(0, /*i == 0 ? 0 :*/ mTabMargin, 0, 0);
            view.setLayoutParams(params);
        }
        mTabStrip.invalidate();
        mTabStrip.post(new Runnable() {
            @Override
            public void run() {
                mTabStrip.updataIndicatorMargin();
            }
        });
    }

    /**
     * only in TAB_MODE_SCROLLABLE mode will be supported
     *
     * @param height height
     */
    public void setTabHeight(int height) {
        if (height == mTabHeight) return;
        mTabHeight = height;
        if (mTabMode == TAB_MODE_FIXED) return;
        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
            View view = mTabStrip.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.height = mTabHeight;
            view.setLayoutParams(params);
        }
        mTabStrip.invalidate();
        mTabStrip.post(new Runnable() {
            @Override
            public void run() {
                mTabStrip.updataIndicatorMargin();
            }
        });
    }

    public void setIndicatorColor(int color) {
        mColorIndicator = color;
        mTabStrip.invalidate();
    }

    public void setIndicatorWidth(int width) {
        mIndicatorWidth = width;
        mTabStrip.setIndicatorGravity();
    }

    public void setIndicatorCorners(int corners) {
        mIndicatorCorners = corners;
        mTabStrip.invalidate();
    }

    /**
     * @param gravity only support Gravity.LEFT,Gravity.RIGHT,Gravity.FILL
     */
    public void setIndicatorGravity(int gravity) {
        if (gravity == Gravity.LEFT || gravity == Gravity.RIGHT || Gravity.FILL == gravity) {
            mIndicatorGravity = gravity;
            mTabStrip.setIndicatorGravity();
        } else {
            throw new IllegalStateException("only support Gravity.LEFT,Gravity.RIGHT,Gravity.FILL");
        }
    }

    public void addOnTabSelectedListener(OnTabSelectedListener listener) {
        if (listener != null) {
            mTabSelectedListeners.add(listener);
        }
    }

    public void setTabAdapter(TabAdapter adapter) {
        removeAllTabs();
        if (adapter != null) {
            mTabAdapter = adapter;
            for (int i = 0; i < adapter.getCount(); i++) {
                addTab(new CategoryTabView(mContext).setIcon(adapter.getIcon(i))
                        .setTitle(adapter.getTitle(i)).setBadge(adapter.getBadge(i))
                        .setBackground(adapter.getBackground(i)));
            }
        } else {
            removeAllTabs();
        }
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        if (mViewPager != null && mTabPageChangeListener != null) {
            mViewPager.removeOnPageChangeListener(mTabPageChangeListener);
        }

        if (viewPager != null) {
            final PagerAdapter adapter = viewPager.getAdapter();
            if (adapter == null) {
                throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
            }

            mViewPager = viewPager;

            if (mTabPageChangeListener == null) {
                mTabPageChangeListener = new OnTabPageChangeListener();
            }
            viewPager.addOnPageChangeListener(mTabPageChangeListener);

            addOnTabSelectedListener(new OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabView tab, int position) {
                    if (mViewPager != null && mViewPager.getAdapter().getCount() >= position) {
                        mViewPager.setCurrentItem(position);
                    }
                }

                @Override
                public void onTabReselected(TabView tab, int position) {
                }
            });

            setPagerAdapter(adapter, true);
        } else {
            mViewPager = null;
            setPagerAdapter(null, true);
        }
    }

    private void setPagerAdapter(@Nullable final PagerAdapter adapter, final boolean addObserver) {
        if (mPagerAdapter != null && mPagerAdapterObserver != null) {
            mPagerAdapter.unregisterDataSetObserver(mPagerAdapterObserver);
        }

        mPagerAdapter = adapter;

        if (addObserver && adapter != null) {
            if (mPagerAdapterObserver == null) {
                mPagerAdapterObserver = new PagerAdapterObserver();
            }
            adapter.registerDataSetObserver(mPagerAdapterObserver);
        }

        populateFromPagerAdapter();
    }

    private void populateFromPagerAdapter() {
        removeAllTabs();
        if (mPagerAdapter != null) {
            final int adapterCount = mPagerAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                if (mPagerAdapter instanceof TabAdapter) {
                    mTabAdapter = (TabAdapter) mPagerAdapter;
                    addTab(new CategoryTabView(mContext).setIcon(mTabAdapter.getIcon(i))
                            .setTitle(mTabAdapter.getTitle(i)).setBadge(mTabAdapter.getBadge(i))
                            .setBackground(mTabAdapter.getBackground(i)));
                } else {
                    String title = mPagerAdapter.getPageTitle(i) == null ? "tab" + i : mPagerAdapter.getPageTitle(i).toString();
                    addTab(new CategoryTabView(mContext).setTitle(
                            new CategoryTabView.TabTitle.Builder(mContext).setContent(title).build()));
                }
            }

            // Make sure we reflect the currently set ViewPager item
            if (mViewPager != null && adapterCount > 0) {
                final int curItem = mViewPager.getCurrentItem();
                if (curItem != getSelectedTabPosition() && curItem < getTabCount()) {
                    setTabSelected(curItem);
                }
            }
        } else {
            removeAllTabs();
        }
    }

    private int getTabCount() {
        return mTabStrip.getChildCount();
    }

    private int getSelectedTabPosition() {
//        if (mViewPager != null) return mViewPager.getCurrentItem();
        int index = mTabStrip.indexOfChild(mSelectedTab);
        return index == -1 ? 0 : index;
    }


    private class TabStrip extends LinearLayout {
        private float mIndicatorY;
        private float mIndicatorX;
        private float mIndicatorBottomY;
        private int mLastWidth;
        private int mIndicatorHeight;
        private Paint mIndicatorPaint;
        //record invalidate count,used to initialize on mIndicatorBottomY
        private long mInvalidateCount;

        public TabStrip(Context context) {
            super(context);
            setWillNotDraw(false);
            setOrientation(LinearLayout.VERTICAL);
            mIndicatorPaint = new Paint();
            mIndicatorGravity = mIndicatorGravity == 0 ? Gravity.LEFT : mIndicatorGravity;
            setIndicatorGravity();
        }

        @Override
        protected void onFinishInflate() {
            super.onFinishInflate();
            mIndicatorBottomY = mIndicatorHeight;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (getChildCount() > 0) {
                View childView = getChildAt(0);
                mIndicatorHeight = childView.getMeasuredHeight();
                if (mInvalidateCount == 0) {
                    mIndicatorBottomY = mIndicatorHeight;
                }
                mInvalidateCount++;
            }
        }

        protected void updataIndicatorMargin() {
            int index = getSelectedTabPosition();
            mIndicatorY = calcIndicatorY(index);
            mIndicatorBottomY = mIndicatorY + mIndicatorHeight;
            invalidate();
        }


        protected void setIndicatorGravity() {
            if (mIndicatorGravity == Gravity.LEFT) {
                mIndicatorX = 0;
                if (mLastWidth != 0) mIndicatorWidth = mLastWidth;
                setPadding(mIndicatorWidth, 0, 0, 0);
            } else if (mIndicatorGravity == Gravity.RIGHT) {
                if (mLastWidth != 0) mIndicatorWidth = mLastWidth;
                setPadding(0, 0, mIndicatorWidth, 0);
            } else if (mIndicatorGravity == Gravity.FILL) {
                mIndicatorX = 0;
                setPadding(0, 0, 0, 0);
            }
            post(new Runnable() {
                @Override
                public void run() {
                    if (mIndicatorGravity == Gravity.RIGHT) {
                        mIndicatorX = getWidth() - mIndicatorWidth;
                    } else if (mIndicatorGravity == Gravity.FILL) {
                        mLastWidth = mIndicatorWidth;
                        mIndicatorWidth = getWidth();
                    }
                    invalidate();
                }
            });
        }

        private float calcIndicatorY(float offset) {
            if (mTabMode == TAB_MODE_FIXED)
                return offset * mIndicatorHeight;
            return offset * (mIndicatorHeight + mTabMargin);
        }


        protected void moveIndicator(float offset) {
            mIndicatorY = calcIndicatorY(offset);
            mIndicatorBottomY = mIndicatorY + mIndicatorHeight;
            invalidate();
        }

        /**
         * move indicator to a tab location
         *
         * @param index tab location's index
         */
        protected void moveIndicator(final int index) {
            final int direction = index - getSelectedTabPosition();
            final float target = calcIndicatorY(index);
            final float targetBottom = target + mIndicatorHeight;
            if (mIndicatorY == target) return;
            post(new Runnable() {
                @Override
                public void run() {
                    ValueAnimator anime = null;
                    if (direction > 0) {
                        anime = ValueAnimator.ofFloat(mIndicatorBottomY, targetBottom);
                        anime.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float value = Float.parseFloat(animation.getAnimatedValue().toString());
                                mIndicatorBottomY = value;
                                invalidate();
                            }
                        });
                        anime.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ValueAnimator anime2 = ValueAnimator.ofFloat(mIndicatorY, target);
                                anime2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        float value = Float.parseFloat(animation.getAnimatedValue().toString());
                                        mIndicatorY = value;
                                        invalidate();
                                    }
                                });
                                anime2.setDuration(100).start();
                            }
                        });

                    } else if (direction < 0) {
                        anime = ValueAnimator.ofFloat(mIndicatorY, target);
                        anime.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float value = Float.parseFloat(animation.getAnimatedValue().toString());
                                mIndicatorY = value;
                                invalidate();
                            }
                        });
                        anime.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ValueAnimator anime2 = ValueAnimator.ofFloat(mIndicatorBottomY, targetBottom);
                                anime2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        float value = Float.parseFloat(animation.getAnimatedValue().toString());
                                        mIndicatorBottomY = value;
                                        invalidate();
                                    }
                                });
                                anime2.setDuration(100).start();
                            }
                        });
                    }
                    if (anime != null) {
                        anime.setDuration(100).start();
                    }
                }
            });
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            mIndicatorPaint.setColor(mColorIndicator);
            RectF r = new RectF(mIndicatorX, mIndicatorY,
                    mIndicatorX + mIndicatorWidth, mIndicatorBottomY);
            if (mIndicatorCorners != 0) {
                canvas.drawRoundRect(r, mIndicatorCorners, mIndicatorCorners, mIndicatorPaint);
            } else {
                canvas.drawRect(r, mIndicatorPaint);
            }
        }

    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private class OnTabPageChangeListener implements ViewPager.OnPageChangeListener {

        public OnTabPageChangeListener() {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
//            mTabStrip.moveIndicator(positionOffset + position);
        }

        @Override
        public void onPageSelected(int position) {
            if (position != getSelectedTabPosition() && !getTabAt(position).isPressed()) {
                setTabSelected(position);
            }
        }
    }

    private class PagerAdapterObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            populateFromPagerAdapter();
        }

        @Override
        public void onInvalidated() {
            populateFromPagerAdapter();
        }
    }

    public interface OnTabSelectedListener {

        void onTabSelected(TabView tab, int position);

        void onTabReselected(TabView tab, int position);
    }
}
