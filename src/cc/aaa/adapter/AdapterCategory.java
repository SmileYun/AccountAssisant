package cc.aaa.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import cc.aaa.R;
import cc.aaa.business.BusinessCategory;
import cc.aaa.model.ModelCategory;

public class AdapterCategory extends BaseExpandableListAdapter {

	private class groupHolder {
		TextView name;
		TextView count;
	}

	private class childHolder {
		TextView name;
	}

	private Context mContext;
	private List mList;
	private BusinessCategory mBusinessCategory;
	public List _ChildCountOfGroup;

	public AdapterCategory(Context pContext) {
		mBusinessCategory = new BusinessCategory(pContext);
		mContext = pContext;
		mList = mBusinessCategory.getNotHideRootCategory();
		_ChildCountOfGroup = new ArrayList();
	}

	public Integer getChildCountOfGroup(int groupPosition) {
		return (Integer) _ChildCountOfGroup.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ModelCategory _ModelCategory = (ModelCategory) getGroup(groupPosition);
		List _List = mBusinessCategory.getNotHideCategoryListByParentID(_ModelCategory.getCategoryID());
		return _List.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		groupHolder _GroupHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.category_group_list_item, null);
			_GroupHolder = new groupHolder();
			_GroupHolder.name = (TextView) convertView.findViewById(R.id.tvCategoryName);
			_GroupHolder.count = (TextView) convertView.findViewById(R.id.tvCount);
			convertView.setTag(_GroupHolder);
		} else {
			_GroupHolder = (groupHolder) convertView.getTag();
		}
		ModelCategory _ModelCategory = (ModelCategory) getGroup(groupPosition);
		_GroupHolder.name.setText(_ModelCategory.getCategoryName());
		int _Count = mBusinessCategory.getNotHideCountByParentID(_ModelCategory.getCategoryID());
		_GroupHolder.count.setText(mContext.getString(R.string.TextViewTextChildrenCategory, _Count));
		_ChildCountOfGroup.add(_Count);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		childHolder _ChildHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.category_children_list_item, null);
			_ChildHolder = new childHolder();
			_ChildHolder.name = (TextView) convertView.findViewById(R.id.tvCategoryName);
			convertView.setTag(_ChildHolder);
		} else {
			_ChildHolder = (childHolder) convertView.getTag();
		}
		ModelCategory _ModelCategory = (ModelCategory) getChild(groupPosition, childPosition);
		_ChildHolder.name.setText(_ModelCategory.getCategoryName());
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		ModelCategory _ModelCategory = (ModelCategory) getGroup(groupPosition);
		List _List = mBusinessCategory.getNotHideCategoryListByParentID(_ModelCategory.getCategoryID());
		return _List.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return (ModelCategory) mList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return (long) groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		// 行是否具有唯一id
		// 是否指定分组视图及其子视图的ID对应的后台数据改变也会保持该ID
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// //行是否可选
		return true;
	}

}
