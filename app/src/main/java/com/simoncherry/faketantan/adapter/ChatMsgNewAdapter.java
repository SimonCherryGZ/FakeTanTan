package com.simoncherry.faketantan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.baoyz.treasure.Treasure;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.orhanobut.logger.Logger;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.bean.PhotoItem;
import com.simoncherry.faketantan.custom.DynamicListView;
import com.simoncherry.faketantan.realm.ChatMessage;
import com.simoncherry.faketantan.realm.MatchUser;
import com.simoncherry.faketantan.realm.MatchUserManager;
import com.simoncherry.faketantan.realm.Tutorial;
import com.simoncherry.faketantan.sp.UserData;
import com.simoncherry.faketantan.utils.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.github.rockerhieu.emojicon.EmojiconTextView;

/**
 * Created by Simon on 2016/11/6.
 */

public class ChatMsgNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = ChatMsgNewAdapter.class.getSimpleName();
    private final static int TYPE_SEND = 0;
    private final static int TYPE_RECEIVE = 1;

    private Context mContext;
    private List<ChatMessage> mData;
    private TutorialNewAdapter.TutorialBtnListener btnListener;

    public interface TutorialItemListener {
        void onClick(int index, String tutorial);
    }

    private TutorialItemListener tutorialItemListener;

    public void setTutorialListener(TutorialItemListener listener) {
        tutorialItemListener = listener;
    }

    public ChatMsgNewAdapter(Context mContext, List<ChatMessage> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_SEND) {
            return new SendViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_test_chat_to, parent, false));
        } else {
            return new ReceiveViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_test_chat_from, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_SEND) {
            SendViewHolder sendViewHolder = (SendViewHolder) holder;
            sendViewHolder.tvContent.setText(mData.get(position).getContent());
            String path = getAvatar();
            if (path != null) {
                if (URLUtil.isNetworkUrl(path)) {
                    Picasso.with(mContext)
                            .load(path)
                            .fit().centerInside()
                            .placeholder(R.drawable.img_defalut).error(R.drawable.img_defalut)
                            .into(sendViewHolder.ivAvatar);
                } else if (new File(path).exists()) {
                    Picasso.with(mContext)
                            .load(new File(path))
                            .fit().centerInside()
                            .placeholder(R.drawable.img_defalut).error(R.drawable.img_defalut)
                            .into(sendViewHolder.ivAvatar);
                } else {
                    Picasso.with(mContext)
                            .load(R.drawable.img_defalut)
                            .fit().centerInside()
                            .into(sendViewHolder.ivAvatar);
                }
            }

        } else {
            final ReceiveViewHolder receiveViewHolder = (ReceiveViewHolder) holder;
            ChatMessage chatMsgBean = mData.get(position);
            String content = chatMsgBean.getContent();
            if (content.contains("http") && content.contains(".jpg") && content.startsWith("http") && content.endsWith(".jpg")) {
                content = content.replace("\\", "");
                Logger.t(TAG).e(content);
                Picasso.with(mContext)
                        .load(content)
                        .fit().centerCrop()
                        .transform(new RoundedTransformation(20, 0))
                        .placeholder(R.drawable.img_defalut).error(R.drawable.img_defalut)
                        .into(receiveViewHolder.ivImg);
                receiveViewHolder.ivImg.setVisibility(View.VISIBLE);
                receiveViewHolder.tvContent.setVisibility(View.GONE);
            } else {
                receiveViewHolder.tvContent.setText(content);
                receiveViewHolder.tvContent.setVisibility(View.VISIBLE);
                receiveViewHolder.ivImg.setVisibility(View.GONE);
            }

            MatchUser user = MatchUserManager.getInstance().retrieveMatchUserByIdentify(chatMsgBean.getConversation());
            if (user != null) {
                String avatarUrl = user.getAvatarUrl();
                if (avatarUrl != null && !TextUtils.isEmpty(avatarUrl)) {
                    Picasso.with(mContext)
                            .load(avatarUrl)
                            .fit().centerCrop()
                            .placeholder(R.drawable.img_defalut).error(R.drawable.img_defalut)
                            .into(receiveViewHolder.ivAvatar);
                }
            }

            receiveViewHolder.tutorialData = new ArrayList<>();
            receiveViewHolder.tutorialAdapter = new TutorialNewAdapter(mContext, receiveViewHolder.tutorialData);
            receiveViewHolder.lvTutorial.setAdapter(receiveViewHolder.tutorialAdapter);

            btnListener = new TutorialNewAdapter.TutorialBtnListener() {
                @Override
                public void onCLick(int index) {
                    if (receiveViewHolder.tutorialData != null && receiveViewHolder.tutorialData.size() > (index%10-1)) {
                        Tutorial bean = receiveViewHolder.tutorialData.get(index%10-1);
                        tutorialItemListener.onClick(index, bean.getContent());
                    }
                }
            };
            receiveViewHolder.tutorialAdapter.setTutorialBtnListener(btnListener);

            List<Tutorial> tutorialList = chatMsgBean.getTutorialList();
            if (tutorialList == null || tutorialList.size() == 0) {
                Logger.t(TAG).e("getTutorialList: null");
                receiveViewHolder.lvTutorial.setVisibility(View.GONE);
            } else {
                receiveViewHolder.lvTutorial.setVisibility(View.VISIBLE);
                if (receiveViewHolder.tutorialData != null) {
                    if (receiveViewHolder.tutorialData.size() > 0) {
                        receiveViewHolder.tutorialData.clear();
                    }
                    receiveViewHolder.tutorialData.addAll(tutorialList);
                    receiveViewHolder.tutorialAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).isSend()) {
            return TYPE_SEND;
        } else {
            return TYPE_RECEIVE;
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    private String getAvatar() {
        UserData userData = Treasure.get(mContext, UserData.class);
        List<PhotoItem> photoItemList = userData.getPhotoItem();
        if (photoItemList != null && photoItemList.size() > 0) {
            return photoItemList.get(0).hyperlink;
        } else {
            return null;
        }
    }

    private class SendViewHolder extends RecyclerView.ViewHolder {

        EmojiconTextView tvContent;
        CircularImageView ivAvatar;

        SendViewHolder(View view) {
            super(view);
            tvContent = (EmojiconTextView) view.findViewById(R.id.tv_content);
            ivAvatar = (CircularImageView) view.findViewById(R.id.iv_avatar);
        }
    }

    private class ReceiveViewHolder extends RecyclerView.ViewHolder {

        EmojiconTextView tvContent;
        ImageView ivImg;
        CircularImageView ivAvatar;
        DynamicListView lvTutorial;
        TutorialNewAdapter tutorialAdapter;
        List<Tutorial> tutorialData;

        ReceiveViewHolder(View view) {
            super(view);
            tvContent = (EmojiconTextView) view.findViewById(R.id.tv_content);
            ivImg = (ImageView) view.findViewById(R.id.iv_img);
            ivAvatar = (CircularImageView) view.findViewById(R.id.iv_avatar);
            lvTutorial = (DynamicListView) view.findViewById(R.id.lv_tutorial);
        }
    }
}
