package com.yp.memo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentTime {
	public static String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createDate = format.format(date);
		return createDate;
	}

	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	public static String getAudioFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'AudioRecord'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".3gp";
	}
}
