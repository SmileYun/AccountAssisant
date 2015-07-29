package cc.aaa.activity;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import cc.aaa.R;
import cc.aaa.activity.base.ActivityFrame;
import cc.aaa.adapter.AdapterAccountBook;
import cc.aaa.adapter.AdapterAccountBook;
import cc.aaa.business.BusinessAccountBook;
import cc.aaa.business.BusinessAccountBook;
import cc.aaa.controls.SlideMenuView.OnSlideMenuListener;
import cc.aaa.model.ModelAccountBook;
import cc.aaa.model.SlideMenuItem;

public class AccountBookActivity extends ActivityFrame implements OnSlideMenuListener, OnItemClickListener {

	private BusinessAccountBook mBusinessAccountBook;
	private ListView lvAccountBookList;
	private AdapterAccountBook mAdapterAccountBook;
	private ModelAccountBook mModelAccountBook;
	private static final int AccountBook_EDIT_DIALOG = 1;
	private static final int AccountBook_ADD_DIALOG = 2;
	private static final int AccountBook_IS_DELETE_DIALOG = 3;
	private Bundle mBundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hideTitleBackButton();
		// 注意
		AppendMainBody(R.layout.account_book);
		InitVariable();
		InitView();
		InitListeners();
		BindData();
		createSlideMenu(R.array.SlideMenuAccountBook);
	}

	/**
	 * 绑定Adapter
	 */
	private void BindData() {
		/**
		 * 初始化变量，即绑定数据源，创建Adapter
		 */
		mAdapterAccountBook = new AdapterAccountBook(AccountBookActivity.this);
		lvAccountBookList.setAdapter(mAdapterAccountBook);
	}

	/**
	 * 设置监听器
	 */
	private void InitListeners() {
		registerForContextMenu(lvAccountBookList);
	}

	/**
	 * ListView Item 监听器
	 * 
	 * @author Administrator
	 * 
	 */
	private class onAccountBookListViewItemClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			return false;
		}

	}

	public void InitVariable() {
		mBusinessAccountBook = new BusinessAccountBook(AccountBookActivity.this);
	}

	/**
	 * 得到界面布局
	 */
	public void InitView() {
		lvAccountBookList = (ListView) findViewById(R.id.lvAccountBookList);
	}

	/**
	 * 创建上下文菜单项
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

		AdapterContextMenuInfo _AdapterContextMenuInfo = (AdapterContextMenuInfo) menuInfo;
		// 获取Adapter
		mModelAccountBook = (ModelAccountBook) lvAccountBookList.getAdapter().getItem(_AdapterContextMenuInfo.position);
		menu.setHeaderTitle(mModelAccountBook.getAccountBookName());
		menu.setHeaderIcon(R.drawable.account_book_small_icon);
		// add context menu item
		menu.add(0, 1, Menu.NONE, "修改账本");
		menu.add(0, 2, Menu.NONE, "删除账本");
	}

	/**
	 * 设置上下文菜单项监听器
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// 得到当前被选中的item信息
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();

		switch (item.getItemId()) {
		case 1:
			Bundle _Bundlee = new Bundle();
			ModelAccountBook _ModelAccountBooke = (ModelAccountBook) mAdapterAccountBook.getList().get(menuInfo.position);
			_Bundlee.putString("AccountBookName", _ModelAccountBooke.getAccountBookName());
			_Bundlee.putInt("AccountBookID", _ModelAccountBooke.getAccountBookID());
			_Bundlee.putInt("Position", menuInfo.position);
			mBundle = _Bundlee;
			showMyDialog(AccountBook_EDIT_DIALOG, _Bundlee);
			break;
		case 2:
			Bundle _Bundle = new Bundle();
			ModelAccountBook _ModelAccountBook = (ModelAccountBook) mAdapterAccountBook.getList().get(menuInfo.position);
			_Bundle.putString("AccountBookName", _ModelAccountBook.getAccountBookName());
			_Bundle.putInt("AccountBookID", _ModelAccountBook.getAccountBookID());
			_Bundle.putInt("Position", menuInfo.position);
			mBundle = _Bundle;
			showMyDialog(AccountBook_IS_DELETE_DIALOG, _Bundle);
			_Bundle = null;
			break;
		default:
			return super.onContextItemSelected(item);
		}
		return true;
	}

	@Override
	public void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem) {
		if (pSlideMenuItem.getmTitle().equals(getString(R.string.SlideMentTextAccountBookAdd))) {
			slideMenuToggle();
			showMyDialog(AccountBook_ADD_DIALOG, null);
			return;
		}
	}

	private Bundle getData() {
		return mBundle;
	}

	private void showMyDialog(int id, Bundle args) {

		if (id == AccountBook_ADD_DIALOG) {
			// 添加用户
			final View add_AccountBook_view = getLayoutInflater().inflate(R.layout.account_book_add_or_edit, null);

			createAlertDialog(getString(R.string.DialogTitleAccountBook), mBundle, add_AccountBook_view,
					new addAccountBookListener(add_AccountBook_view));
			return;
		}

		if (id == AccountBook_IS_DELETE_DIALOG) {
			createMsgAlertDialog(getString(R.string.DialogTitleDelete), mBundle,
					getString(R.string.DialogMessageAccountBookDelete), new deleteClickListener(args));
			return;
		}

		if (id == AccountBook_EDIT_DIALOG) {
			View _view = getLayoutInflater().inflate(R.layout.account_book_add_or_edit, null);
			createAlertDialog(getString(R.string.DialogTitleAccountBook), mBundle, _view, new editAccountBookListener(args,
					_view));
			return;
		}
		return;
	}

	/**
	 * 添加或编辑账本的对话框
	 * 
	 * @param title
	 * @param args
	 * @param _view
	 * @param listener
	 * @return
	 */
	private void createAlertDialog(String title, Bundle args, View _view, DialogInterface.OnClickListener listener) {
		new AlertDialog.Builder(AccountBookActivity.this).setTitle(title).setView(_view)
				.setIcon(R.drawable.account_book_big_icon).setPositiveButton("确定", listener)
				.setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setAlertDialogIsClose(dialog, true);
						dialog.dismiss();

					}
				}).show();
	}

	private void createMsgAlertDialog(String title, Bundle args, String msg, DialogInterface.OnClickListener listener) {
		AlertDialog _AlertDialog = new AlertDialog.Builder(AccountBookActivity.this).setTitle(title).setMessage(msg)
				.setIcon(R.drawable.account_book_big_icon).setPositiveButton("确定", listener)
				.setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setAlertDialogIsClose(dialog, true);
						dialog.dismiss();

					}
				}).show();
	}

	/**
	 * 添加用户时，对话框弹出的执行的业务逻辑
	 * 
	 * @param dialog
	 * @param which
	 * @param _EditText
	 */
	private void addAccountBookDialogClick(DialogInterface dialog, int which, View view) {
		EditText _EditText;
		CheckBox _CheckBox;
		_EditText = (EditText) view.findViewById(R.id.etAccountBookName);
		_CheckBox = (CheckBox) view.findViewById(R.id.chkIsDefault);
		String _editIn = _EditText.getText().toString().trim();
		_EditText.setText("");

		if ("".equals(_editIn)) {

			ToastMsg("请填入正确的用户名");
			setAlertDialogIsClose(dialog, false);

		} else {
			ModelAccountBook _ModelAccountBook = new ModelAccountBook();
			_ModelAccountBook.setAccountBookName(_editIn);
			if (_CheckBox.isChecked()) {
				_ModelAccountBook.setIsDefault(1);
				mBusinessAccountBook.setAccountNotDefault(_ModelAccountBook.getAccountBookID());
				System.out.println(_ModelAccountBook.getAccountBookID());
			} else {
				_ModelAccountBook.setIsDefault(0);
			}

			if (mBusinessAccountBook.insertAccountBook(_ModelAccountBook)) {
				InitVariable();
				BindData();

			} else {
				ToastMsg("用户已存在，插入失败！");
				setAlertDialogIsClose(dialog, false);
				return;
			}
			setAlertDialogIsClose(dialog, true);
			dialog.dismiss();
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param dialog
	 * @param which
	 * @param _EditText
	 */
	private void deleteAccountBookDialogClick(DialogInterface dialog, int which, Bundle data) {

		int AccountBookID = data.getInt("AccountBookID");

		// 此处是逻辑删除而非物理删除

		if (mBusinessAccountBook.hideAccountBookByID(AccountBookID)) {
			ToastMsg("删除成功");
			BindData();
		} else {
			ToastMsg("删除失败");
			BindData();
		}
	}

	/**
	 * android提供了一个专门判断空字符串的方法。 TextUtils.isEmpty(edit.getText())直接调用就可以了。
	 */

	/**
	 * 菜单ListView， 设立监听器 暂时没用
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		// String _MenuName = parent.getAdapter().getItem(position);
		SlideMenuItem _ItemName = (SlideMenuItem) parent.getAdapter().getItem(position);

		if (_ItemName.getmTitle().equals(getString(R.string.SlideMentTextAccountBookAdd))) {
			slideMenuToggle();
			showMyDialog(AccountBook_ADD_DIALOG, null);
			return;
		}

	}

	private class editAccountBookListener implements DialogInterface.OnClickListener {
		private int poistion;
		private EditText _EditText;
		private CheckBox _CheckBox;

		/**
		 * 编辑账本对话框
		 * 
		 * @param args
		 * @param _view
		 */
		public editAccountBookListener(Bundle args, View _view) {
			poistion = args.getInt("Position");
			_EditText = (EditText) _view.findViewById(R.id.etAccountBookName);
			_EditText.setText(args.getString("AccountBookName"));
			_CheckBox = (CheckBox) _view.findViewById(R.id.chkIsDefault);
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			ModelAccountBook _ModelAccountBook = (ModelAccountBook) lvAccountBookList.getAdapter().getItem(poistion);
			_ModelAccountBook.setAccountBookName(_EditText.getText().toString().trim());
			_ModelAccountBook.setIsDefault(_CheckBox.isChecked() ? 1 : 0);

			if (!mBusinessAccountBook.isExistAccountBookByName(_EditText.getText().toString().trim(), null)
					&& mBusinessAccountBook.updateAccountBookByAccountBookID(_ModelAccountBook)) {

				BindData();
				setAlertDialogIsClose(dialog, true);
			} else {
				setAlertDialogIsClose(dialog, false);
				ToastMsg("账本已存在");
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
			deleteAccountBookDialogClick(dialog, which, pBundle);

		}

	}

	private class addAccountBookListener implements DialogInterface.OnClickListener {
		View view;

		public addAccountBookListener(View pView) {
			view = pView;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			addAccountBookDialogClick(dialog, which, view);
		}
	}
}
