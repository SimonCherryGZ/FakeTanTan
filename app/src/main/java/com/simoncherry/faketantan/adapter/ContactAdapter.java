package com.simoncherry.faketantan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.bean.ContactBean;
import com.simoncherry.faketantan.custom.QuickAlphabeticBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Simon on 2016/10/16.
 */

public class ContactAdapter extends BaseAdapter {

    private Context mContext;
    private List<ContactBean> mData;
    private HashMap<String, Integer> alphaIndexer;
    private String[] sections;


    public ContactAdapter(Context context, List<ContactBean> data, QuickAlphabeticBar alphabeticBar) {
        mContext = context;
        mData = data;
        alphaIndexer = new HashMap<>();

        for (int i =0; i <data.size(); i++) {
            String name = getAlpha(String.valueOf(data.get(i).getSortKey().charAt(0)));
            //String name = getAlpha(tutorialData.get(i).getPinyin());
            if(!alphaIndexer.containsKey(name)){
                alphaIndexer.put(name, i);
            }
        }

        Set<String> sectionLetters = alphaIndexer.keySet();
        ArrayList<String> sectionList = new ArrayList<>(sectionLetters);
        Collections.sort(sectionList);
        sections = new String[sectionList.size()];
        sectionList.toArray(sections);

        alphabeticBar.setAlphaIndexer(alphaIndexer);
    }

    @Override
    public int getCount() {
        return (mData !=null && mData.size()>0) ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_contact, null);
            holder = new ViewHolder();
            holder.divider = convertView.findViewById(R.id.view_divider_1);
            holder.alpha = (TextView) convertView.findViewById(R.id.tv_alpha);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.phone = (TextView) convertView.findViewById(R.id.tv_phone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactBean bean = mData.get(position);
        holder.name.setText(bean.getName());
        holder.phone.setText(bean.getPhone());

        //Logger.e("getPinyin： " + bean.getPinyin());
        Logger.e("getSortKey： " + bean.getSortKey());

        if (position == 0) {
            holder.alpha.setText(String.valueOf(bean.getSortKey().charAt(0)));
            holder.alpha.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.VISIBLE);
        }
        else {
            if (!String.valueOf(bean.getSortKey().charAt(0)).equals(String.valueOf(mData.get(position-1).getSortKey().charAt(0)))) {
                holder.alpha.setText(String.valueOf(bean.getSortKey().charAt(0)));
                holder.alpha.setVisibility(View.VISIBLE);
                holder.divider.setVisibility(View.VISIBLE);
            } else {
                holder.alpha.setVisibility(View.GONE);
                holder.divider.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    private class ViewHolder{
        View divider;
        TextView alpha;
        TextView name;
        TextView phone;
    }


    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else {
            return "#";
        }
    }
}
