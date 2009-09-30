package ui.swing.mainScreen.periods;

import java.util.Date;

import org.apache.commons.lang.Validate;

import basic.Formatter;

class ShortDateWithWeekDay implements Comparable<ShortDateWithWeekDay> {

	private final Formatter formatter;
	private final Date value;

	public ShortDateWithWeekDay(final Formatter formatter, final Date value) {
		Validate.notNull(value, "date must not be null");

		this.formatter = formatter;
		this.value = value;
	}

	@Override
	public String toString() {
		return formatter.formatShortDateWithWeekday(value);
	}

	@Override
	public int compareTo(final ShortDateWithWeekDay o) {
		if (o == null) {
			return 1;
		}

		return this.value.compareTo(o.value);
	}

}
