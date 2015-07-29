package cc.aaa.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;
import cc.aaa.R;
import cc.aaa.activity.base.ActivityFrame;
import cc.aaa.adapter.AdapterAccountBookSelect;
import cc.aaa.adapter.AdapterPayout;
import cc.aaa.business.BusinessAccountBook;
import cc.aaa.business.BusinessPayout;
import cc.aaa.controls.SlideMenuView.OnSlideMenuListener;
import cc.aaa.model.ModelAccountBook;
import cc.aaa.model.ModelPayout;
import cc.aaa.model.SlideMenuItem;

public class ActivityPayout extends ActivityFrame implements OnSlideMenuListener {

	private ListView lvPayoutList;
	private ModelPayout mSelectModelPayout;
	private BusinessPayout mBusinessPayout;
	private AdapterPayout mAdapterPayout;
	private ModelAccountBook mAccountBook;
	private BusinessAccountBook mBusinessAccountBook;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AppendMainBody(R.layout.payout_list);
		
		initVariable();
		initView();
		initListeners();
		bindData();
		createSlideMenu(R.array.SlideMenuPayout);
	}
	
	private void setTitle() {
		int _Count = lvPayoutList.getCount();
		String _Titel = getString(R.string.ActivityTitlePayout, new Object[]{mAccountBook.getAccountBookName(),_Count});
		setTopBarTitle(_Titel);
	}

	protected void initView() {
		lvPayoutList = (ListView) findViewById(R.id.lvPayoutList);
	}
	/**
	 * 注册上下文菜单
	 */
	protected void initListeners() {
		registerForContextMenu(lvPayoutList);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu p_ContextMenu, View p_View, ContextMenuInfo p_MenuInfo) {
		//获取当前被选择的菜单项的信息
		AdapterContextMenuInfo _AdapterContextMenuInfo = (AdapterContextMenuInfo)p_MenuInfo;
		ListAdapter _ListAdapter = lvPayoutList.getAdapter();
		mSelectModelPayout = (ModelPayout)_ListAdapter.getItem(_AdapterContextMenuInfo.position);
		p_ContextMenu.setHeaderIcon(R.drawable.payout_small_icon);
		p_ContextMenu.setHeaderTitle(mSelectModelPayout.getCategoryName());
		int _ItemID[] = {1,2};
		createMenu(p_ContextMenu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem p_Item) {
		switch (p_Item.getItemId()) {
		case 1:
			Intent _Intent = new Intent(this, ActivityPayoutAddOrEdit.class);
			_Intent.putExtra("ModelPayout", mSelectModelPayout);
			this.startActivityForResult(_Intent, 1);
			break;
		case 2:
			delete(mSelectModelPayout);
			break;
		default:
			break;
		}
		
		return super.onContextItemSelected(p_Item);
	}

	protected void initVariable() {
		mBusinessPayout = new BusinessPayout(ActivityPayout.this);
		mBusinessAccountBook = new BusinessAccountBook(ActivityPayout.this);
		mAccountBook = mBusinessAccountBook.getDefaultModelAccountBook();
		mAdapterPayout = new AdapterPayout(this,mAccountBook.getAccountBookID());
	}

	protected void bindData()
	{
		AdapterPayout _AdapterPayout = new AdapterPayout(this,mAccountBook.getAccountBookID());
		lvPayoutList.setAdapter(_AdapterPayout);
		setTitle();
	}

	@Override
	public void onSlideMenuItemClick(View p_View, SlideMenuItem p_SlideMenuItem) {
		slideMenuToggle();
		if (p_SlideMenuItem.getmItemID() == 0) {
			showAccountBookSelectDialog();
		}
	}
	
	private void showAccountBookSelectDialog()
	{
		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		View _View = LayoutInflater.from(this).inflate(R.layout.dialog_list, null);
		ListView _ListView = (ListView)_View.findViewById(R.id.ListViewSelect);
		AdapterAccountBookSelect _AdapterAccountBookSelect = new AdapterAccountBookSelect(this);
		_ListView.setAdapter(_AdapterAccountBookSelect);

		_Builder.setTitle(R.string.ButtonTextSelectAccountBook);
		_Builder.setNegativeButton(R.string.ButtonTextBack, null);
		_Builder.setView(_View);
		AlertDialog _AlertDialog = _Builder.create();
		_AlertDialog.show();
		_ListView.setOnItemClickListener(new OnAccountBookItemClickListener(_AlertDialog));
	}
	
	private class OnAccountBookItemClickListener implements AdapterView.OnItemClickListener
	{
		private AlertDialog m_AlertDialog;
		public OnAccountBookItemClickListener(AlertDialog p_AlertDialog)
		{
			m_AlertDialog = p_AlertDialog;
		}
		@Override
		public void onItemClick(AdapterView p_AdapterView, View arg1, int p_Position,
				long arg3) {
			mAccountBook = (ModelAccountBook)((Adapter)p_AdapterView.getAdapter()).getItem(p_Position);
			bindData();
			m_AlertDialog.dismiss();
		}
	}
	
	public void delete(ModelPayout p_ModelPayout)
	{
		Object _Object[] = {p_ModelPayout.getCategoryName()}; 	
		String _Message = getString(R.string.DialogMessagePayoutDelete,_Object);
	 	showAlertDialog(R.string.DialogTitleDelete,_Message,new OnDeleteClickListener());
	 
	} 
	
	private class OnDeleteClickListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			Boolean _Result = mBusinessPayout.deletePayoutByPayoutID(mSelectModelPayout.getPayoutID());
			if(_Result == true)
			{
//				mAdapterPayout.GetList().remove(mListViewPosition);
//				mAdapterPayout.notifyDataSetChanged();
				bindData();
			}
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		bindData();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
