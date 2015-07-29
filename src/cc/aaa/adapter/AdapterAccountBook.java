package cc.aaa.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cc.aaa.R;
import cc.aaa.adapter.base.AdapterBase;
import cc.aaa.business.BusinessAccountBook;
import cc.aaa.business.base.BusinessBase;
import cc.aaa.model.ModelAccountBook;

public class AdapterAccountBook extends AdapterBase {

	public AdapterAccountBook(Context pContext) {
		super(null, pContext);
		BusinessAccountBook _BusinessAccountBook = new BusinessAccountBook(pContext);
		List<ModelAccountBook> _List = _BusinessAccountBook.getNotHideAccountBook();
		setList(_List);
	}

	private class Holder {
		ImageView ivIcon;
		TextView tvName;
		TextView tvTotal;
		TextView tvMoney;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder _Holder;
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(R.layout.account_book_list_item, null);
			_Holder = new Holder();
			_Holder.ivIcon = (ImageView) convertView.findViewById(R.id.ivAccountBookIcon);
			_Holder.tvName = (TextView) convertView.findViewById(R.id.tvAccountBookName);
			_Holder.tvTotal = (TextView) convertView.findViewById(R.id.tvTotal);
			_Holder.tvMoney = (TextView) convertView.findViewById(R.id.tvMoney);
			convertView.setTag(_Holder);
		} else {
			_Holder = (Holder) convertView.getTag();
		}

		// 获取绑定的数据源（数组）
		ModelAccountBook _ModelAccountBook = (ModelAccountBook) getList().get(position);
		if (_ModelAccountBook.getIsDefault() == 1) {
			_Holder.ivIcon.setImageResource(R.drawable.account_book_default);
		} else {
			_Holder.ivIcon.setImageResource(R.drawable.account_book_big_icon);
		}
		_Holder.tvName.setText(_ModelAccountBook.getAccountBookName());

		return convertView;
	}
}
