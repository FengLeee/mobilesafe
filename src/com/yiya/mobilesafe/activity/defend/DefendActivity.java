package com.yiya.mobilesafe.activity.defend;

import java.util.ArrayList;
import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.activity.domain.BlackPerson;
import com.yiya.mobilesafe.db.DefendDb;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	MyAdapter adapter;
	BlackPerson person;

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
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapter = new MyAdapter();
						defend_number.setAdapter(adapter);
					}
				});
			};
		}.start();
	}

	public void defendAdd(View v) {
		//boolean b = db.insert("号码", "姓名", 9);
		for (int i = 0; i < 15; i++) {
			db.insert("号码"+i, "姓名", 9);
			list.add(new BlackPerson("号码"+i, "姓名", 9));
		}
		//Toast.makeText(this, "add=====" + b, 0).show();
		// refresh adapter
		if (true) {
			//list.add(new BlackPerson("号码", "姓名", 9));
			adapter.notifyDataSetChanged();
		} else {
			Toast.makeText(this, "add=====" + false, 0).show();
		}
	}

	static class viewHolder {
		static TextView mode;
		static TextView name;
		static TextView number;
	}

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			viewHolder holder;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.defenfitems, null);
				holder = new viewHolder();
				holder.mode = (TextView) view
						.findViewById(R.id.tv_defend_mode);
				holder.name = (TextView) view
						.findViewById(R.id.tv_defend_name);
				holder.number = (TextView) view
						.findViewById(R.id.tv_defend_number);
				view.setTag(holder);
			} else {
				view = convertView;
				 holder = (com.yiya.mobilesafe.activity.defend.DefendActivity.viewHolder) convertView
						.getTag();
			}
			person = list.get(position);

			holder.number.setText(person.getNumber());

			holder.name.setText(person.getName());

			holder.mode.setText("" + person.getMode());
			// set delete method
			ImageButton ib_defend = (ImageButton) view
					.findViewById(R.id.ib_defend);

			ib_defend.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							DefendActivity.this);
					builder.setTitle("移出黑名单");
					builder.setMessage("将不再屏蔽号码");

					builder.setPositiveButton(
							"确认移除",
							new android.content.DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// delete and refresh adapter
									boolean b = db.delete(person.getNumber());
									if (b) {
										list.remove(person);
										adapter.notifyDataSetChanged();
									} else {
										list.remove(person);
										adapter.notifyDataSetChanged();
										Toast.makeText(DefendActivity.this,
												"delete=====" + b, 0).show();
									}
								}
							});
					builder.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// cancer
									dialog.dismiss();
								}
							});
					DialogInterface dialog = builder.show();

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
