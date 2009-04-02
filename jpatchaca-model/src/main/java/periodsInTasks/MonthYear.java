package periodsInTasks;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MonthYear {

	private final int month;
	private final int year;
	private final NumberFormat monthFormat = new DecimalFormat("00");

	public MonthYear(final int month, final int year) {
		this.month = month;
		this.year = year;
	}

	public synchronized String print() {
		return monthFormat.format(month) + "-" + year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MonthYear other = (MonthYear) obj;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

}
