package com.yiya.mobilesafe.activity.lostfound;

import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.activity.broadcastreceive.LostBroadcastReceive;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LastActivity extends Activity {
	SharedPreferences sp ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_lastlostfound);
		
		TextView tv_number = (TextView) findViewById(R.id.tv_number);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		String phonenumber = sp.getString("phonenumber", "");
		sp.edit().putBoolean("config", true).commit();
		tv_number.setText(phonenumber);
		
	}
	
	public void reset(View v) {
		Intent it = new Intent(this, OneActivity.class);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		sp.edit().putBoolean("config", false).commit();
		startActivity(it);
		finish();
	}
}
