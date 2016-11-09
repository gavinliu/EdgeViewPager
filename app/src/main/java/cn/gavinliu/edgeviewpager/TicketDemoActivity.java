package cn.gavinliu.edgeviewpager;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import cn.gavinliu.android.lib.edgeviewpager.EdgeViewPager;
import cn.gavinliu.android.lib.glide.stackblur.GlideStackBlur;
import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * Created by Gavin on 16-11-3.
 */

public class TicketDemoActivity extends AppCompatActivity {

    String[][] movies = {
            {"Zootopia", "https://img1.doubanio.com/view/photo/photo/public/p2315672647.jpg"},
            {"Doctor Strange", "https://img3.doubanio.com/view/photo/photo/public/p2333351484.jpg"},
            {"Jason Bourne", "https://img3.doubanio.com/view/photo/photo/public/p2379032162.jpg"},
            {"The Martian", "https://img3.doubanio.com/view/photo/photo/public/p2280097442.jpg"},
            {"Swiss Army Man", "https://img3.doubanio.com/view/photo/photo/public/p2328680655.jpg"},
            {"Sausage Party", "https://img1.doubanio.com/view/photo/photo/public/p2371145119.jpg"},
            {"Now You See Me 2","https://img3.doubanio.com/view/photo/photo/public/p2358173775.jpg"}
    };

    Drawable placeHolder;

    View container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        container = findViewById(R.id.container);
        EdgeViewPager mViewPager = (EdgeViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new MyAdapter());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String url = movies[position][1];
                ImageLoader.loadBlurBg(TicketDemoActivity.this, container, url);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(1);
        ImageLoader.loadBlurBg(TicketDemoActivity.this, container, movies[1][1]);

        placeHolder = new ColorDrawable(0xFFF2F2F2);

        container.setPadding(0, container.getPaddingTop(), 0, container.getPaddingBottom() + getNavigationBarHeight());
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_pager_ticket, null);
            view.setTag(position);
            container.addView(view);

            TextView textView = (TextView) view.findViewById(R.id.textView);
            ShapedImageView cover = (ShapedImageView) view.findViewById(R.id.cover);
            cover.setExtension(new TicketPathExtension(false));
            ShapedImageView ticketBg = (ShapedImageView) view.findViewById(R.id.ticket_bg);
            ticketBg.setExtension(new TicketPathExtension(true));


            String url = movies[position][1];
            String name = movies[position][0];

            textView.setText(name);

            Glide.with(TicketDemoActivity.this)
                    .load(url)
                    .placeholder(placeHolder)
                    .centerCrop()
                    .into(cover);


            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return movies.length;
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

    static class ImageLoader {

        public static void loadBlurBg(Activity activity, final View view, String url) {
            Glide.with(activity)
                    .load(url)
                    .placeholder(view.getBackground())
                    .override(1080 / 5, 1920 / 5)
                    .transform(new CenterCrop(activity), new GlideStackBlur(activity))
                    .into(new Target<GlideDrawable>() {
                        @Override
                        public void onLoadStarted(Drawable placeholder) {

                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            view.setBackgroundDrawable(resource);
                        }

                        @Override
                        public void onLoadCleared(Drawable placeholder) {

                        }

                        @Override
                        public void getSize(SizeReadyCallback cb) {

                        }

                        @Override
                        public void setRequest(Request request) {

                        }

                        @Override
                        public Request getRequest() {
                            return null;
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onStop() {

                        }

                        @Override
                        public void onDestroy() {

                        }
                    });

        }
    }

    private boolean checkDeviceHasNavigationBar() {
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        return !hasMenuKey && !hasBackKey;
    }

    public int getNavigationBarHeight() {
        if (!checkDeviceHasNavigationBar()) {
            return 0;
        }

        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

}
