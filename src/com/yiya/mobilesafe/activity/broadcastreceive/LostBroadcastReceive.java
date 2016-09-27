package com.yiya.mobilesafe.activity.broadcastreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;

public class LostBroadcastReceive extends BroadcastReceiver {

	private static final String TAG = "LostBroadcastReceive";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, intent.getAction());
		SharedPreferences sp = context.getSharedPreferences("config", 0);
		boolean config = sp.getBoolean("config", false);
		String simid = sp.getString("SIMID", "");

		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);
		String simSerialNumber = manager.getSimSerialNumber();
		
		
		Log.d(TAG, simid + config);
		if(config) {
			if(simSerialNumber.equals(simid+"88")){
				return;
			}else {
				//send mes to safe number;
				android.telephony.SmsManager default1 = android.telephony.SmsManager.getDefault();
				default1.sendTextMessage(sp.getString("phonenumber", ""), null, "phone lost", null, null);
				Log.d(TAG, "send mes====="+sp.getString("phonenumber", ""));
			}
		}else {
			return;
		}
		
		

	}
}
