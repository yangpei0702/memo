package com.yp.memo.model;

import java.io.Serializable;

public class TimeInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;
	private int mSecond;
	public int getmSecond() {
		return mSecond;
	}
	public void setmSecond(int mSecond) {
		this.mSecond = mSecond;
	}
	public int getmYear() {
		return mYear;
	}
	public void setmYear(int mYear) {
		this.mYear = mYear;
	}
	public int getmMonth() {
		return mMonth;
	}
	public void setmMonth(int mMonth) {
		this.mMonth = mMonth;
	}
	public int getmDay() {
		return mDay;
	}
	public void setmDay(int mDay) {
		this.mDay = mDay;
	}
	public int getmHour() {
		return mHour;
	}
	public void setmHour(int mHour) {
		this.mHour = mHour;
	}
	public int getmMinute() {
		return mMinute;
	}
	public void setmMinute(int mMinute) {
		this.mMinute = mMinute;
	}
}
