package cc.aaa.database.sqlitedal;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cc.aaa.database.base.SQLiteDALBase;
import cc.aaa.model.ModelPayout;
import cc.aaa.utility.DateTools;

public class SQLiteDALPayout extends SQLiteDALBase {

	public SQLiteDALPayout(Context pContext) {
		super(pContext);
	}

	public Boolean insertPayout(ModelPayout pInfo) {
		ContentValues _ContentValues = creatParms(pInfo);
		Long pNewID = getDataBase().insert("Payout", null, _ContentValues);
		pInfo.setPayoutID(pNewID.intValue());
		return pNewID > 0;
	}
	
	public Boolean deletePayout(String pCondition)
	{
		return delete(getTableNameAndPK()[0], pCondition);
	}
	
	public Boolean updatePayout(String pCondition, ModelPayout pInfo)
	{
		ContentValues _ContentValues = creatParms(pInfo);
		return getDataBase().update("Payout", _ContentValues, pCondition, null) > 0;
	}
	
	public Boolean updatePayout(String pCondition,ContentValues pContentValues)
	{
		return getDataBase().update("Payout", pContentValues, pCondition, null) > 0;
	}
	
	/**
	 * 通过对视图，进行查询 
	 * @param pCondition
	 * @return
	 */
	public List<ModelPayout> getPayout(String pCondition)
	{
		String _SqlText = "Select * From v_Payout Where  1=1 " + pCondition;
		return getList(_SqlText);
	}
	
	protected ModelPayout findModel(Cursor pCursor)
	{
		ModelPayout _ModelPayout = new ModelPayout();
		_ModelPayout.setPayoutID(pCursor.getInt(pCursor.getColumnIndex("PayoutID")));
		_ModelPayout.setAccountBookID(pCursor.getInt((pCursor.getColumnIndex("AccountBookID"))));
		_ModelPayout.setAccountBookName((pCursor.getString(pCursor.getColumnIndex("AccountBookName"))));
		_ModelPayout.setCategoryID(pCursor.getInt((pCursor.getColumnIndex("CategoryID"))));
		_ModelPayout.setCategoryName((pCursor.getString(pCursor.getColumnIndex("CategoryName"))));
		_ModelPayout.setPath((pCursor.getString(pCursor.getColumnIndex("Path"))));
		_ModelPayout.setPayWayID(pCursor.getInt((pCursor.getColumnIndex("PayWayID"))));
		_ModelPayout.setPlaceID(pCursor.getInt((pCursor.getColumnIndex("PlaceID"))));
		_ModelPayout.setAmount(new BigDecimal(pCursor.getString(((pCursor.getColumnIndex("Amount"))))));
		Date _PayoutDate = DateTools.getDate(pCursor.getString(pCursor.getColumnIndex("PayoutDate")), "yyyy-MM-dd");	
		_ModelPayout.setPayoutDate(_PayoutDate);
		_ModelPayout.setPayoutType((pCursor.getString(pCursor.getColumnIndex("PayoutType"))));
		_ModelPayout.setPayoutUserID((pCursor.getString(pCursor.getColumnIndex("PayoutUserID"))));
		_ModelPayout.setComment((pCursor.getString(pCursor.getColumnIndex("Comment"))));
		Date _CreateDate = DateTools.getDate(pCursor.getString(pCursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss");	
		_ModelPayout.setCreateDate(_CreateDate);
		_ModelPayout.setState((pCursor.getInt(pCursor.getColumnIndex("State"))));
		
		return _ModelPayout;
	}

	@Override
	public void OnCreate(SQLiteDatabase pDataBase) {
		StringBuilder s_CreateTableScript = new StringBuilder();
		
		s_CreateTableScript.append("		Create  TABLE Payout(");
		s_CreateTableScript.append("				[PayoutID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
		s_CreateTableScript.append("				,[AccountBookID] integer NOT NULL");
		s_CreateTableScript.append("				,[CategoryID] integer NOT NULL");
		s_CreateTableScript.append("				,[PayWayID] integer");
		s_CreateTableScript.append("				,[PlaceID] integer");
		s_CreateTableScript.append("				,[Amount] decimal NOT NULL");
		s_CreateTableScript.append("				,[PayoutDate] datetime NOT NULL");
		s_CreateTableScript.append("				,[PayoutType] varchar(20) NOT NULL");
		s_CreateTableScript.append("				,[PayoutUserID] text NOT NULL");
		s_CreateTableScript.append("				,[Comment] text");
		s_CreateTableScript.append("				,[CreateDate] datetime NOT NULL");
		s_CreateTableScript.append("				,[State] integer NOT NULL");
		s_CreateTableScript.append("				)");
		
		pDataBase.execSQL(s_CreateTableScript.toString());
	}

	@Override
	public void OnUpgrade(SQLiteDatabase pDataBase) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String[] getTableNameAndPK() {
		return new String[]{"Payout","PayoutID"};
	}

	public ContentValues creatParms(ModelPayout pInfo) {
		ContentValues _ContentValues = new ContentValues();
		_ContentValues.put("AccountBookID",pInfo.getAccountBookID());
		_ContentValues.put("CategoryID",pInfo.getCategoryID());
		_ContentValues.put("PayWayID",pInfo.getPayWayID());
		_ContentValues.put("PlaceID",pInfo.getPlaceID());
		_ContentValues.put("Amount",pInfo.getAmount().toString());
		_ContentValues.put("PayoutDate",DateTools.getFormatDateTime(pInfo.getPayoutDate(),"yyyy-MM-dd"));
		_ContentValues.put("PayoutType",pInfo.getPayoutType());
		_ContentValues.put("PayoutUserID",pInfo.getPayoutUserID());
		_ContentValues.put("Comment",pInfo.getComment());
		_ContentValues.put("CreateDate",DateTools.getFormatDateTime(pInfo.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
		_ContentValues.put("State",pInfo.getState());
		
		return _ContentValues;
	}

}
