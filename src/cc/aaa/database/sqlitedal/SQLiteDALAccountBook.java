package cc.aaa.database.sqlitedal;

import java.util.Date;
import java.util.List; 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cc.aaa.R;
import cc.aaa.database.base.SQLiteDALBase;
import cc.aaa.model.ModelAccountBook;
import cc.aaa.model.ModelAccountBook;
import cc.aaa.utility.DateTools;
/**
 * ���û������ݲ�����
 * @author Administrator
 *
 */
public class SQLiteDALAccountBook extends SQLiteDALBase {

	public SQLiteDALAccountBook(Context mContext) {
		super(mContext);
	}
	
	/**
	 * ��ȡ������������
	 */
	@Override
	protected String[] getTableNameAndPK() {
		return new String[]{"AccountBook","AccountBookID"};
	}
	
	/**
	 * ��д���󷽷�����Cursorת��Ϊʵ����
	 */
	@Override
	protected Object findModel(Cursor pCursor) {
		ModelAccountBook _ModelAccountBook = new ModelAccountBook();
		
		_ModelAccountBook.setAccountBookID(pCursor.getInt(pCursor.getColumnIndex("AccountBookID")));
		_ModelAccountBook.setAccountBookName(pCursor.getString(pCursor.getColumnIndex("AccountBookName")));
		Date _createDate = DateTools.getDate(pCursor.getString(pCursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss");	
		_ModelAccountBook.setCreateDate(_createDate);
		_ModelAccountBook.setState(pCursor.getInt(pCursor.getColumnIndex("State")));
		_ModelAccountBook.setIsDefault(pCursor.getInt(pCursor.getColumnIndex("IsDefault"))); 
		 
		return _ModelAccountBook;
	}
	
	/**
	 * ����SQL�ű��������ݿ�
	 */
	@Override
	public void OnCreate(SQLiteDatabase p_DataBase) {
		StringBuilder s_CreateTableScript = new StringBuilder();
		s_CreateTableScript.append(" Create  TABLE AccountBook(");
		s_CreateTableScript.append("	[AccountBookID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
		s_CreateTableScript.append("   ,[AccountBookName] varchar(20) NOT NULL");
		s_CreateTableScript.append("   ,[CreateDate] datetime NOT NULL");
		s_CreateTableScript.append("   ,[State] integer NOT NULL");
		s_CreateTableScript.append("   ,[IsDefault] integer NOT NULL");
		s_CreateTableScript.append("    )");
		 
		//�������ݿ�
		p_DataBase.execSQL(s_CreateTableScript.toString());
		//�ڱ������֮���ʼ�����ݿ�����
		intiDefaultDate(p_DataBase);
	}
	
	/**
	 * �ɹ�����true
	 * @param ModelAccountBook
	 * @return
	 */
	public boolean insertAccountBook(ModelAccountBook ModelAccountBook) {
		ContentValues _Values = CreateParams(ModelAccountBook);
		long _newId = getDataBase().insert(getTableNameAndPK()[0], null, _Values);
		ModelAccountBook.setAccountBookID((int) _newId);
		return _newId>0;
	}
	
	/**
	 * ��������ɾ��
	 * @param pCondition
	 * @return
	 */
	public boolean delete(String pCondition){
		 return delete(getTableNameAndPK()[0], pCondition);
		
	}
	
	/**
	 * ���������û���Ϣ
	 * @param pCondition
	 * @param ModelAccountBook
	 * @return
	 */
	public boolean upDateAccountBook(String pCondition , ModelAccountBook ModelAccountBook){
		ContentValues _Values = CreateParams(ModelAccountBook);
		return getDataBase().update(getTableNameAndPK()[0], _Values, pCondition, null)>0;
	}
	
	/**
	 * �����û�ָ��״̬
	 * @param pCondition
	 * @param pContentValues
	 * @return
	 */
	public  boolean upDateAccountBook(String pCondition ,ContentValues pContentValues){
		return getDataBase().update(getTableNameAndPK()[0], pContentValues, pCondition, null)>0;
		
	}
	
	
	/**
	 * ��ѯ���е��˱���Ϣ�����ҽ�Cursorת��ΪList
	 * @param pCondition
	 * @return
	 */
	public List<ModelAccountBook> queryAccountBook(String pCondition) {
		String _sqlString = "select * from  AccountBook where 1=1 " + pCondition;
		return getList(_sqlString);
	}
	
	@Override
	public void OnUpgrade(SQLiteDatabase p_DataBase) {}

	
	/**
	 * ��ʼ��Ĭ������
	 * @param  SQLiteDatabase
	 */
	private void intiDefaultDate(SQLiteDatabase sqLiteDatabase){
		ModelAccountBook _ModelAccountBook = new ModelAccountBook();
		
		String _AccountBook[] = getContext().getResources().getStringArray(R.array.initDefaultDataAccountBookName);
	 
		_ModelAccountBook.setAccountBookName(_AccountBook[0]);
		_ModelAccountBook.setIsDefault(1);
	
		ContentValues _ContentValues = CreateParams(_ModelAccountBook);
		sqLiteDatabase.insert(getTableNameAndPK()[0], null, _ContentValues); 
		 
	}
	/**
	 * ��װ�û���Ҫ��д��ContentValues
	 * @param ModelAccountBook
	 * @return
	 */
	public ContentValues CreateParams(ModelAccountBook ModelAccountBook) {
		ContentValues _Values = new ContentValues();
		_Values.put("AccountBookName", ModelAccountBook.getAccountBookName());
		_Values.put("CreateDate", DateTools.getFormatDateTime(ModelAccountBook.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
		_Values.put("State", ModelAccountBook.getState());
		_Values.put("IsDefault",ModelAccountBook.getIsDefault());
		return _Values;
	}
	 
}
