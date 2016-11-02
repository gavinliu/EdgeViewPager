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

    private int pageMargin = 0;

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

    private int getClientHeight() {
        return getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }

    class MyPageTransformer implements PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            float transformPos = (float) (page.getLeft() - getPaddingLeft() - getScrollX()) / getClientWidth();
            Log.d("MyPageTransformer", page.getTag() + ": transformPos:" + transformPos + " position:" + position);

            int height = getClientHeight();
            float scaleH = height * 0.9f;

            int width = getClientWidth();
            float scaleW = width * 0.9f;

            int contentH = getContext().getResources().getDimensionPixelSize(R.dimen.content_height);
            float scaleCH = contentH * 0.9f;

            float widthOffset = (width - scaleW) / 2 - pageMargin;
            float heightOffset = (height - scaleH) / 2 + scaleCH - contentH;

            if (transformPos <= 1f || transformPos >= -1f) {
                float offset = Math.abs(transformPos);

//                page.setScaleX(1f - 0.1f * offset);
                page.setScaleY(1f - 0.1f * offset);

                page.setTranslationY(heightOffset * offset);
            }

            if (transformPos < 2f || transformPos > -2f) {
                float offset = Math.abs(transformPos);

                if (transformPos > 0) {
                    widthOffset = -widthOffset * offset;
                } else if (transformPos < 0) {
                    widthOffset = widthOffset * offset;
                } else {
                    widthOffset = 0;
                }
//                page.setTranslationX(widthOffset);

                Log.d("MyPageTransformer", page.getTag() + " widthOffset:" + widthOffset);
            }

        }
    }

}
