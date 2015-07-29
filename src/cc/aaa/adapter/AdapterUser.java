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
import cc.aaa.business.BusinessUser;
import cc.aaa.business.base.BusinessBase;
import cc.aaa.model.ModelUser;

public class AdapterUser extends AdapterBase {

	public AdapterUser(Context pContext) {
		super(null, pContext);
		BusinessUser _BusinessUser = new BusinessUser(pContext);
		List<ModelUser> _List = _BusinessUser.getNotHideUser();
		setList(_List);

	}

	private class Holder {
		ImageView imageView;
		TextView textView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder _Holder;
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(R.layout.user_item_lay, null);
			_Holder = new Holder();
			_Holder.imageView = (ImageView) convertView.findViewById(R.id.UserIcon);
			_Holder.textView = (TextView) convertView.findViewById(R.id.UserNameTv);
			convertView.setTag(_Holder);
		} else {
			_Holder = (Holder) convertView.getTag();
		}

		// 获取绑定的数据源（数组）
		ModelUser _ModelUser = (ModelUser) getList().get(position);

		_Holder.imageView.setImageResource(R.drawable.user_big_icon);
		_Holder.textView.setText(_ModelUser.getUserName());

		return convertView;
	}

}
