package cc.aaa.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import cc.aaa.R;
import cc.aaa.activity.base.ActivityFrame;
import cc.aaa.business.BusinessCategory;
import cc.aaa.model.ModelCategory;
import cc.aaa.utility.RegexTools;

public class ActivityCategoryAddOrEdit extends ActivityFrame implements android.view.View.OnClickListener {

	private Button btnSave;
	private Button btnCancel;

	private BusinessCategory mBusinessCategory;
	private ModelCategory mModelCategory;
	private Spinner spParentID;
	private EditText etCategoryName;

	@Override
	public void onClick(View v) {
		int _ID = v.getId();

		switch (_ID) {
		case R.id.btnSave:
			AddOrEditCategory();
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

		AppendMainBody(R.layout.category_add_or_edit);
		removeBottomBox();
		initView();
		initVariable();
		bindData();
		setTitle();
		initListeners();
	}

	private void setTitle() {
		String _Titel;
		if (mModelCategory == null) {
			_Titel = getString(R.string.ActivityTitleCategoryAddOrEdit, new Object[] { getString(R.string.TitleAdd) });
		} else {
			_Titel = getString(R.string.ActivityTitleCategoryAddOrEdit, new Object[] { getString(R.string.TitleEdit) });
			initData(mModelCategory);
		}

		setTopBarTitle(_Titel);
	}

	protected void initView() {
		btnSave = (Button) findViewById(R.id.btnSave);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		etCategoryName = (EditText) findViewById(R.id.etCategoryName);
		spParentID = (Spinner) findViewById(R.id.SpinnerParentID);
	}

	protected void initListeners() {
		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}

	protected void initVariable() {
		mBusinessCategory = new BusinessCategory(this);
		mModelCategory = (ModelCategory) getIntent().getSerializableExtra("ModelCategory");
	}

	protected void bindData() {
		ArrayAdapter<ModelCategory> _ArrayAdapter = mBusinessCategory.getRootCategoryArrayAdapter();
		spParentID.setAdapter(_ArrayAdapter);
	}

	private void initData(ModelCategory p_ModelCategory) {
		etCategoryName.setText(p_ModelCategory.getCategoryName());
		ArrayAdapter _ArrayAdapter = (ArrayAdapter) spParentID.getAdapter();

		if (p_ModelCategory.getParentID() != 0) {
			int _Position = 0;
			for (int i = 1; i < _ArrayAdapter.getCount(); i++) {
				ModelCategory _ModelCategory = (ModelCategory) _ArrayAdapter.getItem(i);
				if (_ModelCategory.getCategoryID() == p_ModelCategory.getParentID()) {
					_Position = _ArrayAdapter.getPosition(_ModelCategory);
				}
			}
			spParentID.setSelection(_Position);
		} else {
			int _Count = mBusinessCategory.getNotHideCountByParentID(p_ModelCategory.getCategoryID());
			if (_Count != 0) {
				spParentID.setEnabled(false);
			}
		}
	}

	private void AddOrEditCategory() {
		String _CategoryName = etCategoryName.getText().toString().trim();
		Boolean _CheckResult = RegexTools.IsChineseEnglishNum(_CategoryName);
		if (!_CheckResult) {
			Toast.makeText(this,getString(R.string.CheckDataTextChineseEnglishNum,
							new Object[] { getString(R.string.TextViewTextCategoryName) }), 1).show();
			return;
		}

		if (mModelCategory == null) {
			mModelCategory = new ModelCategory();
			mModelCategory.setTypeFlag(getString(R.string.PayoutTypeFlag));
			mModelCategory.setPath("");
		}
		mModelCategory.setCategoryName(_CategoryName);
		if (!spParentID.getSelectedItem().toString().equals("--ÇëÑ¡Ôñ--")) {
			ModelCategory _ModelCategory = (ModelCategory) spParentID.getSelectedItem();
			if (_ModelCategory != null) {
				mModelCategory.setParentID(_ModelCategory.getCategoryID());
			}
		} else {
			mModelCategory.setParentID(0);
		}

		Boolean _Result = false;

		if (mModelCategory.getCategoryID() == 0) {
			_Result = mBusinessCategory.insertCategory(mModelCategory);
		} else {
			_Result = mBusinessCategory.updateCategoryByCategoryID(mModelCategory);
		}

		if (_Result) {
			Toast.makeText(getApplicationContext(), getString(R.string.TipsAddSucceed), 1).show();
			finish();
		} else {
			Toast.makeText(getApplicationContext(), getString(R.string.TipsAddFail), 1).show();
		}
	}
}
