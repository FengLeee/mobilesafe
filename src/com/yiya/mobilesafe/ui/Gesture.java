package com.yiya.mobilesafe.ui;

import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Gesture implements OnTouchListener, OnGestureListener {

	@Override
	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {

	}

	@Override
	public void onGesture(GestureOverlayView overlay, MotionEvent event) {

	}

	@Override
	public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {

	}

	@Override
	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

}
