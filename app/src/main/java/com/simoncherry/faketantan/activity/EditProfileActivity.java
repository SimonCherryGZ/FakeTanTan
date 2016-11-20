package com.simoncherry.faketantan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baoyz.treasure.Treasure;
import com.learnncode.mediachooser.MediaChooser;
import com.learnncode.mediachooser.activity.HomeScreenMediaChooser;
import com.orhanobut.logger.Logger;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.bean.PhotoItem;
import com.simoncherry.faketantan.custom.AlbumView;
import com.simoncherry.faketantan.custom.ObservableScrollView;
import com.simoncherry.faketantan.event.RefreshProfileEvent;
import com.simoncherry.faketantan.sp.JsonConverterFactory;
import com.simoncherry.faketantan.sp.UserData;
import com.simoncherry.faketantan.utils.DateUtil;
import com.simoncherry.faketantan.utils.TestUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.lujun.androidtagview.TagContainerLayout;

public class EditProfileActivity extends AppCompatActivity implements AlbumView.OnItemClickListener {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.icon_slidingmenu_left)
    ImageView iconSlidingmenuLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.icon_slidingmenu_right)
    ImageView iconSlidingmenuRight;

    @BindView(R.id.imageListView)
    AlbumView mAlbumView;
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
    @BindView(R.id.tv_plus_industry)
    TextView tvPlusIndustry;
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.layout_industry)
    RelativeLayout layoutIndustry;
    @BindView(R.id.iv_job)
    ImageView ivJob;
    @BindView(R.id.tv_plus_job)
    TextView tvPlusJob;
    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.layout_job)
    RelativeLayout layoutJob;
    @BindView(R.id.iv_company)
    ImageView ivCompany;
    @BindView(R.id.tv_plus_company)
    TextView tvPlusCompany;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.layout_company)
    RelativeLayout layoutCompany;
    @BindView(R.id.iv_from)
    ImageView ivFrom;
    @BindView(R.id.tv_plus_from)
    TextView tvPlusFrom;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.layout_from)
    RelativeLayout layoutFrom;
    @BindView(R.id.iv_marker)
    ImageView ivMarker;
    @BindView(R.id.tv_plus_plus_marker)
    TextView tvPlusPlusMarker;
    @BindView(R.id.tv_marker)
    TextView tvMarker;
    @BindView(R.id.layout_marker)
    RelativeLayout layoutMarker;
    @BindView(R.id.iv_sign)
    ImageView ivSign;
    @BindView(R.id.tv_plus_sign)
    TextView tvPlusSign;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.layout_sign)
    RelativeLayout layoutSign;
    @BindView(R.id.layout_my_info)
    RelativeLayout layoutMyInfo;
    @BindView(R.id.layout_tag_container)
    RelativeLayout layoutTagContainer;
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
    @BindView(R.id.Rootlayout)
    GridLayout Rootlayout;
    @BindView(R.id.activity_edit_profile)
    RelativeLayout activityEditProfile;
    @BindView(R.id.tv_plus_tag_sign)
    TextView tvPlusTagSign;
    @BindView(R.id.tv_tag_sign)
    TextView tvTagSign;
    @BindView(R.id.tv_plus_tag_sport)
    TextView tvPlusTagSport;
    @BindView(R.id.tv_tag_sport)
    TextView tvTagSport;
    @BindView(R.id.tv_plus_tag_music)
    TextView tvPlusTagMusic;
    @BindView(R.id.tv_tag_music)
    TextView tvTagMusic;
    @BindView(R.id.tv_plus_tag_food)
    TextView tvPlusTagFood;
    @BindView(R.id.tv_tag_food)
    TextView tvTagFood;
    @BindView(R.id.tv_plus_tag_movie)
    TextView tvPlusTagMovie;
    @BindView(R.id.tv_tag_movie)
    TextView tvTagMovie;
    @BindView(R.id.tv_plus_tag_book)
    TextView tvPlusTagBook;
    @BindView(R.id.tv_tag_book)
    TextView tvTagBook;
    @BindView(R.id.tv_plus_tag_travel)
    TextView tvPlusTagTravel;
    @BindView(R.id.tv_tag_travel)
    TextView tvTagTravel;

    @BindView(R.id.layout_loading)
    RelativeLayout layoutLoading;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;


    private Context mContext;
    private Unbinder unbinder;
    private UserData userData;
    private int clickPosition = -1;
    private AlbumView.AlbumViewListener albumViewListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        unbinder = ButterKnife.bind(this);
        mContext = EditProfileActivity.this;
        Treasure.setConverterFactory(new JsonConverterFactory());
        userData = Treasure.get(mContext, UserData.class);

        initView();
        initMockData();
        initGallery();

        IntentFilter imageIntentFilter = new IntentFilter(MediaChooser.IMAGE_SELECTED_ACTION_FROM_MEDIA_CHOOSER);
        registerReceiver(imageBroadcastReceiver, imageIntentFilter);

        IntentFilter videoIntentFilter = new IntentFilter(MediaChooser.VIDEO_SELECTED_ACTION_FROM_MEDIA_CHOOSER);
        registerReceiver(videoBroadcastReceiver, videoIntentFilter);

        albumViewListener = new AlbumView.AlbumViewListener() {
            @Override
            public void swapItem(int pos1, int pos2) {
                Toast.makeText(mContext, "pos1: " + String.valueOf(pos1) + "  pos2: " + String.valueOf(pos2), Toast.LENGTH_SHORT).show();
            }
        };
        mAlbumView.setAlbumViewListener(albumViewListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMockData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        unregisterReceiver(imageBroadcastReceiver);
        unregisterReceiver(videoBroadcastReceiver);
    }

    private void initView() {
        iconSlidingmenuLeft.setImageResource(R.drawable.title_left_back_white);
        iconSlidingmenuRight.setImageResource(0);
        tvTitleMiddle.setText("编辑个人资料");

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    private void initGallery() {
        mAlbumView.setRootView((GridLayout) findViewById(R.id.Rootlayout));
        mAlbumView.setImages(convertPhotoItem(6, getImageDate()));
        mAlbumView.setOnItemClickListener(this);
    }

    private List<PhotoItem> getImageDate() {
//        List<PhotoItem> mDataList = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            PhotoItem item = new PhotoItem();
//            item.hyperlink = TestUtil.Url[i];
//            item.id = i + 1;
//            item.sort = i + 1;
//            mDataList.add(item);
//        }
//        return mDataList;
        List<PhotoItem> mDataList = userData.getPhotoItem();
        if (mDataList != null && mDataList.size() > 0) {
            return mDataList;
        } else {
            mDataList = new ArrayList<>();
            PhotoItem item = new PhotoItem();
            item.hyperlink = TestUtil.Url[0];
            item.id = 1;
            item.sort = 1;
            mDataList.add(item);
            return mDataList;
        }
    }

    public List<PhotoItem> convertPhotoItem(int qty, List<PhotoItem> Datas) {
        List<PhotoItem> items = new ArrayList<>();
        if (Datas != null) {
            items.addAll(Datas);
        }
        //添加null
        for (int i = Datas == null ? 0 : Datas.size(); i < qty; i++) {
            items.add(new PhotoItem());
        }
        return items;
    }

    private void initMockData() {
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
        //tvStatus.setText(TestUtil.status);
        tvLikeCount.setText(String.valueOf(TestUtil.likeCount));

        String industry = userData.getIndustry();
        if (industry != null && !TextUtils.isEmpty(industry)) {
            tvIndustry.setText(industry);
            tvIndustry.setTextColor(getResources().getColor(R.color.color5));
            tvPlusIndustry.setVisibility(View.GONE);
        } else {
            tvIndustry.setText("添加行业信息");
            tvIndustry.setTextColor(getResources().getColor(R.color.color9));
            tvPlusIndustry.setVisibility(View.VISIBLE);
        }
        String job = userData.getJob();
        if (job != null && !TextUtils.isEmpty(job)) {
            tvJob.setText(job);
            tvJob.setTextColor(getResources().getColor(R.color.color5));
            tvPlusJob.setVisibility(View.GONE);
        } else {
            tvJob.setText("添加工作领域");
            tvJob.setTextColor(getResources().getColor(R.color.color9));
            tvPlusJob.setVisibility(View.VISIBLE);
        }
        String company = userData.getCompany();
        if (company != null && !TextUtils.isEmpty(company)) {
            tvCompany.setText(company);
            tvCompany.setTextColor(getResources().getColor(R.color.color5));
            tvPlusCompany.setVisibility(View.GONE);
        } else {
            tvCompany.setText("添加公司信息");
            tvCompany.setTextColor(getResources().getColor(R.color.color9));
            tvPlusCompany.setVisibility(View.VISIBLE);
        }
        String from = userData.getFrom();
        if (from != null && !TextUtils.isEmpty(from)) {
            tvFrom.setText(from);
            tvFrom.setTextColor(getResources().getColor(R.color.color5));
            tvPlusFrom.setVisibility(View.GONE);
        } else {
            tvFrom.setText("添加来自信息");
            tvFrom.setTextColor(getResources().getColor(R.color.color9));
            tvPlusFrom.setVisibility(View.VISIBLE);
        }
        String marker = userData.getMarker();
        if (marker != null && !TextUtils.isEmpty(marker)) {
            tvMarker.setText(marker);
            tvMarker.setTextColor(getResources().getColor(R.color.color5));
            tvPlusPlusMarker.setVisibility(View.GONE);
        } else {
            tvMarker.setText("添加你常去的地点");
            tvMarker.setTextColor(getResources().getColor(R.color.color9));
            tvPlusPlusMarker.setVisibility(View.VISIBLE);
        }
        String sign = userData.getSign();
        if (sign != null && !TextUtils.isEmpty(sign)) {
            tvSign.setText(sign);
            tvSign.setTextColor(getResources().getColor(R.color.color5));
            tvPlusSign.setVisibility(View.GONE);
        } else {
            tvSign.setText("添加你的个性签名");
            tvSign.setTextColor(getResources().getColor(R.color.color9));
            tvPlusSign.setVisibility(View.VISIBLE);
        }

        List<String> tags = userData.getTagSign();
        if (tags != null && tags.size() > 0) {
            layoutTagSign.setTags(tags);
            tags.clear();
            tvPlusTagSign.setVisibility(View.GONE);
            tvTagSign.setVisibility(View.GONE);
        } else {
            layoutTagSign.removeAllViews();
            tvPlusTagSign.setVisibility(View.VISIBLE);
            tvTagSign.setVisibility(View.VISIBLE);
        }

        tags = userData.getTagSport();
        if (tags != null && tags.size() > 0) {
            layoutTagSport.setTags(tags);
            tags.clear();
            tvPlusTagSport.setVisibility(View.GONE);
            tvTagSport.setVisibility(View.GONE);
        } else {
            layoutTagSport.removeAllViews();
            tvPlusTagSport.setVisibility(View.VISIBLE);
            tvTagSport.setVisibility(View.VISIBLE);
        }

        tags = userData.getTagMusic();
        if (tags != null && tags.size() > 0) {
            layoutTagMusic.setTags(tags);
            tags.clear();
            tvPlusTagMusic.setVisibility(View.GONE);
            tvTagMusic.setVisibility(View.GONE);
        } else {
            layoutTagMusic.removeAllViews();
            tvPlusTagMusic.setVisibility(View.VISIBLE);
            tvTagMusic.setVisibility(View.VISIBLE);
        }

        tags = userData.getTagFood();
        if (tags != null && tags.size() > 0) {
            layoutTagFood.setTags(tags);
            tags.clear();
            tvPlusTagFood.setVisibility(View.GONE);
            tvTagFood.setVisibility(View.GONE);
        } else {
            layoutTagFood.removeAllViews();
            tvPlusTagFood.setVisibility(View.VISIBLE);
            tvTagFood.setVisibility(View.VISIBLE);
        }

        tags = userData.getTagMovie();
        if (tags != null && tags.size() > 0) {
            layoutTagMovie.setTags(tags);
            tags.clear();
            tvPlusTagMovie.setVisibility(View.GONE);
            tvTagMovie.setVisibility(View.GONE);
        } else {
            layoutTagMovie.removeAllViews();
            tvPlusTagMovie.setVisibility(View.VISIBLE);
            tvTagMovie.setVisibility(View.VISIBLE);
        }

        tags = userData.getTagBook();
        if (tags != null && tags.size() > 0) {
            layoutTagBook.setTags(tags);
            tags.clear();
            tvPlusTagBook.setVisibility(View.GONE);
            tvTagBook.setVisibility(View.GONE);
        } else {
            layoutTagBook.removeAllViews();
            tvPlusTagBook.setVisibility(View.VISIBLE);
            tvTagBook.setVisibility(View.VISIBLE);
        }

        tags = userData.getTagTravel();
        if (tags != null && tags.size() > 0) {
            layoutTagTravel.setTags(tags);
            tags.clear();
            tvPlusTagTravel.setVisibility(View.GONE);
            tvTagTravel.setVisibility(View.GONE);
        } else {
            layoutTagTravel.removeAllViews();
            tvPlusTagTravel.setVisibility(View.VISIBLE);
            tvTagTravel.setVisibility(View.VISIBLE);
        }
    }

    private void refreshMockData() {
        initMockData();
        EventBus.getDefault().post(new RefreshProfileEvent());
    }

    private void showEditDialog(String title, View view, MaterialDialog.SingleButtonCallback callback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        builder.title(title);
        builder.positiveColor(getResources().getColor(R.color.colorPrimary));
        builder.negativeColor(getResources().getColor(R.color.colorPrimary));
        builder.positiveText("确定");
        builder.negativeText("取消");
        builder.customView(view, true);
        builder.onPositive(callback);
        builder.show();
    }

    private void showSingleSelectDialog(String title, @ArrayRes int itemsRes, MaterialDialog.ListCallbackSingleChoice callback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        builder.title(title);
        builder.positiveColor(getResources().getColor(R.color.colorPrimary));
        builder.negativeColor(getResources().getColor(R.color.colorPrimary));
        builder.positiveText("确定");
        builder.negativeText("取消");
        builder.items(itemsRes);
        builder.itemsCallbackSingleChoice(-1, callback);
        builder.show();
    }

    private void showMultiSelectDialog(String title, @ArrayRes int itemsRes, Integer[] selected, MaterialDialog.ListCallbackMultiChoice callback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        builder.title(title);
        builder.positiveColor(getResources().getColor(R.color.colorPrimary));
        builder.negativeColor(getResources().getColor(R.color.colorPrimary));
        builder.positiveText("确定");
        builder.negativeText("取消");
        builder.items(itemsRes);
        builder.itemsCallbackMultiChoice(selected, callback);
        builder.show();
    }

    BroadcastReceiver imageBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<String> urlList = intent.getStringArrayListExtra("list");
            if (urlList != null && urlList.size() > 0) {
                MediaChooser.setSelectedMediaCount(0);
                Toast.makeText(mContext, "Image Url :" + urlList.get(0), Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "click position :" + clickPosition, Toast.LENGTH_SHORT).show();

                List<PhotoItem> mDataList = userData.getPhotoItem();
                if (mDataList == null) {
                    mDataList = new ArrayList<>();
                }
                PhotoItem photoItem = new PhotoItem();
                photoItem.hyperlink = urlList.get(0);
                if (mDataList.size() == 0) {
                    photoItem.sort = 1;
                    photoItem.id = 1;
                    mDataList.add(photoItem);
                } else if (clickPosition < mDataList.size()) {
                    photoItem.sort = clickPosition + 1;
                    photoItem.id = clickPosition + 1;
                    //mDataList.add(clickPosition, photoItem);
                    mDataList.set(clickPosition, photoItem);
                } else if (clickPosition == mDataList.size()) {
                    photoItem.sort = mDataList.size() + 1;
                    photoItem.id = mDataList.size() + 1;
                    mDataList.add(photoItem);
                }
                userData.setPhotoItem(mDataList);

                if (clickPosition >= 0) {
                    mAlbumView.updateImage(clickPosition, urlList.get(0));
                    clickPosition = -1;
                }
            }
        }
    };

    BroadcastReceiver videoBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<String> urlList = intent.getStringArrayListExtra("list");
            if (urlList != null && urlList.size() > 0) {
                MediaChooser.setSelectedMediaCount(0);
                Toast.makeText(mContext, "Image Url :" + urlList.get(0), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void ItemClick(View view, int position, boolean Photo) {
        // Photo 照片还是空格子
        Logger.e("position: " + position + ",Photo : " + Photo);
        if (position <= mAlbumView.getDataSize()) {
            clickPosition = position;
            MediaChooser.setSelectionLimit(1);
            HomeScreenMediaChooser.startMediaChooser(EditProfileActivity.this, false);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        layoutLoading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);

        List<PhotoItem> photoItemList = mAlbumView.getImages();
        if (photoItemList != null && photoItemList.size() > 0) {
            userData.setPhotoItem(photoItemList);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        }, 2000);
    }

    @OnClick({R.id.icon_slidingmenu_left,
            R.id.layout_user_base, R.id.layout_industry, R.id.layout_job, R.id.layout_company, R.id.layout_from, R.id.layout_marker, R.id.layout_sign,
            R.id.layout_tag_container, R.id.layout_sport, R.id.layout_music, R.id.layout_food, R.id.layout_movie, R.id.layout_book, R.id.layout_travel,
            //R.id.layout_tag_sign, R.id.layout_tag_sport, R.id.layout_tag_music, R.id.layout_tag_food, R.id.layout_tag_movie, R.id.layout_tag_book, R.id.layout_tag_travel
    })
    public void onClick(View v) {
        final EditText editText = new EditText(this);
        editText.setBackground(null);
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(editText, R.drawable.edit_cursor_color);
        } catch (Exception ignored) {
        }

        switch (v.getId()) {
            case R.id.icon_slidingmenu_left:
                onBackPressed();
                break;
            case R.id.layout_user_base:
                Intent account = new Intent(this, AccountActivity.class);
                startActivity(account);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.layout_industry:
                final String[] industry = getResources().getStringArray(R.array.preset_industry);
                showSingleSelectDialog("行业", R.array.preset_industry, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        //Toast.makeText(mContext, industry[which], Toast.LENGTH_SHORT).show();
                        userData.setIndustry(industry[which]);
                        refreshMockData();
                        return false;
                    }
                });
                break;
            case R.id.layout_job:
                final String[] job = getResources().getStringArray(R.array.preset_job);
                showSingleSelectDialog("工作领域", R.array.preset_job, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        //Toast.makeText(mContext, job[which], Toast.LENGTH_SHORT).show();
                        userData.setJob(job[which]);
                        refreshMockData();
                        return false;
                    }
                });
                break;
            case R.id.layout_company:
                editText.setHint("添加公司信息");
                showEditDialog("公司", editText, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //Toast.makeText(mContext, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                        String str = editText.getText().toString();
                        userData.setCompany(!TextUtils.isEmpty(str) ? str : null);
                        refreshMockData();
                    }
                });
                break;
            case R.id.layout_from:
                final String[] from = getResources().getStringArray(R.array.preset_from);
                showSingleSelectDialog("来自", R.array.preset_from, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        //Toast.makeText(mContext, from[which], Toast.LENGTH_SHORT).show();
                        userData.setFrom(from[which]);
                        refreshMockData();
                        return false;
                    }
                });
                break;
            case R.id.layout_marker:
                editText.setHint("添加你常去的地点");
                showEditDialog("经常出没", editText, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //Toast.makeText(mContext, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                        String str = editText.getText().toString();
                        userData.setMarker(!TextUtils.isEmpty(str) ? str : null);
                        refreshMockData();
                    }
                });
                break;
            case R.id.layout_sign:
                editText.setHint("添加你的个人签名");
                showEditDialog("个人签名", editText, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //Toast.makeText(mContext, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                        String str = editText.getText().toString();
                        userData.setSign(!TextUtils.isEmpty(str) ? str : null);
                        refreshMockData();
                    }
                });
                break;
            case R.id.layout_tag_container:
                //case R.id.layout_tag_sign:
                final String[] sign = getResources().getStringArray(R.array.preset_sign);
                Integer[] signIndexArray = null;
                List<Integer> signIndexList = userData.getIndexSign();
                if (signIndexList != null && signIndexList.size() > 0) {
                    signIndexArray = signIndexList.toArray(new Integer[signIndexList.size()]);
                }
                showMultiSelectDialog("个性", R.array.preset_sign, signIndexArray, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        List<Integer> indexs = Arrays.asList(which);
                        List<String> tags = new ArrayList<>();
                        for (Integer index : which) {
                            tags.add(sign[index]);
                        }
                        userData.setTagSign(tags.size() > 0 ? tags : null);
                        userData.setIndexSign(indexs);
                        refreshMockData();
                        return false;
                    }
                });
                break;
            case R.id.layout_sport:
                final String[] sports = getResources().getStringArray(R.array.preset_sport);
                Integer[] sportIndexArray = null;
                List<Integer> sportIndexList = userData.getIndexSport();
                if (sportIndexList != null && sportIndexList.size() > 0) {
                    sportIndexArray = sportIndexList.toArray(new Integer[sportIndexList.size()]);
                }
                showMultiSelectDialog("运动", R.array.preset_sport, sportIndexArray, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        //String str = "";
                        List<Integer> indexs = Arrays.asList(which);
                        List<String> tags = new ArrayList<>();
                        for (Integer index : which) {
                            //str = str + sports[index] + " ";
                            tags.add(sports[index]);
                        }
                        //Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
                        userData.setTagSport(tags.size() > 0 ? tags : null);
                        userData.setIndexSport(indexs);
                        refreshMockData();
                        return false;
                    }
                });
                break;
            case R.id.layout_music:
                final String[] music = getResources().getStringArray(R.array.preset_music);
                Integer[] musicIndexArray = null;
                List<Integer> musicIndexList = userData.getIndexMusic();
                if (musicIndexList != null && musicIndexList.size() > 0) {
                    musicIndexArray = musicIndexList.toArray(new Integer[musicIndexList.size()]);
                }
                showMultiSelectDialog("音乐", R.array.preset_music, musicIndexArray, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        List<Integer> indexs = Arrays.asList(which);
                        List<String> tags = new ArrayList<>();
                        for (Integer index : which) {
                            tags.add(music[index]);
                        }
                        userData.setTagMusic(tags.size() > 0 ? tags : null);
                        userData.setIndexMusic(indexs);
                        refreshMockData();
                        return false;
                    }
                });
                break;
            case R.id.layout_food:
                final String[] food = getResources().getStringArray(R.array.preset_food);
                Integer[] foodIndexArray = null;
                List<Integer> foodIndexList = userData.getIndexFood();
                if (foodIndexList != null && foodIndexList.size() > 0) {
                    foodIndexArray = foodIndexList.toArray(new Integer[foodIndexList.size()]);
                }
                showMultiSelectDialog("食物", R.array.preset_food, foodIndexArray, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        List<Integer> indexs = Arrays.asList(which);
                        List<String> tags = new ArrayList<>();
                        for (Integer index : which) {
                            tags.add(food[index]);
                        }
                        userData.setTagFood(tags.size() > 0 ? tags : null);
                        userData.setIndexFood(indexs);
                        refreshMockData();
                        return false;
                    }
                });
                break;
            case R.id.layout_movie:
                final String[] movie = getResources().getStringArray(R.array.preset_movie);
                Integer[] movieIndexArray = null;
                List<Integer> movieIndexList = userData.getIndexMovie();
                if (movieIndexList != null && movieIndexList.size() > 0) {
                    movieIndexArray = movieIndexList.toArray(new Integer[movieIndexList.size()]);
                }
                showMultiSelectDialog("电影", R.array.preset_movie, movieIndexArray, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        List<Integer> indexs = Arrays.asList(which);
                        List<String> tags = new ArrayList<>();
                        for (Integer index : which) {
                            tags.add(movie[index]);
                        }
                        userData.setTagMovie(tags.size() > 0 ? tags : null);
                        userData.setIndexMovie(indexs);
                        refreshMockData();
                        return false;
                    }
                });
                break;
            case R.id.layout_book:
                final String[] book = getResources().getStringArray(R.array.preset_book);
                Integer[] bookIndexArray = null;
                List<Integer> bookIndexList = userData.getIndexBook();
                if (bookIndexList != null && bookIndexList.size() > 0) {
                    bookIndexArray = bookIndexList.toArray(new Integer[bookIndexList.size()]);
                }
                showMultiSelectDialog("书籍", R.array.preset_book, bookIndexArray, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        List<Integer> indexs = Arrays.asList(which);
                        List<String> tags = new ArrayList<>();
                        for (Integer index : which) {
                            tags.add(book[index]);
                        }
                        userData.setTagBook(tags.size() > 0 ? tags : null);
                        userData.setIndexBook(indexs);
                        refreshMockData();
                        return false;
                    }
                });
                break;
            case R.id.layout_travel:
                final String[] travel = getResources().getStringArray(R.array.preset_travel);
                Integer[] travelIndexArray = null;
                List<Integer> travelIndexList = userData.getIndexTravel();
                if (travelIndexList != null && travelIndexList.size() > 0) {
                    travelIndexArray = travelIndexList.toArray(new Integer[travelIndexList.size()]);
                }
                showMultiSelectDialog("旅行", R.array.preset_travel, travelIndexArray, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        List<Integer> indexs = Arrays.asList(which);
                        List<String> tags = new ArrayList<>();
                        for (Integer index : which) {
                            tags.add(travel[index]);
                        }
                        userData.setTagTravel(tags.size() > 0 ? tags : null);
                        userData.setIndexTravel(indexs);
                        refreshMockData();
                        return false;
                    }
                });
                break;
        }
    }
}
