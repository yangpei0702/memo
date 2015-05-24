package com.yp.memo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yp.memo.model.Information;

public class MemoDB {
	public static final String DB_NAME="memo";
	public static final int VERSION=1;
	private static MemoDB memoDB;
	private SQLiteDatabase db;
	private MemoDB(Context context) {
		MemoOpenHelper dbHelper =new MemoOpenHelper(context, 
				DB_NAME,null, VERSION);
		db=dbHelper.getWritableDatabase();
	}
	
	public synchronized static MemoDB getInstance(Context context){
		if(memoDB==null){
			memoDB=new MemoDB(context);
		}
		return memoDB;
	}
	
	//添加信息
	public void saveInformation(Information information){
		if(information!=null){
			ContentValues values=new ContentValues();
			values.put("memo_info", information.getMemoInfo());
			values.put("memo_remind", information.getMemoRemind());
			values.put("remind_date", information.getRemindDate());
			values.put("memo_finish", information.getMemoFinish());
			values.put("create_date", information.getCreateDate());
			db.insert("t_information", null, values);
		}
	}
	
	//读取所有信息
	public List<Information> loadProvinces(){
		List<Information> list =new ArrayList<Information>();
		Cursor cursor=db
				.query("t_information", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Information info =new Information();
				info.setId(cursor.getInt(cursor.getColumnIndex("id")));
				info.setMemoInfo(cursor.getString(cursor.getColumnIndex("memo_info")));
				info.setMemoRemind(cursor.getInt(cursor.getColumnIndex("memo_remind")));
				info.setRemindDate(cursor.getString(cursor.getColumnIndex("remind_date")));
				info.setMemoFinish(cursor.getInt(cursor.getColumnIndex("memo_finish")));
				info.setCreateDate(cursor.getString(cursor.getColumnIndex("create_date")));
			}while(cursor.moveToNext());
		}
		return list;
	}
}
