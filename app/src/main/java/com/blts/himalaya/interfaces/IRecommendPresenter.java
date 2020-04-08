package com.blts.himalaya.interfaces;

/*
 * 包名：      com.blts.himalaya.interfaces
 * 文件名：      IRecommendPresenter
 * 创建时间：      2020/4/8 5:40 PM
 *
 */
public interface IRecommendPresenter {
    /**
     * 获取推荐内容
     */
    void getRecommendList();
    /**
     * 下拉刷新更多内容
     */
    void pullRefreshMore();
    /**
     * 上拉加载更多
     */
    void loadMore();

    /**
     * 注册UI的回调
     * @param callBack
     */
    void registerViewCallback(IRecommendViewCallBack callBack);

    /**
     * 取消注册
     * @param callBack
     */
    void unRegisterViewCallback(IRecommendViewCallBack callBack);
}
