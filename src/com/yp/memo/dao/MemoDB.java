package com.yp.memo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yp.memo.model.Information;
import com.yp.memo.model.Resource;
import com.yp.memo.util.CurrentTime;

public class MemoDB {
	public static final String DB_NAME = "memo";
	public static final int VERSION = 2;
	private static MemoDB memoDB;
	private SQLiteDatabase db;

	private MemoDB(Context context) {
		MemoOpenHelper dbHelper = new MemoOpenHelper(context, DB_NAME, null,
				VERSION);
		db = dbHelper.getWritableDatabase();
	}

	public synchronized static MemoDB getInstance(Context context) {
		if (memoDB == null) {
			memoDB = new MemoDB(context);
		}
		return memoDB;
	}

	// 添加信息
	public int saveInformation(Information information, List<Resource> list) {
		if (information != null) {
			int i = 0;
			int memoId = 0;
			int resourceId = 0;

			// 将文字信息填入到infoValues
			ContentValues infoValues = new ContentValues();
			infoValues.put("memo_info", information.getMemoInfo());
			infoValues.put("memo_remind", information.getMemoRemind());
			infoValues.put("remind_date", information.getRemindDate());
			infoValues.put("memo_finish", information.getMemoFinish());
			infoValues.put("create_date", CurrentTime.getCurrentDate());

			Resource resource = new Resource();
			ContentValues resourceValues = new ContentValues();

			db.beginTransaction(); // 开始事务
			try {

				// 文字信息添加
				i = (int) db.insert("t_information", null, infoValues);
				Log.d("print", "" + i);
				Cursor cursor = db.rawQuery(
						"select max(id) as id from t_information ", null);
				if (cursor.moveToLast()) {
					memoId = cursor.getInt(cursor.getColumnIndex("id"));
				}
				String mId = String.valueOf(memoId);

				// 资源添加
				if (list != null && list.size() != 0) {
					for (int j = 0; j < list.size(); j++) {
						resource = list.get(j);
						resourceValues.put("file_name", resource.getFileName());
						resourceValues.put("file_path", resource.getFilePath());
						int k = (int) db.insert("t_resource", null,
								resourceValues);
						Log.d("print", "" + k);
						cursor = db.rawQuery(
								"select max(id) as id from t_resource ", null);
						if (cursor.moveToLast()) {
							resourceId = cursor.getInt(cursor
									.getColumnIndex("id"));
						}
						String rId = String.valueOf(resourceId);
						db.execSQL(
								"insert into t_info_resource (memo_id,resource_id) values (?,?)",
								new String[] { mId, rId });
					}
				}
				db.setTransactionSuccessful(); // 设置事务成功完成
			} catch (Exception e) {
				Log.e("Transaction", "Failed", e);
			} finally {
				db.endTransaction(); // 结束事务
			}

			return 1;

		}
		return 0;
	}

	// 根据id查看一条信息
	public List<Resource> loadResources(int id) {
		String memo_id = String.valueOf(id).trim();
		List<Resource> list = new ArrayList<Resource>();

		Cursor cursor = db.rawQuery(
				"select * from t_info_resource where memo_id=?",
				new String[] { memo_id });
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					Resource resource = new Resource();
					String resource_id = String.valueOf(cursor.getInt(cursor
							.getColumnIndex("resource_id")));

					Cursor cursorR = db.rawQuery(
							"select * from t_resource where id=?",
							new String[] { resource_id });
					if (cursorR.moveToLast()) {
						resource.setFileName(cursorR.getString(cursorR
								.getColumnIndex("file_name")));
						resource.setFilePath(cursorR.getString(cursorR
								.getColumnIndex("file_path")));
						list.add(resource);
					}
				} while (cursor.moveToNext());
			}
		}
		return list;

	}

	// 读取所有信息
	public List<Information> loadProvinces() {
		List<Information> list = new ArrayList<Information>();
		Cursor cursor = db.query("t_information", null, null, null, null, null,
				"id desc");
		if (cursor.moveToFirst()) {
			do {
				Information info = new Information();
				info.setId(cursor.getInt(cursor.getColumnIndex("id")));
				info.setMemoInfo(cursor.getString(cursor
						.getColumnIndex("memo_info")));
				info.setMemoRemind(cursor.getInt(cursor
						.getColumnIndex("memo_remind")));
				info.setRemindDate(cursor.getString(cursor
						.getColumnIndex("remind_date")));
				info.setMemoFinish(cursor.getInt(cursor
						.getColumnIndex("memo_finish")));
				info.setCreateDate(cursor.getString(cursor
						.getColumnIndex("create_date")));
				list.add(info);
			} while (cursor.moveToNext());
		}
		return list;
	}

	// 删除一条信息
	public void deleteInformation(Information information) {
		int id = information.getId();
		String sid = String.valueOf(id);
		db.execSQL("delete from t_information where id=?", new String[] { sid });
	}

	// 更新一条信息
	public int updateInformation(Information information, List<Resource> list) {
		int id = information.getId();
		String sid = String.valueOf(id);

		int resourceId = 0;

		ContentValues values = new ContentValues();
		ContentValues resourceValues = new ContentValues();
		ContentValues updateRId = new ContentValues();

		values.put("memo_info", information.getMemoInfo());
		values.put("memo_remind", information.getMemoRemind());
		values.put("remind_date", information.getRemindDate());
		values.put("memo_finish", information.getMemoFinish());
		values.put("create_date", information.getCreateDate());

		if (list != null && list.size() != 0) {
			Resource resource = list.get(0);
			if (resource.getId() != 0) {
				String oldRId = String.valueOf(resource.getId());
				db.beginTransaction();
				try {
					resourceValues.put("file_name", resource.getFileName());
					resourceValues.put("file_path", resource.getFilePath());
					int q = db.update("t_resource", resourceValues, "id=?",
							new String[] { oldRId });
					Log.d("print", "" + q);
					int p = db.update("t_information", values, "id=?",
							new String[] { sid });
					return p;

				} catch (Exception e) {
					// TODO: handle exception
					Log.e("transaction", "failed", e);
				} finally {
					db.endTransaction();
				}
			} else {
				db.beginTransaction();
				try {
					resourceValues.put("file_name", resource.getFileName());
					resourceValues.put("file_path", resource.getFilePath());
					int k = (int) db.insert("t_resource", null, resourceValues);
					Log.d("print", "" + k);
					Cursor cursor = db.rawQuery(
							"select max(id) as id  from t_resource", null);
					if (cursor.moveToLast()) {
						resourceId = cursor.getInt(cursor.getColumnIndex("id"));
					}
					String rId = String.valueOf(resourceId);
					updateRId.put("memo_id", sid);
					updateRId.put("resource_id", rId);
					int q = (int) db.insert("t_info_resource", null, updateRId);
					Log.d("print", "" + q);
					int p = db.update("t_information", values, "id=?",
							new String[] { sid });
					return p;

				} catch (Exception e) {
					// TODO: handle exception
					Log.e("transaction", "failed", e);
				} finally {
					db.endTransaction();
				}

			}

		} else {
			int p = db.update("t_information", values, "id=?",
					new String[] { sid });
			return p;
		}

		return -1;

	}

	// 关闭数据库连接
	public void closeDB() {
		db.close();
	}
}
