package com.yiya.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DefendSQLiteOpenHelper extends SQLiteOpenHelper {

	public DefendSQLiteOpenHelper(Context context) {
		super(context, "defend.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String table = "create table bn (_id integer primary key autoincrement, number varchar(20), name varchar(20), mode int(1))";
		db.execSQL(table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
