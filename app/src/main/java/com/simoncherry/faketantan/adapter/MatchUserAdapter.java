package com.simoncherry.faketantan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.orhanobut.logger.Logger;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.realm.MatchUser;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Simon on 2016/11/27.
 */

public class MatchUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = MatchUserAdapter.class.getSimpleName();

    private Context mContext;
    private List<MatchUser> mData;

    public interface ItemClickListener{
        void onItemClick(int position);
    }
    private ItemClickListener itemClickListener;
    public void setItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }

    public MatchUserAdapter(Context mContext, List<MatchUser> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MatchViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_match_user, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MatchViewHolder viewHolder = (MatchViewHolder) holder;
        MatchUser matchUser = mData.get(position);
        if (matchUser != null) {
            viewHolder.tvName.setText(matchUser.getName() != null ? matchUser.getName() : "");
            viewHolder.tvWord.setText(matchUser.getWord() != null ? matchUser.getWord() : "");
            String url = matchUser.getAvatarUrl();
            if (url != null && !TextUtils.isEmpty(url)) {
                Picasso.with(mContext)
                        .load(url)
                        .fit().centerCrop()
                        .placeholder(R.drawable.img_defalut).error(R.drawable.img_defalut)
                        .into(viewHolder.ivAvatar);
            }
        }
        viewHolder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    Logger.t(TAG).e("click item position: " + String.valueOf(position));
                    itemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    private class MatchViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutRoot;
        private CircularImageView ivAvatar;
        private TextView tvName;
        private TextView tvWord;

        MatchViewHolder(View itemView) {
            super(itemView);
            layoutRoot = (RelativeLayout) itemView.findViewById(R.id.layout_root);
            ivAvatar = (CircularImageView) itemView.findViewById(R.id.iv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvWord = (TextView) itemView.findViewById(R.id.tv_word);
        }
    }
}
