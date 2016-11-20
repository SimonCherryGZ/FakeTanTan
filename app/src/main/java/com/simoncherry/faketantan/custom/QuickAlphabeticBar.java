package com.simoncherry.faketantan.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.simoncherry.faketantan.R;

import java.util.HashMap;

/**
 * 字母表
 */
public class QuickAlphabeticBar extends ImageButton {
	private TextView mDialogText;
	private Handler mHandler;
	private ListView mList;
	private float mHeight;
	private String[] letters = new String[] { "#", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };
	private HashMap<String, Integer> alphaIndexer;

	public QuickAlphabeticBar(Context context) {
		super(context);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void init(Activity ctx) {
		mDialogText = (TextView) ctx.findViewById(R.id.fast_position);
		mDialogText.setVisibility(View.INVISIBLE);
		mHandler = new Handler();
	}

	public void setListView(ListView mList) {
		this.mList = mList;
	}

	public void setAlphaIndexer(HashMap<String, Integer> alphaIndexer) {
		this.alphaIndexer = alphaIndexer;
		Logger.e("alphaIndexer" + alphaIndexer.toString());
	}

	public void setHeight(float mHeight) {
		this.mHeight = mHeight;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int act = event.getAction();
		float y = event.getY();
		final int oldChoose = choose;
		// 计算手指位置，找到对应的段，让mList移动段开头的位置上
		int selectIndex = (int) (y / (mHeight / letters.length));
		
		if (selectIndex > -1 && selectIndex < letters.length) {// 防止越界
			String key = letters[selectIndex];
			if (alphaIndexer.containsKey(key))
			{
				int pos = alphaIndexer.get(key);
				if (mList.getHeaderViewsCount() > 0) {// 防止ListView有标题栏，本例中没有。
					this.mList.setSelectionFromTop(
							pos + mList.getHeaderViewsCount(), 0);

				} else {
					this.mList.setSelectionFromTop(pos, 0);
				}
				//mDialogText.setText(letters[selectIndex]);
			}
			mDialogText.setText(letters[selectIndex]);
		}

		switch (act) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;

			if (oldChoose != selectIndex) {
				if (selectIndex > 0 && selectIndex < letters.length) {
					choose = selectIndex;

					// TODO 移动mDialogText
					float offset = getTop()/2 + choose * (mHeight/letters.length);
					mDialogText.setY(offset);

					invalidate();
				}
			}

			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (mDialogText != null
								&& mDialogText.getVisibility() == View.INVISIBLE) {
							mDialogText.setVisibility(VISIBLE);
						}
					}
				});
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != selectIndex) {
				if (selectIndex > 0 && selectIndex < letters.length) {
					choose = selectIndex;
					// TODO 移动mDialogText
					float offset = getTop()/2 + choose * (mHeight/letters.length);
					mDialogText.setY(offset);
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (mDialogText != null
								&& mDialogText.getVisibility() == View.VISIBLE) {
							mDialogText.setVisibility(INVISIBLE);

							float offset = getTop()/2;
							mDialogText.setY(offset);
							invalidate();
						}
					}
				});
			}
			break;
		default:
			break;
		}

		return super.onTouchEvent(event);
	}

	//TODO:
	Paint paint = new Paint();
	boolean showBkg = false;
	int choose = -1;

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showBkg) {//设置边栏背景色
			//canvas.drawColor(Color.parseColor("#b20264"));
			canvas.drawColor(getResources().getColor(R.color.color3));
		}

		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / letters.length;
		for (int i = 0; i < letters.length; i++) {
			paint.setColor(getResources().getColor(R.color.color4));
			paint.setTextSize(20);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			if (i == choose) {
				//paint.setColor(Color.parseColor("#00BFFF"));//滑动时按下的字母颜色
				paint.setFakeBoldText(true);
			}
			float xPos = width / 2 - paint.measureText(letters[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(letters[i], xPos, yPos, paint);
			paint.reset();
		}

	}

}
