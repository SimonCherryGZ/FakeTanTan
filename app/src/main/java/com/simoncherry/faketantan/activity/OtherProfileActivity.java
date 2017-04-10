package com.simoncherry.faketantan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.RecyclingPagerAdapter;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.custom.ObservableScrollView;
import com.simoncherry.faketantan.custom.ScrollViewListener;
import com.simoncherry.faketantan.event.ChangePhotoEvent;
import com.simoncherry.faketantan.utils.MockData;
import com.simoncherry.faketantan.utils.TestUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.lujun.androidtagview.TagContainerLayout;

public class OtherProfileActivity extends AppCompatActivity implements ScrollViewListener {

    @BindView(R.id.layout_global_title)
    LinearLayout globalTitle;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.icon_slidingmenu_left)
    ImageView iconSlidingmenuLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.icon_slidingmenu_right)
    ImageView iconSlidingmenuRight;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.banner_viewPager)
    ViewPager bannerViewPager;
    @BindView(R.id.banner_indicator)
    FixedIndicatorView bannerIndicator;
    @BindView(R.id.layout_gallery)
    FrameLayout layoutGallery;
    @BindView(R.id.view_blank)
    View viewBlank;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.layout_age)
    LinearLayout layoutAge;
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
    @BindView(R.id.activity_other_profile_new)
    RelativeLayout activityOtherProfileNew;


    private Context mContext;
    private ExitActivityTransition exitTransition;
    private IndicatorViewPager indicatorViewPager;

    public final static int REQUEST_CODE = 1024;
    private List<String> urlList;
    private int img_index = 0;
    private int img_page = 0;
    private String img_url = TestUtil.Url[0];
    private float touchX = 0;
    private float touchY = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        ButterKnife.bind(this);
        mContext = OtherProfileActivity.this;

        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int screenWidth = dm.widthPixels;

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutGallery.getLayoutParams();
        lp.width = screenWidth;
        lp.height = screenWidth;
        lp.addRule(RelativeLayout.BELOW, globalTitle.getId());
        layoutGallery.setLayoutParams(lp);
        // viewBlank.setLayoutParams(lp);  // TODO 这句导致layoutGallery的位置出错
        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) viewBlank.getLayoutParams();
        lp2.width = screenWidth;
        lp2.height = screenWidth;
        viewBlank.setLayoutParams(lp2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            img_url = bundle.getString("img_url");
            img_index = bundle.getInt("img_index");
            img_page = bundle.getInt("img_page");
        }

        Picasso.with(mContext)
                .load(img_url)
                .noFade()
                .resize(200, 200)
                .centerCrop()
                .into(ivAvatar);

        bannerViewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(bannerIndicator, bannerViewPager);
        indicatorViewPager.setAdapter(adapter);

        initMockData(img_index);
        bannerViewPager.setCurrentItem(img_page);
        img_url = urlList.get(img_page);

        exitTransition = ActivityTransition.with(getIntent()).to(layoutGallery).duration(300).start(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in);
                animation.setFillAfter(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        layoutProfile.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //ivAvatar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                layoutProfile.startAnimation(animation);
            }
        }, 50);

        initGallery();
    }

    private void initGallery() {
        bannerViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (urlList != null && urlList.size() > 0) {
                    img_url = urlList.get(position);
                    img_page = position;
                    EventBus.getDefault().post(new ChangePhotoEvent(img_url));
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

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
                            onBackPressed();
                        }
                    }
                }

                scrollView.onTouchEvent(event);
                if (!isContain(layoutProfile, event.getRawX(), event.getRawY())) {  // 精华：判断触摸的坐标不在layoutProfile范围内，则将MotionEvent赋值给ViewPager
                    bannerViewPager.onTouchEvent(event);
                }
                return true;
            }
        });

        // TODO test
        globalTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources resources = mContext.getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                int screenWidth = dm.widthPixels;
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)layoutGallery.getLayoutParams();
                lp.width = screenWidth;
                lp.height = screenWidth;
                //viewBlank.setLayoutParams(lp);
                lp.addRule(RelativeLayout.BELOW, globalTitle.getId());
                layoutGallery.setLayoutParams(lp);
            }
        });
    }

    private boolean isContain(View view,float x,float y) {
        int[] point = new int[2];
        view.getLocationOnScreen(point);
        if(x >= point[0] && x <= (point[0]+view.getWidth()) && y >= point[1] && y <= (point[1]+view.getHeight())) {
            return true;
        }
        return false;
    }

    private void initMockData(int which) {
        initMockImage(which);
        which = which % 4;
        tvNickname.setText(MockData.nickName[which]);
        layoutAge.setBackgroundResource(R.drawable.bg_tv_age_female);
        tvAge.setText(MockData.age[which]);
        ivSex.setImageResource(R.drawable.ic_female);
        tvStar.setText(MockData.constellation[which]);
        tvLikeCount.setText(String.valueOf(MockData.likeCount[which]));
        tvIndustry.setText(MockData.industry[which]);
        tvJob.setText(MockData.job[which]);
        layoutCompany.setVisibility(View.GONE);
        tvFrom.setText(MockData.comeFrom[which]);
        layoutMarker.setVisibility(View.GONE);
        tvSign.setText(MockData.sign[which]);
        layoutTagSign.setTags(MockData.my_tags[which]);
        layoutTagSport.setTags(MockData.sport_tags[which]);
        layoutTagMusic.setTags(MockData.music_tags[which]);
        layoutTagFood.setTags(MockData.food_tags[which]);
        layoutTagMovie.setTags(MockData.movie_tags[which]);
        layoutTagBook.setTags(MockData.book_tags[which]);
        layoutTagTravel.setTags(MockData.travel_tags[which]);
    }

    private void initMockImage(int which) {
        urlList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            //urlList.add(MockData.avatarUrl[which*4 + i]);
            urlList.add(TestUtil.Url[which * 4 + i]);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        //Logger.t("onScrollChanged y").e(String.valueOf(y));
        layoutGallery.scrollTo(x, y/2);
    }

    private IndicatorViewPager.IndicatorViewPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {
        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_welcome, container, false);
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
                convertView = new ImageView(getApplicationContext());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            ImageView imageView = (ImageView) convertView;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (urlList.size() > 0) {
                Picasso.with(mContext)
                        .load(urlList.get(position))
//                        .resize(200, 200)
//                        .centerCrop()
                        .fit()
                        .centerInside()
                        .placeholder(R.drawable.img_defalut)
                        .error(R.drawable.img_defalut)
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
    public void onBackPressed() {
        scrollView.fullScroll(ScrollView.FOCUS_UP);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString("url", img_url);
        bundle.putInt("index", img_index);
        bundle.putInt("page", img_page);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);

        layoutProfile.setVisibility(View.GONE);
        exitTransition.exit(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
        animation.setFillAfter(true);
        layoutProfile.startAnimation(animation);
    }
}
