package cc.aaa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import android.R.integer;

public class ModelPayout implements Serializable {
	/**
	 * �õ�������������õ���ͼ
	 */
	
	//֧��������ID
	private int mPayoutID;
	//�˱�ID���
	private int mAccountBookID;
	//�˱�����
	private String mAccountBookName;
	//֧�����ID���
	private int mCategoryID;
	//�������
	private String mCategoryName;
	//·��
	private String mPath;
	//���ʽID���
	private int mPayWayID;
	//���ѵص�ID���
	private int mPlaceID;
	//���ѽ��
	private BigDecimal mAmount;
	//��������
	private Date mPayoutDate;
	//���㷽ʽ
	private String mPayoutType;
	//������ID���
	private String mPayoutUserID;
	//��ע
	private String mComment;
	//�������
	private Date mCreateDate = new Date();
	//״̬ 0ʧЧ 1����
	private int mState = 1;
	/**
	 * ֧��������ID
	 */
	public int getPayoutID() {
		return mPayoutID;
	}
	/**
	 * ֧��������ID
	 */
	public void setPayoutID(int pPayoutID) {
		this.mPayoutID = pPayoutID;
	}
	/**
	 * �˱�����ID���
	 */
	public int getAccountBookID() {
		return mAccountBookID;
	}
	/**
	 * �˱�ID���
	 */
	public void setAccountBookID(int pAccountBookID) {
		this.mAccountBookID = pAccountBookID;
	}
	/**
	 * �˱�����
	 */
	public String getAccountBookName() {
		return mAccountBookName;
	}
	/**
	 * �˱�����
	 */
	public void setAccountBookName(String pAccountBookName) {
		this.mAccountBookName = pAccountBookName;
	}
	/**
	 * ֧�����ID���
	 */
	public int getCategoryID() {
		return mCategoryID;
	}
	/**
	 * ֧�����ID���
	 */
	public void setCategoryID(int pCategoryID) {
		this.mCategoryID = pCategoryID;
	}
	/**
	 * ·��
	 */
	public String getPath() {
		return mPath;
	}
	/**
	 * ·��
	 */
	public void setPath(String pPath) {
		this.mPath = pPath;
	}
	/**
	 * �˱�����
	 */
	public String getCategoryName() {
		return mCategoryName;
	}
	/**
	 * �˱�����
	 */
	public void setCategoryName(String pCategoryName) {
		this.mCategoryName = pCategoryName;
	}
	/**
	 * 	���ʽID���
	 */
	public int getPayWayID() {
		return mPayWayID;
	}
	/**
	 * 	���ʽID���
	 */
	public void setPayWayID(int pPayWayID) {
		this.mPayWayID = pPayWayID;
	}
	/**
	 * ���ѵص�ID���
	 */
	public int getPlaceID() {
		return mPlaceID;
	}
	/**
	 * ���ѵص�ID���
	 */
	public void setPlaceID(int pPlaceID) {
		this.mPlaceID = pPlaceID;
	}
	/**
	 * ���ѽ��
	 */
	public BigDecimal getAmount() {
		return mAmount;
	}
	/**
	 * ���ѽ��
	 */
	public void setAmount(BigDecimal pAmount) {
		this.mAmount = pAmount;
	}
	/**
	 * ��������
	 */
	public Date getPayoutDate() {
		return mPayoutDate;
	}
	/**
	 * ��������
	 */
	public void setPayoutDate(Date pPayoutDate) {
		this.mPayoutDate = pPayoutDate;
	}
	/**
	 * ���㷽ʽ
	 */
	public String getPayoutType() {
		return mPayoutType;
	}
	/**
	 * ���㷽ʽ
	 */
	public void setPayoutType(String pPayoutType) {
		this.mPayoutType = pPayoutType;
	}
	/**
	 * ������ID���
	 */
	public String getPayoutUserID() {
		return mPayoutUserID;
	}
	/**
	 * ������ID���
	 */
	public void setPayoutUserID(String pPayoutUserID) {
		this.mPayoutUserID = pPayoutUserID;
	}	
	/**
	 * ��ע
	 */
	public String getComment() {
		return mComment;
	}
	/**
	 * ��ע
	 */
	public void setComment(String pComment) {
		this.mComment = pComment;
	}
	/**
	 * �������
	 */
	public Date getCreateDate() {
		return mCreateDate;
	}
	/**
	 * �������
	 */
	public void setCreateDate(Date pCreateDate) {
		this.mCreateDate = pCreateDate;
	}
	/**
	 * ״̬ 0ʧЧ 1����
	 */
	public int getState() {
		return mState;
	}
	/**
	 * ״̬ 0ʧЧ 1����
	 */
	public void setState(int pState) {
		this.mState = pState;
	}
}
