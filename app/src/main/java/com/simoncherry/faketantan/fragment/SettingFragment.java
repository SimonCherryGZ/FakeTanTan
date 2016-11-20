package com.simoncherry.faketantan.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.activity.AccountActivity;
import com.simoncherry.faketantan.activity.ChatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.tv_title_sex)
    TextView tvTitleSex;
    @BindView(R.id.tv_content_sex)
    TextView tvContentSex;
    @BindView(R.id.layout_show_sex)
    RelativeLayout layoutShowSex;
    @BindView(R.id.tv_title_age)
    TextView tvTitleAge;
    @BindView(R.id.tv_content_age)
    TextView tvContentAge;
    @BindView(R.id.iv_young)
    ImageView ivYoung;
    @BindView(R.id.iv_old)
    ImageView ivOld;
    @BindView(R.id.layout_show_age)
    RelativeLayout layoutShowAge;
    @BindView(R.id.range_bar_age)
    RangeBar rangeBarAge;
    @BindView(R.id.tv_title_distance)
    TextView tvTitleDistance;
    @BindView(R.id.tv_content_distance)
    TextView tvContentDistance;
    @BindView(R.id.iv_near)
    ImageView ivNear;
    @BindView(R.id.iv_far)
    ImageView ivFar;
    @BindView(R.id.range_bar_distance)
    RangeBar rangeBarDistance;
    @BindView(R.id.layout_show_distance)
    RelativeLayout layoutShowDistance;
    @BindView(R.id.view_divider_1)
    View viewDivider1;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.iv_account)
    ImageView ivAccount;
    @BindView(R.id.tv_title_account)
    TextView tvTitleAccount;
    @BindView(R.id.tv_content_account)
    TextView tvContentAccount;
    @BindView(R.id.layout_account)
    RelativeLayout layoutAccount;
    @BindView(R.id.iv_privacy)
    ImageView ivPrivacy;
    @BindView(R.id.tv_title_privacy)
    TextView tvTitlePrivacy;
    @BindView(R.id.tv_content_privacy)
    TextView tvContentPrivacy;
    @BindView(R.id.layout_privacy)
    RelativeLayout layoutPrivacy;
    @BindView(R.id.iv_data)
    ImageView ivData;
    @BindView(R.id.tv_title_data)
    TextView tvTitleData;
    @BindView(R.id.tv_content_data)
    TextView tvContentData;
    @BindView(R.id.layout_data)
    RelativeLayout layoutData;
    @BindView(R.id.view_divider_2)
    View viewDivider2;
    @BindView(R.id.iv_report)
    ImageView ivReport;
    @BindView(R.id.tv_title_report)
    TextView tvTitleReport;
    @BindView(R.id.tv_content_report)
    TextView tvContentReport;
    @BindView(R.id.layout_report)
    RelativeLayout layoutReport;
    @BindView(R.id.btn_quit)
    Button btnQuit;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        rangeBarAge.setPinRadius(0);
        rangeBarAge.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                String age = leftPinValue + " - " + rightPinValue;
                if (rightPinValue.equals("50")) {
                    age += "+";
                }
                tvContentAge.setText(age);
            }
        });

        rangeBarDistance.setPinRadius(0);
        rangeBarDistance.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                String distance = rightPinValue + "km";
                if (rightPinValue.equals("100")) {
                    distance += "+";
                }
                tvContentDistance.setText(distance);
            }
        });
    }

    @OnClick({R.id.layout_show_sex, R.id.layout_account, R.id.layout_privacy, R.id.layout_data, R.id.layout_report})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_show_sex:
                break;
            case R.id.layout_account:
                Intent account = new Intent(getActivity(), AccountActivity.class);
                startActivity(account);
                break;
            case R.id.layout_data:
                break;
            case R.id.layout_report:
                Intent report = new Intent(getActivity(), ChatActivity.class);
                startActivity(report);
                break;
        }
    }
}
