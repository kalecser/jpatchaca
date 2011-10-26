package periods;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.time.DateUtils;
import org.reactive.Signal;
import org.reactive.Source;

import basic.AlertImpl;
import basic.Subscriber;

@SuppressWarnings("boxing")
public class Period {
	private final Source<Integer> year;
	private final AlertImpl changeAlert;
	private Date start;
	private Date stop;
	private Date day;
	private Date month;
	private boolean worklogSent;

	public Period(final Date start) {

		this.changeAlert = new AlertImpl();
		this.year = new Source<Integer>(getYear(start));

		Validate.notNull(start);

		setStart(start);
	}

	private Date extractDay(final Date start2) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(start2);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);

		return cal.getTime();
	}

	private int getYear(final Date start) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		final int year = calendar.get(Calendar.YEAR);
		return year;
	}

	public Period(final Date start, final Date stop) {

		this(start);
		this.stop = stop;
	}

	public synchronized void setStart(final Date start) {

		this.start = start;
		if (start == null) {
			return;
		}

		final int newYear = getYear(start);
		if (newYear != getYear(this.start)) {
			year.supply(newYear);
		}

		this.day = extractDay(start);
		this.month = extractMonth(start);

		this.changeAlert.fire();
	}

	private Date extractMonth(final Date start2) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(start2);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		return cal.getTime();
	}

	public void setStop(final Date stop) {

		if (stop != null && stop.before(start)) {
			this.stop = start;
		} else {
			this.stop = stop;
		}

		this.changeAlert.fire();
	}

	public synchronized Date startTime() {
		return this.start;
	}

	public Date endTime() {
		return this.stop;
	}

	public Date getDay() {
		return day;
	}

	public Date getMonth() {
		return month;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((stop == null) ? 0 : stop.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Period other = (Period) obj;
		if (start == null) {
			if (other.start != null) {
				return false;
			}
		} else if (!start.equals(other.start)) {
			return false;
		}
		if (stop == null) {
			if (other.stop != null) {
				return false;
			}
		} else if (!stop.equals(other.stop)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final String stopString = (this.stop == null ? "" : this.stop
				.toString());
		return "Period: " + this.start + " - " + stopString + " "
				+ super.toString();
	}
	
	public double getMiliseconds() {
		if (this.stop == null) {
			return 0.0;
		}

		return this.stop.getTime() - this.start.getTime();
	}
	
	public double getHours()
	{
		return getMiliseconds() / DateUtils.MILLIS_PER_HOUR;
	}

	public Long totalTime() {
		if (this.endTime() == null) {
			return 0L;
		}

		return this.endTime().getTime() - this.startTime().getTime();
	}

	public Signal<Integer> year() {
		return year;
	}

	public Period createNewStarting(final Date startDate) {
		return new Period(startDate, endTime());
	}

	public Period createNewEnding(final Date endDate) {
		return new Period(start, endDate);
	}

	public void subscribe(final Subscriber subscriber) {
		changeAlert.subscribe(subscriber);
	}

	public void unsubscribe(final Subscriber subscriber) {
		changeAlert.unsubscribe(subscriber);
	}

	public boolean isWorklogSent() {
		return worklogSent;
	}

	public void setWorklogSent(final boolean worklogSent) {
		this.worklogSent = worklogSent;
	}
}
