package com.blts.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

/*
 * 包名：      com.blts.himalaya.interfaces
 * 文件名：      IRecommendViewCallBack
 * 创建时间：      2020/4/8 5:42 PM
 *
 */
public interface IRecommendViewCallBack {
    /**
     * 获取推荐内容的结果
     */
    void onRecommendListLoaded(List<Album> result);
//    /**
//     * 加载更多
//     */
//    void onLoadMore(List<Album> result);
//    /**
//     * 上载更多
//     */
//    void onRefreshMore(List<Album> result);

    /**
     * 网络错误
     */
    void onNetwordError();

    /**
     * 数据为空
     */
    void onEmpty();

    /**
     * 正在加载
     */
    void onLoading();
}
