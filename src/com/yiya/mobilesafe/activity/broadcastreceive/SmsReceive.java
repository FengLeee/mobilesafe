package com.yiya.mobilesafe.activity.broadcastreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SmsReceive extends BroadcastReceiver {

	private static final String TAG = "SmsReceive";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "短信到来==="+ intent.getAction());
	}

}
