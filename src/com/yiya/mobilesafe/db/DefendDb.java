package com.yiya.mobilesafe.db;

import java.util.ArrayList;
import java.util.List;

import com.yiya.mobilesafe.activity.domain.BlackPerson;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DefendDb {
	ArrayList<BlackPerson> list;
	DefendSQLiteOpenHelper helper;

	public DefendDb(Context context) {
		helper = new DefendSQLiteOpenHelper(context);
	}

	// insert
	public boolean insert(String number, String name, int mode) {
		SQLiteDatabase database = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", number);
		values.put("name", name);
		values.put("mode", mode);
		long l = database.insert("bn", null, values);
		database.close();
		if (l == -1) {
			return false;
		} else {
			return true;
		}
	}

	// delete
	public boolean delete(String number) {
		SQLiteDatabase database = helper.getWritableDatabase();
		int i = database.delete("bn", "number=?", new String[] { number });
		database.close();
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	// update
	public boolean update(String number, String name, int mode) {
		SQLiteDatabase database = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", number);
		values.put("name", name);
		values.put("mode", mode);
		int i = database.update("bn", values, "number = ?",
				new String[] { number });
		database.close();
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	// find
	public int find(String number) {
		SQLiteDatabase database = helper.getWritableDatabase();
		Cursor cursor = database.query("bn", new String[] { "mode" },
				"number = ?", new String[] { number }, null, null, null);
		database.close();
		if(cursor.moveToNext()) {
			int i = cursor.getInt(0);
			return i;
		}else {
			return -1;
		}
	}
	//findall database
	public ArrayList<BlackPerson> findAll() {
		SQLiteDatabase database = helper.getWritableDatabase();
		Cursor cursor = database.query("bn", null, null, null, null, null, null);
		if(cursor.moveToNext()) {
			String number = cursor.getString(0);
			String name = cursor.getString(1);
			int mode = cursor.getInt(2);
			BlackPerson p = new BlackPerson(number, name, mode);
			list.add(p);
		}
		database.close();
		return list;
		
	}
}
