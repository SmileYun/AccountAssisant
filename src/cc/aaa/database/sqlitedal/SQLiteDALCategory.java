package cc.aaa.database.sqlitedal;

import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cc.aaa.R;
import cc.aaa.database.base.SQLiteDALBase;
import cc.aaa.model.ModelCategory;
import cc.aaa.utility.DateTools;

public class SQLiteDALCategory extends SQLiteDALBase {

	public SQLiteDALCategory(Context pContext) {
		super(pContext);
	}

	public Boolean insertCategory(ModelCategory pInfo) {
		ContentValues _ContentValues = creatParms(pInfo);
		Long pNewID = getDataBase().insert("Category", null, _ContentValues);
		pInfo.setCategoryID(pNewID.intValue());
		return pNewID > 0;
	}

	public Boolean deleteCategory(String pCondition) {
		return delete(getTableNameAndPK()[0], pCondition);
	}

	public Boolean updateCategory(String pCondition, ModelCategory pInfo) {
		ContentValues _ContentValues = creatParms(pInfo);
		return getDataBase().update("Category", _ContentValues, pCondition, null) > 0;
	}

	public Boolean updateCategory(String pCondition, ContentValues pContentValues) {
		return getDataBase().update("Category", pContentValues, pCondition, null) > 0;
	}

	public List<ModelCategory> getCategory(String pCondition) {
		String _SqlText = "Select * From Category Where  1=1 " + pCondition;
		return getList(_SqlText);
	}

	protected ModelCategory findModel(Cursor pCursor) {
		ModelCategory _ModelCategory = new ModelCategory();
		_ModelCategory.setCategoryID(pCursor.getInt(pCursor.getColumnIndex("CategoryID")));
		_ModelCategory.setCategoryName(pCursor.getString(pCursor.getColumnIndex("CategoryName")));
		_ModelCategory.setTypeFlag(pCursor.getString(pCursor.getColumnIndex("TypeFlag")));
		_ModelCategory.setParentID(pCursor.getInt(pCursor.getColumnIndex("ParentID")));
		_ModelCategory.setPath(pCursor.getString(pCursor.getColumnIndex("Path")));
		Date _CreateDate = DateTools.getDate(pCursor.getString(pCursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss");
		_ModelCategory.setCreateDate(_CreateDate);
		_ModelCategory.setState((pCursor.getInt(pCursor.getColumnIndex("State"))));

		return _ModelCategory;
	}

	private void initDefaultData(SQLiteDatabase pDataBase) {
		ModelCategory _ModelCategory = new ModelCategory();

		// 设置标记
		_ModelCategory.setTypeFlag(getContext().getString((R.string.PayoutTypeFlag)));
		// 插入时的默认路径，即一个父类，保持一个根节点
		_ModelCategory.setPath("");
		_ModelCategory.setParentID(0);
		String _InitDefaultCategoryNameArr[] = getContext().getResources().getStringArray(R.array.InitDefaultCategoryName);
		for (int i = 0; i < _InitDefaultCategoryNameArr.length; i++) {
			_ModelCategory.setCategoryName(_InitDefaultCategoryNameArr[i]);

			ContentValues _ContentValues = creatParms(_ModelCategory);
			Long _NewID = pDataBase.insert("Category", null, _ContentValues);

			_ModelCategory.setPath(_NewID.intValue() + ".");
			_ContentValues = creatParms(_ModelCategory);
			pDataBase.update("Category", _ContentValues, " CategoryID = " + _NewID.intValue(), null);
		}
	}

	@Override
	public void OnCreate(SQLiteDatabase pDataBase) {
		StringBuilder s_CreateTableScript = new StringBuilder();

		s_CreateTableScript.append("		Create  TABLE Category(");
		s_CreateTableScript.append("				 [CategoryID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
		s_CreateTableScript.append("				,[CategoryName] varchar(20) NOT NULL");
		s_CreateTableScript.append("				,[TypeFlag] varchar(20) NOT NULL");
		s_CreateTableScript.append("				,[ParentID] integer NOT NULL");
		s_CreateTableScript.append("				,[Path] text NOT NULL");
		s_CreateTableScript.append("				,[CreateDate] datetime NOT NULL");
		s_CreateTableScript.append("				,[State] integer NOT NULL");
		s_CreateTableScript.append("				)");

		pDataBase.execSQL(s_CreateTableScript.toString());
		initDefaultData(pDataBase);
	}

	@Override
	public void OnUpgrade(SQLiteDatabase pDataBase) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String[] getTableNameAndPK() {
		return new String[] { "Category", "CategoryID" };
	}

	public ContentValues creatParms(ModelCategory pInfo) {
		ContentValues _ContentValues = new ContentValues();
		_ContentValues.put("CategoryName", pInfo.getCategoryName());
		_ContentValues.put("TypeFlag", pInfo.getTypeFlag());
		_ContentValues.put("ParentID", pInfo.getParentID());
		_ContentValues.put("Path", pInfo.getPath());
		_ContentValues.put("CreateDate", DateTools.getFormatDateTime(pInfo.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
		_ContentValues.put("State", pInfo.getState());
		return _ContentValues;
	}

}
