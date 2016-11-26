package com.simoncherry.faketantan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import com.baoyz.treasure.Treasure;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.bean.PhotoItem;
import com.simoncherry.faketantan.bean.TutorialBean;
import com.simoncherry.faketantan.custom.DynamicListView;
import com.simoncherry.faketantan.realm.ChatMessage;
import com.simoncherry.faketantan.sp.UserData;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.github.rockerhieu.emojicon.EmojiconTextView;

/**
 * Created by Simon on 2016/11/6.
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_SEND = 0;
    private final static int TYPE_RECEIVE = 1;

    private Context mContext;
    private List<ChatMessage> mData;
    private TutorialAdapter.TutorialBtnListener btnListener;

    public interface TutorialItemListener {
        void onClick(int index, String tutorial);
    }

    private TutorialItemListener tutorialItemListener;

    public void setTutorialListener(TutorialItemListener listener) {
        tutorialItemListener = listener;
    }

    public ChatMessageAdapter(Context mContext, List<ChatMessage> mData) {
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

            receiveViewHolder.tvContent.setText(chatMsgBean.getContent());

            receiveViewHolder.tutorialData = new ArrayList<>();
            receiveViewHolder.tutorialAdapter = new TutorialAdapter(mContext, receiveViewHolder.tutorialData);
            receiveViewHolder.lvTutorial.setAdapter(receiveViewHolder.tutorialAdapter);

            btnListener = new TutorialAdapter.TutorialBtnListener() {
                @Override
                public void onCLick(int index) {
                    if (receiveViewHolder.tutorialData != null && receiveViewHolder.tutorialData.size() > (index%10-1)) {
                        TutorialBean bean = receiveViewHolder.tutorialData.get(index%10-1);
                        tutorialItemListener.onClick(index, bean.getContent());
                    }
                }
            };
            receiveViewHolder.tutorialAdapter.setTutorialBtnListener(btnListener);

            //setMockTutorial(receiveViewHolder.tutorialData, receiveViewHolder.tutorialAdapter);
//            List<TutorialBean> tutorialBeanList = chatMsgBean.getTutorialList();
//            if (tutorialBeanList == null || tutorialBeanList.size() == 0) {
//                receiveViewHolder.lvTutorial.setVisibility(View.GONE);
//            } else {
//                receiveViewHolder.lvTutorial.setVisibility(View.VISIBLE);
//                if (receiveViewHolder.tutorialData != null) {
//                    if (receiveViewHolder.tutorialData.size() > 0) {
//                        receiveViewHolder.tutorialData.clear();
//                    }
//                    receiveViewHolder.tutorialData.addAll(tutorialBeanList);
//                    receiveViewHolder.tutorialAdapter.notifyDataSetChanged();
//                }
//            }
            receiveViewHolder.lvTutorial.setVisibility(View.GONE);
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

    private void setMockTutorial(List<TutorialBean> data, TutorialAdapter adapter) {
        for (int i=0; i<6; i++) {
            TutorialBean bean = new TutorialBean();
            bean.setId(i);
            bean.setContent(String.valueOf(i+1) + ".啦啦啦啦啦啦啦啦啦");
            data.add(bean);
        }
        adapter.notifyDataSetChanged();
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
        CircularImageView ivAvatar;
        DynamicListView lvTutorial;
        TutorialAdapter tutorialAdapter;
        List<TutorialBean> tutorialData;

        ReceiveViewHolder(View view) {
            super(view);
            tvContent = (EmojiconTextView) view.findViewById(R.id.tv_content);
            ivAvatar = (CircularImageView) view.findViewById(R.id.iv_avatar);
            lvTutorial = (DynamicListView) view.findViewById(R.id.lv_tutorial);
        }
    }
}
