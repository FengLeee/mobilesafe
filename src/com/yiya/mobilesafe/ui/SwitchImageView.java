package com.yiya.mobilesafe.ui;

import com.yiya.mobilesafe.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class SwitchImageView extends ImageView {

	private static final String TAG = "SwitchImageView";

	public SwitchImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SwitchImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SwitchImageView(Context context) {
		super(context);
	}
	
	//自定义控件,通过调用控件的方法跟换图片显示,状态要根据读取的sp文件来设置默认值.
	private boolean status;
	
	public boolean switchStatus () {
		boolean values = changeValues(status);
		if(values) {
			Log.d(TAG, "STATUS1"+status);
			setImageResource(R.drawable.on);
		}else {
			Log.d(TAG, "STATUS2"+status);
			setImageResource(R.drawable.off);
		}
		return values;
	}
	
	public void setStatus(Boolean b) {
		this.status = b;
		Log.d(TAG, "status==="+b);
	}
	public boolean changeValues(Boolean b) {
		this.status = !b;
		return  this.status;
	}
	

}
