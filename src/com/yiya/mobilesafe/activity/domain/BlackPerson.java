package com.yiya.mobilesafe.activity.domain;

public class BlackPerson {
	private String number;
	private String name;
	private int mode;
	public BlackPerson(String number, String name, int mode) {
		super();
		this.number = number;
		this.name = name;
		this.mode = mode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
}
