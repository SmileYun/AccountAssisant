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
	 * ��װ���ݲ�����Ĳ��뷽������ҵ���õ����ؽ��(ԭʼ�������صĽ��)����������Ҫִ�е��߼�ҵ��
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
		// ��ҵ��㲻Ӧֱ�ӳ���Sql���(���ݿ��е��ֶο��ܳ��ָ���)����������ֶ���������Ҫ�������߼�������޸ģ�
		// ���Խ����װ����SQLiteDAL���װ�����ֶεĳ���
		String pCondition = " and AccountBookID = " + AccountBookID;
		return mSqLiteDALAccountBook.delete(pCondition);
	}

	/**
	 * ����ָ��ID���޸��˱�
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
	 * �����û�ID���޸��û�ָ����Ϣ
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
	 * ��ȡ״̬Ϊ1���û�
	 * 
	 * @return
	 */
	public List<ModelAccountBook> getNotHideAccountBook() {
		return getAccountBooks(" and State = 1");
	}

	/**
	 * ����ID��ȡAccountBookʵ����
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
	 * ����ID���鷵�ز�ѯ�õ����û�List
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
	 * �����˱�ID����������Ĭ���˱�����Ϊ��Ĭ���˱�
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
	 * �����˱�����(�� ID) �жϱ����Ƿ���ڸ��˱�
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
	 * ����������ѯ���ݿ������������ĸ���
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
