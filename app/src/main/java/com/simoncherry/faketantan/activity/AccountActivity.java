package com.simoncherry.faketantan.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baoyz.treasure.Treasure;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.sp.JsonConverterFactory;
import com.simoncherry.faketantan.sp.UserData;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.icon_slidingmenu_left)
    ImageView iconSlidingmenuLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.icon_slidingmenu_right)
    ImageView iconSlidingmenuRight;
    @BindView(R.id.tv_title_sex)
    TextView tvTitleSex;
    @BindView(R.id.tv_content_sex)
    TextView tvContentSex;
    @BindView(R.id.layout_sex)
    RelativeLayout layoutSex;
    @BindView(R.id.tv_title_nickname)
    TextView tvTitleNickname;
    @BindView(R.id.tv_content_nickname)
    TextView tvContentNickname;
    @BindView(R.id.layout_nickname)
    RelativeLayout layoutNickname;
    @BindView(R.id.tv_title_birthday)
    TextView tvTitleBirthday;
    @BindView(R.id.tv_content_birthday)
    TextView tvContentBirthday;
    @BindView(R.id.layout_birthday)
    RelativeLayout layoutBirthday;
    @BindView(R.id.tv_title_phone)
    TextView tvTitlePhone;
    @BindView(R.id.tv_content_phone)
    TextView tvContentPhone;
    @BindView(R.id.layout_phone)
    RelativeLayout layoutPhone;
    @BindView(R.id.tv_title_password)
    TextView tvTitlePassword;
    @BindView(R.id.tv_content_password)
    TextView tvContentPassword;
    @BindView(R.id.layout_password)
    RelativeLayout layoutPassword;
    @BindView(R.id.layout_option)
    RelativeLayout layoutOption;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.activity_account)
    RelativeLayout activityAccount;

    private Context mContext;
    private UserData userData;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        mContext = AccountActivity.this;

        initView();
        initDatePicker();
    }

    private void initView() {
        iconSlidingmenuLeft.setImageResource(R.drawable.title_left_back_white);
        tvTitleMiddle.setText("账户信息");
        iconSlidingmenuRight.setImageResource(0);

        loadMockData();
    }

    private void loadMockData() {
        Treasure.setConverterFactory(new JsonConverterFactory());
        userData = Treasure.get(mContext, UserData.class);
        if (userData.getSex() != null) {
            String sex = userData.getSex();
            if (sex.equals("男") || sex.equals("女")) {
                tvContentSex.setText(sex);
            } else {
                tvContentSex.setText("null");
            }
        } else {
            tvContentSex.setText("null");
        }
        tvContentNickname.setText(userData.getNickName() != null ? userData.getNickName() : "null");
        tvContentBirthday.setText(userData.getBirthday() != null ? userData.getBirthday() : "null");
        tvContentPhone.setText(userData.getPhone() != null ? userData.getPhone() : "null");

    }

    public void initDatePicker() {
        Calendar now = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(
                AccountActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setAccentColor(mContext.getResources().getColor(R.color.colorPrimary));
        datePickerDialog.setTitle("选择出生日期");
        datePickerDialog.setOkText("确定");
        datePickerDialog.setCancelText("取消");
        datePickerDialog.setMaxDate(now);
        datePickerDialog.showYearPickerFirst(true);
    }

    private void showEditDialog(@Nullable String title, View view, MaterialDialog.SingleButtonCallback callback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        if (title != null) {
            builder.title(title);
        }
        builder.positiveColor(getResources().getColor(R.color.colorPrimary));
        builder.negativeColor(getResources().getColor(R.color.colorPrimary));
        builder.positiveText("确定");
        builder.negativeText("取消");
        builder.customView(view, true);
        builder.onPositive(callback);
        builder.show();
    }

    private void showSingleSelectDialog(String title, @ArrayRes int itemsRes, int preselect, MaterialDialog.ListCallbackSingleChoice callback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        builder.title(title);
        builder.positiveColor(getResources().getColor(R.color.colorPrimary));
        builder.negativeColor(getResources().getColor(R.color.colorPrimary));
        builder.positiveText("确定");
        builder.negativeText("取消");
        builder.items(itemsRes);
        builder.itemsCallbackSingleChoice(preselect, callback);  // 第一个参数是默认选择哪一项
        builder.itemsGravity(GravityEnum.END);
        builder.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String dateString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " 00:00:00";
        tvContentBirthday.setText(dateString);
        userData.setBirthday(dateString);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @OnClick({R.id.icon_slidingmenu_left, R.id.layout_sex, R.id.layout_nickname, R.id.layout_birthday, R.id.layout_phone, R.id.layout_password})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_slidingmenu_left:
                onBackPressed();
                break;
            case R.id.layout_sex:
                int preselect = 0;
                String sex = userData.getSex();
                if (sex != null && sex.equals("女")) {
                    preselect = 1;
                }
                showSingleSelectDialog("性别", R.array.sex_select, preselect, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        if (which == 0) {
                            tvContentSex.setText("男");
                            userData.setSex("男");
                        } else {
                            tvContentSex.setText("女");
                            userData.setSex("女");
                        }
                        return false;
                    }
                });
                break;
            case R.id.layout_nickname:
                final EditText editText = new EditText(this);
                editText.setBackground(null);
                try {
                    // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
                    Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
                    f.setAccessible(true);
                    f.set(editText, R.drawable.edit_cursor_color);
                } catch (Exception ignored) {
                }
                editText.setText(tvContentNickname.getText().toString());
                editText.setSelection(editText.getText().toString().length());
                showEditDialog(null, editText, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //Toast.makeText(mContext, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                        String str = editText.getText().toString();
                        userData.setNickName(!TextUtils.isEmpty(str) ? str : null);
                        tvContentNickname.setText(str);
                    }
                });
                break;
            case R.id.layout_birthday:
                if (datePickerDialog != null) {
                    datePickerDialog.show(getFragmentManager(), "datePickerDialog");
                }
                break;
            case R.id.layout_phone:
                break;
            case R.id.layout_password:
                break;
        }
    }
}
