package com.blts.himalaya.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blts.himalaya.R;
import com.blts.himalaya.base.BaseApplication;
import com.blts.himalaya.utils.LogUtil;
import com.blts.himalaya.views.SobPopWindow;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * 包名：      com.blts.himalaya.adapters
 * 文件名：      PlayListAdapter
 * 创建时间：      2020/4/10 9:22 AM
 *
 */
public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.InnerHolder> {
    private static final String TAG = "PlayListAdapter";
    private List<Track> mData = new ArrayList();
    private int playingIndex = 0;
    private SobPopWindow.PlayListItemClickListener mItemClickListener = null;
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_play_list,parent,false);


        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {

        //设置数据
        Track track = mData.get(position);
        LogUtil.e(TAG, "onBindViewHolder: " + " position = " + position + ";title = " + track.getTrackTitle() + " size = " + mData.size());

        TextView trackTitleTv = holder.itemView.findViewById(R.id.track_title_tv);

        //设置字体颜色
        trackTitleTv.setTextColor(
                BaseApplication.getAppContext().getResources().getColor(playingIndex == position ?
                        R.color.second_color : R.color.play_list_text_color));


        trackTitleTv.setText(track.getTrackTitle());

//        Log.e(TAG, "onBindViewHolder: 宽=" + trackTitleTv.getWidth() + ";高度=" + trackTitleTv.getHeight());
//        //找播放状态的图标
        View playingIconView = holder.itemView.findViewById(R.id.play_icon_iv);
        playingIconView.setVisibility(playingIndex == position ? View.VISIBLE : View.INVISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position);
                }
                //这里直接调notifyDataSetChanged(); 也会出现点击空白的问题

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Track> data) {
        //设置数据更新列表
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setCurrentPlayPosition(int position) {
        playingIndex = position;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(SobPopWindow.PlayListItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(View itemView) {
            super(itemView);
        }
    }
}
