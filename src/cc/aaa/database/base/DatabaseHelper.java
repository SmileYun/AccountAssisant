package cc.aaa.database.base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import cc.aaa.utility.Reflection;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static SQLiteDataBaseConfig SBASECONFIG;
	private static DatabaseHelper SDATABASE_HELPER;
	private Context mContext;
	private Reflection mReflection;
	
	public interface SQLiteDataTable {
		public void OnCreate(SQLiteDatabase p_DataBase); 
		public void OnUpgrade(SQLiteDatabase p_DataBase);
	}

	private DatabaseHelper(Context context) {
		super(context, SBASECONFIG.getDatabaseName(), null, SBASECONFIG.getVersion());
		mContext = context;
	}

	public static DatabaseHelper getDatabaseHelper(Context context) {
		if (SDATABASE_HELPER == null) {
			
			SBASECONFIG = SQLiteDataBaseConfig.getInstance(context);
			
			SDATABASE_HELPER = new DatabaseHelper(context);
		}

		return SDATABASE_HELPER;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		ArrayList<String> _ArrayList = SQLiteDataBaseConfig.getTables();
		mReflection = new Reflection();
		//数据库的加载，使用动态反射,动态实例化对象，(由xxx.class---->SQLiteDALUser对象)
		for (int i = 0; i < _ArrayList.size(); i++) {
			try {
				//动态实例化SQLiteDataTable对象
				SQLiteDataTable _SQLiteDataTable =  
						(SQLiteDataTable) mReflection.newInstance(_ArrayList.get(i),
										new Object[]{mContext},
										new Class[]{Context.class});
				//创建
				_SQLiteDataTable.OnCreate(db);
				
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
