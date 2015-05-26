package com.yp.memo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yp.memo.model.Information;
import com.yp.memo.util.CurrentTime;

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
	public int saveInformation(Information information){
		if(information!=null){
			ContentValues values=new ContentValues();
			values.put("memo_info", information.getMemoInfo());
			values.put("memo_remind", information.getMemoRemind());
			values.put("remind_date", information.getRemindDate());
			values.put("memo_finish", information.getMemoFinish());
			values.put("create_date", CurrentTime.getCurrentDate());
			int i =(int) db.insert("t_information", null, values);
			Log.d("ok", "sssss");
		return i;
			
		}
		return -1;
	}
	
	//读取所有信息
	public List<Information> loadProvinces(){
		List<Information> list =new ArrayList<Information>();
		Cursor cursor=db
				.query("t_information", null, null, null, null, null, "id desc");
		if(cursor.moveToFirst()){
			do{
				Information info =new Information();
				info.setId(cursor.getInt(cursor.getColumnIndex("id")));
				info.setMemoInfo(cursor.getString(cursor.getColumnIndex("memo_info")));
				info.setMemoRemind(cursor.getInt(cursor.getColumnIndex("memo_remind")));
				info.setRemindDate(cursor.getString(cursor.getColumnIndex("remind_date")));
				info.setMemoFinish(cursor.getInt(cursor.getColumnIndex("memo_finish")));
				info.setCreateDate(cursor.getString(cursor.getColumnIndex("create_date")));
				list.add(info);
			}while(cursor.moveToNext());
		}
		return list;
	}
	//删除一条信息
	public void deleteInformation(Information information){
		int id=information.getId();
		String sid=String.valueOf(id);
		db.execSQL("delete from t_information where id=?",new String[]{sid});
	}
	
	//更新一条信息
	public int updateInformation(Information information){
		int id=information.getId();
		String sid=String.valueOf(id);
		
		ContentValues values=new ContentValues();
		
		values.put("memo_info", information.getMemoInfo());
		values.put("memo_remind", information.getMemoRemind());
		values.put("remind_date", information.getRemindDate());
		values.put("memo_finish", information.getMemoFinish());
		values.put("create_date", information.getCreateDate());
		
		
		int p=db.update("t_information", values, "id=?", new String[]{sid});
		return p;
		
	}
	//关闭数据库连接
	public void closeDB(){
		db.close();
	}
}
