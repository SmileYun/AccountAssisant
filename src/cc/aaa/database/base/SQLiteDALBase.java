package cc.aaa.database.base;

import java.util.ArrayList;
import java.util.List;

import cc.aaa.database.base.DatabaseHelper.SQLiteDataTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库操作层，不能用于在Activity中的调用以及执行业务逻辑
 * 
 * @author Administrator
 *
 */

public abstract class SQLiteDALBase implements SQLiteDataTable {

	private Context mContext;
	private SQLiteDatabase mDataBase;

	public SQLiteDALBase(Context pContext) {
		mContext = pContext;
	}

	protected Context getContext() {
		return mContext;
	}

	/**
	 * 获取一个可写的数据库
	 * 
	 * @return
	 */
	public SQLiteDatabase getDataBase() {

		if (mDataBase == null) {
			mDataBase = DatabaseHelper.getDatabaseHelper(mContext).getWritableDatabase();
		}

		return mDataBase;
	}

	public void beginTransaction() {
		getDataBase().beginTransaction();
	}

	public void setTransactionSuccessful() {
		getDataBase().setTransactionSuccessful();
	}

	public void endTransaction() {
		mDataBase.endTransaction();
	}

	/**
	 * 根据条件查询数据库中满足条件的个数
	 * 
	 * @param pCondition
	 * @return
	 */
	public int getCount(String pCondition) {
		String _String[] = getTableNameAndPK();
		Cursor _Cursor = execSql("Select " + _String[1] + " From " + _String[0] + " Where 1=1 " + pCondition);
		int _Count = _Cursor.getCount();
		_Cursor.close();
		return _Count;
	}

	public int getCount(String pPK, String pTableName, String pCondition) {
		Cursor _Cursor = execSql("Select " + pPK + " From " + pTableName + " Where 1=1 " + pCondition);
		int _Count = _Cursor.getCount();
		_Cursor.close();
		return _Count;
	}

	protected boolean delete(String pTableName, String pCondition) {
		return getDataBase().delete(pTableName, " 1=1 " + pCondition, null) >= 0;
	}

	/**
	 * {表名， 主键名}
	 */
	protected abstract String[] getTableNameAndPK();

	/**
	 * 根据SQL语句得到表中用户信息
	 * 
	 * @param pSqlText
	 *            :String SQL脚本
	 * @return List
	 */
	protected List getList(String pSqlText) {
		Cursor _Cursor = execSql(pSqlText);
		return cursorToList(_Cursor);
	}

	/**
	 * 将 游标 转换为List
	 * 
	 * @param pCursor
	 *            :Cursor
	 * @return List
	 */
	protected List cursorToList(Cursor pCursor) {
		List _List = new ArrayList();
		while (pCursor.moveToNext()) {
			Object _Object = findModel(pCursor);
			_List.add(_Object);
		}
		pCursor.close();
		return _List;
	}

	public Cursor execSql(String pSqlText) {
		return getDataBase().rawQuery(pSqlText, null);
	}

	/**
	 * 游标转换成实体类，父类调用子类方法
	 * 
	 * @param pCursor
	 * @return
	 */
	protected abstract Object findModel(Cursor pCursor);

}
