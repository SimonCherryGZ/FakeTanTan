package com.simoncherry.faketantan.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.orhanobut.logger.Logger;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.adapter.ContactAdapter;
import com.simoncherry.faketantan.bean.ContactBean;
import com.simoncherry.faketantan.custom.QuickAlphabeticBar;
import com.simoncherry.faketantan.utils.RegUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    final static String TAG = "ContactActivity";
    final static int MSG_CONTACT = 1024;

    private ListView lvContact;
    private List<ContactBean> data = new ArrayList<>();
    private ContactAdapter adapter;
    private QuickAlphabeticBar alphabeticBar;

    private Context mContext;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_CONTACT) {
                Logger.t(TAG).e("load contact");
                updateList();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mContext = ContactActivity.this;
        lvContact = (ListView) findViewById(R.id.lv_contact);
        alphabeticBar = (QuickAlphabeticBar) findViewById(R.id.alpha_bar);

        //initList();
        loadContact();
    }

    private void initList() {
        adapter = new ContactAdapter(mContext, data, alphabeticBar);
        lvContact.setAdapter(adapter);
        alphabeticBar.init(ContactActivity.this);
        alphabeticBar.setListView(lvContact);
        alphabeticBar.setHeight(alphabeticBar.getHeight());
        alphabeticBar.setVisibility(View.VISIBLE);
    }

    private void loadContact() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                List<ContactBean> list = loadContactsFromPhone(mContext);
                if (list != null && list.size() > 0) {
                    data.addAll(list);
                }
                handler.sendEmptyMessage(MSG_CONTACT);
            }
        });
    }

    private void updateList() {
        if (data != null && data.size() > 0) {
            //adapter.notifyDataSetChanged();
            initList();
        }
    }

    public static List<ContactBean> loadContactsFromPhone(Context context){
        List<ContactBean> listContacts = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        long timeStart = new Date().getTime();
        String[] mContactsProjection = new String[] {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.SORT_KEY_PRIMARY
        };

        //如果android操作系统版本4.4或4.4以上就要用phonebook_label而不是sort_key字段
        if(android.os.Build.VERSION.SDK_INT >= 19){
            mContactsProjection[3] = "phonebook_label";
        }

        //查询contacts表中的所有数据
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                mContactsProjection, null, null,
                android.provider.ContactsContract.Contacts.SORT_KEY_PRIMARY);

        if(cursor != null && cursor.getCount() > 0){
            String lastName = "";
            while (cursor.moveToNext()){
                String contactsId = cursor.getString(0);
                String phoneNum = cursor.getString(1);
                String name = cursor.getString(2);
                String sortKey = cursor.getString(3);

                if (!name.equals(lastName)) {
                    // 对手机号码进行预处理（去掉号码前的+86、首尾空格、“-”号等）
                    phoneNum = phoneNum.replaceAll("^(\\+86)", "");
                    phoneNum = phoneNum.replaceAll("^(86)", "");
                    phoneNum = phoneNum.replaceAll("-", "");
                    phoneNum = phoneNum.replaceAll(" ", "");
                    phoneNum = phoneNum.trim();
                    // 如果当前号码是手机号码
                    if (RegUtil.PhoneNumberCheck(phoneNum)) {
                        ContactBean user = new ContactBean();
                        user.setContactId(contactsId);
                        user.setName(name);
                        user.setPhone(phoneNum);
                        user.setPinyin(Pinyin.toPinyin(name.charAt(0)));
                        user.setSortKey(sortKey);
                        listContacts.add(user);
                    }
                }
                lastName = name;
            }
            cursor.close();
        }
        Logger.e(TAG + "time used " + (new Date().getTime() - timeStart) + " ms");
        return listContacts;
    }

}
