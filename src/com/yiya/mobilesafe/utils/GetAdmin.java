package com.yiya.mobilesafe.utils;

import com.yiya.mobilesafe.activity.broadcastreceive.AdminReceive;
import com.yiya.mobilesafe.activity.lostfound.OneActivity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class GetAdmin extends OneActivity {
	private static final String TAG = "GetAdmin";
	public ComponentName who;
	public DevicePolicyManager manager;
	public SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		manager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		who = new ComponentName(this, AdminReceive.class);
		adminStatues();
	}

	public void adminStatues() {

		boolean b = manager.isAdminActive(who);
		if (b) {

		} else {
			// 激活admin
			Log.d(TAG, "激活admin");
			Intent intent = new Intent(
					DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, who);
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
					"要获取系统权限才能使用此功能");
			startActivityForResult(intent, 0);
			finish();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean b = manager.isAdminActive(who);
		sp.edit().putBoolean("admin", b).commit();
		
	}
}
