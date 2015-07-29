package cc.aaa.business.base;

import android.content.Context;

public class BusinessBase {
	private Context mContext;
	
	protected BusinessBase(Context mContext){
		this.mContext = mContext;
	}
	
	/**
	 * 获取资源文件中的字符等内容所需要
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
