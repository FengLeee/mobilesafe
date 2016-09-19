package com.yiya.mobilesafe.activity;

import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.activity.lostfound.OneActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	private static final String TAG = "HomeActivity";
	GridView gv;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		// 获取SP
		sp = getSharedPreferences("pw", MODE_PRIVATE);
		ImageView iv_heima = (ImageView) findViewById(R.id.iv_heima); 
		ObjectAnimator animator = ObjectAnimator.ofFloat(iv_heima, "rotationY",
				0, 359);
		// 实现动画效果
		animator.setRepeatCount(ObjectAnimator.INFINITE);
		animator.setRepeatMode(ObjectAnimator.RESTART);
		animator.setDuration(2000);
		animator.start();
		// 在这里要显示8控件
		gv = (GridView) findViewById(R.id.gv);
		showHome();
		// 设置gridvie监听器
		setOnItemClickListener();
	}

	private void setOnItemClickListener() {
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					String passWord = sp.getString("pw", "");
					// 进入防盗相关页面,判断是否已经设置密码
					if ("".equals(passWord)) {
						// 密码为空,进入设置密码界面
						setPw();
					} else {
						// 进入输入密码界面
						confirmPw();
					}

					break;
				case 1:

					break;
				case 2:

					break;
				case 3:

					break;
				case 4:

					break;
				case 5:

					break;
				case 6:

					break;
				case 7:

					break;
				case 8:

					break;

				default:
					break;
				}
			}
		});
	}

	protected void confirmPw() {
		
		//设置dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = View.inflate(this, R.layout.activity_confirmpw, null);
		builder.setView(view);
		final AlertDialog dialog = builder.show();
		
		
		//获取控件
		Button confirm = (Button) view.findViewById(R.id.confirm);
		Button cancer = (Button) view.findViewById(R.id.cancer);

		final EditText et_pw = (EditText) view.findViewById(R.id.et_pw);
		 //确认按钮
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 判断密码是否正确
				String cpw = et_pw.getText().toString();
				String pw = sp.getString("pw", "");
				
				if (cpw.isEmpty()) {
					Toast.makeText(getApplicationContext(), "密码不能为空,请重新输入", 0)
							.show();
				} else {
					if (cpw.equals(pw)) {
						// 密码正确.进入界面,判断是否已经设置防盗,设置了直接进入最后界面,没设置重头开始设置
						Log.d(TAG, "进入界面设置");
						
						sp = getSharedPreferences("config", MODE_PRIVATE);
						boolean config = sp.getBoolean("config", false);
						if(config) {
							//进入最后
							loadResoult();
						}else {
							//进入设置
							dialog.dismiss();
							lostFoundSetting();
						}
						
					} else {
						Toast.makeText(getApplicationContext(), "密码错误,请重新输入", 0)
								.show();
					}
				}
			}
		});
		
		
		cancer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
	}

	protected void lostFoundSetting() {
		//进入第一个防盗页面
		Intent it = new Intent(this, OneActivity.class);
		startActivity(it);
	}

	protected void loadResoult() {
		//进入最后一个防盗页面		
	}

	protected void setPw() {
		// 进入密码设置
		AlertDialog.Builder setPw = new AlertDialog.Builder(this);
		View v = View.inflate(this, R.layout.activity_setpw, null);
		setPw.setView(v);
		final AlertDialog dialog = setPw.show();

		final EditText et_pw = (EditText) v.findViewById(R.id.et_pw);
		final EditText et_pw_confirm = (EditText) v.findViewById(R.id.et_pw_confirm);

		Button confirm = (Button) v.findViewById(R.id.confirm);
		Button cancer = (Button) v.findViewById(R.id.cancer);
		// 确认密码
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				 String spw = et_pw.getText().toString();
				 String spwc = et_pw_confirm.getText().toString();
				if (spw.equals(spwc)) {
					if (spw.isEmpty()) {
						Toast.makeText(getApplicationContext(), "密码不能为空,请重新输入",
								0).show();
					} else {
						// 保存密码
						Log.d(TAG, "确认密码,保存密码");
						Editor edit = sp.edit();
						edit.putString("pw", spw);
						edit.commit();
						
						Toast.makeText(getApplicationContext(), "密码设置成功", 0)
								.show();
						// 进入确认密码界面
						dialog.dismiss();
						confirmPw();
					}
				} else {
					Toast.makeText(getApplicationContext(), "密码不一致,请重新输入", 0)
							.show();
				}
			}
		});
		cancer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				return;
			}
		});

	}

	public void setting(View v) {
		Intent setting = new Intent(this, SettingActivity.class);
		startActivity(setting);
	}

	private void showHome() {
		// 显示gridview
		MyGridView v = new MyGridView();
		gv.setAdapter(v);

	}

	private class MyGridView extends BaseAdapter {

		@Override
		public int getCount() {
			return 8;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 在这里显示,先写初级代码,静态方法
			/*
			 * if(convertView != null) { return convertView; }else { return
			 * View.inflate(HomeActivity.this, R.layout.items, null); }
			 */
			View v = null;
			if (convertView != null) {
				v = convertView;
			} else {
				v = View.inflate(HomeActivity.this, R.layout.items, null);
			}
			int[] arr = new int[] { R.drawable.sjfd, R.drawable.srlj,
					R.drawable.rjgj, R.drawable.jcgl, R.drawable.lltj,
					R.drawable.sjsd, R.drawable.xtjs, R.drawable.szzx };
			String[] title = new String[] { "手机防盗", "骚扰拦截", "软件管家", "进程管理",
					"流量统计", "手机杀毒", "系统加速", "常用工具" };
			String[] title1 = new String[] { "远程定位手机", "全面拦截骚扰", "管理您的软件",
					"管理正在运行", "流量使用统计", "病毒无法藏身", "手机快如火箭", "常用工具大全" };

			ImageView iv_items = (ImageView) v.findViewById(R.id.iv_items);
			iv_items.setImageResource(arr[position]);
			TextView tv_items_title = (TextView) v
					.findViewById(R.id.tv_items_title);
			tv_items_title.setText(title[position]);
			TextView tv_items_title1 = (TextView) v
					.findViewById(R.id.tv_items_title1);
			tv_items_title1.setText(title1[position]);
			return v;

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
