package com.yp.memo.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Information implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Information() {

	}

	public Information(int id, String memoInfo, int memoRemind,
			String remindDate, int memoFinish, String createDate) {
		super();
		this.id = id;
		this.memoInfo = memoInfo;
		this.memoRemind = memoRemind;
		this.remindDate = remindDate;
		this.memoFinish = memoFinish;
		
		this.createDate = createDate;
	}

	private int id;
	private String memoInfo;
	private int memoRemind;
	private String remindDate;
	private int memoFinish;
	private String createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMemoInfo() {
		return memoInfo;
	}

	public void setMemoInfo(String memoInfo) {
		this.memoInfo = memoInfo;
	}

	public int getMemoRemind() {
		return memoRemind;
	}

	public void setMemoRemind(int memoRemind) {
		this.memoRemind = memoRemind;
	}

	public String getRemindDate() {
		return remindDate;
	}

	public void setRemindDate(String remindDate) {
		this.remindDate = remindDate;
	}

	public int getMemoFinish() {
		return memoFinish;
	}

	public void setMemoFinish(int memoFinish) {
		this.memoFinish = memoFinish;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		
		this.createDate = createDate;
	}

}
