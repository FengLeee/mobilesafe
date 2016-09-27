package com.yiya.mobilesafe.activity.lostfound;

import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.R.drawable;
import com.yiya.mobilesafe.utils.GestureActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class TwoActivity extends GestureActivity {

	ImageView iv_lock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twolostfound);
		iv_lock = (ImageView) findViewById(R.id.iv_lock);

		// 回显
		showLock();

	}

	private void showLock() {
		boolean sim = sp.getBoolean("SIM", false);
		if (sim) {
			iv_lock.setImageResource(R.drawable.list_button_lock_pressed);
		} else {
			iv_lock.setImageResource(R.drawable.list_button_unlock_pressed);
		}
	}

	public void lockChange(View v) {
		// change lock statues
		boolean sim = sp.getBoolean("SIM", false);
		Editor edit = sp.edit();
		if (!sim) {
			iv_lock.setImageResource(R.drawable.list_button_lock_pressed);
			edit.putBoolean("SIM", true);
			// get sim card number
			TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			String simid = manager.getSimSerialNumber();
			edit.putString("SIMID", simid);
			edit.commit();
		} else {
			iv_lock.setImageResource(R.drawable.list_button_unlock_pressed);
			edit.putBoolean("SIM", false);
			edit.putString("SIMID", "");
			edit.commit();
		}
	}

	public void cancer(View v) {
		previous();
	}

	public void next(View v) {
		next();

	}

	@Override
	protected void previous() {
		Intent it = new Intent(this, OneActivity.class);
		startActivity(it);
		finish();
		overridePendingTransition(R.anim.previous_in, R.anim.previous_exit);
	}

	@Override
	protected void next() {
		boolean sim = sp.getBoolean("SIM", false);
		// next activity
		if (sim) {
			Intent it = new Intent(this, ThreeActivity.class);
			startActivity(it);
			finish();
			overridePendingTransition(R.anim.next_in, R.anim.next_exit);
		} else {
			Toast.makeText(this, "必须要设置SIM卡号才能正常使用手机防盗功能", 0).show();
			return;
		}
	}

}
