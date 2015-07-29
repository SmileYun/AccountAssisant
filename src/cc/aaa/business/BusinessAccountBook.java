package cc.aaa.business;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import cc.aaa.business.base.BusinessBase;
import cc.aaa.database.sqlitedal.SQLiteDALAccountBook;
import cc.aaa.model.ModelAccountBook;

public class BusinessAccountBook extends BusinessBase {
	private SQLiteDALAccountBook mSqLiteDALAccountBook;

	public BusinessAccountBook(Context mContext) {
		super(mContext);
		mSqLiteDALAccountBook = new SQLiteDALAccountBook(mContext);
	}

	/**
	 * 封装数据操作层的插入方法，在业务层得到返回结果(原始操作返回的结果)，可以做需要执行的逻辑业务
	 * 
	 * @param modelAccountBook
	 * @return
	 */
	public boolean insertAccountBook(ModelAccountBook modelAccountBook) {
		List<ModelAccountBook> _li;
		if ((_li = getAccountBooks(" and AccountBookName = '" + modelAccountBook.getAccountBookName().trim() + "'")).size() != 0) {

			return false;

		} else {
			boolean _res = mSqLiteDALAccountBook.insertAccountBook(modelAccountBook);
			return _res;
		}
	}

	public boolean deleteAccountBookByAccountBookID(int AccountBookID) {
		// 在业务层不应直接出现Sql语句(数据库中的字段可能出现更改)，如果更改字段名字则需要对整个逻辑层进行修改，
		// 可以将其封装，在SQLiteDAL层封装表中字段的常量
		String pCondition = " and AccountBookID = " + AccountBookID;
		return mSqLiteDALAccountBook.delete(pCondition);
	}

	/**
	 * 根据指定ID，修改账本
	 * 
	 * @param modelAccountBook
	 * @return
	 */
	public boolean updateAccountBookByAccountBookID(ModelAccountBook modelAccountBook) {
		// return mSqLiteDALAccountBook.upDateAccountBook(
		// " 1=1 and AccountBookID = " + modelAccountBook.getAccountBookID(),
		// modelAccountBook);
		mSqLiteDALAccountBook.beginTransaction();
		try {
			String _conditionString = "  AccountBookID = " + modelAccountBook.getAccountBookID();

			boolean res_1 = mSqLiteDALAccountBook.upDateAccountBook(_conditionString, modelAccountBook);

			boolean res_2 = true;
			if (modelAccountBook.getIsDefault() == 1 && res_1) {
				res_2 = setAccountNotDefault(modelAccountBook.getAccountBookID());
			}
			if (res_1 && res_2) {
				mSqLiteDALAccountBook.setTransactionSuccessful();
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			mSqLiteDALAccountBook.endTransaction();
		}
	}

	/**
	 * 根据用户ID，修改用户指定信息
	 * 
	 * @param modelAccountBook
	 * @return
	 */
	public boolean hideAccountBookByID(int AccountBookID) {
		ContentValues _ContentValues = new ContentValues();
		_ContentValues.put("State", 0);
		return mSqLiteDALAccountBook.upDateAccountBook(" 1=1 and AccountBookID = " + AccountBookID, _ContentValues);
	}

	public List<ModelAccountBook> getAccountBooks(String condition) {
		return mSqLiteDALAccountBook.queryAccountBook(condition);
	}

	/**
	 * 获取状态为1的用户
	 * 
	 * @return
	 */
	public List<ModelAccountBook> getNotHideAccountBook() {
		return getAccountBooks(" and State = 1");
	}

	/**
	 * 根据ID获取AccountBook实体类
	 * 
	 * @param AccountBookID
	 * @return
	 */
	public ModelAccountBook getAccountBookByAccountBookID(int AccountBookID) {
		if (getAccountBooks(" and AccountBookID = " + AccountBookID).size() == 1) {
			return getAccountBooks(" and AccountBookID = " + AccountBookID).get(0);
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
	private List<ModelAccountBook> getAccountBooksByAccountBooksID(String[] conditions) {
		List<ModelAccountBook> _List = new ArrayList<ModelAccountBook>();
		for (int i = 0; i < conditions.length; i++) {
			_List.add(getAccountBookByAccountBookID(Integer.parseInt(conditions[i])));
		}
		return _List;
	}

	/**
	 * 根据账本ID，将其他的默认账本设置为非默认账本
	 * 
	 * @param accountID
	 * @return
	 */
	public boolean setAccountNotDefault(int accountID) {
		String _condition = " 1=1 and IsDefault = 1 and AccountBookID <> " + accountID;
		ContentValues _ContentValues = new ContentValues();
		_ContentValues.put("IsDefault", 0);
		return mSqLiteDALAccountBook.upDateAccountBook(_condition, _ContentValues);
	}

	/**
	 * 根据账本名称(和 ID) 判断表中是否存在该账本
	 * 
	 * @param bookName
	 * @param bookID
	 * @return
	 */
	public boolean isExistAccountBookByName(String bookName, integer bookID) {
		String _conditionString = " and AccountBookName = '" + bookName + "'";
		if (bookID != null) {
			_conditionString += " and AccountBookID <> " + bookID;
		}
		List<ModelAccountBook> _list;
		_list = getAccountBooks(_conditionString);
		if (_list.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据条件查询数据库中满足条件的个数
	 * 
	 * @param pCondition
	 * @ret
	 */
	public int getCount() {
		return mSqLiteDALAccountBook.getCount("");
	}

	public ModelAccountBook getDefaultModelAccountBook() {
		List _List = mSqLiteDALAccountBook.queryAccountBook(" And IsDefault = 1");
		if (_List.size() == 1) {
			return (ModelAccountBook) _List.get(0);
		} else {
			return null;
		}
	}

	public String GetAccountBookNameByAccountId(int p_BookId) {
		String _ConditionString = "And AccountBookID = " + String.valueOf(p_BookId);
		List<ModelAccountBook> _AccountBooks = mSqLiteDALAccountBook.queryAccountBook(_ConditionString);
		return _AccountBooks.get(0).getAccountBookName();
	}
}
