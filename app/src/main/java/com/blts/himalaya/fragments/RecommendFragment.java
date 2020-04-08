package com.blts.himalaya.fragments;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blts.himalaya.R;
import com.blts.himalaya.adapters.RecommendListAdapter;
import com.blts.himalaya.base.BaseFragment;
import com.blts.himalaya.utils.Constants;
import com.blts.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 包名：      com.blts.himalaya.fragments
 * 文件名：      RecommendFragment
 * 创建时间：      2020/4/8 10:09 AM
 *
 */
public class RecommendFragment extends BaseFragment {
    private static final String TAG = "RecommendFragment";
    private View mRootView;
    private RecommendListAdapter mRecommendListAdapter;
    private List<Album> mAlbumList;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        //view 加载完成
        mRootView = layoutInflater.inflate(R.layout.fragment_recommend,container,false);

        RecyclerView mRecommendView = mRootView.findViewById(R.id.recommend_list);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecommendView.setLayoutManager(linearLayoutManager);

        //设置适配器
        mRecommendListAdapter = new RecommendListAdapter();
        //取数据
        getRecommendData();

        mRecommendView.setAdapter(mRecommendListAdapter);
        //返回，显示界面
        return mRootView;
    }

    /**
     * 获取推荐内容(猜你喜欢)
     * 3.10.6 获取猜你喜欢专辑
     */
    private void getRecommendData() {
        Map<String, String> map = new HashMap<String, String>();
        //返回多少条数据
        map.put(DTransferConstants.LIKE_COUNT, Constants.RECOMMEND_COUNT + "");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(@Nullable GussLikeAlbumList gussLikeAlbumList) {
                mAlbumList = gussLikeAlbumList.getAlbumList();
                if (mAlbumList != null) {
                    LogUtil.d(TAG, "onSuccess: " + mAlbumList.size());
                    LogUtil.d(TAG, "onSuccess: " + mAlbumList);
                    //更新ui
                    updateUI();
                }
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG, "onError: "  + i);
                LogUtil.d(TAG, "onError: "  + s);
            }
        });
    }

    private void updateUI() {
        //把数据设置给适配器并更新
        mRecommendListAdapter.setData(mAlbumList);
    }
}
