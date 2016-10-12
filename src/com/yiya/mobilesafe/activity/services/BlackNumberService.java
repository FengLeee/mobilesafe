package com.yiya.mobilesafe.activity.services;

import java.lang.reflect.Method;
import java.net.URL;

import com.android.internal.telephony.ITelephony;
import com.yiya.mobilesafe.db.DefendDb;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BlackNumberService extends Service {
	private static final String TAG = "BlackNumberService";
	DefendDb db;
	MyListen listener;
	MySmsReceive receiver;
	TelephonyManager tm;

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
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		listener = new MyListen();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

		// 使用代码注册广播监听器
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);

		receiver = new MySmsReceive();
		registerReceiver(receiver, filter);
	}

	class MyListen extends PhoneStateListener {

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

	}

	class MySmsReceive extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "Action=====" + intent.getAction());
			Log.d(TAG, "拦截短信");
			//获取到发送短信的号码,判断是不知在模式二和三
			Bundle bundle = intent.getExtras();
			Object[] object = (Object[]) bundle.get("pdus");
			String address = "";
			for (Object object2 : object) {
				SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) object2);
				address = smsMessage.getOriginatingAddress();
				Log.d(TAG, "address==="+address);
			}
			int i = db.find(address);
			if(i==2||i==3) {
				//setResultData("");
				abortBroadcast();
			}
		}

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
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		
		unregisterReceiver(receiver);
		receiver = null;
	}

}
