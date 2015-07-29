package cc.aaa.model;

import java.io.Serializable;
import java.util.Date;

public class ModelUser {
	//用户表主键ID
		private int mUserID;
		//用户名称
		private String mUserName;
		//添加日期
		private Date mCreateDate = new Date();
		//状态 0失效 1启用
		private int mState  = 1;
		public int getUserID() {
			return mUserID;
		}
		public void setUserID(int mUserID) {
			this.mUserID = mUserID;
		}
		public String getUserName() {
			return mUserName;
		}
		public void setUserName(String mUserName) {
			this.mUserName = mUserName;
		}
		public Date getCreateDate() {
			return mCreateDate;
		}
		public void setCreateDate(Date mCreateDate) {
			this.mCreateDate = mCreateDate;
		}
		public int getState() {
			return mState;
		}
		public void setState(int mState) {
			this.mState = mState;
		}
}
