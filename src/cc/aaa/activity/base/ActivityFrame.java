package cc.aaa.activity.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cc.aaa.R;
import cc.aaa.controls.SlideMenuView;
import cc.aaa.model.SlideMenuItem;

public class ActivityFrame extends ActivityBase {

	private SlideMenuView mSlideMenuView;
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ô�����չ����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		View _view = findViewById(R.id.top_title_imgback);
		_view.setOnClickListener(new MyListener());
	}

	private class MyListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			System.out.println("finish");
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * ����TopBar����
	 * 
	 * @param pRestID
	 *            Ҫ���õ�String��ԴID
	 */
	protected void setTopBarTitle(String pText) {
		// String _Title = FormatResString(pRestID);
		TextView _tvTitle = (TextView) findViewById(R.id.top_title_tv);
		_tvTitle.setText(pText);

		// ImageView _ImageView = (ImageView) findViewById(R.id.ivBottomIcon);
		// _ImageView.setImageResource(R.drawable.account_book_32x32);
	}

	/**
	 * �ڳ�ʼ�������ط��ذ�ť
	 */
	protected void hideTitleBackButton() {
		findViewById(R.id.top_title_imgback).setVisibility(View.GONE);
	}

	/**
	 * ���Layout������Body��
	 * 
	 * @param pResID
	 *            Ҫ��ӵ�Layout��ԴID
	 */
	protected void AppendMainBody(int pResID) {
		LinearLayout _LinearLayout = (LinearLayout) findViewById(getMainBodyId());
		View _View = LayoutInflater.from(this).inflate(pResID, null);
		RelativeLayout.LayoutParams _Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		_LinearLayout.addView(_View, _Params);
	}

	protected void AppendMainBody(View pView) {
		LinearLayout _MainBody = (LinearLayout) findViewById(getMainBodyId());
		RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		_MainBody.addView(pView, _LayoutParams);
		// _View.setPadding(15,15,15,15);
	}

	private int getMainBodyId() {
		return R.id.MainBody;
	}

	protected void slideMenuToggle() {
		mSlideMenuView.toggle();
	}

	/**
	 * ������Դ����ȡ���飬����,SlidMenu�µ�List����
	 * 
	 * @param pResID
	 */
	protected void createSlideMenu(int pResID) {
		mSlideMenuView = new SlideMenuView(this);
		String[] _MenuItemArray = getResources().getStringArray(pResID);
		// ��̬���ListView�е�����
		for (int i = 0; i < _MenuItemArray.length; i++) {
			SlideMenuItem _Item = new SlideMenuItem(i, _MenuItemArray[i]);
			mSlideMenuView.add(_Item);
		}
		/**
		 * ����Ӻ��List �� ListView ��
		 */
		mSlideMenuView.bindList();
	}

	protected void createMenu(Menu p_Menu) {
		int _GroupID = 0;
		int _Order = 0;
		int[] p_ItemID = { 1, 2 };

		for (int i = 0; i < p_ItemID.length; i++) {
			switch (p_ItemID[i]) {
			case 1:
				p_Menu.add(_GroupID, p_ItemID[i], _Order, R.string.MenuTextEdit);
				break;
			case 2:
				p_Menu.add(_GroupID, p_ItemID[i], _Order, R.string.MenuTextDelete);
				break;
			default:
				break;
			}
		}
	}

	protected void removeBottomBox() {
		mSlideMenuView = new SlideMenuView(this);
		mSlideMenuView.removeBottomBox();
	}

	/**
	 * �Ի���ķ�װ
	 * 
	 * @param p_TitelResID
	 * @param p_Message
	 * @param p_ClickListener
	 * @return
	 */
	protected AlertDialog showAlertDialog(int p_TitelResID, String p_Message, DialogInterface.OnClickListener p_ClickListener) {
		String _Title = getResources().getString(p_TitelResID);
		return showAlertDialog(_Title, p_Message, p_ClickListener);
	}

	protected AlertDialog showAlertDialog(String p_Title, String p_Message, DialogInterface.OnClickListener p_ClickListener) {
		return new AlertDialog.Builder(this).setTitle(p_Title).setMessage(p_Message)
				.setPositiveButton(R.string.ButtonTextYes, p_ClickListener).setNegativeButton(R.string.ButtonTextNo, null)
				.show();
	}

	/**
	 * ����һ��״̬��
	 * 
	 * @param p_TitleResID
	 * @param p_MessageResID
	 */
	protected void showProgressDialog(int p_TitleResID, int p_MessageResID) {
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle(getString(p_TitleResID));
		mProgressDialog.setMessage(getString(p_MessageResID));
		mProgressDialog.show();
	}

	/**
	 * ȡ��״̬��
	 */
	protected void dismissProgressDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}
}
