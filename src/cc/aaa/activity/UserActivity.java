package cc.aaa.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import cc.aaa.R;
import cc.aaa.activity.base.ActivityFrame;
import cc.aaa.adapter.AdapterUser;
import cc.aaa.business.BusinessUser;
import cc.aaa.controls.SlideMenuView.OnSlideMenuListener;
import cc.aaa.model.ModelUser;
import cc.aaa.model.SlideMenuItem;

public class UserActivity extends ActivityFrame implements OnSlideMenuListener, OnItemClickListener {

	private BusinessUser mBusinessUser;
	private ListView lvUserList;
	private AdapterUser mAdapterUser;
	private static final int USER_EDIT_DIALOG = 1;
	private static final int USER_ADD_DIALOG = 2;
	private static final int USER_IS_DELETE_DIALOG = 3;
	private ModelUser mModelUser;
	private Bundle mBundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hideTitleBackButton();
		// ע��
		AppendMainBody(R.layout.user_lay);
		InitVariable();
		InitView();
		InitListeners();
		BindData();
		createSlideMenu(R.array.SlideMenuUser);
		// StartService();

	}

	/**
	 * ��Adapter
	 */
	private void BindData() {
		/**
		 * ��ʼ����������������Դ������Adapter
		 */
		mAdapterUser = new AdapterUser(UserActivity.this);
		lvUserList.setAdapter(mAdapterUser);
		// ע�������Ĳ˵�
		this.registerForContextMenu(lvUserList);
	}

	/**
	 * ���ü�����
	 */
	private void InitListeners() {
		// lvUserList.setOnItemLongClickListener(new
		// onUserListViewItemClickListener());
	}

	/**
	 * �õ����沼��
	 */
	public void InitView() {
		lvUserList = (ListView) findViewById(R.id.user_listview);
	}

	/**
	 * ListView Item ������
	 * 
	 * @author Administrator
	 * 
	 */
	private class onUserListViewItemClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			/*
			 * Bundle _Bundle = new Bundle(); ModelUser _ModelUser = (ModelUser)
			 * parent.getAdapter().getItem( position);
			 * _Bundle.putString("UserName", _ModelUser.getUserName());
			 * _Bundle.putInt("UserID", _ModelUser.getUserID());
			 * _Bundle.putInt("Position", position);
			 * showMyDialog(USER_IS_DELETE_DIALOG, _Bundle);
			 */
			return false;
		}

	}

	private Integer[] mImageInteger = { R.drawable.grid_payout, R.drawable.grid_bill, R.drawable.grid_report,
			R.drawable.grid_account_book, R.drawable.grid_category, R.drawable.grid_user, };

	public void InitVariable() {
		// List _List = new ArrayList();
		//
		// for (int x = 0; x < 6; ++x) {
		// _List.add(mImageInteger[x]);
		// }
		mBusinessUser = new BusinessUser(UserActivity.this);

	}

	/**
	 * ���������Ĳ˵���
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

		AdapterContextMenuInfo _AdapterContextMenuInfo = (AdapterContextMenuInfo) menuInfo;
		// ��ȡAdapter
		mModelUser = (ModelUser) lvUserList.getAdapter().getItem(_AdapterContextMenuInfo.position);
		menu.setHeaderTitle(mModelUser.getUserName());
		menu.setHeaderIcon(R.drawable.user_small_icon);
		// add context menu item
		menu.add(0, 1, Menu.NONE, "�û��޸�");
		menu.add(0, 2, Menu.NONE, "�û�ɾ��");
	}

	/**
	 * ���������Ĳ˵��������
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// �õ���ǰ��ѡ�е�item��Ϣ
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();

		switch (item.getItemId()) {
		case 1:
			Bundle _Bundlee = new Bundle();
			ModelUser _ModelUsere = (ModelUser) mAdapterUser.getList().get(menuInfo.position);
			_Bundlee.putString("UserName", _ModelUsere.getUserName());
			_Bundlee.putInt("UserID", _ModelUsere.getUserID());
			_Bundlee.putInt("Position", menuInfo.position);
			mBundle = _Bundlee;
			showMyDialog(USER_EDIT_DIALOG, _Bundlee);
			break;
		case 2:
			Bundle _Bundle = new Bundle();
			ModelUser _ModelUser = (ModelUser) mAdapterUser.getList().get(menuInfo.position);
			_Bundle.putString("UserName", _ModelUser.getUserName());
			_Bundle.putInt("UserID", _ModelUser.getUserID());
			_Bundle.putInt("Position", menuInfo.position);
			mBundle = _Bundle;
			showMyDialog(USER_IS_DELETE_DIALOG, _Bundle);
			_Bundle = null;
			break;
		default:
			return super.onContextItemSelected(item);
		}
		return true;
	}

	@Override
	public void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem) {

		if (pSlideMenuItem.getmTitle().equals(getString(R.string.SlideMentTextUserAdd))) {
			slideMenuToggle();
			System.out.println("����û�ǰ");
			showMyDialog(USER_ADD_DIALOG, null);
			return;
		}

	}

	private Bundle getData() {
		return mBundle;
	}

	private void showMyDialog(int id, Bundle args) {

		if (id == USER_ADD_DIALOG) {
			// ����û�
			System.out.println("����û�");
			final View add_user_view = getLayoutInflater().inflate(R.layout.user_edit_or_add, null);

			createAlertDialog(getString(R.string.DialogUserADDTitle), args, add_user_view,
					new addUserListener(add_user_view));
			return;
		}
		if (id == USER_IS_DELETE_DIALOG) {
			createMsgAlertDialog(getString(R.string.DialogTitleDelete), args, getString(R.string.DialogMessageUserDelete),
					new deleteClickListener(mBundle));
			return;
		}

		if (id == USER_EDIT_DIALOG) {

			View _view = getLayoutInflater().inflate(R.layout.user_edit_or_add, null);
			EditText _EditText = (EditText) _view.findViewById(R.id.etUserName);
			_EditText.setText(mModelUser.getUserName());
			createAlertDialog(getString(R.string.DialogUserEditTitle), args, _view, new editUserListener(mBundle, _view));
		}
		// return null;
		return;
	}

	private void createAlertDialog(String title, Bundle args, View _view, DialogInterface.OnClickListener listener) {
		// AlertDialog _AlertDialog =
		new AlertDialog.Builder(UserActivity.this).setTitle(title).setView(_view).setIcon(R.drawable.user_big_icon)
				.setPositiveButton("ȷ��", listener).setNegativeButton("ȡ��", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setAlertDialogIsClose(dialog, true);
						dialog.dismiss();

					}
				}).create().show();

		// return null;
	}

	private void createMsgAlertDialog(String title, Bundle args, String msg, DialogInterface.OnClickListener listener) {
		// AlertDialog _AlertDialog =
		new AlertDialog.Builder(UserActivity.this).setTitle(title).setMessage(msg).setIcon(R.drawable.user_big_icon)
				.setPositiveButton("ȷ��", listener).setNegativeButton("ȡ��", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setAlertDialogIsClose(dialog, true);
						dialog.dismiss();

					}
				}).create().show();

		// return _AlertDialog;
	}

	/**
	 * ����û�ʱ���Ի��򵯳���ִ�е�ҵ���߼�
	 * 
	 * @param dialog
	 * @param which
	 * @param _EditText
	 */
	private void addUserDialogClick(DialogInterface dialog, int which, EditText _EditText) {

		final String _editIn = _EditText.getText().toString().trim();
		_EditText.setText("");

		if ("".equals(_editIn)) {

			ToastMsg("��������ȷ���û���");
			setAlertDialogIsClose(dialog, false);

		} else {
			ModelUser _ModelUser = new ModelUser();
			_ModelUser.setUserName(_editIn);
			if (mBusinessUser.insertUser(_ModelUser)) {
				InitVariable();
				BindData();

			} else {
				ToastMsg("�û��Ѵ��ڣ�����ʧ�ܣ�");
				setAlertDialogIsClose(dialog, false);
				return;
			}
			setAlertDialogIsClose(dialog, true);
			dialog.dismiss();
		}
	}

	/**
	 * ɾ���û�
	 * 
	 * @param dialog
	 * @param which
	 * @param _EditText
	 */
	private void deleteUserDialogClick(DialogInterface dialog, int which, Bundle data) {

		int UserID = mBundle.getInt("UserID");

		// �˴����߼�ɾ����������ɾ��

		if (mBusinessUser.hideUserByID(UserID)) {
			ToastMsg("ɾ���ɹ�");
			BindData();
		} else {
			ToastMsg("ɾ��ʧ��");
			BindData();
		}
	}

	/**
	 * android�ṩ��һ��ר���жϿ��ַ����ķ����� TextUtils.isEmpty(edit.getText())ֱ�ӵ��þͿ����ˡ�
	 */

	/**
	 * �˵�ListView�� ����������
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		// String _MenuName = parent.getAdapter().getItem(position);
		SlideMenuItem _ItemName = (SlideMenuItem) parent.getAdapter().getItem(position);

		if (_ItemName.getmTitle().equals(getString(R.string.SlideMentTextUserAdd))) {
			slideMenuToggle();
			System.out.println("����û�ǰ");
			showMyDialog(USER_ADD_DIALOG, null);
			return;
		}

	}

	private class editUserListener implements DialogInterface.OnClickListener {
		int poistion;
		EditText _EditText;

		public editUserListener(Bundle args, View _view) {
			// poistion = mBundle.getInt("Position");
			_EditText = (EditText) _view.findViewById(R.id.etUserName);
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			ModelUser _ModelUser = (ModelUser) lvUserList.getAdapter().getItem(mBundle.getInt("Position"));

			_ModelUser.setUserName(_EditText.getText().toString().trim());
			if (mBusinessUser.updateUserByUserID(_ModelUser)) {
				BindData();
			} else {
				setAlertDialogIsClose(dialog, false);
				ToastMsg("�û��Ѵ���");
			}
		}
	}

	private class deleteClickListener implements DialogInterface.OnClickListener {
		Bundle pBundle;

		public deleteClickListener(Bundle pBundle) {
			this.pBundle = pBundle;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			deleteUserDialogClick(dialog, which, pBundle);

		}

	}

	private class addUserListener implements DialogInterface.OnClickListener {
		EditText _EditText;

		public addUserListener(View pView) {
			_EditText = (EditText) pView.findViewById(R.id.etUserName);
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			addUserDialogClick(dialog, which, _EditText);
		}
	}
}
