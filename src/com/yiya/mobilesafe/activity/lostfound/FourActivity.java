package com.yiya.mobilesafe.activity.lostfound;

import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.utils.GestureActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class FourActivity extends GestureActivity {
	CheckBox ck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fourlostfound);
		// show back
		ck = (CheckBox) findViewById(R.id.ck);
		boolean b = sp.getBoolean("check", false);
		ck.setChecked(b);
	}

	// if checkbox save statues of lostfound
	public void next(View v) {
		next();
	}

	public void cancer(View v) {
		previous();
	}

	@Override
	protected void previous() {
		Intent it = new Intent(this, ThreeActivity.class);
		startActivity(it);
		finish();
		overridePendingTransition(R.anim.previous_in, R.anim.previous_exit);
	}

	@Override
	protected void next() {
		boolean checked = ck.isChecked();
		if (checked) {
			sp.edit().putBoolean("check", true).commit();
			Toast.makeText(this, "状态保存成功", 0).show();
			// start the last activity
			Intent it = new Intent(this, LastActivity.class);
			startActivity(it);
			finish();
			overridePendingTransition(R.anim.next_in, R.anim.next_exit);
		} else {
			// toast
			sp.edit().putBoolean("check", false).commit();
			Toast.makeText(this, "必须勾选才能生效,否则无法正常使用手机防盗功能", 0).show();
			return;
		}
	}

}
