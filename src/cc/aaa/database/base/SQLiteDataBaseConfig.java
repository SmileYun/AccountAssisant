package cc.aaa.database.base;

import java.util.ArrayList;

import cc.aaa.R;

import android.content.Context;

public class SQLiteDataBaseConfig {
	private static final String DATABASE_NAME = "AAA";
	private static final int VERSION = 1;
	private static SQLiteDataBaseConfig mSQLiteDataBaseConfig;
	/**
	 * 用于获取资源文件的上下文对象
	 */
	private static Context mContext;

	private SQLiteDataBaseConfig() {
	}

	public static SQLiteDataBaseConfig getInstance(Context pContext) {
		if (mSQLiteDataBaseConfig == null) {
			synchronized (SQLiteDataBaseConfig.class) {
				if (mSQLiteDataBaseConfig == null) {
					mSQLiteDataBaseConfig = new SQLiteDataBaseConfig();
				}
			}
		}
		mContext = pContext;
		return mSQLiteDataBaseConfig;
	}

	/**
	 * 根据配置类，动态实例化对象
	 * 
	 * @return
	 */
	public static ArrayList<String> getTables() {
		ArrayList<String> _ArrayList = new ArrayList<String>();

		// 獲得配置文件中的數組
		String[] _SQLiteDALClassName = mContext.getResources().getStringArray(R.array.SQLiteDALClassName);

		// 获取的包名(cc.aaa)，组成要反射的完整路径
		String _PackagePath = mContext.getPackageName() + ".database.sqlitedal.";
		for (int i = 0; i < _SQLiteDALClassName.length; i++) {
			// 获取包名+类名
			_ArrayList.add(_PackagePath + _SQLiteDALClassName[i]);
		}
		return _ArrayList;
	}

	public static String getDatabaseName() {
		return DATABASE_NAME;
	}

	public static int getVersion() {
		return VERSION;
	}

}
