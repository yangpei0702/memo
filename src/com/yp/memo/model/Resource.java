package com.yp.memo.model;

import java.io.Serializable;

public class Resource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Resource() {

	}

	public Resource(int id, int memoId, String name, String fileName,
			String filePath) {
		super();
		this.id = id;
		this.memoId = memoId;
		this.name = name;
		this.fileName = fileName;
		this.filePath = filePath;
	}

	private int id;
	private int memoId;
	private String name;
	private String fileName;
	private String filePath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMemoId() {
		return memoId;
	}

	public void setMemoId(int memoId) {
		this.memoId = memoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
