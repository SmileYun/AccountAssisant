package cc.aaa.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cc.aaa.R;
import cc.aaa.adapter.base.AdapterBase;
import cc.aaa.model.SlideMenuItem;

public class AdapterSlideMenu extends AdapterBase{
	
	public AdapterSlideMenu(List pList, Context pContext) {
		super(pList, pContext); 
	}
	
	private class Hodler{
		TextView tvMenuName;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Hodler _Hodler;

		if (convertView==null) {
			convertView = getLayoutInflater().inflate(R.layout.slidemenu_list_item, null);
			_Hodler = new Hodler();
			_Hodler.tvMenuName = (TextView) convertView.findViewById(R.id.tvMenuName);
			convertView.setTag(_Hodler);
		}else {
			_Hodler = (Hodler) convertView.getTag();
		}
		
		SlideMenuItem _SlideMenuItem = (SlideMenuItem) getList().get(position);
		_Hodler.tvMenuName.setText(_SlideMenuItem.getmTitle());
		
		
		return convertView;
	}

}
