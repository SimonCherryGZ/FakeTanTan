package com.simoncherry.faketantan.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.treasure.Treasure;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.RecyclingPagerAdapter;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.activity.EditProfileActivity;
import com.simoncherry.faketantan.bean.PhotoItem;
import com.simoncherry.faketantan.custom.ObservableScrollView;
import com.simoncherry.faketantan.custom.ScrollViewListener;
import com.simoncherry.faketantan.event.RefreshProfileEvent;
import com.simoncherry.faketantan.sp.JsonConverterFactory;
import com.simoncherry.faketantan.sp.UserData;
import com.simoncherry.faketantan.utils.DateUtil;
import com.simoncherry.faketantan.utils.TestUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.lujun.androidtagview.TagContainerLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment implements ScrollViewListener {

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.banner_viewPager)
    ViewPager viewPager;
    @BindView(R.id.banner_indicator)
    FixedIndicatorView indicator;
    @BindView(R.id.layout_gallery)
    FrameLayout layoutGallery;
    @BindView(R.id.view_blank)
    View viewBlank;
    @BindView(R.id.tv_nickname)
    TextView tvNickName;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.layout_age)
    LinearLayout layoutAge;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_star)
    TextView tvStar;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.layout_user_base)
    RelativeLayout layoutUserBase;
    @BindView(R.id.iv_heart)
    ImageView ivHeart;
    @BindView(R.id.tv_like_count)
    TextView tvLikeCount;
    @BindView(R.id.tv_flaunt)
    TextView tvFlaunt;
    @BindView(R.id.layout_like_count)
    RelativeLayout layoutLikeCount;
    @BindView(R.id.tv_my_info)
    TextView tvMyInfo;
    @BindView(R.id.iv_industry)
    ImageView ivIndustry;
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.layout_industry)
    RelativeLayout layoutIndustry;
    @BindView(R.id.iv_job)
    ImageView ivJob;
    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.layout_job)
    RelativeLayout layoutJob;
    @BindView(R.id.iv_company)
    ImageView ivCompany;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.layout_company)
    RelativeLayout layoutCompany;
    @BindView(R.id.iv_from)
    ImageView ivFrom;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.layout_from)
    RelativeLayout layoutFrom;
    @BindView(R.id.iv_marker)
    ImageView ivMarker;
    @BindView(R.id.tv_marker)
    TextView tvMarker;
    @BindView(R.id.layout_marker)
    RelativeLayout layoutMarker;
    @BindView(R.id.iv_sign)
    ImageView ivSign;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.layout_sign)
    RelativeLayout layoutSign;
    @BindView(R.id.layout_my_info)
    RelativeLayout layoutMyInfo;
    @BindView(R.id.tv_my_tag)
    TextView tvMyTag;
    @BindView(R.id.iv_tag)
    TextView ivTag;
    @BindView(R.id.layout_tag_sign)
    TagContainerLayout layoutTagSign;
    @BindView(R.id.layout_my_tag)
    RelativeLayout layoutMyTag;
    @BindView(R.id.tv_my_interest)
    TextView tvMyInterest;
    @BindView(R.id.iv_sport)
    ImageView ivSport;
    @BindView(R.id.layout_tag_sport)
    TagContainerLayout layoutTagSport;
    @BindView(R.id.layout_sport)
    RelativeLayout layoutSport;
    @BindView(R.id.iv_music)
    ImageView ivMusic;
    @BindView(R.id.layout_tag_music)
    TagContainerLayout layoutTagMusic;
    @BindView(R.id.layout_music)
    RelativeLayout layoutMusic;
    @BindView(R.id.iv_food)
    ImageView ivFood;
    @BindView(R.id.layout_tag_food)
    TagContainerLayout layoutTagFood;
    @BindView(R.id.layout_food)
    RelativeLayout layoutFood;
    @BindView(R.id.iv_movie)
    ImageView ivMovie;
    @BindView(R.id.layout_tag_movie)
    TagContainerLayout layoutTagMovie;
    @BindView(R.id.layout_movie)
    RelativeLayout layoutMovie;
    @BindView(R.id.iv_book)
    ImageView ivBook;
    @BindView(R.id.layout_tag_book)
    TagContainerLayout layoutTagBook;
    @BindView(R.id.layout_book)
    RelativeLayout layoutBook;
    @BindView(R.id.iv_travel)
    ImageView ivTravel;
    @BindView(R.id.layout_tag_travel)
    TagContainerLayout layoutTagTravel;
    @BindView(R.id.layout_travel)
    RelativeLayout layoutTravel;
    @BindView(R.id.layout_my_interest)
    RelativeLayout layoutMyInterest;
    @BindView(R.id.layout_profile)
    RelativeLayout layoutProfile;
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.activity_user_profile)
    RelativeLayout activityUserProfile;


    private Context mContext;
    private Unbinder unbinder;
    private IndicatorViewPager indicatorViewPager;
    private List<String> urlList;
    private float touchX = 0;
    private float touchY = 0;


    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initMockData();
        initGallery();
    }

    //    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            initMockData();
//        }
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initMockData();
            initGallery();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshProfileEvent event) {
        initMockData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();

        initView();
        initMockData();
        initGallery();
    }

    private void initView() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int screenWidth = dm.widthPixels;

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutGallery.getLayoutParams();
        lp.width = screenWidth;
        lp.height = screenWidth;
        layoutGallery.setLayoutParams(lp);
        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) viewBlank.getLayoutParams();
        lp2.width = screenWidth;
        lp2.height = screenWidth;
        viewBlank.setLayoutParams(lp2);

        viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(adapter);

        scrollView.setScrollViewListener(this);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    touchX = event.getX();
                    touchY = event.getY();
                } else if (action == MotionEvent.ACTION_UP) {
                    if (!isContain(layoutProfile, event.getRawX(), event.getRawY())) {
                        if (Math.abs(event.getX() - touchX) < 10 && Math.abs(event.getY()) - touchY < 10) {
                            //onBackPressed();
                            Intent intent = new Intent(mContext, EditProfileActivity.class);
                            startActivity(intent);
                        }
                    }
                }

                scrollView.onTouchEvent(event);
                if (!isContain(layoutProfile, event.getRawX(), event.getRawY())) {  // 精华：判断触摸的坐标不在layoutProfile范围内，则将MotionEvent赋值给ViewPager
                    viewPager.onTouchEvent(event);
                }
                return true;
            }
        });
    }

    private void initMockData() {
        Treasure.setConverterFactory(new JsonConverterFactory());
        UserData userData = Treasure.get(mContext, UserData.class);

        tvNickName.setText(userData.getNickName() != null ? userData.getNickName() : "null");
        String birthday = userData.getBirthday();
        if (birthday != null && !TextUtils.isEmpty(birthday)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                Date date = sdf.parse(birthday);
                tvAge.setText(String.valueOf(DateUtil.getAgeByBirthday(date)));
                tvStar.setText(DateUtil.getConstellation(date));
            } catch (Exception e) {
                tvAge.setText("fuck");
                tvStar.setText("fuck");
            }
        } else {
            tvAge.setText("0");
            tvStar.setText("null");
        }
        String sex = userData.getSex();
        if (sex != null) {
            if (sex.equals("男")) {
                ivSex.setImageResource(R.drawable.ic_male);
                layoutAge.setBackgroundResource(R.drawable.bg_tv_age_male);
            } else if (sex.equals("女")) {
                ivSex.setImageResource(R.drawable.ic_female);
                layoutAge.setBackgroundResource(R.drawable.bg_tv_age_female);
            } else {
                ivSex.setImageResource(0);
                layoutAge.setBackgroundResource(R.drawable.bg_tv_age_male);
            }
        } else {
            ivSex.setImageResource(0);
            layoutAge.setBackgroundResource(R.drawable.bg_tv_age_male);
        }
        tvStatus.setText(TestUtil.status);
        tvLikeCount.setText(String.valueOf(userData.getLikeCount()));

        String str = userData.getIndustry();
        if (str != null && !TextUtils.isEmpty(str)) {
            tvIndustry.setText(str);
            layoutIndustry.setVisibility(View.VISIBLE);
        } else {
            layoutIndustry.setVisibility(View.GONE);
        }

        str = userData.getJob();
        if (str != null && !TextUtils.isEmpty(str)) {
            tvJob.setText(str);
            layoutJob.setVisibility(View.VISIBLE);
        } else {
            layoutJob.setVisibility(View.GONE);
        }

        str = userData.getCompany();
        if (str != null && !TextUtils.isEmpty(str)) {
            tvCompany.setText(str);
            layoutCompany.setVisibility(View.VISIBLE);
        } else {
            layoutCompany.setVisibility(View.GONE);
        }

        str = userData.getFrom();
        if (str != null && !TextUtils.isEmpty(str)) {
            tvFrom.setText(str);
            layoutFrom.setVisibility(View.VISIBLE);
        } else {
            layoutFrom.setVisibility(View.GONE);
        }

        str = userData.getMarker();
        if (str != null && !TextUtils.isEmpty(str)) {
            tvMarker.setText(str);
            layoutMarker.setVisibility(View.VISIBLE);
        } else {
            layoutMarker.setVisibility(View.GONE);
        }

        str = userData.getSign();
        if (str != null && !TextUtils.isEmpty(str)) {
            tvSign.setText(str);
            layoutSign.setVisibility(View.VISIBLE);
        } else {
            layoutSign.setVisibility(View.GONE);
        }

        List<String> tags = userData.getTagSign();
        if (tags != null && tags.size() > 0) {
            layoutMyTag.setVisibility(View.VISIBLE);
            layoutTagSign.setTags(tags);
            tags.clear();
        } else {
            layoutMyTag.setVisibility(View.GONE);
        }

        tags = userData.getTagSport();
        if (tags != null && tags.size() > 0) {
            layoutSport.setVisibility(View.VISIBLE);
            layoutTagSport.setTags(tags);
            tags.clear();
        } else {
            layoutSport.setVisibility(View.GONE);
        }

        tags = userData.getTagMusic();
        if (tags != null && tags.size() > 0) {
            layoutMusic.setVisibility(View.VISIBLE);
            layoutTagMusic.setTags(tags);
            tags.clear();
        } else {
            layoutMusic.setVisibility(View.GONE);
        }

        tags = userData.getTagFood();
        if (tags != null && tags.size() > 0) {
            layoutFood.setVisibility(View.VISIBLE);
            layoutTagFood.setTags(tags);
            tags.clear();
        } else {
            layoutFood.setVisibility(View.GONE);
        }

        tags = userData.getTagMovie();
        if (tags != null && tags.size() > 0) {
            layoutMovie.setVisibility(View.VISIBLE);
            layoutTagMovie.setTags(tags);
            tags.clear();
        } else {
            layoutMovie.setVisibility(View.GONE);
        }

        tags = userData.getTagBook();
        if (tags != null && tags.size() > 0) {
            layoutBook.setVisibility(View.VISIBLE);
            layoutTagBook.setTags(tags);
            tags.clear();
        } else {
            layoutBook.setVisibility(View.GONE);
        }

        tags = userData.getTagTravel();
        if (tags != null && tags.size() > 0) {
            layoutTravel.setVisibility(View.VISIBLE);
            layoutTagTravel.setTags(tags);
            tags.clear();
        } else {
            layoutTravel.setVisibility(View.GONE);
        }

//        List<String> my_tags = new ArrayList<>();
//        Collections.addAll(my_tags, TestUtil.tagSign);
//        layoutTagSign.setTags(my_tags);
//        my_tags.clear();
//        Collections.addAll(my_tags, TestUtil.tagSport);
//        layoutTagSport.setTags(my_tags);
//        my_tags.clear();
//        Collections.addAll(my_tags, TestUtil.tagMusic);
//        layoutTagMusic.setTags(my_tags);
//        my_tags.clear();
//        Collections.addAll(my_tags, TestUtil.tagFood);
//        layoutTagFood.setTags(my_tags);
//        my_tags.clear();
//        Collections.addAll(my_tags, TestUtil.tagMovie);
//        layoutTagMovie.setTags(my_tags);
//        my_tags.clear();
//        Collections.addAll(my_tags, TestUtil.tagBook);
//        layoutTagBook.setTags(my_tags);
//        my_tags.clear();
//        Collections.addAll(my_tags, TestUtil.tagTravel);
//        layoutTagTravel.setTags(my_tags);

//        userData.setNickName("老哥");
//        userData.setIndustry("钱少加班多的行业");
//        userData.setJob(null);
//        userData.setCompany("某小公司");
//        userData.setFrom(null);
//        userData.setMarker("自宅");
//        userData.setSign("Read Fucking Source Code");

//        my_tags.clear();
//        Collections.addAll(my_tags, TestUtil.tagSignTest);
//        userData.setTagSign(null);
//
//        my_tags.clear();
//        Collections.addAll(my_tags, TestUtil.tagSport);
//        userData.setTagSport(my_tags);
//
//        my_tags.clear();
//        Collections.addAll(my_tags, TestUtil.tagMusic);
//        userData.setTagMusic(null);
//
//        my_tags.clear();
//        Collections.addAll(my_tags, TestUtil.tagFood);
//        userData.setTagFood(my_tags);
//
//        my_tags.clear();
//        Collections.addAll(my_tags, TestUtil.tagMovie);
//        userData.setTagMovie(null);
//
//        my_tags.clear();
//        Collections.addAll(my_tags, TestUtil.tagBook);
//        userData.setTagBook(my_tags);
//
//        my_tags.clear();
//        Collections.addAll(my_tags, TestUtil.tagTravel);
//        userData.setTagTravel(null);
    }

    private void initGallery() {
        urlList = new ArrayList<>();
//        urlList.add(TestUtil.Url[0]);
//        urlList.add(TestUtil.Url[1]);
        UserData userData = Treasure.get(mContext, UserData.class);
        List<PhotoItem> photoItemList = userData.getPhotoItem();
        if (photoItemList != null && photoItemList.size() > 0) {
            for (PhotoItem item : photoItemList) {
                urlList.add(item.hyperlink);
            }
        } else {
            urlList.add(TestUtil.Url[0]);
        }
        adapter.notifyDataSetChanged();
    }

    private boolean isContain(View view, float x, float y) {
        int[] point = new int[2];
        view.getLocationOnScreen(point);
        if (x >= point[0] && x <= (point[0] + view.getWidth()) && y >= point[1] && y <= (point[1] + view.getHeight())) {
            return true;
        }
        return false;
    }

    private IndicatorViewPager.IndicatorViewPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {
        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.tab_welcome, container, false);
                ViewGroup.LayoutParams lp;
                lp = convertView.getLayoutParams();
                lp.width = 24;
                lp.height = 24;
                convertView.setLayoutParams(lp);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new ImageView(getActivity());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            ImageView imageView = (ImageView) convertView;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (urlList != null && urlList.size() > position) {
                String path = urlList.get(position);
                if (URLUtil.isNetworkUrl(path)) {
                    Picasso.with(mContext)
                            .load(path)
                            .fit().centerInside()
                            .placeholder(R.drawable.img_defalut).error(R.drawable.img_defalut)
                            .into(imageView);
                } else if (new File(path).exists()) {
                    Picasso.with(mContext)
                            .load(new File(path))
                            .fit().centerInside()
                            .placeholder(R.drawable.img_defalut).error(R.drawable.img_defalut)
                            .into(imageView);
                } else {
                    Picasso.with(mContext)
                            .load(R.drawable.img_defalut)
                            .fit().centerInside()
                            .into(imageView);
                }
            } else {
                Picasso.with(mContext)
                        .load(R.drawable.img_defalut)
                        .fit().centerInside()
                        .into(imageView);
            }
            return convertView;
        }

        @Override
        public int getItemPosition(Object object) {
            return RecyclingPagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            if (urlList != null && urlList.size() > 0) {
                return urlList.size();
            } else {
                return 0;
            }
        }
    };

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        layoutGallery.scrollTo(x, y / 2);
    }
}
