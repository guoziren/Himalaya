package com.blts.himalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blts.himalaya.R;
import com.blts.himalaya.base.BaseFragment;

/*
 * 包名：      com.blts.himalaya.fragments
 * 文件名：      HistoryFragment
 * 创建时间：      2020/4/8 10:10 AM
 *
 */
public class HistoryFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView = layoutInflater.inflate(R.layout.fragment_history,container,false);
        return rootView;
    }
}
