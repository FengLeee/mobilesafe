package com.yiya.mobilesafe.activity.services;

import java.lang.reflect.Method;
import java.net.URL;

import com.android.internal.telephony.ITelephony;
import com.yiya.mobilesafe.db.DefendDb;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BlackNumberService extends Service {

	private static final String TAG = "BlackNumberService";
	DefendDb db;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		db = new DefendDb(this);
		Log.d(TAG, "开启服务");
		// 监听来电
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		tm.listen(new PhoneStateListener() {
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				super.onCallStateChanged(state, incomingNumber);
				switch (state) {
				case TelephonyManager.CALL_STATE_IDLE:

					break;
				case TelephonyManager.CALL_STATE_RINGING:
					// 挂断电话
					int i = db.find(incomingNumber);
					if (i == 1 || i == 3) {
						endCalll(incomingNumber);
					}
					Log.d(TAG, "int i ====" + i);
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:

					break;

				default:
					break;
				}

			}
		}, PhoneStateListener.LISTEN_CALL_STATE);

	}

	protected void endCalll(final String incomingNumber) {
		Log.d(TAG, "挂断电话");
		// 使用远程服务 调用系统
		try {
			Class clazz = Class.forName("android.os.ServiceManager");
			Method method = clazz.getMethod("getService", String.class);
			IBinder ibinder = (IBinder) method.invoke(null, TELEPHONY_SERVICE);
			// ITelephony.Stub.asInterface()需要Ibinder对象,
			// 而ServiceManager.getService返回的就是Ibinder对象
			// 然后通过这个方法把Ibinder对象转换为父类.这样就可以调用父类接口的方法,而这个方法就在远程service里面
			ITelephony iTelephony = ITelephony.Stub.asInterface(ibinder);
			iTelephony.endCall();
			// 清除通话记录
			final ContentResolver resolver = getContentResolver();
			final Uri url = Uri.parse("content://call_log/calls");

			resolver.registerContentObserver(url, true, new ContentObserver(
					new Handler()) {

				@Override
				public void onChange(boolean selfChange) {
					super.onChange(selfChange);
					resolver.delete(url, "number=?",
							new String[] { incomingNumber });
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "关闭服务");
	}

}
