package cc.aaa.database.sqlitedal;

import java.util.Date;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cc.aaa.R;
import cc.aaa.database.base.SQLiteDALBase;
import cc.aaa.model.ModelUser;
import cc.aaa.utility.DateTools;

/**
 * 对用户的数据操作层
 * 
 * @author Administrator
 *
 */
public class SQLiteDALUser extends SQLiteDALBase {

	public SQLiteDALUser(Context mContext) {
		super(mContext);
	}

	@Override
	protected String[] getTableNameAndPK() {
		return new String[] { "User", "UserId" };
	}

	/**
	 * 覆写抽象方法，将Cursor转化为实体类
	 */
	@Override
	protected Object findModel(Cursor pCursor) {
		ModelUser _ModelUser = new ModelUser();

		_ModelUser.setUserID(pCursor.getInt(pCursor.getColumnIndex("UserID")));
		_ModelUser.setUserName(pCursor.getString(pCursor.getColumnIndex("UserName")));
		Date _createDate = DateTools.getDate(pCursor.getString(pCursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss");
		_ModelUser.setCreateDate(_createDate);
		_ModelUser.setState(pCursor.getInt(pCursor.getColumnIndex("State")));

		return _ModelUser;
	}

	@Override
	public void OnCreate(SQLiteDatabase p_DataBase) {
		StringBuilder s_CreateTableScript = new StringBuilder();

		s_CreateTableScript.append("	 Create  TABLE User(");
		s_CreateTableScript.append("	 [UserID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
		s_CreateTableScript.append("	 ,[UserName] varchar(10) NOT NULL");
		s_CreateTableScript.append("	 ,[CreateDate] datetime NOT NULL");
		s_CreateTableScript.append("	 ,[State] integer NOT NULL");
		s_CreateTableScript.append("	 )");
		// 创建数据库
		p_DataBase.execSQL(s_CreateTableScript.toString());
		// 在表创建完毕之后初始化数据库数据
		intiDefaultDate(p_DataBase);
	}

	/**
	 * 成功返回true
	 * 
	 * @param modelUser
	 * @return
	 */
	public boolean insertUser(ModelUser modelUser) {
		ContentValues _Values = CreateParams(modelUser);
		long _newId = getDataBase().insert(getTableNameAndPK()[0], null, _Values);
		modelUser.setUserID((int) _newId);
		return _newId > 0;
	}

	public boolean delete(String pCondition) {
		return delete(getTableNameAndPK()[0], pCondition);

	}

	/**
	 * 更新整个用户信息
	 * 
	 * @param pCondition
	 * @param modelUser
	 * @return
	 */
	public boolean upDateUser(String pCondition, ModelUser modelUser) {
		ContentValues _Values = CreateParams(modelUser);
		return getDataBase().update(getTableNameAndPK()[0], _Values, pCondition, null) > 0;
	}

	/**
	 * 更新用户指定状态
	 * 
	 * @param pCondition
	 * @param pContentValues
	 * @return
	 */
	public boolean upDateUser(String pCondition, ContentValues pContentValues) {
		return getDataBase().update(getTableNameAndPK()[0], pContentValues, pCondition, null) > 0;

	}

	/**
	 * 查询表中的用户信息，并且将Cursor转换为List
	 * 
	 * @param pCondition
	 * @return
	 */
	public List<ModelUser> queryUser(String pCondition) {
		String _sqlString = "select * from User where 1=1 " + pCondition;
		return getList(_sqlString);
	}

	@Override
	public void OnUpgrade(SQLiteDatabase p_DataBase) {
	}

	/**
	 * 初始化默认数据
	 * 
	 * @param SQLiteDatabase
	 */
	private void intiDefaultDate(SQLiteDatabase sqLiteDatabase) {
		ModelUser _ModelUser = new ModelUser();
		String[] user = getContext().getResources().getStringArray(R.array.initDefaultUserName);
		for (int i = 0; i < user.length; i++) {
			_ModelUser.setUserName(user[0]);
			ContentValues _Values = CreateParams(_ModelUser);
			sqLiteDatabase.insert(getTableNameAndPK()[0], null, _Values);

		}
	}

	/**
	 * 封装用户所要填写的ContentValues
	 * 
	 * @param modelUser
	 * @return
	 */
	public ContentValues CreateParams(ModelUser modelUser) {
		ContentValues _Values = new ContentValues();
		_Values.put("UserName", modelUser.getUserName());
		_Values.put("CreateDate", DateTools.getFormatDateTime(modelUser.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
		_Values.put("State", modelUser.getState());

		return _Values;
	}

}
