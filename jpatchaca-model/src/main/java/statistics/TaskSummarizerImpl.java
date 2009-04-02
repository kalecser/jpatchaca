package statistics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import periods.Period;

import tasks.tasks.TaskView;

public class TaskSummarizerImpl implements TaskSummarizer {


	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MM/yyyy");

	public List<SummaryItem> summarizePerDay(List<TaskView> tasks) {
	
		final List<SummaryItem> items = new ArrayList<SummaryItem>();
		for (final TaskView task : tasks)
			for (final Date workDay : workDays(task.periods()))
				items.add(new SummaryItemImpl(workDay, task.name(), getHoursInDay(task.periods(), workDay)));
		
		return sortItems(items);
	}

	private List<SummaryItem> sortItems(final List<SummaryItem> items) {
		Collections.sort(items, new Comparator<SummaryItem>() {
		
			@Override
			public int compare(SummaryItem o1, SummaryItem o2) {
				return o1.compareTo(o2) * -1;
			}
		
		});
		
		return items;
	}

	public static double getHoursInDay(List<Period> periods, Date workDay) {
		double hours = 0.0;
		for (final Period period : periods){
			if (period.getDay().equals(workDay))
				hours += period.getMiliseconds() / DateUtils.MILLIS_PER_HOUR;
		}
		
		return hours;
	}

	public static List<Date> workDays(List<Period> periods) {
		final List<Date> days = new ArrayList<Date>();
		
		for (final Period period : periods){
			if (!days.contains(period.getDay())) days.add(period.getDay());
		}
		
		return days;
	}
	
	public static List<Date> workMonths(List<Period> periods) {
		final List<Date> days = new ArrayList<Date>();
		
		for (final Period period : periods){
			if (!days.contains(period.getMonth())) days.add(period.getMonth());
		}
		
		return days;
	}

	@Override
	public List<SummaryItem> summarizePerMonth(List<TaskView> tasks) {
		final List<SummaryItem> items = new ArrayList<SummaryItem>();
		for (final TaskView task : tasks) {
			List<Period> periods = task.periods();
			for (final Date workDay : workMonths(periods))
				items.add(new SummaryItemImpl(workDay, task.name(), SIMPLE_DATE_FORMAT.format(workDay), getHoursInMonth(periods, workDay)));
		}
		
		return sortItems(items);
	}

	private double getHoursInMonth(List<Period> periods, Date workMonth) {
		double hours = 0.0;
		for (final Period period : periods){
			if (period.getMonth().equals(workMonth))
				hours += period.getMiliseconds() / DateUtils.MILLIS_PER_HOUR;
		}
		
		return hours;
	}

	

}
