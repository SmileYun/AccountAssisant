package cc.aaa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import android.R.integer;

public class ModelPayout implements Serializable {
	/**
	 * 用到主外键关联，用到视图
	 */
	
	//支出表主键ID
	private int mPayoutID;
	//账本ID外键
	private int mAccountBookID;
	//账本名称
	private String mAccountBookName;
	//支出类别ID外键
	private int mCategoryID;
	//类别名称
	private String mCategoryName;
	//路径
	private String mPath;
	//付款方式ID外键
	private int mPayWayID;
	//消费地点ID外键
	private int mPlaceID;
	//消费金额
	private BigDecimal mAmount;
	//消费日期
	private Date mPayoutDate;
	//计算方式
	private String mPayoutType;
	//消费人ID外键
	private String mPayoutUserID;
	//备注
	private String mComment;
	//添加日期
	private Date mCreateDate = new Date();
	//状态 0失效 1启用
	private int mState = 1;
	/**
	 * 支出表主键ID
	 */
	public int getPayoutID() {
		return mPayoutID;
	}
	/**
	 * 支出表主键ID
	 */
	public void setPayoutID(int pPayoutID) {
		this.mPayoutID = pPayoutID;
	}
	/**
	 * 账本名称ID外键
	 */
	public int getAccountBookID() {
		return mAccountBookID;
	}
	/**
	 * 账本ID外键
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
	 * 支出类别ID外键
	 */
	public int getCategoryID() {
		return mCategoryID;
	}
	/**
	 * 支出类别ID外键
	 */
	public void setCategoryID(int pCategoryID) {
		this.mCategoryID = pCategoryID;
	}
	/**
	 * 路径
	 */
	public String getPath() {
		return mPath;
	}
	/**
	 * 路径
	 */
	public void setPath(String pPath) {
		this.mPath = pPath;
	}
	/**
	 * 账本名称
	 */
	public String getCategoryName() {
		return mCategoryName;
	}
	/**
	 * 账本名称
	 */
	public void setCategoryName(String pCategoryName) {
		this.mCategoryName = pCategoryName;
	}
	/**
	 * 	付款方式ID外键
	 */
	public int getPayWayID() {
		return mPayWayID;
	}
	/**
	 * 	付款方式ID外键
	 */
	public void setPayWayID(int pPayWayID) {
		this.mPayWayID = pPayWayID;
	}
	/**
	 * 消费地点ID外键
	 */
	public int getPlaceID() {
		return mPlaceID;
	}
	/**
	 * 消费地点ID外键
	 */
	public void setPlaceID(int pPlaceID) {
		this.mPlaceID = pPlaceID;
	}
	/**
	 * 消费金额
	 */
	public BigDecimal getAmount() {
		return mAmount;
	}
	/**
	 * 消费金额
	 */
	public void setAmount(BigDecimal pAmount) {
		this.mAmount = pAmount;
	}
	/**
	 * 消费日期
	 */
	public Date getPayoutDate() {
		return mPayoutDate;
	}
	/**
	 * 消费日期
	 */
	public void setPayoutDate(Date pPayoutDate) {
		this.mPayoutDate = pPayoutDate;
	}
	/**
	 * 计算方式
	 */
	public String getPayoutType() {
		return mPayoutType;
	}
	/**
	 * 计算方式
	 */
	public void setPayoutType(String pPayoutType) {
		this.mPayoutType = pPayoutType;
	}
	/**
	 * 消费人ID外键
	 */
	public String getPayoutUserID() {
		return mPayoutUserID;
	}
	/**
	 * 消费人ID外键
	 */
	public void setPayoutUserID(String pPayoutUserID) {
		this.mPayoutUserID = pPayoutUserID;
	}	
	/**
	 * 备注
	 */
	public String getComment() {
		return mComment;
	}
	/**
	 * 备注
	 */
	public void setComment(String pComment) {
		this.mComment = pComment;
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
}
