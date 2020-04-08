package com.blts.himalaya.fragments;

import android.graphics.Rect;
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
import com.blts.himalaya.interfaces.IRecommendViewCallBack;
import com.blts.himalaya.presenters.RecommendPresenter;
import com.blts.himalaya.utils.Constants;
import com.blts.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 包名：      com.blts.himalaya.fragments
 * 文件名：      RecommendFragment
 * 创建时间：      2020/4/8 10:09 AM
 *
 */
public class RecommendFragment extends BaseFragment implements IRecommendViewCallBack {
    private static final String TAG = "RecommendFragment";
    private View mRootView;
    private RecommendListAdapter mRecommendListAdapter;
    private RecommendPresenter mRecommendPresenter;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        //view 加载完成
        mRootView = layoutInflater.inflate(R.layout.fragment_recommend,container,false);
        //RecyclerView的使用
        //1.找到控件
        RecyclerView mRecommendView = mRootView.findViewById(R.id.recommend_list);
        //2.设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecommendView.setLayoutManager(linearLayoutManager);
        mRecommendView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(),5);
                outRect.bottom = UIUtil.dip2px(view.getContext(),5);;
                outRect.left = UIUtil.dip2px(view.getContext(),5);;
                outRect.right = UIUtil.dip2px(view.getContext(),5);;
            }
        });
        //3.设置适配器
        mRecommendListAdapter = new RecommendListAdapter();
        mRecommendView.setAdapter(mRecommendListAdapter);

        //获取到逻辑层对象
        mRecommendPresenter = RecommendPresenter.getInstance();
        //先要设置通知接口的注册
        mRecommendPresenter.registerViewCallback(this);
        //获取推荐列表
        mRecommendPresenter.getRecommendList();


        //返回，显示界面
        return mRootView;
    }




    @Override
    public void onRecommendListLoaded(List<Album> result) {
        //获取到推荐内容的时候，此方法会被调用

        //把数据设置给适配器,更新ui
        mRecommendListAdapter.setData(result);
    }

    @Override
    public void onNetwordError() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //取消接口的注册，以免内存泄露
        if (mRecommendPresenter != null){
            mRecommendPresenter.unRegisterViewCallback(this);
        }
    }
}
