package com.blts.himalaya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.blts.himalaya.MainActivity;
import com.blts.himalaya.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/*
 * 包名：      com.blts.himalaya.adapters
 * 文件名：      IndicatorAdapter
 * 创建时间：      2020/4/8 9:30 AM
 *
 */
public class IndicatorAdapter extends CommonNavigatorAdapter {

    private final String[] mTitle;

    public IndicatorAdapter(Context context) {
        mTitle = context.getResources().getStringArray(R.array.indicator_name);
    }

    @Override
    public int getCount() {
        if (mTitle != null){
            return mTitle.length;
        }
        return 0;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int index) {
        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
        colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#aaffffff"));
        colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
        colorTransitionPagerTitleView.setTextSize(18);
        colorTransitionPagerTitleView.setText(mTitle[index]);
        colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });
        return colorTransitionPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        linePagerIndicator.setColors(Color.parseColor("#ffffff"));
        return linePagerIndicator;
    }
}
