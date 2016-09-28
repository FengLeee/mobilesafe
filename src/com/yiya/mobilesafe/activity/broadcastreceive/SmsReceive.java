package com.yiya.mobilesafe.activity.broadcastreceive;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.util.Log;

import com.yiya.mobilesafe.R;

public class SmsReceive extends BroadcastReceiver {

	private static final String TAG = "SmsReceive";

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sp = context.getSharedPreferences("config",
				context.MODE_PRIVATE);
		boolean b1 = sp.getBoolean("config", false);
		if (!b1) {
			return;
		}

		Object[] objs = (Object[]) intent.getExtras().get("pdus");

		for (Object object : objs) {
			SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
			String body = message.getMessageBody();
			if ("#*alarm*#".equals(body)) {

				// MediaPlayer player = MediaPlayer.create(context, R.raw.ddd);
				// player.setLooping(true);
				// player.start();

				Log.d(TAG, "alarm");

				abortBroadcast();
			} else if ("#*location*#".equals(body)) {
				Log.d(TAG, "location");

				abortBroadcast();
			} else if ("#*wipedata*#".equals(body)) {

				// get admin
				DevicePolicyManager manager = (DevicePolicyManager) context
						.getSystemService(context.DEVICE_POLICY_SERVICE);
				manager.wipeData(manager.WIPE_EXTERNAL_STORAGE);
				Log.d(TAG, "wipedata");
				abortBroadcast();

			} else if ("#*lockscreen*#".equals(body)) {
				DevicePolicyManager manager = (DevicePolicyManager) context
						.getSystemService(context.DEVICE_POLICY_SERVICE);
				manager.lockNow();
				abortBroadcast();
			}

		}

	}

}
