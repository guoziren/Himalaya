package com.blts.himalaya.presenters;

import android.support.annotation.Nullable;

import com.blts.himalaya.interfaces.IRecommendPresenter;
import com.blts.himalaya.interfaces.IRecommendViewCallBack;
import com.blts.himalaya.utils.Constants;
import com.blts.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 包名：      com.blts.himalaya.presenters
 * 文件名：      RecommendPresenter
 * 创建时间：      2020/4/8 5:44 PM
 *
 */
public class RecommendPresenter implements IRecommendPresenter {
    private static final String TAG = "RecommendPresenter";
    private List<IRecommendViewCallBack> mCallBacks = new ArrayList<>();
    private RecommendPresenter(){}

    private static RecommendPresenter sInstance = null;
    //懒汉式单例
    public static RecommendPresenter getInstance(){
        if (sInstance == null){
            synchronized (RecommendPresenter.class){
                if (sInstance == null){
                    sInstance = new RecommendPresenter();
                }
            }
        }
        return sInstance;
    }
    /**
     * 获取推荐内容(猜你喜欢)
     * 3.10.6 获取猜你喜欢专辑
     */
    @Override
    public void getRecommendList() {
        updateLoading();
        Map<String, String> map = new HashMap<String, String>();
        //返回多少条数据
        map.put(DTransferConstants.LIKE_COUNT, Constants.RECOMMEND_COUNT + "");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(@Nullable GussLikeAlbumList gussLikeAlbumList) {
                List<Album> albumList = gussLikeAlbumList.getAlbumList();
                if (albumList != null) {
                    LogUtil.d(TAG, "onSuccess: " + albumList.size());
                    //更新ui
                   // updateUI();
                    handleRecommendResult(albumList);
                }
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG, "onError: "  + i);
                LogUtil.d(TAG, "onError: "  + s);
                handleError();
            }
        });
    }

    private void handleError() {
        if (mCallBacks != null){
            for (IRecommendViewCallBack callBack : mCallBacks) {
                callBack.onNetwordError();
            }
        }
    }

    private void handleRecommendResult(List<Album> albumList) {
        //通知ui更新
        if (albumList != null){

            if (albumList.size() == 0){
                for (IRecommendViewCallBack callBack : mCallBacks) {
                    callBack.onEmpty();
                }
            }else{
                for (IRecommendViewCallBack callBack : mCallBacks) {
                    callBack.onRecommendListLoaded(albumList);
                }
            }
        }

    }
    private void updateLoading(){
        for (IRecommendViewCallBack callBack : mCallBacks) {
            callBack.onLoading();
        }
    }

    @Override
    public void pullRefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallback(IRecommendViewCallBack callBack) {
        if (mCallBacks != null && !mCallBacks.contains(callBack)){
            mCallBacks.add(callBack);
        }
    }

    @Override
    public void unRegisterViewCallback(IRecommendViewCallBack callBack) {
        if (mCallBacks != null){
            mCallBacks.remove(callBack);
        }
    }
}
