package cc.aaa.model;

import java.io.Serializable;
import java.util.Date;

public class ModelUser {
	//�û�������ID
		private int mUserID;
		//�û�����
		private String mUserName;
		//�������
		private Date mCreateDate = new Date();
		//״̬ 0ʧЧ 1����
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
