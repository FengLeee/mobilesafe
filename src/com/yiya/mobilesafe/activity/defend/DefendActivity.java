package com.yiya.mobilesafe.activity.defend;

import java.util.ArrayList;
import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.activity.domain.BlackPerson;
import com.yiya.mobilesafe.db.DefendDb;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DefendActivity extends Activity {
	private static final String TAG = "DefendActivity";
	ListView defend_number;
	ArrayList<BlackPerson> list;
	DefendDb db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_defend);
		defend_number = (ListView) findViewById(R.id.defend_number);
		db = new DefendDb(this);
		findAllData();
	}

	private void findAllData() {
		new Thread() {
			public void run() {
				list = db.findAll();
				Log.d(TAG, "size===="+list.size());
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						MyAdapter adapter = new MyAdapter();
						defend_number.setAdapter(adapter);
					}
				});
			};
		}.start();
	}

	public void defendAdd(View v) {
		// add defend number
		boolean b = db.insert("号码", "姓名", 9);
		Toast.makeText(this, "add=====" + true, 0).show();
		//refresh ui
		Log.d(TAG, "add=====" + true);
		findAllData();
	}

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.defenfitems, null);
			} else {
				view = convertView;
			}
			TextView mode = (TextView) view.findViewById(R.id.tv_defend_mode);
			TextView name = (TextView) view.findViewById(R.id.tv_defend_name);
			TextView number = (TextView) view
					.findViewById(R.id.tv_defend_number);
			BlackPerson person = list.get(position);
			
			number.setText(person.getNumber());
			
			name.setText(person.getName());
			
			mode.setText(""+person.getMode());
			//set delete method
			ImageButton ib_defend = (ImageButton) view.findViewById(R.id.ib_defend);
			
			ib_defend.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(DefendActivity.this);
					//builder.setPositiveButton(text, listener);
				}
			});
			
			
			
			
			return view;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	}
}
