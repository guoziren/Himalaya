package com.blts.himalaya;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blts.himalaya.adapters.TrackListAdapter;
import com.blts.himalaya.base.BaseActivity;
import com.blts.himalaya.base.BaseApplication;
import com.blts.himalaya.interfaces.IAlbumDetailViewCallback;
import com.blts.himalaya.presenters.AlbumDetailPresenter;
import com.blts.himalaya.utils.ImageBlur;
import com.blts.himalaya.utils.LogUtil;
import com.blts.himalaya.views.RoundRectImageView;
import com.blts.himalaya.views.UILoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/*
 * 包名：      com.blts.himalaya
 * 文件名：      DetailActivity
 * 创建时间：      2020/4/9 9:39 AM
 *
 */
public class DetailActivity extends BaseActivity implements IAlbumDetailViewCallback, UILoader.OnRetryClickListener, TrackListAdapter.ItemClickListener {
    private static final String TAG = "DetailActivity";
    private ImageView mLargeCover;
    private RoundRectImageView mSmallCover;
    private TextView mAlbumTitle;
    private TextView mAlbumAuthor;
    private AlbumDetailPresenter mAlbumDetailPresenter;
    private int mCurrentPage = 1;
    private RecyclerView mDetailList;
    private TrackListAdapter mDetailListAdapter;
    private FrameLayout mDetailListContainer;
    private UILoader mUiLoader;
    private long mCurrentId = -1;
    private ImageView mPlayControlBtn;
    private TextView mPlayControlTips;
//    private PlayerPresenter mPlayerPresenter;
    private List<Track> mCurrentTracks = null;
    private final static int DEFAULT_PLAY_INDEX = 0;
//    private TwinklingRefreshLayout mRefreshLayout;
    private String mCurrentTrackTitle;
    private TextView mSubBtn;
//    private ISubscriptionPresenter mSubscriptionPresenter;
    private Album mCurrentAlbum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        initView();
        initPresenter();
//        //设置订阅按钮的状态
//        updateSubState();
//        updatePlaySate(mPlayerPresenter.isPlaying());
//        initListener();
    }
    private void initView() {
        mDetailListContainer = this.findViewById(R.id.detail_list_container);
        //
        if (mUiLoader == null) {
            mUiLoader = new UILoader(this) {
                @Override
                protected View getSuccessView(ViewGroup container) {
                    return createSuccessView(container);
                }
            };
            mDetailListContainer.removeAllViews();
            mDetailListContainer.addView(mUiLoader);
            mUiLoader.setOnRetryClickListener(DetailActivity.this);
        }

        mLargeCover = this.findViewById(R.id.iv_large_cover);
        mSmallCover = this.findViewById(R.id.iv_small_cover);
        mAlbumTitle = this.findViewById(R.id.tv_album_title);
        mAlbumAuthor = this.findViewById(R.id.tv_album_author);
        //播放控制的图标
//        mPlayControlBtn = this.findViewById(R.id.detail_play_control);
//        mPlayControlTips = this.findViewById(R.id.play_control_tv);
//        mPlayControlTips.setSelected(true);
//        //
//        mSubBtn = this.findViewById(R.id.detail_sub_btn);
    }
    private void initPresenter() {
        //这个是专辑详情的presenter.
        mAlbumDetailPresenter = AlbumDetailPresenter.getInstance();
        mAlbumDetailPresenter.registerViewCallback(this);
        //播放器的Presenter.
//        mPlayerPresenter = PlayerPresenter.getPlayerPresenter();
//        mPlayerPresenter.registerViewCallback(this);
//        //订阅相关的presenter.
//        mSubscriptionPresenter = SubscriptionPresenter.getInstance();
//        mSubscriptionPresenter.getSubscriptionList();
//        mSubscriptionPresenter.registerViewCallback(this);
    }
    private View createSuccessView(ViewGroup container) {
        View detailListView = LayoutInflater.from(this).inflate(R.layout.item_detail_list, container, false);
        mDetailList = detailListView.findViewById(R.id.album_detail_list);
        //mRefreshLayout = detailListView.findViewById(R.id.refresh_layout);
        //RecyclerView的使用步骤
        //第一步:设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mDetailList.setLayoutManager(layoutManager);
        //第二步:设置适配器
        mDetailListAdapter = new TrackListAdapter();
        mDetailList.setAdapter(mDetailListAdapter);
        //设置item的上下间距
        mDetailList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 2);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 2);
                outRect.left = UIUtil.dip2px(view.getContext(), 2);
                outRect.right = UIUtil.dip2px(view.getContext(), 2);
            }
        });

        mDetailListAdapter.setItemClickListener(this);
//        BezierLayout headerView = new BezierLayout(this);
//        mRefreshLayout.setHeaderView(headerView);
//        mRefreshLayout.setMaxHeadHeight(140);
//        mRefreshLayout.setOverScrollBottomShow(false);
//        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
//            @Override
//            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
//                super.onRefresh(refreshLayout);
//                BaseApplication.getHandler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(DetailActivity.this, "刷新成功...", Toast.LENGTH_SHORT).show();
//                        mRefreshLayout.finishRefreshing();
//                    }
//                }, 2000);
//            }
//
//            @Override
//            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
//                super.onLoadMore(refreshLayout);
//                //去加载更多的内容
//                if (mAlbumDetailPresenter != null) {
//                    mAlbumDetailPresenter.loadMore();
//                    mIsLoaderMore = true;
//                }
//            }
//        });
        return detailListView;
    }
    @Override
    public void onDetailListLoaded(List<Track> tracks) {
//        if (mIsLoaderMore && mRefreshLayout != null) {
//            mRefreshLayout.finishLoadmore();
//            mIsLoaderMore = false;
//        }

        this.mCurrentTracks = tracks;
        //判断数据结果，根据结果控制UI显示
        if (tracks == null || tracks.size() == 0) {
            if (mUiLoader != null) {
                mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
            }
        }

        if (mUiLoader != null) {
            mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
        }
        //更新/设置UI数据
        mDetailListAdapter.setData(tracks);
    }

    @Override
    public void onNetworkError(int errorCode, String errorMsg) {
        //请求发生错误，显示网络异常状态
        mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onAlbumLoaded(Album album) {
        this.mCurrentAlbum = album;
        long id = album.getId();

        LogUtil.d(TAG, "album -- > " + id);
        mCurrentId = id;
        //获取专辑的详情内容
        if (mAlbumDetailPresenter != null) {
            mAlbumDetailPresenter.getAlbumDetail((int) id, mCurrentPage);
        }
        //拿数据，显示Loading状态
        if (mUiLoader != null) {
            mUiLoader.updateStatus(UILoader.UIStatus.LOADING);
        }
        if (mAlbumTitle != null) {
            mAlbumTitle.setText(album.getAlbumTitle());
        }

        if (mAlbumAuthor != null) {
            mAlbumAuthor.setText(album.getAnnouncer().getNickname());
        }

        //做毛玻璃效果
        if (mLargeCover != null && null != mLargeCover) {
            Picasso.get().load(album.getCoverUrlLarge()).into(mLargeCover, new Callback() {
                @Override
                public void onSuccess() {
                    Drawable drawable = mLargeCover.getDrawable();
                    if (drawable != null) {
                        //到这里才说明是有图片的
                        ImageBlur.makeBlur(mLargeCover, DetailActivity.this);
                    }
                }

                @Override
                public void onError(Exception e) {
                    LogUtil.d(TAG, "onError" + e.toString());
                }
            });

        }

        if (mSmallCover != null) {
            Picasso.get().load(album.getCoverUrlLarge()).into(mSmallCover);
        }
    }

    @Override
    public void onLoaderMoreFinished(int size) {

    }

    @Override
    public void onRefreshFinished(int size) {

    }
    @Override
    public void onRetryClick() {
        //这里面表示用户网络不佳的时候 去点击了重新加载
        if (mAlbumDetailPresenter != null) {
            mAlbumDetailPresenter.getAlbumDetail((int) mCurrentId, mCurrentPage);
        }
    }

    @Override
    public void onItemClick(List<Track> detailData, int position) {

    }
}

