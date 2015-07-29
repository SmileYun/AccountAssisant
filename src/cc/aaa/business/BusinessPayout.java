package cc.aaa.business;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import cc.aaa.R;
import cc.aaa.business.base.BusinessBase;
import cc.aaa.database.sqlitedal.SQLiteDALPayout;
import cc.aaa.model.ModelPayout;

public class BusinessPayout extends BusinessBase {

	private SQLiteDALPayout mSqLiteDALPayout;

	public BusinessPayout(Context pContext) {
		super(pContext);
		mSqLiteDALPayout = new SQLiteDALPayout(pContext);
	}

	public Boolean insertPayout(ModelPayout pInfo) {
		Boolean _Result = mSqLiteDALPayout.insertPayout(pInfo);

		if (_Result) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean deletePayoutByPayoutID(int pPayoutID) {
		String _Condition = " And PayoutID = " + pPayoutID;
		Boolean _Result = mSqLiteDALPayout.deletePayout(_Condition);
		return _Result;
	}

	public Boolean deletePayoutByAccountBookID(int pAccountBookID) {
		String _Condition = " And AccountBookID = " + pAccountBookID;
		Boolean _Result = mSqLiteDALPayout.deletePayout(_Condition);
		return _Result;
	}

	public Boolean updatePayoutByPayoutID(ModelPayout pInfo) {
		String _Condition = " PayoutID = " + pInfo.getPayoutID();
		Boolean _Result = mSqLiteDALPayout.updatePayout(_Condition, pInfo);

		if (_Result) {
			return true;
		} else {
			return false;
		}
	}

	public List<ModelPayout> getPayout(String pCondition) {
		return mSqLiteDALPayout.getPayout(pCondition);
	}

	public int getCount() {
		return mSqLiteDALPayout.getCount("");
	}

	/**
	 * 根据Book的ID查找，并将结果按照PayOutDate 和PayoutID 降序排列
	 * 
	 * @param pAccountBookID
	 * @return List
	 */
	public List<ModelPayout> getPayoutByAccountBookID(int pAccountBookID) {
		String _Condition = " And AccountBookID = " + pAccountBookID + " Order By PayoutDate DESC,PayoutID DESC";
		return mSqLiteDALPayout.getPayout(_Condition);
	}

	public String getPayoutTotalMessage(String pPayoutDate, int pAccountBookID) {
		String _Total[] = getPayoutTotalByPayoutDate(pPayoutDate, pAccountBookID);
		return getContext().getString(R.string.TextViewTextPayoutTotal, new Object[] { _Total[0], _Total[1] });
	}

	private String[] getPayoutTotalByPayoutDate(String pPayoutDate, int pAccountBookID) {
		String _Condition = " And PayoutDate = '" + pPayoutDate + "' And AccountBookID = " + pAccountBookID;
		return getPayoutTotal(_Condition);
	}

	public String[] getPayoutTotalByAccountBookID(int pAccountBookID) {
		String _Condition = " And AccountBookID = " + pAccountBookID;
		return getPayoutTotal(_Condition);
	}

	// --------------------------------------------------- TODO List
	private String[] getPayoutTotal(String pCondition) {
		String _SqlText = "Select ifnull(Sum(Amount),0) As SumAmount,Count(Amount) As Count From Payout Where 1=1 "
				+ pCondition;
		String _Total[] = new String[2];
		Cursor _Cursor = mSqLiteDALPayout.execSql(_SqlText);
		if (_Cursor.getCount() == 1) {
			while (_Cursor.moveToNext()) {
				_Total[0] = _Cursor.getString(_Cursor.getColumnIndex("Count"));
				_Total[1] = _Cursor.getString(_Cursor.getColumnIndex("SumAmount"));
			}
		}
		return _Total;
	}

	// ---------------------------------------------------
	public List<ModelPayout> getPayoutOrderByPayoutUserID(String pCondition) {
		pCondition += " Order By PayoutUserID";
		List<ModelPayout> _List = getPayout(pCondition);
		if (_List.size() > 0) {
			return _List;
		}

		return null;
	}

	public String[] getPayoutDateAndAmountTotal(String pCondition) {
		String _SqlText = "Select Min(PayoutDate) As MinPayoutDate,Max(PayoutDate) As MaxPayoutDate,Sum(Amount) As Amount From Payout Where 1=1 "
				+ pCondition;
		String _PayoutTotal[] = new String[3];
		Cursor _Cursor = mSqLiteDALPayout.execSql(_SqlText);
		if (_Cursor.getCount() == 1) {
			while (_Cursor.moveToNext()) {
				_PayoutTotal[0] = _Cursor.getString(_Cursor.getColumnIndex("MinPayoutDate"));
				_PayoutTotal[1] = _Cursor.getString(_Cursor.getColumnIndex("MaxPayoutDate"));
				_PayoutTotal[2] = _Cursor.getString(_Cursor.getColumnIndex("Amount"));
			}
		}
		return _PayoutTotal;
	}
}
