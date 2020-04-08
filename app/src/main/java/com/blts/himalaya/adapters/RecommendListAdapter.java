package com.blts.himalaya.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blts.himalaya.R;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

/*
 * 包名：      com.blts.himalaya.adapters
 * 文件名：      RecommendListAdapter
 * 创建时间：      2020/4/8 2:11 PM
 *
 */
public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.InnerHolder> {
    private List<Album> mData = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //创建ITEM  要传viewGroup（他老爸）,测量要参照他老爸
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend,viewGroup,false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder innerHolder, int position) {
        //设置数据
        innerHolder.itemView.setTag(position);
        innerHolder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        if (mData != null){
            return mData.size();
        }
        return 0;
    }

    public void setData(List<Album> albumList) {
        if (mData != null){
            mData.clear();
            mData.addAll(albumList);
        }
        //更新UI
        notifyDataSetChanged();

    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(Album album) {
            //找到控件，设置数据
            //封面

            ImageView albumCover = itemView.findViewById(R.id.album_imv);

            //描述
            TextView albumDesTv = itemView.findViewById(R.id.album_description);
            albumDesTv.setText(album.getAlbumIntro());

            //titoel
            TextView albumTitleTv = itemView.findViewById(R.id.album_title);
            albumTitleTv.setText(album.getAlbumTitle());

            //播放数量
            TextView albumPlayCountTv = itemView.findViewById(R.id.album_play_count);
            albumPlayCountTv.setText(album.getPlayCount()+"");

            //集数
            TextView albumJiCountTv = itemView.findViewById(R.id.album_ji_count);
            albumJiCountTv.setText(album.getIncludeTrackCount()+"");

            Picasso.get().load(album.getCoverUrlLarge()).into(albumCover);
        }
    }
}
