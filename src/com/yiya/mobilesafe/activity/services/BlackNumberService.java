package com.yiya.mobilesafe.activity.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BlackNumberService extends Service {

	private static final String TAG = "BlackNumberService";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		Log.d(TAG, "开启服务");
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "关闭服务");
	}

}
