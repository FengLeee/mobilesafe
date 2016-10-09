package com.yiya.mobilesafe.activity.defend;

import java.util.ArrayList;
import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.activity.domain.BlackPerson;
import com.yiya.mobilesafe.db.DefendDb;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DefendActivity extends Activity {
	private static final String TAG = "DefendActivity";
	ListView defend_number;
	ArrayList<BlackPerson> list;
	DefendDb db;
	MyAdapter adapter;
	BlackPerson person;
	ImageView iv_empty;
	TextView tv_bar;
	ProgressBar pr_bar;
	RelativeLayout rl_bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_defend);
		defend_number = (ListView) findViewById(R.id.defend_number);
		iv_empty = (ImageView) findViewById(R.id.iv_empty);
		rl_bar = (RelativeLayout) findViewById(R.id.rl_bar);
		iv_empty.setVisibility(ImageView.INVISIBLE);
		db = new DefendDb(this);
		findAllData();
	}

	private void findAllData() {
		new Thread() {
			public void run() {
				SystemClock.sleep(1000);
				list = db.findAll();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// pro _bar always can see ,untile adapter get view
						rl_bar.setVisibility(RelativeLayout.INVISIBLE);
						adapter = new MyAdapter();
						defend_number.setAdapter(adapter);
					}
				});
			};
		}.start();
	}

	public void defendAdd(View v) {
		boolean b = db.insert("号码", "姓名", 9);
		// refresh adapter
		if (b) {
			list.add(new BlackPerson("号码", "姓名", 9));
			adapter.notifyDataSetChanged();
		} else {
			Toast.makeText(this, "add=====" + false, 0).show();
		}
	}

	static class viewHolder {
		TextView mode;
		TextView name;
		TextView number;
		ImageButton ib_defend;
	}

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (list.size() == 0) {
				iv_empty.setVisibility(ImageView.VISIBLE);
				return 0;
			} else {
				iv_empty.setVisibility(ImageView.INVISIBLE);
				return list.size();
			}

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			viewHolder holder;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.defenfitems, null);
				holder = new viewHolder();
				holder.mode = (TextView) view.findViewById(R.id.tv_defend_mode);
				holder.name = (TextView) view.findViewById(R.id.tv_defend_name);
				holder.number = (TextView) view
						.findViewById(R.id.tv_defend_number);
				holder.ib_defend = (ImageButton) view
						.findViewById(R.id.ib_defend);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (com.yiya.mobilesafe.activity.defend.DefendActivity.viewHolder) convertView
						.getTag();
			}
			person = list.get(position);

			holder.number.setText(person.getNumber());

			holder.name.setText(person.getName());
			switch (person.getMode()) {
			case 1:
				holder.mode.setText("拦截电话");
				break;
			case 2:
				holder.mode.setText("拦截短信");
				break;
			case 3:
				holder.mode.setText("拦截电话+短信");
				break;

			default:
				holder.mode.setText("模式出错");
				break;
			}

			
			// set delete method
			holder.ib_defend.setOnClickListener(new OnClickListener() {

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
