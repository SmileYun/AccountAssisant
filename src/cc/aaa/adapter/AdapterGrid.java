package cc.aaa.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cc.aaa.R;
import cc.aaa.adapter.base.AdapterBase;

public class AdapterGrid extends AdapterBase {

	private Context mContext;
	private List mList;

	/**
	 * 适配GridView ， 绑定数据并显示
	 * 
	 * @param pList
	 * @param pContext
	 */
	public AdapterGrid(List pList, Context pContext) {
		super(pList, pContext);
		mContext = pContext;
		mList = pList;
	}

	private Integer[] mImageInteger = { R.drawable.grid_payout,
			R.drawable.grid_bill, R.drawable.grid_report,
			R.drawable.grid_account_book, R.drawable.grid_category,
			R.drawable.grid_user, };

	private class Holder {
		ImageView imageView;
		TextView textView;
	}

	@SuppressLint("NewApi") public View getView(int position, View convertView, ViewGroup parent) {
		Holder _Holder;
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(R.layout.main_body_item,
					null);
			_Holder = new Holder();
			_Holder.imageView = (ImageView) convertView.findViewById(R.id.ivIcon);
			_Holder.textView = (TextView) convertView.findViewById(R.id.tvName);
			convertView.setTag(_Holder);
		} else {
			_Holder = (Holder) convertView.getTag();
		}

		_Holder.imageView.setImageResource(mImageInteger[position]);
		
		int h = 96, w = 96;
		
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1 ) {
			Point p = new Point();
			parent.getDisplay().getSize(p);
			h = (int)(p.x * 0.134f);
			w = (int)(p.x * 0.134f);
		}
		
		LinearLayout.LayoutParams _LayoutParams = new LinearLayout.LayoutParams(
				h, w);
		_Holder.imageView.setLayoutParams(_LayoutParams);
		/**
		 * 按X/Y轴缩放
		 */
		_Holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);

		_Holder.textView.setText((String) mList.get(position));

		return convertView;
	}

}
