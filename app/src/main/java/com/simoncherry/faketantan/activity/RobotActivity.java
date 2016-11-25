package com.simoncherry.faketantan.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.treasure.Treasure;
import com.orhanobut.logger.Logger;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.adapter.ChatMsgAdapter;
import com.simoncherry.faketantan.bean.ChatMsgBean;
import com.simoncherry.faketantan.bean.PhotoItem;
import com.simoncherry.faketantan.bean.TutorialBean;
import com.simoncherry.faketantan.sp.UserData;
import com.simoncherry.faketantan.utils.BitmapUtil;
import com.simoncherry.faketantan.utils.GaussBlurUtil;
import com.simoncherry.faketantan.utils.TutorialUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.turing.androidsdk.InitListener;
import com.turing.androidsdk.SDKInit;
import com.turing.androidsdk.SDKInitBuilder;
import com.turing.androidsdk.TuringApiManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dreamtobe.kpswitch.util.KPSwitchConflictUtil;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchPanelFrameLayout;
import cn.dreamtobe.kpswitch.widget.KPSwitchRootLinearLayout;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;
import turing.os.http.core.ErrorMessage;
import turing.os.http.core.HttpConnectionListener;
import turing.os.http.core.RequestResult;

public class RobotActivity extends AppCompatActivity implements
        EmojiconGridFragment.OnEmojiconClickedListener,
        EmojiconsFragment.OnEmojiconBackspaceClickedListener{

    @BindView(R.id.chat_bar)
    RelativeLayout chatBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.rv_chat)
    RecyclerView rvChat;
    @BindView(R.id.iv_plus)
    ImageView ivPlus;
    @BindView(R.id.iv_emoji)
    ImageView ivEmoji;
    @BindView(R.id.edt_chat)
    EmojiconEditText edtChat;
    @BindView(R.id.btn_speak)
    Button btnSpeak;
    @BindView(R.id.iv_recorder)
    ImageView ivRecorder;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.iv_chat1)
    ImageView ivChat1;
    @BindView(R.id.iv_chat2)
    ImageView ivChat2;
    @BindView(R.id.iv_gallery)
    ImageView ivGallery;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.iv_marker)
    ImageView ivMarker;
    @BindView(R.id.layout_menu)
    LinearLayout layoutMenu;
    @BindView(R.id.layout_emoji)
    FrameLayout layoutEmoji;
    @BindView(R.id.layout_panel)
    KPSwitchPanelFrameLayout layoutPanel;
    @BindView(R.id.layout_chat_root)
    KPSwitchRootLinearLayout layoutRoot;

    private Context mContext;
    private ChatMsgAdapter mAdapter;
    private List<ChatMsgBean> mData;
    private ChatMsgAdapter.TutorialItemListener itemListener;

    private final String TAG = RobotActivity.class.getSimpleName();
    private TuringApiManager mTuringApiManager;
    private final String TURING_APIKEY = "ba43961a07e546ed987e2b57473a66dd";
    private final String TURING_SECRET = "cdb46ccaf70cd67d";
    private final String UNIQUEID = "0816203538";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
        ButterKnife.bind(this);
        mContext = RobotActivity.this;

        initView();
        initBackGround();
        initKPSwitchPanel();
        initRecycleView();
        initRobot();
    }

    private void initView() {
        rvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    KPSwitchConflictUtil.hidePanelAndKeyboard(layoutPanel);
                }
                return false;
            }
        });

        edtChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(edtChat.getText())) {
                    ivRecorder.setVisibility(View.VISIBLE);
                    tvSend.setVisibility(View.GONE);
                } else {
                    ivRecorder.setVisibility(View.INVISIBLE);
                    tvSend.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    float[] array = {
            0.7f, 0, 0, 0, 100,
            0, 0.7f, 0, 0, 100,
            0, 0, 0.7f, 0, 100,
            0, 0, 0, 1, 0
    };

    private void initBackGround() {
        UserData userData = Treasure.get(mContext, UserData.class);
        List<PhotoItem> photoItemList = userData.getPhotoItem();
        if (photoItemList != null && photoItemList.size() > 0) {
            String path = photoItemList.get(0).hyperlink;
            if (URLUtil.isNetworkUrl(path)) {
                Picasso.with(mContext).load(path)
                        .resize(300, 300).centerCrop()
                        .placeholder(R.drawable.img_defalut)
                        .placeholder(R.drawable.img_defalut)
                        .into(mTarget);
            } else if (new File(path).exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                setBackground(bitmap);
            }
        }
    }

    Target mTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            setBackground(bitmap);
        }
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }
        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    private void setBackground(Bitmap avatar) {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Logger.e("get layoutRoot width: " + String.valueOf(width));
        Logger.e("get layoutRoot height: " + String.valueOf(height));

        avatar = BitmapUtil.scaleBitmap(BitmapUtil.cropBitmap(avatar), height, height);
        Bitmap faceIconGreyBitmap = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(faceIconGreyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(array);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(avatar, 0, 0, paint);

        avatar = Bitmap.createBitmap(faceIconGreyBitmap, height/4, 0, height/2, height);
        avatar = GaussBlurUtil.toBlur(avatar, 4);
        layoutRoot.setBackground(new BitmapDrawable(avatar));
    }

    private void initKPSwitchPanel() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_emoji, EmojiconsFragment.newInstance(false))
                .commit();

        KeyboardUtil.attach(this, layoutPanel,
                new KeyboardUtil.OnKeyboardShowingListener() {
                    @Override
                    public void onKeyboardShowing(boolean isShowing) {
                        if (isShowing) {
                            ivPlus.setImageResource(R.drawable.ic_plus);
                            ivEmoji.setImageResource(R.drawable.ic_emoji);
                            ivRecorder.setImageResource(R.drawable.ic_recoder);
                        }
                        if (mData != null && mData.size() > 0) {
                            rvChat.smoothScrollToPosition(mData.size() - 1);  // 精华，加上这句就达到了软键盘把聊天界面往上推的效果
                        }
                    }
                });

        KPSwitchConflictUtil.attach(layoutPanel, edtChat,
                new KPSwitchConflictUtil.SwitchClickListener() {
                    @Override
                    public void onClickSwitch(boolean switchToPanel) {
                        if (switchToPanel) {
                            edtChat.clearFocus();
                            edtChat.setVisibility(View.VISIBLE);
                            btnSpeak.setVisibility(View.GONE);
                            ivRecorder.setImageResource(R.drawable.ic_recoder);
                        } else {
                            edtChat.requestFocus();
                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mData != null && mData.size() > 0) {
                                    rvChat.smoothScrollToPosition(mData.size() - 1);  // 精华，加上这句就达到了软键盘把聊天界面往上推的效果
                                }
                            }
                        }, 100);
                    }
                },
                new KPSwitchConflictUtil.SubPanelAndTrigger(layoutMenu, ivPlus),
                new KPSwitchConflictUtil.SubPanelAndTrigger(layoutEmoji, ivEmoji)
        );
    }

    private void initRecycleView() {
        mData = new ArrayList<>();
        rvChat.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvChat.setAdapter(mAdapter = new ChatMsgAdapter(mContext, mData));

        itemListener = new ChatMsgAdapter.TutorialItemListener() {
            @Override
            public void onClick(int index, String tutorial) {
                ChatMsgBean chatMsgBean = new ChatMsgBean();
                chatMsgBean.setContent(tutorial);
                chatMsgBean.setSend(true);
                mData.add(chatMsgBean);
                mAdapter.notifyDataSetChanged();
                if (mData != null && mData.size() > 0) {
                    rvChat.smoothScrollToPosition(mData.size() - 1);  // 精华，加上这句就达到了软键盘把聊天界面往上推的效果
                }

                //addDefaultTutorial();
                showTutorial(index, tutorial);
            }
        };
        mAdapter.setTutorialListener(itemListener);
    }

    private void addDefaultTutorial() {
        ChatMsgBean chatMsgBean = new ChatMsgBean();
        chatMsgBean.setContent("在瘫瘫遇到问题？点击相应问题，马上得到解答~");
        chatMsgBean.setSend(false);

        List<TutorialBean> tutorialList = new ArrayList<>();
        String[] tutorialArray = TutorialUtil.getTutorialArray(0);
        for (int i=0; i<tutorialArray.length; i++) {
            TutorialBean bean = new TutorialBean();
            bean.setId(i+1);
            bean.setContent(tutorialArray[i]);
            tutorialList.add(bean);
        }
        if (tutorialList.size() > 0) {
            chatMsgBean.setTutorialList(tutorialList);
        }

        mData.add(chatMsgBean);
        mAdapter.notifyDataSetChanged();
        if (mData != null && mData.size() > 0) {
            rvChat.smoothScrollToPosition(mData.size() - 1);  // 精华，加上这句就达到了软键盘把聊天界面往上推的效果
        }
    }

    private void showTutorial(int index, String question) {
        ChatMsgBean chatMsgBean = new ChatMsgBean();
        chatMsgBean.setSend(false);

        String[] tutorialArray = TutorialUtil.getTutorialArray(index);
        if (tutorialArray.length > 1) {
            chatMsgBean.setContent(question);

            List<TutorialBean> tutorialList = new ArrayList<>();
            for (int i=0; i<tutorialArray.length; i++) {
                TutorialBean bean = new TutorialBean();
                bean.setId(index*10 + i+1);
                bean.setContent(tutorialArray[i]);
                tutorialList.add(bean);
            }
            if (tutorialList.size() > 0) {
                chatMsgBean.setTutorialList(tutorialList);
            }
        } else {
            chatMsgBean.setContent(tutorialArray[0]);
        }

        if (mData != null) {
            mData.add(chatMsgBean);
            mAdapter.notifyDataSetChanged();
            if (mData.size() > 0) {
                rvChat.smoothScrollToPosition(mData.size() - 1);  // 精华，加上这句就达到了软键盘把聊天界面往上推的效果
            }
        }
    }

    private void showRobotResponse(String response) {
        ChatMsgBean chatMsgBean = new ChatMsgBean();
        chatMsgBean.setSend(false);
        chatMsgBean.setContent(response);

        if (mData != null) {
            mData.add(chatMsgBean);
            mAdapter.notifyDataSetChanged();
            if (mData.size() > 0) {
                rvChat.smoothScrollToPosition(mData.size() - 1);  // 精华，加上这句就达到了软键盘把聊天界面往上推的效果
            }
        }
    }

    private void initRobot() {
        SDKInitBuilder builder = new SDKInitBuilder(this)
                .setSecret(TURING_SECRET).setTuringKey(TURING_APIKEY).setUniqueId(UNIQUEID);
        SDKInit.init(builder,new InitListener() {
            @Override
            public void onFail(String error) {
                Logger.d(TAG, error);
            }
            @Override
            public void onComplete() {
                // 获取userid成功后，才可以请求Turing服务器，需要请求必须在此回调成功，才可正确请求
                mTuringApiManager = new TuringApiManager(RobotActivity.this);
                mTuringApiManager.setHttpListener(myHttpConnectionListener);
            }
        });
    }

    HttpConnectionListener myHttpConnectionListener = new HttpConnectionListener() {

        @Override
        public void onSuccess(RequestResult result) {
            if (result != null) {
                try {
                    Logger.d(TAG, result.getContent().toString());
                    JSONObject result_obj = new JSONObject(result.getContent().toString());
                    if (result_obj.has("text")) {
                        Logger.d(TAG, result_obj.get("text").toString());
                        showRobotResponse(result_obj.get("text").toString());
                    }
                } catch (JSONException e) {
                    Logger.d(TAG, "JSONException:" + e.getMessage());
                }
            }
        }

        @Override
        public void onError(ErrorMessage errorMessage) {
            Logger.d(TAG, errorMessage.getMessage());
        }
    };

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(edtChat, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(edtChat);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP &&
                event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (layoutPanel.getVisibility() == View.VISIBLE) {
                KPSwitchConflictUtil.hidePanelAndKeyboard(layoutPanel);
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }


    @OnClick({R.id.iv_back, R.id.iv_plus, R.id.iv_emoji, R.id.iv_recorder, R.id.tv_send, R.id.btn_speak})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.iv_plus:
                ivPlus.setImageResource(R.drawable.ic_keyboard);
                break;
            case R.id.iv_emoji:
                ivEmoji.setImageResource(R.drawable.ic_keyboard);
                break;
            case R.id.iv_recorder:
                KeyboardUtil.hideKeyboard(edtChat);
                KPSwitchConflictUtil.hidePanelAndKeyboard(layoutPanel);

                if (edtChat.getVisibility() == View.VISIBLE) {
                    ivRecorder.setImageResource(R.drawable.ic_keyboard);
                    edtChat.setVisibility(View.GONE);
                    btnSpeak.setVisibility(View.VISIBLE);
                } else {
                    ivRecorder.setImageResource(R.drawable.ic_recoder);
                    edtChat.setVisibility(View.VISIBLE);
                    edtChat.requestFocus();
                    btnSpeak.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_send:
                String msg = edtChat.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    ChatMsgBean chatMsgBean = new ChatMsgBean();
                    chatMsgBean.setContent(msg);
                    chatMsgBean.setSend(true);
                    mData.add(chatMsgBean);
                    mAdapter.notifyDataSetChanged();
                    if (mData != null && mData.size() > 0) {
                        rvChat.smoothScrollToPosition(mData.size() - 1);  // 精华，加上这句就达到了软键盘把聊天界面往上推的效果
                    }

                    edtChat.setText("");
                    tvSend.setVisibility(View.GONE);

                    mTuringApiManager.requestTuringAPI(msg);
                }

                //addDefaultTutorial();
                break;
            case R.id.btn_speak:
                break;
        }
    }
}
