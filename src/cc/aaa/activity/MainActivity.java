package cc.aaa.activity;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cc.aaa.R;
import cc.aaa.activity.base.ActivityFrame;
import cc.aaa.adapter.AdapterGrid;
import cc.aaa.controls.SlideMenuView.OnSlideMenuListener;
import cc.aaa.model.SlideMenuItem;

public class MainActivity extends ActivityFrame implements OnSlideMenuListener {
	AdapterGrid mAdapterAppGrid;
	GridView gvAppGrid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hideTitleBackButton();
		AppendMainBody(R.layout.main_body);
		InitVariable();
		InitView();
		InitListeners();
		BindData();
		createSlideMenu(R.array.SlideMenuActivityMain);
		// StartService();

	}

	/**
	 * 绑定Adapter
	 */
	private void BindData() {
		gvAppGrid.setAdapter(mAdapterAppGrid);
	}

	/**
	 * 设置监听器
	 */
	private void InitListeners() {
		gvAppGrid.setOnItemClickListener(new onAppGridItemClickListener());
	}

	private class onAppGridItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id) {

			String _MenuName = (String) parent.getAdapter().getItem(position);
			if (_MenuName.equals(getString(R.string.appGridTextUserManage))) {// 人员管理
				openActivity(UserActivity.class);
				return;
			}
			if (_MenuName.equals(getString(R.string.appGridTextAccountBookManage))) {// 账本管理
				openActivity(AccountBookActivity.class);
				return;
			}
			if (_MenuName.equals(getString(R.string.appGridTextCategoryManage))) {// 类别管理
				openActivity(ActivityCategory.class);
				return;
			}
			if (_MenuName.equals(getString(R.string.appGridTextPayoutAdd))) {// 记录消费
				openActivity(ActivityPayoutAddOrEdit.class);
				return;
			}
			if (_MenuName.equals(getString(R.string.appGridTextPayoutManage))){// 查询消费
			
				openActivity(ActivityPayout.class);
				return;
			}
			if (_MenuName.equals(getString(R.string.appGridTextStatisticsManage))) {
				// openActivity(ActivityStatistics.class);
				return;
			}
		}

	}

	public void InitView() {
		gvAppGrid = (GridView) findViewById(R.id.gvAppGrid);
	}

	/**
	 * 初始化变量，即绑定数据源
	 */
	public void InitVariable() {
		List _List = new ArrayList();
		String[] mImageString = new String[6];
		mImageString[0] = this.getString(R.string.appGridTextPayoutAdd);
		mImageString[1] = this.getString(R.string.appGridTextPayoutManage);
		mImageString[2] = this.getString(R.string.appGridTextStatisticsManage);
		mImageString[3] = this.getString(R.string.appGridTextAccountBookManage);
		mImageString[4] = this.getString(R.string.appGridTextCategoryManage);
		mImageString[5] = this.getString(R.string.appGridTextUserManage);
		for (int i = 0; i < mImageString.length; i++) {
			_List.add(mImageString[i]);
		}
		mAdapterAppGrid = new AdapterGrid(_List, this);
	}

	@Override
	public void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem) {
		slideMenuToggle();
	}
}
