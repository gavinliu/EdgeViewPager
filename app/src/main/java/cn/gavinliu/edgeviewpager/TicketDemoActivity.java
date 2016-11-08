package cn.gavinliu.edgeviewpager;

import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.gavinliu.android.lib.edgeviewpager.EdgeViewPager;
import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * Created by Gavin on 16-11-3.
 */

public class TicketDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        EdgeViewPager mViewPager = (EdgeViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new MyAdapter());
        mViewPager.setCurrentItem(1);
    }

    int[] colors = {0xFF795548, 0xFF212121, 0xFFFFC107, 0xFF4CAF50, 0xFF8BC34A, 0xFF448AFF,
            0xFFFF5252, 0xFFC2185B, 0xFF009688, 0xFFFFC107, 0XFF757575};

    class MyAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_pager_ticket, null);
            view.setTag(position);
            container.addView(view);

            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(String.valueOf(position));

            ShapedImageView cover = (ShapedImageView) view.findViewById(R.id.cover);
            cover.setExtension(new TicketPathExtension(false));
            ShapedImageView ticketBg = (ShapedImageView) view.findViewById(R.id.ticket_bg);
            ticketBg.setExtension(new TicketPathExtension(true));

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

    class TicketPathExtension implements ShapedImageView.PathExtension {

        private boolean isTop;

        public TicketPathExtension(boolean isTop) {
            this.isTop = isTop;
        }

        @Override
        public void onLayout(Path path, int width, int height) {
            int y = height;
            if (isTop) {
                y = 0;
            }

            int circle_big_r = getResources().getDimensionPixelSize(R.dimen.big_circle_r);
            int circle_big_offset = getResources().getDimensionPixelSize(R.dimen.big_circle_offset);

            int circle_small_r = getResources().getDimensionPixelSize(R.dimen.small_circle_r);
            int circle_small_offset = getResources().getDimensionPixelSize(R.dimen.small_circle_offset);
            int circle_small_spacing = getResources().getDimensionPixelSize(R.dimen.small_circle_spacing);

            path.reset();
            path.addCircle(0 - circle_big_offset, y, circle_big_r, Path.Direction.CW);
            path.addCircle(width + circle_big_offset, y, circle_big_r, Path.Direction.CW);
            for (int i = circle_small_offset; i < width - circle_small_offset; i += circle_small_spacing) {
                path.addCircle(i, y, circle_small_r, Path.Direction.CW);
            }
        }
    }

}
