package com.simoncherry.faketantan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
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
import com.shizhefei.view.indicator.Indicator;
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

import co.lujun.androidtagview.TagContainerLayout;

public class OtherProfileActivity extends Activity implements ScrollViewListener {

    public final static int REQUEST_CODE = 1024;

    private Context mContext;
    private ExitActivityTransition exitTransition;

    private LinearLayout globalTitle;
    private FrameLayout layoutGallery;
    private ImageView ivAvatar;
    private ViewPager viewPager;
    private Indicator indicator;
    private IndicatorViewPager indicatorViewPager;

    private ObservableScrollView scrollView;
    private LinearLayout layoutProfile;
    private TextView tvNickName;
    private ImageView ivSex;
    private TextView tvIdentify;
    private TextView tvBirthDay;
    private TextView tvConstellation;
    private ImageView ivConstellation;
    private TextView tvFrom;
    private TextView tvJob;
    private TextView tvSign;
    private TagContainerLayout layoutTag;
    private View viewBlank;

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
        mContext = OtherProfileActivity.this;

        globalTitle = (LinearLayout) findViewById(R.id.layout_global_title);
        scrollView = (ObservableScrollView) findViewById(R.id.scrollView);
        ivAvatar = (ImageView) findViewById(R.id.iv_avatar);
        layoutGallery = (FrameLayout) findViewById(R.id.layout_gallery);
        layoutProfile = (LinearLayout) findViewById(R.id.layout_profile);
        viewPager = (ViewPager) findViewById(R.id.banner_viewPager);
        indicator = (Indicator) findViewById(R.id.banner_indicator);
        tvNickName = (TextView) findViewById(R.id.tv_nickname);
        ivSex = (ImageView) findViewById(R.id.iv_sex);
        tvIdentify = (TextView) findViewById(R.id.tv_id);
        tvBirthDay = (TextView) findViewById(R.id.tv_birthday);
        tvConstellation = (TextView) findViewById(R.id.tv_constellation);
        ivConstellation = (ImageView) findViewById(R.id.iv_constellation);
        tvFrom = (TextView) findViewById(R.id.tv_from);
        tvJob = (TextView) findViewById(R.id.tv_job);
        tvSign = (TextView) findViewById(R.id.tv_sign);
        layoutTag = (TagContainerLayout) findViewById(R.id.layout_tag);
        viewBlank = (View) findViewById(R.id.view_blank);

        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int screenWidth = dm.widthPixels;

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)layoutGallery.getLayoutParams();
        lp.width = screenWidth;
        lp.height = screenWidth;
        lp.addRule(RelativeLayout.BELOW, globalTitle.getId());
        layoutGallery.setLayoutParams(lp);
        // viewBlank.setLayoutParams(lp);  // TODO 这句导致layoutGallery的位置出错
        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams)viewBlank.getLayoutParams();
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

        viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(adapter);

        initMockData(img_index);
        viewPager.setCurrentItem(img_page);
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

        initView();
    }

    private void initView() {
//        Resources resources = this.getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        int screenWidth = dm.widthPixels;
//        //Logger.e("get Screen Width", String.valueOf(screenWidth));
//
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)layoutGallery.getLayoutParams();
//        lp.width = screenWidth;
//        lp.height = screenWidth;
//        lp.addRule(RelativeLayout.BELOW, globalTitle.getId());
//        //layoutGallery.setLayoutParams(lp);
//        viewBlank.setLayoutParams(lp);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                    viewPager.onTouchEvent(event);
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

    private void initMockImage(int which) {
        urlList = new ArrayList<>();
        for (int i=0; i<4; i++) {
            //urlList.add(MockData.avatarUrl[which*4 + i]);
            urlList.add(TestUtil.Url[which*4 + i]);
        }
        adapter.notifyDataSetChanged();
    }

    private void initMockData(int which) {
        initMockImage(which);
        which = which%4;
        tvNickName.setText(MockData.nickName[which]);
        ivSex.setImageResource(R.drawable.imformation_sex_female);
        tvIdentify.setText(MockData.ID[which]);
        tvBirthDay.setText(MockData.birthDay[which]);
        tvConstellation.setText(MockData.constellation[which]);
        ivConstellation.setImageResource(MockData.starResId[which]);
        tvFrom.setText(MockData.comeFrom[which]);
        tvJob.setText(MockData.job[which]);
        tvSign.setText(MockData.sign[which]);
        layoutTag.setTags(MockData.tags[which]);
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
        //Logger.t("onScrollChanged y").e(String.valueOf(y));
        layoutGallery.scrollTo(x, y/2);
    }

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

        exitTransition.exit(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
        animation.setFillAfter(true);
        layoutProfile.startAnimation(animation);
    }
}
