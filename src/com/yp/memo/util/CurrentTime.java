package com.yp.memo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public  class CurrentTime {
	public static String getCurrentDate(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createDate = format.format(date);
		return createDate;
	}
}
