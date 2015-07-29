package cc.aaa.database.base;

import java.util.ArrayList;

import cc.aaa.R;

import android.content.Context;

public class SQLiteDataBaseConfig {
	private static final String DATABASE_NAME = "AAA";
	private static final int VERSION = 1;
	private static SQLiteDataBaseConfig mSQLiteDataBaseConfig;
	/**
	 * ���ڻ�ȡ��Դ�ļ��������Ķ���
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
	 * ���������࣬��̬ʵ��������
	 * 
	 * @return
	 */
	public static ArrayList<String> getTables() {
		ArrayList<String> _ArrayList = new ArrayList<String>();

		// �@�������ļ��еĔ��M
		String[] _SQLiteDALClassName = mContext.getResources().getStringArray(R.array.SQLiteDALClassName);

		// ��ȡ�İ���(cc.aaa)�����Ҫ���������·��
		String _PackagePath = mContext.getPackageName() + ".database.sqlitedal.";
		for (int i = 0; i < _SQLiteDALClassName.length; i++) {
			// ��ȡ����+����
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
