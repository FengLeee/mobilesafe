package com.yiya.mobilesafe.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

public abstract class GestureActivity extends Activity {
	// add gesture method
	public SharedPreferences sp;
	private GestureDetector gesture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		gesture = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						if (e1.getRawX() - e2.getRawX() > 50) {
							// move next activity
							next();
						}
						if (e2.getRawX() - e1.getRawX() > 50) {
							// move previous activity
							previous();
						}

						return super.onFling(e1, e2, velocityX, velocityY);
					}
				});

	}

	protected abstract void previous();

	protected abstract void next();

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gesture.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

}
