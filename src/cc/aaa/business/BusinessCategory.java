package cc.aaa.business;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import cc.aaa.R;
import cc.aaa.business.base.BusinessBase;
import cc.aaa.database.sqlitedal.SQLiteDALCategory;
import cc.aaa.model.ModelCategory;
import cc.aaa.model.ModelCategoryTotal;

public class BusinessCategory extends BusinessBase {

	private SQLiteDALCategory mSqLiteDALCategory;
	/**
	 * 静态变量，取字典类的类别
	 */
	private final String TYPE_FLAG = " And TypeFlag= '" + getString(R.string.PayoutTypeFlag) + "'";

	public BusinessCategory(Context pContext) {
		super(pContext);
		mSqLiteDALCategory = new SQLiteDALCategory(pContext);
	}

	public Boolean insertCategory(ModelCategory pInfo) {
		mSqLiteDALCategory.beginTransaction();
		try {
			Boolean _Result = mSqLiteDALCategory.insertCategory(pInfo);
			Boolean _Result2 = true;

			ModelCategory _ParentModelCategory = getModelCategoryByCategoryID(pInfo.getParentID());
			String _Path;
			if (_ParentModelCategory != null) {
				_Path = _ParentModelCategory.getPath() + pInfo.getCategoryID() + ".";
			} else {
				_Path = pInfo.getCategoryID() + ".";
			}

			pInfo.setPath(_Path);
			_Result2 = updateCategoryInsertTypeByCategoryID(pInfo);

			if (_Result && _Result2) {
				mSqLiteDALCategory.setTransactionSuccessful();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			mSqLiteDALCategory.endTransaction();
		}
	}

	public Boolean deleteCategoryByCategoryID(int pCategoryID) {
		String _Condition = " CategoryID = " + pCategoryID;
		Boolean _Result = mSqLiteDALCategory.deleteCategory(_Condition);
		return _Result;
	}

	public Boolean deleteCategoryByPath(String pPath) throws Exception {
		/*
		 * int _Count = mSqLiteDALCategory.GetCount("PayoutID", "v_Payout",
		 * " And Path Like '" + pPath + "%'");
		 * 
		 * if(_Count != 0) { throw new
		 * Exception(GetString(R.string.ErrorMessageExistPayout)); }
		 * 
		 * String _Condition = " And Path Like '" + pPath + "%'"; Boolean
		 * _Result = mSqLiteDALCategory.DeleteCategory(_Condition); return
		 * _Result;
		 */

		return true;
	}

	public Boolean updateCategoryInsertTypeByCategoryID(ModelCategory pInfo) {
		String _Condition = " CategoryID = " + pInfo.getCategoryID();
		Boolean _Result = mSqLiteDALCategory.updateCategory(_Condition, pInfo);

		if (_Result) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean updateCategoryByCategoryID(ModelCategory pInfo) {
		mSqLiteDALCategory.beginTransaction();
		try {
			String _Condition = " CategoryID = " + pInfo.getCategoryID();
			Boolean _Result = mSqLiteDALCategory.updateCategory(_Condition, pInfo);
			Boolean _Result2 = true;

			ModelCategory _ParentModelCategory = getModelCategoryByCategoryID(pInfo.getParentID());
			String _Path;
			if (_ParentModelCategory != null) {
				_Path = _ParentModelCategory.getPath() + pInfo.getCategoryID() + ".";
			} else {
				_Path = pInfo.getCategoryID() + ".";
			}

			pInfo.setPath(_Path);
			_Result2 = updateCategoryInsertTypeByCategoryID(pInfo);

			if (_Result && _Result2) {
				mSqLiteDALCategory.setTransactionSuccessful();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			mSqLiteDALCategory.endTransaction();
		}
	}

	public Boolean hideCategoryByByPath(String pPath) {
		String _Condition = " Path Like '" + pPath + "%'";
		ContentValues _ContentValues = new ContentValues();
		_ContentValues.put("State", 0);
		Boolean _Result = mSqLiteDALCategory.updateCategory(_Condition, _ContentValues);

		if (_Result) {
			return true;
		} else {
			return false;
		}
	}

	public List<ModelCategory> getCategory(String pCondition) {
		return mSqLiteDALCategory.getCategory(pCondition);
	}

	public List<ModelCategory> getNotHideCategory() {
		return mSqLiteDALCategory.getCategory(TYPE_FLAG + " And State = 1");
	}

	public int getNotHideCount() {
		return mSqLiteDALCategory.getCount(TYPE_FLAG + " And State = 1");
	}

	public int getNotHideCountByParentID(int pCategoryID) {
		return mSqLiteDALCategory.getCount(TYPE_FLAG + " And ParentID = " + pCategoryID + " And State = 1");
	}

	/**
	 * 获取没被隐藏的跟类别数据
	 * 
	 * @return
	 */
	public List<ModelCategory> getNotHideRootCategory() {
		return mSqLiteDALCategory.getCategory(TYPE_FLAG + " And ParentID = 0 And State = 1");
	}

	/**
	 * 根据父类别ID，获取未隐藏的元素
	 * 
	 * @param pParentID
	 * @return
	 */
	public List<ModelCategory> getNotHideCategoryListByParentID(int pParentID) {
		return mSqLiteDALCategory.getCategory(TYPE_FLAG + " And ParentID = " + pParentID + " And State = 1");
	}

	public ModelCategory getModelCategoryByParentID(int pParentID) {
		List _List = mSqLiteDALCategory.getCategory(TYPE_FLAG + " And ParentID = " + pParentID);
		if (_List.size() == 1) {
			return (ModelCategory) _List.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据类别ID，查询类别
	 * 
	 * @param pCategoryID
	 *            传入父类ID
	 * @return
	 */
	public ModelCategory getModelCategoryByCategoryID(int pCategoryID) {
		List _List = mSqLiteDALCategory.getCategory(TYPE_FLAG + " And CategoryID = " + pCategoryID);
		if (_List.size() == 1) {
			return (ModelCategory) _List.get(0);
		} else {
			return null;
		}
	}

	public ArrayAdapter getRootCategoryArrayAdapter() {
		List _List = getNotHideRootCategory();
		_List.add(0, "--请选择--");
		ArrayAdapter _ArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, _List);
		_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return _ArrayAdapter;
	}

	// 添加消费时用
	public ArrayAdapter getAllCategoryArrayAdapter() {
		/*
		 * List _List = GetNotHideCategory(); String _Name[] = new
		 * String[_List.size()]; for (int i = 0; i < _List.size(); i++) {
		 * _Name[i] = ((ModelCategory)_List.get(i)).GetCategoryName(); }
		 * ArrayAdapter _ArrayAdapter = new ArrayAdapter(GetContext(),
		 * R.layout.common_auto_complete, _List); return _ArrayAdapter;
		 */

		return null;
	}

	public List<ModelCategoryTotal> categoryTotalByRootCategory() {
		String _Condition = TYPE_FLAG + " And ParentID = 0 And State = 1";
		List<ModelCategoryTotal> _ModelCategoryTotalList = categoryTotal(_Condition);

		return _ModelCategoryTotalList;
	}

	public List<ModelCategoryTotal> categoryTotalByParentID(int pParentID) {
		String _Condition = TYPE_FLAG + " And ParentID = " + pParentID;
		List<ModelCategoryTotal> _ModelCategoryTotalList = categoryTotal(_Condition);

		return _ModelCategoryTotalList;
	}

	public List<ModelCategoryTotal> categoryTotal(String pCondition) {
		String _Condition = pCondition;
		Cursor _Cursor = mSqLiteDALCategory
				.execSql("Select Count(PayoutID) As Count, Sum(Amount) As SumAmount, CategoryName From v_Payout Where 1=1 "
						+ _Condition + " Group By CategoryName");
		List<ModelCategoryTotal> _ModelCategoryTotalList = new ArrayList<ModelCategoryTotal>();
		while (_Cursor.moveToNext()) {
			ModelCategoryTotal _ModelCategoryTotal = new ModelCategoryTotal();
			_ModelCategoryTotal.Count = _Cursor.getString(_Cursor.getColumnIndex("Count"));
			_ModelCategoryTotal.SumAmount = _Cursor.getString(_Cursor.getColumnIndex("SumAmount"));
			_ModelCategoryTotal.CategoryName = _Cursor.getString(_Cursor.getColumnIndex("CategoryName"));
			_ModelCategoryTotalList.add(_ModelCategoryTotal);
		}

		return _ModelCategoryTotalList;
	}
}
