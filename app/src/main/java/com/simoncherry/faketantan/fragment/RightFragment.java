package com.simoncherry.faketantan.fragment;


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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.URLUtil;
import android.widget.LinearLayout;

import com.baoyz.treasure.Treasure;
import com.gigamole.library.navigationtabstrip.NavigationTabStrip;
import com.orhanobut.logger.Logger;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.activity.MainActivity;
import com.simoncherry.faketantan.adapter.MyFragmentPagerAdapter;
import com.simoncherry.faketantan.bean.PhotoItem;
import com.simoncherry.faketantan.sp.UserData;
import com.simoncherry.faketantan.utils.BitmapUtil;
import com.simoncherry.faketantan.utils.GaussBlurUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 右边界面
 */
public class RightFragment extends Fragment{

	private Context mContext;
	private LinearLayout layoutRoot;
	private NavigationTabStrip mItemTab;
	private ViewPager mViewpager;
	private List<Fragment> mFragments=new ArrayList<>();
	private MyFragmentPagerAdapter adapter;

	private String avatarUrl = "";
	private int layoutWidth = 480;
	private int layoutHeight = 800;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sliding_menu_right, container, false);
		initView(view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		//setBlurBackground();

		ViewTreeObserver vto = layoutRoot.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				layoutRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				layoutWidth = layoutRoot.getWidth();
				layoutHeight = layoutRoot.getHeight();
				Logger.e("get layoutRoot width: " + String.valueOf(layoutWidth));
				Logger.e("get layoutRoot height: " + String.valueOf(layoutHeight));
				setBlurBackground();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isAvatarChange()) {
			setBlurBackground();
		}
	}

	private boolean isAvatarChange() {
		UserData userData = Treasure.get(mContext, UserData.class);
		List<PhotoItem> photoItemList = userData.getPhotoItem();
		if (photoItemList != null && photoItemList.size() > 0) {
			String url = photoItemList.get(0).hyperlink;
			return !url.equals(avatarUrl);
		} else {
			return false;
		}
	}

	private void initView(View view) {
		layoutRoot = (LinearLayout) view.findViewById(R.id.layout_root);
		mItemTab=(NavigationTabStrip)view.findViewById(R.id.menu_right_tab);
		mViewpager=(ViewPager)view.findViewById(R.id.menu_right_viewpager);
		RightMatchFragment mOneFragment=new RightMatchFragment();
		RightChatFragment mTwoFragment=new RightChatFragment();
		mFragments.add(mOneFragment);
		mFragments.add(mTwoFragment);
		adapter=new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(),mFragments);
		mViewpager.setAdapter(adapter);
		mItemTab.setTitles("所有配对","聊天");
		mItemTab.setViewPager(mViewpager);
		MainActivity mainActivity=(MainActivity)getActivity();
		mainActivity.addIgnoredView(mViewpager);
	}


	/**
	 *  点击右侧菜单中切换中间主界面的fragment
	 * @param fragment
	 */
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		MainActivity ra = (MainActivity) getActivity();
		ra.switchContent(fragment);
	}

	float[] array = {
			0.7f, 0, 0, 0, -100,
			0, 0.7f, 0, 0, -100,
			0, 0, 0.7f, 0, -100,
			0, 0, 0, 1, 0
	};

	private void setBlurBackground() {
//		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//		layoutRoot.measure(w, h);
//		int width = layoutRoot.getMeasuredWidth();
//		int height = layoutRoot.getMeasuredHeight();
//		int width = 192;
//		int height = 1235;
//		Logger.e("get layoutRoot width: " + String.valueOf(width));
//		Logger.e("get layoutRoot height: " + String.valueOf(height));

//		Bitmap avatar = BitmapFactory.decodeResource(getResources(), R.drawable.fox);
//		Bitmap faceIconGreyBitmap = Bitmap
//				.createBitmap(height, height, Bitmap.Config.ARGB_8888);
//		Canvas canvas = new Canvas(faceIconGreyBitmap);
//		Paint paint = new Paint();
//		ColorMatrix colorMatrix = new ColorMatrix();
//		colorMatrix.set(array);
//		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
//				colorMatrix);
//		paint.setColorFilter(colorMatrixFilter);
//		canvas.drawBitmap(avatar, 0, 0, paint);
//
//		avatar = Bitmap.createBitmap(faceIconGreyBitmap, height/4, 0, height/2, height);
//		avatar = GaussBlurUtil.toBlur(avatar, 4);
//
//		layoutRoot.setBackground(new BitmapDrawable(avatar));

		UserData userData = Treasure.get(mContext, UserData.class);
		List<PhotoItem> photoItemList = userData.getPhotoItem();
		if (photoItemList != null && photoItemList.size() > 0) {
			avatarUrl = photoItemList.get(0).hyperlink;
			if (URLUtil.isNetworkUrl(avatarUrl)) {
				Picasso.with(mContext).load(avatarUrl)
						.resize(300, 300).centerCrop()
						.placeholder(R.drawable.img_defalut)
						.placeholder(R.drawable.img_defalut)
						.into(mTarget);
			} else if (new File(avatarUrl).exists()) {
				Bitmap bitmap = BitmapFactory.decodeFile(avatarUrl);
				setBlurBackground(bitmap);
			}
		}
	}

	Target mTarget = new Target() {
		@Override
		public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
			setBlurBackground(bitmap);
		}
		@Override
		public void onBitmapFailed(Drawable errorDrawable) {
		}
		@Override
		public void onPrepareLoad(Drawable placeHolderDrawable) {
		}
	};

	private void setBlurBackground(Bitmap avatar) {
//		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//		layoutRoot.measure(w, h);
//		int width = layoutRoot.getMeasuredWidth();
//		int height = layoutRoot.getMeasuredHeight();
		int width = layoutWidth;
		int height = layoutHeight;
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
}
