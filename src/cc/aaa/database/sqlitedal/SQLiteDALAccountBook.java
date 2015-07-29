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
 * 对用户的数据操作层
 * @author Administrator
 *
 */
public class SQLiteDALAccountBook extends SQLiteDALBase {

	public SQLiteDALAccountBook(Context mContext) {
		super(mContext);
	}
	
	/**
	 * 获取表名和主键名
	 */
	@Override
	protected String[] getTableNameAndPK() {
		return new String[]{"AccountBook","AccountBookID"};
	}
	
	/**
	 * 覆写抽象方法，将Cursor转化为实体类
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
	 * 根据SQL脚本创建数据库
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
		 
		//创建数据库
		p_DataBase.execSQL(s_CreateTableScript.toString());
		//在表创建完毕之后初始化数据库数据
		intiDefaultDate(p_DataBase);
	}
	
	/**
	 * 成功返回true
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
	 * 根据条件删除
	 * @param pCondition
	 * @return
	 */
	public boolean delete(String pCondition){
		 return delete(getTableNameAndPK()[0], pCondition);
		
	}
	
	/**
	 * 更新整个用户信息
	 * @param pCondition
	 * @param ModelAccountBook
	 * @return
	 */
	public boolean upDateAccountBook(String pCondition , ModelAccountBook ModelAccountBook){
		ContentValues _Values = CreateParams(ModelAccountBook);
		return getDataBase().update(getTableNameAndPK()[0], _Values, pCondition, null)>0;
	}
	
	/**
	 * 更新用户指定状态
	 * @param pCondition
	 * @param pContentValues
	 * @return
	 */
	public  boolean upDateAccountBook(String pCondition ,ContentValues pContentValues){
		return getDataBase().update(getTableNameAndPK()[0], pContentValues, pCondition, null)>0;
		
	}
	
	
	/**
	 * 查询表中的账本信息，并且将Cursor转换为List
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
	 * 初始化默认数据
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
	 * 封装用户所要填写的ContentValues
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
