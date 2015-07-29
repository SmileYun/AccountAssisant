package cc.aaa.model;

import java.util.Date;

public class ModelAccountBook {
	//账本表主键ID
	private int mAccountBookID;
	//账本名称
	private String mAccountBookName;
	//添加日期
	private Date mCreateDate = new Date();
	//状态 0失效 1启用
	private int mState = 1;
	//是否默账本 0否1是
	private int mIsDefault;
	/**
	 * 账本表主键ID
	 */
	public int getAccountBookID() {
		return mAccountBookID;
	}
	/**
	 * 账本表主键ID
	 */
	public void setAccountBookID(int pAccountBookID) {
		this.mAccountBookID = pAccountBookID;
	}
	/**
	 * 账本名称
	 */
	public String getAccountBookName() {
		return mAccountBookName;
	}
	/**
	 * 账本名称
	 */
	public void setAccountBookName(String pAccountBookName) {
		this.mAccountBookName = pAccountBookName;
	}
	/**
	 * 添加日期
	 */
	public Date getCreateDate() {
		return mCreateDate;
	}
	/**
	 * 添加日期
	 */
	public void setCreateDate(Date pCreateDate) {
		this.mCreateDate = pCreateDate;
	}
	/**
	 * 状态 0失效 1启用
	 */
	public int getState() {
		return mState;
	}
	/**
	 * 状态 0失效 1启用
	 */
	public void setState(int pState) {
		this.mState = pState;
	}
	/**
	 * 是否默账本 0否1是
	 */
	public int getIsDefault() {
		return mIsDefault;
	}
	/**
	 * 是否默账本 0否1是
	 */
	public void setIsDefault(int pIsDefault) {
		this.mIsDefault = pIsDefault;
	}
}
