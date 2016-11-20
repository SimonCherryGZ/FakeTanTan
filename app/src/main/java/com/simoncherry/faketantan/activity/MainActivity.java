package com.simoncherry.faketantan.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.dalong.sidemenu.SlidingMenu;
import com.dalong.sidemenu.lib.SlidingFragmentActivity;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.fragment.LeftFragment;
import com.simoncherry.faketantan.fragment.MainFragment;
import com.simoncherry.faketantan.fragment.RightFragment;
import com.simoncherry.faketantan.utils.StatusBarUtils;

public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener{

//    private LinearLayout globalTitle;
//    private RelativeLayout layoutTest;
//    private Button btnTest;

    private MainFragment mMainFragment;
    private SlidingMenu mSlidingMenu;
    private Fragment mContent;
    private View mStatusBar;
    private ImageView mLeftIcon,mRightIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        StatusBarUtils.initStatusBar(this, R.color.transparent);
        initSlidingMenu();
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
//        globalTitle = (LinearLayout) findViewById(R.id.layout_global_title);
//        layoutTest = (RelativeLayout) findViewById(R.id.layout_test);
//        btnTest = (Button) findViewById(R.id.btn_test);

        mStatusBar = (View)findViewById(R.id.status_bar);
        mRightIcon = (ImageView)findViewById(R.id.icon_slidingmenu_right);
        mLeftIcon = (ImageView)findViewById(R.id.icon_slidingmenu_left);
        ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) mStatusBar.getLayoutParams();
        linearParams.height = StatusBarUtils.getStatusBarHeight(this);
        mStatusBar.setLayoutParams(linearParams);
        StatusBarUtils.setStatusBarViewVisibility(mStatusBar);
        mLeftIcon.setOnClickListener(this);
        mRightIcon.setOnClickListener(this);
    }


    /**
     * 初始化菜单配置
     */
    private void initSlidingMenu() {
        mMainFragment=new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mMainFragment).commit();
        setBehindContentView(R.layout.sliding_left_layout);

        FragmentTransaction leftFragementTransaction = getSupportFragmentManager().beginTransaction();
        Fragment leftFragment = new LeftFragment();
        leftFragementTransaction.replace(R.id.main_left_fragment, leftFragment);
        leftFragementTransaction.commit();

        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);// 设置是左滑还是右滑，还是左右都可以滑，我这里只做了左滑
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset_left);// 设置左边菜单宽度
        mSlidingMenu.setRightMenuOffsetRes(R.dimen.slidingmenu_offset_right);// 设置右边菜单宽度
        mSlidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//设置手势模式  // TODO ? 这里原来是TOUCHMODE_FULLSCREEN，但是右滑打开左侧不太灵敏
        mSlidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置手势模式
        mSlidingMenu.setShadowDrawable(R.color.transparent);// 设置左菜单阴影图片
        mSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
        mSlidingMenu.setBehindScrollScale(0.25f);// 设置滑动时拖拽效果
        mSlidingMenu.setSecondaryMenu(R.layout.sliding_right_layout);

        FragmentTransaction rightFragementTransaction = getSupportFragmentManager().beginTransaction();
        Fragment rightFragment = new RightFragment();
        rightFragementTransaction.replace(R.id.main_right_fragment, rightFragment);
        rightFragementTransaction.commit();


        mSlidingMenu.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                //setSlidingMenuIconAlpha(percentOpen, mRightIcon);
            }
        });

        mSlidingMenu.setAboveCanvasTransformer(new SlidingMenu.CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                //setSlidingMenuIconAlpha(percentOpen, mLeftIcon);
            }
        });
    }


    /**
     *  左侧菜单点击切换首页的内容
     */
    public void switchContent(Fragment fragment) {
        mContent = fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        getSlidingMenu().showContent();
    }

    public void showSecondaryMenu() {
        mSlidingMenu.showSecondaryMenu(true);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.icon_slidingmenu_right:
                mSlidingMenu.showSecondaryMenu(true);
                break;
            case R.id.icon_slidingmenu_left:
                mSlidingMenu.toggle();
                break;
        }
    }


    /**
     * 侧边栏滑动时通知
     * @param percentOpen
     *
     */
    public void setSlidingMenuIconAlpha(float percentOpen,ImageView mIcon) {
        if(mIcon == null) {
            return;
        }
        float alphaPoint = 1 - percentOpen;
        if(alphaPoint < 0.0f) {
            alphaPoint = 0.0f;
        } else if(alphaPoint > 1.0f) {
            alphaPoint = 1.0f;
        }
        int alpha = (int) (alphaPoint * 255);
        mIcon.getBackground().setAlpha(alpha);
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                if(mSlidingMenu.isMenuShowing())
//                    break;
//            default:
//                break;
//
//        }
//        return super.onKeyDown(keyCode, event);
//
//    }

    public void addIgnoredView(View addIgnoredView){
        mSlidingMenu.addIgnoredView(addIgnoredView);
    }
    public void removeIgnoredView(View addIgnoredView){
        mSlidingMenu.removeIgnoredView(addIgnoredView);
    }
}
