package com.blts.himalaya;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.blts.himalaya.adapters.IndicatorAdapter;
import com.blts.himalaya.adapters.MainContentAdapter;
import com.blts.himalaya.utils.LogUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";
    private MagicIndicator mMagicIndicator;
    private ViewPager mViewPager;
    private IndicatorAdapter mIndicatorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();

    }

    private void initEvent() {
      mIndicatorAdapter.setOnIndicatorTabClickListener(new IndicatorAdapter.OnIndicatorTabClickListener() {
          @Override
          public void onTabClick(int index) {
              LogUtil.d(TAG, "onTabClick: ");
              mViewPager.setCurrentItem(index);
          }
      });
    }

    private void initView() {
        mMagicIndicator = findViewById(R.id.main_indicator3);
        mMagicIndicator.setBackgroundColor(this.getResources().getColor(R.color.main_color));
        //创建Indicator的适配器
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);//平分宽度
        mIndicatorAdapter = new IndicatorAdapter(this);
        commonNavigator.setAdapter(mIndicatorAdapter);


        mViewPager = this.findViewById(R.id.content_pager);
        //创建内容适配器
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainContentAdapter mainContentAdapter = new MainContentAdapter(fragmentManager);
        mViewPager.setAdapter(mainContentAdapter);


        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator,mViewPager);
    }
}
