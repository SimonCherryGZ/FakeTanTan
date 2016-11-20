package com.simoncherry.faketantan.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.RecyclingPagerAdapter;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.custom.ObservableScrollView;
import com.simoncherry.faketantan.custom.ScrollViewListener;
import com.simoncherry.faketantan.utils.TestUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

public class UserProfileActivity extends AppCompatActivity implements ScrollViewListener {

    private LinearLayout globalTitle;
    private FrameLayout layoutGallery;
    private ImageView ivAvatar;
    private ViewPager viewPager;
    private Indicator indicator;
    private IndicatorViewPager indicatorViewPager;

    private ObservableScrollView scrollView;
    private View viewBlank;
    private RelativeLayout layoutProfile;
    private TextView tvNickName;
    private TextView tvAge;
    private TextView tvStar;
    private TextView tvStatus;

    private TextView tvLikeCount;
    private TextView tvFlaunt;

    private TextView tvIndustry;
    private TextView tvJob;
    private TextView tvFrom;

    private TagContainerLayout layoutTagSign;
    private TagContainerLayout layouTagSport;
    private TagContainerLayout layouTagMusic;
    private TagContainerLayout layouTagFood;
    private TagContainerLayout layouTagMovie;
    private TagContainerLayout layouTagBook;
    private TagContainerLayout layouTagTravel;

    private Context mContext;
    private List<String> urlList;
    private float touchX = 0;
    private float touchY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mContext = UserProfileActivity.this;

        globalTitle = (LinearLayout) findViewById(R.id.layout_global_title);
        scrollView = (ObservableScrollView) findViewById(R.id.scrollView);
        layoutGallery = (FrameLayout) findViewById(R.id.layout_gallery);
        viewPager = (ViewPager) findViewById(R.id.banner_viewPager);
        indicator = (Indicator) findViewById(R.id.banner_indicator);
        viewBlank = (View) findViewById(R.id.view_blank);
        layoutProfile = (RelativeLayout) findViewById(R.id.layout_profile);
        tvNickName = (TextView) findViewById(R.id.tv_nickname);
        tvAge = (TextView) findViewById(R.id.tv_age);
        tvStar = (TextView) findViewById(R.id.tv_star);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        tvLikeCount = (TextView) findViewById(R.id.tv_like_count);
        tvFlaunt = (TextView) findViewById(R.id.tv_flaunt);
        tvIndustry = (TextView) findViewById(R.id.tv_industry);
        tvJob = (TextView) findViewById(R.id.tv_job);
        tvFrom = (TextView) findViewById(R.id.tv_from);
        layoutTagSign = (TagContainerLayout) findViewById(R.id.layout_tag_sign);
        layouTagSport = (TagContainerLayout) findViewById(R.id.layout_tag_sport);
        layouTagMusic = (TagContainerLayout) findViewById(R.id.layout_tag_music);
        layouTagFood = (TagContainerLayout) findViewById(R.id.layout_tag_food);
        layouTagMovie = (TagContainerLayout) findViewById(R.id.layout_tag_movie);
        layouTagBook = (TagContainerLayout) findViewById(R.id.layout_tag_book);
        layouTagTravel = (TagContainerLayout) findViewById(R.id.layout_tag_travel);

        initView();
        initMockData();
    }

    private void initView() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int screenWidth = dm.widthPixels;

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)layoutGallery.getLayoutParams();
        lp.width = screenWidth;
        lp.height = screenWidth;
        lp.addRule(RelativeLayout.BELOW, globalTitle.getId());
        layoutGallery.setLayoutParams(lp);
        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams)viewBlank.getLayoutParams();
        lp2.width = screenWidth;
        lp2.height = screenWidth;
        viewBlank.setLayoutParams(lp2);

        viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(adapter);

        urlList = new ArrayList<>();
        urlList.add(TestUtil.Url[0]);
        adapter.notifyDataSetChanged();

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
                    viewPager.onTouchEvent(event);
                }
                return true;
            }
        });
    }

    private  void initMockData() {
        tvNickName.setText(TestUtil.nickName);
        tvAge.setText(TestUtil.age);
        tvStar.setText(TestUtil.star);
        tvStatus.setText(TestUtil.status);
        tvLikeCount.setText(String.valueOf(TestUtil.likeCount));
        tvIndustry.setText(TestUtil.industry);
        tvJob.setText(TestUtil.job);
        tvFrom.setText(TestUtil.from);

        List<String> tags = new ArrayList<>();
        Collections.addAll(tags, TestUtil.tagSign);
        layoutTagSign.setTags(tags);
        tags.clear();
        Collections.addAll(tags, TestUtil.tagSport);
        layouTagSport.setTags(tags);
        tags.clear();
        Collections.addAll(tags, TestUtil.tagMusic);
        layouTagMusic.setTags(tags);
        tags.clear();
        Collections.addAll(tags, TestUtil.tagFood);
        layouTagFood.setTags(tags);
        tags.clear();
        Collections.addAll(tags, TestUtil.tagMovie);
        layouTagMovie.setTags(tags);
        tags.clear();
        Collections.addAll(tags, TestUtil.tagBook);
        layouTagBook.setTags(tags);
        tags.clear();
        Collections.addAll(tags, TestUtil.tagTravel);
        layouTagTravel.setTags(tags);
    }

    private boolean isContain(View view,float x,float y) {
        int[] point = new int[2];
        view.getLocationOnScreen(point);
        if(x >= point[0] && x <= (point[0]+view.getWidth()) && y >= point[1] && y <= (point[1]+view.getHeight())) {
            return true;
        }
        return false;
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
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        layoutGallery.scrollTo(x, y/2);
    }
}
