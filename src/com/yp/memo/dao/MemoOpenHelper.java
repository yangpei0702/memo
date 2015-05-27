package com.yp.memo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoOpenHelper extends SQLiteOpenHelper{
	//t_information建表语句
	public static final String CREATE_INFORMATION ="create table t_information("
			+"id integer primary key autoincrement,"
			+"memo_info text,"
			+"memo_remind integer,"
			+ "remind_date text,"
			+ "memo_finish integer,"
			+ "create_date text)";
	//t_resource建表语句
	public static final String CREATE_RESOURCE ="create table t_resource("
			+ "id integer primary key autoincrement,"
			+ "memo_id integer,"
			+ "name text,"
			+ "file_name text,"
			+ "file_path text,"
			+ "create_date text)";
	//t_info_resource建表语句
	public static final String CREATE_INFO_RESOURCE="create table t_info_resource("
			+ "id integer primary key autoincrement,"
			+ "memo_id integer,"
			+ "resource_id integer)";
			
			
	public MemoOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_INFORMATION);
		db.execSQL(CREATE_RESOURCE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_INFO_RESOURCE);
	}

}
