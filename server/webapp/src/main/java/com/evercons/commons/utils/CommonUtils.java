package com.evercons.commons.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class CommonUtils {

	private static final String TIME_FORMAT = "yyyy- MM-dd HH:mm:ss";
	private static final String DATE_FORMAT = "yyyy- MM-dd HH:mm:ss";

	public static final Date getTime(String time) {
		return formatDate(time, TIME_FORMAT);
	}

	public static final Date getDate(String date) {
		return formatDate(date, DATE_FORMAT);
	}

	public static final Date formatDate(String time, String format) {
		if (StringUtils.isBlank(time) || StringUtils.isBlank(format))
			return null;

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Timestamp getCurrentTimeStamp() {
		return new Timestamp(new Date().getTime());
	}

	public static String getName(String firstName, String lastName) {
		return new StringBuilder().append(lastName).append(" ").append(firstName).toString();
	}
}
