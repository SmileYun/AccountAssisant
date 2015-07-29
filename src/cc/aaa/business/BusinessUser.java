package cc.aaa.business;

import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.content.ContentValues;
import android.content.Context;
import cc.aaa.business.base.BusinessBase;
import cc.aaa.database.sqlitedal.SQLiteDALUser;
import cc.aaa.model.ModelUser;

public class BusinessUser extends BusinessBase {
	private SQLiteDALUser mSqLiteDALUser;

	public BusinessUser(Context mContext) {
		super(mContext);
		mSqLiteDALUser = new SQLiteDALUser(mContext);
	}

	/**
	 * ��װ���ݲ�����Ĳ��뷽������ҵ���õ����ؽ��(ԭʼ�������صĽ��)����������Ҫִ�е��߼�ҵ��
	 * 
	 * @param modelUser
	 * @return
	 */
	public boolean insertUser(ModelUser modelUser) {
		List<ModelUser> _li;
		if ((_li = getUsers(" and UserName = '" + modelUser.getUserName().trim() + "'")).size() != 0) {

			return false;

		} else {
			boolean _res = mSqLiteDALUser.insertUser(modelUser);
			return _res;
		}

	}

	public boolean deleteUserByUserID(int userID) {
		// ��ҵ��㲻Ӧֱ�ӳ���Sql���(���ݿ��е��ֶο��ܳ��ָ���)����������ֶ���������Ҫ�������߼�������޸ģ�
		// ���Խ����װ����SQLiteDAL���װ�����ֶεĳ���
		String pCondition = " and UserID = " + userID;
		return mSqLiteDALUser.delete(pCondition);
	}

	/**
	 * �����û�ID���޸��û�
	 * 
	 * @param modelUser
	 * @return
	 */
	public boolean updateUserByUserID(ModelUser modelUser) {
		return mSqLiteDALUser.upDateUser(" 1=1 and UserID = " + modelUser.getUserID(), modelUser);
	}

	/**
	 * �����û�ID���޸��û�ָ����Ϣ
	 * 
	 * @param modelUser
	 * @return
	 */
	public boolean hideUserByID(int UserID) {
		ContentValues _ContentValues = new ContentValues();
		_ContentValues.put("State", 0);
		return mSqLiteDALUser.upDateUser(" 1=1 and UserID = " + UserID, _ContentValues);
	}

	private List<ModelUser> getUsers(String condition) {
		return mSqLiteDALUser.queryUser(condition);
	}

	/**
	 * ��ȡ״̬Ϊ1���û�
	 * 
	 * @return
	 */
	public List<ModelUser> getNotHideUser() {
		return getUsers(" and State = 1");
	}

	/**
	 * ����ID��ȡUserʵ����
	 * 
	 * @param userID
	 * @return
	 */
	public ModelUser getUserByUserID(int userID) {
		if (getUsers(" and UserID = " + userID).size() == 1) {
			return getUsers(" and UserID = " + userID).get(0);
		} else {
			return null;
		}
	}

	/**
	 * ����ID���鷵�ز�ѯ�õ����û�List
	 * 
	 * @param conditions
	 * @return
	 */
	private List<ModelUser> getUsersByUsersID(String[] conditions) {
		List<ModelUser> _List = new ArrayList<ModelUser>();
		for (int i = 0; i < conditions.length; i++) {
			_List.add(getUserByUserID(Integer.parseInt(conditions[i])));
		}
		return _List;
	}

	public String getUserNameByUserID(String p_UserID) {
		List<ModelUser> _List = getUsersByUsersID(p_UserID.split(","));
		String _Name = "";

		for (int i = 0; i < _List.size(); i++) {
			_Name += _List.get(i).getUserName() + ",";
		}
		return _Name;
	}

}
