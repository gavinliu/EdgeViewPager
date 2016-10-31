package cn.gavinliu.edgeviewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EdgeViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (EdgeViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new MyAdapter());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(1);
    }

    int[] colors = {0xFF795548, 0xFF212121, 0xFFFFC107, 0xFF4CAF50, 0xFF8BC34A, 0xFF448AFF,
            0xFFFF5252, 0xFFC2185B, 0xFF009688, 0xFFFFC107, 0XFF757575};

    class MyAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_pager, null);
            view.setBackgroundColor(colors[position]);
            view.setTag(position);

            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(String.valueOf(position));

            container.addView(view);
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
