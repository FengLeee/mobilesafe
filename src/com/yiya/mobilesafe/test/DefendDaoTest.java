package com.yiya.mobilesafe.test;

import com.yiya.mobilesafe.db.DefendDb;

import android.test.AndroidTestCase;

public class DefendDaoTest extends AndroidTestCase {

	public void insert() {
		DefendDb db = new DefendDb(getContext());
		boolean b = db.insert("110", "报警电话", 1);
		System.out.println("insert=======" + b);
	}

	public void delete() {
		DefendDb db = new DefendDb(getContext());
		boolean b = db.delete("110");
		System.out.println("delete=======" + b);
	}

	public void update() {
		DefendDb db = new DefendDb(getContext());
		boolean b = db.update("110", "火警电话", 3);
		System.out.println("update=======" + b);
	}

	public void find() {
		DefendDb db = new DefendDb(getContext());
		int i = db.find("110");
		System.out.println("find_mode=======" + i);
	}
}
