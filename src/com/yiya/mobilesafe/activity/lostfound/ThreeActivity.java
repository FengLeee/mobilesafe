package com.yiya.mobilesafe.activity.lostfound;

import com.yiya.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ThreeActivity extends Activity {
	EditText et_phone;
	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_threelostfound);

		sp = getSharedPreferences("config", MODE_PRIVATE);

		et_phone = (EditText) findViewById(R.id.et_phone);

		// show back
		String snumber = sp.getString("phonenumber", "");
		et_phone.setText(snumber);
	}

	// next step
	public void next(View v) {
		// et_phone == null ?
		String number = et_phone.getText().toString().trim();
		if (number.isEmpty()) {
			Toast.makeText(this, "安全号码不能为空,请输入安全号码", 0).show();
			return;
		} else {
			sp.edit().putString("phonenumber", number).commit();
			Toast.makeText(this, "安全号码已设置", 0).show();
			// start new activity
			Intent it = new Intent(this, FourActivity.class);
			startActivity(it);
		}
	}

	public void cancer(View v) {
		finish();
	}
}
