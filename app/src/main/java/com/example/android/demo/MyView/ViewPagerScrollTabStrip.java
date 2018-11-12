package com.example.android.demo.MyView;

/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

class ViewPagerScrollTabStrip extends LinearLayout {

    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0;
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 3;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;

    private final int mBottomBorderThickness;
    private final Paint mBottomBorderPaint;

    private int mSelectedIndicatorThickness;
    private final Paint mSelectedIndicatorPaint;

    private final int mDefaultBottomBorderColor;

    private int mSelectedPosition;
    private float mSelectionOffset;

    private ViewPagerScrollTabBar.TabColorizer mCustomTabColorizer;
    private final SimpleTabColorizer mDefaultTabColorizer;

    private int mWidth = 0;
    private RectF mRect;
    private int mRadius;
    private boolean isStripGradient;
    private int indicatorMarginTop;

    ViewPagerScrollTabStrip(Context context) {
        this(context, null);
    }

    ViewPagerScrollTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        final float density = getResources().getDisplayMetrics().density;

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.colorForeground, outValue, true);
        final int themeForegroundColor = outValue.data;

        mDefaultBottomBorderColor = setColorAlpha(themeForegroundColor,
                DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);

        mDefaultTabColorizer = new SimpleTabColorizer();
        mDefaultTabColorizer.setIndicatorColors(DEFAULT_SELECTED_INDICATOR_COLOR);

        mBottomBorderThickness = (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
        mBottomBorderPaint = new Paint();
        mBottomBorderPaint.setColor(mDefaultBottomBorderColor);

        mSelectedIndicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
        mSelectedIndicatorPaint = new Paint();
    }

    public void setSelectedIndicatorThickness(int thickness){
        mSelectedIndicatorThickness = thickness;
    }

    public void setIndicatorMarginTop(int indicatorMarginTop) {
        this.indicatorMarginTop = indicatorMarginTop;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public void setRadius(int radius){
        mRadius = radius;
    }

    void setCustomTabColorizer(ViewPagerScrollTabBar.TabColorizer customTabColorizer) {
        mCustomTabColorizer = customTabColorizer;
        invalidate();
    }

    public void setStriopGradient(boolean striopGradient) {
        isStripGradient = striopGradient;
    }

    void setSelectedIndicatorColors(int... colors) {
        // Make sure that the custom colorizer is removed
        mCustomTabColorizer = null;
        mDefaultTabColorizer.setIndicatorColors(colors);
        invalidate();
    }

    void onViewPagerPageChanged(int position, float positionOffset) {
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();
        final int childCount = getChildCount();
        final ViewPagerScrollTabBar.TabColorizer tabColorizer = mCustomTabColorizer != null
                ? mCustomTabColorizer
                : mDefaultTabColorizer;

        // Thick colored underline below the current selection
        if (childCount > 0) {
            View selectedTitle = getChildAt(mSelectedPosition);
            if(selectedTitle == null){
                return;
            }
            int color = tabColorizer.getIndicatorColor(mSelectedPosition);
            // Draw the selection partway between the tabs
            View nextTitle = getChildAt(mSelectedPosition + 1);

            if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {
                int nextColor = tabColorizer.getIndicatorColor(mSelectedPosition + 1);
                if (color != nextColor) {
                    color = blendColors(nextColor, color, mSelectionOffset);
                }
            }

            /**
             * 修复线与文字宽度不一致，这个只适用于，tabStrip添加的child是textView，其它情况走原有逻辑
             */
            if(selectedTitle instanceof TextView && nextTitle instanceof TextView) {
                TextView selectTextView = null;
                TextView nextTextView = null;
                selectTextView = (TextView) selectedTitle;
                nextTextView = (TextView) nextTitle;

                Rect bound = new Rect();
                selectTextView.getPaint().getTextBounds(selectTextView.getText().toString(), 0, selectTextView.getText().length(), bound);
                int contentWidth = bound.width();//这里要获取文字的宽度,而不是控件的宽度
                int leftX = selectTextView.getLeft() + selectTextView.getWidth() / 2 - contentWidth / 2;
                int rightX = selectTextView.getLeft() + selectTextView.getWidth() / 2 + contentWidth / 2;

                bound = new Rect();
                nextTextView.getPaint().getTextBounds(nextTextView.getText().toString(), 0, nextTextView.getText().length(), bound);
                contentWidth = bound.width();
                int nextLeftX = nextTextView.getLeft() + nextTextView.getWidth() / 2 - contentWidth / 2;
                int nextRightX = nextTextView.getLeft() + nextTextView.getWidth() / 2 + contentWidth / 2;

                if(null == mRect){
                    mRect = new RectF();
                }
                mRect.left = leftX + (nextLeftX - leftX) * mSelectionOffset;
                mRect.right = rightX + (nextRightX - rightX) * mSelectionOffset;
                mRect.top = height - mSelectedIndicatorThickness - indicatorMarginTop;
                mRect.bottom = height-indicatorMarginTop;

                mSelectedIndicatorPaint.setColor(color);
                //半径就是mSelectedIndicatorThickness，防止半径过大导致在某些机型上显示异常
                canvas.drawRoundRect(mRect,mSelectedIndicatorThickness,mSelectedIndicatorThickness,mSelectedIndicatorPaint);

                canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height, mBottomBorderPaint);
                return;
            }

            int left = selectedTitle.getLeft();
            int right = selectedTitle.getRight();

            if(mWidth == -1){
                left += selectedTitle.getPaddingLeft();
                right -= selectedTitle.getPaddingRight();
            }
            int tabWidth = right - left;

            if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {
                if(mWidth == -1){
                    left = (int) (mSelectionOffset * (nextTitle.getLeft() + nextTitle.getPaddingLeft() - left) + left);
                    right = (int) (mSelectionOffset * (nextTitle.getRight() - nextTitle.getPaddingRight() - right) + right);
                }else{
                    left = (int) (mSelectionOffset * (nextTitle.getLeft() - left) + left);
                    right = (int) (mSelectionOffset * (nextTitle.getRight() - right) + right);
                }
            }

            int cutWidth = 0;
            if(mWidth == -1){
               cutWidth = tabWidth;
            }else{
                if (tabWidth > mWidth) {
                    cutWidth = (tabWidth - mWidth) / 2;
                }
            }
            int rectLeft = left + cutWidth ;
            int rectRight = right - cutWidth;
            if (rectRight - rectLeft < mWidth) {
                rectLeft = rectLeft - (mWidth - (rectRight - rectLeft))/2;
                rectRight = rectRight + (mWidth - (rectRight - rectLeft))/2;
            }


            if(mRect == null){
                mRect = new RectF(rectLeft,height - mSelectedIndicatorThickness-indicatorMarginTop, rectRight,
                        height-indicatorMarginTop);
            }else {
                mRect.set(rectLeft, height - mSelectedIndicatorThickness-indicatorMarginTop, rectRight,
                        height-indicatorMarginTop);
            }

            if(isStripGradient) {
                LinearGradient lg = new LinearGradient(mRect.left, mRect.top, mRect.right, mRect.bottom,
                        new int[]{color, setColorAlpha(color, (byte) 0x00)}, null, LinearGradient.TileMode.CLAMP);
                mSelectedIndicatorPaint.setShader(lg);
            }else {
                mSelectedIndicatorPaint.setColor(color);
            }
            //半径就是mSelectedIndicatorThickness，防止半径过大导致在某些机型上显示异常
            canvas.drawRoundRect(mRect,mSelectedIndicatorThickness,mSelectedIndicatorThickness,mSelectedIndicatorPaint);
        }

        // Thin underline along the entire bottom edge
        canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height, mBottomBorderPaint);
    }

    /**
     * Set the demo_tween_scale value of the {@code color} to be the given {@code demo_tween_scale} value.
     */
    private static int setColorAlpha(int color, byte alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Blend {@code color1} and {@code color2} using the given ratio.
     *
     * @param ratio of which to blend. 1.0 will return {@code color1}, 0.5 will give an even blend,
     *              0.0 will return {@code color2}.
     */
    private static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }

    private static class SimpleTabColorizer implements ViewPagerScrollTabBar.TabColorizer {
        private int[] mIndicatorColors;

        @Override
        public final int getIndicatorColor(int position) {
            return mIndicatorColors[position % mIndicatorColors.length];
        }

        void setIndicatorColors(int... colors) {
            mIndicatorColors = colors;
        }
    }
}
