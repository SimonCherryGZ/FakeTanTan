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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.treasure.Treasure;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.orhanobut.logger.Logger;
import com.simoncherry.faketantan.R;
import com.simoncherry.faketantan.activity.MainActivity;
import com.simoncherry.faketantan.bean.PhotoItem;
import com.simoncherry.faketantan.sp.JsonConverterFactory;
import com.simoncherry.faketantan.sp.UserData;
import com.simoncherry.faketantan.utils.BitmapUtil;
import com.simoncherry.faketantan.utils.GaussBlurUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.List;


/**
 * 左边界面
 */
public class LeftFragment extends Fragment implements View.OnClickListener{

	private LinearLayout layoutRoot;
	private RelativeLayout layoutUserInfo;
	private CircularImageView ivAvatar;
	private TextView tvNickname;
	private TextView tvViewProfile;
	private TextView tvTan;
	private TextView tvMsg;
	private TextView tvContact;
	private TextView tvSetting;
	private TextView tvGuide;
	private TextView tvShare;

	private Context mContext;
	private String avatarUrl = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sliding_menu_left, container, false);
		layoutRoot = (LinearLayout) view.findViewById(R.id.layout_root);
		layoutUserInfo = (RelativeLayout) view.findViewById(R.id.layout_user_info);
		ivAvatar = (CircularImageView) view.findViewById(R.id.profile_image);
		tvNickname = (TextView) view.findViewById(R.id.tv_nickname);
		tvViewProfile = (TextView) view.findViewById(R.id.tv_view_profile);
		tvTan = (TextView) view.findViewById(R.id.menu_left_item1);
		tvMsg = (TextView) view.findViewById(R.id.menu_left_item2);
		tvContact = (TextView) view.findViewById(R.id.menu_left_item3);
		tvSetting = (TextView) view.findViewById(R.id.menu_left_item4);
		tvGuide = (TextView) view.findViewById(R.id.menu_left_item5);
		tvShare = (TextView) view.findViewById(R.id.menu_left_item6);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		initView();
		setBlurBackground();

		layoutUserInfo.setOnClickListener(this);
		tvTan.setOnClickListener(this);
		tvMsg.setOnClickListener(this);
		tvContact.setOnClickListener(this);
		tvSetting.setOnClickListener(this);
		tvGuide.setOnClickListener(this);
		tvShare.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isAvatarChange()) {
			initAvatar();
			setBlurBackground();
		}
	}

	private void initView() {
		//initAvatar();
		Treasure.setConverterFactory(new JsonConverterFactory());
		UserData userData = Treasure.get(mContext, UserData.class);
		tvNickname.setText(userData.getNickName() != null ? userData.getNickName() : "null");
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

	private void initAvatar() {
		UserData userData = Treasure.get(mContext, UserData.class);
		List<PhotoItem> photoItemList = userData.getPhotoItem();
		if (photoItemList != null && photoItemList.size() > 0) {
			avatarUrl = photoItemList.get(0).hyperlink;
			if (avatarUrl != null) {
				if (URLUtil.isNetworkUrl(avatarUrl)) {
					Picasso.with(mContext)
							.load(avatarUrl)
							.fit().centerInside()
							.placeholder(R.drawable.img_defalut).error(R.drawable.img_defalut)
							.into(ivAvatar);
				} else if (new File(avatarUrl).exists()) {
					Picasso.with(mContext)
							.load(new File(avatarUrl))
							.fit().centerInside()
							.placeholder(R.drawable.img_defalut).error(R.drawable.img_defalut)
							.into(ivAvatar);
				} else {
					Picasso.with(mContext)
							.load(R.drawable.fox)
							.fit().centerInside()
							.into(ivAvatar);
				}
			} else {
				Picasso.with(mContext)
						.load(R.drawable.fox)
						.fit().centerInside()
						.into(ivAvatar);
			}
		}
	}

	/**
	 *  点击左侧菜单中切换中间主界面的fragment
	 * @param fragment
	 */
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		MainActivity ra = (MainActivity) getActivity();
		ra.switchContent(fragment);
	}

	private void showSecondaryMenu() {
		if (getActivity() == null)
			return;
		MainActivity ra = (MainActivity) getActivity();
		ra.showSecondaryMenu();
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
//		Logger.e("get layoutRoot width: " + String.valueOf(width));
//		Logger.e("get layoutRoot height: " + String.valueOf(height));
//
//		Bitmap avatar = BitmapFactory.decodeResource(getResources(), R.drawable.fox);
////		avatar = Bitmap.createBitmap(avatar, height/4, 0, height/2, height);
////		avatar = GaussBlurUtil.toBlur(avatar, 4);
//
//		Bitmap faceIconGreyBitmap = Bitmap
//				.createBitmap(height, height, Bitmap.Config.ARGB_8888);
//		Canvas canvas = new Canvas(faceIconGreyBitmap);
//		Paint paint = new Paint();
//		ColorMatrix colorMatrix = new ColorMatrix();
//		//colorMatrix.setSaturation(0.3f);
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
			String path = photoItemList.get(0).hyperlink;
			if (URLUtil.isNetworkUrl(path)) {
				Picasso.with(mContext).load(path)
						.resize(300, 300).centerCrop()
						.placeholder(R.drawable.img_defalut)
						.placeholder(R.drawable.img_defalut)
						.into(mTarget);
			} else if (new File(path).exists()) {
				Bitmap bitmap = BitmapFactory.decodeFile(path);
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
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		layoutRoot.measure(w, h);
		int width = layoutRoot.getMeasuredWidth();
		int height = layoutRoot.getMeasuredHeight();
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.layout_user_info:
				switchFragment(new UserProfileFragment());
				break;
			case R.id.menu_left_item1:
				switchFragment(new MainFragment());
				break;
			case R.id.menu_left_item2:
				showSecondaryMenu();
				break;
			case R.id.menu_left_item3:
				switchFragment(new ContactFragment());
				break;
			case R.id.menu_left_item4:
				switchFragment(new SettingFragment());
				break;
			case R.id.menu_left_item5:
				break;
			case R.id.menu_left_item6:
				break;
		}
	}
}
