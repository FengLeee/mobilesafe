package com.yiya.mobilesafe.activity.defend;

import java.util.ArrayList;
import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.activity.domain.BlackPerson;
import com.yiya.mobilesafe.db.DefendDb;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
		//findAllData();
	}

	private void findAllData() {
		new Thread() {
			public void run() {
				list = db.findAll();
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
		boolean b = db.insert("123", "678", 1);
		Toast.makeText(this, "add=====" + true, 0).show();
		Log.d(TAG, "add=====" + true);
	}

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView != null) {
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
			mode.setText(person.getMode());
			name.setText(person.getName());
			number.setText(person.getNumber());
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
