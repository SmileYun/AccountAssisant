package cc.aaa.activity;

import java.math.BigDecimal;
import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cc.aaa.R;
import cc.aaa.activity.base.ActivityFrame;
import cc.aaa.adapter.AdapterAccountBookSelect;
import cc.aaa.adapter.AdapterCategory;
import cc.aaa.adapter.AdapterUser;
import cc.aaa.business.BusinessAccountBook;
import cc.aaa.business.BusinessCategory;
import cc.aaa.business.BusinessPayout;
import cc.aaa.business.BusinessUser;
import cc.aaa.controls.NumberDialog;
import cc.aaa.controls.NumberDialog.OnNumberDialogListener;
import cc.aaa.model.ModelAccountBook;
import cc.aaa.model.ModelCategory;
import cc.aaa.model.ModelPayout;
import cc.aaa.model.ModelUser;
import cc.aaa.utility.DateTools;
import cc.aaa.utility.RegexTools;

public class ActivityPayoutAddOrEdit extends ActivityFrame implements android.view.View.OnClickListener,
		OnNumberDialogListener {

	private Button btnSave;
	private Button btnCancel;

	private ModelPayout mModelPayout;
	private BusinessPayout mBusinessPayout;
	private Integer mAccountBookID;
	private Integer mCategoryID;
	private String mPayoutUserID;
	private String mPayoutTypeArr[];

	private EditText etAccountBookName;
	private EditText etAmount;
	private AutoCompleteTextView actvCategoryName;
	private EditText etPayoutDate;
	private EditText etPayoutType;
	private EditText etPayoutUser;
	private EditText etComment;
	private Button btnSelectCategory;
	private Button btnSelectUser;
	private Button btnSelectAccountBook;
	private Button btnSelectAmount;
	private Button btnSelectPayoutDate;
	private Button btnSelectPayoutType;

	private BusinessAccountBook mBusinessAccountBook;
	private BusinessCategory mBusinessCategory;
	private ModelAccountBook mModelAccountBook;
	private BusinessUser mBusinessUser;
	private List<RelativeLayout> mItemColor;
	private List<ModelUser> mUserSelectedList;

	@Override
	public void onClick(View v) {
		int _ID = v.getId();
		switch (_ID) {
		case R.id.btnSelectAccountBook:
			ShowAccountBookSelectDialog();
			break;
		case R.id.btnSelectAmount:
			(new NumberDialog(this)).show();
			break;
		case R.id.btnSelectCategory:
			ShowCategorySelectDialog();
			break;
		case R.id.btnSelectPayoutDate:
			Calendar _Calendar = Calendar.getInstance();
			showDateSelectDialog(_Calendar.get(Calendar.YEAR), _Calendar.get(Calendar.MONTH), _Calendar.get(Calendar.DATE));
			break;
		case R.id.btnSelectPayoutType:
			ShowPayoutTypeSelectDialog();
			break;
		case R.id.btnSelectUser:
			showUserSelectDialog(etPayoutType.getText().toString());
			break;
		case R.id.btnSave:
			AddOrEditPayout();
			break;
		case R.id.btnCancel:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AppendMainBody(R.layout.payout_add_or_edit);
		removeBottomBox();
		initView();
		initVariable();
		bindData();
		setTitle();
		initListeners();
	}

	private void setTitle() {
		String _Titel;
		if (mModelPayout == null) {
			_Titel = getString(R.string.ActivityTitlePayoutAddOrEdit, new Object[] { getString(R.string.TitleAdd) });
		} else {
			_Titel = getString(R.string.ActivityTitlePayoutAddOrEdit, new Object[] { getString(R.string.TitleEdit) });
			initData(mModelPayout);
		}

		setTopBarTitle(_Titel);
	}

	protected void initView() {
		btnSave = (Button) findViewById(R.id.btnSave);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnSelectAccountBook = (Button) findViewById(R.id.btnSelectAccountBook);
		btnSelectAmount = (Button) findViewById(R.id.btnSelectAmount);
		btnSelectCategory = (Button) findViewById(R.id.btnSelectCategory);
		btnSelectPayoutDate = (Button) findViewById(R.id.btnSelectPayoutDate);
		btnSelectPayoutType = (Button) findViewById(R.id.btnSelectPayoutType);
		btnSelectUser = (Button) findViewById(R.id.btnSelectUser);
		etAccountBookName = (EditText) findViewById(R.id.etAccountBookName);
		etPayoutDate = (EditText) findViewById(R.id.etPayoutDate);
		etPayoutType = (EditText) findViewById(R.id.etPayoutType);
		etAmount = (EditText) findViewById(R.id.etAmount);
		etAccountBookName = (EditText) findViewById(R.id.etAccountBookName);
		actvCategoryName = (AutoCompleteTextView) findViewById(R.id.actvCategoryName);
		etPayoutUser = (EditText) findViewById(R.id.etPayoutUser);
		etComment = (EditText) findViewById(R.id.etComment);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	protected void initListeners() {
		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		btnSelectAmount.setOnClickListener(this);
		btnSelectCategory.setOnClickListener(this);
		btnSelectPayoutDate.setOnClickListener(this);
		btnSelectPayoutType.setOnClickListener(this);
		btnSelectUser.setOnClickListener(this);
		actvCategoryName.setOnItemClickListener(new OnAutoCompleteTextViewItemClickListener());
		btnSelectAccountBook.setOnClickListener(this);
	}

	protected void initVariable() {
		mBusinessPayout = new BusinessPayout(this);
		mModelPayout = (ModelPayout) getIntent().getSerializableExtra("ModelPayout");
		mBusinessAccountBook = new BusinessAccountBook(this);
		mBusinessCategory = new BusinessCategory(this);
		mModelAccountBook = mBusinessAccountBook.getDefaultModelAccountBook();
		mBusinessUser = new BusinessUser(this);
	}

	protected void bindData() {

		mAccountBookID = mModelAccountBook.getAccountBookID();
		etAccountBookName.setText(mModelAccountBook.getAccountBookName());
		actvCategoryName.setAdapter(mBusinessCategory.getAllCategoryArrayAdapter());
		etPayoutDate.setText(DateTools.getFormatDateTime(new Date(), "yyyy-MM-dd"));
		mPayoutTypeArr = getResources().getStringArray(R.array.PayoutType);
		etPayoutType.setText(mPayoutTypeArr[0]);
	}

	private void initData(ModelPayout p_ModelPayout) {
		etAccountBookName.setText(p_ModelPayout.getAccountBookName());
		mAccountBookID = p_ModelPayout.getAccountBookID();
		etAmount.setText(p_ModelPayout.getAmount().toString());
		actvCategoryName.setText(p_ModelPayout.getCategoryName());
		mCategoryID = p_ModelPayout.getCategoryID();
		etPayoutDate.setText(DateTools.getFormatDateTime(p_ModelPayout.getPayoutDate(), "yyyy-MM-dd"));
		etPayoutType.setText(p_ModelPayout.getPayoutType());

		BusinessUser _BusinessUser = new BusinessUser(this);
		String _UserNameString = _BusinessUser.getUserNameByUserID(p_ModelPayout.getPayoutUserID());

		etPayoutUser.setText(_UserNameString);
		mPayoutUserID = p_ModelPayout.getPayoutUserID();
		etComment.setText(p_ModelPayout.getComment());
	}

	private void AddOrEditPayout() {
		Boolean _CheckResult = CheckData();
		if (_CheckResult == false) {
			return;
		}

		if (mModelPayout == null) {
			mModelPayout = new ModelPayout();
		}
		mModelPayout.setAccountBookID(mAccountBookID);
		mModelPayout.setCategoryID(mCategoryID);
		mModelPayout.setAmount(new BigDecimal(etAmount.getText().toString().trim()));
		mModelPayout.setPayoutDate(DateTools.getDate(etPayoutDate.getText().toString().trim(), "yyyy-MM-dd"));
		mModelPayout.setPayoutType(etPayoutType.getText().toString().trim());
		mModelPayout.setPayoutUserID(mPayoutUserID);
		mModelPayout.setComment(etComment.getText().toString().trim());

		Boolean _Result = false;

		if (mModelPayout.getPayoutID() == 0) {
			_Result = mBusinessPayout.insertPayout(mModelPayout);
		} else {
			_Result = mBusinessPayout.updatePayoutByPayoutID(mModelPayout);
		}

		if (_Result) {
			Toast.makeText(getApplicationContext(), getString(R.string.TipsAddSucceed), 1).show();
			finish();
		} else {
			Toast.makeText(getApplicationContext(), getString(R.string.TipsAddFail), 1).show();
		}
	}

	private Boolean CheckData() {
		Boolean _CheckResult = RegexTools.IsMoney(etAmount.getText().toString().trim());
		if (_CheckResult == false) {
			etAmount.requestFocus();
			etAmount.setFocusable(false);
			Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextMoney), 1).show();
			return _CheckResult;
		}

		_CheckResult = RegexTools.IsNull(mCategoryID);
		if (_CheckResult == false) {
			btnSelectCategory.setFocusable(true);
			btnSelectCategory.setFocusableInTouchMode(true);
			btnSelectCategory.requestFocus();
			Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextCategoryIsNull), 1).show();
			return _CheckResult;
		}

		if (mPayoutUserID == null) {
			btnSelectUser.setFocusable(true);
			btnSelectUser.setFocusableInTouchMode(true);
			btnSelectUser.requestFocus();
			Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextPayoutUserIsNull), 1).show();
			return false;
		}

		String _PayoutType = etPayoutType.getText().toString();
		if (_PayoutType.equals(mPayoutTypeArr[0]) || _PayoutType.equals(mPayoutTypeArr[1])) {
			if (mPayoutUserID.split(",").length <= 1) {
				btnSelectUser.setFocusable(true);
				btnSelectUser.setFocusableInTouchMode(true);
				btnSelectUser.requestFocus();
				Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextPayoutUser), 1).show();
				return false;
			}
		} else {
			if (mPayoutUserID.equals("")) {
				btnSelectUser.setFocusable(true);
				btnSelectUser.setFocusableInTouchMode(true);
				btnSelectUser.requestFocus();
				Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextPayoutUser2), 1).show();
				return false;
			}
		}

		return true;
	}

	@Override
	public void setNumberFinish(BigDecimal p_Number) {
		((EditText) findViewById(R.id.etAmount)).setText(p_Number.toString());
	}

	private class OnAutoCompleteTextViewItemClickListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView p_AdapterView, View arg1, int p_Postion, long arg3) {
			ModelCategory _ModelCategory = (ModelCategory) p_AdapterView.getAdapter().getItem(p_Postion);
			mCategoryID = _ModelCategory.getCategoryID();
		}

	}

	private void ShowAccountBookSelectDialog() {
		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		View _View = LayoutInflater.from(this).inflate(R.layout.dialog_list, null);
		ListView _ListView = (ListView) _View.findViewById(R.id.ListViewSelect);
		AdapterAccountBookSelect _AdapterAccountBookSelect = new AdapterAccountBookSelect(this);
		_ListView.setAdapter(_AdapterAccountBookSelect);

		_Builder.setTitle(R.string.ButtonTextSelectAccountBook);
		_Builder.setNegativeButton(R.string.ButtonTextBack, null);
		_Builder.setView(_View);
		AlertDialog _AlertDialog = _Builder.create();
		_AlertDialog.show();
		_ListView.setOnItemClickListener(new OnAccountBookItemClickListener(_AlertDialog));
	}

	private class OnAccountBookItemClickListener implements AdapterView.OnItemClickListener {
		private AlertDialog m_AlertDialog;

		public OnAccountBookItemClickListener(AlertDialog p_AlertDialog) {
			m_AlertDialog = p_AlertDialog;
		}

		@Override
		public void onItemClick(AdapterView p_AdapterView, View arg1, int p_Position, long arg3) {
			ModelAccountBook _ModelAccountBook = (ModelAccountBook) ((Adapter) p_AdapterView.getAdapter())
					.getItem(p_Position);
			etAccountBookName.setText(_ModelAccountBook.getAccountBookName());
			mAccountBookID = _ModelAccountBook.getAccountBookID();
			m_AlertDialog.dismiss();
		}
	}

	private void ShowCategorySelectDialog() {
		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		View _View = LayoutInflater.from(this).inflate(R.layout.category_select_list, null);
		ExpandableListView _ExpandableListView = (ExpandableListView) _View.findViewById(R.id.ExpandableListViewCategory);
		AdapterCategory _AdapterCategorySelect = new AdapterCategory(this);
		_ExpandableListView.setAdapter(_AdapterCategorySelect);

		_Builder.setIcon(R.drawable.category_small_icon);
		_Builder.setTitle(R.string.ButtonTextSelectCategory);
		_Builder.setNegativeButton(R.string.ButtonTextBack, null);
		_Builder.setView(_View);
		AlertDialog _AlertDialog = _Builder.create();
		_AlertDialog.show();
		_ExpandableListView.setOnGroupClickListener(new OnCategoryGroupItemClickListener(_AlertDialog,
				_AdapterCategorySelect));
		_ExpandableListView.setOnChildClickListener(new OnCategoryChildItemClickListener(_AlertDialog,
				_AdapterCategorySelect));
	}

	private class OnCategoryGroupItemClickListener implements OnGroupClickListener {
		private AlertDialog m_AlertDialog;
		private AdapterCategory m_AdapterCategory;

		public OnCategoryGroupItemClickListener(AlertDialog p_AlertDialog, AdapterCategory p_AdapterCategory) {
			m_AlertDialog = p_AlertDialog;
			m_AdapterCategory = p_AdapterCategory;
		}

		@Override
		public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
			int _Count = m_AdapterCategory.getChildrenCount(groupPosition);
			if (_Count == 0) {
				ModelCategory _ModelCategory = (ModelCategory) m_AdapterCategory.getGroup(groupPosition);
				actvCategoryName.setText(_ModelCategory.getCategoryName());
				mCategoryID = _ModelCategory.getCategoryID();
				m_AlertDialog.dismiss();
			}
			return false;
		}

	}

	private class OnCategoryChildItemClickListener implements OnChildClickListener {
		private AlertDialog m_AlertDialog;
		private AdapterCategory m_AdapterCategory;

		public OnCategoryChildItemClickListener(AlertDialog p_AlertDialog, AdapterCategory p_AdapterCategory) {
			m_AlertDialog = p_AlertDialog;
			m_AdapterCategory = p_AdapterCategory;
		}

		@Override
		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
			ModelCategory _ModelCategory = (ModelCategory) m_AdapterCategory.getChild(groupPosition, childPosition);
			actvCategoryName.setText(_ModelCategory.getCategoryName());
			mCategoryID = _ModelCategory.getCategoryID();
			m_AlertDialog.dismiss();
			return false;
		}

	}

	private void showDateSelectDialog(int p_Year, int p_Month, int p_Day) {
		(new DatePickerDialog(this, new OnDateSelectedListener(), p_Year, p_Month, p_Day)).show();
	}

	private class OnDateSelectedListener implements OnDateSetListener {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			Date _Date = new Date(year - 1900, monthOfYear, dayOfMonth);
			etPayoutDate.setText(DateTools.getFormatDateTime(_Date, "yyyy-MM-dd"));
		}
	}

	private void ShowPayoutTypeSelectDialog() {
		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		View _View = LayoutInflater.from(this).inflate(R.layout.payout_type_select_list, null);

		ListView _ListView = (ListView) _View.findViewById(R.id.ListViewPayoutType);

		_Builder.setTitle(R.string.ButtonTextSelectPayoutType);
		_Builder.setNegativeButton(R.string.ButtonTextBack, null);
		_Builder.setView(_View);

		AlertDialog _AlertDialog = _Builder.create();
		_AlertDialog.show();

		_ListView.setOnItemClickListener(new OnPayoutTypeItemClickListener(_AlertDialog));
	}

	private class OnPayoutTypeItemClickListener implements AdapterView.OnItemClickListener {
		private AlertDialog m_AlertDialog;

		public OnPayoutTypeItemClickListener(AlertDialog p_AlertDialog) {
			m_AlertDialog = p_AlertDialog;
		}

		@Override
		public void onItemClick(AdapterView p_AdapterView, View arg1, int p_Position, long arg3) {
			String _PayoutType = (String) p_AdapterView.getAdapter().getItem(p_Position);
			etPayoutType.setText(_PayoutType);
			etPayoutUser.setText("");
			mPayoutUserID = "";
			m_AlertDialog.dismiss();
		}
	}

	private void showUserSelectDialog(String p_PayoutType) {
		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		View _View = LayoutInflater.from(this).inflate(R.layout.user, null);
		LinearLayout _LinearLayout = (LinearLayout) _View.findViewById(R.id.LinearLayoutMain);
		_LinearLayout.setBackgroundResource(R.drawable.blue);
		ListView _ListView = (ListView) _View.findViewById(R.id.lvUserList);
		AdapterUser _AdapterUser = new AdapterUser(this);
		_ListView.setAdapter(_AdapterUser);

		_Builder.setIcon(R.drawable.user_small_icon);
		_Builder.setTitle(R.string.ButtonTextSelectUser);
		_Builder.setNegativeButton(R.string.ButtonTextBack, new OnSelectUserBack());
		_Builder.setView(_View);
		AlertDialog _AlertDialog = _Builder.create();
		_AlertDialog.show();
		_ListView.setOnItemClickListener(new OnUserItemClickListener(_AlertDialog, p_PayoutType));
	}

	private class OnSelectUserBack implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			etPayoutUser.setText("");
			String _Name = "";
			mPayoutUserID = "";
			if (mUserSelectedList != null) {
				for (int i = 0; i < mUserSelectedList.size(); i++) {
					_Name += mUserSelectedList.get(i).getUserName() + ",";
					mPayoutUserID += mUserSelectedList.get(i).getUserID() + ",";
				}
				etPayoutUser.setText(_Name);
			}

			mItemColor = null;
			mUserSelectedList = null;
		}
	}

	private class OnUserItemClickListener implements AdapterView.OnItemClickListener {
		private AlertDialog m_AlertDialog;
		private String m_PayoutType;

		public OnUserItemClickListener(AlertDialog p_AlertDialog, String p_PayoutType) {
			m_AlertDialog = p_AlertDialog;
			m_PayoutType = p_PayoutType;
		}

		@Override
		public void onItemClick(AdapterView p_AdapterView, View arg1, int p_Position, long arg3) {
			// ModelUser _ModelUser =
			// (ModelUser)((Adapter)p_AdapterView.getAdapter()).getItem(p_Position);
			// ((OnListSelectListener)ActivityBase.this).OnSelected(_ModelUser,"User");
			// m_AlertDialog.dismiss();

			String _PayoutTypeArr[] = getResources().getStringArray(R.array.PayoutType);
			ModelUser _ModelUser = (ModelUser) ((Adapter) p_AdapterView.getAdapter()).getItem(p_Position);
			System.out.println(arg1.getTag().toString());

			if (m_PayoutType.equals(_PayoutTypeArr[0]) || m_PayoutType.equals(_PayoutTypeArr[1])) { // ................??
																									// null
				RelativeLayout _Main = (RelativeLayout) arg1.findViewById(R.id.RelativeLayoutMain);

				if (mItemColor == null && mUserSelectedList == null) {
					mItemColor = new ArrayList<RelativeLayout>();
					mUserSelectedList = new ArrayList<ModelUser>();
				}

				if (mItemColor.contains(_Main)) {
					_Main.setBackgroundResource(R.drawable.blue);
					mItemColor.remove(_Main);
					mUserSelectedList.remove(_ModelUser);
				} else {
					_Main.setBackgroundResource(R.drawable.red);
					mItemColor.add(_Main);
					mUserSelectedList.add(_ModelUser);
				}

				// if(m_PayoutType.equals(_PayoutTypeArr[1]))
				// {
				// if(m_UserSelectedList.size() == 2)
				// {
				// ((OnListSelectListener)ActivityBase.this).OnSelected(m_UserSelectedList,"User");
				// m_AlertDialog.dismiss();
				// }
				// }
				return;
			}

			if (m_PayoutType.equals(_PayoutTypeArr[2])) {
				mUserSelectedList = new ArrayList<ModelUser>();
				mUserSelectedList.add(_ModelUser);
				etPayoutUser.setText("");
				String _Name = "";
				mPayoutUserID = "";
				for (int i = 0; i < mUserSelectedList.size(); i++) {
					_Name += mUserSelectedList.get(i).getUserName() + ",";
					mPayoutUserID += mUserSelectedList.get(i).getUserID() + ",";
				}
				etPayoutUser.setText(_Name);

				mItemColor = null;
				mUserSelectedList = null;
				m_AlertDialog.dismiss();
			}
		}
	}

}
