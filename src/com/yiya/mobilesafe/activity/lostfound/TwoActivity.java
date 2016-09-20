package com.yiya.mobilesafe.activity.lostfound;

import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.R.drawable;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TwoActivity extends Activity {
	SharedPreferences sp;
	ImageView iv_lock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twolostfound);
		iv_lock = (ImageView) findViewById(R.id.iv_lock);

		sp = getSharedPreferences("config", MODE_PRIVATE);
		
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
		//change lock statues
		boolean sim = sp.getBoolean("SIM", false);
		Editor edit = sp.edit();
		if (!sim) {
			iv_lock.setImageResource(R.drawable.list_button_lock_pressed);
			edit.putBoolean("SIM", true);
			//get sim card number
			
			
			edit.putString("SIMID", "");
			edit.commit();
		} else {
			iv_lock.setImageResource(R.drawable.list_button_unlock_pressed);
			edit.putBoolean("SIM", false);
			edit.commit();
		}
	}
	
	public void cancer(View v) {
		finish();
	}
	
	public void next(View v) {
		//next activity
		
	}
	
	
}
