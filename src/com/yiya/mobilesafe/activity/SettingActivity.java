package com.yiya.mobilesafe.activity;


import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.ui.SwitchImageView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SettingActivity extends Activity {
	private static final String TAG = "SettingActivity";
	private SwitchImageView iv_update ;
	private SharedPreferences sp;
	boolean b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		iv_update = (SwitchImageView) findViewById(R.id.iv_update);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		b = sp.getBoolean("status", true);
		Log.d(TAG, "b==="+b);
		iv_update.setStatus(b);
		
		if(b) {
			iv_update.setImageResource(R.drawable.on);
		}else {
			iv_update.setImageResource(R.drawable.off);
		}
	}

	// 要保存是否更新选项
	public void updateStatus(View v) {
		boolean changeValues = iv_update.switchStatus();
		//要获取状态进行保存
		Editor edit = sp.edit();
		edit.putBoolean("status", changeValues);
		edit.commit();
	}
}
