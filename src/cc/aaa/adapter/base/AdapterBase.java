package cc.aaa.adapter.base;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AdapterBase extends BaseAdapter {
	private List mList;
	private Context mContext;
	private LayoutInflater mLayoutInflater;

	public AdapterBase(List pList, Context pContext) {
		mList = pList;
		mContext = pContext;
		mLayoutInflater = LayoutInflater.from(pContext);
	}

	public List getList() {
		return mList;
	}

	public void setList(List mList) {
		this.mList = mList;
	}

	public Context getContext() {
		return mContext;
	}

	public LayoutInflater getLayoutInflater() {
		return mLayoutInflater;
	}

	@Override
	public int getCount() {
		return mList.size();
	}
	 
	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
