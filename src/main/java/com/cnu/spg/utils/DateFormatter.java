package com.cnu.spg.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatter {
	public String getDate(Calendar calendar) {
		Calendar cal = calendar.getInstance();
		Date date = new Date(cal.getTimeInMillis());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");	
		String result = df.format(date);
		
		return result;
	}
}
