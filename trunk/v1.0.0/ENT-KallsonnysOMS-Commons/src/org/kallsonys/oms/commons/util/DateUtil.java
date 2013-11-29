package org.kallsonys.oms.commons.util;

import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.PARSING_DATE_ERROR;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.kallsonys.oms.commons.Exception.OMSException;

public class DateUtil {

	private static final SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static Date formatDate(String dateParam) {
		if (dateParam == null)
			return null;
		try {
			return format.parse(dateParam);
		} catch (ParseException e) {
			throw new OMSException(PARSING_DATE_ERROR.getCode(),
					PARSING_DATE_ERROR.getMsg());
		}
	}

	public static Date formatDate(Date date) {

		Calendar formatedDate = Calendar.getInstance();

		formatedDate.setTime(date);
		formatedDate.set(Calendar.HOUR_OF_DAY, 0);
		formatedDate.set(Calendar.MINUTE, 0);
		formatedDate.set(Calendar.SECOND, 0);
		formatedDate.set(Calendar.MILLISECOND, 0);

		return formatedDate.getTime();

	}

	public static Date formatDateLastHour(Date date) {

		Calendar formatedDate = Calendar.getInstance();

		formatedDate.setTime(date);
		formatedDate.set(Calendar.HOUR_OF_DAY, 23);
		formatedDate.set(Calendar.MINUTE, 59);
		formatedDate.set(Calendar.SECOND, 59);
		formatedDate.set(Calendar.MILLISECOND, 999);

		return formatedDate.getTime();
	}

	public static Date getFirstDayOfWeek(Date dueDate) {

		Calendar date = Calendar.getInstance();
		date.setTime(dueDate);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);

		int currentDay = date.get(Calendar.DAY_OF_WEEK);

		if (currentDay != Calendar.SUNDAY) {

			int day = currentDay - 1;
			date.add(Calendar.DAY_OF_YEAR, -day);

		}

		return date.getTime();

	}

	public static Date getLastDayOfWeek(Date dueDate) {

		Calendar date = Calendar.getInstance();
		date.setTime(dueDate);
		date.set(Calendar.HOUR_OF_DAY, 23);
		date.set(Calendar.MINUTE, 59);
		date.set(Calendar.SECOND, 59);

		int currentDay = date.get(Calendar.DAY_OF_WEEK);
		if (currentDay != Calendar.SATURDAY) {

			int lastDay = 7 - currentDay;
			date.add(Calendar.DAY_OF_YEAR, lastDay);
		}

		return date.getTime();

	}

	public static String parseDate(Date startDate) {
		if (startDate == null)
			return null;
		return format.format(startDate);
	}

}
