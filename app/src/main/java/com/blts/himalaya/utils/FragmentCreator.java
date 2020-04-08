package com.blts.himalaya.utils;

import com.blts.himalaya.base.BaseFragment;
import com.blts.himalaya.fragments.HistoryFragment;
import com.blts.himalaya.fragments.RecommendFragment;
import com.blts.himalaya.fragments.SubscriptionFragment;

import java.util.HashMap;
import java.util.Map;

/*
 * 包名：      com.blts.himalaya.utils
 * 文件名：      FragmentCreator
 * 创建时间：      2020/4/8 10:27 AM
 *
 */
public class FragmentCreator {

    public static final int INDEX_RECOMMENT = 0 ;
    public static final int INDEX_HISTORY = 1 ;
    public static final int INDEX_SUBSCRIPTION = 2 ;

    public static final int PAGE_COUNT = 3;
    private static Map<Integer,BaseFragment> sCache = new HashMap<>();

    public static BaseFragment getFragment(int index){
        BaseFragment baseFragment = sCache.get(index);
        if (baseFragment != null){
            return baseFragment;
        }
        switch (index){
            case INDEX_RECOMMENT:
                baseFragment = new RecommendFragment();
                break;
            case INDEX_HISTORY:
                baseFragment = new HistoryFragment();
                break;
            case INDEX_SUBSCRIPTION:
                baseFragment = new SubscriptionFragment();
                break;
        }
        sCache.put(index,baseFragment);
        return baseFragment;
    }
}
