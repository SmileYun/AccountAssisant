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
	 * 封装数据操作层的插入方法，在业务层得到返回结果(原始操作返回的结果)，可以做需要执行的逻辑业务
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
		// 在业务层不应直接出现Sql语句(数据库中的字段可能出现更改)，如果更改字段名字则需要对整个逻辑层进行修改，
		// 可以将其封装，在SQLiteDAL层封装表中字段的常量
		String pCondition = " and UserID = " + userID;
		return mSqLiteDALUser.delete(pCondition);
	}

	/**
	 * 根据用户ID，修改用户
	 * 
	 * @param modelUser
	 * @return
	 */
	public boolean updateUserByUserID(ModelUser modelUser) {
		return mSqLiteDALUser.upDateUser(" 1=1 and UserID = " + modelUser.getUserID(), modelUser);
	}

	/**
	 * 根据用户ID，修改用户指定信息
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
	 * 获取状态为1的用户
	 * 
	 * @return
	 */
	public List<ModelUser> getNotHideUser() {
		return getUsers(" and State = 1");
	}

	/**
	 * 根据ID获取User实体类
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
	 * 根据ID数组返回查询得到的用户List
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
