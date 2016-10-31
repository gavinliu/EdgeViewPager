package cn.gavinliu.edgeviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Gavin on 2016/10/31.
 */

public class EdgeViewPager extends ViewPager {

    public EdgeViewPager(Context context) {
        this(context, null);
    }

    public EdgeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(true, new MyPageTransformer());
    }

    private int getClientWidth() {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    class MyPageTransformer implements PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            final int scrollX = getScrollX();

            final float transformPos = (float) (page.getLeft() - getPaddingLeft() - scrollX) / getClientWidth();

            Log.d("MyPageTransformer", page.getTag() + ": transformPos:" + transformPos + " position:" + position);

            int height = getMeasuredHeight();
            float scaleH = height * 0.9f;
            int contentH = getContext().getResources().getDimensionPixelSize(R.dimen.content_height);
            float scaleCH = contentH * 0.9f;

            float heightOffset = (height - scaleH) / 2 + scaleCH - contentH;

            if (transformPos <= 1f || transformPos >= -1) {
                float offset = Math.abs(transformPos);

                page.setScaleX(1f - 0.1f * offset);
                page.setScaleY(1f - 0.1f * offset);

                page.setTranslationY(heightOffset * offset);
            }

        }
    }

}
