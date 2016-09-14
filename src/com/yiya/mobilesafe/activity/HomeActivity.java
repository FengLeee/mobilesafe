package com.yiya.mobilesafe.activity;

import com.yiya.mobilesafe.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class HomeActivity extends Activity {
	GridView gv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// 在这里要显示8控件
		gv = (GridView) findViewById(R.id.gv);
		showHome();
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
			//在这里显示,先写初级代码
			View view = findViewById(R.layout.items);
			
			
			return null;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}
}
