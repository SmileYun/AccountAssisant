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
 * ���û������ݲ�����
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
	 * ��д���󷽷�����Cursorת��Ϊʵ����
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
		// �������ݿ�
		p_DataBase.execSQL(s_CreateTableScript.toString());
		// �ڱ������֮���ʼ�����ݿ�����
		intiDefaultDate(p_DataBase);
	}

	/**
	 * �ɹ�����true
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
	 * ���������û���Ϣ
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
	 * �����û�ָ��״̬
	 * 
	 * @param pCondition
	 * @param pContentValues
	 * @return
	 */
	public boolean upDateUser(String pCondition, ContentValues pContentValues) {
		return getDataBase().update(getTableNameAndPK()[0], pContentValues, pCondition, null) > 0;

	}

	/**
	 * ��ѯ���е��û���Ϣ�����ҽ�Cursorת��ΪList
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
	 * ��ʼ��Ĭ������
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
	 * ��װ�û���Ҫ��д��ContentValues
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
