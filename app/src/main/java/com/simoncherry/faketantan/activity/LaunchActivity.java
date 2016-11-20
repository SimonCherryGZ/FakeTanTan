package com.simoncherry.faketantan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.custom.RadarScanView;
import com.simoncherry.faketantan.utils.ChannelUtil;

public class LaunchActivity extends AppCompatActivity {

    private RadarScanView radarScanView;
    private Button btnRegister;
    private TextView tvChannel;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        mContext = LaunchActivity.this;

        radarScanView = (RadarScanView) findViewById(R.id.radar_view);
        btnRegister = (Button) findViewById(R.id.btn_register);
        tvChannel = (TextView) findViewById(R.id.tv_channel);

        initView();
        getChannel();
    }

    private void initView() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radarScanView.stopAnim();
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getChannel() {
        String channel = "渠道标识： " + ChannelUtil.getChannel(mContext, "null");
        tvChannel.setText(channel);
    }
}
