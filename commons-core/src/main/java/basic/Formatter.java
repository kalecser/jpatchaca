package basic;

import java.text.ParseException;
import java.util.Date;

public interface Formatter {
	String formatNumber(Double number);

	String formatShortTime(Date date);

	String formatShortDate(Date date);

	String formatTimeSpent(double d);

	Date parseShortDateTime(String dateTimeString) throws ParseException;

	Date parseShortDate(String dateString) throws ParseException;

	String formatShortDateWithWeekday(Date date);

	
}
