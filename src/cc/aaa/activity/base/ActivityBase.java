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
	 * Activity的跳转
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
	 * 用反射的方法，获取对话框里面的一个私有变量，并动态改变其值，设置他是否关闭
	 * 
	 * @param pDialog
	 * @param pIsClose
	 *            true 为可关闭
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
	 * 显示一个Toast信息
	 * 
	 * @param p_Msg
	 *            要显示的信息ID
	 */
	protected void ShowMsg(int p_ResID) {
		Toast.makeText(this, p_ResID, SHOW_TIME).show();
	}

	protected void ToastMsg(String msg) {
		Toast.makeText(this, msg, 3000).show();
	}
}
