package com.yiya.mobilesafe.activity.defend;

import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.db.DefendDb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class AddBlackNumberActivity extends Activity implements OnClickListener {
	EditText et_number;
	EditText et_name;
	RadioGroup rgp;
	Button confirm;
	Button cancel;
	DefendDb db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_blacknumber);
		db = new DefendDb(this);

		et_number = (EditText) findViewById(R.id.et_number);
		et_name = (EditText) findViewById(R.id.et_name);
		rgp = (RadioGroup) findViewById(R.id.rgp);
		confirm = (Button) findViewById(R.id.confirm);
		cancel = (Button) findViewById(R.id.cancel);

		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.confirm:
			// save data
			String number = et_number.getText().toString().trim();
			String name = et_name.getText().toString().trim();
			int mode = 0;
			int id = rgp.getCheckedRadioButtonId();
			switch (id) {
			case R.id.radio1:
				mode = 1;
				break;
			case R.id.radio2:
				mode = 2;
				break;
			case R.id.radio3:
				mode = 3;
				break;

			default:
				break;
			}

			if (TextUtils.isEmpty(number)) {
				Animation shake = AnimationUtils.loadAnimation(this,
						R.anim.shake);
				findViewById(R.id.et_number).startAnimation(shake);
			} else if (TextUtils.isEmpty(name)) {
				Animation shake = AnimationUtils.loadAnimation(this,
						R.anim.shake);
				findViewById(R.id.et_name).startAnimation(shake);
			} else {
				boolean insert = db.insert(number, name, mode);
				if (insert) {
					Toast.makeText(getApplicationContext(), "add true==="+mode, 0)
							.show();
					Intent it = new Intent();
					it.putExtra("number", number);
					it.putExtra("name", name);
					it.putExtra("mode", mode);
					setResult(0, it);
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "add false", 0)
							.show();
					finish();
				}

			}
			
			break;
		case R.id.cancel:
			finish();
			break;

		default:
			break;
		}
	}

}
