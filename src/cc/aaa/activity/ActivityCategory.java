package cc.aaa.activity;

import java.io.Serializable;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.Toast;
import cc.aaa.R;
import cc.aaa.activity.base.ActivityFrame;
import cc.aaa.adapter.AdapterCategory;
import cc.aaa.business.BusinessCategory;
import cc.aaa.controls.SlideMenuView.OnSlideMenuListener;
import cc.aaa.model.ModelCategory;
import cc.aaa.model.ModelCategoryTotal;
import cc.aaa.model.SlideMenuItem;

public class ActivityCategory extends ActivityFrame implements OnSlideMenuListener {
	private ExpandableListView elvCategoryList;
	private ModelCategory mSelectModelCategory;
	private BusinessCategory mBusinessCategory;
	private AdapterCategory mAdapterCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AppendMainBody(R.layout.category);
		initVariable();
		initView();
		initListeners();
		bindData();
		createSlideMenu(R.array.SlideMenuCategory);
	}

	protected void initVariable() {
		mBusinessCategory = new BusinessCategory(ActivityCategory.this);
	}

	protected void initView() {
		elvCategoryList = (ExpandableListView) findViewById(R.id.ExpandableListViewCategory);
	}

	protected void initListeners() {
		// 注册上下文菜单
		registerForContextMenu(elvCategoryList);
	}

	protected void bindData() {
		mAdapterCategory = new AdapterCategory(this);
		elvCategoryList.setAdapter(mAdapterCategory);
		setTitle();
	}

	private void setTitle() {
		int _Count = mBusinessCategory.getNotHideCount();
		String _Titel = getString(R.string.ActivityTitleCategory, new Object[] { _Count });
		setTopBarTitle(_Titel);
	}

	@Override
	public void onCreateContextMenu(ContextMenu pContextMenu, View pView, ContextMenuInfo pMenuInfo) {
		// 获取菜单信息
		ExpandableListContextMenuInfo _ExpandableListContextMenuInfo = (ExpandableListContextMenuInfo) pMenuInfo;
		long _Position = _ExpandableListContextMenuInfo.packedPosition;
		int _Type = ExpandableListView.getPackedPositionType(_Position);
		int _GroupPosition = ExpandableListView.getPackedPositionGroup(_Position);

		switch (_Type) {
		case ExpandableListView.PACKED_POSITION_TYPE_GROUP:
			mSelectModelCategory = (ModelCategory) mAdapterCategory.getGroup(_GroupPosition);
			break;
		case ExpandableListView.PACKED_POSITION_TYPE_CHILD:
			int _ChildPosition = ExpandableListView.getPackedPositionChild(_Position);
			mSelectModelCategory = (ModelCategory) mAdapterCategory.getChild(_GroupPosition, _ChildPosition);
			break;
		default:
			break;
		}

		pContextMenu.setHeaderIcon(R.drawable.category_small_icon);
		if (mSelectModelCategory != null) {
			pContextMenu.setHeaderTitle(mSelectModelCategory.getCategoryName());
		}
		
		createMenu(pContextMenu);
		
		pContextMenu.add(0, 3, 3, R.string.ActivityCategoryTotal);
		
		if (mAdapterCategory.getChildCountOfGroup(_GroupPosition) != 0 && mSelectModelCategory.getParentID() == 0) {
			pContextMenu.findItem(2).setEnabled(false);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem pItem) {
		Intent _Intent;
		switch (pItem.getItemId()) {
		case 1:
			_Intent = new Intent(this, ActivityCategoryAddOrEdit.class);
			_Intent.putExtra("ModelCategory", mSelectModelCategory);
			this.startActivityForResult(_Intent, 1);
			break;
		case 2:
			delete(mSelectModelCategory);
			break;
		case 3:
			List<ModelCategoryTotal> _List = mBusinessCategory.categoryTotalByParentID(mSelectModelCategory.getCategoryID());
			_Intent = new Intent();
			_Intent.putExtra("Total", (Serializable) _List);
			_Intent.setClass(this, ActivityCategoryChart.class);
			startActivity(_Intent);
			break;
		default:
			break;
		}
		return super.onContextItemSelected(pItem);
	}

	@Override
	public void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem) {
		slideMenuToggle();

		if (pSlideMenuItem.getmItemID() == 0) {
			Intent _Intent = new Intent(this, ActivityCategoryAddOrEdit.class);
			this.startActivityForResult(_Intent, 1);

			return;
		}

		if (pSlideMenuItem.getmItemID() == 1) {
			List<ModelCategoryTotal> _List = mBusinessCategory.categoryTotalByRootCategory();
			Intent _Intent = new Intent();
			_Intent.putExtra("Total", (Serializable) _List);
			_Intent.setClass(this, ActivityCategoryChart.class);
			startActivity(_Intent);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		bindData();
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void delete(ModelCategory pModelCategory) {
		Object _Object[] = { pModelCategory.getCategoryName() };
		String _Message = getString(R.string.DialogMessageCategoryDelete, _Object);

		showAlertDialog(R.string.DialogTitleDelete, _Message, new OnDeleteClickListener(pModelCategory));
	}

	private class OnDeleteClickListener implements DialogInterface.OnClickListener {
		private ModelCategory m_ModelCategory;

		public OnDeleteClickListener(ModelCategory pModelCategory) {
			m_ModelCategory = pModelCategory;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			Boolean _Result;
			_Result = mBusinessCategory.hideCategoryByByPath(m_ModelCategory.getPath());
			if (_Result == true) {
				bindData();
			} else {
				Toast.makeText(getApplicationContext(), R.string.TipsDeleteFail, 1).show();
			}
		}

	}
}
