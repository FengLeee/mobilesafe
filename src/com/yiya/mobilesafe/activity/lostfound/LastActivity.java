package com.yiya.mobilesafe.activity.lostfound;

import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.activity.HomeActivity;
import com.yiya.mobilesafe.activity.broadcastreceive.LostBroadcastReceive;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LastActivity extends Activity {
	SharedPreferences lsp ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_lastlostfound);
		
		TextView tv_number = (TextView) findViewById(R.id.tv_number);
		lsp = getSharedPreferences("config", MODE_PRIVATE);
		String phonenumber = lsp.getString("phonenumber", "");
		lsp.edit().putBoolean("config", true).commit();
		tv_number.setText(phonenumber);
		
	}
	
	public void reset(View v) {
		Intent it = new Intent(this, OneActivity.class);
		lsp = getSharedPreferences("config", MODE_PRIVATE);
		lsp.edit().putBoolean("config", false).commit();
		startActivity(it);
		finish();
	}
	public void home(View v) {
		Intent it = new Intent(this, HomeActivity.class);
		startActivity(it);
		finish();
	}
}
