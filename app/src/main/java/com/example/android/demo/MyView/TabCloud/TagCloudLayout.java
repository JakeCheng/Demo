package com.example.android.demo.MyView.TabCloud;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 标签流容器
 */
public class TagCloudLayout extends ViewGroup {

    private int mLineSpacing;
    private int mTagSpacing;
    private BaseAdapter mAdapter;
    private TagItemClickListener mListener;
    private DataChangeObserver mObserver;

    public TagCloudLayout(Context context) {
        super(context);
        init(context, null, 0);
    }
    public TagCloudLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TagCloudLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }
    
    private void init(Context context, AttributeSet attrs, int defStyle) {
        TagCloudConfiguration config = new TagCloudConfiguration(context, attrs);
        mLineSpacing = config.getLineSpacing();
        mTagSpacing = config.getTagSpacing();
    }

    private void drawLayout() {
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }

        this.removeAllViews();

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View view = mAdapter.getView(i,null,null);
            final int position = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.itemClick(position);
                    }
                }
            });
            this.addView(view);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wantHeight = 0;
        int wantWidth = resolveSize(0, widthMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int childLeft = paddingLeft;
        int childTop = paddingTop;
        int lineHeight = 0;

        //TODO 固定列的数量所需要的代码
        for (int i = 0; i < getChildCount(); i++) {
            final View childView = getChildAt(i);
            LayoutParams params = childView.getLayoutParams();
            childView.measure(
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, params.width),
                    getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, params.height)
            );
            int childHeight = childView.getMeasuredHeight();
            int childWidth = childView.getMeasuredWidth();
            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > wantWidth) {
                childLeft = paddingLeft;
                childTop += mLineSpacing + childHeight;
                lineHeight = childHeight;
            }

            childLeft += childWidth + mTagSpacing;
        }
        wantHeight += childTop + lineHeight + paddingBottom;
        setMeasuredDimension(wantWidth, resolveSize(wantHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //TODO 固定列的数量所需要的代码

        int width = r - l;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int childLeft = paddingLeft;
        int childTop = paddingTop;
        int lineHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE) {
                continue;
            }
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > width) {
                childLeft = paddingLeft;
                childTop += mLineSpacing + lineHeight;
                lineHeight = childHeight;
            }

            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + mTagSpacing;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(this.getContext(), attrs);
    }

    public void setAdapter(BaseAdapter adapter){
        if (mAdapter == null){
            mAdapter = adapter;
            if (mObserver == null){
                mObserver = new DataChangeObserver();
                mAdapter.registerDataSetObserver(mObserver);
            }
            drawLayout();
        }
    }

    public void setItemClickListener(TagItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface TagItemClickListener {
        void itemClick(int position);
    }

    class DataChangeObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            TagCloudLayout.this.drawLayout();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }

}
