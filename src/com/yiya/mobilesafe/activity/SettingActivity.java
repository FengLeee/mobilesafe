package com.yiya.mobilesafe.activity;

import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.activity.services.BlackNumberService;
import com.yiya.mobilesafe.ui.SwitchImageView;
import com.yiya.mobilesafe.utils.GetServiceStatues;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SettingActivity extends Activity {
	private static final String TAG = "SettingActivity";
	private SwitchImageView iv_update;
	private SwitchImageView iv_defend;
	private SharedPreferences sp;
	boolean b;
	boolean serviceStatues;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		iv_update = (SwitchImageView) findViewById(R.id.iv_update);
		iv_defend = (SwitchImageView) findViewById(R.id.iv_defend);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		b = sp.getBoolean("status", true);
		
		serviceStatues = GetServiceStatues.getStatues(this,
				"com.yiya.mobilesafe.activity.services.BlackNumberService");
		
		iv_update.setStatus(b);
		iv_defend.setStatus(serviceStatues);
		Log.d(TAG, "serviceStatues====="+serviceStatues);
		if (b) {
			iv_update.setImageResource(R.drawable.on);
		} else {
			iv_update.setImageResource(R.drawable.off);
		}

		if (serviceStatues) {
			iv_defend.setImageResource(R.drawable.on);
		} else {
			iv_defend.setImageResource(R.drawable.off);
		}
	}

	// 要保存是否更新选项
	public void updateStatus(View v) {
		boolean changeValues = iv_update.switchStatus();
		// 要获取状态进行保存
		Editor edit = sp.edit();
		edit.putBoolean("status", changeValues);
		edit.commit();
	}

	

	// 骚扰任务是否开启
	public void defendServiceStatus(View v) {
		Intent service = new Intent(this, BlackNumberService.class);
		boolean changeValues = iv_defend.switchStatus();
		// 要获取状态进行保存
		if (changeValues) {
			startService(service);
		} else {
			stopService(service);
		}

	}
}
