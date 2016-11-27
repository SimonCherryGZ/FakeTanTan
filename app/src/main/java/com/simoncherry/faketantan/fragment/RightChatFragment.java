package com.simoncherry.faketantan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.activity.MainActivity;
import com.simoncherry.faketantan.adapter.MatchUserAdapter;
import com.simoncherry.faketantan.realm.MatchUser;
import com.simoncherry.faketantan.realm.MatchUserManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;


/**
 * 左边界面
 */
public class RightChatFragment extends Fragment {

    private final static String TAG = RightChatFragment.class.getSimpleName();
    private final static String TEST_USER_1 = "LaoGe";
    private final static String TEST_USER_2 = "Helper";
    private final static String TEST_USER_3 = "Robot";

    @BindView(R.id.rv_match_user)
    RecyclerView rvMatchUser;

    private Context mContext;
    private MatchUserAdapter mAdapter;
    private List<MatchUser> mData;

    private Realm realm;
    private RealmChangeListener changeListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right_chat_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initRealm();
        initView();
        initRecyclerView();
        initDefaultUser();
        loadMatchUser();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(changeListener);
        realm.close();
    }

    private void initRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name(TEST_USER_1)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfig);
        changeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                mAdapter.notifyDataSetChanged();
            }
        };
        realm.addChangeListener(changeListener);
        MatchUserManager.initRealm(realm);
    }

    private void initView() {
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        rvMatchUser.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvMatchUser.setAdapter(mAdapter = new MatchUserAdapter(mContext, mData));
    }

    private void initDefaultUser() {
        List<MatchUser> data = MatchUserManager.getInstance().retrieveMatchUsers();
        if (data == null || data.size() == 0) {
            MatchUser helper = new MatchUser();
            helper.setName("瘫瘫小助手");
            helper.setIdentify(TEST_USER_2);
            helper.setAvatarUrl("http://ohaim99jn.bkt.clouddn.com/fox.png");
            helper.setWord("在瘫瘫遇到问题？");
            helper.setHelper(true);
            helper.setMatchTime(new Date());
            MatchUserManager.getInstance().createMatchUser(helper);

            MatchUser robot = new MatchUser();
            robot.setName("聊天机器人");
            robot.setIdentify(TEST_USER_3);
            robot.setAvatarUrl("http://ohaim99jn.bkt.clouddn.com/paruru1.jpg");
            robot.setWord("啦啦啦");
            robot.setHelper(false);
            robot.setMatchTime(new Date());
            MatchUserManager.getInstance().createMatchUser(robot);
        }
    }

    private void loadMatchUser() {
        List<MatchUser> data = MatchUserManager.getInstance().retrieveMatchUsers();
        onLoadMatchUserCallBack(data);
    }

    private void onLoadMatchUserCallBack(List<MatchUser> data) {
        if (data != null && data.size() > 0) {
            if (mData.size() > 0) {
                mData.clear();
            }
            mData.addAll(data);
            mAdapter.notifyDataSetChanged();
        } else {
            Logger.t(TAG).e("load match user null");
        }
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
