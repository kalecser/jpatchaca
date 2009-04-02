package statistics;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

public class SummaryItemImpl implements SummaryItem {

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("E " + FastDateFormat.getDateInstance(FastDateFormat.SHORT).getPattern());
	private final Date date;
	private final String taskName;
	private final double hours;
	private final String formatedDate;

	public SummaryItemImpl(Date date, String taskName, double hours) {
		this(
				date, 
				taskName, SIMPLE_DATE_FORMAT.format(date), hours);
	}
	
	public SummaryItemImpl(Date date, String taskName, String formatedDate, double hours) {
		this.date = date;
		this.taskName = taskName;
		this.formatedDate = formatedDate;
		this.hours = hours;
	}

	public Date date() {
		return this.date;
	}

	public String taskName() {
		return this.taskName;
	}

	public Double hours() {
		return this.hours;
	}
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		long temp;
		temp = Double.doubleToLongBits(hours);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((taskName == null) ? 0 : taskName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SummaryItemImpl other = (SummaryItemImpl) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Double.doubleToLongBits(hours) != Double
				.doubleToLongBits(other.hours))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Task: " + taskName +  " Date: " + date + " hours: " + hours;
	}

	public int compareTo(SummaryItem otherObj) {
		if (otherObj == null) return 1;
		final SummaryItem otherItem = otherObj;
		
		final int dateResult = date().compareTo(otherItem.date());
		if (dateResult != 0) return dateResult;
		
		final int taskResult = taskName().compareTo(otherItem.taskName());
		return taskResult;
	}

	@Override
	public String getFormatedDate() {
		return formatedDate;
	}
}
