package com.yiya.mobilesafe.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.yiya.mobilesafe.R;
import com.yiya.mobilesafe.utils.InputStreamTostring;
import com.yiya.mobilesafe.utils.PackageInformation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {
	protected static final String TAG = "SplashActivity";
	protected static final int UPDATE_NOT_FOUND = 0;
	protected static final int FOUND_UPDATE = 1;
	protected static final int ERROR = 2;
	TextView tv_version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tv_version = (TextView) findViewById(R.id.tv_version);
		try {
			String currentVersion = PackageInformation.getVersion(this);
			tv_version.setText("版本:" + currentVersion);

		} catch (NameNotFoundException e) {
			tv_version.setText("版本解析错误");
			e.printStackTrace();
		}
		SharedPreferences sp = getSharedPreferences("update", MODE_PRIVATE);
		boolean b = sp.getBoolean("status", true);
		if (b) {
			checkVersion();
		} else {
			new Thread() {
				public void run() {
					SystemClock.sleep(2000);
					loadHomeUI();
				};
			}.start();

		}

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPDATE_NOT_FOUND:
				loadHomeUI();
				break;

			case FOUND_UPDATE:
				// 弹出对话框提示更新,抽出一个方法
				Bundle data = msg.getData();
				updata(data);
				break;
			case ERROR:
				loadHomeUI();
				String err = (String) msg.obj;
				Toast.makeText(SplashActivity.this, err, 0).show();
				break;

			default:
				break;
			}

		};
	};

	private void checkVersion() {

		final String ip = getResources().getString(R.string.ip);
		final Message msg = Message.obtain();
		final long dt = System.currentTimeMillis();
		new Thread() {
			public void run() {
				try {
					URL url = new URL(ip);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(2000);
					int code = conn.getResponseCode();
					if (200 == code) {
						InputStream in = conn.getInputStream();
						String str = InputStreamTostring.getString(in);
						JSONObject json = new JSONObject(str);
						String serverVersion = json.getString("version");
						if (PackageInformation.getVersion(SplashActivity.this)
								.equals(serverVersion)) {

							msg.what = UPDATE_NOT_FOUND;

						} else {
							msg.what = FOUND_UPDATE;
							Bundle data = new Bundle();
							String desc = json.getString("desc");
							data.putString("serverVersion", serverVersion);
							data.putString("desc", desc);
							msg.setData(data);
						}

					} else {
						msg.what = ERROR;
						msg.obj = "网络连接错误";
					}

				} catch (MalformedURLException e) {
					msg.what = ERROR;
					msg.obj = "网络连接错误1";
					e.printStackTrace();
				} catch (IOException e) {
					msg.what = ERROR;
					msg.obj = "网络连接错误2";
					e.printStackTrace();
				} catch (JSONException e) {
					msg.what = ERROR;
					msg.obj = "网络连接错误3";
					e.printStackTrace();
				} catch (NameNotFoundException e) {
					msg.what = ERROR;
					msg.obj = "网络连接错误4";
					e.printStackTrace();
				} finally {
					long ct = System.currentTimeMillis();
					long t = ct - dt;
					if (t > 2000) {
						handler.sendMessage(msg);
					} else {
						SystemClock.sleep(2000 - t);
						handler.sendMessage(msg);
					}
				}

			};
		}.start();
	}

	protected void updata(Bundle bundle) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SplashActivity.this);
		String serverVersion = bundle.getString("serverVersion");
		String desc = bundle.getString("desc");

		builder.setTitle("发现新版本:" + serverVersion);
		builder.setMessage("版本:" + serverVersion + "," + desc);

		builder.setNegativeButton("暂不更新", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				loadHomeUI();
			}
		});

		builder.setPositiveButton("马上更新", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.d(TAG, "进入更新界面");
				// 怎么更新,以及显示进度条
				Intent it = new Intent();
				it.setAction("android.intent.action.VIEW");
				//it.setDataAndType(data, "application/vnd.android.package-archive");
			}
		});
		builder.show();
	}

	protected void loadHomeUI() {
		Intent it = new Intent(SplashActivity.this, HomeActivity.class);
		startActivity(it);
		finish();
	}
}
