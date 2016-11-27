package com.simoncherry.faketantan.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.realm.Tutorial;

import java.util.List;

/**
 * Created by Simon on 2016/11/16.
 */

public class TutorialNewAdapter extends ArrayAdapter<Tutorial> {

    private Context mContext;
    private List<Tutorial> mData;

    public interface TutorialBtnListener {
         void onCLick(int index);
    }

    private TutorialBtnListener mListener;

    public void setTutorialBtnListener(TutorialBtnListener listener) {
        mListener = listener;
    }

    public TutorialNewAdapter(Context context, List<Tutorial> data) {
        super(context, 0, data);
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    @Nullable
    @Override
    public Tutorial getItem(int position) {
        return mData.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tutorial_btn, null);
            holder.btnQuestion = (Button) convertView.findViewById(R.id.btn_tutorial);
            convertView.setTag(holder);
        }

        final Tutorial bean = getItem(position);
        if (bean != null) {
            holder.btnQuestion.setText(bean.getContent());
            holder.btnQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onCLick(bean.getIndex());
                }
            });
        }

        return convertView;
    }

    private static class ViewHolder {
        Button btnQuestion;
    }
}
