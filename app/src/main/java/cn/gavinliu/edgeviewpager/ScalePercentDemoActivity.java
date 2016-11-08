package cn.gavinliu.edgeviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.gavinliu.android.lib.edgeviewpager.EdgeViewPager;

/**
 * Created by Gavin on 16-11-3.
 */

public class ScalePercentDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_percent);

        EdgeViewPager mViewPager = (EdgeViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new MyAdapter());
        mViewPager.setCurrentItem(1);
    }

    int[] colors = {0xFF795548, 0xFF212121, 0xFFFFC107, 0xFF4CAF50, 0xFF8BC34A, 0xFF448AFF,
            0xFFFF5252, 0xFFC2185B, 0xFF009688, 0xFFFFC107};

    class MyAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_pager, null);
            view.setTag(position);
            container.addView(view);

            view.setBackgroundColor(colors[position]);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return colors.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
