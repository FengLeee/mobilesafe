package com.yiya.mobilesafe.activity.lostfound;

import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.activity.broadcastreceive.AdminReceive;
import com.yiya.mobilesafe.utils.GestureActivity;
import com.yiya.mobilesafe.utils.GetAdmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Toast;

public class OneActivity extends GestureActivity {
	protected static final String TAG = "OneActivity";
	public ComponentName who;
	public DevicePolicyManager manager;
	boolean b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_onelostfound);
		who = new ComponentName(OneActivity.this, AdminReceive.class);
		manager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		
		b = manager.isAdminActive(who);
		if(!b) {
			getAdmin();
		}

		// 设置两个button的方法,add gesture

	}

	/*****
	 * 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	
		boolean b = manager.isAdminActive(who);
		sp.edit().putBoolean("admin", b);
		if (b) {
			Toast.makeText(OneActivity.this, "获取权限成功", 0).show();
		} else {
			Toast.makeText(OneActivity.this, "获取权限失败,请退出界面重试", 0).show();
			finish();
		}
	}

	private void getAdmin() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("获取系统管理员权限");
		builder.setMessage("获取到管理员权限,手机防盗功能才能正常工作");
		builder.setPositiveButton("获取权限", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (b) {
					Toast.makeText(OneActivity.this, "获取权限成功", 0).show();
					dialog.dismiss();
				} else {

					/*Intent intent = new Intent(
							DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
					intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, who);
					intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
							"要获取系统权限才能使用此功能");
					startActivityForResult(intent, 0);*/
					Intent it = new Intent(OneActivity.this, GetAdmin.class);
					startActivity(it);

				}
			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(OneActivity.this, "获取权限失败,请退出界面重试", 0).show();
				finish();
			}
		});
		AlertDialog dialog = builder.show();
	}

	public void cancer(View v) {
		finish();
	}

	public void next(View v) {
		next();
	}

	@Override
	protected void previous() {
		return;
	}

	@Override
	protected void next() {
		Intent it = new Intent(this, TwoActivity.class);
		startActivity(it);
		finish();
		overridePendingTransition(R.anim.next_in, R.anim.next_exit);
	}
}
