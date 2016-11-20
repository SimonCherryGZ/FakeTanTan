package com.simoncherry.faketantan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.activity.ChatActivity;
import com.simoncherry.faketantan.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 左边界面
 */
public class RightTwoFragment extends Fragment {

    @BindView(R.id.tv_right_page)
    TextView tvRightPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right_two_layout, container, false);
        ButterKnife.bind(this, view);

        tvRightPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    /**
     * 点击左侧菜单中切换中间主界面的fragment
     *
     * @param fragment
     */
    private void switchFragment(Fragment fragment) {
        if (getActivity() == null)
            return;
        MainActivity ra = (MainActivity) getActivity();
        ra.switchContent(fragment);
    }
}
