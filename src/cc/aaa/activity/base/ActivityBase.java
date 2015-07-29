package cc.aaa.activity.base;

import java.lang.reflect.Field;

import cc.aaa.activity.UserActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.Toast;

public class ActivityBase extends Activity {
	protected static final int SHOW_TIME = 1;

	protected void showMsg(String msg) {
		Toast.makeText(this, msg, SHOW_TIME).show();
	}

	/**
	 * Activity����ת
	 * 
	 * @param pactivity
	 */
	protected void openActivity(Class<?> pactivity) {
		Intent _Intent = new Intent();
		_Intent.setClass(this, pactivity);
		startActivity(_Intent);
	}

	@Override
	public LayoutInflater getLayoutInflater() {
		LayoutInflater _LayoutInflater = LayoutInflater.from(this);
		return _LayoutInflater;
	}

	/**
	 * �÷���ķ�������ȡ�Ի��������һ��˽�б���������̬�ı���ֵ���������Ƿ�ر�
	 * 
	 * @param pDialog
	 * @param pIsClose
	 *            true Ϊ�ɹر�
	 */
	public void setAlertDialogIsClose(DialogInterface pDialog, Boolean pIsClose) {
		try {
			Field _Field = pDialog.getClass().getSuperclass().getDeclaredField("mShowing");
			_Field.setAccessible(true);
			_Field.set(pDialog, pIsClose);
		} catch (Exception e) {
		}
	}

	/**
	 * ��ʾһ��Toast��Ϣ
	 * 
	 * @param p_Msg
	 *            Ҫ��ʾ����ϢID
	 */
	protected void ShowMsg(int p_ResID) {
		Toast.makeText(this, p_ResID, SHOW_TIME).show();
	}

	protected void ToastMsg(String msg) {
		Toast.makeText(this, msg, 3000).show();
	}
}
