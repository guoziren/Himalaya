package com.blts.himalaya.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.blts.himalaya.utils.FragmentCreator;

/*
 * 包名：      com.blts.himalaya.adapters
 * 文件名：      MainContentAdapter
 * 创建时间：      2020/4/8 10:00 AM
 *
 */
public class MainContentAdapter extends FragmentPagerAdapter {

    public MainContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return FragmentCreator.getFragment(i);
    }

    @Override
    public int getCount() {
        return FragmentCreator.PAGE_COUNT;
    }
}
