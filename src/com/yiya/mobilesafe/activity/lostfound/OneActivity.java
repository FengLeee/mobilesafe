package com.yiya.mobilesafe.activity.lostfound;

import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.utils.GestureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;

public class OneActivity extends GestureActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_onelostfound);
		
		//设置两个button的方法,add gesture
		
	}
	
	public void cancer(View v) {
		finish();
	}
	public void next(View v) {
		next();
	}

	@Override
	protected void previous() {
		return ;
	}

	@Override
	protected void next() {
		Intent it = new Intent(this, TwoActivity.class);
		startActivity(it);
		finish();
		overridePendingTransition(R.anim.next_in, R.anim.next_exit);
	}
}
