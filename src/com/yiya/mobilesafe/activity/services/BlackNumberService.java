package com.yiya.mobilesafe.activity.services;

import com.yiya.mobilesafe.db.DefendDb;

import android.app.Service;
import android.content.Intent;
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
						endCall();
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

	protected void endCall() {
		Log.d(TAG, "挂断电话");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "关闭服务");
	}

}
