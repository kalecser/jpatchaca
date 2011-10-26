package basic;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.time.FastDateFormat;

public class FormatterImpl implements Formatter {

	

	private static final DecimalFormat TIME_SPENT_FORMAT = new DecimalFormat("#0.00");	
	private static final String SHORT_DATE_PATTERN = FastDateFormat.getDateInstance(FastDateFormat.SHORT).getPattern();
	private static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat(SHORT_DATE_PATTERN);
	public static final FastDateFormat SHORT_TIME_FORMAT = FastDateFormat
			.getTimeInstance(FastDateFormat.SHORT, TimeZone.getDefault());
	private static final SimpleDateFormat SHORT_DATE_FORMAT_WITH_WEEKDAY = new SimpleDateFormat("E " + SHORT_DATE_PATTERN);
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
			SHORT_DATE_PATTERN + " " + SHORT_TIME_FORMAT.getPattern());
	private final DecimalFormat decimalFormat;

	public FormatterImpl(){
		decimalFormat = new DecimalFormat();		
	}
	
	@Override
	public synchronized String formatNumber(Double number) {
		return decimalFormat.format(number);
	}

	@Override
	public synchronized String formatShortDate(Date startTime) {
		return SHORT_DATE_FORMAT.format(startTime);
	}

	@Override
	public synchronized String formatShortTime(Date endTime) {
		return SHORT_TIME_FORMAT.format(endTime);
	}

	@Override
	public synchronized String formatTimeSpent(double d) {
		return TIME_SPENT_FORMAT.format(d);
	}

	@Override
	public synchronized Date parseShortDateTime(String dateTimeString) throws ParseException {
		return DATE_TIME_FORMAT.parse(dateTimeString);
	}

	@Override
	public synchronized Date parseShortDate(String dateString) throws ParseException {
		return SHORT_DATE_FORMAT.parse(dateString);
	}

	@Override
	public String formatShortDateWithWeekday(Date date) {
		return SHORT_DATE_FORMAT_WITH_WEEKDAY.format(date);
	}

}
