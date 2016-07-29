package com.feicui.zh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feicui.zh.fagment.HotListFragment;

/**
 * Created by Administrator on 2016/7/27.
 * 适配器，用FragmentPagerAdapter比用PagerAdapter更方便
 */
public class HotAdapter extends FragmentPagerAdapter {
    public HotAdapter(FragmentManager fm) {
        super(fm);
    }
/**碎片*/
    @Override
    public Fragment getItem(int position) {
        return new HotListFragment();
    }
/**碎片个数*/
    @Override
    public int getCount() {
        return 10;
    }
/**tablayout标题*/
    @Override
    public CharSequence getPageTitle(int position) {
        return "Java"+position;
    }
}
