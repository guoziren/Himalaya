package com.blts.himalaya.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 * 包名：      com.blts.himalaya.base
 * 文件名：      BaseFragment
 * 创建时间：      2020/4/8 10:09 AM
 *
 */
public abstract class BaseFragment extends Fragment {

    private View mRootView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = onSubViewLoaded(inflater,container);
        return mRootView;
    }


    protected abstract View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container);
}
