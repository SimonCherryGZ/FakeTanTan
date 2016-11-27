package com.simoncherry.faketantan.activity;

import android.content.Context;
import android.content.Intent;
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
import android.os.Message;
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
import com.simoncherry.faketantan.adapter.ChatMsgNewAdapter;
import com.simoncherry.faketantan.bean.PhotoItem;
import com.simoncherry.faketantan.realm.ChatMessage;
import com.simoncherry.faketantan.realm.ChatMsgManager;
import com.simoncherry.faketantan.realm.MatchUser;
import com.simoncherry.faketantan.realm.MatchUserManager;
import com.simoncherry.faketantan.realm.Tutorial;
import com.simoncherry.faketantan.sp.UserData;
import com.simoncherry.faketantan.utils.BitmapUtil;
import com.simoncherry.faketantan.utils.GaussBlurUtil;
import com.simoncherry.faketantan.utils.TestUtil;
import com.simoncherry.faketantan.utils.TutorialUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.turing.androidsdk.InitListener;
import com.turing.androidsdk.SDKInit;
import com.turing.androidsdk.SDKInitBuilder;
import com.turing.androidsdk.TuringApiManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dreamtobe.kpswitch.util.KPSwitchConflictUtil;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchPanelFrameLayout;
import cn.dreamtobe.kpswitch.widget.KPSwitchRootLinearLayout;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import turing.os.http.core.ErrorMessage;
import turing.os.http.core.HttpConnectionListener;
import turing.os.http.core.RequestResult;

public class ChatNewActivity extends AppCompatActivity implements
        EmojiconGridFragment.OnEmojiconClickedListener,
        EmojiconsFragment.OnEmojiconBackspaceClickedListener {

    @BindView(R.id.chat_bar)
    RelativeLayout chatBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;

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
    @BindView(R.id.ptr_frame)
    PtrClassicFrameLayout ptrFrame;

    private Realm realm;
    private RealmChangeListener changeListener;
    private Context mContext;
    private ChatMsgNewAdapter mAdapter;
    private List<ChatMessage> mData;
    private ChatMsgNewAdapter.TutorialItemListener itemListener;

    private final String TAG = ChatNewActivity.class.getSimpleName();
    private int pageIndex = 1;
    private String conversation = "";
    private boolean isHelper = true;

    private TuringApiManager mTuringApiManager;
    private final String TURING_APIKEY = "ba43961a07e546ed987e2b57473a66dd";
    private final String TURING_SECRET = "cdb46ccaf70cd67d";
    private final String UNIQUEID = "0816203538";

    private final static int MSG_URL =  100;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_URL) {
                String url = msg.getData().getString("url");
                if (url != null && url.contains("http") && url.contains(".jpg")) {
                    showRobotResponse(url);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_new);
        ButterKnife.bind(this);
        mContext = ChatNewActivity.this;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            conversation = bundle.getString("conversation", "");
            isHelper = bundle.getBoolean("isHelper", true);
        }
        if (!isHelper) {
            initRobot();
        }

        initView();
        initKPSwitchPanel();
        initRecycleView();

        initRealm();
        initChatMsgFromRealm();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(changeListener);
        realm.close();
    }

    private void initView() {
        MatchUser user = MatchUserManager.getInstance().retrieveMatchUserByIdentify(conversation);
        if (user != null) {
            String name = user.getName();
            if (name != null) {
                tvNickname.setText(name);
            }
            String avatarUrl = user.getAvatarUrl();
            if (avatarUrl != null && !TextUtils.isEmpty(avatarUrl)) {
                Picasso.with(mContext).load(avatarUrl)
                        .fit().centerCrop()
                        .placeholder(R.drawable.img_defalut).error(R.drawable.img_defalut)
                        .into(ivAvatar);
                initBackGround(avatarUrl);
            } else {
                initBackGround();
            }
        } else {
            initBackGround();
        }

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

        ptrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                List<ChatMessage> data = ChatMsgManager.getInstance().retrieveChatMsgByPage(conversation, ++pageIndex);
                if (data == null || data.size() == 0) {
                    Logger.t(TAG).e("no more message can load");
                }
                onLoadChatMsgCallBack(data);
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

    private void initBackGround(String url) {
        if (URLUtil.isNetworkUrl(url)) {
            Picasso.with(mContext).load(url)
                    .resize(300, 300).centerCrop()
                    .placeholder(R.drawable.img_defalut)
                    .placeholder(R.drawable.img_defalut)
                    .into(mTarget);
        } else if (new File(url).exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(url);
            setBackground(bitmap);
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

        avatar = Bitmap.createBitmap(faceIconGreyBitmap, height / 4, 0, height / 2, height);
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
        rvChat.setAdapter(mAdapter = new ChatMsgNewAdapter(mContext, mData));

        itemListener = new ChatMsgNewAdapter.TutorialItemListener() {
            @Override
            public void onClick(int index, String tutorial) {
                showTutorialByIndex(index, tutorial);
            }
        };
        mAdapter.setTutorialListener(itemListener);
    }

    private void showDefaultTutorial() {
        ChatMessage chatMsgBean = new ChatMessage();
        chatMsgBean.setContent("在瘫瘫遇到问题？点击相应问题，马上得到解答~");
        chatMsgBean.setSend(false);
        chatMsgBean.setConversation(TestUtil.TEST_USER_HELPER);
        chatMsgBean.setTextFrom(TestUtil.TEST_USER_HELPER);
        chatMsgBean.setTextTo(TestUtil.TEST_USER_ME);
        chatMsgBean.setName(TestUtil.TEST_USER_HELPER);

        RealmList<Tutorial> tutorialList = new RealmList<>();
        String[] tutorialArray = TutorialUtil.getTutorialArray(0);
        for (int i=0; i<tutorialArray.length; i++) {
            Tutorial bean = new Tutorial();
            bean.setIndex(i+1);
            bean.setContent(tutorialArray[i]);
            tutorialList.add(bean);
        }
        if (tutorialList.size() > 0) {
            chatMsgBean.setTutorialList(tutorialList);
        }
        chatMsgBean.setTextTime(new Date());
        ChatMsgManager.getInstance().createChatMsg(chatMsgBean);

        mData.add(chatMsgBean);
        mAdapter.notifyDataSetChanged();
        if (mData != null && mData.size() > 0) {
            rvChat.smoothScrollToPosition(mData.size() - 1);  // 精华，加上这句就达到了软键盘把聊天界面往上推的效果
        }
    }

    private void showTutorialByIndex(int index, String question) {
        ChatMessage chatMsgBean = new ChatMessage();
        chatMsgBean.setSend(false);
        chatMsgBean.setConversation(TestUtil.TEST_USER_HELPER);
        chatMsgBean.setTextFrom(TestUtil.TEST_USER_HELPER);
        chatMsgBean.setTextTo(TestUtil.TEST_USER_ME);
        chatMsgBean.setName(TestUtil.TEST_USER_HELPER);

        String[] tutorialArray = TutorialUtil.getTutorialArray(index);
        if (tutorialArray.length > 1) {
            chatMsgBean.setContent(question);

            RealmList<Tutorial> tutorialList = new RealmList<>();
            for (int i=0; i<tutorialArray.length; i++) {
                Tutorial bean = new Tutorial();
                bean.setIndex(index*10 + i+1);
                bean.setContent(tutorialArray[i]);
                tutorialList.add(bean);
            }
            if (tutorialList.size() > 0) {
                chatMsgBean.setTutorialList(tutorialList);
            }
        } else {
            chatMsgBean.setContent(tutorialArray[0]);
        }
        chatMsgBean.setTextTime(new Date());
        ChatMsgManager.getInstance().createChatMsg(chatMsgBean);

        if (mData != null) {
            mData.add(chatMsgBean);
            mAdapter.notifyDataSetChanged();
            if (mData.size() > 0) {
                rvChat.smoothScrollToPosition(mData.size() - 1);  // 精华，加上这句就达到了软键盘把聊天界面往上推的效果
            }
        }
    }

    private void showRobotResponse(String response) {
        ChatMessage chatMsgBean = packageChatMsg(response, false);

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
        SDKInit.init(builder, new InitListener() {
            @Override
            public void onFail(String error) {
                Logger.t(TAG).e(error);
            }

            @Override
            public void onComplete() {
                // 获取userid成功后，才可以请求Turing服务器，需要请求必须在此回调成功，才可正确请求
                mTuringApiManager = new TuringApiManager(ChatNewActivity.this);
                mTuringApiManager.setHttpListener(myHttpConnectionListener);
            }
        });
    }

    HttpConnectionListener myHttpConnectionListener = new HttpConnectionListener() {

        @Override
        public void onSuccess(RequestResult result) {
            if (result != null) {
                try {
                    Logger.t(TAG).e(result.getContent().toString());
                    JSONObject result_obj = new JSONObject(result.getContent().toString());
                    if (result_obj.has("url")) {
                        Logger.t(TAG).e("getUrl: " + result_obj.get("url").toString());
                        //showRobotResponse(result_obj.get("url").toString());
                        //parseHtmlForImgUrl();
                        parseHtmlTask(result_obj.get("url").toString());

                    } else if (result_obj.has("text")) {
                        Logger.t(TAG).e(result_obj.get("text").toString());
                        showRobotResponse(result_obj.get("text").toString());
                    }

                } catch (JSONException e) {
                    Logger.t(TAG).e("JSONException:" + e.getMessage());
                }
            }
        }

        @Override
        public void onError(ErrorMessage errorMessage) {
            Logger.t(TAG).e(errorMessage.getMessage());
        }
    };

    private void parseHtmlTask(final String html) {
        new Thread(){
            @Override
            public void run() {
                Logger.t(TAG).e("ThreadName: " + Thread.currentThread().getName());
                parseHtmlForImgUrl(html);
            }
        }.start();
    }

    private void parseHtmlForImgUrl(String html) {
        try {
            Document doc = Jsoup.connect(html)
                    .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                    .timeout(6000).get();
            Logger.t(TAG).e("get doc: " + doc.toString());
            Elements scriptElements = doc.select("script");
            Logger.t(TAG).e("get scriptElements: " + scriptElements.toString());
            for (Element script : scriptElements) {
                for (DataNode node : script.dataNodes()) {
                    Logger.t(TAG).e("get node: " + node.getWholeData());
                    String wholeData = node.getWholeData();
                    if (wholeData != null && wholeData.contains("thumb")) {
                        String[] array = wholeData.split("thumb");
                        if (array.length > 0) {
//                            for (String str : array) {
//                                //Logger.t(TAG).e("get str: " + str);
//                                if (str.contains("http") && str.contains(".jpg")) {
//                                    int startIndex = str.indexOf("http");
//                                    int endIndex = str.indexOf(".jpg");
//                                    if (startIndex > -1 && endIndex > startIndex) {
//                                        String url = str.substring(startIndex, endIndex+4);
//                                        Logger.t(TAG).e("get url: " + url);
//                                        sendUrlToHandler(url);
//                                        break;
//                                    }
//                                }
//                            }
                            int size = array.length;
                            for (int i=0; i<size; i++) {
                                int index = new Random().nextInt(size);
                                String str = array[index];
                                if (str.contains("http") && str.contains(".jpg")) {
                                    int startIndex = str.indexOf("http");
                                    int endIndex = str.indexOf(".jpg");
                                    if (startIndex > -1 && endIndex > startIndex) {
                                        String url = str.substring(startIndex, endIndex+4);
                                        Logger.t(TAG).e("get url: " + url);
                                        sendUrlToHandler(url);
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            Logger.t(TAG).e(e.toString());
        }
    }

    private void sendUrlToHandler(String url) {
        Message message = new Message();
        message.what = MSG_URL;
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    private void initRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name(TestUtil.TEST_USER_ME)
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
        ChatMsgManager.initRealm(realm);
    }

    private void initChatMsgFromRealm() {
        List<ChatMessage> data = ChatMsgManager.getInstance().retrieveChatMsgByPage(conversation, 1);
        if (data != null && data.size() > 0) {
            onInitChatMsgCallBack(data);
        } else {
            Logger.t(TAG).e("loadChatMsg: null");
        }
    }

    private void onInitChatMsgCallBack(List<ChatMessage> data) {
        if (data != null && data.size() > 0) {
            Logger.t(TAG).e("loadChatMsg: " + data.toString());
            if (mData.size() > 0) {
                mData.clear();
            }
            mData.addAll(data);
            mAdapter.notifyDataSetChanged();
            if (mData != null && mData.size() > 0) {
                rvChat.smoothScrollToPosition(mData.size() - 1);  // 精华，加上这句就达到了软键盘把聊天界面往上推的效果
            }
        } else {
            Logger.t(TAG).e("loadChatMsg: null");
        }
    }

    private void onLoadChatMsgCallBack(List<ChatMessage> data) {
        if (data != null && data.size() > 0) {
            for (ChatMessage message : data) {
                mData.add(0, message);
            }
            mAdapter.notifyDataSetChanged();
        }
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrame.refreshComplete();
            }
        }, 500);
    }

    private ChatMessage packageChatMsg(String msg, boolean isSend) {
        ChatMessage chatMsgBean = new ChatMessage();
        chatMsgBean.setConversation(conversation);
        if (isSend) {
            chatMsgBean.setTextFrom(TestUtil.TEST_USER_ME);
            chatMsgBean.setTextTo(conversation);
            chatMsgBean.setName(TestUtil.TEST_USER_ME);
        } else {
            chatMsgBean.setTextFrom(conversation);
            chatMsgBean.setTextTo(TestUtil.TEST_USER_ME);
            chatMsgBean.setName(conversation);
        }
        chatMsgBean.setSend(isSend);
        chatMsgBean.setContent(msg);
        chatMsgBean.setTextTime(new Date());
        ChatMsgManager.getInstance().createChatMsg(chatMsgBean);
        return chatMsgBean;
    }

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
                    ChatMessage chatMsgBean = packageChatMsg(msg, true);
                    mData.add(chatMsgBean);
                    mAdapter.notifyDataSetChanged();
                    if (mData != null && mData.size() > 0) {
                        rvChat.smoothScrollToPosition(mData.size() - 1);  // 精华，加上这句就达到了软键盘把聊天界面往上推的效果
                    }

                    edtChat.setText("");
                    tvSend.setVisibility(View.GONE);
                    if (isHelper) {
                        showDefaultTutorial();
                    } else {
                        mTuringApiManager.requestTuringAPI(msg);
                    }
                }
                break;
            case R.id.btn_speak:
                break;
        }
    }
}
