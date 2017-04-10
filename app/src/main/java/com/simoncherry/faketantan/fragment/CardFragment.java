package com.simoncherry.faketantan.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.dalong.carview.CardDataItem;
import com.dalong.carview.CardSlidePanel;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.orhanobut.logger.Logger;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.activity.MainActivity;
import com.simoncherry.faketantan.activity.OtherProfileActivity;
import com.simoncherry.faketantan.event.ChangePhotoEvent;
import com.simoncherry.faketantan.utils.TestUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡片Fragment
 *
 */
@SuppressLint({"HandlerLeak", "NewApi", "InflateParams"})
public class CardFragment extends Fragment {

    private CardSlidePanel.CardSwitchListener cardSwitchListener;

//    private String imagePaths[] = {
//            "assets://wall01.jpg", "assets://wall12.jpg",
//            "assets://wall02.jpg", "assets://wall03.jpg",
//            "assets://wall04.jpg", "assets://wall05.jpg",
//            "assets://wall06.jpg", "assets://wall07.jpg",
//            "assets://wall08.jpg", "assets://wall09.jpg",
//            "assets://wall10.jpg", "assets://wall11.jpg",
//            "assets://wall12.jpg", "assets://wall01.jpg",
//            "assets://wall02.jpg", "assets://wall03.jpg",
//            "assets://wall04.jpg", "assets://wall05.jpg",
//            "assets://wall06.jpg", "assets://wall07.jpg",
//            "assets://wall08.jpg", "assets://wall09.jpg",
//            "assets://wall10.jpg", "assets://wall11.jpg"}; // 24个图片资源名称
    private String imagePaths[] = {
            "assets://lady1.jpeg", "assets://lady2.jpeg",
            "assets://lady3.jpeg", "assets://lady4.jpeg",
            "assets://paruru1.jpg", "assets://paruru2.jpg",
            "assets://paruru3.jpg", "assets://paruru4.jpg",
            "assets://test1.png", "assets://test2.png",
            "assets://test3.png", "assets://test4.png",
            "assets://xyc1.jpg", "assets://xyc2.jpg",
            "assets://xyc3.jpg", "assets://xyc4.jpg"};

//    private String names[] = {"郭富城", "刘德华", "张学友", "李连杰", "成龙", "谢霆锋", "李易峰",
//            "霍建华", "胡歌", "曾志伟", "吴孟达", "梁朝伟", "周星驰", "赵本山", "郭德纲", "周润发", "邓超",
//            "王祖蓝", "王宝强", "黄晓明", "张卫健", "徐峥", "李亚鹏", "郑伊健"}; // 24个人名
    private String names[] = {
        "Abby", "Bianca", "Christina", "Daisy", "Emily", "Fiona", "Grace", "Hazel", "Ivy", "Jessie", "Katherine", "Lauren", "Martha",
        "Nova", "Olivia", "Priscilla", "Queenie", "Rachel", "Sasha", "Tiffany", "Una", "Vivienne", "Whitney", "Xenia", "Yolanda", "Zoe"
    };

    private List<CardDataItem> dataList = new ArrayList<CardDataItem>();
    public  CardSlidePanel slidePanel;

    private int img_index = 0;
    private int img_page = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.dalong.carview.R.layout.card_layout, null);
        EventBus.getDefault().register(this);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView(View rootView) {
        slidePanel = (CardSlidePanel) rootView
                .findViewById(com.dalong.carview.R.id.image_slide_panel);
        cardSwitchListener = new CardSlidePanel.CardSwitchListener() {

            @Override
            public void onShow(View cardView,int index) {
                cardView.findViewById(R.id.card_like_icon).setAlpha(0);
                cardView.findViewById(R.id.card_dislike_icon).setAlpha(0);
                Log.d("CardFragment", "正在显示-" + dataList.get(index).userName);
            }

            @Override
            public void onCardVanish(View changedView,int index, int type) {
                img_page = 0;
                Log.d("CardFragment", "正在消失-" + dataList.get(index).userName + " 消失type=" + type);
                switch (type){
                    case 0:
                        Log.d("CardFragment", "不喜欢" );
                        setViewPressed(getActivity().findViewById(R.id.card_left_btn),200);
                        break;
                    case 1:
                        Log.d("CardFragment", "喜欢" );
                        setViewPressed(getActivity().findViewById(R.id.card_right_btn),200);
                        break;
                }
            }

            @Override
            public void onItemClick(View cardView, int index) {
                img_index = index;
                Log.d("CardFragment", "卡片点击-" + dataList.get(index).userName);

                Intent intent = new Intent(getActivity(), OtherProfileActivity.class);
                Bundle transitionBundle = ActivityTransitionLauncher.with(getActivity()).from(cardView).createBundle();
                transitionBundle.putString("img_url", dataList.get(index).imagePath);
                transitionBundle.putInt("img_index", index);
                transitionBundle.putInt("img_page", img_page);
                intent.putExtras(transitionBundle);
                startActivityForResult(intent, OtherProfileActivity.REQUEST_CODE);
                // you should prevent default activity transition animation
                getActivity().overridePendingTransition(0, 0);
            }

            @Override
            public void onViewPosition(View changedView,float dx, float dy) {
                changedView.findViewById(R.id.card_like_icon).setAlpha(dx);
                changedView.findViewById(R.id.card_dislike_icon).setAlpha(dy);
            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);
        prepareDataList();
        slidePanel.fillData(dataList);
        slidePanel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    public  void setViewPressed(final View view,long time){
        view.setPressed(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setPressed(false);
            }
        },time);

    }

    private void prepareDataList() {
        //int num = imagePaths.length;
        int num = TestUtil.Url.length/4;

       // for (int j = 0; j < 3; j++)
        {
            for (int i = 0; i < num; i++) {
                CardDataItem dataItem = new CardDataItem();
                //dataItem.userName = names[i];
                dataItem.userName = "Test";
                //dataItem.imagePath = imagePaths[i];
                dataItem.imagePath = TestUtil.Url[i*4];
                dataItem.likeNum = (int) (Math.random() * 10);
                dataItem.imageNum = (int) (Math.random() * 6);
                dataList.add(dataItem);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addIgnoredView(view.findViewById(R.id.carview1));
        addIgnoredView(view.findViewById(R.id.carview2));
        addIgnoredView(view.findViewById(R.id.carview3));
        addIgnoredView(view.findViewById(R.id.carview4));
        removeIgnoredView(view.findViewById(R.id.card_bottom_layout));
    }

    public void addIgnoredView(View view){
        MainActivity main=(MainActivity)getActivity();
        if(view!=null){
            main.addIgnoredView(slidePanel);
        }
    }
    public void removeIgnoredView(View view){
        MainActivity main=(MainActivity)getActivity();
        if(view!=null){
            main.removeIgnoredView(slidePanel);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == OtherProfileActivity.REQUEST_CODE) {
                String img_url = data.getStringExtra("url");
                img_index = data.getIntExtra("index", 0);
                img_page = data.getIntExtra("page", 0);

//                Picasso.with(mContext)
//                        .load(url)
//                        .resize(200, 200)
//                        .centerCrop()
//                        .into(cornerImageViews[which]);
                slidePanel.updateCardViewImage(img_index, img_url);
                slidePanel.invalidate();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChangePhotoEvent event) {
        String url = event.getUrl();
        Logger.t("ChangePhotoEvent").e(url);
        slidePanel.updateCardViewImage(img_index, url);
        slidePanel.invalidate();
    }
}
