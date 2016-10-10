package com.yiya.mobilesafe.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class GetServiceStatues {

	public static boolean getStatues(Context context, String string) {
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> list = am.getRunningServices(200);
		for (RunningServiceInfo info : list) {
			if(info.service.getClassName().equals(string)) {
				return true;
			}
		}
		return false;
	}

}
