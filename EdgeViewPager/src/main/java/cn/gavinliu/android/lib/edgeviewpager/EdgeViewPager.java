package cn.gavinliu.android.lib.edgeviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Gavin on 2016/10/31.
 */
public class EdgeViewPager extends ViewPager {

    private int mEdgeMargin;
    private int mEdgePageMargin;

    private float mEdgePercent;

    private int mContentHeight;

    public EdgeViewPager(Context context) {
        this(context, null);
    }

    public EdgeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.EdgeViewPager);
            mEdgeMargin = array.getDimensionPixelSize(R.styleable.EdgeViewPager_edge_margin, 0);
            mEdgePageMargin = array.getDimensionPixelSize(R.styleable.EdgeViewPager_edge_page_margin, 0);

            mEdgePercent = array.getFraction(R.styleable.EdgeViewPager_edge_percent, 1, 1, 0);

            mContentHeight = array.getDimensionPixelSize(R.styleable.EdgeViewPager_content_height, 0);
            array.recycle();
        }
        setClipToPadding(false);
        setOffscreenPageLimit(3);
        setPageMargin(mEdgePageMargin);
        setPadding(mEdgeMargin, 0, mEdgeMargin, 0);

        if (mEdgePercent > 0) {
            setPageTransformer(true, new EdgePageTransformer());
        }
    }

    private int getClientWidth() {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int getClientHeight() {
        return getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }

    class EdgePageTransformer implements PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            float transformPos = (float) (page.getLeft() - getPaddingLeft() - getScrollX()) / getClientWidth();
            if (BuildConfig.DEBUG) {
                Log.d("EdgePageTransformer", page.getTag() + ": transformPos:" + transformPos + " position:" + position);
            }

            int height = getClientHeight();
            float scaledH = height * mEdgePercent;

            float heightOffset = 0;
            if (mContentHeight != 0) {
                float scaledCH = mContentHeight * mEdgePercent;
                heightOffset = (height - scaledH) / 2 + scaledCH - mContentHeight;
            }

            if (transformPos <= 1f || transformPos >= -1f) {
                float offset = Math.abs(transformPos);
                page.setScaleY(1f - (1f - mEdgePercent) * offset);
                page.setTranslationY(heightOffset * offset);
            }
        }
    }

}
