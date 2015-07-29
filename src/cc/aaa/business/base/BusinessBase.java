package cc.aaa.business.base;

import android.content.Context;

public class BusinessBase {
	private Context mContext;
	
	protected BusinessBase(Context mContext){
		this.mContext = mContext;
	}
	
	/**
	 * ��ȡ��Դ�ļ��е��ַ�����������Ҫ
	 * @param resID
	 * @return
	 */
	protected String getString(int resID){
		return mContext.getResources().getString(resID);
	}
	protected String getString(int resID,Object[] formatArgs){
		return mContext.getResources().getString(resID, formatArgs);
	}
	protected Context getContext() {
		return mContext;
	}
}
