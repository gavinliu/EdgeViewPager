package cn.gavinliu.edgeviewpager;

/**
 * Created by Gavin on 16-11-3.
 */

public class ScalePercentDemoActivity3 extends ScalePercentDemoActivity2 {

    @Override
    public int getContentViewId() {
        return R.layout.activity_demo_percent;
    }

    @Override
    public int getPageItemLayoutId() {
        return R.layout.item_pager_offset;
    }
}
