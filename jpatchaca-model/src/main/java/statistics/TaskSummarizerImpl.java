package statistics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import periods.Period;
import tasks.TaskView;

public class TaskSummarizerImpl implements TaskSummarizer {

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(
			"MM/yyyy");
	private static final SimpleDateFormat WEEK_YEAR_DATE_FORMAT = new SimpleDateFormat("w MM/yyyy");
	
	@Override
	public List<SummaryItem> summarizePerDay(final List<TaskView> tasks) {

		final List<SummaryItem> items = new ArrayList<SummaryItem>();
		for (final TaskView task : tasks) {
			for (final Date workDay : workDays(task.periods())) {
				items.add(new SummaryItemImpl(workDay, task.name(),
						getHoursInDay(task.periods(), workDay)));
			}
		}

		return sortItems(items);
	}

	private List<SummaryItem> sortItems(final List<SummaryItem> items) {
		Collections.sort(items, new Comparator<SummaryItem>() {

			@Override
			public int compare(final SummaryItem o1, final SummaryItem o2) {
				return o1.compareTo(o2) * -1;
			}

		});

		return items;
	}

	public static double getHoursInDay(final List<Period> periods,
			final Date workDay) {
		double hours = 0.0;
		for (final Period period : periods) {
			if (period.getDay().equals(workDay)) {
				hours += period.getMiliseconds() / DateUtils.MILLIS_PER_HOUR;
			}
		}

		return hours;
	}

	public static List<Date> workDays(final List<Period> periods) {
		final List<Date> days = new ArrayList<Date>();

		for (final Period period : periods) {
			if (!days.contains(period.getDay())) {
				days.add(period.getDay());
			}
		}

		return days;
	}

	public static List<Date> workMonths(final List<Period> periods) {
		final List<Date> days = new ArrayList<Date>();

		for (final Period period : periods) {
			if (!days.contains(period.getMonth())) {
				days.add(period.getMonth());
			}
		}

		return days;
	}

	public static List<Date> workWeeks(final List<Period> periods) {
		
		final List<Date> weeks = new ArrayList<Date>();
		
		for (final Period period : periods) {
			int weekOfYearOfPeriod = period.getWeekOfYear();
			
			boolean addWeek = true;
			for (Date week : weeks) {
				if (weekOfYearOfPeriod == getWeekOfDate(week)) {
					addWeek = false;
					break;
				}
			}
			
			if (addWeek) weeks.add(period.getDay());
		}
		
		return weeks;
	}
	
	@Override
	public List<SummaryItem> summarizePerMonth(final List<TaskView> tasks) {
		final List<SummaryItem> items = new ArrayList<SummaryItem>();
		for (final TaskView task : tasks) {
			final List<Period> periods = task.periods();
			for (final Date workDay : workMonths(periods)) {
				items.add(new SummaryItemImpl(workDay, task.name(),
						SIMPLE_DATE_FORMAT.format(workDay), getHoursInMonth(
								periods, workDay)));
			}
		}

		return sortItems(items);
	}

	@Override
	public List<SummaryItem> summarizePerWeek(final List<TaskView> tasks) {
	
		final List<SummaryItem> items = new ArrayList<SummaryItem>();
		for (final TaskView task : tasks) {
			final List<Period> periods = task.periods();
			for (final Date workWeek : workWeeks(periods)) {
				items.add(new SummaryItemImpl(workWeek, task.name(), WEEK_YEAR_DATE_FORMAT.format(workWeek), getHoursInWeek(periods, workWeek)));
			}
		}
		
		return sortItems(items);
	}
	
	private double getHoursInMonth(final List<Period> periods,
			final Date workMonth) {
		double hours = 0.0;
		for (final Period period : periods) {
			if (period.getMonth().equals(workMonth)) {
				hours += period.getMiliseconds() / DateUtils.MILLIS_PER_HOUR;
			}
		}

		return hours;
	}

	
	
	private double getHoursInWeek(final List<Period> periods, final Date workWeek) {
		
		double hours = 0.0;
		for (final Period period : periods) {
			if (getWeekOfDate(period.getDay()) == getWeekOfDate(workWeek)) {
				hours += period.getMiliseconds() / DateUtils.MILLIS_PER_HOUR;
			}
		}
		
		return hours;
	}
	
	private static int getWeekOfDate(Date date) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
}
