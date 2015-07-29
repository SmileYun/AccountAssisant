package cc.aaa.controls;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import cc.aaa.R;
import cc.aaa.adapter.AdapterSlideMenu;
import cc.aaa.model.SlideMenuItem;

public class SlideMenuView {
	private Activity mActivity;
	private List mMenuList;
	private boolean mIsClosed;

	/**
	 * 按键回调接口，父调子
	 */
	public interface OnSlideMenuListener {
		public void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem);
	}

	/**
	 * 用于添加点击事件
	 */
	private RelativeLayout mBottomBox;
	private OnSlideMenuListener mSlideMenuListener;

	// private OnItemClickListener mOnItemClickListener ;

	public SlideMenuView(Activity pActivity) {
		mActivity = pActivity;
		if (pActivity instanceof OnSlideMenuListener) {
			mSlideMenuListener = (OnSlideMenuListener) pActivity;
		}
		// if (pActivity instanceof OnItemClickListener) {
		// mOnItemClickListener = (OnItemClickListener) pActivity;
		// }
		initVariable();
		initView();
		InitListener();

	}

	/**
	 * 初始化，创建数组，设置标志位
	 */
	private void initVariable() {
		mMenuList = new ArrayList();
		mIsClosed = true;

	}

	/**
	 * 得到布局
	 */
	private void initView() {
		mBottomBox = (RelativeLayout) mActivity.findViewById(R.id.IncludeBottom);

	}

	private void InitListener() {
		mBottomBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		mBottomBox.setFocusableInTouchMode(true);
		mBottomBox.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU && event.getAction() == KeyEvent.ACTION_UP) {
					toggle();
				}
				return false;
			}
		});
	}

	/**
	 * 点击下部块时，加载下面的布局
	 */
	private void open() {
		// 设置动画
		AnimationSet animation = new AnimationSet(true);

		Animation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
		alphaAnimation.setFillAfter(true);
		Animation translate = new TranslateAnimation(0, 0, 800, 0);
		translate.setFillAfter(true);

		animation.addAnimation(alphaAnimation);
		animation.addAnimation(translate);
		animation.setDuration(500);
		/*
		 * animation.setAnimationListener(new AnimationListener() {
		 * 
		 * @Override public void onAnimationStart(Animation animation) {
		 * 
		 * }
		 * 
		 * @Override public void onAnimationRepeat(Animation animation) { }
		 * 
		 * @Override public void onAnimationEnd(Animation animation) { //
		 * 布局的参数LayoutParams RelativeLayout.LayoutParams _LayoutParams = new
		 * RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.FILL_PARENT,
		 * RelativeLayout.LayoutParams.FILL_PARENT); // 设置规则
		 * _LayoutParams.addRule(RelativeLayout.BELOW, R.id.IncludeBottom);
		 * mBottomBox.setLayoutParams(_LayoutParams);
		 * 
		 * mIsClosed = false; } });
		 */
		mBottomBox.startAnimation(animation);

		// 布局的参数LayoutParams
		RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		// 设置规则
		_LayoutParams.addRule(RelativeLayout.BELOW, R.id.IncludeBottom);
		mBottomBox.setLayoutParams(_LayoutParams);

		mIsClosed = false;
	}

	private void close() {
		// 设置动画
		AnimationSet animation = new AnimationSet(true);

		Animation alphaAnimation = new AlphaAnimation(1f, 0.5f);
		alphaAnimation.setFillAfter(true);
		Animation translate = new TranslateAnimation(0, 0, -300, 0);
		translate.setFillAfter(true);

		animation.addAnimation(alphaAnimation);
		animation.addAnimation(translate);
		animation.setDuration(300);

		mBottomBox.startAnimation(animation);

		RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
				px2dp(65));

		_LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		mBottomBox.setLayoutParams(_LayoutParams);
		mIsClosed = true;
	}

	public int px2dp(int px) {
		DisplayMetrics outMetrics = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		return (int) (px * outMetrics.scaledDensity + 0.5f);
	}

	/**
	 * 设置滑动开关
	 */
	public void toggle() {
		if (mIsClosed) {
			open();
		} else {
			close();
		}
	}

	public void add(SlideMenuItem pSlideMenuItem) {
		mMenuList.add(pSlideMenuItem);
	}

	public void bindList() {
		AdapterSlideMenu _AdapterSlideMenu = new AdapterSlideMenu(mMenuList, mActivity);
		ListView _list = (ListView) mActivity.findViewById(R.id.lvSlideList);
		_list.setAdapter(_AdapterSlideMenu);
		_list.setOnItemClickListener(new OnSlideMenuItemClick());
		// _list.setOnItemClickListener(new OnItemClickListener() {

		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// mOnItemClickListener.onItemClick(parent, view, position, id);
		// }

		// });
	}

	private class OnSlideMenuItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			SlideMenuItem _SlideMenuItem = (SlideMenuItem) parent.getItemAtPosition(position);
			mSlideMenuListener.onSlideMenuItemClick(view, _SlideMenuItem);
		}

	}

	public void removeBottomBox() {
		RelativeLayout _MainLayout = (RelativeLayout) mActivity.findViewById(R.id.layMainLayout);
		_MainLayout.removeView(mBottomBox);
		mBottomBox = null;
	}
}